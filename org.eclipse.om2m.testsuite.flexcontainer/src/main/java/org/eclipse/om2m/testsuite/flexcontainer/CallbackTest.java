package org.eclipse.om2m.testsuite.flexcontainer;

import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class CallbackTest extends FlexContainerTestSuite {

	private String parentLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
	private String flexContainerName = "BinarySwitch_" + System.currentTimeMillis();
	private String flexContainerLocation = parentLocation + "/" + flexContainerName;
	private FlexContainer flexContainer;
	private BundleContext bundleContext;
	private ServiceRegistration flexContainerServiceRegistration;

	private int numberOfGetAttributeValue = 0;

	public CallbackTest(CseService pCseService, BundleContext pBundleContext) {
		super(pCseService);
		bundleContext = pBundleContext;

		flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		CustomAttribute powerState = new CustomAttribute();
		powerState.setCustomAttributeType("xs:boolean");
		powerState.setCustomAttributeValue("false");
		powerState.setCustomAttributeName("powerState");
		flexContainer.getCustomAttributes().add(powerState);

		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, parentLocation, flexContainerName);
		flexContainer = (FlexContainer) response.getContent();
	}

	@Override
	protected String getTestSuiteName() {
		return "CallbackTest";
	}

	public void testCallback_Retrieve() {

		numberOfGetAttributeValue = 0;

		// flexContainerService
		FlexContainerService fcs = new FlexContainerService() {

			@Override
			public void setCustomAttributeValues(List<CustomAttribute> customAttributes,
					RequestPrimitive requestPrimitive) throws Om2mException {
				throw new Om2mException("not allowed", ResponseStatusCode.NOT_IMPLEMENTED);
			}

			@Override
			public String getFlexContainerLocation() {
				return flexContainer.getResourceID();
			}

			@Override
			public String getCustomAttributeValue(String customAttributeName) throws Om2mException {
				if (!customAttributeName.equals("powerState")) {
					throw new Om2mException(
							"unexpected getCustomAttributeValue for attributeName=" + customAttributeName,
							ResponseStatusCode.NOT_IMPLEMENTED);
				}
				numberOfGetAttributeValue++;
				return Boolean.TRUE.toString();
			}

		};

		// register FlexContainerService
		flexContainerServiceRegistration = bundleContext.registerService(FlexContainerService.class, fcs, null);

		// retrieve the FlexContainer
		ResponsePrimitive response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

			if (!retrievedFlexContainer.getCustomAttribute("powerState").getCustomAttributeValue()
					.equals(Boolean.TRUE.toString())) {
				createTestReport("testCallback", Status.KO, "invalid powerState value, expecting true", null);
				return;
			}

			if (numberOfGetAttributeValue != 1) {
				createTestReport("testCallback", Status.KO,
						"invalid numberOfGetAttributeValue, expecting 1, found " + numberOfGetAttributeValue, null);
				return;
			}

		}

		// unregister the flexContainerService
		flexContainerServiceRegistration.unregister();

		// retrieve the flexContainer again
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

			if (!retrievedFlexContainer.getCustomAttribute("powerState").getCustomAttributeValue()
					.equals(Boolean.TRUE.toString())) {
				createTestReport("testCallback", Status.KO, "invalid powerState value, expecting true", null);
				return;
			}

			if (numberOfGetAttributeValue != 1) {
				createTestReport("testCallback", Status.KO,
						"invalid numberOfGetAttributeValue, expecting 1, found " + numberOfGetAttributeValue, null);
				return;
			}

		}

		createTestReport("testCallback_Retrieve", Status.OK, null, null);
	}

	public void testCallback_update() {

		numberOfGetAttributeValue = 0;

		// flexContainerService
		FlexContainerService fcs = new FlexContainerService() {

			@Override
			public void setCustomAttributeValues(List<CustomAttribute> customAttributes,
					RequestPrimitive requestPrimitive) throws Om2mException {
				if (customAttributes.isEmpty()) {
					throw new Om2mException("not allowed. Missing powerState", ResponseStatusCode.INVALID_ARGUMENTS);
				}
				if (customAttributes.size() != 1) {
					throw new Om2mException("not allowed. Invalid size of CustomAttribute", ResponseStatusCode.INVALID_ARGUMENTS);
				}
				CustomAttribute powerStateCA = customAttributes.get(0);
				if (!"powerState".equals(powerStateCA.getCustomAttributeName())) {
					throw new Om2mException("not allowed", ResponseStatusCode.INVALID_ARGUMENTS);
				}

				Boolean booleanValue = null;
				try {
					booleanValue = new Boolean((String) powerStateCA.getCustomAttributeValue());
				} catch (Exception e) {
					throw new Om2mException("not allowed", ResponseStatusCode.INVALID_ARGUMENTS);
				}
				if (!Boolean.TRUE.equals(booleanValue)) {
					throw new Om2mException("not allowed", ResponseStatusCode.INVALID_ARGUMENTS);
				}

			}

			@Override
			public String getFlexContainerLocation() {
				return flexContainer.getResourceID();
			}

			@Override
			public String getCustomAttributeValue(String customAttributeName) throws Om2mException {
				if (!customAttributeName.equals("powerState")) {
					throw new Om2mException(
							"unexpected getCustomAttributeValue for attributeName=" + customAttributeName,
							ResponseStatusCode.NOT_IMPLEMENTED);
				}
				numberOfGetAttributeValue++;
				return Boolean.TRUE.toString();
			}

		};

		// register FlexContainerService
		flexContainerServiceRegistration = bundleContext.registerService(FlexContainerService.class, fcs, null);

		FlexContainer toBeUpdated = new FlexContainer();
		toBeUpdated.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		CustomAttribute powerState = new CustomAttribute();
		powerState.setCustomAttributeType("xs:boolean");
		powerState.setCustomAttributeValue("true");
		powerState.setCustomAttributeName("powerState");
		toBeUpdated.getCustomAttributes().add(powerState);

		ResponsePrimitive response = sendUpdateFlexContainerRequest(flexContainerLocation, toBeUpdated);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.UPDATED)) {
			createTestReport("testCallback_update", Status.KO,
					"unable to update FlexContainer:" + response.getContent(), null);
			return;
		}

		FlexContainer updatedFlexContainer = (FlexContainer) response.getContent();
		if (!updatedFlexContainer.getCustomAttribute("powerState").getCustomAttributeValue()
				.equals(Boolean.TRUE.toString())) {
			createTestReport("testCallback_update", Status.KO, "invalid for powerState, expecting TRUE, found"
					+ updatedFlexContainer.getCustomAttribute("powerState").getCustomAttributeValue(), null);
			return;
		}
		
		// retrieve the flexContainer
		response = sendRetrieveRequest(flexContainerLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();

			if (!retrievedFlexContainer.getCustomAttribute("powerState").getCustomAttributeValue()
					.equals(Boolean.TRUE.toString())) {
				createTestReport("testCallback", Status.KO, "invalid powerState value, expecting true", null);
				return;
			}

			if (numberOfGetAttributeValue != 1) {
				createTestReport("testCallback", Status.KO,
						"invalid numberOfGetAttributeValue, expecting 1, found " + numberOfGetAttributeValue, null);
				return;
			}

		}
		createTestReport("testCallback_update", Status.OK, null, null);
	}

}
