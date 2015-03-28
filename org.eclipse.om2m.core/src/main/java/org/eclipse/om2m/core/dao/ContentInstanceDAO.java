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

import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.DBEntities;

/**
 * Implements CRUD Methods for {@link ContentInstance} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class ContentInstanceDAO extends DAO<ContentInstance> {

    /**
     * Retrieves the {@link ContentInstance} resource from the Database based on its uri
     * @param uri - uri of the {@link ContentInstance} resource to retrieve
     * @return The requested {@link ContentInstance} resource otherwise null
     */
    public ContentInstance find(String uri, EntityManager em) {
        // Create the query based on the uri constraint
        ContentInstance contentInstance = null ;
        if("latest".equals(uri.split("contentInstances/")[1])){
        	String uriToLoad = uri.replaceAll("/latest/*", "");
        	String q = DBUtil.generateLikeRequestOrderByCreationTime(DBEntities.CONTENT_INSTANCE_ENTITY, uriToLoad);
        	javax.persistence.Query queryJPA = em.createQuery(q);
        	@SuppressWarnings("unchecked")
    		List<ContentInstance> result = queryJPA.getResultList();

            if (!result.isEmpty()) {
            	return result.get(result.size()-1);
            }

        }else if ("oldest".equals(uri.split("contentInstances/")[1])){
        	String uriToLoad = uri.replaceAll("/oldest/*", "");
        	String q = DBUtil.generateLikeRequestOrderByCreationTime(DBEntities.CONTENT_INSTANCE_ENTITY, uriToLoad);
        	javax.persistence.Query queryJPA = em.createQuery(q);
        	@SuppressWarnings("unchecked")
    		List<ContentInstance> result = queryJPA.getResultList();

            if (!result.isEmpty()) {
            	return result.get(0);
            }
        }else {
        	contentInstance = em.find(ContentInstance.class, uri);
        }
        // Return null if the resource is not found
        return contentInstance;
    }

    /**
     * Deletes the {@link ContentInstance} resource from the DataBase without validating the transaction
     * @param resource - The {@link ContentInstance} resource to delete
     */
    public void delete(ContentInstance resource, EntityManager em){
        // Delete the resource
        em.remove(resource);
    }
}
