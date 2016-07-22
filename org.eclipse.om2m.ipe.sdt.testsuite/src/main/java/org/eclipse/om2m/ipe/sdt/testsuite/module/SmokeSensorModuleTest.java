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
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

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
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
		
		// check alarm
		CustomAttribute alarmCA = retrievedFlexContainer.getCustomAttribute("alarm");
		if (alarmCA == null) {
			report.setErrorMessage("ERROR : no alarm customAttribute");
			report.setState(State.KO);
			return report;
		}
		Boolean alarm = Boolean.parseBoolean(alarmCA.getCustomAttributeValue());
		
		// alarm from module
		BooleanDataPoint alarmDP = (BooleanDataPoint) getModule().getDataPoint("alarm");
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
		FlexContainer toBeUpdated = new FlexContainer();
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
		retrievedFlexContainer = (FlexContainer) response.getContent();
		CustomAttribute detectedTimeCA = retrievedFlexContainer.getCustomAttribute("detectedTime");
		if (detectedTimeCA != null) {
			// detectedTime is optional
			
			Integer detectedTime = new Integer(detectedTimeCA.getCustomAttributeValue());
			
			// get detectedTime from module
			IntegerDataPoint detectedTimeDP = (IntegerDataPoint) getModule().getDataPoint("detectedTime");
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
