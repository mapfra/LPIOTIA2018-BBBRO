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
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.commons.resource.Subscriptions;

/**
 * Implements CRUD Methods for {@link Subscriptions} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class SubscriptionsDAO extends DAO<Subscriptions> {

    /**
     * Creates an {@link Subscriptions} collection resource in the DataBase.
     * @param resource - The {@link Subscriptions} collection resource to create
     */
	@Override
    public void create(Subscriptions resource, EntityManager em) {
		// NOT ALLOWED
    }

    /**
     * Retrieves the {@link Subscriptions} collection resource based on its uri with sub-resources references
     * @param uri - uri of the {@link Subscriptions} collection resource
     * @return The requested {@link Subscriptions} collection resource otherwise null
     */
    public Subscriptions find(String uri, EntityManager em) {
    	Subscriptions subscriptions = new Subscriptions();
    	subscriptions.setUri(uri);
    	subscriptions.getSubscriptionCollection().getNamedReference().clear();

    	// Find subscription sub-resources and add their references
    	String q = DBUtil.generateLikeRequest(DBEntities.SUBSCRIPTION_ENTITY, uri);
    	javax.persistence.Query query = em.createQuery(q);
    	@SuppressWarnings("unchecked")
    	List<Subscription> result = query.getResultList();

    	for(Subscription s : result){
    		ReferenceToNamedResource ref = new ReferenceToNamedResource();
    		ref.setId(s.getId());
    		ref.setValue(s.getUri());
    		subscriptions.getSubscriptionCollection().getNamedReference().add(ref);
    	}

    	return subscriptions;
    }

    /**
     * Updates an existing {@link Subscriptions} collection resource in the DataBase
     * @param resource - The {@link Subscriptions} the updated resource
     */
    @Override
    public void update(Subscriptions resource, EntityManager em) {
    	// NOT ALLOWED
    }

    /**
     * Deletes the {@link Subscriptions} collection resource from the DataBase without validating the transaction
     * @Param the {@link Subscriptions} collection resource to delete
     */
    public void delete(Subscriptions resource, EntityManager em){
    	// Delete subscription sub-resources
    	String q = DBUtil.generateLikeRequest(DBEntities.SUBSCRIPTION_ENTITY, resource.getUri());
    	javax.persistence.Query query = em.createQuery(q);
    	@SuppressWarnings("unchecked")
		List<Subscription> result = query.getResultList();
    	
    	for (Subscription s : result){
    		DAOFactory.getSubscriptionDAO().delete(s,em);
    	}
    }
}
