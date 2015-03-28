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

import org.eclipse.om2m.commons.resource.M2MPoc;

/**
 * Implements CRUD Methods for {@link M2mPoc} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class M2MPocDAO extends DAO<M2MPoc> {

    /**
     * Retrieves the {@link M2MPoc} resource from the Database based on its uri
     * @param uri - uri of the {@link M2MPoc} resource to retrieve
     * @return The requested {@link M2MPoc} resource otherwise null
     */
    public M2MPoc find(String uri, EntityManager em) {
    	// Retrieve the object from DB
    	return em.find(M2MPoc.class,uri);
    }

    /**
     * Deletes the {@link M2MPoc} resource from the DataBase without validating the transaction
     * @param resource - The {@link M2MPoc} resource to delete
     */
    public void delete(M2MPoc resource, EntityManager em) {
        // Delete the resource
    	em.remove(resource);
    }
}
