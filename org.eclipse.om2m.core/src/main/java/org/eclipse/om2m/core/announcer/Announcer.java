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

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MgmtDefinitionTypes;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.entities.AnnounceableSubordinateEntity;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.exceptions.NotImplementedException;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.AnnounceableResource;
import org.eclipse.om2m.commons.resource.AnnouncedMgmtResource;
import org.eclipse.om2m.commons.resource.AnnouncedResource;
import org.eclipse.om2m.commons.resource.AreaNwkDeviceInfo;
import org.eclipse.om2m.commons.resource.AreaNwkDeviceInfoAnnc;
import org.eclipse.om2m.commons.resource.AreaNwkInfo;
import org.eclipse.om2m.commons.resource.AreaNwkInfoAnnc;
import org.eclipse.om2m.commons.resource.DeviceInfo;
import org.eclipse.om2m.commons.resource.DeviceInfoAnnc;
import org.eclipse.om2m.commons.resource.MgmtObj;
import org.eclipse.om2m.commons.resource.Node;
import org.eclipse.om2m.commons.resource.NodeAnnc;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FlexContainerFactory;
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

public class Announcer implements Constants {
	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Announcer.class);
	
	private static final String SUFFIX = "_Annc";

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
	public static void announce(AnnounceableResource toBeAnnounced, String requestingEntity, String remoteDestination) {
		AnnouncedResource announcedResource = null;
		LOGGER.info("announce " + toBeAnnounced + " with " + toBeAnnounced.getAnnouncedAttribute());
		int type = toBeAnnounced.getResourceType().intValue();
		switch (type) {
		case ResourceType.AE:
			announcedResource = createAEAnnc((AE)toBeAnnounced); break;
		case ResourceType.FLEXCONTAINER:
			announcedResource = createFlexContainerAnnc((AbstractFlexContainer)toBeAnnounced); break;
		case ResourceType.NODE:
			announcedResource = createNodeAnnc((Node)toBeAnnounced); break;
		case ResourceType.MGMT_OBJ:
			announcedResource = createMgmtObjAnnc((MgmtObj)toBeAnnounced); break;
		default:
			throw new NotImplementedException("Not implemented " + type);
		}
		
		announcedResource.setName(toBeAnnounced.getName() + SUFFIX);

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

		for (String cseId : toBeAnnounced.getAnnounceTo()) { 
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
			request.setResourceType(BigInteger.valueOf(
					toBeAnnounced.getResourceType().intValue() + 10000));
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
	public static void deAnnounce(AnnounceableSubordinateEntity toBeDeAnnounced, String requestingEntity) {
		// get the database service
		DBService dbs = PersistenceService.getInstance().getDbService();
		DBTransaction transaction = dbs.getDbTransaction();
		transaction.open();

		AnnouncedResourceUtil announceResourceUtil = dbs.getDBUtilManager().getAnnouncedResourceUtil();

		for (String cseId : toBeDeAnnounced.getAnnounceTo()) {
			CreatedAnnouncedResourceEntity are = announceResourceUtil.find(transaction, 
					toBeDeAnnounced.getResourceID(), cseId);
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
	
	private static AnnouncedResource createAEAnnc(AE res) {
		AEAnnc annc = new AEAnnc();
		// Mandatory Announced
		annc.setExpirationTime(res.getExpirationTime());
//		annc.getAccessControlPolicyIDs().addAll(res.getAccessControlPolicyIDs());
		annc.getLabels().addAll(res.getLabels());
		// Optionally Announced
		for (String aa : res.getAnnouncedAttribute()) {
			switch(aa) {
			case ShortName.APP_NAME: annc.setAppName(res.getAppName()); break;
			case ShortName.APP_ID: annc.setAppID(res.getAppID()); break;
			case ShortName.AE_ID: annc.setAEID(res.getAEID()); break;
			case ShortName.POA: annc.getPointOfAccess().addAll(res.getPointOfAccess()); break;
			case ShortName.ONTOLOGY_REF: annc.setOntologyRef(res.getOntologyRef()); break;
			case ShortName.NODE_LINK: annc.setNodeLink(res.getNodeLink()); break;
			}
		}
		return annc;
	}

	private static AnnouncedResource createFlexContainerAnnc(AbstractFlexContainer res) {
		AbstractFlexContainerAnnc annc = FlexContainerFactory.getSpecializationFlexContainerAnnc(res.getShortName() + "Annc");
		// Mandatory Announced
		annc.setExpirationTime(res.getExpirationTime());
//		annc.getAccessControlPolicyIDs().addAll(res.getAccessControlPolicyIDs());
		annc.getLabels().addAll(res.getLabels());
		for (String aa : res.getAnnouncedAttribute()) {
			switch(aa) {
			case ShortName.STATETAG: annc.setStateTag(res.getStateTag()); break;
			case ShortName.ONTOLOGY_REF: annc.setOntologyRef(res.getOntologyRef()); break;
			case ShortName.NODE_LINK: annc.setNodeLink(res.getNodeLink() + SUFFIX); break;
			}
		}
		return annc;
	}

	private static AnnouncedResource createNodeAnnc(Node res) {
		NodeAnnc annc = new NodeAnnc();
		// Mandatory Announced
		annc.setExpirationTime(res.getExpirationTime());
//		annc.getAccessControlPolicyIDs().addAll(res.getAccessControlPolicyIDs());
		annc.getLabels().addAll(res.getLabels());
		annc.setNodeID(res.getNodeID());
		// Optionally Announced
		for (String aa : res.getAnnouncedAttribute()) {
			switch(aa) {
			case ShortName.HOSTED_CSE_LINK: annc.setHostedCSELink(res.getHostedCSELink()); break;
			case ShortName.HOSTED_APP_LINK: annc.setHostedAppLinks(res.getHostedAppLinks() + SUFFIX); break;
			}
		}
		return annc;
	}

	private static AnnouncedResource createMgmtObjAnnc(MgmtObj res) {
		AnnouncedMgmtResource annc = null;
		BigInteger type = res.getMgmtDefinition();
		if (type.equals(MgmtDefinitionTypes.AREA_NWK_INFO)) {
			AreaNwkInfo ani = (AreaNwkInfo) res;
			AreaNwkInfoAnnc ania = new AreaNwkInfoAnnc();
			annc = ania;
			for (String aa : res.getAnnouncedAttribute()) {
				switch(aa) {
				case ShortName.AREA_NWK_TYPE: ania.setAreaNwkType(ani.getAreaNwkType()); break;
				}
			}
		}
		else if (type.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO)) {
			AreaNwkDeviceInfo andi = (AreaNwkDeviceInfo) res;
			AreaNwkDeviceInfoAnnc andia = new AreaNwkDeviceInfoAnnc();
			annc = andia;
			for (String aa : res.getAnnouncedAttribute()) {
				switch(aa) {
				case ShortName.DEV_ID: andia.setDevID(andi.getDevID()); break;
				case ShortName.DEV_TYPE: andia.setDevType(andi.getDevType()); break;
				case ShortName.AREA_NWK_ID: andia.setAreaNwkId(andi.getAreaNwkId()); break;
				case ShortName.SLEEP_INTERVAL: andia.setSleepInterval(andi.getSleepInterval()); break;
				case ShortName.SLEEP_DURATION: andia.setSleepDuration(andi.getSleepDuration()); break;
				case ShortName.STATUS: andia.setStatus(andi.getStatus()); break;
				}
			}
		}
		else if (type.equals(MgmtDefinitionTypes.DEVICE_INFO)) {
			DeviceInfo di = (DeviceInfo) res;
			DeviceInfoAnnc dia = new DeviceInfoAnnc();
			annc = dia;
			for (String aa : res.getAnnouncedAttribute()) {
				switch(aa) {
				case ShortName.DEVICE_LABEL: dia.setDeviceLabel(di.getDeviceLabel()); break;
				case ShortName.MANUFACTURER: dia.setManufacturer(di.getManufacturer()); break;
				case ShortName.DEVICE_MODEL: dia.setModel(di.getModel()); break;
				case ShortName.DEVICE_TYPE: dia.setDeviceType(di.getDeviceType()); break;
				case ShortName.FW_VERSION: dia.setFwVersion(di.getFwVersion()); break;
				case ShortName.HW_VERSION: dia.setHwVersion(di.getHwVersion()); break;
				case ShortName.OS_VERSION: dia.setOsVersion(di.getOsVersion()); break;
				}
			}
		}
		//	else if (type.equals(MgmtDefinitionTypes.FIRMWARE))
		//		return new FirmwareAnnc();
		//	else if (type.equals(MgmtDefinitionTypes.SOFTWARE))
		//		return new SoftwareAnnc();
		//	else if (type.equals(MgmtDefinitionTypes.MEMORY))
		//		return new MemoryAnnc();
		//	else if (type.equals(MgmtDefinitionTypes.BATTERY))
		//		return new BatteryAnnc();
		//	else if (type.equals(MgmtDefinitionTypes.DEVICE_CAPABILITY))
		//		return new DeviceCapabilityAnnc();
		//	else if (type.equals(MgmtDefinitionTypes.REBOOT))
		//		return new RebootAnnc();
		//	else if (type.equals(MgmtDefinitionTypes.EVENT_LOG))
		//		return new EventLogAnnc();
		
		// Common attributes
		if (annc != null) {
			// Mandatory Announced
			annc.setExpirationTime(res.getExpirationTime());
//			annc.getAccessControlPolicyIDs().addAll(res.getAccessControlPolicyIDs());
			annc.getLabels().addAll(res.getLabels());
			// Optionally Announced
			for (String aa : res.getAnnouncedAttribute()) {
				switch(aa) {
				case ShortName.OBJ_IDS: annc.getObjectIDs().addAll(res.getObjectIDs()); break;
				case ShortName.OBJ_PATHS: annc.getObjectPaths().addAll(res.getObjectPaths()); break;
				case ShortName.DESCRIPTION: annc.setDescription(res.getDescription()); break;
				}
			}
		}
		return annc;
	}

}
