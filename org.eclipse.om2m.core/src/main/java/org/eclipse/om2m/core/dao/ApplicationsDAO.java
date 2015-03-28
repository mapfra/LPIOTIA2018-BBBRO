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
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test
 *         and documentation.
 *     Guillaume Garzone - Conception, implementation, test and documentation.
 *     Francois Aissaoui - Conception, implementation, test and documentation.
 ******************************************************************************/
package org.eclipse.om2m.core.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.ApplicationAnnc;
import org.eclipse.om2m.commons.resource.Applications;
import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.MgmtObjs;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.resource.Refs;

/**
 * Implements CRUD Methods for {@link Applications} collection resource
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
public class ApplicationsDAO extends DAO<Applications> {

	/**
	 * Creates an {@link Applications} collection resource in the DataBase.
	 * 
	 * @param resource
	 *            - The {@link Applications} collection resource to create
	 */
	@Override
	public void create(Applications resource, EntityManager em) {
		// NOT IMPLEMENTED
	}

	/**
	 * Retrieves the {@link Applications} collection resource based on its uri
	 * with sub-resources references
	 * 
	 * @param uri
	 *            - uri of the {@link Applications} collection resource
	 * @return The requested {@link Applications} collection resource otherwise
	 *         null
	 */
	public Applications find(String uri, EntityManager em) {
		Applications applications = new Applications();
		applications.setUri(uri);

		applications.getApplicationCollection().getNamedReference().clear();
		applications.getApplicationAnncCollection().getNamedReference().clear();

		// Find Application sub-resources and add their references
		String q = DBUtil.generateLikeRequest(DBEntities.APPLICATION_ENTITY, uri);
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<Application> result = query.getResultList();

		for (Application a : result) {
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(a.getAppId());
			ref.setValue(a.getUri());
			applications.getApplicationCollection().getNamedReference()
			.add(ref);
		}

		// Find ApplicationAnnc sub-resources and add their references
		String q2 = DBUtil.generateLikeRequest(DBEntities.APPLICATION_ANNC_ENTITY, uri);
		javax.persistence.Query query2 = em.createQuery(q2);
		@SuppressWarnings("unchecked")
		List<ApplicationAnnc> resultAppAnnc = query2.getResultList();

		for (ApplicationAnnc a : resultAppAnnc) {
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(a.getId());
			ref.setValue(a.getUri());
			applications.getApplicationAnncCollection().getNamedReference()
			.add(ref);
		}
		return applications;
	}

	/**
	 * Updates an existing {@link Applications} collection resource in the
	 * DataBase
	 * 
	 * @param resource
	 *            - The {@link Applications} the updated resource
	 */
	@Override
	public void update(Applications resource, EntityManager em) {
		// NOT IMPLEMENTED
	}

	/**
	 * Deletes the {@link Applications} collection resource from the DataBase
	 * without validating the transaction
	 * 
	 * @Param the {@link Applications} collection resource to delete
	 */
	public void delete(Applications resource, EntityManager em) {
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);
		
		// Delete mgmtObjs
		MgmtObjs mgmtObjs = new MgmtObjs();
		mgmtObjs.setUri(resource.getMgmtObjsReference());
		mgmtObjs.setSubscriptionsReference(mgmtObjs.getUri() + Refs.SUBSCRIPTIONS_REF);
		DAOFactory.getMgmtObjsDAO().delete(mgmtObjs,em);

		// Delete application sub-resources
		String q = DBUtil.generateLikeRequest(DBEntities.APPLICATION_ENTITY, resource.getUri());
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<Application> result = query.getResultList();
		for (Application a : result) {
			a.setContainersReference(a.getUri() + Refs.CONTAINERS_REF);
			a.setGroupsReference(a.getUri() + Refs.GROUPS_REF);
			a.setAccessRightsReference(a.getUri() + Refs.ACCESSRIGHTS_REF);
			a.setSubscriptionsReference(a.getUri() + Refs.SUBSCRIPTIONS_REF);
			a.setNotificationChannelsReference(a.getUri() + Refs.NOTIFICATIONCHANNELS_REF);
			DAOFactory.getApplicationDAO().delete(a, em);
		}

		// Delete applicationAnnc sub-resources
		String q2 = DBUtil.generateLikeRequest(DBEntities.APPLICATION_ANNC_ENTITY, resource.getUri());
		javax.persistence.Query query2 = em.createQuery(q2);
		@SuppressWarnings("unchecked")
		List<ApplicationAnnc> resultAppAnnc = query2.getResultList();
		for (ApplicationAnnc a : resultAppAnnc) {
			a.setContainersReference(a.getUri() + Refs.CONTAINERS_REF);
			a.setGroupsReference(a.getUri() + Refs.GROUPS_REF);
			a.setAccessRightsReference(a.getUri() + Refs.ACCESSRIGHTS_REF);
			DAOFactory.getApplicationAnncDAO().delete(a, em);
		}
	}
}
