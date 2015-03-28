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
import org.eclipse.om2m.commons.resource.M2MPoc;
import org.eclipse.om2m.commons.resource.M2MPocs;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;

/**
 * Implements CRUD Methods for {@link M2mPocs} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class M2MPocsDAO extends DAO<M2MPocs> {

    /**
     * Creates an {@link M2MPocs} collection resource in the DataBase.
     * @param resource - The {@link M2MPocs} collection resource to create
     */
	@Override
    public void create(M2MPocs resource, EntityManager em) {
		// NOT ALLOWED
    }

    /**
     * Retrieves the {@link M2MPocs} collection resource based on its uri with sub-resources references
     * @param uri - uri of the {@link M2MPocs} collection resource
     * @return The requested {@link M2MPocs} collection resource otherwise null
     */
    public M2MPocs find(String uri, EntityManager em) {
        M2MPocs m2mPocs = new M2MPocs();
        m2mPocs.setUri(uri);
        m2mPocs.getM2MPocCollection().getNamedReference().clear();

        //Find M2MPoc sub-resources and add their references
        String q = DBUtil.generateLikeRequest(DBEntities.M2MPOC_ENTITY, uri);
        javax.persistence.Query query = em.createQuery(q);
        @SuppressWarnings("unchecked")
        List<M2MPoc> result = query.getResultList();

        for (int i = 0; i < result.size(); i++) {
        	ReferenceToNamedResource reference = new ReferenceToNamedResource();
        	reference.setId(result.get(i).getId());
        	reference.setValue(result.get(i).getUri());
        	m2mPocs.getM2MPocCollection().getNamedReference().add(reference);
        }
        return m2mPocs;
    }

    /**
     * Updates an existing {@link M2MPocs} collection resource in the DataBase
     * @param resource - The {@link M2MPocs} the updated resource
     */
    @Override
    public void update(M2MPocs resource, EntityManager em) {
    	// NOT ALLOWED
    }

    /**
     * Deletes the {@link M2MPocs} collection resource from the DataBase without validating the transaction
     * @Param the {@link M2MPocs} collection resource to delete
     */
    public void delete(M2MPocs resource, EntityManager em) {
        // Delete m2mPocs sub-resources
    	String q = DBUtil.generateLikeRequest(DBEntities.M2MPOC_ENTITY, resource.getUri());
    	javax.persistence.Query query = em.createQuery(q);
    	@SuppressWarnings("unchecked")
		List<M2MPoc> result = query.getResultList();

        for (M2MPoc m2mPoc : result) {
            DAOFactory.getM2MPocDAO().delete(m2mPoc, em);
        }
    }
}
