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

import org.eclipse.om2m.commons.resource.AccessRight;
import org.eclipse.om2m.commons.resource.AccessRightAnnc;
import org.eclipse.om2m.commons.resource.AccessRights;
import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.resource.Refs;

/**
 * Implements CRUD Methods for {@link AccessRights} collection resource
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
public class AccessRightsDAO extends DAO<AccessRights> {

	/**
	 * Creates an {@link AccessRights} collection resource in the DataBase.
	 * 
	 * @param resource
	 *            - The {@link AccessRights} collection resource to create
	 */
	@Override
	public void create(AccessRights resource, EntityManager em) {
		//NOT IMPLEMENTED
	}

	/**
	 * Retrieves the {@link AccessRights} collection resource based on its uri
	 * with sub-resources references
	 * 
	 * @param uri
	 *            - uri of the {@link AccessRights} collection resource
	 * @return The requested {@link AccessRights} collection resource otherwise
	 *         null
	 */
	public AccessRights find(String uri, EntityManager em) {
		AccessRights accessRights = new AccessRights();
		accessRights.setUri(uri);

		accessRights.getAccessRightCollection().getNamedReference().clear();
		accessRights.getAccessRightAnncCollection().getNamedReference().clear();

		// Find AccessRight sub-resources and add their references			
		String q = DBUtil.generateLikeRequest(DBEntities.ACCESSRIGHT_ENTITY, uri);
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<AccessRight> arList = query.getResultList();
		for (AccessRight ar : arList){
			ReferenceToNamedResource reference = new ReferenceToNamedResource();
			reference.setId(ar.getId());
			reference.setValue(ar.getUri());
			accessRights.getAccessRightCollection().getNamedReference().add(reference);
		}

		// Find AccessRightAnnc sub-resources Resources and add their
		// references
		String r = DBUtil.generateLikeRequest(DBEntities.ACCESSRIGHT_ANNC_ENTITY, uri);
		javax.persistence.Query query2 = em.createQuery(r);
		@SuppressWarnings("unchecked")
		List<AccessRightAnnc> araList = query2.getResultList();
		for (AccessRightAnnc ara : araList) {
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(ara.getId());
			ref.setValue(ara.getUri());
			accessRights.getAccessRightAnncCollection().getNamedReference().add(ref);
		}

		return accessRights;
	}

	/**
	 * Updates an existing {@link AccessRights} collection resource in the
	 * DataBase
	 * 
	 * @param resource
	 *            - The {@link AccessRights} the updated resource
	 */
	@Override
	public void update(AccessRights resource, EntityManager em) {
		// NOT IMPLEMENTED
	}

	/**
	 * Deletes the {@link AccessRights} collection resource from the DataBase
	 * without validating the transaction
	 * 
	 * @Param the {@link AccessRights} collection resource to delete
	 */
	public void delete(AccessRights resource, EntityManager em) {
		// Delete sub-resources
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);

		// Delete accessRight sub-resources
		String q = DBUtil.generateLikeRequest(DBEntities.ACCESSRIGHT_ENTITY, resource.getUri());
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<AccessRight> arList = query.getResultList();
		for (AccessRight ar : arList){
			ar.setSubscriptionsReference(ar.getUri()+Refs.SUBSCRIPTIONS_REF);
			DAOFactory.getAccessRightDAO().delete(ar, em);
		}

		// Delete accessRightAnnc sub-resources
		String r = DBUtil.generateLikeRequest(DBEntities.ACCESSRIGHT_ANNC_ENTITY, resource.getUri());
		javax.persistence.Query query2 = em.createQuery(r);
		@SuppressWarnings("unchecked")
		List<AccessRightAnnc> araList = query2.getResultList();
		for (AccessRightAnnc ara : araList) {
			DAOFactory.getAccessRightAnncDAO().delete(ara, em);
		}

	}
}
