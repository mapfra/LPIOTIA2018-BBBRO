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

public class EnergyConsumptionFlexContainerTest extends FlexContainerTestSuite {

	public EnergyConsumptionFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "EnergyConsumptionFlexContainer";
	}

	public void testCreateAndRetrieveEnergyConsumptionFlexContainer() {
		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "EnergyConsumptionFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.energyconsumption");
		flexContainer.setOntologyRef("Ontology");
		flexContainer.setCreator("greg");

		CustomAttribute powerCustomAttribute = new CustomAttribute();
		powerCustomAttribute.setCustomAttributeName("power");
		powerCustomAttribute.setCustomAttributeType("xs:float");
		powerCustomAttribute.setCustomAttributeValue("342");
		flexContainer.getCustomAttributes().add(powerCustomAttribute);

		CustomAttribute absoluteEnergyConsumptionDataCustomAttribute = new CustomAttribute();
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeName("absoluteEnergyConsumption");
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeType("xs:float");
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeValue("3434");
		flexContainer.getCustomAttributes().add(absoluteEnergyConsumptionDataCustomAttribute);

		CustomAttribute roundingEnergyConsumptionDataCustomAttribute = new CustomAttribute();
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeName("roundingEnergyConsumption");
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeType("xs:integer");
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeValue("43242");
		flexContainer.getCustomAttributes().add(roundingEnergyConsumptionDataCustomAttribute);

		CustomAttribute significantFigures = new CustomAttribute();
		significantFigures.setCustomAttributeName("significantDigits");
		significantFigures.setCustomAttributeType("xs:integer");
		significantFigures.setCustomAttributeValue("3");
		flexContainer.getCustomAttributes().add(significantFigures);

		CustomAttribute multiplyingFactors = new CustomAttribute();
		multiplyingFactors.setCustomAttributeName("multiplyingFactors");
		multiplyingFactors.setCustomAttributeType("xs:integer");
		multiplyingFactors.setCustomAttributeValue("100");
		flexContainer.getCustomAttributes().add(multiplyingFactors);

		CustomAttribute voltage = new CustomAttribute();
		voltage.setCustomAttributeName("voltage");
		voltage.setCustomAttributeType("xs:float");
		voltage.setCustomAttributeValue("3443");
		flexContainer.getCustomAttributes().add(voltage);

		CustomAttribute current = new CustomAttribute();
		current.setCustomAttributeName("current");
		current.setCustomAttributeType("xs:float");
		current.setCustomAttributeValue("45353");
		flexContainer.getCustomAttributes().add(current);

		CustomAttribute frequency = new CustomAttribute();
		frequency.setCustomAttributeName("frequency");
		frequency.setCustomAttributeType("xs:float");
		frequency.setCustomAttributeValue("34");
		flexContainer.getCustomAttributes().add(frequency);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateAndRetrieveEnergyConsumption", Status.KO,
					"unable to create EnergyConsumption FlexContainer: " + response.getContent(), null);

			return;
		} else {
			createdFlexContainer = (FlexContainer) response.getContent();

			if (!flexContainerName.equals(createdFlexContainer.getName())) {
				createTestReport("testCreateAndRetrieveEnergyConsumption", Status.KO,
						"wrong name. Expecting: " + flexContainerName + ", found:" + createdFlexContainer.getName(),
						null);
				return;
			}

			try {
				checkFlexContainerCreator(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveEnergyConsumption", Status.KO, e.getMessage(), e);
				return;
			}

			try {
				checkFlexContainerCustomAttribute(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveEnergyConsumption", Status.KO, e.getMessage(), e);
				return;
			}

			try {
				checkFlexContainerDefinition(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveEnergyConsumption", Status.KO, e.getMessage(), e);
				return;
			}

			try {
				checkFlexContainerOntologyRef(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveEnergyConsumption", Status.KO, e.getMessage(), e);
				return;
			}

		}

		// retrieve FlexContainer
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testCreateAndRetrieveEnergyConsumption", Status.KO,
					"unable to retrieve EnergyConsumption FlexContainer: " + response.getContent(), null);

			return;
		} else {
			// OK
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testCreateAndRetrieveEnergyConsumption", Status.KO, e.getMessage(), e);
				return;
			}
		}

		createTestReport("testCreateAndRetrieveEnergyConsumptionFlexContainer", Status.OK, null, null);
	}

	public void testDeleteEnergyConsumptionFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "EnergyConsumptionFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.energyconsumption");
		flexContainer.setOntologyRef("Ontology");
		flexContainer.setCreator("greg");

		CustomAttribute powerCustomAttribute = new CustomAttribute();
		powerCustomAttribute.setCustomAttributeName("power");
		powerCustomAttribute.setCustomAttributeType("xs:float");
		powerCustomAttribute.setCustomAttributeValue("342");
		flexContainer.getCustomAttributes().add(powerCustomAttribute);

		CustomAttribute absoluteEnergyConsumptionDataCustomAttribute = new CustomAttribute();
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeName("absoluteEnergyConsumption");
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeType("xs:float");
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeValue("3434");
		flexContainer.getCustomAttributes().add(absoluteEnergyConsumptionDataCustomAttribute);

		CustomAttribute roundingEnergyConsumptionDataCustomAttribute = new CustomAttribute();
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeName("roundingEnergyConsumption");
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeType("xs:integer");
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeValue("43242");
		flexContainer.getCustomAttributes().add(roundingEnergyConsumptionDataCustomAttribute);

		CustomAttribute significantFigures = new CustomAttribute();
		significantFigures.setCustomAttributeName("significantDigits");
		significantFigures.setCustomAttributeType("xs:integer");
		significantFigures.setCustomAttributeValue("3");
		flexContainer.getCustomAttributes().add(significantFigures);

		CustomAttribute multiplyingFactors = new CustomAttribute();
		multiplyingFactors.setCustomAttributeName("multiplyingFactors");
		multiplyingFactors.setCustomAttributeType("xs:integer");
		multiplyingFactors.setCustomAttributeValue("100");
		flexContainer.getCustomAttributes().add(multiplyingFactors);

		CustomAttribute voltage = new CustomAttribute();
		voltage.setCustomAttributeName("voltage");
		voltage.setCustomAttributeType("xs:float");
		voltage.setCustomAttributeValue("3443");
		flexContainer.getCustomAttributes().add(voltage);

		CustomAttribute current = new CustomAttribute();
		current.setCustomAttributeName("current");
		current.setCustomAttributeType("xs:float");
		current.setCustomAttributeValue("45353");
		flexContainer.getCustomAttributes().add(current);

		CustomAttribute frequency = new CustomAttribute();
		frequency.setCustomAttributeName("frequency");
		frequency.setCustomAttributeType("xs:float");
		frequency.setCustomAttributeValue("34");
		flexContainer.getCustomAttributes().add(frequency);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteEnergyConsumptionFlexContainer", Status.KO,
					"unable to create EnergyConsumption FlexContainer: " + response.getContent(), null);

			return;
		}

		// send a DELETE request
		response = sendDeleteRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.DELETED)) {
			// KO
			createTestReport("testDeleteEnergyConsumptionFlexContainer", Status.KO,
					"unable to delete EnergyConsumption FlexContainer: " + response.getContent(), null);

			return;
		}

		// send a RETRIEVE request ==> NOT_FOUND
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.NOT_FOUND)) {
			// KO
			createTestReport("testDeleteEnergyConsumptionFlexContainer", Status.KO,
					"expecting: " + ResponseStatusCode.NOT_FOUND + ", found:" + response.getResponseStatusCode(), null);

			return;
		}

		createTestReport("testDeleteEnergyConsumptionFlexContainer", Status.OK, null, null);
	}

	public void testUpdateEnergyConsumptionFlexContainer() {

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String flexContainerName = "EnergyConsumptionFlexContainer_" + System.currentTimeMillis();
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.energyconsumption");
		flexContainer.setOntologyRef("Ontology");
		flexContainer.setCreator("greg");

		CustomAttribute powerCustomAttribute = new CustomAttribute();
		powerCustomAttribute.setCustomAttributeName("power");
		powerCustomAttribute.setCustomAttributeType("xs:float");
		powerCustomAttribute.setCustomAttributeValue("342");
		flexContainer.getCustomAttributes().add(powerCustomAttribute);

		CustomAttribute absoluteEnergyConsumptionDataCustomAttribute = new CustomAttribute();
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeName("absoluteEnergyConsumption");
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeType("xs:float");
		absoluteEnergyConsumptionDataCustomAttribute.setCustomAttributeValue("3434");
		flexContainer.getCustomAttributes().add(absoluteEnergyConsumptionDataCustomAttribute);

		CustomAttribute roundingEnergyConsumptionDataCustomAttribute = new CustomAttribute();
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeName("roundingEnergyConsumption");
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeType("xs:integer");
		roundingEnergyConsumptionDataCustomAttribute.setCustomAttributeValue("43242");
		flexContainer.getCustomAttributes().add(roundingEnergyConsumptionDataCustomAttribute);

		CustomAttribute significantFigures = new CustomAttribute();
		significantFigures.setCustomAttributeName("significantDigits");
		significantFigures.setCustomAttributeType("xs:integer");
		significantFigures.setCustomAttributeValue("3");
		flexContainer.getCustomAttributes().add(significantFigures);

		CustomAttribute multiplyingFactors = new CustomAttribute();
		multiplyingFactors.setCustomAttributeName("multiplyingFactors");
		multiplyingFactors.setCustomAttributeType("xs:integer");
		multiplyingFactors.setCustomAttributeValue("100");
		flexContainer.getCustomAttributes().add(multiplyingFactors);

		CustomAttribute voltage = new CustomAttribute();
		voltage.setCustomAttributeName("voltage");
		voltage.setCustomAttributeType("xs:float");
		voltage.setCustomAttributeValue("3443");
		flexContainer.getCustomAttributes().add(voltage);

		CustomAttribute current = new CustomAttribute();
		current.setCustomAttributeName("current");
		current.setCustomAttributeType("xs:float");
		current.setCustomAttributeValue("45353");
		flexContainer.getCustomAttributes().add(current);

		CustomAttribute frequency = new CustomAttribute();
		frequency.setCustomAttributeName("frequency");
		frequency.setCustomAttributeType("xs:float");
		frequency.setCustomAttributeValue("34");
		flexContainer.getCustomAttributes().add(frequency);

		// send CREATE request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation, flexContainerName);
		FlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testUpdateEnergyConsumptionFlexContainer", Status.KO,
					"unable to create EnergyConsumption FlexContainer: " + response.getContent(), null);

			return;
		} else {
			createdFlexContainer = (FlexContainer) response.getContent();
		}

		// prepare Update request
		FlexContainer toBeUpdated = new FlexContainer();
		CustomAttribute updatedVoltage = new CustomAttribute();
		updatedVoltage.setCustomAttributeName("voltage");
		updatedVoltage.setCustomAttributeType("xs:float");
		updatedVoltage.setCustomAttributeValue("0");
		toBeUpdated.getCustomAttributes().add(updatedVoltage);

		// send UPDATE request
		response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			// KO
			createTestReport("testUpdateEnergyConsumptionFlexContainer", Status.KO,
					"unable to update EnergyConsumption FlexContainer: " + response.getContent(), null);

			return;
		} else {
			FlexContainer updatedFlexContainer = (FlexContainer) response.getContent();
			if (updatedFlexContainer.getCustomAttributes().size() != 1) {
				createTestReport("testUpdateEnergyConsumptionFlexContainer", Status.KO,
						"expecting 1 customAttribute, found " + updatedFlexContainer.getCustomAttributes().size()
								+ " customAttributes",
						null);

				return;
			}

			if (!updatedFlexContainer.getCustomAttribute("voltage").getCustomAttributeValue()
					.equals(updatedVoltage.getCustomAttributeValue())) {
				createTestReport("testUpdateEnergyConsumptionFlexContainer", Status.KO,
						"wrong voltage customAttribute value. Expecting: " + updatedVoltage.getCustomAttributeValue()
								+ " , found: "
								+ updatedFlexContainer.getCustomAttribute("voltage").getCustomAttributeValue(),
						null);

				return;
			}
		}
		
		// retrieve the updated Flex container
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testUpdateEnergyConsumptionFlexContainer", Status.KO,
					"unable to retrieve EnergyConsumption FlexContainer: " + response.getContent(), null);

			return;
		} else {
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
			
			// prepare initial flexContainer
			createdFlexContainer.setName(flexContainerName);
			createdFlexContainer.getCustomAttribute("voltage").setCustomAttributeValue("0");
			
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport("testUpdateEnergyConsumptionFlexContainer", Status.KO,
						e.getMessage(), e);

				return;
			}
			
		}

		createTestReport("testUpdateEnergyConsumptionFlexContainer", Status.OK, null, null);
	}

}
