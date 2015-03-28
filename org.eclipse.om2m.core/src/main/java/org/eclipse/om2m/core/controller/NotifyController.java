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
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.Refs;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.comm.RestClient;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;
import org.eclipse.om2m.core.redirector.Redirector;

public class NotifyController extends Controller{

    @Override
    public ResponseConfirm doCreate(RequestIndication requestIndication) {
        String sclId = requestIndication.getTargetID().split("/")[0];
        if(Constants.SCL_ID.equals(sclId)){
                String appId = requestIndication.getTargetID().split("/")[0];
                EntityManager em = DBAccess.createEntityManager();
                em.getTransaction().begin();
                Application application = DAOFactory.getApplicationDAO().find(Constants.SCL_ID+Refs.APPLICATIONS_REF+"/"+appId, em);
                em.close();
                requestIndication.setBase(application.getAPoC());
                return new RestClient().sendRequest(requestIndication);
        }else{
            return new Redirector().retarget(requestIndication);
        }
    }

    @Override
    public ResponseConfirm doRetrieve(RequestIndication requestIndication) {
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_METHOD_NOT_ALLOWED,requestIndication.getMethod()+" Method is not allowed"));

    }

    @Override
    public ResponseConfirm doUpdate(RequestIndication requestIndication) {
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_METHOD_NOT_ALLOWED,requestIndication.getMethod()+" Method is not allowed"));

    }

    @Override
    public ResponseConfirm doDelete(RequestIndication requestIndication) {
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_METHOD_NOT_ALLOWED,requestIndication.getMethod()+" Method is not allowed"));
    }

    @Override
    public ResponseConfirm doExecute(RequestIndication requestIndication) {
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_METHOD_NOT_ALLOWED,requestIndication.getMethod()+" Method is not allowed"));
    }

}
