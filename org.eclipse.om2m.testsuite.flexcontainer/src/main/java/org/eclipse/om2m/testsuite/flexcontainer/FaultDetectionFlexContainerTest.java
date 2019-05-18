/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.testsuite.flexcontainer;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FaultDetectionFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class FaultDetectionFlexContainerTest extends FlexContainerTestSuite {

	public FaultDetectionFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "FaultDetectionFlexContainerTest";
	}

	/**
	 * Test create and retrieve FaultDetection FlexContainer
	 */
	public void testCreateFaultDetectionFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "FaultDetectionFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FaultDetectionFlexContainer flexContainer = new FaultDetectionFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setOntologyRef("OrangeOntology");
		flexContainer.setCreator("Greg");

		CustomAttribute statusCustomAttribute = new CustomAttribute();
		statusCustomAttribute.setShortName("stats");
		statusCustomAttribute.setValue("false");
		flexContainer.getCustomAttributes().add(statusCustomAttribute);

		CustomAttribute codeCustomAttribute = new CustomAttribute();
		codeCustomAttribute.setShortName("code");
		codeCustomAttribute.setValue("123");
		flexContainer.getCustomAttributes().add(codeCustomAttribute);

		CustomAttribute descriptionCustomAttribute = new CustomAttribute();
		descriptionCustomAttribute.setShortName("descn");
		descriptionCustomAttribute.setValue("My description");
		flexContainer.getCustomAttributes().add(descriptionCustomAttribute);

		// send create Request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		FaultDetectionFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateFaultDetectionFlexContainer", Status.KO,
					"unable to create FaultDetectionFlexContainer", null);
			return;
		} else {
			createdFlexContainer = (FaultDetectionFlexContainer) response.getContent();

			if (!flexContainerName.equals(createdFlexContainer.getName())) {
				createTestReport("testCreateFaultDetectionFlexContainer", Status.KO,
						"resource name are differents(expected:" + flexContainerName + ", found:"
								+ createdFlexContainer.getName() + ")",
						null);
				return;
			}

			try {
				checkFlexContainerCustomAttribute(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateFaultDetectionFlexContainer", Status.KO,
						"custom attributes are differents(expected:" + flexContainer.getCustomAttributes() + ", found:"
								+ createdFlexContainer.getCustomAttributes() + ")",
						e);
				return;
			}

			try {
				checkFlexContainerCreator(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateFaultDetectionFlexContainer", Status.KO, "creator are differents(expected:"
						+ flexContainer.getCreator() + ", found:" + createdFlexContainer.getCreator() + ")", e);
				return;
			}

			try {
				checkFlexContainerDefinition(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateFaultDetectionFlexContainer", Status.KO,
						"containerDefinition are differents(expected:" + flexContainer.getContainerDefinition()
								+ ", found:" + createdFlexContainer.getContainerDefinition() + ")",
						e);
				return;
			}

			try {
				checkFlexContainerOntologyRef(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateFaultDetectionFlexContainer", Status.KO,
						"ontologyRef are differents(expected:" + flexContainer.getOntologyRef() + ", found:"
								+ createdFlexContainer.getOntologyRef() + ")",
						e);
				return;
			}

		}

		// try to retrieve the FlexContainer
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testCreateFaultDetectionFlexContainer", Status.KO, "unable to retrieve the FlexContainer",
					null);
			return;
		} else {
			FaultDetectionFlexContainer retrievedFlexContainer = (FaultDetectionFlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateFaultDetectionFlexContainer", Status.KO,
						"flexContainers are differents: " + e.getMessage(), e);
				return;
			}
		}

		createTestReport("testCreateFaultDetectionFlexContainer", Status.OK, null, null);

	}

	public void testUpdateFaultDetectionFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "FaultDetectionFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FaultDetectionFlexContainer flexContainer = new FaultDetectionFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setOntologyRef("OrangeOntology");
		flexContainer.setCreator("Greg");

		CustomAttribute statusCustomAttribute = new CustomAttribute();
		statusCustomAttribute.setShortName("stats");
		statusCustomAttribute.setValue("false");
		flexContainer.getCustomAttributes().add(statusCustomAttribute);

		CustomAttribute codeCustomAttribute = new CustomAttribute();
		codeCustomAttribute.setShortName("code");
		codeCustomAttribute.setValue("123");
		flexContainer.getCustomAttributes().add(codeCustomAttribute);

		CustomAttribute descriptionCustomAttribute = new CustomAttribute();
		descriptionCustomAttribute.setShortName("descn");
		descriptionCustomAttribute.setValue("My description");
		flexContainer.getCustomAttributes().add(descriptionCustomAttribute);

		// send create Request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testUpdateFaultDetectionFlexContainer", Status.KO,
					"unable to create FaultDetectionFlexContainer", null);
			return;
		}

		// update the status value
		FaultDetectionFlexContainer flexContainerToBeUpdated = new FaultDetectionFlexContainer();
		CustomAttribute statusCustomAttributeToBeUpdated = new CustomAttribute();
		statusCustomAttributeToBeUpdated.setShortName("stats");
		statusCustomAttributeToBeUpdated.setValue("true");
		flexContainerToBeUpdated.getCustomAttributes().add(statusCustomAttributeToBeUpdated);

		// send UPDATE request
		response = sendUpdateFlexContainerRequest(flexContainerLocation, flexContainerToBeUpdated);
		FaultDetectionFlexContainer updatedFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport("testUpdateFaultDetectionFlexContainer", Status.KO,
					"unable to update FaultDetectionFlexContainer", null);
			return;
		} else {
			updatedFlexContainer = (FaultDetectionFlexContainer) response.getContent();
			if (!updatedFlexContainer.getCustomAttribute("stats").getValue().equals("true")) {
				createTestReport("testUpdateFaultDetectionFlexContainer", Status.KO,
						"expected \"true\" value for status custom attribute", null);
				return;
			}
		}

		createTestReport("testUpdateFaultDetectionFlexContainer", Status.OK, null, null);
	}

	public void testDeleteFaultDetectionFlexContainer() {
		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "FaultDetectionFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FaultDetectionFlexContainer flexContainer = new FaultDetectionFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setOntologyRef("OrangeOntology");
		flexContainer.setCreator("Greg");

		CustomAttribute statusCustomAttribute = new CustomAttribute();
		statusCustomAttribute.setShortName("stats");
		statusCustomAttribute.setValue("false");
		flexContainer.getCustomAttributes().add(statusCustomAttribute);

		CustomAttribute codeCustomAttribute = new CustomAttribute();
		codeCustomAttribute.setShortName("code");
		codeCustomAttribute.setValue("123");
		flexContainer.getCustomAttributes().add(codeCustomAttribute);

		CustomAttribute descriptionCustomAttribute = new CustomAttribute();
		descriptionCustomAttribute.setShortName("descn");
		descriptionCustomAttribute.setValue("My description");
		flexContainer.getCustomAttributes().add(descriptionCustomAttribute);

		// send create Request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteFaultDetectionFlexContainer", Status.KO,
					"unable to create FaultDetectionFlexContainer", null);
			return;
		}

		// retrieve it ==> OK
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			createTestReport("testDeleteFaultDetectionFlexContainer", Status.KO,
					"unable to retrieve FaultDetectionFlexContainer", null);
			return;
		}

		// delete it
		response = sendDeleteRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteFaultDetectionFlexContainer", Status.KO,
					"unable to delete FaultDetectionFlexContainer", null);
			return;
		}

		// retrieve it again ==> NOT FOUND
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			createTestReport("testDeleteFaultDetectionFlexContainer", Status.KO,
					"expected " + ResponseStatusCode.NOT_FOUND + ", found: " + response.getResponseStatusCode(), null);
			return;
		}

		createTestReport("testDeleteFaultDetectionFlexContainer", Status.OK, null, null);
	}

}
