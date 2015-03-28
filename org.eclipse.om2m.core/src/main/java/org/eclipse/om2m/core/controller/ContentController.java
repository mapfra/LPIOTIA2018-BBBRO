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
package org.eclipse.om2m.core.controller;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.Content;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ContentInstances;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.Refs;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;

/**
 * Implements Create, Retrieve, Update, Delete and Execute methods to handle
 * generic REST request for the {@link ContentInstance} {@link Content}.
 *
 * @author <ul>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         </ul>
 */

public class ContentController extends Controller {

    /**
     * Creates {@link Content} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doCreate (RequestIndication requestIndication) {

        // Response
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_METHOD_NOT_ALLOWED,"Content CREATE method is not allowed")) ;
    }

    /**
     * Retrieves {@link Content} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doRetrieve (RequestIndication requestIndication) {

        ResponseConfirm errorResponse = new ResponseConfirm();
        ContentInstance contentInstance = new ContentInstance();
        // Check URI validity
        if (!requestIndication.getTargetID().contains("contentInstances")) {
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Bad URI: "+requestIndication.getTargetID())) ;
        }

        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        // Check contentInstances existence
        String contentInstancesURI = new String(requestIndication.getTargetID().split(Refs.CONTENTINSTANCES_REF+"/")[0]+Refs.CONTENTINSTANCES_REF);
        ContentInstances contentInstances = DAOFactory.getContentInstancesDAO().find(contentInstancesURI, em);
        if (contentInstances == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,contentInstancesURI+" does not exist")) ;
        }
        // Check AccessRight
        Container container = DAOFactory.getContainerDAO().find(requestIndication.getTargetID().split(Refs.CONTENTINSTANCES_REF)[0], em);
        errorResponse = checkAccessRight(container.getAccessRightID(), requestIndication.getRequestingEntity(), Constants.AR_READ);
        if (errorResponse != null) {
        	em.close();
            return errorResponse;
        }

        String contentInstanceURI = requestIndication.getTargetID().split(Refs.CONTENTINSTANCES_REF+"/")[0]+Refs.CONTENTINSTANCES_REF+"/"+(requestIndication.getTargetID().split(Refs.CONTENTINSTANCES_REF+"/")[1].split("/content")[0]);
        contentInstance = DAOFactory.getContentInstanceDAO().find(contentInstanceURI, em);
        em.close();
        if (contentInstance == null) {
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,contentInstanceURI+" does not exist")) ;
        }
        // Retrieve content
        Content content = new Content();
        content.setValue(contentInstance.getContent());
        // Response
        return new ResponseConfirm(StatusCode.STATUS_OK, new String(content.getValue().getValue()));

    }

    /**
     * Updates {@link Content} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doUpdate (RequestIndication requestIndication) {

        // Response
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_METHOD_NOT_ALLOWED,"Content UPDATE method is not allowed")) ;
    }

    /**
     * Deletes {@link Content} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doDelete (RequestIndication requestIndication) {

        // Response
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_METHOD_NOT_ALLOWED,"Content DELETE method is not allowed")) ;
    }

    /**
     * Executes {@link Content} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doExecute (RequestIndication requestIndication) {

        // Response
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method is not yet Implemented")) ;
    }
}
