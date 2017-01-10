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
import org.eclipse.om2m.ipe.sdt.testsuite.module.exception.FlexContainerNotFound;
import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class ColourModuleTest extends AbstractModuleTest {

	public ColourModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);

		// switch on
		Device device = getModule().getOwner();
		String binarySwitchModuleName = null;
		for(String moduleName : device.getModuleNames()) {
			if (moduleName.toLowerCase().contains("binaryswitch")) {
				binarySwitchModuleName = moduleName;
				break;
			}
		}
		
		if (binarySwitchModuleName != null) {
			BooleanDataPoint powerStateDP = (BooleanDataPoint) getModule().getOwner().getModule(binarySwitchModuleName)
					.getDataPoint("powerState");
			try {
				powerStateDP.setValue(Boolean.TRUE);
			} catch (DataPointException | AccessException e) {
			}
		}
	}

	public TestReport test() {
		TestReport report = new TestReport("Test module " + getModule().getName());

		String moduleUrl = null;
		try {
			moduleUrl = getModuleFlexContainerUrl();
		} catch (FlexContainerNotFound e) {
			report.setErrorMessage("unable to find out the FlexContainer of module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}

		// at this point, we are sure the module FlexContainer exist (ie we have
		// its url)

		// retrieve FlexContainer
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer :" + response.getContent());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
		CustomAttribute redCA = retrievedFlexContainer.getCustomAttribute("red");
		CustomAttribute greenCA = retrievedFlexContainer.getCustomAttribute("green");
		CustomAttribute blueCA = retrievedFlexContainer.getCustomAttribute("blue");
		Integer redValueFromFlexContainer = Integer.valueOf(redCA.getCustomAttributeValue());
		Integer greenValueFromFlexContainer = Integer.valueOf(greenCA.getCustomAttributeValue());
		Integer blueValueFromFlexContainer = Integer.valueOf(blueCA.getCustomAttributeValue());

		// get value from DataPoint
		IntegerDataPoint redDP = (IntegerDataPoint) getModule().getDataPoint("red");
		IntegerDataPoint greenDP = (IntegerDataPoint) getModule().getDataPoint("green");
		IntegerDataPoint blueDP = (IntegerDataPoint) getModule().getDataPoint("blue");

		Integer redValueFromDP = null;
		Integer greenValueFromDP = null;
		Integer blueValueFromDP = null;
		try {
			redValueFromDP = redDP.getValue();
			greenValueFromDP = greenDP.getValue();
			blueValueFromDP = blueDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve value from DataPoint:" + e.getMessage());
			report.setState(State.KO);
			return report;
		}

		// check value between FlexContainer and DP
		if (!redValueFromDP.equals(redValueFromFlexContainer)) {
			report.setErrorMessage("red value from FlexContainer (" + redValueFromFlexContainer
					+ ") is different of the value from red datapoint (" + redValueFromDP + ")");
			report.setState(State.KO);
			return report;
		}
		if (!greenValueFromDP.equals(greenValueFromFlexContainer)) {
			report.setErrorMessage("green value from FlexContainer (" + greenValueFromFlexContainer
					+ ") is different of the value from green datapoint (" + greenValueFromDP + ")");
			report.setState(State.KO);
			return report;
		}
		if (!blueValueFromDP.equals(blueValueFromFlexContainer)) {
			report.setErrorMessage("blue value from FlexContainer (" + blueValueFromFlexContainer
					+ ") is different of the value from blue datapoint (" + blueValueFromDP + ")");
			report.setState(State.KO);
			return report;
		}
		
		// set new value
		Integer newRedValue = (int) (Math.random()*255d);
		Integer newGreenValue = (int) (Math.random()*255d);
		Integer newBlueValue = (int) (Math.random()*255d);
		FlexContainer toBeUpdated = new FlexContainer();
		redCA.setCustomAttributeValue(newRedValue.toString());
		greenCA.setCustomAttributeValue(newGreenValue.toString());
		blueCA.setCustomAttributeValue(newBlueValue.toString());
		toBeUpdated.getCustomAttributes().add(redCA);
		toBeUpdated.getCustomAttributes().add(greenCA);
		toBeUpdated.getCustomAttributes().add(blueCA);
		response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to set red/green/blue customAttribute:" + response.getContent());
			report.setState(State.KO);
			return report;
		}
		
		// get value from datapoint and check with new value
		try {
			redValueFromDP = redDP.getValue();
			greenValueFromDP = greenDP.getValue();
			blueValueFromDP = blueDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve value from DataPoint:" + e.getMessage());
			report.setState(State.KO);
			return report;
		}
		if (Math.abs(newRedValue - redValueFromDP) > 2) {
//		if (!newRedValue.equals(redValueFromDP)) {
			report.setErrorMessage("new red value (" + newRedValue
					+ ") is different of the value from red datapoint (" + redValueFromDP + ")");
			report.setState(State.KO);
			return report;
		}
		if (Math.abs(newGreenValue - greenValueFromDP) > 2) {
//		if (!newGreenValue.equals(greenValueFromDP)) {
			report.setErrorMessage("new green value (" + newGreenValue
					+ ") is different of the value from green datapoint (" + greenValueFromDP + ")");
			report.setState(State.KO);
			return report;
		}
		if (Math.abs(newBlueValue - blueValueFromDP) > 2) {
//		if (!newBlueValue.equals(blueValueFromDP)) {
			report.setErrorMessage("new blue value (" + newBlueValue
					+ ") is different of the value from blue datapoint (" + blueValueFromDP + ")");
			report.setState(State.KO);
			return report;
		}
		
		// get red/green/blue customAttribute from FlexContainer and check with new value
		response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer :" + response.getContent());
			report.setState(State.KO);
			return report;
		}
		retrievedFlexContainer = (FlexContainer) response.getContent();
		redCA = retrievedFlexContainer.getCustomAttribute("red");
		greenCA = retrievedFlexContainer.getCustomAttribute("green");
		blueCA = retrievedFlexContainer.getCustomAttribute("blue");
		redValueFromFlexContainer = Integer.valueOf(redCA.getCustomAttributeValue());
		greenValueFromFlexContainer = Integer.valueOf(greenCA.getCustomAttributeValue());
		blueValueFromFlexContainer = Integer.valueOf(blueCA.getCustomAttributeValue());
		if (Math.abs(newRedValue - redValueFromFlexContainer) > 2) {
//		if (!newRedValue.equals(redValueFromFlexContainer)) {
			report.setErrorMessage("new red value (" + newRedValue
					+ ") is different of the value from red customAttribute (" + redValueFromFlexContainer + ")");
			report.setState(State.KO);
			return report;
		}
		if (Math.abs(newGreenValue - greenValueFromFlexContainer) > 2) {
//		if (!newGreenValue.equals(greenValueFromFlexContainer)) {
			report.setErrorMessage("new green value (" + newGreenValue
					+ ") is different of the value from green customAttribute (" + greenValueFromFlexContainer + ")");
			report.setState(State.KO);
			return report;
		}
		if (Math.abs(newBlueValue - blueValueFromFlexContainer) > 2) {
//		if (!newBlueValue.equals(blueValueFromFlexContainer)) {
			report.setErrorMessage("new blue value (" + newBlueValue
					+ ") is different of the value from blue customAttribute (" + blueValueFromFlexContainer + ")");
			report.setState(State.KO);
			return report;
		}
		
		report.setState(State.OK);
		return report;
	}

}
