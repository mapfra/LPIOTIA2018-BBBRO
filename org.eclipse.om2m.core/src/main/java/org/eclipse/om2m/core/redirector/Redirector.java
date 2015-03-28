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
 ******************************************************************************/
package org.eclipse.om2m.core.redirector;

import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.ReferenceToNamedResource;
import org.eclipse.om2m.commons.resource.Refs;
import org.eclipse.om2m.commons.resource.Scl;
import org.eclipse.om2m.commons.resource.Scls;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.core.comm.RestClient;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;

/**
 * Re-target the REST request to the Distant SCL registered in the {@link Scls} Collection.
 *
 * @author <ul>
 *         <li> Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         <li> Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         </ul>
 */
public class Redirector {

    /**
     * Re-targets a request to a Distant SCL registered in the sclCollection.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm retarget(RequestIndication requestIndication) {
        // Get scls collection from db
    	EntityManager em = DBAccess.createEntityManager();
    	em.getTransaction().begin();
        Scls scls = DAOFactory.getSclsDAO().find(Constants.SCL_ID+Refs.SCLS_REF, em);
        
        boolean found = false;
        String sclId= requestIndication.getTargetID().split("/")[0];
        List<ReferenceToNamedResource> sclCollection = scls.getSclCollection().getNamedReference();
        for (int i=0; i<sclCollection.size(); i++) {
            if (sclCollection.get(i).getId().equalsIgnoreCase(sclId)) {
                found = true;
                break;
            }
        }
        String sclURI = null;
        if (found) {
        	sclURI = Constants.SCL_ID+Refs.SCLS_REF+"/"+sclId;
        }else{
        	if(!Constants.SCL_TYPE.equalsIgnoreCase("NSCL") && sclCollection.size()>0){
        			sclURI = Constants.SCL_ID+Refs.SCLS_REF+"/"+sclCollection.get(0).getId();
        	}else{
        		em.close();
        		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,"Scl "+sclId+" does not exist in scls")) ;
        	}
        }
        // Found remote scl
        Scl scl = DAOFactory.getSclDAO().find(sclURI, em);
        em.close();
        String base = scl.getPocs().getReference().get(0)+"/";
        requestIndication.setBase(base);

        // Retarget the request
        return new RestClient().sendRequest(requestIndication);
    }

}
