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
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceWarningFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class WarningDeviceFlexContainerTest extends FlexContainerTestSuite {

	public WarningDeviceFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "WarningDeviceFlexContainerTest";
	}

	public void testCreateAndRetrieveWarningDeviceFlexContainer() {
		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "SirenFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		DeviceWarningFlexContainer flexContainer = new DeviceWarningFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OntologyRef");

		CustomAttribute serialNumberCA = new CustomAttribute();
		serialNumberCA.setShortName("pDSNm");
		serialNumberCA.setValue("sn1");
		flexContainer.getCustomAttributes().add(serialNumberCA);

		CustomAttribute locationCA = new CustomAttribute();
		locationCA.setShortName("proLn");
		locationCA.setValue("kitchen");
		flexContainer.getCustomAttributes().add(locationCA);

		CustomAttribute deviceManufacturerCA = new CustomAttribute();
		deviceManufacturerCA.setShortName("prDMr");
		deviceManufacturerCA.setValue("Orange");
		flexContainer.getCustomAttributes().add(deviceManufacturerCA);

		CustomAttribute protocolCA = new CustomAttribute();
		protocolCA.setShortName("proPl");
		protocolCA.setValue("ZigBee");
		flexContainer.getCustomAttributes().add(protocolCA);

		CustomAttribute deviceModelCA = new CustomAttribute();
		deviceModelCA.setShortName("pDMNe");
		deviceModelCA.setValue("Model1");
		flexContainer.getCustomAttributes().add(deviceModelCA);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		DeviceWarningFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateAndRetrieveWarningDeviceFlexContainer", Status.KO,
					"unable to create WarningDevice FlexContainer:" + response.getContent(), null);
			return;
		} else {
			createdFlexContainer = (DeviceWarningFlexContainer) response.getContent();

			if (!flexContainerName.equals(createdFlexContainer.getName())) {
				createTestReport("testCreateAndRetrieveWarningDeviceFlexContainer", Status.KO,
						"invalid name.Expecting: " + flexContainerName + ", found:" + createdFlexContainer.getName(),
						null);
				return;
			}

			try {
				checkFlexContainerCreator(flexContainer, createdFlexContainer);
				checkFlexContainerCustomAttribute(flexContainer, createdFlexContainer);
				checkFlexContainerDefinition(flexContainer, createdFlexContainer);
				checkFlexContainerOntologyRef(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveWarningDeviceFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}
		}

		// send RETRIEVE request
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testCreateAndRetrieveWarningDeviceFlexContainer", Status.KO,
					"unable to retrieve Warning Device FlexContainer:" + response.getContent(), null);
			return;
		} else {
			DeviceWarningFlexContainer retrievedFlexContainer = (DeviceWarningFlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveWarningDeviceFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}
		}

		createTestReport("testCreateAndRetrieveWarningDeviceFlexContainer", Status.OK, null, null);

	}

	public void testDeleteWarningDeviceFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "WarningDeviceFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		DeviceWarningFlexContainer flexContainer = new DeviceWarningFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OntologyRef");

		CustomAttribute serialNumberCA = new CustomAttribute();
		serialNumberCA.setShortName("pDSNm");
		serialNumberCA.setValue("sn1");
		flexContainer.getCustomAttributes().add(serialNumberCA);

		CustomAttribute locationCA = new CustomAttribute();
		locationCA.setShortName("proLn");
		locationCA.setValue("kitchen");
		flexContainer.getCustomAttributes().add(locationCA);

		CustomAttribute deviceManufacturerCA = new CustomAttribute();
		deviceManufacturerCA.setShortName("prDMr");
		deviceManufacturerCA.setValue("Orange");
		flexContainer.getCustomAttributes().add(deviceManufacturerCA);

		CustomAttribute protocolCA = new CustomAttribute();
		protocolCA.setShortName("proPl");
		protocolCA.setValue("ZigBee");
		flexContainer.getCustomAttributes().add(protocolCA);

		CustomAttribute deviceModelCA = new CustomAttribute();
		deviceModelCA.setShortName("pDMNe");
		deviceModelCA.setValue("Model1");
		flexContainer.getCustomAttributes().add(deviceModelCA);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteWarningDeviceFlexContainer", Status.KO,
					"unable to create WarningDevice FlexContainer:" + response.getContent(), null);
			return;
		}

		// send DELETE request
		response = sendDeleteRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteWarningDeviceFlexContainer", Status.KO,
					"unable to delete WarningDevice FlexContainer:" + response.getContent(), null);
			return;
		}

		// send RETRIEVE request ==> NOT_FOUND
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteWarningDeviceFlexContainer", Status.KO,
					"Expecting:" + ResponseStatusCode.NOT_FOUND + ", received:" + response.getResponseStatusCode(),
					null);
			return;
		}

		createTestReport("testDeleteWarningDeviceFlexContainer", Status.OK, null, null);
	}

}
