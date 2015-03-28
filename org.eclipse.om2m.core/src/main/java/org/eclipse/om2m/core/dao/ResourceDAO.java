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

import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.core.router.Patterns;

/**
 * Implements CRUD Methods for resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class ResourceDAO extends DAO<Resource>{

    @Override
    public void create(Resource resource, EntityManager em) {
    	// NOT ALLOWED
    }

    /**
     * Retrieves the {@link Resource} based on its uri
     * @param uri - uri of the {@link Resource}
     * @return The requested {@link Resource} otherwise null
     */
    public Resource find(String uri, EntityManager em) {
    	@SuppressWarnings("rawtypes")
		DAO dao = Patterns.getDAO(uri);
    	if (dao == null){
    		return null ; 
    	}
    	return (Resource) dao.find(uri, em);
    }

    @Override
    public void update(Resource resource, EntityManager em) {
    	// NOT ALLOWED
    }

    @Override
    public void delete(Resource resource, EntityManager em) {
    	// NOT ALLOWED
    }
}
