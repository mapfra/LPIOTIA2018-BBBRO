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
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
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

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.runmode");
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setCustomAttributeName("operationMode");
		operationModeCustomAttribute.setCustomAttributeType("xs:enum");
		operationModeCustomAttribute.setCustomAttributeValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setCustomAttributeName("supportedModes");
		supportedModesCustomAttribute.setCustomAttributeType("xs:enum");
		supportedModesCustomAttribute.setCustomAttributeValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateAndRetrieveRunModeFlexContainer", Status.KO,
					"unable to create RunModeFlexContainer :" + response.getContent(), null);
			return;
		} else {
			// OK
			createdFlexContainer = (FlexContainer) response.getContent();

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
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
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

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.runmode");
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setCustomAttributeName("operationMode");
		operationModeCustomAttribute.setCustomAttributeType("xs:enum");
		operationModeCustomAttribute.setCustomAttributeValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setCustomAttributeName("supportedModes");
		supportedModesCustomAttribute.setCustomAttributeType("xs:enum");
		supportedModesCustomAttribute.setCustomAttributeValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testUpdateRunModeFlexContainer", Status.KO,
					"unable to create RunModeFlexContainer :" + response.getContent(), null);
			return;
		} 

		// update Flexcontainer
		FlexContainer toBeUpdated = new FlexContainer();
		toBeUpdated.setContainerDefinition("org.onem2m.home.moduleclass.runmode");

		CustomAttribute operationModeToBeUpdated = new CustomAttribute();
		operationModeToBeUpdated.setCustomAttributeValue("OFF");
		operationModeToBeUpdated.setCustomAttributeName("operationMode");
		operationModeToBeUpdated.setCustomAttributeType("xs:enum");
		toBeUpdated.getCustomAttributes().add(operationModeToBeUpdated);

		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		FlexContainer updatedFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport(
					"testUpdateRunModeFlexContainer", Status.KO, "unable to update RunModeFlexContainer (expected "
							+ ResponseStatusCode.UPDATED + ", received: " + response.getResponseStatusCode() + ")",
					null);
			return;
		} else {
			
			updatedFlexContainer = (FlexContainer) response.getContent();
			if (updatedFlexContainer.getCustomAttributes().size() != 1) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"expecting 1 custom attribute, found " + updatedFlexContainer.getCustomAttributes().size(),
						null);
				return;
			}
			
			if (!updatedFlexContainer.getCustomAttribute("operationMode").getCustomAttributeValue().equals("OFF")) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"invalid operationMode customAttribute value (expected: OFF, received: "
								+ updatedFlexContainer.getCustomAttribute("operationMode").getCustomAttributeValue()
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
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
			if (retrievedFlexContainer.getCustomAttributes().size() != 2) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"expecting 2 custom attribute, found " + updatedFlexContainer.getCustomAttributes().size(),
						null);
				return;
			}
			
			if (!retrievedFlexContainer.getCustomAttribute("operationMode").getCustomAttributeValue().equals("OFF")) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"invalid operationMode customAttribute value (expected: OFF, received: "
								+ retrievedFlexContainer.getCustomAttribute("operationMode").getCustomAttributeValue()
								+ ")",
						null);
				return;
			}
			
			if (!retrievedFlexContainer.getCustomAttribute("supportedModes").getCustomAttributeValue().equals("ON,OFF,UNKNOWN")) {
				createTestReport("testUpdateRunModeFlexContainer", Status.KO,
						"invalid supportedModes customAttribute value (expected: ON,OFF,UNKNOWN, received: "
								+ retrievedFlexContainer.getCustomAttribute("supportedModes").getCustomAttributeValue()
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

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.runmode");
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OrangeOntology");

		CustomAttribute operationModeCustomAttribute = new CustomAttribute();
		operationModeCustomAttribute.setCustomAttributeName("operationMode");
		operationModeCustomAttribute.setCustomAttributeType("xs:enum");
		operationModeCustomAttribute.setCustomAttributeValue("ON");
		flexContainer.getCustomAttributes().add(operationModeCustomAttribute);

		CustomAttribute supportedModesCustomAttribute = new CustomAttribute();
		supportedModesCustomAttribute.setCustomAttributeName("supportedModes");
		supportedModesCustomAttribute.setCustomAttributeType("xs:enum");
		supportedModesCustomAttribute.setCustomAttributeValue("ON,OFF,UNKNOWN");
		flexContainer.getCustomAttributes().add(supportedModesCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
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
