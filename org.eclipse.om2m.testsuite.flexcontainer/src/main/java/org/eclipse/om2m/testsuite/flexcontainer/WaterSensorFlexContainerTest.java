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
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class WaterSensorFlexContainerTest extends FlexContainerTestSuite {

	public WaterSensorFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "WaterSensorFlexContainerTest";
	}

	public void testCreateAndRetrieveWaterSensorFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "WaterSensorFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.watersensor");
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OntologyOrange");

		CustomAttribute alarmCustomAttribute = new CustomAttribute();
		alarmCustomAttribute.setCustomAttributeName("alarm");
		alarmCustomAttribute.setCustomAttributeType("xs:boolean");
		alarmCustomAttribute.setCustomAttributeValue("true");
		flexContainer.getCustomAttributes().add(alarmCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateAndRetrieveWaterSensorFlexContainer", Status.KO,
					"unable to create WaterSensor flexContainer:" + response.getContent(), null);
			return;
		} else {
			createdFlexContainer = (FlexContainer) response.getContent();

			if (!flexContainerName.equals(createdFlexContainer.getName())) {
				createTestReport("testCreateAndRetrieveWaterSensorFlexContainer", Status.KO,
						"invalid name. Expecting" + flexContainerName + ", found:" + createdFlexContainer.getName(),
						null);
				return;
			}

			try {
				checkFlexContainerCreator(flexContainer, createdFlexContainer);
				checkFlexContainerCustomAttribute(flexContainer, createdFlexContainer);
				checkFlexContainerDefinition(flexContainer, createdFlexContainer);
				checkFlexContainerOntologyRef(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveWaterSensorFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}
		}

		// send a RETRIEVE request
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testCreateAndRetrieveWaterSensorFlexContainer", Status.KO,
					"unable to retrieve WaterSensor flexContainer:" + response.getContent(), null);
			return;
		} else {
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveWaterSensorFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}
		}

		createTestReport("testCreateAndRetrieveWaterSensorFlexContainer", Status.OK, null, null);
	}

	public void testDeleteWaterSensorFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "WaterSensorFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.watersensor");
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OntologyOrange");

		CustomAttribute alarmCustomAttribute = new CustomAttribute();
		alarmCustomAttribute.setCustomAttributeName("alarm");
		alarmCustomAttribute.setCustomAttributeType("xs:boolean");
		alarmCustomAttribute.setCustomAttributeValue("true");
		flexContainer.getCustomAttributes().add(alarmCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteWaterSensorFlexContainer", Status.KO,
					"unable to create WaterSensor flexContainer:" + response.getContent(), null);
			return;
		}

		// send DELETE request
		response = sendDeleteRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteWaterSensorFlexContainer", Status.KO,
					"unable to delete WaterSensor flexContainer:" + response.getContent(), null);
			return;
		}

		// send RETRIEVE request ==> NOT_FOUND
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteWaterSensorFlexContainer", Status.KO,
					"Expecting :" + ResponseStatusCode.NOT_FOUND + ", found: " + response.getResponseStatusCode(),
					null);
			return;
		}

		createTestReport("testDeleteWaterSensorFlexContainer", Status.OK, null, null);
	}

	public void testUpdateWaterSensorFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "WaterSensorFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.watersensor");
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OntologyOrange");

		CustomAttribute alarmCustomAttribute = new CustomAttribute();
		alarmCustomAttribute.setCustomAttributeName("alarm");
		alarmCustomAttribute.setCustomAttributeType("xs:boolean");
		alarmCustomAttribute.setCustomAttributeValue("true");
		flexContainer.getCustomAttributes().add(alarmCustomAttribute);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testUpdateWaterSensorFlexContainer", Status.KO,
					"unable to create WaterSensor flexContainer:" + response.getContent(), null);
			return;
		} else {
			createdFlexContainer = (FlexContainer) response.getContent();
		}

		// prepare UPDATE request
		FlexContainer toBeUpdated = new FlexContainer();
		CustomAttribute alarmToBeUpdated = new CustomAttribute();
		alarmToBeUpdated.setCustomAttributeName("alarm");
		alarmToBeUpdated.setCustomAttributeType("xs:boolean");
		alarmToBeUpdated.setCustomAttributeValue("false");
		toBeUpdated.getCustomAttributes().add(alarmToBeUpdated);

		// send UPDATE request
		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport("testUpdateWaterSensorFlexContainer", Status.KO,
					"unable to update WaterSensor flexContainer:" + response.getContent(), null);
			return;
		} else {
			FlexContainer updatedFlexContainer = (FlexContainer) response.getContent();

			if (updatedFlexContainer.getCustomAttributes().size() != 1) {
				createTestReport("testUpdateWaterSensorFlexContainer", Status.KO, "Expecting 1 customAttribute, found "
						+ updatedFlexContainer.getCustomAttributes().size() + " customAttributes", null);
				return;
			}

			if (!alarmToBeUpdated.getCustomAttributeValue()
					.equals(updatedFlexContainer.getCustomAttribute("alarm").getCustomAttributeValue())) {
				createTestReport("testUpdateWaterSensorFlexContainer", Status.KO,
						"Wrong alarm customAttribute value. Expecting:" + alarmToBeUpdated.getCustomAttributeValue()
								+ ", found:"
								+ updatedFlexContainer.getCustomAttribute("alarm").getCustomAttributeValue(),
						null);
				return;
			}
		}

		// retrieve the updated flexContainer
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testUpdateWaterSensorFlexContainer", Status.KO,
					"unable to retrieve WaterSensor flexContainer:" + response.getContent(), null);
			return;
		} else {
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
			
			// apply update on createdFlexContainer
			createdFlexContainer.getCustomAttribute("alarm").setCustomAttributeValue("false");
			
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testUpdateWaterSensorFlexContainer", Status.KO,
						e.getMessage(), e);
				return;
			}
		}

		createTestReport("testUpdateWaterSensorFlexContainer", Status.OK, null, null);
	}

}
