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

public class Group extends TestConfig {
	
	private static String groupTestRU = "groupTestRU";
	private static String groupTestD = "groupTestD";
	private static String groupTestC = "groupTestC";
	private static String aeName = "aeTest";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("rn", aeName);
		obj.put("api", 12345);
		obj.put("rr", false);
		JSONObject resource = new JSONObject();
		resource.put("m2m:ae", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 2);
		
		JSONArray array = new JSONArray();
		array.put("/"+cseId+"/"+cseName+"/"+aeName);
		obj = new JSONObject();
		obj.put("mid", array);
		obj.put("rn", groupTestRU); 
		obj.put("mnm", 3);
		resource = new JSONObject();		
		resource.put("m2m:grp", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 9);
		
		obj = new JSONObject();
		obj.put("mid", array);
		obj.put("rn", groupTestD); 
		obj.put("mnm", 3);
		resource = new JSONObject();		
		resource.put("m2m:grp", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 9);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupTestC);
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupTestRU);
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName);
	}

	@Test
	public void testCreate() {
		JSONArray array = new JSONArray();
		array.put("/"+cseId+"/"+cseName+"/"+aeName);
		JSONObject obj = new JSONObject();
		obj.put("mid", array);
		obj.put("rn", groupTestC); 
		obj.put("mnm", 3);
		JSONObject resource = new JSONObject();		
		resource.put("m2m:grp", obj);
		HttpResponse httpResponse = RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 9);
		assertEquals(201, httpResponse.getStatusCode());
	}
	
	@Test 
	public void testRetreive(){
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupTestRU);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testUpdate(){
		JSONObject obj = new JSONObject();
		obj.put("et", "20171228T164835");
		JSONObject resource = new JSONObject();
		resource.put("m2m:grp", obj);
		HttpResponse httpResponse = RestHttpClient.put(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupTestRU, resource.toString());
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test 
	public void testDelete() {
		HttpResponse httpResponse = RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupTestD);
		assertEquals(200, httpResponse.getStatusCode());
	}

}
