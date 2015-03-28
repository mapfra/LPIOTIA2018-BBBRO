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
import org.eclipse.om2m.commons.resource.NotificationChannel;
import org.eclipse.om2m.commons.resource.NotificationChannels;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;

/**
 * Implements CRUD Methods for {@link NotificationChannels} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class NotificationChannelsDAO extends DAO<NotificationChannels> {

    /**
     * Creates an {@link NotificationChannels} collection resource in the DataBase.
     * @param resource - The {@link NotificationChannels} collection resource to create
     */
	@Override
    public void create(NotificationChannels resource, EntityManager em) {
		// NOT ALLOWED
    }

    /**
     * Retrieves the {@link NotificationChannels} collection resource based on its uri with sub-resources references
     * @param uri - uri of the {@link NotificationChannels} collection resource
     * @return The requested {@link NotificationChannels} collection resource otherwise null
     */
    public NotificationChannels find(String uri, EntityManager em) {
        NotificationChannels notificationChannels = new NotificationChannels();
        notificationChannels.setUri(uri);
        
        if (notificationChannels != null){
        	// Find NotificationChannel sub-resources and add their references
        	notificationChannels.getNotificationChannelCollection().getNamedReference().clear();

        	String q = DBUtil.generateLikeRequest(DBEntities.NOTIFICATION_CHANNEL_ENTITY, uri);
        	javax.persistence.Query query = em.createQuery(q);
        	@SuppressWarnings("unchecked")
    		List<NotificationChannel> result = query.getResultList();
        	
            for (NotificationChannel notificationChannel : result) {
                ReferenceToNamedResource reference = new ReferenceToNamedResource();
                reference.setId(notificationChannel.getId());
                reference.setValue(notificationChannel.getUri());
                notificationChannels.getNotificationChannelCollection().getNamedReference().add(reference);
            }
        }
        return notificationChannels;
    }

    /**
     * Updates an existing {@link NotificationChannels} collection resource in the DataBase
     * @param resource - The {@link NotificationChannels} the updated resource
     */
    @Override
    public void update(NotificationChannels resource, EntityManager em) {
    	// NOT ALLOWED
    }

    /**
     * Deletes the {@link NotificationChannels} collection resource from the DataBase without validating the transaction
     * @param the {@link NotificationChannels} collection resource to delete
     */
    public void delete(NotificationChannels resource, EntityManager em) {
    	// Delete sub-resources
    	String q = DBUtil.generateLikeRequest(DBEntities.NOTIFICATION_CHANNEL_ENTITY, resource.getUri());
    	javax.persistence.Query query = em.createQuery(q);
    	@SuppressWarnings("unchecked")
		List<NotificationChannel> result = query.getResultList();
    	
        for (NotificationChannel notificationChannel : result) {
            DAOFactory.getNotificationChannelDAO().delete(notificationChannel, em);
        }
    }
}
