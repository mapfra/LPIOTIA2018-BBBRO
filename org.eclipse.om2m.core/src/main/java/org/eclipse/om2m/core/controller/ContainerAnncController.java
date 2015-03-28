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

import org.eclipse.om2m.commons.resource.ContainerAnnc;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.commons.utils.XmlMapper;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;
import org.eclipse.om2m.core.notifier.Notifier;

/**
 * Implements Create, Retrieve, Update, Delete and Execute methods to handle
 * generic REST request for {@link ContainerAnnc} resource.
 *
 * @author <ul>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         </ul>
 */

public class ContainerAnncController extends Controller {

    /**
     * Creates {@link ContainerAnnc} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doCreate (RequestIndication requestIndication)  {

        // Link:            (createReq M) (response M)
        // accessRightID:   (createReq O) (response O)
        // searchStrings:   (createReq M) (response M)
        // expirationTime:  (createReq O) (response M*)
        // Id:              (createReq O) (response M*)

        ResponseConfirm errorResponse = new ResponseConfirm();
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();        

        String accessRightID = getAccessRightId(requestIndication.getTargetID(), em);
        // CheckResourceParentExistence
        if (accessRightID == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getTargetID()+" does not exist")) ;
        }
        // Check AccessRight
        errorResponse = checkAccessRight(accessRightID, requestIndication.getRequestingEntity(), Constants.AR_CREATE);
        if (errorResponse != null) {
        	em.close();
            return errorResponse;
        }
        // Check Resource Representation
        if (requestIndication.getRepresentation() == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Resource Representation is EMPTY")) ;
        }
        // Checks on attributes
        ContainerAnnc containerAnnc = null ;  
        try{
        	containerAnnc = (ContainerAnnc) XmlMapper.getInstance().xmlToObject(requestIndication.getRepresentation());
        } catch (ClassCastException e){
        	em.close();
        	LOGGER.debug("ClassCastException : Incorrect resource type in JAXB unmarshalling.",e);
        	return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, "Incorrect resource type"));
        }
        if (containerAnnc == null){
        	em.close();
        	return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, "Incorrect resource representation syntax")) ;
        }
        // Check the Id uniqueness
        if (containerAnnc.getId() != null && DAOFactory.getContainerAnncDAO().find(requestIndication.getTargetID()+"/"+containerAnnc.getId(), em) != null) {
        	em.close();
        	return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_CONFLICT,"ContainerAnncId Conflit")) ;
        }
        // Generate the id if it does not exist
        if (containerAnnc.getId() == null || containerAnnc.getId().isEmpty()) {
            containerAnnc.setId(generateId("CONT_","Annc"));
        }
        // SearchStrings Attribute is Mandatory
        if (containerAnnc.getSearchStrings() == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"searchStrings attribute CREATE is Mandatory")) ;
        }
        // Link is Mandatory
        if (containerAnnc.getLink() == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Link attribute CREATE is Mandatory")) ;
        }
        // Check ExpirationTime
        if (containerAnnc.getExpirationTime() != null && !checkExpirationTime(containerAnnc.getExpirationTime())) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Expiration Time CREATE is Out of Date")) ;
        }

        // Storage
        // Set URI
        containerAnnc.setUri(requestIndication.getTargetID()+"/"+containerAnnc.getId());
        // Set Expiration Time if it is null
        if (containerAnnc.getExpirationTime() == null) {
            //infinity expiration Time
            containerAnnc.setExpirationTime(getNewExpirationTime(Constants.EXPIRATION_TIME));
        }
        // Set accessRightID if it exists
        if (DAOFactory.getAccessRightDAO().find(containerAnnc.getAccessRightID(), em) == null) {
            containerAnnc.setAccessRightID(accessRightID);
        }

        //Notify the subscribers
        Notifier.notify(StatusCode.STATUS_CREATED, containerAnnc);

        //Store containerAnnc
        DAOFactory.getContainerAnncDAO().create(containerAnnc, em);
        
        em.getTransaction().commit();
        em.close();
        // Response
        return new ResponseConfirm(StatusCode.STATUS_CREATED);
    }

    /**
     * Retrieves {@link ContainerAnnc} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doRetrieve (RequestIndication requestIndication) {

        // Link:            (response M)
        // accessRightID:   (response O)
        // searchStrings:   (response M)
        // expirationTime:  (response M*)
        // Id:              (response M*)

        ResponseConfirm errorResponse = new ResponseConfirm();
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        ContainerAnnc containerAnnc = DAOFactory.getContainerAnncDAO().find(requestIndication.getTargetID(), em);
        em.close();
        // Check if the resource exists in DataBase or Not
        if (containerAnnc == null) {
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getTargetID()+" does not exist in DataBase")) ;
        }
        // Check accessRight
        errorResponse = checkAccessRight(containerAnnc.getAccessRightID(), requestIndication.getRequestingEntity(), Constants.AR_READ);
        if (errorResponse != null) {
            return errorResponse;
        }
        // Response
        return new ResponseConfirm(StatusCode.STATUS_OK, containerAnnc);

    }

    /**
     * Updates {@link ContainerAnnc} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doUpdate (RequestIndication requestIndication) {

        // Link:            (updateReq NP) (response M)
        // accessRightID:   (updateReq O)  (response O)
        // searchStrings:   (updateReq M)  (response M)
        // expirationTime:  (updateReq O)  (response M*)
        // Id:              (updateReq NP) (response M*)

        ResponseConfirm errorResponse = new ResponseConfirm();
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        ContainerAnnc containerAnnc = DAOFactory.getContainerAnncDAO().find(requestIndication.getTargetID(), em);

        // Check resource Existence
        if (containerAnnc == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getTargetID()+" does not exist in DataBase")) ;
        }
        // Check AccessRight
        errorResponse = checkAccessRight(containerAnnc.getAccessRightID(), requestIndication.getRequestingEntity(), Constants.AR_WRITE);
        if (errorResponse != null) {
        	em.close();
            return errorResponse;
        }
        // Check Resource Representation
        if (requestIndication.getRepresentation() == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Resource Representation is EMPTY")) ;
        }
        // Checks on attributes
        ContainerAnnc containerAnncNew = null ;  
        try{
        	containerAnncNew = (ContainerAnnc) XmlMapper.getInstance().xmlToObject(requestIndication.getRepresentation());
        } catch (ClassCastException e){
        	em.close();
        	LOGGER.debug("ClassCastException : Incorrect resource type in JAXB unmarshalling.",e);
        	return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, "Incorrect resource type"));
        }
        if (containerAnncNew == null){
        	em.close();
        	return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, "Incorrect resource representation syntax")) ;
        }
        // The Update of the ConAnncId is NP
        if (containerAnncNew.getId() != null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"ContainerAnncId UPDATE is Not Permitted")) ;
        }
        // Check ExpirationTime
        if (containerAnncNew.getExpirationTime() != null && !checkExpirationTime(containerAnncNew.getExpirationTime())) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Expiration Time UPDATE is Out of Date")) ;
        }
        // Link Must be NP
        if (containerAnncNew.getLink() != null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Link attribute UPDATE is Mandatory")) ;
        }
        // SearchStrings Attribute is mandatory
        if (containerAnncNew.getSearchStrings() == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"searchStrings attribute UPDATE is Mandatory")) ;
        }
        // Storage
        // Set Expiration Time
        if (containerAnncNew.getExpirationTime() != null) {
            containerAnnc.setExpirationTime(containerAnncNew.getExpirationTime());
        }
        // Set AccessRightID from the Parent if it's null or nonexistent
        if (DAOFactory.getAccessRightDAO().find(containerAnncNew.getAccessRightID(), em) != null) {
            containerAnnc.setAccessRightID(containerAnncNew.getAccessRightID());
        }
        // Set searchStrings
        containerAnnc.setSearchStrings(containerAnncNew.getSearchStrings());

        // Notify the subscribers
        Notifier.notify(StatusCode.STATUS_OK, containerAnnc);

        // Store
        DAOFactory.getContainerAnncDAO().update(containerAnnc, em);
        
        em.getTransaction().commit();
        em.close();
        // Response
        return new ResponseConfirm(StatusCode.STATUS_OK, containerAnnc);
    }

    /**
     * Deletes {@link ContainerAnnc} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doDelete (RequestIndication requestIndication) {

        ResponseConfirm errorResponse = new ResponseConfirm();
        EntityManager em = DBAccess.createEntityManager();
        em.getTransaction().begin();
        ContainerAnnc containerAnnc = DAOFactory.getContainerAnncDAO().find(requestIndication.getTargetID(), em);

        // Check Resource Existence
        if (containerAnnc == null) {
        	em.close();
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getTargetID()+" does not exist")) ;
        }
        // Check AccessRight
        errorResponse = checkAccessRight(containerAnnc.getAccessRightID(), requestIndication.getRequestingEntity(), Constants.AR_DELETE);
        if (errorResponse != null) {
        	em.close();
            return errorResponse;
        }

        // Notify the subscribers
        Notifier.notify(StatusCode.STATUS_DELETED, containerAnnc);

        // Delete
        DAOFactory.getContainerAnncDAO().delete(containerAnnc, em);
        // Validate the transaction
        em.getTransaction().commit();
        em.close();
        // Response
        return new ResponseConfirm(StatusCode.STATUS_OK);

    }

    /**
     * Executes {@link ContainerAnnc} resource.
     * @param requestIndication - The generic request to handle.
     * @return The generic returned response.
     */
    public ResponseConfirm doExecute (RequestIndication requestIndication) {

        // Response
        return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method is not yet Implemented")) ;
    }
}
