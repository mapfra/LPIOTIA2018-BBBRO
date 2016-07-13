package org.eclipse.om2m.testsuite.flexcontainer;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class LocationFlexContainerTest extends FlexContainerTestSuite {

	@Override
	protected String getTestSuiteName() {
		return "LocationFlexContainerTest";
	}

	public LocationFlexContainerTest(final CseService pCseService) {
		super(pCseService);
	}

	public void testUnderCseBase() {
		
		genericTest("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME, "testUnderCseBase");

	}

	public void testUnderRemoteCSE() {
		genericTest("/" + Constants.REMOTE_CSE_ID + "/" + Constants.REMOTE_CSE_NAME, "testUnderRemoteCSE");
	}

	public void testUnderFlexContainer() {
		
		// create a FlexContainer
		FlexContainer parentFlexContainer = new FlexContainer();
		parentFlexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeType("xs:boolean");
		ca.setCustomAttributeValue("true");
		parentFlexContainer.getCustomAttributes().add(ca);
		
		String parentFlexContainerName = "parentFlexContainer_" + System.currentTimeMillis();
		sendCreateFlexContainerRequest(parentFlexContainer, "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME, parentFlexContainerName);
		
		genericTest("/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + parentFlexContainerName, "testUnderFlexContainer");
	
	}

	public void testUnderContainer() {
		
		// Container
		Container container = new Container();
		container.setOntologyRef("OrangeOntology");

		String baseParentContainerLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		String parentContainerName = "parentContainerName_" + System.currentTimeMillis();
		
		
		RequestPrimitive request = new RequestPrimitive();
		request.setContent(container);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(baseParentContainerLocation);
		request.setResourceType(BigInteger.valueOf(ResourceType.CONTAINER));
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setName(parentContainerName);
		request.setOperation(Operation.CREATE);
		ResponsePrimitive response = getCseService().doRequest(request);
		
		// we suppose here the container is created
		genericTest(baseParentContainerLocation + "/" + parentContainerName, "testUnderContainer");

	}

	private void genericTest(String location, String methodName) {

		// set a new flexContainer
		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powerState");
		ca.setCustomAttributeType("xs:boolean");
		ca.setCustomAttributeValue("true");
		flexContainer.getCustomAttributes().add(ca);

		String flexContainerName = "FLEXCONTAINER_" + System.currentTimeMillis();
		
		String baseLocation =  location;
		String flexContainerLocation = baseLocation + "/" + flexContainerName;
		
		FlexContainer createdFlexContainer = null;

		// send create request
		ResponsePrimitive response = sendCreateFlexContainerRequest(flexContainer, baseLocation,
				flexContainerName);
		if (response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// OK
			createdFlexContainer = (FlexContainer) response.getContent();
			try {
				checkFlexContainerOntologyRef(flexContainer, createdFlexContainer);
				checkFlexContainerCustomAttribute(flexContainer, createdFlexContainer);
			} catch (Exception e) {
				createTestReport(methodName, Status.KO, e.getMessage(), e);
				return;
			}
			if (!createdFlexContainer.getName().equals(flexContainerName)) {
				// KO
				createTestReport(methodName, Status.KO,
						"invalid flexContainer name, expecting:" + flexContainerName, null);
				return;
			}

		} else {
			// KO
			createTestReport(methodName, Status.KO, "unexpected response code:"
					+ response.getResponseStatusCode() + ", expected:" + ResponseStatusCode.CREATED, null);
			return;
		}

		// retrieve
		response = sendRetrieveRequest(flexContainerLocation);
		if (response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// OK
			FlexContainer retrievedFlexContainer = (FlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, retrievedFlexContainer);
			} catch (Exception e) {
				createTestReport(methodName, Status.KO, e.getMessage(), e);
				return;
			}
		} else {
			// KO
			createTestReport(methodName, Status.KO, "unexpected response code:"
					+ response.getResponseStatusCode() + ", expected:" + ResponseStatusCode.OK, null);
			return;
		}

		// OK
		createTestReport(methodName, Status.OK, null, null);

	}

}
