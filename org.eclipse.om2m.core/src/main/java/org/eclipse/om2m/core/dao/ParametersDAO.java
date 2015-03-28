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

import java.util.Date;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.MgmtObj;
import org.eclipse.om2m.commons.resource.Parameters;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Subscriptions;
import org.eclipse.om2m.commons.resource.Refs;
import org.eclipse.om2m.commons.utils.DateConverter;

/**
 * Implements CRUD Methods for {@link Parameters} collection resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>  
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         </ul>
 */
public class ParametersDAO extends DAO<Parameters> {

    /**
     * Creates an {@link Parameters} collection resource in the DataBase.
     * @param resource - The {@link Parameters} collection resource to create
     */
	@Override
    public void create(Parameters resource, EntityManager em) {
        //Set subscriptions
        resource.setSubscriptionsReference(resource.getUri()+ Refs.SUBSCRIPTIONS_REF);
        // Store the created resource
        em.persist(resource);

        //Add mgmtObj reference to mgmtObjCollection
        String target = resource.getUri().split("/"+resource.getId())[0];
        String[] parameter = target.split("mgmtObjs/");
        ReferenceToNamedResource reference = new ReferenceToNamedResource();
        reference.setId(resource.getId());
        reference.setValue(resource.getUri());

        if (!parameter[1].contains("/")){
            MgmtObj mgmtObj = DAOFactory.getMgmtObjDAO().find(target, em);
            mgmtObj.getParametersCollection().getNamedReference().add(reference);
            mgmtObj.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());
            DAOFactory.getMgmtObjDAO().update(mgmtObj, em);
        } else {
            Parameters parameters = DAOFactory.getParametersDAO().find(target, em);
            parameters.getParametersCollection().getNamedReference().add(reference);
            parameters.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());
            DAOFactory.getParametersDAO().update(parameters, em);
        }
    }

    /**
     * Retrieves the {@link Parameters} collection resource based on its uri with sub-resources references
     * @param uri - uri of the {@link Parameters} collection resource
     * @return The requested {@link Parameters} collection resource otherwise null
     */
    public Parameters find(String uri, EntityManager em) {
    	return em.find(Parameters.class,uri);
    }

    /**
     * Deletes the {@link Parameters} collection resource from the DataBase without validating the transaction
     * @Param the {@link Parameters} collection resource to delete
     */
    public void delete(Parameters resource, EntityManager em) {
		// Delete subscriptions
		Subscriptions subscriptions = new Subscriptions();
		subscriptions.setUri(resource.getSubscriptionsReference());
		DAOFactory.getSubscriptionsDAO().delete(subscriptions, em);

        // Delete parametersCollection
        for(ReferenceToNamedResource reference : resource.getParametersCollection().getNamedReference()){
            Parameters parameters = DAOFactory.getParametersDAO().find(reference.getValue(), em);
            DAOFactory.getParametersDAO().delete(parameters, em);
        }
        
        // Get the parent Parameters
        String parentUri = resource.getUri().replace("/"+resource.getId(),"");
        Parameters parent = DAOFactory.getParametersDAO().find(parentUri, em);
        
        if (parent != null){
        	// Create the reference to the current Parameters to delete
        	ReferenceToNamedResource reference = new ReferenceToNamedResource();
        	reference.setValue(resource.getUri());
        	ReferenceToNamedResource referenceInParent = null ;
        	
        	// Getting the correct object reference from the parent collection
        	for (ReferenceToNamedResource ref : parent.getParametersCollection().getNamedReference()){
        		if (ref.getValue().equals(resource.getUri())){
        			referenceInParent = ref ; 
        			break ;
        		}
        	}	
        	if (referenceInParent != null){
        		// Remove the reference of the current object from the parent
        		parent.getParametersCollection().getNamedReference().remove(referenceInParent);        		
        	}
        	DAOFactory.getParametersDAO().update(parent, em);
        }
        // Delete the resource
    	em.remove(resource);

    }
}
