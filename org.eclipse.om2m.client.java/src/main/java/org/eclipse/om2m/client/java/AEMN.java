/*******************************************************************************
 * Copyright (c) 2016- 2017 SENSINOV (www.sensinov.com)
 * 41 Rue de la découverte 31676 Labège - France 
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.om2m.client.java;


import org.eclipse.om2m.client.java.tools.RestHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class AEMN extends TestConfig {
	private static String aeRN = "gateway_ae"; 
	private static String aeRId = "Cgateway_ae"; 
	private static String aeRN_1 = "light_ae1"; 
	private static String aeRId_1 = "Clight_ae1"; 
	private static String aeRN_2 = "light_ae2"; 
	private static String aeRId_2 = "Clight_ae2"; 
	private static String aeRId_3 = "Csmartphone_ae"; 
	private static String cnRN = "light"; 
	private static String acpRN = "MN-CSEAcp";
	private static String groupRN = "containers_grp"; 
	
	public static void main(String[] args) throws Exception {
		
		/** Application Registry **/
		JSONObject obj = new JSONObject();
		obj.put("rn", aeRN);
		obj.put("api", "A01.com.company.gatewayApp");
		obj.put("rr", false); 
		JSONObject resource = new JSONObject();
		resource.put("m2m:ae", obj); 
		RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId+"/"+remoteCseName+"?rcn=1", resource.toString(), 2);
		
		/** Access right resource creation **/ 
		JSONArray acor = new JSONArray();
		acor.put(aeRId); 
		acor.put(aeRId_1);
		acor.put(aeRId_2);
		acor.put(aeRId_3); 
		acor.put("admin:admin"); 
		
		JSONObject item = new JSONObject();
		item.put("acor", acor);
		item.put("acop", 63);
		
		JSONObject acr_1 = new JSONObject();
		acr_1.put("acr",item);
		
		acor = new JSONArray();
		acor.put(aeRN); 
		acor.put("admin:admin");
		
		item = new JSONObject();
		item.put("acor", acor);
		item.put("acop", 63);
		
		JSONObject acr_2 = new JSONObject();
		acr_2.put("acr",item);
		
		JSONObject obj_1 = new JSONObject(); 
		obj_1.put("rn", acpRN); 
		obj_1.put("pv", acr_1); 
		obj_1.put("pvs", acr_2); 
		
		resource = new JSONObject();
		resource.put("m2m:acp", obj_1);
		
		RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId+"/"+remoteCseName+"/", resource.toString(), 1);
		
		
		/** Group resource creation **/
		JSONArray array = new JSONArray();
		array.put("/"+remoteCseId+"/"+remoteCseName+"/"+aeRN_1+"/"+cnRN);
		array.put("/"+remoteCseId+"/"+remoteCseName+"/"+aeRN_2+"/"+cnRN);
		obj = new JSONObject();
		obj.put("mid", array);
		obj.put("rn", groupRN); 
		obj.put("mnm", 3);
		resource = new JSONObject();		
		resource.put("m2m:grp", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId+"/"+remoteCseName, resource.toString(), 9); 
	}

}
