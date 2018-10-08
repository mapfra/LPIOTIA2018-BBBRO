/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.datamapping.jaxb;

import static org.junit.Assert.*;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BinarySwitchFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.BrightnessFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ColourFlexContainer;
import org.eclipse.om2m.commons.resource.flexcontainerspec.ColourSaturationFlexContainer;
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
	
	@Test
	public void testDatashareStringToObj_json() {
		String xmlPayload = readFile("src/test/resources/deviceLightDatashareFlexContainer.json");
		
		DeviceLightFlexContainer deviceLightFlexContainer = (DeviceLightFlexContainer) jsonMapper.stringToObj(xmlPayload);
		
		assertNotNull(deviceLightFlexContainer);
		
		assertFalse(deviceLightFlexContainer.getFlexContainerOrContainerOrSubscription().isEmpty());
		assertTrue(deviceLightFlexContainer.getFlexContainerOrContainerOrSubscription().size() == 4);
		
		ColourSaturationFlexContainer colorSaturationFlexContainer = null;
		ColourFlexContainer colourFlexContainer = null;
		BrightnessFlexContainer brightnessFlexContainer = null;
		BinarySwitchFlexContainer binarySwitchFlexContainer = null;
		for(Object o : deviceLightFlexContainer.getFlexContainerOrContainerOrSubscription()) {
			if (o instanceof ColourSaturationFlexContainer) {
				colorSaturationFlexContainer = (ColourSaturationFlexContainer) o;
			} else if (o instanceof ColourFlexContainer) {
				colourFlexContainer = (ColourFlexContainer) o;
			} else if (o instanceof BrightnessFlexContainer) {
				brightnessFlexContainer = (BrightnessFlexContainer) o;
			} else if (o instanceof BinarySwitchFlexContainer) {
				binarySwitchFlexContainer = (BinarySwitchFlexContainer) o;
			}
		}
		
		assertNotNull(colorSaturationFlexContainer);
		assertNotNull(colourFlexContainer);
		assertNotNull(brightnessFlexContainer);
		assertNotNull(binarySwitchFlexContainer);
		
		// colorSaturation
		assertTrue("org.onem2m.home.moduleclass.colourSaturation".equals(colorSaturationFlexContainer.getContainerDefinition()));
		assertTrue(colorSaturationFlexContainer.getResourceType().intValue() == 28);
		assertTrue(!colorSaturationFlexContainer.getCustomAttributes().isEmpty());
		System.out.println("nb of custumAttributes: " + colorSaturationFlexContainer.getCustomAttributes().size());
		System.out.println("=======================================================");
		for(CustomAttribute ca : colorSaturationFlexContainer.getCustomAttributes()) {
			System.out.println("CA longName=" + ca.getLongName());
			System.out.println("CA shortName=" + ca.getShortName());
			System.out.println("CA value=" + ca.getValue());
			System.out.println("CA type=" + ca.getType());
			System.out.println("------------------------------------");
		}
		System.out.println("=======================================================");
		assertTrue(colorSaturationFlexContainer.getCustomAttributes().size() == 1);
		CustomAttribute colSn = colorSaturationFlexContainer.getCustomAttribute("colSn");
		assertNotNull(colSn);
		assertTrue("xs:integer".equals(colSn.getType()));
		System.out.println("colSn value=" + colSn.getValue());
		assertTrue("73".equals(colSn.getValue()));
		assertTrue("colSn".equals(colSn.getShortName()));
		assertTrue("colourSaturation".equals(colSn.getLongName()));
		
		
		// color
		assertTrue("org.onem2m.home.moduleclass.colour".equals(colourFlexContainer.getContainerDefinition()));
		assertTrue(colourFlexContainer.getResourceType().intValue() == 28);
		assertTrue(!colourFlexContainer.getCustomAttributes().isEmpty());
		assertTrue(colourFlexContainer.getCustomAttributes().size() == 3);
		CustomAttribute red = colourFlexContainer.getCustomAttribute("red");
		assertNotNull(red);
		assertTrue("red".equals(red.getShortName()));
		assertTrue("red".equals(red.getLongName()));
		assertTrue("xs:integer".equals(red.getType()));
		assertTrue("66".equals(red.getValue()));
		CustomAttribute blue = colourFlexContainer.getCustomAttribute("blue");
		assertNotNull(blue);
		assertTrue("blue".equals(blue.getShortName()));
		assertTrue("blue".equals(blue.getLongName()));
		assertTrue("xs:integer".equals(blue.getType()));
		assertTrue("244".equals(blue.getValue()));
		CustomAttribute green = colourFlexContainer.getCustomAttribute("green");
		assertNotNull(green);
		assertTrue("green".equals(green.getShortName()));
		assertTrue("green".equals(green.getLongName()));
		assertTrue("xs:integer".equals(green.getType()));
		assertTrue("115".equals(green.getValue()));
		
		// brightness
		assertTrue("org.onem2m.home.moduleclass.brightness".equals(brightnessFlexContainer.getContainerDefinition()));
		assertTrue(brightnessFlexContainer.getResourceType().intValue() == 28);
		assertTrue(!brightnessFlexContainer.getCustomAttributes().isEmpty());
		assertTrue(brightnessFlexContainer.getCustomAttributes().size() == 1);
		CustomAttribute brigs = brightnessFlexContainer.getCustomAttribute("brigs");
		assertNotNull(brigs);
		assertTrue("brigs".equals(brigs.getShortName()));
		assertTrue("brightness".equals(brigs.getLongName()));
		assertTrue("xs:integer".equals(brigs.getType()));
		assertTrue("96".equals(brigs.getValue()));
		
		// binarySwitch
		assertTrue("org.onem2m.home.moduleclass.binarySwitch".equals(binarySwitchFlexContainer.getContainerDefinition()));
		assertTrue(binarySwitchFlexContainer.getResourceType().intValue() == 28);
		assertTrue(!binarySwitchFlexContainer.getCustomAttributes().isEmpty());
		assertTrue(binarySwitchFlexContainer.getCustomAttributes().size() == 1);
		CustomAttribute powerState = binarySwitchFlexContainer.getCustomAttribute("powSe");
		assertNotNull(powerState);
		assertTrue("powerState".equals(powerState.getLongName()));
		assertTrue("powSe".equals(powerState.getShortName()));
		assertTrue("xs:boolean".equals(powerState.getType()));
		assertTrue("false".equals(powerState.getValue()));
		ToggleFlexContainer toggle = binarySwitchFlexContainer.getToggle();
		assertNotNull(toggle);
		assertTrue("org.onem2m.home.moduleclass.binarySwitch.toggle".equals(toggle.getContainerDefinition()));
		assertTrue(toggle.getResourceType().intValue() == 28);
		
//		assertNotNull(binarySwitchFlexContainer.getToggle());
//		assertNotNull(binarySwitchFlexContainer.getFlexContainerOrContainerOrSubscription());
//		assertFalse(binarySwitchFlexContainer.getFlexContainerOrContainerOrSubscription().isEmpty());
//		assertTrue(binarySwitchFlexContainer.getFlexContainerOrContainerOrSubscription().size() == 1);
//		
//		Object toggleObject = binarySwitchFlexContainer.getFlexContainerOrContainerOrSubscription().get(0);
//		assertTrue(toggleObject instanceof ToggleFlexContainer);
				
		
	}

}
