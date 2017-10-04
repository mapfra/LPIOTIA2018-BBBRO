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
import org.eclipse.om2m.commons.resource.flexcontainerspec.RunModeFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class RunModeFlexContainerTest extends FlexContainerTestSuite {

	public RunModeFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "RunModeFlexContainerTest";
	}

	public void testCreateAndRetrieveRunModeFlexContainer() {
		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "RunModeFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		RunModeFlexContainer flexContainer = new RunModeFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setCustomAttributeName("opeMe");
		operationModeCustomAttribute.setCustomAttributeValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setCustomAttributeName("supMs");
		supportedModesCustomAttribute.setCustomAttributeValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		RunModeFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
					"unable to create RunModeFlexContainer :" + response.getContent(), null);
			return;
		} else {
			// OK
			createdFlexContainer = (RunModeFlexContainer) response.getContent();

			if (!flexContainerName.equals(createdFlexContainer.getName())) {
				createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
						"name are differents (expected:" + flexContainerName + ", found:"
								+ createdFlexContainer.getName() + ")",
						null);
				return;
			}

			try {
				checkFlexContainerCreator(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
						"creator are differents (expected:" + flexContainer.getCreator() + ", found:"
								+ createdFlexContainer.getCreator() + ")",
						e);
				return;
			}

			try {
				checkFlexContainerCustomAttribute(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
						"customAttributes are differents (expected:" + flexContainer.getCustomAttributes() + ", found:"
								+ createdFlexContainer.getCustomAttributes() + ")",
						e);
				return;
			}

			try {
				checkFlexContainerDefinition(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
						"containerDefinition are differents (expected:" + flexContainer.getContainerDefinition()
								+ ", found:" + createdFlexContainer.getContainerDefinition() + ")",
						e);
				return;
			}

			try {
				checkFlexContainerOntologyRef(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
						"ontologyRef are differents (expected:" + flexContainer.getOntologyRef() + ", found:"
								+ createdFlexContainer.getOntologyRef() + ")",
						e);
				return;
			}
		}

		// retrieve it
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
					"unable to retrieve RunModeFlexContainer (expected:" + ResponseStatusCode.OK + ", found:"
							+ response.getResponseStatusCode() + ")",
					null);
		} else {
			// OK
			RunModeFlexContainer retrievedFlexContainer = (RunModeFlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
						"flexContainers are differents:" + e.getMessage(), e);
			}

		}

		createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.OK, null, null);

	}

	public void testUpdateRunModeFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "RunModeFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		RunModeFlexContainer flexContainer = new RunModeFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setCustomAttributeName("opeMe");
		operationModeCustomAttribute.setCustomAttributeValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setCustomAttributeName("supMs");
		supportedModesCustomAttribute.setCustomAttributeValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		RunModeFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testUpdateRunModeFlexContainer", Status.KO,
					"unable to create RunModeFlexContainer :" + response.getContent(), null);
			return;
		} 

		// update Flexcontainer
		RunModeFlexContainer toBeUpdated = new RunModeFlexContainer();

		CustomAttribute operationModeToBeUpdated = new CustomAttribute();
		operationModeToBeUpdated.setCustomAttributeValue("OFF");
		operationModeToBeUpdated.setCustomAttributeName("opeMe");
		toBeUpdated.getCustomAttributes().add(operationModeToBeUpdated);

		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		RunModeFlexContainer updatedFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport(
					"testUpdateRunModeFlexContainer", Status.KO, "unable to update RunModeFlexContainer (expected "
							+ ResponseStatusCode.UPDATED + ", received: " + response.getResponseStatusCode() + ")",
					null);
			return;
		} else {
			
			updatedFlexContainer = (RunModeFlexContainer) response.getContent();
			if (updatedFlexContainer.getCustomAttributes().size() != 1) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"expecting 1 custom attribute, found " + updatedFlexContainer.getCustomAttributes().size(),
						null);
				return;
			}
			
			if (!updatedFlexContainer.getCustomAttribute("opeMe").getCustomAttributeValue().equals("OFF")) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"invalid operationMode customAttribute value (expected: OFF, received: "
								+ updatedFlexContainer.getCustomAttribute("opeMe").getCustomAttributeValue()
								+ ")",
						null);
				return;
			}
			

		}

		// retrieve the updated FlexContainer
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testUpdateRunModeFlexContainer", Status.KO,
					"unable to retrieve RunModeFlexContainer (expected " + ResponseStatusCode.OK + ", received: "
							+ response.getResponseStatusCode() + ")",
					null);
			return;
		} else {
			// OK
			RunModeFlexContainer retrievedFlexContainer = (RunModeFlexContainer) response.getContent();
			if (retrievedFlexContainer.getCustomAttributes().size() != 2) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"expecting 2 custom attribute, found " + updatedFlexContainer.getCustomAttributes().size(),
						null);
				return;
			}
			
			if (!retrievedFlexContainer.getCustomAttribute("opeMe").getCustomAttributeValue().equals("OFF")) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"invalid operationMode customAttribute value (expected: OFF, received: "
								+ retrievedFlexContainer.getCustomAttribute("opeMe").getCustomAttributeValue()
								+ ")",
						null);
				return;
			}
			
			if (!retrievedFlexContainer.getCustomAttribute("supMs").getCustomAttributeValue().equals("ON,OFF,UNKNOWN")) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"invalid supportedModes customAttribute value (expected: ON,OFF,UNKNOWN, received: "
								+ retrievedFlexContainer.getCustomAttribute("supMs").getCustomAttributeValue()
								+ ")",
						null);
				return;
			}
		}

		createTestReport("testUpdateRunModeFlexContainer", Status.OK, null, null);
	}

	public void testDeleteRunModeFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "RunModeFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		RunModeFlexContainer flexContainer = new RunModeFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setCustomAttributeName("opeMe");
		operationModeCustomAttribute.setCustomAttributeValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setCustomAttributeName("supMs");
		supportedModesCustomAttribute.setCustomAttributeValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		RunModeFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteRunModeFlexContainer", Status.KO,
					"unable to create RunModeFlexContainer :" + response.getContent(), null);
			return;
		}

		// send DELETE request
		response = sendDeleteRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteRunModeFlexContainer", Status.KO,
					"unable to delete RunModeFlexContainer :" + response.getContent(), null);
			return;
		}

		// try to retrieve it
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteRunModeFlexContainer", Status.KO, "expected errorCode:"
					+ ResponseStatusCode.NOT_FOUND + ", found:" + response.getResponseStatusCode(), null);
			return;
		}

		createTestReport("testDeleteRunModeFlexContainer", Status.OK, null, null);
	}

}
