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
import org.onem2m.sdt.datapoints.StringDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

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
		FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
		CustomAttribute statusCA = retrievedFlexContainer.getCustomAttribute("status");
		CustomAttribute codeCA = retrievedFlexContainer.getCustomAttribute("code"); // optional
		CustomAttribute descriptionCA = retrievedFlexContainer.getCustomAttribute("description"); // optional
		Boolean statusValueFromFlexContainer = Boolean.parseBoolean(statusCA.getCustomAttributeValue());
		Integer codeValueFromFlexContainer = (codeCA != null ? Integer.valueOf(codeCA.getCustomAttributeValue()) :null);
		String descriptionValueFromFlexContainer = (descriptionCA != null ? descriptionCA.getCustomAttributeValue() : null);
		
		
		// retrieve Datapoint 
		BooleanDataPoint statusDP = (BooleanDataPoint) getModule().getDataPoint("status");
		IntegerDataPoint codeDP = (IntegerDataPoint) getModule().getDataPoint("code");
		StringDataPoint descriptionDP = (StringDataPoint) getModule().getDataPoint("description");
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
			if (!checkObject(codeValueFromDP, codeValueFromFlexContainer, report, "code")) {
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
		FlexContainer toBeUpdated = new FlexContainer();
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
		if (checkObject(statusValueFromDP, newStatusValue, report, "status")) {
			// statusValueFromDP should not be equal to newStatusValue
			report.setErrorMessage("status should not be writable");
			report.setState(State.KO);
			return report;
		} else {
			// erase errorMEssage
			report.setErrorMessage(null);
		}
		
		// set code customAttribute
		toBeUpdated = new FlexContainer();
		if(codeCA == null) {
			codeCA = new CustomAttribute();
			codeCA.setCustomAttributeName("code");
			codeCA.setCustomAttributeType("xs:integer");
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
		retrievedFlexContainer = (FlexContainer) response.getContent();
		codeCA = retrievedFlexContainer.getCustomAttribute("code");
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
		toBeUpdated = new FlexContainer();
		if (descriptionCA == null) {
			descriptionCA = new CustomAttribute();
			descriptionCA.setCustomAttributeName("description");
			descriptionCA.setCustomAttributeType("xs:string");
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
		retrievedFlexContainer = (FlexContainer) response.getContent();
		descriptionCA = retrievedFlexContainer.getCustomAttribute("description");
		
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
