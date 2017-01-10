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
import org.eclipse.om2m.sdt.home.types.LevelType;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class WaterLevelModuleTest extends AbstractModuleTest {

	public WaterLevelModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);
	}

	
	public TestReport testGetLiquidLevel() {
		TestReport report = new TestReport("Test " + getModule().getName() + ".getLiquidLevel()");
		
		if (!checkModuleUrl(report)) {
			return report;
		}
		// at this point, we are sure the module FlexContainer exists
		
		// retrieve Flexcontainer
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), getModuleUrl());
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer: " + response.getContent());
			report.setState(State.KO);
			return report;
		}
		FlexContainer retrievedFlexContainer =  (FlexContainer) response.getContent();
		
		// retrieve liquidLevel custom attribute
		CustomAttribute liquidLevelCA = retrievedFlexContainer.getCustomAttribute("liquidLevel");
		if (liquidLevelCA == null) {
			// customAttribute does not exist
			report.setErrorMessage("liquidLevel customAttribute does not exist");
			report.setState(State.KO);
			return report;
		}

		// retrieve liquidLevel datapoint
		LevelType liquidLevelDP = (LevelType) getModule().getDataPoint("liquidLevel");
		
		// retrieve liquidLevel value from datapoint
		Integer liquidLevelValueFromDP = null;
		try {
			liquidLevelValueFromDP = liquidLevelDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve liquidLevel datapoint value: " + e.getMessage());
			report.setException(e);
			report.setState(State.KO);
			return report;
		}
		
		// retrieve value from customAttribute
		Integer liquidLevelFromFlexContainer = null;
		try {
			liquidLevelFromFlexContainer = Integer.parseInt(liquidLevelCA.getCustomAttributeValue());
		} catch (NumberFormatException nfe) {
			report.setErrorMessage("unable to cast liquidLevel customAttribute value (" + liquidLevelCA.getCustomAttributeValue() + ") as an Integer");
			report.setState(State.KO);
			return report;
		}
		
		if (!checkObject(liquidLevelFromFlexContainer, liquidLevelValueFromDP, report, "liquidLevel")) {
			return report;
		}
		
		
		report.setState(State.OK);
		return report;
	}
	
	public TestReport testSetLiquidLevel() {
		TestReport report = new TestReport("Test " + getModule().getName() + ".setLiquidLevel()");
		
		if (!checkModuleUrl(report)) {
			return report;
		}
		
		// retrieve liquidLevel datapoint
		LevelType liquidLevelDP = (LevelType) getModule().getDataPoint("liquidLevel");
		Integer liquidLevelFromDP = null;
		try {
			liquidLevelFromDP = liquidLevelDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve value from liquidLevel datapoint: " + e.getMessage());
			report.setException(e);
			report.setState(State.KO);
			return report;
		}
		
		// compute new value for liquidLevel customAttribute (value between 1 and 5)
		Integer newLiquidLevelValue = (liquidLevelFromDP.intValue() == 1 ? 5 : 1);
		
		// prepare FlexContainer + customAttribute
		FlexContainer toBeUpdated = new FlexContainer();
		CustomAttribute liquidLevelCA = new CustomAttribute();
		liquidLevelCA.setCustomAttributeName("liquidLevel");
		liquidLevelCA.setCustomAttributeType("hd:liquidLevel");
		liquidLevelCA.setCustomAttributeValue(newLiquidLevelValue.toString());
		toBeUpdated.getCustomAttributes().add(liquidLevelCA);
		
		// perform UPDATE request
		ResponsePrimitive response = CSEUtil.updateFlexContainerEntity(getCseService(), getModuleUrl(), toBeUpdated);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to update liquidLevel customAttribute: " + response.getContent());
			report.setState(State.KO);
			return report;
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// retrieve value from datapoint 
		try {
			liquidLevelFromDP = liquidLevelDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve value from liquidLevel datapoint: " + e.getMessage());
			report.setException(e);
			report.setState(State.KO);
			return report;
		}
		
		// check value 
		if (!checkObject(newLiquidLevelValue, liquidLevelFromDP, report, "liquidLevel")) {
			return report;
		}
		
		report.setState(State.OK);
		return report;
	}
	
}
