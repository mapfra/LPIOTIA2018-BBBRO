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
import javax.persistence.Query;

import org.eclipse.om2m.commons.resource.DBEntities;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.Resources;

/**
 * Implements CRUD Methods for {@link Resources} persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.Feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class ResourcesDAO extends DAO<Resources>{

    @Override
    public void create(Resources resource, EntityManager em) {
    	// NOT ALLOWED
    }

    /**
     * Retrieves resources based on its uri
     * @param uri - uri of the {@link Resource} or beginning with
     * @return The requested {@link Resources} otherwise null
     */
    public Resources find(String uri, EntityManager em) {
    	Resources resources = new Resources();
    	long begFindAll = System.currentTimeMillis();
    	for (String entityName : DBEntities.ENTITY_LIST){
    		String query = DBUtil.generateLikeRequest(entityName, uri);
    		Query q = em.createQuery(query);
    		@SuppressWarnings("unchecked")
			List<Resource> result = q.getResultList() ;
    		resources.getResources().addAll(result);
    	}
    	long endFindAll = System.currentTimeMillis();
    	LOGGER.debug("***************** Time FindAll JPA: " + (endFindAll-begFindAll));
    	
        return resources;
    }

    @Override
    public void update(Resources resource, EntityManager em) {
    	// NOT ALLOWED
    }

    @Override
    public void delete(Resources resource, EntityManager em) {
    	// NOT ALLOWED
    }
}
