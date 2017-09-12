/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.testsuite.flexcontainer;

import org.eclipse.om2m.commons.constants.AccessControl;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.AccessControlRule;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.SetOfAcrs;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BinarySwitchFlexContainer;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.testsuite.flexcontainer.TestReport.Status;

public class AccessControlPolicyTest extends FlexContainerTestSuite {

	public AccessControlPolicyTest(final CseService pCseService) {
		super(pCseService);
	}

	@Override
	protected String getTestSuiteName() {
		return "AccessControlPolicyTest";
	}

	public void testCreateAccessControlPolicy() {

		String acpName = "acp_" + System.currentTimeMillis();
		AccessControlPolicy acp = new AccessControlPolicy();
		acp.setName(acpName);
		SetOfAcrs privileges = new SetOfAcrs();
		AccessControlRule accessControlRule = new AccessControlRule();
		accessControlRule.getAccessControlOriginators().add("greg:greg");
		// accessControlRule.getAccessControlOriginators().add("admin:admin");
		accessControlRule.setAccessControlOperations(AccessControl.ALL);
		privileges.getAccessControlRule().add(accessControlRule);
		acp.setPrivileges(privileges);

		SetOfAcrs selfPrivileges = new SetOfAcrs();
		AccessControlRule selfAccessControlRule = new AccessControlRule();
		selfAccessControlRule.getAccessControlOriginators().add("admin:admin");
		selfAccessControlRule.setAccessControlOperations(AccessControl.ALL);
		selfPrivileges.getAccessControlRule().add(selfAccessControlRule);
		acp.setSelfPrivileges(selfPrivileges);

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		
		String acpLocation = baseLocation + "/" + acpName;

		ResponsePrimitive response = sendCreateAccessControlPolicyRequest(acp, baseLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testAccessControlPolicy", Status.KO, "unable to create a new acp", null);
			return;
		}
		// here are sure the ACP has been created
		AccessControlPolicy returnedAcp = (AccessControlPolicy) response.getContent();

		// init a new FlexContainer
		BinarySwitchFlexContainer flexContainer = new BinarySwitchFlexContainer();
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powSe");
		ca.setCustomAttributeValue("false");
		flexContainer.getCustomAttributes().add(ca);
		String flexContainerName = "flexContainerACPTest_" + System.currentTimeMillis();
		flexContainer.setName(flexContainerName);
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		// set acp
		flexContainer.getAccessControlPolicyIDs().add(returnedAcp.getResourceID());

		// send create FlexContainer request
		response = sendCreateFlexContainerRequest(flexContainer, baseLocation, Constants.ADMIN_REQUESTING_ENTITY);
		BinarySwitchFlexContainer createdFlexContainer = null;
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testAccessControlPolicy", Status.KO, "unable to create a FlexContainer", null);
			return;
		} else {
			createdFlexContainer = (BinarySwitchFlexContainer) response.getContent();
		}

