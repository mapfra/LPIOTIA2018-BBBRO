/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.testsuite.flexcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.ResponseTypeInfo;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public abstract class FlexContainerTestSuite {

	private final CseService cseService;
	private final List<TestReport> reports = new ArrayList<TestReport>();

	public FlexContainerTestSuite(final CseService pCseService) {
		this.cseService = pCseService;
	}

	protected abstract String getTestSuiteName();

	public CseService getCseService() {
		return cseService;
	}

	public void executeTests() {

		Method[] methods = this.getClass().getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("test")) {
				try {
					method.invoke(this, null);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void executeTestsAndPrintReports() {
		executeTests();
		printTestReports();
	}

	protected ResponsePrimitive sendCreateFlexContainerRequest(FlexContainer flexContainer, String resourceLocation,
			String resourceName, String from) {
		return sendCreateRequest(resourceLocation, resourceName, ResourceType.FLEXCONTAINER, flexContainer, from);
	}

	/**
	 * Send a create request
	 * 
	 * @param flexContainer
	 *            flexContainer to be created
	 * @param resourceLocation
	 * @param resourceName
	 */
	protected ResponsePrimitive sendCreateFlexContainerRequest(FlexContainer flexContainer, String resourceLocation,
			String resourceName) {
		return sendCreateRequest(resourceLocation, resourceName, ResourceType.FLEXCONTAINER, flexContainer,
				Constants.ADMIN_REQUESTING_ENTITY);
	}

	protected ResponsePrimitive sendCreateSubscriptionRequest(Subscription subscription, String resourceLocation,
			String resourceName) {
		return sendCreateRequest(resourceLocation, resourceName, ResourceType.SUBSCRIPTION, subscription,
				Constants.ADMIN_REQUESTING_ENTITY);
	}

	protected ResponsePrimitive sendCreateAccessControlPolicyRequest(AccessControlPolicy policy,
			String resourceLocation, String resourceName) {
		return sendCreateRequest(resourceLocation, resourceName, ResourceType.ACCESS_CONTROL_POLICY, policy,
				Constants.ADMIN_REQUESTING_ENTITY);
	}

	private ResponsePrimitive sendCreateRequest(String resourceLocation, String resourceName, int resourceType,
			Resource resource, String from) {
		RequestPrimitive request = new RequestPrimitive();
		request.setContent(resource);
		request.setFrom(from);
		request.setTargetId(resourceLocation);
		request.setTo(resourceLocation);
		request.setResourceType(BigInteger.valueOf(resourceType));
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setName(resourceName);
		request.setOperation(Operation.CREATE);
		ResponsePrimitive response = cseService.doRequest(request);
		return response;
	}

	/**
	 * Send a update flexcontainer request
	 * 
	 * @param flexContainer
	 *            flexContainer to be created
	 * @param resourceLocation
	 */
	protected ResponsePrimitive sendUpdateFlexContainerRequest(String resourceLocation, FlexContainer flexContainer) {
		return sendUpdateRequest(resourceLocation, ResourceType.FLEXCONTAINER, flexContainer);
	}

	/**
	 * Send an update Subscription request
	 * 
	 * @param resourceLocation
	 * @param subscription
	 * @return
	 */
	protected ResponsePrimitive sendUpdateSubscriptionRequest(String resourceLocation, Subscription subscription) {
		return sendUpdateRequest(resourceLocation, ResourceType.SUBSCRIPTION, subscription);
	}

	/**
	 * Send a generic update request
	 * 
	 * @param resourceLocation
	 * @param resourceType
	 * @param resource
	 * @return
	 */
	protected ResponsePrimitive sendUpdateRequest(String resourceLocation, int resourceType, Resource resource) {
		RequestPrimitive request = new RequestPrimitive();
		request.setContent(resource);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(resourceLocation);
		request.setTo(resourceLocation);
		request.setResourceType(BigInteger.valueOf(resourceType));
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.UPDATE);
		ResponsePrimitive response = cseService.doRequest(request);
		return response;
	}

	/**
	 * Send a RETRIEVE request
	 * 
	 * @param resourceLocation
	 *            location of the resource to be requested
	 * @return ResponsePrimitive
	 */
	protected ResponsePrimitive sendRetrieveRequest(String resourceLocation) {
		return sendRetrieveRequest(resourceLocation, Constants.ADMIN_REQUESTING_ENTITY);
	}

	/**
	 * Send a RETRIEVE request
	 * 
	 * @param resourceLocation
	 *            location of the resource to be requested
	 * @param from
	 *            from
	 * @return ResponsePrimitive
	 */
	protected ResponsePrimitive sendRetrieveRequest(String resourceLocation, String from) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(from);
		request.setTargetId(resourceLocation);
		request.setTo(resourceLocation);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.RETRIEVE);
		request.setResultContent(ResultContent.ATTRIBUTES_AND_CHILD_REF);
		ResponsePrimitive response = cseService.doRequest(request);
		return response;
	}

	/**
	 * Send a DELETE request
	 * 
	 * @param resourceLocation
	 *            location of the resource to be deleted
	 * @return ResponsePrimitive
	 */
	protected ResponsePrimitive sendDeleteRequest(String resourceLocation) {
		return sendDeleteRequest(resourceLocation, Constants.ADMIN_REQUESTING_ENTITY);
	}
	
	protected ResponsePrimitive sendDeleteRequest(String resourceLocation, String from) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(from);
		request.setTargetId(resourceLocation);
		request.setTo(resourceLocation);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.DELETE);
		ResponsePrimitive response = cseService.doRequest(request);
		return response;
	}

	protected void checkFlexContainer(FlexContainer initial, FlexContainer toBeCompared) throws Exception {

		checkFlexContainerName(initial, toBeCompared);
		checkFlexContainerDefinition(initial, toBeCompared);
		checkFlexContainerOntologyRef(initial, toBeCompared);
		checkFlexContainerCreator(initial, toBeCompared);
		checkFlexContainerCustomAttribute(initial, toBeCompared);

	}

	protected void checkFlexContainerName(FlexContainer initial, FlexContainer toBeCompared) throws Exception {
		if (!initial.getName().equals(toBeCompared.getName())) {
			throw new Exception("name are not equal");
		}
	}

	protected void checkFlexContainerDefinition(FlexContainer initial, FlexContainer toBeCompared) throws Exception {
		if (!initial.getContainerDefinition().equals(toBeCompared.getContainerDefinition())) {
			throw new Exception("containerDefinition are not equal");
		}
	}

	protected void checkFlexContainerOntologyRef(FlexContainer initial, FlexContainer toBeCompared) throws Exception {

		if ((initial.getOntologyRef() == null) && (toBeCompared.getOntologyRef() != null)) {
			throw new Exception("ontologyRef are not equal");
		}
		if ((initial.getOntologyRef() != null) && (!initial.getOntologyRef().equals(toBeCompared.getOntologyRef()))) {
			throw new Exception("ontologyRef are not equal");
		}
	}

	protected void checkFlexContainerCreator(FlexContainer initial, FlexContainer toBeCompared) throws Exception {

		if ((initial.getCreator() == null) && (toBeCompared.getCreator() != null)) {
			throw new Exception("creator are not equal");
		}

		if ((initial.getCreator() != null) && (!initial.getCreator().equals(toBeCompared.getCreator()))) {
			throw new Exception("creator are not equal");
		}
	}

	protected void checkFlexContainerCustomAttribute(FlexContainer initial, FlexContainer toBeCompared)
			throws Exception {
		if (initial.getCustomAttributes().size() != toBeCompared.getCustomAttributes().size()) {
			throw new Exception("customAttributes list size are not equal");
		}

		for (CustomAttribute ca : initial.getCustomAttributes()) {
			// for each customAttribute hosted by initial FlexContainer,
			// check if the same customAttribute exist in toBeCompared
			// FlexContainer.

			CustomAttribute foundCa = null;
			for (CustomAttribute caOfToBeCompared : toBeCompared.getCustomAttributes()) {
				if (caOfToBeCompared.getCustomAttributeName().equals(ca.getCustomAttributeName())) {
					foundCa = caOfToBeCompared;
					break;
				}
			}
			checkCustomAttribute(ca, foundCa);
		}
	}

	protected void checkCustomAttribute(CustomAttribute initialCa, CustomAttribute toBeComparedCa) throws Exception {
		if (!initialCa.getCustomAttributeName().equals(toBeComparedCa.getCustomAttributeName())) {
			throw new Exception(
					"customAttributeName are differents (initialCaName=" + initialCa.getCustomAttributeName()
							+ ", toBeComparedCaName=" + toBeComparedCa.getCustomAttributeName());
		}

		// type may be null
		if ((initialCa.getCustomAttributeType() == null) && (toBeComparedCa.getCustomAttributeType() != null)) {
			throw new Exception("initialCa type is null but toBeComparedCa type is not null");
		}
		if (!initialCa.getCustomAttributeType().equals(toBeComparedCa.getCustomAttributeType())) {
			throw new Exception(
					"customAttributeType are differents (initialCaType=" + initialCa.getCustomAttributeType()
							+ ", toBeComparedCaType=" + toBeComparedCa.getCustomAttributeType());
		}

		// value may be null
		if ((initialCa.getCustomAttributeValue() == null) && (toBeComparedCa.getCustomAttributeValue() != null)) {
			throw new Exception("initialCa value is null but toBeComparedCa value is not null");
		}
		if (!initialCa.getCustomAttributeValue().equals(toBeComparedCa.getCustomAttributeValue())) {
			throw new Exception(
					"customAttributeValue are differents (initialCaType=" + initialCa.getCustomAttributeValue()
							+ ", toBeComparedCaType=" + toBeComparedCa.getCustomAttributeValue());
		}

	}

	protected void createTestReport(String methodName, Status status, String message, Exception e) {
		TestReport tr = new TestReport(methodName, status, message, e);
		reports.add(tr);
	}

	protected void printTestReports() {
		System.out.println("\n\n" + getTestSuiteName() + "\n");
		for (TestReport tr : reports) {
			System.out.println(tr.toString());
			if (tr.getException() != null) {
				tr.getException().printStackTrace();
			}
		}
	}

}
