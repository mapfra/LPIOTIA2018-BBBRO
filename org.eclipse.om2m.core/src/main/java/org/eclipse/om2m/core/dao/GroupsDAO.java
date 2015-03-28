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

import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.Group;
import org.eclipse.om2m.commons.resource.GroupAnnc;
import org.eclipse.om2m.commons.resource.Groups;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.resource.Refs;

/**
 * Implements CRUD Methods for {@link Groups} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class GroupsDAO extends DAO<Groups> {

	/**
	 * Creates an {@link Groups} collection resource in the DataBase.
	 * @param resource - The {@link Groups} collection resource to create
	 */
	@Override
	public void create(Groups resource, EntityManager em) {
		// NOT ALLOWED
	}

	/**
	 * Retrieves the {@link Groups} collection resource based on its uri with sub-resources references
	 * @param uri - uri of the {@link Groups} collection resource
	 * @return The requested {@link Groups} collection resource otherwise null
	 */
	public Groups find(String uri, EntityManager em) {
		Groups groups = new Groups();
		groups.setUri(uri);

		// cleaning references
		groups.getGroupCollection().getNamedReference().clear();
		groups.getGroupAnncCollection().getNamedReference().clear();

		//Find Group sub-resources and add their references
		String q = DBUtil.generateLikeRequest(DBEntities.GROUP_ENTITY, uri);
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<Group> result = query.getResultList();

		for (Group g : result){
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(g.getId());
			ref.setValue(g.getUri());
			groups.getGroupCollection().getNamedReference().add(ref);
		}

		//Find GroupAnnc sub-resources and add their references
		String q2 = DBUtil.generateLikeRequest(DBEntities.GROUP_ANNC_ENTITY, uri);
		javax.persistence.Query query2 = em.createQuery(q2);
		@SuppressWarnings("unchecked")
		List<GroupAnnc> result2 = query2.getResultList();

		for (GroupAnnc gac : result2){
			ReferenceToNamedResource reference = new ReferenceToNamedResource();
			reference.setId(gac.getId());
			reference.setValue(gac.getUri());
			groups.getGroupAnncCollection().getNamedReference().add(reference);
		}
		return groups;
	}

	/**
	 * Updates an existing {@link Groups} collection resource in the DataBase
	 * @param resource - The {@link Groups} the updated resource
	 */
	@Override
	public void update(Groups resource, EntityManager em) {
		//NOT IMPLEMENTED
	}

	/**
	 * Deletes the {@link Groups} collection resource from the DataBase without validating the transaction
	 * @Param the {@link Groups} collection resource to delete
	 */
	public void delete(Groups resource, EntityManager em) {
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);

		// Delete group sub-resources
		String q = DBUtil.generateLikeRequest(DBEntities.GROUP_ENTITY, resource.getUri());
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<Group> result = query.getResultList();

		for (Group g : result){
			g.setSubscriptionsReference(g.getUri()+Refs.SUBSCRIPTIONS_REF);
			DAOFactory.getGroupDAO().delete(g, em);
		}

		// Delete groupAnnc sub-resources
		String q2 = DBUtil.generateLikeRequest(DBEntities.GROUP_ANNC_ENTITY, resource.getUri());
		javax.persistence.Query query2 = em.createQuery(q2);
		@SuppressWarnings("unchecked")
		List<GroupAnnc> result2 = query2.getResultList();

		for (GroupAnnc gac : result2){
			DAOFactory.getGroupAnncDAO().delete(gac, em);
		}

	}
}