		// retrieve the flexContainer with greg:greg credentials
		response = sendRetrieveRequest(flexContainerLocation, "greg:greg");
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			createTestReport("testAccessControlPolicy", Status.KO, "unable to retrieve the FlexContainer", null);
			return;
		} else {
			BinarySwitchFlexContainer toBeRetrieved = (BinarySwitchFlexContainer) response.getContent();
			try {
				checkFlexContainer(createdFlexContainer, toBeRetrieved);
			} catch (Exception e) {
				// KO
				createTestReport("testAccessControlPolicy", Status.KO, "flexContainer are differents", null);
				return;
			}
		}

		// retrieve the flexContainer with admin:admin, expected FORBIDDEN
		response = sendRetrieveRequest(flexContainerLocation, Constants.ADMIN_REQUESTING_ENTITY);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.ACCESS_DENIED)) {
			// KO
			createTestReport("testAccessControlPolicy", Status.KO,
					"expected " + ResponseStatusCode.ACCESS_DENIED + ", received:" + response.getResponseStatusCode(),
					null);
			return;
		}

		createTestReport("testAccessControlPolicy", Status.OK, null, null);

	}

	public void testCreateFlexContainerWithNoRight() {
		// create an ACP for greg:greg with RETRIEVE rights
		String acpName = "acp_" + System.currentTimeMillis();
		AccessControlPolicy acp = new AccessControlPolicy();
		acp.setName(acpName);
		SetOfAcrs privileges = new SetOfAcrs();
		AccessControlRule accessControlRule = new AccessControlRule();
		accessControlRule.getAccessControlOriginators().add("greg:greg");
		// accessControlRule.getAccessControlOriginators().add("admin:admin");
		accessControlRule.setAccessControlOperations(AccessControl.RETRIEVE);
		privileges.getAccessControlRule().add(accessControlRule);
		acp.setPrivileges(privileges);

		SetOfAcrs selfPrivileges = new SetOfAcrs();
		AccessControlRule selfAccessControlRule = new AccessControlRule();
		selfAccessControlRule.getAccessControlOriginators().add("admin:admin");
		selfAccessControlRule.setAccessControlOperations(AccessControl.ALL);
		selfPrivileges.getAccessControlRule().add(selfAccessControlRule);
		acp.setSelfPrivileges(selfPrivileges);

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		
		String acpLocation = baseLocation + "/" + acpName;

		ResponsePrimitive response = sendCreateAccessControlPolicyRequest(acp, baseLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testCreateFlexContainerWithNoRight", Status.KO, "unable to create a new acp", null);
			return;
		}
		// here are sure the ACP has been created
		AccessControlPolicy returnedAcp = (AccessControlPolicy) response.getContent();

		// init a new FlexContainer
		String flexContainerName = "flexContainerACPTest_" + System.currentTimeMillis();
		BinarySwitchFlexContainer flexContainer = new BinarySwitchFlexContainer();
		flexContainer.setName(flexContainerName);
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powSe");
		ca.setCustomAttributeValue("false");
		flexContainer.getCustomAttributes().add(ca);
		
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		// try to create a FlexContainer using greg:greg credentials => expect
		// ACCESS DENIED
		response = sendCreateFlexContainerRequest(flexContainer, baseLocation, "greg:greg");
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.ACCESS_DENIED)) {
			// KO
			createTestReport(
					"testCreateFlexContainerWithNoRight", Status.KO, "error : expected "
							+ ResponseStatusCode.ACCESS_DENIED + ", received:" + response.getResponseStatusCode(),
					null);
			return;
		}

		createTestReport("testCreateFlexContainerWithNoRight", Status.OK, null, null);
	}

	public void testDeleteFlexContainerWithNoRight() {
		// create an ACP for greg:greg with RETRIEVE rights
		String acpName = "acp_" + System.currentTimeMillis();
		AccessControlPolicy acp = new AccessControlPolicy();
		acp.setName(acpName);
		SetOfAcrs privileges = new SetOfAcrs();
		AccessControlRule accessControlRule = new AccessControlRule();
		accessControlRule.getAccessControlOriginators().add("greg:greg");
		// accessControlRule.getAccessControlOriginators().add("admin:admin");
		accessControlRule.setAccessControlOperations(AccessControl.RETRIEVE);
		privileges.getAccessControlRule().add(accessControlRule);
		acp.setPrivileges(privileges);

		SetOfAcrs selfPrivileges = new SetOfAcrs();
		AccessControlRule selfAccessControlRule = new AccessControlRule();
		selfAccessControlRule.getAccessControlOriginators().add("admin:admin");
		selfAccessControlRule.setAccessControlOperations(AccessControl.ALL);
		selfPrivileges.getAccessControlRule().add(selfAccessControlRule);
		acp.setSelfPrivileges(selfPrivileges);

		String baseLocation = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;
		
		String acpLocation = baseLocation + "/" + acpName;

		ResponsePrimitive response = sendCreateAccessControlPolicyRequest(acp, baseLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteFlexContainerWithNoRight", Status.KO, "unable to create a new acp", null);
			return;
		} 
		// here are sure the ACP has been created
		AccessControlPolicy returnedAcp = (AccessControlPolicy) response.getContent();

		// init a new FlexContainer
		String flexContainerName = "flexContainerACPTest_" + System.currentTimeMillis();
		BinarySwitchFlexContainer flexContainer = new BinarySwitchFlexContainer();
		flexContainer.setName(flexContainerName);
		flexContainer.setContainerDefinition("org.onem2m.home.moduleclass.binaryswitch");
		CustomAttribute ca = new CustomAttribute();
		ca.setCustomAttributeName("powSe");
		ca.setCustomAttributeValue("false");
		flexContainer.getCustomAttributes().add(ca);
		
		String flexContainerLocation = baseLocation + "/" + flexContainerName;

		// try to create a FlexContainer using admin:admin credentials
		BinarySwitchFlexContainer createdFlexContainer = null;
		response = sendCreateFlexContainerRequest(flexContainer, baseLocation,
				Constants.ADMIN_REQUESTING_ENTITY);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			// KO
			createTestReport("testDeleteFlexContainerWithNoRight", Status.KO,
					"error : expected " + ResponseStatusCode.CREATED + ", received:" + response.getResponseStatusCode(),
					null);
			return;
		} else {
			createdFlexContainer = (BinarySwitchFlexContainer) response.getContent();
		}

		// try to delete the flexContainer with greg:greg ==> expect
		// ACCESS_DENIED
		response = sendDeleteRequest(flexContainerLocation, "greg:greg");
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.ACCESS_DENIED)) {
			createTestReport(
					"testDeleteFlexContainerWithNoRight", Status.KO, "error : expected "
							+ ResponseStatusCode.ACCESS_DENIED + ", received:" + response.getResponseStatusCode(),
					null);
			return;
		}

		// try to retrieve the FlexContainer using greg:greg credentials ==> ok
		response = sendRetrieveRequest(flexContainerLocation, "greg:greg");
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			createTestReport("testDeleteFlexContainerWithNoRight", Status.KO, 
					"error : expected " + ResponseStatusCode.OK + ", received:" + response.getResponseStatusCode(),
					null);
			return;
		} else {
			try {
				checkFlexContainer(createdFlexContainer, (BinarySwitchFlexContainer) response.getContent());
			} catch (Exception e) {
				// KO
				createTestReport("testDeleteFlexContainerWithNoRight", Status.KO,
						"flexContainers are differents:" + e.getMessage(), e);
			}
		}

		createTestReport("testDeleteFlexContainerWithNoRight", Status.OK, null, null);
	}
}

