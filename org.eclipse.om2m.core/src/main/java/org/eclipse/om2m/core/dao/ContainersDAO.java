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

import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContainerAnnc;
import org.eclipse.om2m.commons.resource.Containers;
import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.LocationContainer;
import org.eclipse.om2m.commons.resource.LocationContainerAnnc;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.resource.Refs;

/**
 * Implements CRUD Methods for {@link Containers} collection resource
 * persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.Feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com
 *         ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com
 *         ></li>
 *         </ul>
 */
public class ContainersDAO extends DAO<Containers> {

	/**
	 * Creates an {@link Containers} collection resource in the DataBase.
	 * 
	 * @param resource
	 *            - The {@link Containers} collection resource to create
	 */
	@Override
	public void create(Containers resource, EntityManager em) {
		// NOT IMPLEMENTED
	}

	/**
	 * Retrieves the {@link Containers} collection resource based on its uri
	 * with sub-resources references
	 * 
	 * @param uri
	 *            - uri of the {@link Containers} collection resource
	 * @return The requested {@link Containers} collection resource otherwise
	 *         null
	 */
	public Containers find(String uri, EntityManager em) {
		Containers containers = new Containers();
		containers.setUri(uri);

		// cleaning collections
		containers.getContainerCollection().getNamedReference().clear();
		containers.getContainerAnncCollection().getNamedReference().clear();
		containers.getLocationContainerCollection().getNamedReference().clear();
		containers.getLocationContainerAnncCollection().getNamedReference().clear();

		// Find Container sub-resources and add their references
		String q = DBUtil.generateLikeRequest(DBEntities.CONTAINER_ENTITY, uri);
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();

		for (Container c : result) {
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(c.getId());
			ref.setValue(c.getUri());
			containers.getContainerCollection().getNamedReference().add(ref);
		}

		// Find ContainerAnnc sub-resources and add their references
		String q2 = DBUtil.generateLikeRequest(DBEntities.CONTAINER_ANNC_ENTITY, uri);
		javax.persistence.Query query2 = em.createQuery(q2);
		@SuppressWarnings("unchecked")
		List<ContainerAnnc> result2 = query2.getResultList();
		for (ContainerAnnc ca : result2) {
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(ca.getId());
			ref.setValue(ca.getUri());
			containers.getContainerAnncCollection().getNamedReference().add(ref);
		}

		// Find LocationContainer sub-resources and add their references
		String q3 = DBUtil.generateLikeRequest(DBEntities.LOCATION_CONTAINER_ENTITY, uri);
		javax.persistence.Query query3 = em.createQuery(q3);
		@SuppressWarnings("unchecked")
		List<LocationContainer> result3 = query3.getResultList();
		for (LocationContainer lc : result3) {
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(lc.getId());
			ref.setValue(lc.getUri());
			containers.getLocationContainerCollection().getNamedReference().add(ref);
		}

		// Find LocationContainerAnnc sub-resources and add their references
		String q4 = DBUtil.generateLikeRequest(DBEntities.LOCATION_CONTAINER_ANNC_ENTITY, uri);
		javax.persistence.Query query4 = em.createQuery(q4);
		@SuppressWarnings("unchecked")
		List<LocationContainerAnnc> result4 = query4.getResultList();
		for (LocationContainerAnnc lca : result4){
			ReferenceToNamedResource ref = new ReferenceToNamedResource();
			ref.setId(lca.getId());
			ref.setValue(lca.getUri());
			containers.getLocationContainerAnncCollection().getNamedReference().add(ref);
		}

		return containers;
	}

	/**
	 * Updates an existing {@link Containers} collection resource in the
	 * DataBase
	 * 
	 * @param resource
	 *            - The {@link Containers} the updated resource
	 */
	@Override
	public void update(Containers resource, EntityManager em) {
		// NOT IMPLEMENTED
	}

	/**
	 * Deletes the {@link Containers} collection resource from the DataBase
	 * without validating the transaction
	 * 
	 * @Param the {@link Containers} collection resource to delete
	 */
	public void delete(Containers resource, EntityManager em) {
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);

		// Delete Container sub-resources
		String q = DBUtil.generateLikeRequest(DBEntities.CONTAINER_ENTITY, resource.getUri());
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<Container> result = query.getResultList();

		for (Container c : result) {
			c.setContentInstancesReference(c.getUri() + Refs.CONTENTINSTANCES_REF);
			c.setSubscriptionsReference(c.getUri() + Refs.SUBSCRIPTIONS_REF);
			DAOFactory.getContainerDAO().delete(c, em);
		}

		// Delete ContainerAnnc sub-resources
		String q2 = DBUtil.generateLikeRequest(DBEntities.CONTAINER_ANNC_ENTITY, resource.getUri());
		javax.persistence.Query query2 = em.createQuery(q2);
		@SuppressWarnings("unchecked")
		List<ContainerAnnc> result2 = query2.getResultList();
		for (ContainerAnnc ca : result2) {
			DAOFactory.getContainerAnncDAO().delete(ca, em);
		}

		// Delete locationContainer sub-resources
		String q3 = DBUtil.generateLikeRequest(DBEntities.LOCATION_CONTAINER_ENTITY, resource.getUri());
		javax.persistence.Query query3 = em.createQuery(q3);
		@SuppressWarnings("unchecked")
		List<LocationContainer> result3 = query3.getResultList();
		for (LocationContainer lc : result3) {
			lc.setContentInstancesReference(lc.getUri() + Refs.CONTENTINSTANCES_REF);
			lc.setSubscriptionsReference(lc.getUri() + Refs.SUBSCRIPTIONS_REF);
			DAOFactory.getLocationContainerDAO().delete(lc, em);
		}

		// Delete locationContainerAnnc sub-resources
		String q4 = DBUtil.generateLikeRequest(DBEntities.LOCATION_CONTAINER_ANNC_ENTITY, resource.getUri());
		javax.persistence.Query query4 = em.createQuery(q4);
		@SuppressWarnings("unchecked")
		List<LocationContainerAnnc> result4 = query4.getResultList();
		for (LocationContainerAnnc lca : result4){
			DAOFactory.getLocationContainerAnncDAO().delete(lca, em);
		}
	}
}
