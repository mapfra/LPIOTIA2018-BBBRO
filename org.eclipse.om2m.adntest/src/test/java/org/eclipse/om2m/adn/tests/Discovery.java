/*******************************************************************************
 * Copyright (c) 2016- 2017 SENSINOV (www.sensinov.com)
 * 41 Rue de la découverte 31676 Labège - France 
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.adn.tests;

import static org.junit.Assert.*;

import org.eclipse.om2m.adn.tools.HttpResponse;
import org.eclipse.om2m.adn.tools.RestHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Discovery extends TestConfig {

	private static String aeName = "aeTest";
	
	//@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	 JSONObject obj = new JSONObject();
	 obj.put("rn", aeName);
	 obj.put("api", 12345);
	 obj.put("rr", false);
	 JSONArray array = new JSONArray();
	 array.put("Type/Sensor"); 
	 array.put("Category/Luminosity"); 
	 array.put("Manufactor/Philips"); 
	 obj.put("lbl", array);
	 JSONObject resource = new JSONObject();
	 resource.put("m2m:ae", obj);
	 RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 2);
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName);
	}
	
	@Test
	public void testAllResources() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void TestWithFilterCriteria() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&ty=2");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void TestWithLimitFilterCriteria() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&lim=1");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void TestWithLabelFilterCriteria() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&lbl=Manufactor/Philips");
		assertEquals(200, httpResponse.getStatusCode());
	}

	@Test
	public void TestWithMultipleFilterCriteria() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&ty=1&lbl=Type/Sensor");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void TestWithLevelFilterCriteria() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&lvl=1");
		assertEquals(200, httpResponse.getStatusCode());
		httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&lvl=2");
		assertEquals(200, httpResponse.getStatusCode());
		httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&lvl=3");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void TestWithOffsetFilterCriteria() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&ofst=3&lim=1");
		assertEquals(200, httpResponse.getStatusCode());
		httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"?fu=1&ofst=3");
		assertEquals(200, httpResponse.getStatusCode());
	}

}
