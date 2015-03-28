/*******************************************************************************
 * Copyright (c) 2013-2015 LAAS-CNRS (www.laas.fr) 
 * 7 Colonel Roche 31077 Toulouse - France
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification, 
 * 		conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification, 
 * 		conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test 
 * 		and documentation.
 *     Guillaume Garzone - Conception, implementation, test and documentation.
 *     Francois Aissaoui - Conception, implementation, test and documentation.
 ******************************************************************************/
package org.eclipse.om2m.core.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.AttachedDevice;
import org.eclipse.om2m.commons.resource.AttachedDevices;
import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.resource.Refs;

/**
 * Implements CRUD Methods for {@link AttachedDevices} collection resource
 * persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com
 *         ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com
 *         ></li>
 *         </ul>
 */
public class AttachedDevicesDAO extends DAO<AttachedDevices> {

	/**
	 * Creates an {@link AttachedDevices} collection resource in the DataBase.
	 * 
	 * @param resource
	 *            - The {@link AttachedDevices} collection resource to create
	 */
	@Override
	public void create(AttachedDevices resource, EntityManager em) {
		// NOT IMPLEMENTED
	}

	/**
	 * Retrieves the {@link AttachedDevices} collection resource based on its
	 * uri with sub-resources references
	 * 
	 * @param uri
	 *            - uri of the {@link AttachedDevices} collection resource
	 * @return The requested {@link AttachedDevices} collection resource
	 *         otherwise null
	 */
	public AttachedDevices find(String uri, EntityManager em) {
		AttachedDevices attachedDevices = new AttachedDevices();
		attachedDevices.setUri(uri);
		attachedDevices.getAttachedDeviceCollection().getNamedReference().clear();
		
		// Find AttachedDevice sub-resources and add their references
		String q = DBUtil.generateLikeRequest(DBEntities.ATTACHED_DEVICE_ENTITY, uri);
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<AttachedDevice> result = query.getResultList();
		for (AttachedDevice ad : result){
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(ad.getId());
			ref.setValue(ad.getUri());
			attachedDevices.getAttachedDeviceCollection().getNamedReference().add(ref);
		}

		return attachedDevices;
	}

	/**
	 * Updates an existing {@link AttachedDevices} collection resource in the
	 * DataBase
	 * 
	 * @param resource
	 *            - The {@link AttachedDevices} the updated resource
	 */
	@Override
	public void update(AttachedDevices resource, EntityManager em) {
		// NOT IMPLEMENTED
	}

	/**
	 * Deletes the {@link AttachedDevices} collection resource from the DataBase
	 * without validating the transaction
	 * 
	 * @Param the {@link AttachedDevices} collection resource to delete
	 */
	public void delete(AttachedDevices resource, EntityManager em) {
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);

		// Delete attachedDevice sub-resources
		String q = DBUtil.generateLikeRequest(DBEntities.ATTACHED_DEVICE_ENTITY, resource.getUri());
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<AttachedDevice> result = query.getResultList();
		for (AttachedDevice ad : result){
			ad.setMgmtObjsReference(ad.getUri()+Refs.MGMTOBJS_REF);
			ad.setSubscriptionsReference(ad.getUri()+Refs.SUBSCRIPTIONS_REF);
			DAOFactory.getAttachedDeviceDAO().delete(ad, em);
		}
	}
}
