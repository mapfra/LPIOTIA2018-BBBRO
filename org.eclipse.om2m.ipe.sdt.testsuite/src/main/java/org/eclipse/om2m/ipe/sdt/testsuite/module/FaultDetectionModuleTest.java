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
import org.eclipse.om2m.commons.resource.flexcontainerspec.FaultDetectionFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.testsuite.CSEUtil;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport.State;
import org.eclipse.om2m.ipe.sdt.testsuite.module.exception.FlexContainerNotFound;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class FaultDetectionModuleTest extends AbstractModuleTest {

	public FaultDetectionModuleTest(CseService pCseService, Module pModule) {
		super(pCseService, pModule);
	}

	public TestReport test() {
		TestReport report = new TestReport("Test module " + getModule().getName());
		
		String moduleUrl = null;
		try {
			moduleUrl = getModuleFlexContainerUrl();
		} catch (FlexContainerNotFound e) {
			report.setErrorMessage("unable to find out FlexContainer for module " + getModule().getName());
			report.setState(State.KO);
			return report;
		}
		
		// at this point, we are sure the module FlexContainer exist
		
		// retrieve FlexContainer and its customAttribute
		ResponsePrimitive response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer : " + response.getContent());
			report.setState(State.KO);
			return report;
		}
		FaultDetectionFlexContainer retrievedFlexContainer = (FaultDetectionFlexContainer) response.getContent();
		CustomAttribute statusCA = retrievedFlexContainer.getCustomAttribute(DatapointType.status.getShortName());
		CustomAttribute codeCA = retrievedFlexContainer.getCustomAttribute(DatapointType.code.getShortName()); // optional
		CustomAttribute descriptionCA = retrievedFlexContainer.getCustomAttribute(DatapointType.description.getShortName()); // optional
		Boolean statusValueFromFlexContainer = Boolean.parseBoolean(statusCA.getCustomAttributeValue());
		Integer codeValueFromFlexContainer = (codeCA != null ? Integer.valueOf(codeCA.getCustomAttributeValue()) :null);
		String descriptionValueFromFlexContainer = (descriptionCA != null ? descriptionCA.getCustomAttributeValue() : null);
		
		
		// retrieve Datapoint 
		BooleanDataPoint statusDP = (BooleanDataPoint) getModule().getDataPoint(DatapointType.status.getShortName());
		IntegerDataPoint codeDP = (IntegerDataPoint) getModule().getDataPoint(DatapointType.code.getShortName());
		StringDataPoint descriptionDP = (StringDataPoint) getModule().getDataPoint(DatapointType.description.getShortName());
		Boolean statusValueFromDP = null;
		Integer codeValueFromDP = null;
		String descriptionValueFromDP = null;
		
		// check status value
		try {
			statusValueFromDP = statusDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve status Datapoint value : " + e.getMessage());
			report.setState(State.KO);
			return report;
		}
		if (!checkObject(statusValueFromDP, statusValueFromFlexContainer, report, "status")) {
			return report;
		}

		// check code value
		if (codeDP != null) {
			try {
				codeValueFromDP = codeDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve code Datapoint value : " + e.getMessage());
				report.setState(State.KO);
				return report;
			}
			if (!checkObject(codeValueFromDP, codeValueFromFlexContainer, report, DatapointType.code.getShortName())) {
				// ko
				return report;
			}
		} else {
			// code Datapoint does not exist ==> code CustomAttribute should not exist
			if (codeCA != null) {
				report.setErrorMessage("code customAttribute is not expected as code Datapoint does not exist");
				report.setState(State.KO);
				return report;
			}
		}
		
		// check description value
		if (descriptionDP != null) {
			try {
				descriptionValueFromDP = descriptionDP.getValue();
			} catch (DataPointException | AccessException e) {
				report.setErrorMessage("unable to retrieve description Datapoint value : " + e.getMessage());
				report.setState(State.KO);
				return report;
			}
			if (!checkObject(descriptionValueFromDP, descriptionValueFromFlexContainer, report, "description")) {
				// ko
				return report;
			}
		} else {
			// descriptionDP == null ==> descriptionCA should also be null
			if (descriptionCA != null) {
				report.setErrorMessage("description customAttribute is not expected as description Datapoint does not exist");
				report.setState(State.KO);
				return report;
			}
		}
		
		// set status customAttribute value
		FaultDetectionFlexContainer toBeUpdated = new FaultDetectionFlexContainer();
		Boolean newStatusValue = (statusValueFromDP.booleanValue() ? Boolean.FALSE : Boolean.TRUE);
		statusCA.setCustomAttributeValue(newStatusValue.toString());
		toBeUpdated.getCustomAttributes().add(statusCA);
		response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("status is not writable !!!");
			report.setState(State.KO);
			return report;
		}
		
		// check current status DataPoint value
		try {
			statusValueFromDP = statusDP.getValue();
		} catch (DataPointException | AccessException e) {
			report.setErrorMessage("unable to retrieve status Datapoint value : " + e.getMessage());
			report.setState(State.KO);
			return report;
		}
		if (checkObject(statusValueFromDP, newStatusValue, report, DatapointType.status.getShortName())) {
			// statusValueFromDP should not be equal to newStatusValue
			report.setErrorMessage("status should not be writable");
			report.setState(State.KO);
			return report;
		} else {
			// erase errorMEssage
			report.setErrorMessage(null);
		}
		
		// set code customAttribute
		toBeUpdated = new FaultDetectionFlexContainer();
		if(codeCA == null) {
			codeCA = new CustomAttribute();
			codeCA.setCustomAttributeName(DatapointType.code.getShortName());
		}
		codeCA.setCustomAttributeValue("1");
		toBeUpdated.getCustomAttributes().add(codeCA);
		response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			// UPDATE request did not fail ==> KO
			report.setErrorMessage("code customAttribute is not writable");
			report.setState(State.KO);
			return report;
		}
		// retrieve code customAttribute
		response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer : " + response.getContent());
			report.setState(State.KO);
			return report;
		}
		retrievedFlexContainer = (FaultDetectionFlexContainer) response.getContent();
		codeCA = retrievedFlexContainer.getCustomAttribute(DatapointType.code.getShortName());
		if (codeDP == null) {
			if (codeCA != null) {
				report.setErrorMessage("code DataPoint does not exist but code customAttribute exist!");
				report.setState(State.KO);
				return report;
			}
		} else {
			if (codeCA.getCustomAttributeValue().equals("1")) {
				report.setErrorMessage("writable operation should fail!!!");
				report.setState(State.KO);
				return report;
			}
		}
		
		
		// set description customAttribute
		toBeUpdated = new FaultDetectionFlexContainer();
		if (descriptionCA == null) {
			descriptionCA = new CustomAttribute();
			descriptionCA.setCustomAttributeName(DatapointType.description.getShortName());
		}
		String newDescriptionValue = "A fake description value " + System.currentTimeMillis();
		descriptionCA.setCustomAttributeValue(newDescriptionValue);
		toBeUpdated.getCustomAttributes().add(descriptionCA);
		response = CSEUtil.updateFlexContainerEntity(getCseService(), moduleUrl, toBeUpdated);
		if (ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			// UPDATE request did not fail ==> KO
			report.setErrorMessage("description customAttribute is not writable");
			report.setState(State.KO);
			return report;
		}
		// retrieve description customAttribute
		response = CSEUtil.retrieveEntity(getCseService(), moduleUrl);
		if (!ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			report.setErrorMessage("unable to retrieve FlexContainer : " + response.getContent());
			report.setState(State.KO);
			return report;
		}
		retrievedFlexContainer = (FaultDetectionFlexContainer) response.getContent();
		descriptionCA = retrievedFlexContainer.getCustomAttribute(DatapointType.description.getShortName());
		
		if (descriptionDP == null) {
			// description custom attribute must be null
			if (descriptionCA != null) {
				report.setErrorMessage("description DataPoint does not exist and description customAttribute exist !!");
				report.setState(State.KO);
				return report;
			}
		} else {
			if (newDescriptionValue.equals(descriptionCA.getCustomAttributeValue())) {
				report.setErrorMessage("writable operation should fail!!!");
				report.setState(State.KO);
				return report;
			}
		}
		
		report.setState(State.OK);
		return report;
	}

}
