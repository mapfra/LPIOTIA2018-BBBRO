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
import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.resource.Containers;
import org.eclipse.om2m.commons.resource.Groups;
import org.eclipse.om2m.commons.resource.NotificationChannels;
import org.eclipse.om2m.commons.resource.Subscriptions;

/**
 * Implements CRUD Methods for {@link Application} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com
 *         ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com
 *         ></li>
 *         </ul>
 */

public class ApplicationDAO extends DAO<Application> {

	/**
	 * Retrieves the {@link Application} resource from the Database based on its
	 * uri
	 * 
	 * @param uri
	 *            - uri of the {@link Application} resource to retrieve
	 * @return The requested {@link Application} resource otherwise null
	 */
	public Application find(String uri, EntityManager em) {
		if (uri == null){
			return null;
		}
		return em.find(Application.class, uri);
	}

	/**
	 * Deletes the {@link Application} resource from the DataBase without
	 * validating the transaction
	 * 
	 * @param resource
	 *            - The {@link Application} resource to delete
	 */
	public void delete(Application resource, EntityManager em) {
		// Delete accessRights
		AccessRights accessRights = new AccessRights();
		accessRights.setUri(resource.getAccessRightsReference());
		DAOFactory.getAccessRightsDAO().delete(accessRights, em);
		// Delete containers
		Containers containers = new Containers();
		containers.setUri(resource.getContainersReference());
		DAOFactory.getContainersDAO().delete(containers, em);
		// Delete groups
		Groups groups = new Groups();
		groups.setUri(resource.getGroupsReference());
		DAOFactory.getGroupsDAO().delete(groups, em);
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);
		// Delete notificationsChannels
		NotificationChannels notificationChannels = new NotificationChannels();
		notificationChannels
				.setUri(resource.getNotificationChannelsReference());
		DAOFactory.getNotificationChannelsDAO()
				.delete(notificationChannels, em);
		// Delete the resource
		em.remove(resource);
	}
}
