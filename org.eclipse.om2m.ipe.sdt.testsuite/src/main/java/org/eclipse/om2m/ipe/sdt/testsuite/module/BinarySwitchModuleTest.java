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
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BinarySwitchFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.testsuite.CSEUtil;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport.State;
import org.eclipse.om2m.ipe.sdt.testsuite.module.exception.FlexContainerNotFound;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class BinarySwitchModuleTest extends AbstractModuleTest {

	public BinarySwitchModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);
	}

	public TestReport test() {
		
		TestReport report = new TestReport("Test module " + getModule().getName());
		
		System.out.println("start test module " + getModule().getName());
		String moduleUrl;
		try {
			moduleUrl = getModuleFlexContainerUrl();
		} catch (FlexContainerNotFound e) {
			report.setErrorMessage("unable to find out flexContainer for module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}
		// at this point, we are sure the module exists
		
		// read powerState datapoint
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve flexContainer (url=" + moduleUrl + ")");
			report.setState(State.KO);
			return report;
		}
		// get powerState value
		BinarySwitchFlexContainer retrievedFlexContainer = (BinarySwitchFlexContainer) response.getContent();
		CustomAttribute powerStateCA = retrievedFlexContainer.getCustomAttribute(DatapointType.powerState.getShortName());
		Boolean powerState = Boolean.parseBoolean(powerStateCA.getCustomAttributeValue());
		System.out.println("powerState=" + powerState);
		
		
		// set powerState value
		BinarySwitchFlexContainer toBeUpdatedFlexContainer = new BinarySwitchFlexContainer();
		Boolean newPowerState = new Boolean(!powerState.booleanValue());
		powerStateCA.setCustomAttributeValue(newPowerState.toString());
		toBeUpdatedFlexContainer.getCustomAttributes().add(powerStateCA);
		response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdatedFlexContainer);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to update flexContainer (url=" + moduleUrl + ")");
			report.setState(State.KO);
			return report;
		}
		
		// check new value from Module object
		BooleanDataPoint powerStateDP = (BooleanDataPoint) getModule().getDataPoint(DatapointType.powerState.getShortName());
		try {
			Boolean valueFromModule = powerStateDP.getValue();
			if (!newPowerState.equals(valueFromModule)) {
				report.setErrorMessage("value from module (" + valueFromModule + ") is the same as the one set (" + newPowerState +")");
				report.setState(State.KO);
				return report;
			}
		} catch (DataPointException e) {
			report.setErrorMessage("unable to retrieve value from module :" + e.getMessage());
			report.setState(State.KO);
			return report;
		} catch (AccessException e) {
			report.setErrorMessage("AccessException - unable to retrieve value from module :" + e.getMessage());
			report.setState(State.KO);
			return report;
		}
		
		// then retrieve from OM2M tree
		retrievedFlexContainer = (BinarySwitchFlexContainer) response.getContent();
		powerStateCA = retrievedFlexContainer.getCustomAttribute(DatapointType.powerState.getShortName());
		Boolean currentPowerStateValue = Boolean.parseBoolean(powerStateCA.getCustomAttributeValue());
		if (!currentPowerStateValue.equals(newPowerState)) {
			System.out.println("value from flexContainer (" + currentPowerStateValue + ") is the same as the one set (" + newPowerState +")");
		}
		
		System.out.println("test module " + getModule().getName() + " __________________ OK ___________________________");
		report.setState(State.OK);
		
		return report;
	}

}
