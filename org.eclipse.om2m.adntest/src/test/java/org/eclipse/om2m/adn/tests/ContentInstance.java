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

public class ContentInstance extends TestConfig{

	private static String aeName = "aeTestRU";
	private static String cntName = "cntTest";
	private static String cinTestRU = "cinTestRU";
	private static String cinTestD = "cinTestD";
	private static String cinTestC = "cinTestC";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	
		JSONObject obj = new JSONObject();
		obj.put("rn", aeName);
		obj.put("api", 12345);
		obj.put("rr", false);
		JSONObject resource = new JSONObject();
		resource.put("m2m:ae", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 2);
				
	
		obj = new JSONObject();
		obj.put("rn", cntName);
		resource = new JSONObject();
		resource.put("m2m:cnt", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName, resource.toString(), 3);
		
		
	
		obj = new JSONObject();
		obj.put("rn", cinTestRU);
		obj.put("cnf", "application/text");
		obj.put("con", 25);
		resource = new JSONObject();
		resource.put("m2m:cin", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName, resource.toString(), 4);
		
	
		obj = new JSONObject();
		obj.put("rn", cinTestD);
		obj.put("cnf", "application/text");
		obj.put("con", 35);
		resource = new JSONObject();
		resource.put("m2m:cin", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName, resource.toString(), 4);
	}

	//@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName);
	}
	
	@Test
	public void testCreate() {
		JSONObject obj = new JSONObject();
		obj.put("rn", cinTestC);
		obj.put("cnf", "application/text");
		obj.put("con", 30);
		JSONObject resource = new JSONObject();
		resource.put("m2m:cin", obj);
		HttpResponse httpResponse = RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName, resource.toString(), 4);
		assertEquals(201, httpResponse.getStatusCode());
	}
	
	@Test
	public void testRetreive() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName+"/"+cinTestRU);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testRetreiveLA() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName+"/la");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testRetreiveOL() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName+"/ol");
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	/*public void testUpdate() {
		JSONObject obj = new JSONObject();
		obj.put("et", "20161228T164835");
		JSONObject resource = new JSONObject();
		resource.put("m2m:cin", obj);
		HttpResponse httpResponse = RestHttpClient.put(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeCi+"/"+cnCi+"/"+cinTestRU, resource.toString());
		assertEquals(200, httpResponse.getStatusCode());
	}*/
	@Test
	public void testDelete() {
		HttpResponse httpResponse = RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName+"/"+cinTestD);
		assertEquals(200, httpResponse.getStatusCode());
	}

}
