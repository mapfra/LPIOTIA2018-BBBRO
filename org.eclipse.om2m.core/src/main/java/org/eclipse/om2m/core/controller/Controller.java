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
package org.eclipse.om2m.core.controller;

import java.security.SecureRandom;
import java.util.Date;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.AccessRight;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.PermissionListType;
import org.eclipse.om2m.commons.resource.PermissionType;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.SearchStrings;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.commons.utils.DateConverter;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAO;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;
import org.eclipse.om2m.core.router.Patterns;

/**
 * Controller class contains generic and abstract Create, Retrieve, Update, Delete and Execute
 * methods to handle generic REST request that will be implemented in extended-to classes.
 *
 * @author <ul>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         </ul>
 */
public abstract class Controller {
    /** Logger */
    protected static Log LOGGER = LogFactory.getLog(Controller.class);

    /**
     * Abstract Create method to handle generic REST request.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public abstract ResponseConfirm doCreate (RequestIndication requestIndication);

    /**
     * Abstract Retrieve method to handle generic REST request.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public abstract ResponseConfirm doRetrieve (RequestIndication requestIndication);

    /**
     * Abstract Update method to handle generic REST request.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public abstract ResponseConfirm doUpdate (RequestIndication requestIndication);

    /**
     * Abstract Delete method to handle generic REST request.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public abstract ResponseConfirm doDelete (RequestIndication requestIndication);

    /**
     * Abstract Execute method to handle generic REST request.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public abstract ResponseConfirm doExecute (RequestIndication requestIndication);

    /**
     * Gets the accessRight from the parent. 
     * @param targetId
     * @param em EntityManager to use for DB Transaction
     * @return
     */
    public String getAccessRightId(String targetId, EntityManager em){
    	// Looks for the parent
        Resource parent = DAOFactory.getResourceDAO().find(targetId, em);
        String[] tabID = targetId.split("/");
        boolean stop = !(parent == null) ; 
        while (!stop && tabID.length > 1){
        	String toRemove = tabID[tabID.length-1];
        	targetId = targetId.split("/" + toRemove)[0];
        	@SuppressWarnings("rawtypes")
			DAO dao = Patterns.getDAO(targetId);
        	// If the DAO is null it means the parent is a collection
        	if (dao == null){
        		tabID = targetId.split("/");        		
        	} else {
        		parent = DAOFactory.getResourceDAO().find(targetId, em);
        		stop = true ;
        	}
        }
        return parent.getAccessRightID();
    }


