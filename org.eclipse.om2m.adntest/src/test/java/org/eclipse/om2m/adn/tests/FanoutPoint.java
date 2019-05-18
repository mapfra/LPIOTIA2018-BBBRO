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

public class FanoutPoint extends TestConfig {

	private static String groupC = "groupTest";
	private static String groupRU = "groupRU"; 
	private static String groupD = "groupD";
	
	private static String cnTestC1 = "cnTestC1";
	private static String cnTestC2 = "cnTestC2";
	
	private static String cnTestRU1 = "cnTestRU1";
	private static String cnTestRU2 = "cnTestRU2";
	
	private static String cnTestD1 = "cnTestD1";
	private static String cnTestD2 = "cnTestD1";
	
	private static String cinTest = "cinTest";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JSONObject obj = new JSONObject();
        obj.put("rn", cnTestRU1);
        JSONObject resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 3);
          
        obj = new JSONObject();
        obj.put("rn", cnTestRU2);
        resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId, resource.toString(), 3);
		
		JSONArray array = new JSONArray();
		array.put("/"+cseId+"/"+cseName+"/"+cnTestRU1);
		array.put("/"+remoteCseId+"/"+remoteCseName+"/"+cnTestRU2);
		obj = new JSONObject();
		obj.put("mid", array);
		obj.put("rn", groupRU); 
		obj.put("mnm", 3);
		resource = new JSONObject();		
		resource.put("m2m:grp", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 9);
		
		obj = new JSONObject();
        obj.put("rn", cnTestD1);
        resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 3);
          
        obj = new JSONObject();
        obj.put("rn", cnTestD2);
        resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId, resource.toString(), 3);
		
		array = new JSONArray();
		array.put("/"+cseId+"/"+cseName+"/"+cnTestD1);
		array.put("/"+remoteCseId+"/"+remoteCseName+"/"+cnTestD2);
		obj = new JSONObject();
		obj.put("mid", array);
		obj.put("rn", groupD); 
		obj.put("mnm", 3);
		resource = new JSONObject();		
		resource.put("m2m:grp", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 9);
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupC);
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupRU);
	}

	@Test
	public void testCreate() {
		JSONObject obj = new JSONObject();
        obj.put("rn", cnTestC1);
        JSONObject resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 3);
          
        obj = new JSONObject();
        obj.put("rn", cnTestC2);
        resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId, resource.toString(), 3);
		
		JSONArray array = new JSONArray();
		array.put("/"+cseId+"/"+cseName+"/"+cnTestC1);
		array.put("/"+remoteCseId+"/"+remoteCseName+"/"+cnTestC2);
		obj = new JSONObject();
		obj.put("mid", array);
		obj.put("rn", groupC); 
		obj.put("mnm", 3);
		resource = new JSONObject();		
		resource.put("m2m:grp", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 9);
		
		obj = new JSONObject();
		obj.put("rn", cinTest);
		obj.put("cnf", "application/text");
		obj.put("con", 25);
		resource = new JSONObject();
		resource.put("m2m:cin", obj);
		HttpResponse httpResponse  = RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupC+"/fopt", resource.toString(), 4);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testRetreive() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupRU+"/fopt");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test 
	public void testDelete() {
		HttpResponse httpResponse = RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupD+"/fopt");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testUpdate() {
		JSONObject obj = new JSONObject();
		obj.put("et", "20181228T164835");
		JSONObject resource = new JSONObject();
		resource.put("m2m:cnt", obj);
		HttpResponse httpResponse = RestHttpClient.put(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+groupRU+"/fopt", resource.toString());
		assertEquals(200, httpResponse.getStatusCode());
	}

}
