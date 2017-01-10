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
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;
import org.eclipse.om2m.sdt.home.types.AlertColourCode;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class AlarmSpeakerModuleTest extends AbstractModuleTest {

	public AlarmSpeakerModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);
	}

	public TestReport testGetTone() {
		TestReport report = new TestReport("test " + getModule().getName() + ".getTone");

		if (!checkModuleUrl(report)) {
			// unable to find out the url of the module
			return report;
		}

		// retrieve alarmSpeaker flexContainer
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), getModuleUrl());
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve flexContainer of module " + getModule().getName() + ": "
					+ response.getContent());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

		// retrieve tone customAttribute
		CustomAttribute toneCA = retrievedFlexContainer.getCustomAttribute("tone");

		// tone is optional
		if (toneCA == null) {
			// tone datapoint should not exist
			if (getModule().getDataPoint("tone") != null) {
				report.setErrorMessage("tone customAttribute does not exist whereas tone Datapoint exists");
				report.setState(State.KO);
				return report;
			}
		} else {
			// toneCA exist ==> check value
			Integer toneValueFromFlexContainer = null;
			try {
				toneValueFromFlexContainer = Integer.parseInt(toneCA.getCustomAttributeValue());
			} catch (NumberFormatException e) {
				report.setErrorMessage(
						"NumberFormatException on toneValue from FlexContainer:" + toneCA.getCustomAttributeValue());
				report.setException(e);
				report.setState(State.KO);
				return report;
			}

			EnumDataPoint<Integer> toneDP = (EnumDataPoint<Integer>) getModule().getDataPoint("tone");
			Integer toneValueFromDP = null;
			try {
				toneValueFromDP = toneDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve value tone: " + e.getMessage());
				report.setState(State.KO);
				return report;
			}

			// check value between flexContainer and DP
			if (!checkObject(toneValueFromFlexContainer, toneValueFromDP, report, "tone")) {
				report.setErrorMessage("tone value from DataPoint and tone value from FlexContainer are different");
				report.setState(State.KO);
				return report;
			}

		}

		report.setState(State.OK);
		return report;
	}

	public TestReport testSetTone() {
		TestReport report = new TestReport("test " + getModule() + ".setTone");

		if (!checkModuleUrl(report)) {
			return report;
		}

		CustomAttribute toneCA;
		FlexContainer toBeUpdated;
		ResponsePrimitive response;

		// retrieve tone datapoint
		EnumDataPoint<Integer> toneDP = (EnumDataPoint<Integer>) getModule().getDataPoint("tone");
		if (toneDP != null) {
			// tone datapoint exist

			// retrieve current value from Datapoint
			Integer currentToneValueFromDP = null;
			try {
				currentToneValueFromDP = toneDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve tone datapoint value :" + e.getMessage());
				report.setState(State.KO);
				return report;
			}

			// based on the current toneValue, choose a new tone value (between
			// 1 -> 5)
			Integer possibleValue = (int) Math.round(Math.random() * 5d);
			while (possibleValue.equals(currentToneValueFromDP)) {
				possibleValue = (int) Math.round(Math.random() * 5d);
			}

			// at this point, we are sure we have a new ton value different from
			// the current one.

			// set toneCA
			toneCA = new CustomAttribute();
			toneCA.setCustomAttributeName("tone");
			toneCA.setCustomAttributeType("hd:tone");
			toneCA.setCustomAttributeValue(possibleValue.toString());

			// flexcontainer
			toBeUpdated = new FlexContainer();
			toBeUpdated.getCustomAttributes().add(toneCA);

			// perform UPDATE request
			response = CSEUtil.updateFlexContainerEntity(getCseService(), getModuleUrl(), toBeUpdated);
			if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
				// the request fails.
				report.setErrorMessage("Expecte UPDATED state: " + response.getContent());
				report.setState(State.KO);
				return report;
			}

			// retrieve tone value from datapoint.
			try {
				currentToneValueFromDP = toneDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve tone datapoint value :" + e.getMessage());
				report.setState(State.KO);
				return report;
			}

			// check value
			if (!checkObject(possibleValue, currentToneValueFromDP, report, "tone")) {
				return report;
			}

		} else {
			// tone datapoint does not exist

			// set toneCA
			toneCA = new CustomAttribute();
			toneCA.setCustomAttributeName("tone");
			toneCA.setCustomAttributeType("hd:tone");
			toneCA.setCustomAttributeValue("1"); // fire

			// flexcontainer
			toBeUpdated = new FlexContainer();
			toBeUpdated.getCustomAttributes().add(toneCA);

			// perform UPDATE request
			response = CSEUtil.updateFlexContainerEntity(getCseService(), getModuleUrl(), toBeUpdated);
			if (ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
				// as toneDP does not exist, the UPDATE request MUST fail.
				report.setErrorMessage("Expected UPDATE request failure: " + response.getContent());
				report.setState(State.KO);
				return report;
			}
		}

		report.setState(State.OK);
		return report;
	}

	public TestReport testGetAlarmStatus() {
		TestReport report = new TestReport("Test " + getModule().getName() + ".getAlarmStatus");

		if (!checkModuleUrl(report)) {
			return report;
		}

		// retrieve module FlexContainer
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), getModuleUrl());
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve flexContainer for module " + getModule().getName() + ": "
					+ response.getContent());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

		// get alarmStatus customAttribute
		CustomAttribute alarmStatusCA = retrievedFlexContainer.getCustomAttribute("alarmStatus");

		if (alarmStatusCA == null) {
			if (getModule().getDataPoint("alarmStatus") != null) {
				// data point exists but no customAttribute
				report.setErrorMessage(
						"alarmStatus Datapoint exists wheres alarmStatus customAttribute does not exist");
				report.setState(State.KO);
				return report;
			}
		} else {
			// retrieve alarmStatus datapoint value
			BooleanDataPoint alarmStatusDP = (BooleanDataPoint) getModule().getDataPoint("alarmStatus");
			Boolean alarmStatusValueFromDP = null;
			try {
				alarmStatusValueFromDP = alarmStatusDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve value of alarmStatus Datapoint:" + e.getMessage());
				report.setState(State.KO);
				return report;
			}

			// retrieve alarmStatus value from FlexContainer
			Boolean alarmStatusValueFromFlexContainer = Boolean.valueOf(alarmStatusCA.getCustomAttributeValue());

			if (!checkObject(alarmStatusValueFromDP, alarmStatusValueFromFlexContainer, report, "alarmStatus")) {
				return report;
			}

		}

		report.setState(State.OK);
		return report;
	}

	public TestReport testSetAlarmStatus() {
		TestReport report = new TestReport("test " + getModule().getName() + ".setAlarmStatus");

		if (!checkModuleUrl(report)) {
			return report;
		}
		// at this point, we are sure the module FlexContainer exist

		// retrieve alarmStatus datapoint
		BooleanDataPoint alarmStatusDP = (BooleanDataPoint) getModule().getDataPoint("alarmStatus");

		// retrieve current alarmStatus value from datapoint
		Boolean alarmStatusValueFromDP = null;
		try {
			alarmStatusValueFromDP = alarmStatusDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve alarmStatus datapoint:" + e.getMessage());
			report.setState(State.KO);
			return report;
		}

		// new alarmstatus value
		Boolean newAlarmStatusValue = Boolean.valueOf(!alarmStatusValueFromDP.booleanValue());

		// prepare request
		CustomAttribute alarmStatusCA = new CustomAttribute();
		alarmStatusCA.setCustomAttributeName("alarmStatus");
		alarmStatusCA.setCustomAttributeType("xs:boolean");
		alarmStatusCA.setCustomAttributeValue(newAlarmStatusValue.toString());
		FlexContainer toBeUpdated = new FlexContainer();
		toBeUpdated.getCustomAttributes().add(alarmStatusCA);

		// send UPDATE request
		ResponsePrimitive response = CSEUtil.updateFlexContainerEntity(getCseService(), getModuleUrl(), toBeUpdated);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to update alarmStatus customAttribute:" + response.getContent());
			report.setState(State.KO);
			return report;
		}

		// retrieve alarmStatus value from datapoint
		try {
			alarmStatusValueFromDP = alarmStatusDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve alarmStatus datapoint:" + e.getMessage());
			report.setState(State.KO);
			return report;
		}

		// check value
		if (!checkObject(alarmStatusValueFromDP, newAlarmStatusValue, report, "alarmStatus")) {
			return report;
		}

		report.setState(State.OK);

		return report;
	}

	public TestReport testGetLight() {
		TestReport report = new TestReport("test " + getModule().getName() + ".getLight");

		if (!checkModuleUrl(report)) {
			return report;
		}
		// at this point, we are sure the module FlexContainer exist

		// retrieve light datapoint
		AlertColourCode lightDP = (AlertColourCode) getModule().getDataPoint("light");
		if (lightDP != null) {

			Integer lightValueFromDP = null;
			try {
				lightValueFromDP = lightDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve value of light datapoint:" + e.getMessage());
				report.setException(e);
				return report;
			}

			// retrieve FlexContainer
			ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), getModuleUrl());
			if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
				report.setErrorMessage("unable to retrieve FlexContainer:" + response.getContent());
				report.setState(State.KO);
				return report;
			}
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

			// retrieve light customAttribute
			CustomAttribute lightCA = retrievedFlexContainer.getCustomAttribute("light");
			if (lightCA == null) {
				report.setErrorMessage("no light customAttribute but light datapoint exists");
				report.setState(State.KO);
				return report;
			}

			// retrieve value from customAttribute
			Integer lightValueFromFlexContainer = null;
			try {
				lightValueFromFlexContainer = Integer.parseInt(lightCA.getCustomAttributeValue());
			} catch (NumberFormatException e) {
				report.setErrorMessage("unable to cast light customAttribute value ("
						+ lightCA.getCustomAttributeValue() + ") as an Integer");
				report.setState(State.KO);
				return report;
			}

			if (!checkObject(lightValueFromDP, lightValueFromFlexContainer, report, "light")) {
				return report;
			}

		} else {
			// light datapoint does not exist

			// retrieve FlexContainer
			ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), getModuleUrl());
			if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
				report.setErrorMessage("unable to retrieve FlexContainer:" + response.getContent());
				report.setState(State.KO);
				return report;
			}
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

			// retrieve light customAttribute
			CustomAttribute lightCA = retrievedFlexContainer.getCustomAttribute("light");
			if (lightCA != null) {
				report.setErrorMessage("no light datapoint but light customAttribute exists");
				report.setState(State.KO);
				return report;
			}
		}

		report.setState(State.OK);

		return report;
	}

	public TestReport testSetLight() {
		TestReport report = new TestReport("test " + getModule().getName() + ".SetLight");

		if (!checkModuleUrl(report)) {
			return report;
		}
		// at this point, we are sure the module FlexContainer exist

		// retrieve light datapoint
		AlertColourCode lightDP = (AlertColourCode) getModule().getDataPoint("light");
		if (lightDP != null) {
			// light datapoint exist

			// retrieve current value
			Integer lightValueFromDP = null;
			try {
				lightValueFromDP = lightDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve light DataPoint:" + e.getMessage());
				report.setException(e);
				report.setState(State.KO);
				return report;
			}

			// prepare new light value
			Integer newLightValue = (lightValueFromDP.intValue() == 1 ? 2 : 1);

			// prepare request
			FlexContainer toBeUpdated = new FlexContainer();
			CustomAttribute lightCA = new CustomAttribute();
			lightCA.setCustomAttributeName("light");
			lightCA.setCustomAttributeType("xs:enum");
			lightCA.setCustomAttributeValue(newLightValue.toString());
			toBeUpdated.getCustomAttributes().add(lightCA);

			// perform UPDATE request
			ResponsePrimitive response = CSEUtil.updateFlexContainerEntity(getCseService(), getModuleUrl(),
					toBeUpdated);
			if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
				report.setErrorMessage("fail to update light customAttribute:" + response.getContent());
				report.setState(State.KO);
				return report;
			}

			// retrieve light datapoint value
			try {
				lightValueFromDP = lightDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve light DataPoint:" + e.getMessage());
				report.setException(e);
				report.setState(State.KO);
				return report;
			}

			if (!checkObject(lightValueFromDP, newLightValue, report, "light")) {
				return report;
			}

		} else {
			// light datapoint does not exist
			
			// prepare request
			FlexContainer toBeUpdated = new FlexContainer();
			CustomAttribute lightCA = new CustomAttribute();
			lightCA.setCustomAttributeName("light");
			lightCA.setCustomAttributeType("xs:enum");
			lightCA.setCustomAttributeValue(Integer.valueOf(AlertColourCode.Red).toString());
			toBeUpdated.getCustomAttributes().add(lightCA);

			// perform UPDATE request
			ResponsePrimitive response = CSEUtil.updateFlexContainerEntity(getCseService(), getModuleUrl(),
					toBeUpdated);
			if (ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
				report.setErrorMessage("light datapoint does not exist, UPDATE must fail");
				report.setState(State.KO);
				return report;
			}

		}

		report.setState(State.OK);

		return report;
	}

}
