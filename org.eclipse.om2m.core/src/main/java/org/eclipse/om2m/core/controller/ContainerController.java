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

import org.eclipse.om2m.commons.resource.AnnounceTo;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.Refs;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.commons.utils.DateConverter;
import org.eclipse.om2m.commons.utils.XmlMapper;
import org.eclipse.om2m.core.announcer.Announcer;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAOFactory;
import org.eclipse.om2m.core.dao.DBAccess;
import org.eclipse.om2m.core.notifier.Notifier;

/**
 * Implements Create, Retrieve, Update, Delete and Execute methods to handle
 * generic REST request for {@link Container} resource.
 *
 * @author <ul>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com
 *         ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com
 *         ></li>
 *         </ul>
 */

public class ContainerController extends Controller {

	/**
	 * Creates {@link Container} resource.
	 * 
	 * @param requestIndication
	 *            - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doCreate(RequestIndication requestIndication) {

		// contentInstancesReference: (createReq NP) (response M)
		// subscriptionsReference: (createReq NP) (response M)
		// id: (createReq O) (response M*)
		// expirationTime: (createReq O) (response M*)
		// accessRightID: (createReq O) (response O)
		// searchStrings: (createReq O) (response M)
		// creationTime: (createReq NP) (response M)
		// lastModifiedTime: (createReq NP) (response M)
		// announceTo: (createReq O) (response M*)
		// maxNrOfInstances: (createReq O) (response M*)
		// maxBytesSize: (createReq O) (response M*)
		// maxInstanceAge: (createReq O) (response M*)

		ResponseConfirm errorResponse = new ResponseConfirm();

		EntityManager em = DBAccess.createEntityManager();
		em.getTransaction().begin();
		
		String accessRightID = getAccessRightId(requestIndication.getTargetID(), em);
		
		// CheckResourceParentExistence
		if (accessRightID == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_NOT_FOUND, requestIndication.getTargetID() + " does not exist"));
		}

		// Check AccessRight
		errorResponse = checkAccessRight(accessRightID,
				requestIndication.getRequestingEntity(), Constants.AR_CREATE);
		if (errorResponse != null) {
			em.close();
			return errorResponse;
		}
		// Check Resource Representation
		if (requestIndication.getRepresentation() == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,	"Resource Representation is EMPTY"));
		}
		// Checks on attributes
		Container container = null;
		try {
			container = (Container) XmlMapper.getInstance().xmlToObject(
					requestIndication.getRepresentation());
		} catch (ClassCastException e) {
			em.close();
			LOGGER.debug("ClassCastException : Incorrect resource type in JAXB unmarshalling.",e);
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, "Incorrect resource type"));
		}
		if (container == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, "Incorrect resource representation syntax"));
		}
		// Check ExpirationTime
		if (container.getExpirationTime() != null && !checkExpirationTime(container.getExpirationTime())) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,	"Expiration Time is Out of Date"));
		}
		// ContentInstancesReference Must be NP
		if (container.getContentInstancesReference() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST, " ContentInstances Reference is Not Permitted"));
		}
		// SubscriptionsReference Must be NP
		if (container.getSubscriptionsReference() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,	" Subscription Reference is Not Permitted"));
		}
		// CreationTime Must be NP
		if (container.getCreationTime() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"Creation Time is Not Permitted"));
		}
		// LastModifiedTime Must be NP
		if (container.getLastModifiedTime() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"Last Modified Time is Not Permitted"));
		}
		// Storage
		// Check uniqueness and Set id if it's not available
		if (container.getId() == null
				|| container.getId().isEmpty()
				|| DAOFactory.getContainerDAO().find(requestIndication.getTargetID() + "/" + container.getId(), em) != null) {
			container.setId(generateId("CONT_", ""));
		}
		// Set URI
		container.setUri(requestIndication.getTargetID() + "/"
				+ container.getId());
		// Set Expiration Time if it's null
		if (container.getExpirationTime() == null) {
			// Expiration after 20min
			container
					.setExpirationTime(getNewExpirationTime(Constants.EXPIRATION_TIME));
		}
		// Set AccessRightID from the Parent if it's null or nonexistent
		if (DAOFactory.getAccessRightDAO().find(container.getAccessRightID(), em) == null) {
			container.setAccessRightID(accessRightID);
		}
		// Set searchString if it's null
		if (container.getSearchStrings() == null) {
			container.setSearchStrings(generateSearchStrings(container
					.getClass().getSimpleName(), container.getId()));
		}
		// Set the maxNrOfInstances
		if (container.getMaxNrOfInstances() == null
				|| container.getMaxNrOfInstances() > Constants.MAX_NBR_OF_INSTANCES) {
			container.setMaxNrOfInstances(Constants.MAX_NBR_OF_INSTANCES);
		}
		// Set announceTo if it is null
		if (container.getAnnounceTo() == null) {
			AnnounceTo announceTo = new AnnounceTo();
			announceTo.setActivated(false);
			announceTo.setGlobal(false);
			container.setAnnounceTo(announceTo);
		}
		// Set CreationTime
		container.setCreationTime(DateConverter.toXMLGregorianCalendar(
				new Date()).toString());
		// Set LastModifiedTime
		container.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(
				new Date()).toString());
		// Set references
		container.setContentInstancesReference(container.getUri()
				+ Refs.CONTENTINSTANCES_REF);
		container.setSubscriptionsReference(container.getUri()
				+ Refs.SUBSCRIPTIONS_REF);

		// Announcement
		if (container.getAnnounceTo().isActivated()) {
			container.setAnnounceTo(new Announcer().announce(
					container.getAnnounceTo(), container.getUri(),
					container.getSearchStrings(),
					requestIndication.getRequestingEntity()));
		}

		// Notify the subscribers
		Notifier.notify(StatusCode.STATUS_CREATED, container);

		// Store container
		DAOFactory.getContainerDAO().create(container, em);
		
		em.getTransaction().commit();
		em.close();
		// Response
		return new ResponseConfirm(StatusCode.STATUS_CREATED, container);

	}

	/**
	 * Retrieves {@link Container} resource.
	 * 
	 * @param requestIndication
	 *            - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doRetrieve(RequestIndication requestIndication) {

		// contentInstancesReference: (response M)
		// subscriptionsReference: (response M)
		// id: (response M*)
		// expirationTime: (response M*)
		// accessRightID: (response O)
		// searchStrings: (response M)
		// creationTime: (response M)
		// lastModifiedTime: (response M)
		// announceTo: (response M*)
		// maxNrOfInstances: (response M*)
		// maxBytesSize: (response M*)
		// maxInstanceAge: (response M*)

		ResponseConfirm errorResponse = new ResponseConfirm();
		EntityManager em = DBAccess.createEntityManager();
		em.getTransaction().begin();
		
		Container container = DAOFactory.getContainerDAO().find(requestIndication.getTargetID(), em);
		
		em.close();

		// Check if the resource exists in DataBase or Not
		if (container == null) {
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_NOT_FOUND,
					requestIndication.getTargetID()
							+ " does not exist in DataBase"));
		}
		// Check AccessRight
		errorResponse = checkAccessRight(container.getAccessRightID(),
				requestIndication.getRequestingEntity(), Constants.AR_READ);
		if (errorResponse != null) {
			return errorResponse;
		}

		container.setContentInstancesReference(container.getUri() + Refs.CONTENTINSTANCES_REF);
		container.setSubscriptionsReference(container.getUri() + Refs.SUBSCRIPTIONS_REF);
		
		// Response
		return new ResponseConfirm(StatusCode.STATUS_OK, container);
	}

	/**
	 * Updates {@link Container} resource.
	 * 
	 * @param requestIndication
	 *            - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doUpdate(RequestIndication requestIndication) {

		// contentInstancesReference: (updateReq NP) (response M)
		// subscriptionsReference: (updateReq NP) (response M)
		// id: (updateReq NP) (response M*)
		// expirationTime: (updateReq O) (response M*)
		// accessRightID: (updateReq O) (response O)
		// searchStrings: (updateReq O) (response M)
		// creationTime: (updateReq NP) (response M)
		// lastModifiedTime: (updateReq NP) (response M)
		// announceTo: (updateReq O) (response M*)
		// maxNrOfInstances: (updateReq O) (response M*)
		// maxBytesSize: (updateReq O) (response M*)
		// maxInstanceAge: (updateReq O) (response M*)

		ResponseConfirm errorResponse = new ResponseConfirm();
		EntityManager em = DBAccess.createEntityManager();
		em.getTransaction().begin();
		
		Container container = DAOFactory.getContainerDAO().find(requestIndication.getTargetID(), em);

		// Check Existence
		if (container == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_NOT_FOUND, requestIndication.getTargetID() + " does not exist in DataBase"));
		}
		// Check AccessRight
		errorResponse = checkAccessRight(container.getAccessRightID(),
				requestIndication.getRequestingEntity(), Constants.AR_WRITE);
		if (errorResponse != null) {
			em.close();
			return errorResponse;
		}
		// Check Resource Representation
		if (requestIndication.getRepresentation() == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"Resource Representation is EMPTY"));
		}
		// Checks on attributes
		// Construct New Resource
		Container containerNew = null;
		try {
			containerNew = (Container) XmlMapper.getInstance().xmlToObject(
					requestIndication.getRepresentation());
		} catch (ClassCastException e) {
			em.close();
			LOGGER.debug("ClassCastException : Incorrect resource type in JAXB unmarshalling.",e);
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST, "Incorrect resource type"));
		}
		if (containerNew == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"Incorrect resource representation syntax"));
		}
		// The Update of the ContainerId is NP
		if (containerNew.getId() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"AppId UPDATE is Not Permitted"));
		}
		// Check ExpirationTime
		if (containerNew.getExpirationTime() != null
				&& !checkExpirationTime(containerNew.getExpirationTime())) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"Expiration Time UPDATE is Out of Date"));
		}
		// ContentInstancesReferences Must be NP
		if (containerNew.getContentInstancesReference() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"ContentInstance Reference UPDATE is Not Permitted"));
		}
		// SubscriptionsReference Must be NP
		if (containerNew.getSubscriptionsReference() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"SubscriptionsReference UPDATE is Not Permitted"));
		}
		// CreationTime Must be NP
		if (containerNew.getCreationTime() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"Creation Time UPDATE is Not Permitted"));
		}
		// LastModifiedTime Must be NP
		if (containerNew.getLastModifiedTime() != null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_BAD_REQUEST,
					"Last Modified Time UPDATE is Not Permitted"));
		}
		// Storage
		// Set Expiration Time
		if (containerNew.getExpirationTime() != null) {
			em.close();
			container.setExpirationTime(containerNew.getExpirationTime());
		}
		// Set accessRightID if it exists
		if (DAOFactory.getAccessRightDAO().find(
				containerNew.getAccessRightID(), em) != null) {
			container.setAccessRightID(containerNew.getAccessRightID());
		}
		// Set searchStrings
		if (containerNew.getSearchStrings() != null) {
			container.setSearchStrings(containerNew.getSearchStrings());
		}
		// Set announceTo
		if (containerNew.getAnnounceTo() != null) {
			container.setAnnounceTo(containerNew.getAnnounceTo());
		}
		// Set maxNrOfInstances
		if (containerNew.getMaxNrOfInstances() != null
				&& containerNew.getMaxNrOfInstances() < Constants.MAX_NBR_OF_INSTANCES) {
			container.setMaxNrOfInstances(containerNew.getMaxNrOfInstances());
		}
		// Set maxByteSize
		if (containerNew.getMaxByteSize() != null) {
			container.setMaxByteSize(containerNew.getMaxByteSize());
		}
		// Set maxInstanceAge
		if (containerNew.getMaxInstanceAge() != null) {
			container.setMaxInstanceAge(containerNew.getMaxInstanceAge());
		}
		// Set LastModifiedTime
		container.setLastModifiedTime(DateConverter.toXMLGregorianCalendar(
				new Date()).toString());

		// Notify the subscribers
		Notifier.notify(StatusCode.STATUS_OK, container);

		// Store
		DAOFactory.getContainerDAO().update(container, em);

		em.getTransaction().commit();
		em.close();
		
		container.setContentInstancesReference(container.getUri() + Refs.CONTENTINSTANCES_REF);
		container.setSubscriptionsReference(container.getUri() + Refs.SUBSCRIPTIONS_REF);
		
		// Response
		return new ResponseConfirm(StatusCode.STATUS_OK, container);
	}

	/**
	 * Deletes {@link Container} resource.
	 * 
	 * @param requestIndication
	 *            - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doDelete(RequestIndication requestIndication) {

		ResponseConfirm errorResponse = new ResponseConfirm();
		EntityManager em = DBAccess.createEntityManager();
		em.getTransaction().begin();
		
		Container container = DAOFactory.getContainerDAO().find(
				requestIndication.getTargetID(), em);

		// Check Resource Existence
		if (container == null) {
			em.close();
			return new ResponseConfirm(new ErrorInfo(
					StatusCode.STATUS_NOT_FOUND,
					requestIndication.getTargetID() + " does not exist"));
		}
		// Check AccessRight
		errorResponse = checkAccessRight(container.getAccessRightID(),
				requestIndication.getRequestingEntity(), Constants.AR_DELETE);
		if (errorResponse != null) {
			em.close();
			return errorResponse;
		}

		// De-announcement
		if (container.getAnnounceTo().isActivated()) {
			new Announcer()
					.deAnnounce(container.getAnnounceTo(), container.getUri(),
							requestIndication.getRequestingEntity());
		}

		// Notify the subscribers
		Notifier.notify(StatusCode.STATUS_DELETED, container);

		// Delete
		DAOFactory.getContainerDAO().delete(container, em);
		
		em.getTransaction().commit();
		em.close();
		// Response
		return new ResponseConfirm(StatusCode.STATUS_OK);

	}

	/**
	 * Executes {@link Container} resource.
	 * 
	 * @param requestIndication
	 *            - The generic request to handle.
	 * @return The generic returned response.
	 */
	public ResponseConfirm doExecute(RequestIndication requestIndication) {

		// Response
		return new ResponseConfirm(new ErrorInfo(
				StatusCode.STATUS_NOT_IMPLEMENTED,
				requestIndication.getMethod()
						+ " Method is not yet Implemented"));
	}
}
