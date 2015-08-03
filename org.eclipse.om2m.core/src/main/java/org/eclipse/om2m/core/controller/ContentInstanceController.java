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

import java.util.Date;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.Base64Binary;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ContentInstances;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.Refs;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.commons.utils.DateConverter;
import org.eclipse.om2m.commons.utils.XmlMapper;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;
import org.eclipse.om2m.core.notifier.Notifier;
import org.eclipse.om2m.core.router.Router;

/**
 * Implements Create, Retrieve, Update, Delete and Execute methods to handle
 * generic REST request for {@link ContentInstance} resource.
 *
 * @author <ul>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         </ul>
 */

public class ContentInstanceController extends Controller {
	public static Object lock = new Object();
	/**
	 * Creates {@link ContentInstance} resource.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doCreate (RequestIndication requestIndication) {

		// id:                  (createReq O)  (response M*)
		// href:                (createReq NP) (response O)
		// contentTypes:        (createReq O)  (response O)
		// contentSize:         (createReq NP) (response M)
		// creationTime:        (createReq NP) (response M)
		// lastModifiedTime:    (createReq NP) (response M)
		// delayTolerance:      (createReq O)  (response O)
		// content:             (createReq M)  (response M)
		
		EntityManager em = DBAccess.createEntityManager();
		em.getTransaction().begin();
		ResponseConfirm errorResponse = new ResponseConfirm();
		ContentInstance contentInstance = new ContentInstance();
		ContentInstances contentInstances = DAOFactory.getContentInstancesDAO().find(requestIndication.getTargetID(), em);

		// Check Resource Parent Existence
		if (contentInstances == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getTargetID()+" does not exist")) ;
		}
		// Check AccessRight
		Container container = DAOFactory.getContainerDAO().find(requestIndication.getTargetID().split(Refs.CONTENTINSTANCES_REF)[0], em);
		errorResponse = checkAccessRight(container.getAccessRightID(), requestIndication.getRequestingEntity(), Constants.AR_CREATE);
		if (errorResponse != null) {
			em.close();
			return errorResponse;
		}
		// Check Resource Representation
		if (requestIndication.getRepresentation() == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Resource Representation is EMPTY")) ;
		}
		// Create a Content if its a direct content Creation with contentInstance
		try{
			contentInstance = (ContentInstance) XmlMapper.getInstance().xmlToObject(requestIndication.getRepresentation());
		} catch (ClassCastException e){
			LOGGER.debug("ClassCastException : Incorrect resource type in JAXB unmarshalling.",e);
			contentInstance = null ; 
		}
		if (contentInstance == null){
			contentInstance = new ContentInstance() ; 
			Base64Binary content = new Base64Binary();
			content.setContentType("application/xml");
			content.setValue(requestIndication.getRepresentation().getBytes());
			contentInstance.setContent(content);
		}
		
		//Check on attributes
		// href Must be NP
		if (contentInstance.getHref() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST," Href CREATE is Not Permitted")) ;
		}
		// ContentSize Must be NP
		if (contentInstance.getContentSize() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST," ContentSize CREATE is Not Permitted")) ;
		}
		// CreationTime Must be NP
		if (contentInstance.getCreationTime() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST," CreationTime CREATE is Not Permitted")) ;
		}
		// LastModifiedTime Must be NP
		if (contentInstance.getLastModifiedTime() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST," LastModifiedTime CREATE is Not Permitted")) ;
		}
		// Content is Mandatory
		if (contentInstance.getContent() == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST," Content CREATE is Mandatory")) ;
		}
		// Check ID Conformity
		if (contentInstance.getId() != null && !contentInstance.getId().matches(Constants.ID_REGEXPR)){
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Id should match the following regexpr: " + Constants.ID_REGEXPR));
		}
		// Check uniqueness and Set id if it is not available
		if (contentInstance.getId() == null || contentInstance.getId().isEmpty()
				|| DAOFactory.getContentInstanceDAO().find(requestIndication.getTargetID()+"/"+contentInstance.getId(), em) != null) {
			contentInstance.setId(generateId("CI_",""));
		}
		// Set URI
		contentInstance.setUri(requestIndication.getTargetID()+ "/" +contentInstance.getId());
		// Set href
		contentInstance.setHref(contentInstance.getUri());
		// Set contentSize
		contentInstance.setContentSize((long) contentInstance.getContent().getValue().length);
		// Set delayTolerance if it's null
		if (contentInstance.getDelayTolerance() == null) {
			contentInstance.setDelayTolerance(getNewDelayTolerance(12000));
		}

		// Set CreationTime
		contentInstance.setCreationTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());
		// Set LastModifiedTime
		contentInstance.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(new Date()).toString());

		// Notify the subscribers
		Notifier.notify(StatusCode.STATUS_CREATED, contentInstance);

		//Store contentInstance
		DAOFactory.getContentInstanceDAO().create(contentInstance, em);
		contentInstances.setCurrentNrOfInstances(contentInstances.getCurrentNrOfInstances() + 1);
		contentInstances.setCurrentByteSize(contentInstances.getCurrentByteSize() + contentInstance.getContentSize());
		DAOFactory.getContentInstancesDAO().update(contentInstances, em);
		// delete the oldest contentInstance if the CurrentNrOfInstances reaches MaxNrOfInstances
		if (contentInstances.getCurrentNrOfInstances() > container.getMaxNrOfInstances()) {
			final String targetID = requestIndication.getTargetID();
			
			new Thread(){
				public void run(){
					Router.readWriteLock.readLock().lock();
					EntityManager em = DBAccess.createEntityManager();
					em.getTransaction().begin();
					ContentInstance contentInstanceOldest = DAOFactory.getContentInstanceDAO().find(targetID+"/oldest", em);
					//Delete the oldest contentInstance
					DAOFactory.getContentInstanceDAO().delete(contentInstanceOldest,em);
					ContentInstances contentInstances = DAOFactory.getContentInstancesDAO().find(targetID, em);
					contentInstances.setCurrentNrOfInstances(contentInstances.getCurrentNrOfInstances() - 1);
					contentInstances.setCurrentByteSize(contentInstances.getCurrentByteSize() - contentInstanceOldest.getContentSize());
					DAOFactory.getContentInstancesDAO().update(contentInstances, em);
					em.getTransaction().commit();
					em.close();
					Router.readWriteLock.readLock().unlock();
				}
			}.start();
		}
		em.getTransaction().commit();
		em.close();

		// Response
		return new ResponseConfirm(StatusCode.STATUS_CREATED, contentInstance);

	}

	/**
	 * Retrieves {@link ContentInstance} resource.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doRetrieve (RequestIndication requestIndication) {

		// id:                  (response M*)
		// href:                (response O)
		// contentTypes:        (response O)
		// contentSize:         (response M)
		// creationTime:        (response M)
		// lastModifiedTime:    (response M)
		// delayTolerance:      (response O)
		// content:             (response M)

		ResponseConfirm errorResponse = new ResponseConfirm();

		// Check AccessRight
		EntityManager em = DBAccess.createEntityManager();
		em.getTransaction().begin();
		Container container = DAOFactory.getContainerDAO().find(requestIndication.getTargetID().split(Refs.CONTENTINSTANCES_REF)[0], em);
		errorResponse = checkAccessRight(container.getAccessRightID(), requestIndication.getRequestingEntity(), Constants.AR_READ);
		if (errorResponse != null) {
			em.close();
			return errorResponse;
		}

		// Check contentInstance existence
		ContentInstance contentInstance = DAOFactory.getContentInstanceDAO().find(requestIndication.getTargetID(), em);
		em.close();
		if (contentInstance == null) {
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getTargetID()+" does not exist")) ;
		}
		// Response
		return new ResponseConfirm(StatusCode.STATUS_OK, contentInstance);

	}

	/**
	 * Updates {@link ContentInstance} resource.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doUpdate (RequestIndication requestIndication) {

		// id:                  (updateReq NA) (response M*)
		// href:                (updateReq NA) (response O)
		// contentTypes:        (updateReq NA) (response O)
		// contentSize:         (updateReq NA) (response M)
		// creationTime:        (updateReq NA) (response M)
		// lastModifiedTime:    (updateReq NA) (response M)
		// delayTolerance:      (updateReq NA) (response O)
		// content:             (updateReq NA) (response M)

		// Response
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_METHOD_NOT_ALLOWED,requestIndication.getMethod()+" Method is not allowed"));

	}

	/**
	 * Deletes {@link ContentInstance} resource.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doDelete (RequestIndication requestIndication) {

		ResponseConfirm errorResponse = new ResponseConfirm();
		EntityManager em = DBAccess.createEntityManager();
		em.getTransaction().begin();
		ContentInstance contentInstance = DAOFactory.getContentInstanceDAO().find(requestIndication.getTargetID(), em);

		// Check Resource Existence
		if (contentInstance == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND,requestIndication.getTargetID()+" does not exist")) ;
		}

		// Check AccessRight
		Container container = DAOFactory.getContainerDAO().find(requestIndication.getTargetID().split(Refs.CONTENTINSTANCES_REF)[0], em);
		errorResponse = checkAccessRight(container.getAccessRightID(), requestIndication.getRequestingEntity(), Constants.AR_DELETE);
		if (errorResponse != null) {
			em.close();
			return errorResponse;
		}

		// Notify the subscribers
		Notifier.notify(StatusCode.STATUS_DELETED, contentInstance);

		// Delete
		DAOFactory.getContentInstanceDAO().delete(contentInstance,em);
		em.getTransaction().commit();
		em.close();
		
		// Response
		return new ResponseConfirm(StatusCode.STATUS_OK);
	}

	/**
	 * Executes {@link ContentInstance} resource.
	 * @param requestIndication - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doExecute (RequestIndication requestIndication) {

		// Response
		return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_IMPLEMENTED,requestIndication.getMethod()+" Method is not yet Implemented")) ;
	}
}
