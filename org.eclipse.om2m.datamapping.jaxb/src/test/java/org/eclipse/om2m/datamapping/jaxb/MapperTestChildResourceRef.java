package org.eclipse.om2m.datamapping.jaxb;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapperTestChildResourceRef {

	private Mapper jsonMapper;
	private Mapper xmlMapper;

	@Before
	public void setUp() throws Exception {
		jsonMapper = new Mapper(MimeMediaType.JSON);
		xmlMapper = new Mapper(MimeMediaType.XML);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		FlexContainer flexContainer = new FlexContainer();
		ChildResourceRef childResourceRef = new ChildResourceRef();
		childResourceRef.setResourceName("totoname");
		childResourceRef.setSpid("totospid");
		childResourceRef.setType(28);
		childResourceRef.setValue("totoValue");
		flexContainer.getChildResource().add(childResourceRef);

		ChildResourceRef childResourceRef2 = new ChildResourceRef();
		childResourceRef2.setResourceName("totoname2");
		childResourceRef2.setSpid("totospid2");
		childResourceRef2.setType(28);
		childResourceRef2.setValue("totoValue2");
		flexContainer.getChildResource().add(childResourceRef2);

		String flexContainerAsString = jsonMapper.objToString(flexContainer);
		String flexContainerAsXmlString = xmlMapper.objToString(flexContainer);

		System.out.println(flexContainerAsString);
		System.out.println(flexContainerAsXmlString);

		FlexContainer fcntFromString = (FlexContainer) jsonMapper.stringToObj(flexContainerAsString);
		

		System.out.println(fcntFromString.getChildResource().size());
		for (ChildResourceRef crr : fcntFromString.getChildResource()) {
			System.out.println(crr.getResourceName());
			System.out.println(crr.getSpid());
			System.out.println(crr.getValue());
			System.out.println(crr.getType());
		}

	}

	@Test
	public void test2() {

		String flexContainerString = "{\r\n" + 
				"   \"m2m:fcnt\" : {\r\n" + 
				"	  \"cnd\" : \"totoCnd\",\r\n" + 
				"      \"ch\" : [ {\r\n" + 
				"         \"nm\" : \"totoname\",\r\n" + 
				"         \"typ\" : 28,\r\n" + 
				"         \"spid\" : \"totospid\",\r\n" + 
				"         \"val\" : \"totoValue\"\r\n" + 
				"      }, {\r\n" + 
				"         \"nm\" : \"totoname2\",\r\n" + 
				"         \"typ\" : 28,\r\n" + 
				"         \"spid\" : \"totospid2\",\r\n" + 
				"         \"val\" : \"totoValue2\"\r\n" + 
				"      } ]\r\n" + 
				"   }\r\n" + 
				"}";

		FlexContainer fcntFromString = (FlexContainer) jsonMapper.stringToObj(flexContainerString);

		assertTrue(fcntFromString != null);
		assertTrue(fcntFromString.getChildResource().size() == 2);
		
		for(ChildResourceRef childRed : fcntFromString.getChildResource()) {
			if (childRed.getResourceName().equals("totoname2")) {
				assertTrue(childRed.getSpid().equals("totospid2"));
				assertTrue(childRed.getType().equals(new BigInteger("28")));
				assertTrue(childRed.getValue().equals("totoValue2"));
			} else if (childRed.getResourceName().equals("totoname")) {
				assertTrue(childRed.getSpid().equals("totospid"));
				assertTrue(childRed.getType().equals(new BigInteger("28")));
				assertTrue(childRed.getValue().equals("totoValue"));
			} else {
				assertFalse(true);
			}
		}
		

	}
	
	
	@Test
	public void test3() {
		String json = "{\r\n" + 
				"   \"m2m:fcnt\" : {\r\n" + 
				"	  \"cnd\" : \"totoCnd\",\r\n" + 
				"      \"ch\": [{\r\n" + 
				"            \"val\": \"/in-cse/fcnt-3016*8929*hue:LCT001@001788fffe16af9e~L2-module-binarySwitch\",\r\n" + 
				"            \"nm\": \"org.onem2m.home.module.binarySwitch__3016*8929*hue:LCT001@001788fffe16af9e~L2\",\r\n" + 
				"            \"typ\": 28\r\n" + 
				"        }, {\r\n" + 
				"            \"val\": \"/in-cse/fcnt-3016*8929*hue:LCT001@001788fffe16af9e~L2-module-colour\",\r\n" + 
				"            \"nm\": \"org.onem2m.home.module.colour__3016*8929*hue:LCT001@001788fffe16af9e~L2\",\r\n" + 
				"            \"typ\": 28\r\n" + 
				"        }, {\r\n" + 
				"            \"val\": \"/in-cse/fcnt-3016*8929*hue:LCT001@001788fffe16af9e~L2-module-colourSaturation\",\r\n" + 
				"            \"nm\": \"org.onem2m.home.module.colourSaturation__3016*8929*hue:LCT001@001788fffe16af9e~L2\",\r\n" + 
				"            \"typ\": 28,\r\n" + 
				"            \"spid\": \"toto\"\r\n" + 
				"        }]\r\n" + 
				"\r\n" + 
				"   }\r\n" + 
				"}";
		
		FlexContainer fcntFromString = (FlexContainer) jsonMapper.stringToObj(json);

		System.out.println(fcntFromString.getChildResource().size());
		for (ChildResourceRef crr : fcntFromString.getChildResource()) {
			System.out.println("new child");
			System.out.println("\t nm:" + crr.getResourceName());
			System.out.println("\t spid:" + crr.getSpid());
			System.out.println("\t val:" + crr.getValue());
			System.out.println("\t type:" + crr.getType());
		}
		System.out.println(fcntFromString.getContainerDefinition());
		
	}

}
