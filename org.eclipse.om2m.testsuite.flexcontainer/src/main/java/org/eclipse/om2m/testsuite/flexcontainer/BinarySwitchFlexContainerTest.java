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
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;  
import org.eclipse.om2m.commons.resource.flexcontainerspec.BinarySwitchFlexContainer;
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
		BinarySwitchFlexContainer initialFlexContainer = new BinarySwitchFlexContainer();
		initialFlexContainer.setOntologyRef("OrangeOntologyRef");
		initialFlexContainer.setCreator("Greg");
		initialFlexContainer.setName("GregFirstBinaryFlexContainer" + System.currentTimeMillis());
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powSe");
		ca.setCustomAttributeValue("true");
		initialFlexContainer.getCustomAttributes().add(ca);

		BinarySwitchFlexContainer responseCreatedFlexContainer = null;

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(initialFlexContainer, "/" + Constants.CSE_ID,
				"admin:admin");
		if (response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// OK
			responseCreatedFlexContainer = (BinarySwitchFlexContainer) response.getContent();

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
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + responseCreatedFlexContainer.getName());
		if (response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// retrieve FlexContainer is in Content as a FlexContainer object
			BinarySwitchFlexContainer retrievedFlexContainer = (BinarySwitchFlexContainer) response.getContent();

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
		BinarySwitchFlexContainer initialFlexContainer = new BinarySwitchFlexContainer();
		initialFlexContainer.setOntologyRef("OrangeOntologyRef");
		initialFlexContainer.setCreator("Greg");
		initialFlexContainer.setName("GregFirstBinaryFlexContainer" + System.currentTimeMillis());
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerStateFake");
		ca.setCustomAttributeValue("true");
		initialFlexContainer.getCustomAttributes().add(ca);

		BinarySwitchFlexContainer responseCreatedFlexContainer = null;

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(initialFlexContainer, "/" + Constants.CSE_ID,
				Constants.ADMIN_REQUESTING_ENTITY);
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
		BinarySwitchFlexContainer initialFlexContainer = new BinarySwitchFlexContainer();
		initialFlexContainer.setOntologyRef("OrangeOntologyRef");
		initialFlexContainer.setCreator("Greg");
		initialFlexContainer.setName("GregFirstBinaryFlexContainer" + System.currentTimeMillis());
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powSe");
		ca.setCustomAttributeValue("true");
		initialFlexContainer.getCustomAttributes().add(ca);

		BinarySwitchFlexContainer responseCreatedFlexContainer = null;

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(initialFlexContainer, "/" + Constants.CSE_ID,
				Constants.ADMIN_REQUESTING_ENTITY);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport(
					"testUpdateBinarySwitchFlexContainer", Status.KO, "CREATE - unexpected response status code :"
							+ response.getResponseStatusCode() + ", expected status code:" + ResponseStatusCode.CREATED,
					null);

			return;
		} else {
			// OK
			responseCreatedFlexContainer = (BinarySwitchFlexContainer) response.getContent();
		}

		// update the flexContainer powerState custom attribute
		BinarySwitchFlexContainer toBeUpdated = new BinarySwitchFlexContainer();
		ca.setCustomAttributeValue("false");
		toBeUpdated.getCustomAttributes().add(ca);

		// send UPDATE request and check the response
		response = sendUpdateFlexContainerRequest(
				"/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + responseCreatedFlexContainer.getName(),
				toBeUpdated);
		BinarySwitchFlexContainer updatedFlexContainer = null;
		if (response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// OK
			updatedFlexContainer = (BinarySwitchFlexContainer) response.getContent();

			// check powerState has been updated
			if (!updatedFlexContainer.getCustomAttribute("powSe").getCustomAttributeValue().equals("false")) {
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
			BinarySwitchFlexContainer retrievedFlexContainer = (BinarySwitchFlexContainer) response.getContent();

			responseCreatedFlexContainer.getCustomAttribute("powSe").setCustomAttributeValue("false");

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
		BinarySwitchFlexContainer initialFlexContainer = new BinarySwitchFlexContainer();
		initialFlexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		initialFlexContainer.setOntologyRef("OrangeOntologyRef");
		initialFlexContainer.setCreator("Greg");
		initialFlexContainer.setName("GregFirstBinaryFlexContainer" + System.currentTimeMillis());
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powSe");
		ca.setCustomAttributeValue("true");
		initialFlexContainer.getCustomAttributes().add(ca);

		BinarySwitchFlexContainer responseCreatedFlexContainer = null;

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(initialFlexContainer, "/" + Constants.CSE_ID,
				Constants.ADMIN_REQUESTING_ENTITY);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport(
					"testUpdateBinarySwitchFlexContainer", Status.KO, "CREATE - unexpected response status code :"
							+ response.getResponseStatusCode() + ", expected status code:" + ResponseStatusCode.CREATED,
					null);

			return;
		} else {
			// OK
			responseCreatedFlexContainer = (BinarySwitchFlexContainer) response.getContent();
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
