package org.eclipse.om2m.datamapping.jaxb;

import static org.junit.Assert.*;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BinarySwitchFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.DeviceLightFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ToggleFlexContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapperTestDeviceLightFlexContainer extends AbstractMapperTest {

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
	public void testStringToObj_json() {
		String xmlPayload = readFile("src/test/resources/deviceLightFlexContainer.json");
		
		DeviceLightFlexContainer deviceLightFlexContainer = (DeviceLightFlexContainer) jsonMapper.stringToObj(xmlPayload);
		
		assertNotNull(deviceLightFlexContainer);
		
		assertFalse(deviceLightFlexContainer.getFlexContainerOrContainerOrSubscription().isEmpty());
		assertTrue(deviceLightFlexContainer.getFlexContainerOrContainerOrSubscription().size() == 1);
		
		Object binarySwitchObject = deviceLightFlexContainer.getFlexContainerOrContainerOrSubscription().get(0);
		assertTrue(binarySwitchObject instanceof BinarySwitchFlexContainer);
		
		BinarySwitchFlexContainer binarySwitchFlexContainer = (BinarySwitchFlexContainer) binarySwitchObject;
		assertNotNull(binarySwitchFlexContainer.getToggle());
		assertNotNull(binarySwitchFlexContainer.getFlexContainerOrContainerOrSubscription());
		assertFalse(binarySwitchFlexContainer.getFlexContainerOrContainerOrSubscription().isEmpty());
		assertTrue(binarySwitchFlexContainer.getFlexContainerOrContainerOrSubscription().size() == 1);
		
		Object toggleObject = binarySwitchFlexContainer.getFlexContainerOrContainerOrSubscription().get(0);
		assertTrue(toggleObject instanceof ToggleFlexContainer);
				
		
	}

}
