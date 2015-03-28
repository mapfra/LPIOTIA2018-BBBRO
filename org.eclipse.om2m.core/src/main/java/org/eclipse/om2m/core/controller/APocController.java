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

import org.eclipse.om2m.commons.resource.Application;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.comm.RestClient;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;

/** allows the choice of the controller based on the aPoC of the application**/
public class APocController extends Controller{

    public ResponseConfirm doCreate (RequestIndication requestIndication) {

        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        Application application= DAOFactory.getApplicationDAO().find(applicationUri, em);
        em.close();
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            InterworkingProxyController IPUController= new InterworkingProxyController();
            return IPUController.doCreate(requestIndication);
        }
    }


    public ResponseConfirm doRetrieve (RequestIndication requestIndication) {


        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;

        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        Application application= DAOFactory.getApplicationDAO().find(applicationUri, em);
        em.close();
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            LOGGER.info(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            Controller IPUController= new InterworkingProxyController();
            return IPUController.doRetrieve(requestIndication);
        }
    }
    
    
    public ResponseConfirm doUpdate (RequestIndication requestIndication) {

        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        Application application= DAOFactory.getApplicationDAO().find(applicationUri, em);
        em.close();
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            Controller IPUController= new InterworkingProxyController();
            return IPUController.doUpdate(requestIndication);
        }
    }



    public ResponseConfirm doDelete (RequestIndication requestIndication) {
        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        Application application= DAOFactory.getApplicationDAO().find(applicationUri, em);
        em.close();
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            Controller IPUController= new InterworkingProxyController();
            return IPUController.doDelete(requestIndication);
        }
    }

    public ResponseConfirm doExecute (RequestIndication requestIndication) {

        String sclId = requestIndication.getTargetID().split("/")[0];
        String applicationId = requestIndication.getTargetID().split("/")[2];
        String applicationUri = sclId+"/applications/"+applicationId;
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        Application application= DAOFactory.getApplicationDAO().find(applicationUri, em);
        em.close();
        String aPoCPath = application.getAPoCPaths().getAPoCPath().get(0).getPath();
        if (aPoCPath.matches(".*://.*")){
            String targetID = requestIndication.getTargetID().split(applicationId)[1];
            requestIndication.setBase(aPoCPath);
            requestIndication.setTargetID(targetID);
            return new RestClient().sendRequest(requestIndication);
        }else{
            Controller IPUController= new InterworkingProxyController();
            return IPUController.doExecute(requestIndication);
        }

    }

}