    /**
     * Checks the Access Right based on accessRightID (Permission)
     * @param accessRightID - Id of the accessRight
     * @param requestingEntity - requesting entity used by the requester
     * @param method - requested method
     * @return error with a specific status code if the requesting Entity or the method does not exist otherwise null
     */
    public ResponseConfirm checkAccessRight(String accessRightID, String requestingEntity, String method) {
        boolean holderFound = false;
        boolean flagFound = false;
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        AccessRight accessRightFound = DAOFactory.getAccessRightDAO().find(accessRightID, em);
        em.close();
        // Check Resource accessRight existence not found
        if (accessRightFound == null) {
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,"AccessRight for this resource is not found"));
        }
        // Check permissions if the accessRight is found
        if (accessRightFound != null) {
            // Get Permissions List
            PermissionListType permissions = accessRightFound.getPermissions();
            for (int j=0; j<permissions.getPermission().size(); j++) {
                holderFound = false;
                PermissionType permission = permissions.getPermission().get(j);
                // Specific Holder
                String holder;
                String flag;
                // Holders in a permission
                for (int i=0; i<permission.getPermissionHolders().getHolderRefs().getHolderRef().size(); i++) {
                    holder = permission.getPermissionHolders().getHolderRefs().getHolderRef().get(i);
                    if (holder.equalsIgnoreCase(requestingEntity)) {
                        holderFound = true;
                        break;
                    }
                }
                if (holderFound) {
                    for (int k=0; k<permission.getPermissionFlags().getFlag().size(); k++) {
                        flag = permission.getPermissionFlags().getFlag().get(k).toString();
                        if (flag.equalsIgnoreCase(method)) {
                            flagFound = true;
                            break;
                        }
                    }
                    // The holder exists just in one permission of all permissions
                    break;
                }
            }
        }
        // returns STATUS_NOT_FOUND error if the holder is not found
        if (!holderFound) {
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,"Requesting Entity ["+requestingEntity+"] does not exist in permissions"));
        }
        // returns STATUS_PERMISSION_DENIED error if the holder is found but the flag is not.
        if (!flagFound) {
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_PERMISSION_DENIED,method+" Method does not exist in permissions"));
        }
        return null;
    }

    /**
     * Checks AccessRight based on selfPermission
     * @param selfPermissions - selfPermissions attribute of the accessRight
     * @param Client requesting entity
     * @param method - requested method
     * @return error with a specific status code if the requesting Entity or the method does not exist otherwise null
     */
    public ResponseConfirm checkSelfPermissions(PermissionListType selfPermissions, String requestingEntity, String method) {
        boolean holderFound = false;
        boolean flagFound = false;
        for (int j=0; j<selfPermissions.getPermission().size(); j++) {
            holderFound = false;
            PermissionType selfPermission = selfPermissions.getPermission().get(j);
            // Specific Holder
            String holder;
            String flag;
            // Holders in a permission
            for (int i=0; i<selfPermission.getPermissionHolders().getHolderRefs().getHolderRef().size(); i++) {
                holder = selfPermission.getPermissionHolders().getHolderRefs().getHolderRef().get(i);
                if (holder.equalsIgnoreCase(requestingEntity)) {
                    holderFound = true;
                    break;
                }
            }
            if (holderFound) {
                for (int k=0; k<selfPermission.getPermissionFlags().getFlag().size(); k++) {
                    flag = selfPermission.getPermissionFlags().getFlag().get(k).toString();
                    if (flag.equalsIgnoreCase(method)) {
                        flagFound = true;
                        break;
                    }
                }
                // The holder exists just in one permission of all permissions
                break;
            }
        }
        // returns STATUS_NOT_FOUND error if the holder is not found
        if (!holderFound) {
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,"Requesting Entity ["+requestingEntity+"] does not exist in permissions")) ;
        }
        // returns STATUS_PERMISSION_DENIED error if the holder is found but the flag is not.
        if (!flagFound) {
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_PERMISSION_DENIED,method+" Method does not exist in permissions")) ;
        }
        return null;
    }

    /**
     * Checks if the expirationTime is out of date or not
     * @param expirationTime - expiration time present in the request representation
     * @return false if the expirationTime attribute is out of date otherwise true
     */
    public boolean checkExpirationTime(String expirationTime) {
//        DateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZ");
//        Date expDate;
//        try {
//            expDate = df.parse(expirationTime);
//            if (expDate.compareTo(new Date()) > 0) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (ParseException e) {
//            LOGGER.error("Invalid XMLGregorianCalendar Format", e);
//            return false;
//        }
        return true;



//        GregorianCalendar cal = new GregorianCalendar();
//        cal.setTime(exp);
//        XMLGregorianCalendar xmlDate2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH), dob.getHours(),dob.getMinutes(),dob.getSeconds(),DatatypeConstants.FIELD_UNDEFINED, cal.getTimeZone().LONG).normalize();
//        XMLGregorianCalendar xmlDate3 = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),dob.getHours(),dob.getMinutes(),dob.getSeconds(),DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
//        System.out.println(xmlDate2);
//        System.out.println(xmlDate3);
//
//        XMLGregorianCalendar exp = expirationTime
//        if (expirationTime != null && expirationTime.toGregorianCalendar().compareTo(now.toGregorianCalendar()) > 0) {
//            isNotExpired = true;
//            return isNotExpired;
//        } else {
//            isNotExpired = false;
//            return isNotExpired;
//        }
    }

    /**
     * Generates a new ExpirationTime by adding seconds to the current Time
     * @param addedSeconds - seconds to add to the current time
     * @return New expirationTime value of the resource
     */
    public String getNewExpirationTime(long addedSeconds) {
        long addedMilSeconds = addedSeconds * 1000;
        Date newDate = new Date((new Date()).getTime() + addedMilSeconds);
        return DateConverter.toXMLGregorianCalendar(newDate).toString();
    }

    /**
     * Generates a new DelayTolerance by adding seconds to the current Time
     * @param addedSeconds - seconds to add to the current time
     * @return New delayTolerance of the resource
     */
    public String getNewDelayTolerance(long addedSeconds) {
        long addedMilSeconds = addedSeconds * 1000;
        Date newDate = new Date((new Date()).getTime() + addedMilSeconds);
        return DateConverter.toXMLGregorianCalendar(newDate).toString();
    }

    /**
     * Generates an aleatory ID based on SecureRandom library
     * @param prefix - prefix of the resource ID
     * @param postfix - postfix of the resource ID
     * @return generated resource ID
     */
    public static String generateId(String prefix, String postfix) {
        SecureRandom secureRandom = new SecureRandom();
        return prefix+String.valueOf(secureRandom.nextInt(999999999))+postfix;
    }

    /**
     * Generates default resource {@link SearchStrings}
     * @param Resourcetype - The resource Type
     * @param resourceId - The resource ID
     * @return generated {@link SearchStrings}
     */
    public static SearchStrings generateSearchStrings (String Resourcetype, String resourceId) {
        SearchStrings searchStrings = new SearchStrings();
        searchStrings.getSearchString().add(Constants.SEARCH_STRING_RES_TYPE+Resourcetype);
        searchStrings.getSearchString().add(Constants.SEARCH_STRING_RES_ID+resourceId);
        return searchStrings;
    }
}
