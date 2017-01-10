/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite.module;

import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.testsuite.CSEUtil;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport.State;
import org.eclipse.om2m.ipe.sdt.testsuite.module.exception.FlexContainerNotFound;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class RunModeModuleTest extends AbstractModuleTest {

	private String moduleUrl = null;

	public RunModeModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);

		try {
			moduleUrl = getModuleFlexContainerUrl();
		} catch (FlexContainerNotFound e) {
		}
	}

	public TestReport testGetOperationMode() {
		TestReport report = new TestReport("test " + getModule().getName() + ".getOperationMode");

		if (moduleUrl == null) {
			report.setState(State.KO);
			report.setErrorMessage("unable to find out FlexContainer for module " + getModule().getName());
			return report;
		}

		// at this point, we are sure the Module Flexcontainer exist
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer for module " + getModule().getName() + " : "
					+ response.getContent());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

		// get operationMode customAttribute
		CustomAttribute operationModeCA = retrievedFlexContainer.getCustomAttribute("operationMode");
		if (operationModeCA == null) {
			report.setErrorMessage("operationMode customAttribute does not exist");
			report.setState(State.KO);
			return report;
		}

		// get operationMode value from customAttribute
		List<String> operationModeFromFlexContainer = getListFromStringArray(operationModeCA.getCustomAttributeValue());

		// get operationMode value from Datapoint
		ArrayDataPoint<String> operationModeDP = (ArrayDataPoint<String>) getModule().getDataPoint("operationMode");
		List<String> operationModeFromDP = null;
		try {
			operationModeFromDP = operationModeDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to get operationMode value from Datapoint");
			report.setState(State.KO);
			return report;
		}

		// check value from flexContainer and from Datapoint
		if (!checkObject(operationModeFromFlexContainer, operationModeFromDP, report, "operationMode")) {
			return report;
		}

		report.setState(State.OK);
		return report;
	}

	public TestReport testSetOperationMode() {
		TestReport report = new TestReport("test " + getModule().getName() + ".setOperationMode");

		if (moduleUrl == null) {
			report.setState(State.KO);
			report.setErrorMessage("unable to find out FlexContainer for module " + getModule().getName());
			return report;
		}

		// at this point, we are sure the Module Flexcontainer exist

		// get possible values from supportedMode datapoint
		ArrayDataPoint<String> supportedModesDP = (ArrayDataPoint<String>) getModule().getDataPoint("supportedModes");
		List<String> supportedModesFromDP;
		try {
			supportedModesFromDP = supportedModesDP.getValue();
		} catch (DataPointException | AccessException e1) {
			report.setErrorMessage("unable to retrieve value of supportedModes Datapoint: " + e1.getMessage());
			report.setState(State.KO);
			return report;
		}

		// get current operationMode
		ArrayDataPoint<String> operationModeDP = (ArrayDataPoint<String>) getModule().getDataPoint("operationMode");
		List<String> currentOperationModeValue = null;
		try {
			currentOperationModeValue = operationModeDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setState(State.KO);
			report.setErrorMessage("unable to retrieve current value of operationMode datapoint");
			return report;
		}

		// find a new value
		String newOperationModeValue = "";
		for (String supportedMode : supportedModesFromDP) {
			if (!currentOperationModeValue.contains(supportedMode)) {
				newOperationModeValue += supportedMode;
				break;
			}
		}
		newOperationModeValue += "";

		// set operationMode value
		FlexContainer toBeUpdated = new FlexContainer();
		CustomAttribute customAttribute = new CustomAttribute();
		customAttribute.setCustomAttributeName("operationMode");
		customAttribute.setCustomAttributeType("xs:enum");
		customAttribute.setCustomAttributeValue(newOperationModeValue.toString());
		toBeUpdated.getCustomAttributes().add(customAttribute);

		// perform UPDATE request
		ResponsePrimitive response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to update FlexContainer for module " + getModule().getName() + " : "
					+ response.getContent());
			report.setState(State.KO);
			return report;
		}

		// perform RETRIEVE request and check value
		response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

		// get operationMode customAttribute and value
		CustomAttribute operationModeCA = retrievedFlexContainer.getCustomAttribute("operationMode");
		String operationModeFromFlexContainer = operationModeCA.getCustomAttributeValue();
		// remove first character and last character "[]"
		operationModeFromFlexContainer = operationModeFromFlexContainer.substring(1,
				operationModeFromFlexContainer.length() - 1);
		if (!checkObject(newOperationModeValue, operationModeFromFlexContainer, report, "operationMode")) {
			return report;
		}

		// set an unknow value
		String newOperationModeWrongValue = "unknownValue_" + System.currentTimeMillis();
		toBeUpdated = new FlexContainer();
		customAttribute.setCustomAttributeName("operationMode");
		customAttribute.setCustomAttributeType("xs:enum");
		customAttribute.setCustomAttributeValue(newOperationModeWrongValue);
		toBeUpdated.getCustomAttributes().add(customAttribute);

		// perform UPDATE request
		response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("expected KO on UPDATE request as operationMode value is wrong");
			report.setState(State.KO);
			return report;
		}

		// perform RETRIEVE request and check value did not change
		response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		retrievedFlexContainer = (FlexContainer) response.getContent();

		// get operationMode customAttribute and value
		operationModeCA = retrievedFlexContainer.getCustomAttribute("operationMode");
		operationModeFromFlexContainer = operationModeCA.getCustomAttributeValue();
		// remove first character and last character "[]"
		operationModeFromFlexContainer = operationModeFromFlexContainer.substring(1,
				operationModeFromFlexContainer.length() - 1);
		if (!checkObject(newOperationModeValue, operationModeFromFlexContainer, report, "operationMode")) {
			return report;
		}

		report.setState(State.OK);
		return report;
	}

	public TestReport testGetSupportedModes() {
		TestReport report = new TestReport("test " + getModule().getName() + ".getSupportedModes");

		if (moduleUrl == null) {
			report.setState(State.KO);
			report.setErrorMessage("unable to find out FlexContainer for module " + getModule().getName());
			return report;
		}
		// at this point, we are sure the Module Flexcontainer exist

		// perform RETRIEVE request on module FlexContainer
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer of module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

		// check supportedModes customAttribute exist
		CustomAttribute supportedModesCA = retrievedFlexContainer.getCustomAttribute("supportedModes");
		if (supportedModesCA == null) {
			report.setErrorMessage("supportedModes customAttribute does not exist");
			report.setState(State.KO);
			return report;
		}

		// get value from customAttribute
		List<String> supportedModesListFromFlexContainer = getListFromStringArray(
				supportedModesCA.getCustomAttributeValue());

		// get value from datapoint
		ArrayDataPoint<String> supportedModesDP = (ArrayDataPoint<String>) getModule().getDataPoint("supportedModes");
		List<String> supportedModesListFromDP = null;
		try {
			supportedModesListFromDP = supportedModesDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve supportedModes Datapoint value: " + e.getMessage());
			report.setState(State.KO);
			return report;
		}

		// check values between flexContainer and dp
		// first check list size
		if (supportedModesListFromDP.size() != supportedModesListFromFlexContainer.size()) {
			report.setErrorMessage("supportedModes size from Datapoint different from the list from flexContainer");
			report.setState(State.KO);
			return report;
		}
		for (String s : supportedModesListFromDP) {
			if (!supportedModesListFromFlexContainer.contains(s)) {
				report.setErrorMessage(s + " mode is not in FlexContainer list");
				report.setState(State.KO);
				return report;
			}
		}

		report.setState(State.OK);
		return report;
	}

	public TestReport testSetSupportedModes() {
		TestReport report = new TestReport("test " + getModule().getName() + ".setSupportedModes");

		if (moduleUrl == null) {
			report.setState(State.KO);
			report.setErrorMessage("unable to find out FlexContainer for module " + getModule().getName());
			return report;
		}
		// at this point, we are sure the Module Flexcontainer exist
		
		CustomAttribute supportedModesCA = new CustomAttribute();
		supportedModesCA.setCustomAttributeName("supportedModes");
		supportedModesCA.setCustomAttributeType("xs:enum");
		supportedModesCA.setCustomAttributeValue("mode2,mode3,mode6,mode7");
		
		FlexContainer toBeUpdated = new FlexContainer();
		toBeUpdated.getCustomAttributes().add(supportedModesCA);
		
		// perform UPDATE request
		ResponsePrimitive response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to update supportedModes customAttribute:" + response.getContent());
			report.setState(State.KO);
			return report;
		}
		
		// retrieve value from datapoint
		ArrayDataPoint<String> supportedModesDP = (ArrayDataPoint<String>) getModule().getDataPoint("supportedModes");
		List<String> supportedModesFromDP = null;
		try {
			supportedModesFromDP = supportedModesDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve supportedModes value from datapoint:" + e.getMessage());
			report.setException(e);
			report.setState(State.KO);
			return report;
		}
		
		// convert customAttribute as a list
		String[] supportedModesValueFromFlexContainer_array = supportedModesCA.getCustomAttributeValue().split(",");
		List<String> supportedModesValueFromFlexContainer_list = Arrays.asList(supportedModesValueFromFlexContainer_array);
		
		// check value
		if (!checkObject(supportedModesValueFromFlexContainer_list, supportedModesFromDP, report, "supportedModes")) {
			return report;
		}
		
		report.setState(State.OK);
		return report;
	}
}
