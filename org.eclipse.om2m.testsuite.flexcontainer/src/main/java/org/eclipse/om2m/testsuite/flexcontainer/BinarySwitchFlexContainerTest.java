/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.testsuite.flexcontainer;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class BinarySwitchFlexContainerTest extends FlexContainerTestSuite {

	// make private the default constructor
	private BinarySwitchFlexContainerTest() {
		super(null);
	}

	public BinarySwitchFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "BinarySwitchFlexContainer";
	}

	public void testCreateValidBinarySwitchFlexContainer() {
		FlexContainer initialFlexContainer = new FlexContainer();
		initialFlexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		initialFlexContainer.setOntologyRef("OrangeOntologyRef");
		initialFlexContainer.setCreator("Greg");
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeType("xs:boolean");
		ca.setCustomAttributeValue("true");
		initialFlexContainer.getCustomAttributes().add(ca);

		FlexContainer responseCreatedFlexContainer = null;

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(initialFlexContainer, "/" + Constants.CSE_ID,
				"GregFirstBinaryFlexContainer");
		if (response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// OK
			responseCreatedFlexContainer = (FlexContainer) response.getContent();

			// set name of the FlexContainer
			initialFlexContainer.setName("GregFirstBinaryFlexContainer");

			try {
				checkFlexContainerName(initialFlexContainer, responseCreatedFlexContainer);
				checkFlexContainerCreator(initialFlexContainer, responseCreatedFlexContainer);
				checkFlexContainerDefinition(initialFlexContainer, responseCreatedFlexContainer);
				checkFlexContainerOntologyRef(initialFlexContainer, responseCreatedFlexContainer);
				checkFlexContainerCustomAttribute(initialFlexContainer, responseCreatedFlexContainer);
			} catch (Exception e) {
				// KO
				e.printStackTrace();

				createTestReport("createValidBinarySwitchFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}

		} else {
			// KO
			createTestReport(
					"createValidBinarySwitchFlexContainer", Status.KO, "unexpected response status code :"
							+ response.getResponseStatusCode() + ", expected status code:" + ResponseStatusCode.CREATED,
					null);

			return;
		}

		// send RETRIEVE request
		response = sendRetrieveRequest(
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/GregFirstBinaryFlexContainer");
		if (response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// retrieve FlexContainer is in Content as a FlexContainer object
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

			try {
				checkFlexContainer(responseCreatedFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				// KO
				e.printStackTrace();
				createTestReport("createValidBinarySwitchFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}

		} else {
			// KO
			createTestReport(
					"createValidBinarySwitchFlexContainer", Status.KO, "unexpected response status code :"
							+ response.getResponseStatusCode() + ", expected status code:" + ResponseStatusCode.OK,
					null);

			return;
		}

		createTestReport("createValidBinarySwitchFlexContainer", Status.OK, null, null);

	}

	public void testCreateInvalidBinarySwitchFlexContainer() {
		FlexContainer initialFlexContainer = new FlexContainer();
		initialFlexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		initialFlexContainer.setOntologyRef("OrangeOntologyRef");
		initialFlexContainer.setCreator("Greg");
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerStateFake");
		ca.setCustomAttributeType("xs:boolean");
		ca.setCustomAttributeValue("true");
		initialFlexContainer.getCustomAttributes().add(ca);

		FlexContainer responseCreatedFlexContainer = null;

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(initialFlexContainer, "/" + Constants.CSE_ID,
				"GregFirstBinaryFlexContainer" + System.currentTimeMillis());
		if (response.getResponseStatusCode().equals(ResponseStatusCode.BAD_REQUEST)) {
			// expected BadRequest

			// nothing to do

		} else {
			// KO
			createTestReport("testCreateInvalidBinarySwitchFlexContainer", Status.KO,
					"unexpected response status code :" + response.getResponseStatusCode() + ", expected status code:"
							+ ResponseStatusCode.BAD_REQUEST,
					null);

			return;
		}

		createTestReport("testCreateInvalidBinarySwitchFlexContainer", Status.OK, null, null);
	}

	public void testUpdateBinarySwitchFlexContainer() {

		// create a binary switch flex container
		FlexContainer initialFlexContainer = new FlexContainer();
		initialFlexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		initialFlexContainer.setOntologyRef("OrangeOntologyRef");
		initialFlexContainer.setCreator("Greg");
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeType("xs:boolean");
		ca.setCustomAttributeValue("true");
		initialFlexContainer.getCustomAttributes().add(ca);

		FlexContainer responseCreatedFlexContainer = null;

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(initialFlexContainer, "/" + Constants.CSE_ID,
				"GregFirstBinaryFlexContainer" + System.currentTimeMillis());
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport(
					"testUpdateBinarySwitchFlexContainer", Status.KO, "CREATE - unexpected response status code :"
							+ response.getResponseStatusCode() + ", expected status code:" + ResponseStatusCode.CREATED,
					null);

			return;
		} else {
			// OK
			responseCreatedFlexContainer = (FlexContainer) response.getContent();
		}

		// update the flexContainer powerState custom attribute
		FlexContainer toBeUpdated = new FlexContainer();
		ca.setCustomAttributeValue("false");
		toBeUpdated.getCustomAttributes().add(ca);

		// send UPDATE request and check the response
		response = sendUpdateFlexContainerRequest(
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + responseCreatedFlexContainer.getName(),
				toBeUpdated);
		FlexContainer updatedFlexContainer = null;
		if (response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// OK
			updatedFlexContainer = (FlexContainer) response.getContent();

			// check powerState has been updated
			if (!updatedFlexContainer.getCustomAttribute("powerState").getCustomAttributeValue().equals("false")) {
				createTestReport("testUpdateBinarySwitchFlexContainer", Status.KO,
						"unable to update powerState value to false", null);
				return;
			}

		} else {
			// KO
			createTestReport(
					"testUpdateBinarySwitchFlexContainer", Status.KO, "UPDATE - unexpected response status code :"
							+ response.getResponseStatusCode() + ", expected status code:" + ResponseStatusCode.UPDATED,
					null);
			return;
		}

		// RETRIEVE flexContainer
		response = sendRetrieveRequest(
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + responseCreatedFlexContainer.getName());
		if (response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// OK
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

			responseCreatedFlexContainer.getCustomAttribute("powerState").setCustomAttributeValue("false");

			try {
				checkFlexContainer(responseCreatedFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				e.printStackTrace();
				createTestReport("testUpdateBinarySwitchFlexContainer", Status.KO,
						"updatedFlexContainer and retrievedFlexContainer are differents", e);
				return;
			}
		} else {
			// KO
			createTestReport("testUpdateBinarySwitchFlexContainer", Status.KO, "unable to retrieve the FlexContainer",
					null);
			return;

		}

		createTestReport("testUpdateBinarySwitchFlexContainer", Status.OK, null, null);

	}

	public void testDeleteBinarySwitchFlexContainer() {
		// create a binary switch flex container
		FlexContainer initialFlexContainer = new FlexContainer();
		initialFlexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		initialFlexContainer.setOntologyRef("OrangeOntologyRef");
		initialFlexContainer.setCreator("Greg");
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeType("xs:boolean");
		ca.setCustomAttributeValue("true");
		initialFlexContainer.getCustomAttributes().add(ca);

		FlexContainer responseCreatedFlexContainer = null;

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(initialFlexContainer, "/" + Constants.CSE_ID,
				"GregFirstBinaryFlexContainer" + System.currentTimeMillis());
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport(
					"testUpdateBinarySwitchFlexContainer", Status.KO, "CREATE - unexpected response status code :"
							+ response.getResponseStatusCode() + ", expected status code:" + ResponseStatusCode.CREATED,
					null);

			return;
		} else {
			// OK
			responseCreatedFlexContainer = (FlexContainer) response.getContent();
		}

		// delete the flexContainer
		response = sendDeleteRequest(
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + responseCreatedFlexContainer.getName());
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteBinarySwitchFlexContainer", Status.KO,
					"DELETE - unexpected response status code:" + response.getResponseStatusCode() + ", expected:"
							+ ResponseStatusCode.DELETED,
					null);
			return;
		}

		// try to retrieve the deleted container
		response = sendRetrieveRequest(
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + responseCreatedFlexContainer.getName());
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteBinarySwitchFlexContainer", Status.KO,
					"DELETE - unexpected response status code:" + response.getResponseStatusCode() + ", expected:"
							+ ResponseStatusCode.NOT_FOUND,
					null);
			return;
		}

		// TEST OK
		createTestReport("testDeleteBinarySwitchFlexContainer", Status.OK, null, null);

	}

	public void testDeleteUnknownBinarySwitch() {
		ResponsePrimitive response = sendDeleteRequest(
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/UNKNOWN_FLEXCONTAINER");
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteUnknownBinarySwitch", Status.KO,
					"DELETE - unexpected response status code:" + response.getResponseStatusCode() + ", expected:"
							+ ResponseStatusCode.NOT_FOUND,
					null);
			return;
		}

		// TEST OK
		createTestReport("testDeleteUnknownBinarySwitch", Status.OK, null, null);

	}

}
