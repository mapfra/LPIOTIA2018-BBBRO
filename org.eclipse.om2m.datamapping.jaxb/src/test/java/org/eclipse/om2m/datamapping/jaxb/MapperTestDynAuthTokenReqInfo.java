package org.eclipse.om2m.datamapping.jaxb;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.resource.DasInfo;
import org.eclipse.om2m.commons.resource.DynAuthDasRequest;
import org.eclipse.om2m.commons.resource.DynAuthTokenReqInfo;
import org.junit.Before;
import org.junit.Test;

public class MapperTestDynAuthTokenReqInfo {

	private Mapper xmlMapper;
	private Mapper jsonMapper;

	@Before
	public void setUp() throws Exception {
		xmlMapper = new Mapper(MimeMediaType.XML);
		jsonMapper = new Mapper(MimeMediaType.JSON);
	}
	
	@Test
	public void test_xmlObjToString() {

		DynAuthTokenReqInfo tokenReqInfo = new DynAuthTokenReqInfo();
		DasInfo dasInfo = new DasInfo();
		dasInfo.setURI("http://test");
		dasInfo.setDasRequest(new DynAuthDasRequest());
		dasInfo.getDasRequest().setOperation(Operation.RETRIEVE);
		dasInfo.getDasRequest().setOriginator("originator");
		dasInfo.getDasRequest().setTargetedResourceID("/incse/resource1");
		dasInfo.getDasRequest().setTargetedResourceType(BigInteger.valueOf(ResourceType.FLEXCONTAINER));
		dasInfo.getDasRequest().getTokenIDs().add("token1");
		dasInfo.getDasRequest().getTokenIDs().add("token2");
		tokenReqInfo.getDasInfo().add(dasInfo);
		
		System.out.println(xmlMapper.objToString(tokenReqInfo));
		
	}
	
	@Test
	public void test_jsonObjToString() {

		DynAuthTokenReqInfo tokenReqInfo = new DynAuthTokenReqInfo();
		DasInfo dasInfo = new DasInfo();
		dasInfo.setURI("http://test");
		dasInfo.setDasRequest(new DynAuthDasRequest());
		dasInfo.getDasRequest().setOperation(Operation.RETRIEVE);
		dasInfo.getDasRequest().setOriginator("originator");
		dasInfo.getDasRequest().setTargetedResourceID("/incse/resource1");
		dasInfo.getDasRequest().setTargetedResourceType(BigInteger.valueOf(ResourceType.FLEXCONTAINER));
		dasInfo.getDasRequest().getTokenIDs().add("token1");
		dasInfo.getDasRequest().getTokenIDs().add("token2");
		tokenReqInfo.getDasInfo().add(dasInfo);
		
		System.out.println(jsonMapper.objToString(tokenReqInfo));
		
	}

}
