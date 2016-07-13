package org.eclipse.om2m.testsuite.flexcontainer;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class SmartElectricMeterFlexContainerTest extends FlexContainerTestSuite {

	public SmartElectricMeterFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "SmartElectricMeterFlexContainerTest";
	}

	public void testCreateAndRetrieveSmartElectricMeterFlexContainer() {
		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "SmartElectricMeterFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.device.devicesmartelectricmeter");
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OntologyRef");

		CustomAttribute serialNumberCA = new CustomAttribute();
		serialNumberCA.setCustomAttributeName("propDeviceSerialNum");
		serialNumberCA.setCustomAttributeType("xs:string");
		serialNumberCA.setCustomAttributeValue("sn1");
		flexContainer.getCustomAttributes().add(serialNumberCA);

		CustomAttribute locationCA = new CustomAttribute();
		locationCA.setCustomAttributeName("propLocation");
		locationCA.setCustomAttributeType("xs:string");
		locationCA.setCustomAttributeValue("kitchen");
		flexContainer.getCustomAttributes().add(locationCA);

		CustomAttribute deviceManufacturerCA = new CustomAttribute();
		deviceManufacturerCA.setCustomAttributeName("propDeviceManufacturer");
		deviceManufacturerCA.setCustomAttributeType("xs:string");
		deviceManufacturerCA.setCustomAttributeValue("Orange");
		flexContainer.getCustomAttributes().add(deviceManufacturerCA);

		CustomAttribute protocolCA = new CustomAttribute();
		protocolCA.setCustomAttributeName("propProtocol");
		protocolCA.setCustomAttributeType("xs:string");
		protocolCA.setCustomAttributeValue("ZigBee");
		flexContainer.getCustomAttributes().add(protocolCA);

		CustomAttribute deviceModelCA = new CustomAttribute();
		deviceModelCA.setCustomAttributeName("propDeviceModelName");
		deviceModelCA.setCustomAttributeType("xs:string");
		deviceModelCA.setCustomAttributeValue("Model1");
		flexContainer.getCustomAttributes().add(deviceModelCA);

		CustomAttribute measuringScopeCA = new CustomAttribute();
		measuringScopeCA.setCustomAttributeName("propMeasuringScope");
		measuringScopeCA.setCustomAttributeType("xs:string");
		measuringScopeCA.setCustomAttributeValue("Room");
		flexContainer.getCustomAttributes().add(measuringScopeCA);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateAndRetrieveSmartElectricMeterFlexContainer", Status.KO,
					"unable to create SmartElectricMeter FlexContainer:" + response.getContent(), null);
			return;
		} else {
			createdFlexContainer = (FlexContainer) response.getContent();

			if (!flexContainerName.equals(createdFlexContainer.getName())) {
				createTestReport("testCreateAndRetrieveSmartElectricMeterFlexContainer", Status.KO,
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
				createTestReport("testCreateAndRetrieveSmartElectricMeterFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}
		}

		// send RETRIEVE request
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testCreateAndRetrieveSmartElectricMeterFlexContainer", Status.KO,
					"unable to retrieve SmartElectricMeter FlexContainer:" + response.getContent(), null);
			return;
		} else {
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveSmartElectricMeterFlexContainer", Status.KO, e.getMessage(), e);
				return;
			}
		}

		createTestReport("testCreateAndRetrieveSmartElectricMeterFlexContainer", Status.OK, null, null);
	}

	public void testDeleteSmartElectricMeterFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "SmartElectricMeterFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.device.devicesmartelectricmeter");
		flexContainer.setCreator("Greg");
		flexContainer.setOntologyRef("OntologyRef");

		CustomAttribute serialNumberCA = new CustomAttribute();
		serialNumberCA.setCustomAttributeName("propDeviceSerialNum");
		serialNumberCA.setCustomAttributeType("xs:string");
		serialNumberCA.setCustomAttributeValue("sn1");
		flexContainer.getCustomAttributes().add(serialNumberCA);

		CustomAttribute locationCA = new CustomAttribute();
		locationCA.setCustomAttributeName("propLocation");
		locationCA.setCustomAttributeType("xs:string");
		locationCA.setCustomAttributeValue("kitchen");
		flexContainer.getCustomAttributes().add(locationCA);

		CustomAttribute deviceManufacturerCA = new CustomAttribute();
		deviceManufacturerCA.setCustomAttributeName("propDeviceManufacturer");
		deviceManufacturerCA.setCustomAttributeType("xs:string");
		deviceManufacturerCA.setCustomAttributeValue("Orange");
		flexContainer.getCustomAttributes().add(deviceManufacturerCA);

		CustomAttribute protocolCA = new CustomAttribute();
		protocolCA.setCustomAttributeName("propProtocol");
		protocolCA.setCustomAttributeType("xs:string");
		protocolCA.setCustomAttributeValue("ZigBee");
		flexContainer.getCustomAttributes().add(protocolCA);

		CustomAttribute deviceModelCA = new CustomAttribute();
		deviceModelCA.setCustomAttributeName("propDeviceModelName");
		deviceModelCA.setCustomAttributeType("xs:string");
		deviceModelCA.setCustomAttributeValue("Model1");
		flexContainer.getCustomAttributes().add(deviceModelCA);

		CustomAttribute measuringScopeCA = new CustomAttribute();
		measuringScopeCA.setCustomAttributeName("propMeasuringScope");
		measuringScopeCA.setCustomAttributeType("xs:string");
		measuringScopeCA.setCustomAttributeValue("Room");
		flexContainer.getCustomAttributes().add(measuringScopeCA);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteSmartElectricMeterFlexContainer", Status.KO,
					"unable to create SmartElectricMeter FlexContainer:" + response.getContent(), null);
			return;
		}

		// send DELETE request
		response = sendDeleteRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteSmartElectricMeterFlexContainer", Status.KO,
					"unable to delete SmartElectricMeter FlexContainer:" + response.getContent(), null);
			return;
		}

		// send RETRIEVE request ==> NOT_FOUND
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteSmartElectricMeterFlexContainer", Status.KO,
					"Expecting:" + ResponseStatusCode.NOT_FOUND + ", received:" + response.getResponseStatusCode(),
					null);
			return;
		}

		createTestReport("testDeleteSmartElectricMeterFlexContainer", Status.OK, null, null);
	}

}
