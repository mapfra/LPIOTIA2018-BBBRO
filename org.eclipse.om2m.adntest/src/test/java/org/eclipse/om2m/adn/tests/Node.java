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

public class Node extends TestConfig {
	
	private static String nodeTestRU = "nodeRU";
	private static String nodeTestD = "nodeD";
	private static String nodeTestC = "nodeC";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("rn", nodeTestRU);
		obj.put("ni", "node-test");
		JSONObject resource = new JSONObject();
		resource.put("m2m:nod", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 14);
		
		obj = new JSONObject();
		obj.put("rn", nodeTestD);
		obj.put("ni", "node-test2");
		resource = new JSONObject();		
		resource.put("m2m:nod", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 14);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+nodeTestC);
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+nodeTestRU);
	}

	@Test
	public void testCreate() {
		JSONObject obj = new JSONObject();
		obj.put("rn", nodeTestC);
		obj.put("ni", "node-test3");
		JSONObject resource = new JSONObject();
		resource.put("m2m:nod", obj);
		HttpResponse httpResponse = RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 14);
		assertEquals(201, httpResponse.getStatusCode());
	}
	
	@Test
	public void testRetreive() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+nodeTestRU);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testUpdate(){
		JSONObject obj = new JSONObject();
		obj.put("et", "20181228T164835");
		JSONObject resource = new JSONObject();
		resource.put("m2m:nod", obj);
		HttpResponse httpResponse = RestHttpClient.put(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+nodeTestRU, resource.toString());
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testDelete() {
		HttpResponse httpResponse = RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+nodeTestD);
		assertEquals(200, httpResponse.getStatusCode());
	}

}
