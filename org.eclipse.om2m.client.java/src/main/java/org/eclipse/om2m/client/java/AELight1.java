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

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.om2m.client.java.tools.RestHttpClient;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class AELight1 extends TestConfig {
	
	private static String aeRN = "light_ae1";
	private static String aeRId = "Clight_ae1"; 
	private static int AE_port = 9090; 
	private static String AE_adress = "http://127.0.0.1"; 
	private static String CT_name = "light"; 
		
	private static String notificationURI = AE_adress+":"+AE_port;
	private static String subRN = "subAE1"; 

	
	public static void Init () throws Exception {
		/** Application registry **/
		JSONObject obj = new JSONObject();
		obj.put("rn", aeRN);
		obj.put("api", "A01.com.company.lightApp01");
		obj.put("rr", true);
		JSONArray acpi = new JSONArray();
		acpi.put("/"+remoteCseId+"/"+remoteCseName+"/MN-CSEAcp"); 
		obj.put("acpi", acpi); 
		obj.put("poa", AE_adress+":"+AE_port);
		JSONObject resource = new JSONObject();
		resource.put("m2m:ae", obj);
		RestHttpClient.post(aeRId, csePoa+"/~/"+remoteCseId+"/"+remoteCseName+"?rcn=1", resource.toString(), 2);
		
		/** container creation **/ 
		obj = new JSONObject();
		obj.put("rn", CT_name); 
		resource = new JSONObject();
		resource.put("m2m:cnt", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId+"/"+remoteCseName+"/"+aeRN+"?rcn=2", resource.toString(), 3);
		
		/** contentInstance creation **/ 
		obj = new JSONObject(); 
		obj.put("cnf", "application/text"); 
		obj.put("con", "OFF"); 
		resource = new JSONObject();
		resource.put("m2m:cin", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId+"/"+remoteCseName+"/"+aeRN+"/"+CT_name+"?rcn=3", resource.toString(), 4);
	
		/**Subscription creation **/
		JSONArray array = new JSONArray();
		array.put(notificationURI);
		obj = new JSONObject();
		obj.put("nu", array);
		obj.put("rn", subRN);
		obj.put("nct", 2);
		resource = new JSONObject();		
		resource.put("m2m:sub", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+remoteCseId+"/"+remoteCseName+"/"+aeRN+"/"+CT_name, resource.toString(), 23);
		
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/** Starting Server **/
		HttpServer server = null;
		try {
			server = HttpServer.create(new InetSocketAddress(AE_port), 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			server.createContext("/", new MyHandler());
			server.setExecutor(Executors.newCachedThreadPool());
			server.start();
			
		/** Initial resources creation **/
		Init(); 
	}
	
	static class MyHandler implements HttpHandler {
		 
		public void handle(HttpExchange httpExchange)  {
			System.out.println("Event Recieved!");
 
			try{
				InputStream in = httpExchange.getRequestBody();
 
				String requestBody = "";
				int i;char c;
				while ((i = in.read()) != -1) {
					c = (char) i;
					requestBody = (String) (requestBody+c);
				}
 
				System.out.println(requestBody);
 
				String responseBudy ="";
				byte[] out = responseBudy.getBytes("UTF-8");
				httpExchange.sendResponseHeaders(200, out.length);
				OutputStream os =  httpExchange.getResponseBody();
				os.write(out);
				os.close();
 
			} catch(Exception e){
				e.printStackTrace();
			}		
		}
	}
}
