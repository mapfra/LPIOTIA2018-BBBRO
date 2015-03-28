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

import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ContentInstances;
import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.Subscriptions;

/**
 * Implements CRUD Methods for {@link ContentInstances} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class ContentInstancesDAO extends DAO<ContentInstances> {

    /**
     * Retrieves the {@link ContentInstances} collection resource based on its uri with sub-resources references
     * @param uri - uri of the {@link ContentInstances} collection resource
     * @return The requested {@link ContentInstances} collection resource otherwise null
     */
    public ContentInstances find(String uri, EntityManager em){
    	ContentInstances contentInstances = em.find(ContentInstances.class, uri) ; 
        
    	if(contentInstances != null){
        	contentInstances.getContentInstanceCollection().getContentInstance().clear();

        	String q = DBUtil.generateLikeRequestOrderByCreationTime(DBEntities.CONTENT_INSTANCE_ENTITY, uri);
        	javax.persistence.Query query = em.createQuery(q);
        	@SuppressWarnings("unchecked")
			List<ContentInstance> result = query.getResultList();
        	
        	for (ContentInstance contentInstance : result){
        		contentInstances.getContentInstanceCollection().getContentInstance().add(contentInstance);
        	}
        }
        return contentInstances;
        
    }

    /**
     * Deletes the {@link ContentInstances} collection resource from the DataBase without validating the transaction
     * @Param the {@link ContentInstances} collection resource to delete
     */
    public void delete(ContentInstances resource, EntityManager em){
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);
        // Delete contentInstance sub-resources
    	String q = DBUtil.generateLikeRequest(DBEntities.CONTENT_INSTANCE_ENTITY, resource.getUri());
    	javax.persistence.Query query = em.createQuery(q);
    	@SuppressWarnings("unchecked")
		List<ContentInstance> result = query.getResultList();

        for (ContentInstance contentInstance : result) {
            DAOFactory.getContentInstanceDAO().delete(contentInstance, em);
        }

        // Delete the resource
        em.remove(resource);
    }
}
