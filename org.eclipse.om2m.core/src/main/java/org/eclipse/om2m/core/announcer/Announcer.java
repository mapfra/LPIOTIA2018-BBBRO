/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.core.announcer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.exceptions.NotImplementedException;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AnnounceableResource;
import org.eclipse.om2m.commons.resource.AnnouncedResource;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.persistence.PersistenceService;
import org.eclipse.om2m.core.redirector.Redirector;
import org.eclipse.om2m.persistence.service.DBService;
import org.eclipse.om2m.persistence.service.DBTransaction;
import org.eclipse.om2m.persistence.service.util.AnnouncedResourceUtil;

/**
 * Announces/De-Announces resources for which the announcement attribute is
 * activated for each Creation/Delete.
 *
 */

public class Announcer {
	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Announcer.class);

	/**
	 * Announces the created resource.
	 * 
	 * @param announceTo
	 *            - cseId or cseUri
	 * @param announceToAttribute
	 *            - list of attributes to be announce
	 * @param toBeAnnounced
	 *            - announceable resource
	 * @param requestingEntity
	 *            - requesting entity
	 * @return
	 */
	public static List<String> announce(List<String> announceTo, List<String> announceToAttribute,
			AnnounceableResource toBeAnnounced, String requestingEntity, String remoteDestination) {

		AnnouncedResource announcedResource = null;

		switch (toBeAnnounced.getResourceType().intValue()) {
		case ResourceType.AE:
			AEAnnc aeAnnc = new AEAnnc();
			aeAnnc.setAppID(((AE) toBeAnnounced).getAppID());
			announcedResource = aeAnnc;
			break;
		case ResourceType.FLEXCONTAINER:
			FlexContainerAnnc flexContainerAnnc = new FlexContainerAnnc();
			flexContainerAnnc.setContainerDefinition(((AbstractFlexContainer) toBeAnnounced).getContainerDefinition());
			announcedResource = flexContainerAnnc;
		default:
		}
		
		announcedResource.setName(toBeAnnounced.getName() + "_Annc");

		// get the database service
		DBService dbs = PersistenceService.getInstance().getDbService();
		DBTransaction transaction = dbs.getDbTransaction();
		transaction.open();

		AnnouncedResourceUtil dao = dbs.getDBUtilManager().getAnnouncedResourceUtil();

		// name is in the request
		// labels
		announcedResource.getLabels().addAll(toBeAnnounced.getLabels());
		// link
		announcedResource.setLink(toBeAnnounced.getResourceID());

		for (String cseId : announceTo) {

			RemoteCSEEntity remoteCSE = dbs.getDAOFactory().getRemoteCSEbyCseIdDAO().find(transaction, cseId);
			RequestPrimitive request = new RequestPrimitive();

			CreatedAnnouncedResourceEntity parentResource = dao.find(transaction, toBeAnnounced.getParentID(), cseId);
			if (parentResource != null) {

				request.setTargetId(parentResource.getRemoteAnnouncedId());
			} else {

				if (!remoteDestination.startsWith("/")) {
					remoteDestination = "/" + remoteDestination;
				}
				request.setTargetId(remoteCSE.getRemoteCseId() + "/" + remoteCSE.getName() + "/" + Constants.CSE_NAME
						+ remoteDestination);
			}
			
			request.setOperation(Operation.CREATE);
			switch (toBeAnnounced.getResourceType().intValue()) {
			case ResourceType.AE:
				request.setResourceType(ResourceType.AE_ANNC);
				break;
			case ResourceType.FLEXCONTAINER:
				request.setResourceType(ResourceType.FLEXCONTAINER_ANNC);
				break;
			default:
				throw new NotImplementedException("");
			}
			request.setContent(announcedResource);
			request.setRequestContentType(MimeMediaType.OBJ);
			request.setReturnContentType(MimeMediaType.OBJ);
			request.setFrom(requestingEntity);

			ResponsePrimitive response = Redirector.retarget(request);
			if (response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
				// retrieve announcedResources
				AnnouncedResource ar = (AnnouncedResource) response.getContent();

				CreatedAnnouncedResourceEntity announcedResourceEntity = new CreatedAnnouncedResourceEntity();
				announcedResourceEntity.setAnnounceCseId(cseId);
				announcedResourceEntity.setLocalAnnounceableId(toBeAnnounced.getResourceID());
				announcedResourceEntity.setRemoteAnnouncedId(ar.getResourceID());

				dao.create(transaction, announcedResourceEntity);

			}

		}

		transaction.commit();
		transaction.close();

		return null;
	}

	/**
	 * De-Announces the deleted resource.
	 * 
	 * @param announceTo
	 *            - sclId target .
	 * @param uri
	 *            - resource uri.
	 * @param requestingEntity
	 *            - Requesting Entity
	 */
	public static void deAnnounce(List<String> announceTo, ResourceEntity toBeDeAnnounced, String requestingEntity) {

		// get the database service
		DBService dbs = PersistenceService.getInstance().getDbService();
		DBTransaction transaction = dbs.getDbTransaction();
		transaction.open();

		AnnouncedResourceUtil announceResourceUtil = dbs.getDBUtilManager().getAnnouncedResourceUtil();

		for (String cseId : announceTo) {

			CreatedAnnouncedResourceEntity are = announceResourceUtil.find(transaction, toBeDeAnnounced.getResourceID(),
					cseId);
			if (are != null) {
				RequestPrimitive request = new RequestPrimitive();
				request.setTargetId(are.getRemoteAnnouncedId());
				request.setOperation(Operation.DELETE);
				request.setFrom(requestingEntity);
				Redirector.retarget(request);

				announceResourceUtil.delete(transaction, are);
			}

		}
		transaction.commit();
		transaction.close();
	}
}
