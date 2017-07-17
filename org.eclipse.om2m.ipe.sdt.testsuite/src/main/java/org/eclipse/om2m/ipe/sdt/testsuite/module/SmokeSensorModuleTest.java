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
import org.eclipse.om2m.commons.resource.flexcontainerspec.SmokeSensorFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.testsuite.CSEUtil;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport.State;
import org.eclipse.om2m.ipe.sdt.testsuite.module.exception.FlexContainerNotFound;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class SmokeSensorModuleTest extends AbstractModuleTest {

	public SmokeSensorModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);
	}

	public TestReport test() {

		TestReport report = new TestReport("Test for module " + getModule().getName());
		
		String moduleUrl = null;
		try {
			moduleUrl = getModuleFlexContainerUrl();
		} catch (FlexContainerNotFound e) {
			report.setErrorMessage("no FlexContainer for module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}
		
		// at this point, we found out the url of the module flexcontainer.
		
		// retrieve flexContainer value
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if(!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer for module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}
		SmokeSensorFlexContainer retrievedFlexContainer = (SmokeSensorFlexContainer) response.getContent();
		
		// check alarm
		CustomAttribute alarmCA = retrievedFlexContainer.getCustomAttribute(DatapointType.alarm.getShortName());
		if (alarmCA == null) {
			report.setErrorMessage("ERROR : no alarm customAttribute");
			report.setState(State.KO);
			return report;
		}
		Boolean alarm = Boolean.parseBoolean(alarmCA.getCustomAttributeValue());
		
		// alarm from module
		BooleanDataPoint alarmDP = (BooleanDataPoint) getModule().getDataPoint(DatapointType.alarm.getShortName());
		Boolean currentValueFromModule = null;
		try {
			currentValueFromModule = alarmDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to get alarm DP value from module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}
		
		if (!alarm.equals(currentValueFromModule)) {
			report.setErrorMessage("invalid value between flexContainer(" + alarm + ") and module (" + currentValueFromModule + ")");
			report.setState(State.KO);
			return report;
		}
	
		// try to set value
		SmokeSensorFlexContainer toBeUpdated = new SmokeSensorFlexContainer();
		alarmCA.setCustomAttributeValue("true");
		toBeUpdated.getCustomAttributes().add(alarmCA);
		response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			// expected KO
			report.setErrorMessage("we should not be able to set alarm datapoint value from module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}
		
		
		// check detectedTime
		response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer for module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}
		retrievedFlexContainer = (SmokeSensorFlexContainer) response.getContent();
		CustomAttribute detectedTimeCA = retrievedFlexContainer.getCustomAttribute(DatapointType.detectedTime.getShortName());
		if (detectedTimeCA != null) {
			// detectedTime is optional
			
			Integer detectedTime = new Integer(detectedTimeCA.getCustomAttributeValue());
			
			// get detectedTime from module
			IntegerDataPoint detectedTimeDP = (IntegerDataPoint) getModule().getDataPoint(DatapointType.detectedTime.getShortName());
			Integer detectedTimeFromModule = null;
			try {
				detectedTimeFromModule = detectedTimeDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve detectedTime datapoint value :" + e.getMessage());
				report.setState(State.KO);
				return report;
			}
			
			if (detectedTime == null) {
				if (detectedTimeFromModule != null) {
					report.setErrorMessage("expected non null detected time");
					report.setState(State.KO);
					return report;
					
				}
			} else {
				if (!detectedTime.equals(detectedTimeFromModule)) {
					report.setErrorMessage("detectedTime from IPE(" + detectedTime + ") is different of detectedTime from module (" + detectedTimeFromModule + ")");
					report.setState(State.KO);
					return report;
				}
			}
		}
		
		System.out.println("test module " + getModule().getName() + " __________________ OK ___________________________");
		report.setState(State.OK);
		return report;
	}

}
