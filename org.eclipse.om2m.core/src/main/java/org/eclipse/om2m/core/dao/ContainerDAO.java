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

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstances;
import org.eclipse.om2m.commons.resource.Subscriptions;

/**
 * Implements CRUD Methods for {@link Container} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com
 *         ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com
 *         ></li>
 *         </ul>
 */
public class ContainerDAO extends DAO<Container> {

	/**
	 * Creates an {@link Container} resource in the DataBase and validates the
	 * transaction
	 * 
	 * @param resource
	 *            - The {@link Container} resource to create
	 */
	@Override
	public void create(Container resource, EntityManager em) {
		// Store the created resource
		em.persist(resource);

		// ContentInstances
		ContentInstances contentInstances = new ContentInstances();
		contentInstances.setUri(resource.getContentInstancesReference());
		contentInstances.setCurrentNrOfInstances(0L);
		contentInstances.setCurrentByteSize(0L);
		contentInstances.setCreationTime(resource.getCreationTime());
		contentInstances.setLastModifiedTime(resource.getLastModifiedTime());
		DAOFactory.getContentInstancesDAO().create(contentInstances, em);
	}

	/**
	 * Retrieves the {@link Container} resource from the Database based on its
	 * uri
	 * 
	 * @param uri
	 *            - uri of the {@link Container} resource to retrieve
	 * @return The requested {@link Container} resource otherwise null
	 */
	public Container find(String uri, EntityManager em) {
		Container container;
		container = em.find(Container.class, uri);
		// Return null if the resource is not found
		return container;
	}

	/**
	 * Deletes the {@link Container} resource from the DataBase without
	 * validating the transaction
	 * 
	 * @param resource
	 *            - The {@link Container} resource to delete
	 */
	public void delete(Container resource, EntityManager em) {
		// delete subscriptions
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);
		// delete contentInstances
		DAOFactory.getContentInstancesDAO().delete(
				DAOFactory.getContentInstancesDAO().find(
						resource.getContentInstancesReference(), em), em);
		// Delete the resource
		em.remove(resource);
	}
}
