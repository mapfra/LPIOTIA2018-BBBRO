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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.eclipse.om2m.client.java.tools.RestHttpClient;
import org.json.JSONObject;

public class INAE extends TestConfig{
	
	private static String aeRN = "smartphone_ae";
	private static String aeRId = "Csmartphone_ae"; 
	private static String groupRN = "containers_grp"; 
	
	public static void main(String[] args) throws Exception {
		 Init(); 
		 JSONObject obj, resource;
		 while (true) {
			 	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		        System.out.print("Switch light (ON/OFF) ");
		        String s = br.readLine();
		        
				obj = new JSONObject(); 
				resource = new JSONObject();
				obj.put("cnf", "application/text"); 
				obj.put("con", s); 
				resource.put("m2m:cin", obj); 
				RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId+"/"+remoteCseName+"/"+groupRN+"/"+"fopt", resource.toString(), 4);
				
		 }
	}
	
	private static void Init () throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("rn", aeRN); 
		obj.put("api", "A01.com.company.lightControlApp"); 
		obj.put("rr", "false"); 
		JSONObject resource = new JSONObject();
		resource.put("m2m:ae", obj); 
		RestHttpClient.post(aeRId, csePoa+"/~/"+cseId+"/"+cseName+"?rcn=1", resource.toString(), 2);
	
	}
}
