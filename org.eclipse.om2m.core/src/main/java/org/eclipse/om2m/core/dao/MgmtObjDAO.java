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

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.MgmtObj;
import org.eclipse.om2m.commons.resource.Subscriptions;

/**
 * Implements CRUD Methods for {@link MgmtObj} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class MgmtObjDAO extends DAO<MgmtObj> {

    /**
     * Retrieves the {@link MgmtObj} resource from the Database based on its uri
     * @param uri - uri of the {@link MgmtObj} resource to retrieve
     * @return The requested {@link MgmtObj} resource otherwise null
     */
    public MgmtObj find(String uri, EntityManager em) {
    	if (uri == null){
    		return null;
    	}
    	// Retrieve the object from the DB
        return em.find(MgmtObj.class,uri);
    }

    /**
     * Deletes the {@link MgmtObj} resource from the DataBase without validating the transaction
     * @param resource - The {@link MgmtObj} resource to delete
     */
    public void delete(MgmtObj resource, EntityManager em) {
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);
		
		// Delete the resource
		em.remove(resource);
    }
}
