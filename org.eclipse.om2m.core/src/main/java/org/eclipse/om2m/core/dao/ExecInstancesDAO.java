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
import org.eclipse.om2m.commons.resource.ExecInstance;
import org.eclipse.om2m.commons.resource.ExecInstances;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.resource.Refs;

/**
 * Implements CRUD Methods for {@link ExecInstances} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class ExecInstancesDAO extends DAO<ExecInstances> {

	/**
	 * Creates an {@link ExecInstances} collection resource in the DataBase.
	 * @param resource - The {@link ExecInstances} collection resource to create
	 */
	@Override
	public void create(ExecInstances resource, EntityManager em) {
		// NOT ALLOWED
	}

	/**
	 * Retrieves the {@link ExecInstances} collection resource based on its uri with sub-resources references
	 * @param uri - uri of the {@link ExecInstances} collection resource
	 * @return The requested {@link ExecInstances} collection resource otherwise null
	 */
	public ExecInstances find(String uri, EntityManager em) {
		ExecInstances execInstances = new ExecInstances();
		execInstances.setUri(uri);

		execInstances.getExecInstanceCollection().getNamedReference().clear();

		String q = DBUtil.generateLikeRequest(DBEntities.EXEC_INSTANCE_ENTITY, uri);
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<ExecInstance> result = query.getResultList();

		for (ExecInstance execInstance : result) {
			ReferenceToNamedResource reference = new ReferenceToNamedResource();
			reference.setId(execInstance.getId());
			reference.setValue(execInstance.getUri());
			execInstances.getExecInstanceCollection().getNamedReference().add(reference);
		}
		return execInstances;
	}

	/**
	 * Updates an existing {@link ExecInstances} collection resource in the DataBase
	 * @param resource - The {@link ExecInstances} the updated resource
	 */
	@Override
	public void update(ExecInstances resource, EntityManager em) {
		// NOT ALLOWED
	}

	/**
	 * Deletes the {@link ExecInstances} collection resource from the DataBase without validating the transaction
	 * @Param the {@link ExecInstances} collection resource to delete
	 */
	public void delete(ExecInstances resource, EntityManager em) {
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);

		String q = DBUtil.generateLikeRequest(DBEntities.EXEC_INSTANCE_ENTITY, resource.getUri());
		javax.persistence.Query query = em.createQuery(q);
		@SuppressWarnings("unchecked")
		List<ExecInstance> result = query.getResultList();

		for (ExecInstance execInstance : result) {
			execInstance.setSubscriptionsReference(execInstance.getUri() + Refs.SUBSCRIPTIONS_REF);
			DAOFactory.getExecInstanceDAO().delete(execInstance, em);
		}
	}
}
