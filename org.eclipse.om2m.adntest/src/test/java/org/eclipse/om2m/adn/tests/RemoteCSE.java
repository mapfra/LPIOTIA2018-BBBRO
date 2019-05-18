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

public class RemoteCSE extends TestConfig {
	
	private static String remoteCSETestRU = "remoteCSETestRU";
	private static String remoteCSETestD = "remoteCSETestD";
	private static String remoteCSETestC = "remoteCSETestC";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JSONObject obj = new JSONObject(); 
		obj.put("cb", remoteCSETestRU); 
		obj.put("rn", remoteCSETestRU);
		obj.put("csi", remoteCSETestRU); 
		obj.put("rr", true); 
		JSONArray array = new JSONArray();
		array.put(remoteCsePoa+"/");
		obj.put("poa", array);
		JSONObject resource = new JSONObject();
		resource.put("m2m:csr", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 16);
		
		obj = new JSONObject(); 
		obj.put("cb", remoteCSETestD); 
		obj.put("rn", remoteCSETestD);
		obj.put("csi", remoteCSETestD); 
		obj.put("rr", true); 
		array = new JSONArray();
		array.put(remoteCsePoa+"/");
		obj.put("poa", array);
		resource = new JSONObject();
		resource.put("m2m:csr", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 16);
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+remoteCSETestRU);
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+remoteCSETestC);
	}

	@Test
	public void testCreate() {
		JSONObject obj = new JSONObject(); 
		obj.put("cb", remoteCSETestC); 
		obj.put("rn", remoteCSETestC);
		obj.put("csi", remoteCSETestC); 
		obj.put("rr", true); 
		JSONArray array = new JSONArray();
		array.put(remoteCsePoa+"/");
		obj.put("poa", array);
		JSONObject resource = new JSONObject();
		resource.put("m2m:csr", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 16);
	}

	@Test 
	public void testRetreive() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+remoteCSETestRU);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testDelete() {
		HttpResponse httpResponse = RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+remoteCSETestD);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testUpdate(){
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		array.put("http://127.0.0.1:8585/");
		obj.put("poa", array);
		JSONObject resource = new JSONObject();
		resource.put("m2m:csr", obj);
		HttpResponse httpResponse = RestHttpClient.put(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+remoteCSETestRU, resource.toString());
		assertEquals(200, httpResponse.getStatusCode());
	}
	
}
