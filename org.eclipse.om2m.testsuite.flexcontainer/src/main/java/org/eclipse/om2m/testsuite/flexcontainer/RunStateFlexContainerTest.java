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
import org.eclipse.om2m.commons.resource.flexcontainerspec.RunStateFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class RunStateFlexContainerTest extends FlexContainerTestSuite {

	public RunStateFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "RunStateFlexContainerTest";
	}

	public void testCreateAndRetrieveRunStateFlexContainer() {
		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "RunStateFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		RunStateFlexContainer flexContainer = new RunStateFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setShortName("opeMe");
		operationModeCustomAttribute.setValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setShortName("supMs");
		supportedModesCustomAttribute.setValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		RunStateFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.KO,
					"unable to create RunStateFlexContainer :" + response.getContent(), null);
			return;
		} else {
			// OK
			createdFlexContainer = (RunStateFlexContainer) response.getContent();

			if (!flexContainerName.equals(createdFlexContainer.getName())) {
				createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.KO,
						"name are differents (expected:" + flexContainerName + ", found:"
								+ createdFlexContainer.getName() + ")",
						null);
				return;
			}

			try {
				checkFlexContainerCreator(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.KO,
						"creator are differents (expected:" + flexContainer.getCreator() + ", found:"
								+ createdFlexContainer.getCreator() + ")",
						e);
				return;
			}

			try {
				checkFlexContainerCustomAttribute(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.KO,
						"customAttributes are differents (expected:" + flexContainer.getCustomAttributes() + ", found:"
								+ createdFlexContainer.getCustomAttributes() + ")",
						e);
				return;
			}

			try {
				checkFlexContainerDefinition(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.KO,
						"containerDefinition are differents (expected:" + flexContainer.getContainerDefinition()
								+ ", found:" + createdFlexContainer.getContainerDefinition() + ")",
						e);
				return;
			}

			try {
				checkFlexContainerOntologyRef(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.KO,
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
			createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.KO,
					"unable to retrieve RunStateFlexContainer (expected:" + ResponseStatusCode.OK + ", found:"
							+ response.getResponseStatusCode() + ")",
					null);
		} else {
			// OK
			RunStateFlexContainer retrievedFlexContainer = (RunStateFlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.KO,
						"flexContainers are differents:" + e.getMessage(), e);
			}

		}

		createTestReport("testCreateAndRetrieveRunStateFlexContainer", Status.OK, null, null);

	}

	public void testUpdateRunStateFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "RunStateFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		RunStateFlexContainer flexContainer = new RunStateFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setShortName("opeMe");
		operationModeCustomAttribute.setValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setShortName("supMs");
		supportedModesCustomAttribute.setValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		RunStateFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testUpdateRunStateFlexContainer", Status.KO,
					"unable to create RunStateFlexContainer :" + response.getContent(), null);
			return;
		} 

		// update Flexcontainer
		RunStateFlexContainer toBeUpdated = new RunStateFlexContainer();

		CustomAttribute operationModeToBeUpdated = new CustomAttribute();
		operationModeToBeUpdated.setValue("OFF");
		operationModeToBeUpdated.setShortName("opeMe");
		toBeUpdated.getCustomAttributes().add(operationModeToBeUpdated);

		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		RunStateFlexContainer updatedFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport(
					"testUpdateRunStateFlexContainer", Status.KO, "unable to update RunStateFlexContainer (expected "
							+ ResponseStatusCode.UPDATED + ", received: " + response.getResponseStatusCode() + ")",
					null);
			return;
		} else {
			
			updatedFlexContainer = (RunStateFlexContainer) response.getContent();
			if (updatedFlexContainer.getCustomAttributes().size() != 1) {
				createTestReport("testUpdateRunStateFlexContainer", Status.KO,
						"expecting 1 custom attribute, found " + updatedFlexContainer.getCustomAttributes().size(),
						null);
				return;
			}
			
			if (!updatedFlexContainer.getCustomAttribute("opeMe").getValue().equals("OFF")) {
				createTestReport("testUpdateRunStateFlexContainer", Status.KO,
						"invalid operationMode customAttribute value (expected: OFF, received: "
								+ updatedFlexContainer.getCustomAttribute("opeMe").getValue()
								+ ")",
						null);
				return;
			}
			

		}

		// retrieve the updated FlexContainer
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testUpdateRunStateFlexContainer", Status.KO,
					"unable to retrieve RunStateFlexContainer (expected " + ResponseStatusCode.OK + ", received: "
							+ response.getResponseStatusCode() + ")",
					null);
			return;
		} else {
			// OK
			RunStateFlexContainer retrievedFlexContainer = (RunStateFlexContainer) response.getContent();
			if (retrievedFlexContainer.getCustomAttributes().size() != 2) {
				createTestReport("testUpdateRunStateFlexContainer", Status.KO,
						"expecting 2 custom attribute, found " + updatedFlexContainer.getCustomAttributes().size(),
						null);
				return;
			}
			
			if (!retrievedFlexContainer.getCustomAttribute("opeMe").getValue().equals("OFF")) {
				createTestReport("testUpdateRunStateFlexContainer", Status.KO,
						"invalid operationMode customAttribute value (expected: OFF, received: "
								+ retrievedFlexContainer.getCustomAttribute("opeMe").getValue()
								+ ")",
						null);
				return;
			}
			
			if (!retrievedFlexContainer.getCustomAttribute("supMs").getValue().equals("ON,OFF,UNKNOWN")) {
				createTestReport("testUpdateRunStateFlexContainer", Status.KO,
						"invalid supportedModes customAttribute value (expected: ON,OFF,UNKNOWN, received: "
								+ retrievedFlexContainer.getCustomAttribute("supMs").getValue()
								+ ")",
						null);
				return;
			}
		}

		createTestReport("testUpdateRunStateFlexContainer", Status.OK, null, null);
	}

	public void testDeleteRunStateFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "RunStateFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		RunStateFlexContainer flexContainer = new RunStateFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setShortName("opeMe");
		operationModeCustomAttribute.setValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setShortName("supMs");
		supportedModesCustomAttribute.setValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		RunStateFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteRunStateFlexContainer", Status.KO,
					"unable to create RunStateFlexContainer :" + response.getContent(), null);
			return;
		}

		// send DELETE request
		response = sendDeleteRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteRunStateFlexContainer", Status.KO,
					"unable to delete RunStateFlexContainer :" + response.getContent(), null);
			return;
		}

		// try to retrieve it
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteRunStateFlexContainer", Status.KO, "expected errorCode:"
					+ ResponseStatusCode.NOT_FOUND + ", found:" + response.getResponseStatusCode(), null);
			return;
		}

		createTestReport("testDeleteRunStateFlexContainer", Status.OK, null, null);
	}

}
