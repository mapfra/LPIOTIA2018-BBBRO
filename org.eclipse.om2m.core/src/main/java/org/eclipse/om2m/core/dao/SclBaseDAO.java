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

import org.eclipse.om2m.commons.resource.AccessRights;
import org.eclipse.om2m.commons.resource.Applications;
import org.eclipse.om2m.commons.resource.Containers;
import org.eclipse.om2m.commons.resource.Groups;
import org.eclipse.om2m.commons.resource.SclBase;
import org.eclipse.om2m.commons.resource.Scls;
import org.eclipse.om2m.commons.resource.Subscriptions;

/**
 * Implements CRUD Methods for {@link SclBase} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com
 *         ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com
 *         ></li>
 *         </ul>
 */
public class SclBaseDAO extends DAO<SclBase> {

	/**
	 * Retrieves the {@link SclBase} resource from the Database based on its uri
	 * 
	 * @param uri
	 *            - uri of the {@link SclBase} resource to retrieve
	 * @return The requested {@link SclBase} resource otherwise null
	 */
	public SclBase find(String uri, EntityManager em) {
		// Retrieve the resource from the DB
		return em.find(SclBase.class, uri);
	}

	/**
	 * Deletes the {@link SclBase} resource from the DataBase without validating
	 * the transaction
	 * 
	 * @param resource
	 *            - The {@link SclBase} resource to delete
	 */
	public void delete(SclBase resource, EntityManager em) {		
		// Delete scls
		Scls scls = new Scls();
		scls.setUri(resource.getSclsReference());
		DAOFactory.getSclsDAO().delete(scls, em);
		// Delete applications
		Applications applications = new Applications();
		applications.setUri(resource.getApplicationsReference());
		DAOFactory.getApplicationsDAO().delete(applications, em);
		// Delete accessRights
		AccessRights accessRights = new AccessRights();
		accessRights.setUri(resource.getAccessRightsReference());
		DAOFactory.getAccessRightsDAO().delete(accessRights, em);
		// Delete groups
		Groups groups = new Groups();
		groups.setUri(resource.getGroupsReference());
		DAOFactory.getGroupsDAO().delete(groups, em);
		// Delete containers
		Containers containers = new Containers();
		containers.setUri(resource.getContainersReference());
		DAOFactory.getContainersDAO().delete(containers, em);
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);
		 
		// Delete the resource
		em.remove(resource);
	}
}
