/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite.module;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.testsuite.CSEUtil;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport.State;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class WaterSensorModuleTest extends AbstractModuleTest {

	public WaterSensorModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);

	}

	public TestReport testGetAlarm() {
		TestReport report = new TestReport("Test module " + getModule().getName() + ".getAlarm()");

		if (!checkModuleUrl(report)) {
			// module url is null
			return report;
		}

		// at this point, we are sure the module flexContainer exists
		
		// retrieve Flexcontainer
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), getModuleUrl());
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer for module " + getModule() + " : " + response.getContent());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
		
		// retrieve alarm customAttribute
		CustomAttribute alarmCA = retrievedFlexContainer.getCustomAttribute("alarm");
		if (alarmCA == null) {
			report.setErrorMessage("alarm customAttribute is missing but it is mandatory");
			report.setState(State.KO);
			return report;
		}

		// retrieve alarm value from flexContainer
		Boolean alarmValueFromFlexContainer = null;
		if (alarmCA.getCustomAttributeValue() == null) {
			report.setErrorMessage("alarm customAttribute value is null");
			report.setState(State.KO);
			return report;
		}
		try {
			alarmValueFromFlexContainer = Boolean.valueOf(alarmCA.getCustomAttributeValue());
		} catch (ClassCastException e) {
			report.setErrorMessage("alarm customAttribute value is not a Boolean value (" + alarmCA.getCustomAttributeValue() + ")");
			report.setState(State.KO);
			return report;
		}
		// at this point, alarmValueFromFlexContainer contains the alarm value (as a boolean) 
		
		// retrieve alarm datapoint
		BooleanDataPoint alarmDP = (BooleanDataPoint) getModule().getDataPoint("alarm");
		
		// retrieve alarm value from datapoint
		Boolean alarmValueFromDP = null;
		try {
			alarmValueFromDP = alarmDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve alarm value from Datapoint : " + e.getMessage());
			report.setState(State.KO);
			return report;
		}
		
		// check value from datapoint and flexcontainer
		if (!checkObject(alarmValueFromFlexContainer, alarmValueFromDP, report, "alarm")) {
			return report;
		}
		
		report.setState(State.OK);
		return report;
	}

	public TestReport testSetAlarm() {
		TestReport report = new TestReport("Test module " + getModule().getName() + ".setAlarm()");
		// alarm is not writable

		if (!checkModuleUrl(report)) {
			// module url is null
			return report;
		}

		// at this point, we are sure the module flexContainer exists
		
		// retrieve current value from datapoint
		BooleanDataPoint alarmDP = (BooleanDataPoint) getModule().getDataPoint("alarm");
		Boolean alarmValueFromDP = null;
		try {
			alarmValueFromDP = alarmDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve alarm DataPoint value:" + e.getMessage());
			report.setState(State.KO);
			return report;
		}
		
		// prepare update
		FlexContainer toBeUpdated = new FlexContainer();
		CustomAttribute alarmCA = new CustomAttribute();
		alarmCA.setCustomAttributeName("alarm");
		alarmCA.setCustomAttributeType("xs:boolean");
		alarmCA.setCustomAttributeValue(Boolean.valueOf(!alarmValueFromDP.booleanValue()).toString());
		toBeUpdated.getCustomAttributes().add(alarmCA);
		
		// perform UPDATE request ==> expect KO
		ResponsePrimitive response = CSEUtil.updateFlexContainerEntity(getCseService(), getModuleUrl(), toBeUpdated);
		if (ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			// expect KO as alarm is not writable
			report.setErrorMessage("alarm is not writable, KO expected");
			report.setState(State.KO);
			return report;
		}
		
		// retrieve the current value from flexContainer
		Boolean currentValueFromDP = null;
		response = CSEUtil.retrieveEntity(getCseService(), getModuleUrl());
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer: " + response.getContent());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
		CustomAttribute ca = retrievedFlexContainer.getCustomAttribute("alarm");
		try {
			currentValueFromDP = Boolean.parseBoolean(ca.getCustomAttributeValue());
		} catch (Exception e) {
			report.setErrorMessage("unable to retrieve alarm customAttribute value:" + e.getMessage());
			report.setState(State.KO);
			return report;
		}
		
		// check current value is the same value as the value retrieved before UPDATE request
		if (!checkObject(alarmValueFromDP, currentValueFromDP, report, "alarm")) {
			return report;
		}
		
		report.setState(State.OK);
		report.setErrorMessage("not implemented");
		return report;
	}
}
