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
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceLightFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class LightFlexContainerTest extends FlexContainerTestSuite {

	public LightFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "LightFlexContainerTest";
	}

	public void testCreateAndRetrieveLightFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "LightFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		DeviceLightFlexContainer flexContainer = new DeviceLightFlexContainer();
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("Orange");

		CustomAttribute illuminanceLevelCustomAttribute = new CustomAttribute();
		illuminanceLevelCustomAttribute.setShortName("illuminanceLevel");
		illuminanceLevelCustomAttribute.setValue("90");
		flexContainer.getCustomAttributes().add(illuminanceLevelCustomAttribute);

		CustomAttribute illuminanceStepLevelCustomAttribute = new CustomAttribute();
		illuminanceStepLevelCustomAttribute.setShortName("illuminanceStepLevel");
		illuminanceStepLevelCustomAttribute.setValue("1");
		flexContainer.getCustomAttributes().add(illuminanceStepLevelCustomAttribute);

		CustomAttribute modeCustomAttribute = new CustomAttribute();
		modeCustomAttribute.setShortName("mode");
		modeCustomAttribute.setValue("normal");
		flexContainer.getCustomAttributes().add(modeCustomAttribute);

		CustomAttribute rgbColorSettingCustomAttribute = new CustomAttribute();
		rgbColorSettingCustomAttribute.setShortName("rgbColorSetting");
		rgbColorSettingCustomAttribute.setValue(new Integer(0x222222).toString());
		flexContainer.getCustomAttributes().add(rgbColorSettingCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		DeviceLightFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateAndRetrieveLightFlexContainer", Status.KO,
					"unable to create LightFlexContainer:" + response.getContent(), null);
			return;
		} else {
			// OK
			createdFlexContainer = (DeviceLightFlexContainer) response.getContent();

			if (!createdFlexContainer.getName().equals(flexContainerName)) {
				createTestReport("testCreateAndRetrieveLightFlexContainer", Status.KO,
						"wrong name. Expecting:" + flexContainerName + ", found: " + createdFlexContainer.getName(),
						null);
				return;
			}

			try {
				checkFlexContainerCreator(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveLightFlexContainer", Status.KO,
						"wrong OntologyRef: " + e.getMessage(), e);
				return;
			}

			try {
				checkFlexContainerDefinition(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveLightFlexContainer", Status.KO,
						"wrong ContainerDefinition: " + e.getMessage(), e);
				return;
			}

			try {
				checkFlexContainerOntologyRef(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveLightFlexContainer", Status.KO,
						"wrong OntologyRef: " + e.getMessage(), e);
				return;
			}

			try {
				checkFlexContainerCustomAttribute(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveLightFlexContainer", Status.KO,
						"wrong CustomAttribute: " + e.getMessage(), e);
				return;
			}

		}

		// send RETRIEVE request
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testCreateAndRetrieveLightFlexContainer", Status.KO,
					"unable to retrieve LightFlexContainer:" + response.getContent(), null);
			return;
		} else {
			// OK
			DeviceLightFlexContainer retrievedFlexContainer = (DeviceLightFlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveLightFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}

		}

		createTestReport("testCreateAndRetrieveLightFlexContainer", Status.OK, null, null);
	}

	public void testDeleteLightFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "LightFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		DeviceLightFlexContainer flexContainer = new DeviceLightFlexContainer();
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("Orange");

		CustomAttribute illuminanceLevelCustomAttribute = new CustomAttribute();
		illuminanceLevelCustomAttribute.setShortName("illuminanceLevel");
		illuminanceLevelCustomAttribute.setValue("90");
		flexContainer.getCustomAttributes().add(illuminanceLevelCustomAttribute);

		CustomAttribute illuminanceStepLevelCustomAttribute = new CustomAttribute();
		illuminanceStepLevelCustomAttribute.setShortName("illuminanceStepLevel");
		illuminanceStepLevelCustomAttribute.setValue("1");
		flexContainer.getCustomAttributes().add(illuminanceStepLevelCustomAttribute);

		CustomAttribute modeCustomAttribute = new CustomAttribute();
		modeCustomAttribute.setShortName("mode");
		modeCustomAttribute.setValue("normal");
		flexContainer.getCustomAttributes().add(modeCustomAttribute);

		CustomAttribute rgbColorSettingCustomAttribute = new CustomAttribute();
		rgbColorSettingCustomAttribute.setShortName("rgbColorSetting");
		rgbColorSettingCustomAttribute.setValue(new Integer(0x222222).toString());
		flexContainer.getCustomAttributes().add(rgbColorSettingCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteLightFlexContainer", Status.KO,
					"unable to create LightFlexContainer:" + response.getContent(), null);
			return;
		}

		// send DELETE request
		response = sendDeleteRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteLightFlexContainer", Status.KO,
					"unable to delete LightFlexContainer:" + response.getContent(), null);
			return;
		}

		// retrieve the deleted LightFlexContainer ==> NOT FOUND
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteLightFlexContainer", Status.KO,
					"expecting:" + ResponseStatusCode.NOT_FOUND + ", received: " + response.getResponseStatusCode(),
					null);
			return;
		}

		createTestReport("testDeleteLightFlexContainer", Status.OK, null, null);
	}

	public void testUpdateLightFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "LightFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		DeviceLightFlexContainer flexContainer = new DeviceLightFlexContainer();
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("Orange");

		CustomAttribute illuminanceLevelCustomAttribute = new CustomAttribute();
		illuminanceLevelCustomAttribute.setShortName("illuminanceLevel");
		illuminanceLevelCustomAttribute.setValue("90");
		flexContainer.getCustomAttributes().add(illuminanceLevelCustomAttribute);

		CustomAttribute illuminanceStepLevelCustomAttribute = new CustomAttribute();
		illuminanceStepLevelCustomAttribute.setShortName("illuminanceStepLevel");
		illuminanceStepLevelCustomAttribute.setValue("1");
		flexContainer.getCustomAttributes().add(illuminanceStepLevelCustomAttribute);

		CustomAttribute modeCustomAttribute = new CustomAttribute();
		modeCustomAttribute.setShortName("mode");
		modeCustomAttribute.setValue("normal");
		flexContainer.getCustomAttributes().add(modeCustomAttribute);

		CustomAttribute rgbColorSettingCustomAttribute = new CustomAttribute();
		rgbColorSettingCustomAttribute.setShortName("rgbColorSetting");
		rgbColorSettingCustomAttribute.setValue(new Integer(0x222222).toString());
		flexContainer.getCustomAttributes().add(rgbColorSettingCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		DeviceLightFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testUpdateLightFlexContainer", Status.KO,
					"unable to create LightFlexContainer:" + response.getContent(), null);
			return;
		} else {
			createdFlexContainer = (DeviceLightFlexContainer) response.getContent();
		}

		// prepare a FlexContainer for update
		DeviceLightFlexContainer toBeUpdated = new DeviceLightFlexContainer();
		CustomAttribute illuminanceToBeUpdated = new CustomAttribute();
		illuminanceToBeUpdated.setShortName("illuminanceLevel");
		illuminanceToBeUpdated.setValue("85");
		toBeUpdated.getCustomAttributes().add(illuminanceToBeUpdated);

		// send UPDATE request
		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport("testUpdateLightFlexContainer", Status.KO,
					"unable to update LightFlexContainer:" + response.getContent(), null);
			return;
		} else {
			// OK
			DeviceLightFlexContainer updatedFlexContainer = (DeviceLightFlexContainer) response.getContent();
			if (updatedFlexContainer.getCustomAttributes().size() != 1) {
				createTestReport("testUpdateLightFlexContainer", Status.KO, "expecting 1 CustomAttribute, found "
						+ updatedFlexContainer.getCustomAttributes().size() + " CustomAttribute", null);
				return;
			}

			if (!updatedFlexContainer.getCustomAttribute("illuminanceLevel").getValue()
					.equals(illuminanceToBeUpdated.getValue())) {
				createTestReport("testUpdateLightFlexContainer", Status.KO,
						"wrong illuminanceLevel CustomAttribute value, expecting: "
								+ illuminanceToBeUpdated.getValue() + ", found: "
								+ updatedFlexContainer.getCustomAttribute("illuminanceLevel").getValue(),
						null);
				return;
			}
		}
		
		// send a RETRIEVE request
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testUpdateLightFlexContainer", Status.KO,
					"unable to retrieve LightFlexContainer:" + response.getContent(), null);
			return;
		} else {
			// OK
			DeviceLightFlexContainer retrievedFlexContainer = (DeviceLightFlexContainer) response.getContent();
			createdFlexContainer.getCustomAttribute("illuminanceLevel").setValue("85");
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testUpdateLightFlexContainer", Status.KO,
						e.getMessage(), e);
				return;
			}
		}

		createTestReport("testUpdateLightFlexContainer", Status.OK, null, null);
	}

}
