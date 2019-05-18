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

public class ACP extends TestConfig{	
	private static String acpTestRU = "acpTestRU";
	private static String acpTestD = "aeTestD";
	private static String acpTestC = "acpTestC";
	private static String aeName = "aeTestRU";
	private static String cntName = "cntTest";
	private static String badOriginator = "guest:guest";	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("rn", aeName);
		obj.put("api", 12345);
		obj.put("rr", false);
		JSONObject resource = new JSONObject();
		resource.put("m2m:ae", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName, resource.toString(), 2);
        
		JSONArray acor = new JSONArray();
		acor.put("admin:admin");
		//acor.put("cae-admin");
		JSONObject item = new JSONObject();
		item.put("acor", acor);
		item.put("acop", 63);
		JSONArray acr1 = new JSONArray();
		acr1.put(item);
		
		acor = new JSONArray();
		acor.put("guest:guest");
		acor.put("admin:admin");
		//acor.put("cae-admin");
		item = new JSONObject();
		item.put("acor", acor);
		item.put("acop", 34);
		JSONArray acr2 = new JSONArray();
		acr2.put(item);
		
		JSONObject pv = new JSONObject();
		pv.put("acr",acr1);	
		pv.put("acr",acr2);	
		JSONObject pvs = new JSONObject();
		pvs.put("acr", acr1);
		obj = new JSONObject();
		obj.put("rn", acpTestRU);
		obj.put("pv", pv);	
		obj.put("pvs", pvs);	
		resource = new JSONObject();
		resource.put("m2m:acp", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName, resource.toString(), 1);
		
		obj = new JSONObject();
		obj.put("rn", acpTestD);
		obj.put("pv", pv);
		obj.put("pvs", pvs);	
		resource = new JSONObject();
		resource.put("m2m:acp", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName, resource.toString(), 1);

		JSONArray acpi = new JSONArray();
		acpi.put("/"+cseId+"/"+cseName+"/"+aeName+"/"+acpTestRU);
		obj = new JSONObject();
        obj.put("acpi",acpi); 
        obj.put("rn", cntName);
        resource = new JSONObject();
        resource.put("m2m:cnt", obj);
        RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName, resource.toString(), 3);  
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName);
	}
	
	@Test
	public void testCreate() {	
		JSONArray acor = new JSONArray();
		acor.put("admin:admin");
		//acor.put("cae-admin");

		JSONObject item = new JSONObject();
		item.put("acor", acor);
		item.put("acop", 63);
		
		JSONArray acr = new JSONArray();
		acr.put(item);
		
		JSONObject pv = new JSONObject();
		pv.put("acr",acr);
		
		JSONObject pvs = new JSONObject();
		pvs.put("acr", acr);	
		
		JSONObject obj = new JSONObject();
		obj.put("rn", acpTestC);
		obj.put("pv", pv);	
		obj.put("pvs", pv);	
		
		JSONObject resource = new JSONObject();
		resource.put("m2m:acp", obj);
				
		HttpResponse httpResponse = RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName, resource.toString(), 1);
		assertEquals(201, httpResponse.getStatusCode());
	}
	
	@Test
	public void testRetreive() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+acpTestRU);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testUpdate() {
		JSONArray acor = new JSONArray();
		//acor.put("cae-admin");
		acor.put("*");
		
		JSONObject item = new JSONObject();
		item.put("acor", acor);
		item.put("acop", 63);
		
		JSONArray acr = new JSONArray();
		acr.put(item);
		
		JSONObject pv = new JSONObject();
		pv.put("acr",acr);
		
		JSONObject pvs = new JSONObject();
		pvs.put("acr", acr);
			
		
		JSONObject obj = new JSONObject();
		obj.put("pv", pv);	
		obj.put("pvs", pv);	
		
		JSONObject resource = new JSONObject();
		resource.put("m2m:acp", obj);
				
		HttpResponse httpResponse = RestHttpClient.put(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+acpTestRU, resource.toString());
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testDelete() {
		HttpResponse httpResponse = RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+acpTestD);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testNonAuthorizedOperation() {
		HttpResponse httpResponse = RestHttpClient.delete(badOriginator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName);
		assertEquals(403, httpResponse.getStatusCode());
	}

}

