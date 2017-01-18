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
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;

public class ColourSaturationModuleTest extends AbstractModuleTest {

	public ColourSaturationModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);
		// switch on
		Device device = getModule().getOwner();
		String binarySwitchModuleName = null;
		for (String moduleName : device.getModuleNames()) {
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
		TestReport report = new TestReport("Test for module " + getModule().getName());

		String moduleUrl = null;
		try {
			moduleUrl = getModuleFlexContainerUrl();
		} catch (FlexContainerNotFound e) {
			report.setErrorMessage("unable to find out FlexContainer for module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}

		// at this point, we are sure moduleUrl contains the url of the module
		// FlexContainer.

		// retrieve flexContainer
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer of module " + getModule().getName() + " : "
					+ response.getContent());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

		// get colourSaturation custom attribute
		CustomAttribute colourSaturationCA = retrievedFlexContainer.getCustomAttribute("colourSaturation");
		Integer colourSaturationFromFlexContainer = Integer.valueOf(colourSaturationCA.getCustomAttributeValue());

		// get colourSaturation from module
		IntegerDataPoint colourSaturationDP = (IntegerDataPoint) getModule().getDataPoint("colourSaturation");
		Integer colourSaturationFromDP = null;
		try {
			colourSaturationFromDP = colourSaturationDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve value from colourSaturation Datapoint:" + e.getMessage());
			report.setState(State.KO);
			return report;
		}

		// check value between flexContainer and module
		if (Math.abs(colourSaturationFromFlexContainer - colourSaturationFromDP) > 2) {
			// if
			// (!colourSaturationFromFlexContainer.equals(colourSaturationFromDP))
			// {
			report.setErrorMessage("value from flexContainer (" + colourSaturationFromFlexContainer
					+ ") is different of the value from Datapoint(" + colourSaturationFromDP + ")");
			report.setState(State.KO);
			return report;
		}

		// set colourSaturation
		FlexContainer toBeUpdated = new FlexContainer();
		Integer newColourSaturation = new Integer((int) (Math.random() * 100d));
		colourSaturationCA.setCustomAttributeValue(newColourSaturation.toString());
		toBeUpdated.getCustomAttributes().add(colourSaturationCA);
		response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage(
					"unable to update value of colourSaturation customAttribute:" + response.getContent());
			report.setState(State.KO);
			return report;
		}

		// get value from datapoint
		try {
			colourSaturationFromDP = colourSaturationDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve value from colourSaturation Datapoint:" + e.getMessage());
			report.setState(State.KO);
			return report;
		}
		if (Math.abs(newColourSaturation - colourSaturationFromDP) > 2) {
			// if (!newColourSaturation.equals(colourSaturationFromDP)) {
			report.setErrorMessage("new value of flexContainer (" + newColourSaturation
					+ ") is different of the value from Datapoint(" + colourSaturationFromDP + ")");
			report.setState(State.KO);
			return report;
		}

		// retrieve value from FlexContainer
		response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer of module " + getModule().getName() + " : "
					+ response.getContent());
			report.setState(State.KO);
			return report;
		}
		retrievedFlexContainer = (FlexContainer) response.getContent();

		// get colourSaturation custom attribute
		colourSaturationCA = retrievedFlexContainer.getCustomAttribute("colourSaturation");
		colourSaturationFromFlexContainer = Integer.valueOf(colourSaturationCA.getCustomAttributeValue());
		// check value between flexContainer and newValue
		if (Math.abs(colourSaturationFromFlexContainer - newColourSaturation) > 2) {
			// if
			// (!colourSaturationFromFlexContainer.equals(newColourSaturation))
			// {
			report.setErrorMessage("value from flexContainer (" + colourSaturationFromFlexContainer
					+ ") is different of the value set(" + newColourSaturation + ")");
			report.setState(State.KO);
			return report;
		}

		report.setState(State.OK);
		return report;
	}

}
