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
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class Container extends TestConfig {
	
	private static String aeC = "aeTestRU";
	private static String cnTestRU = "cnTestRU";
	private static String cnTestD = "cnTestD";
	private static String cnTestC = "cnTestC";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		JSONObject obj = new JSONObject();
		obj.put("rn", aeC);
		obj.put("api", 12345);
		obj.put("rr", false);
		JSONObject resource = new JSONObject();
		resource.put("m2m:ae", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 2);
		
	
		obj = new JSONObject();
        obj.put("rn", cnTestRU);
        resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeC, resource.toString(), 3);
        
  
        obj = new JSONObject();
        obj.put("rn", cnTestD);
        resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeC, resource.toString(), 3);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeC);
	}

	@Test
	public void testCreate() {
		JSONObject obj = new JSONObject();
		obj.put("rn", cnTestC);
		JSONObject resource = new JSONObject();
        resource.put("m2m:cnt", obj);
		HttpResponse httpResponse = RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeC, resource.toString(), 3);
		assertEquals(201, httpResponse.getStatusCode());
	}
	
	@Test
	public void testRetreive() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeC+"/"+cnTestRU);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testUpdate() {
		JSONObject obj = new JSONObject();
		obj.put("et", "20181228T164835");
		JSONObject resource = new JSONObject();
		resource.put("m2m:cnt", obj);
		HttpResponse httpResponse = RestHttpClient.put(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeC+"/"+cnTestRU, resource.toString());
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testDelete() {
		HttpResponse httpResponse = RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeC+"/"+cnTestD);
		assertEquals(200, httpResponse.getStatusCode());
	}
}
