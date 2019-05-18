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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.eclipse.om2m.adn.tools.HttpResponse;
import org.eclipse.om2m.adn.tools.RestHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Subscription extends TestConfig {
	
	private static String aeName = "aeTest";
	private static String cntName = "cntTest";
	private static String subTestRU = "subTestRU"; 
	private static String subTestD = "subTestD"; 
	private static String subTestC = "subTestC"; 
	
	private static String aeProtocol="http";
	private static String aeIp = "127.0.0.1";
	private static int aePort = 1401;	
	private static String notificationURI = aeProtocol+"://"+aeIp+":"+aePort;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//Starting Server
		HttpServer server = null;
		try {
			server = HttpServer.create(new InetSocketAddress(aePort), 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.createContext("/", new MyHandler());
		server.setExecutor(Executors.newCachedThreadPool());
		server.start();
		
	
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
		
	
	
		JSONArray array = new JSONArray();
		array.put(notificationURI);
		obj = new JSONObject();
		obj.put("nu", array);
		obj.put("rn", subTestRU);
		obj.put("nct", 2);
		resource = new JSONObject();		
		resource.put("m2m:sub", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName, resource.toString(), 23);
		

		array = new JSONArray();
		array.put(notificationURI);
		obj = new JSONObject();
		obj.put("nu", array);
		obj.put("rn", subTestD);
		obj.put("nct", 2);
		resource = new JSONObject();		
		resource.put("m2m:sub", obj);
		RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName, resource.toString(), 23);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName);
	}

	@Test
	public void testCreate() {
		JSONArray array = new JSONArray();
		array.put(notificationURI);
		JSONObject obj = new JSONObject();
		obj.put("nu", array);
		obj.put("rn", subTestC);
		obj.put("nct", 2);
		JSONObject resource = new JSONObject();		
		resource.put("m2m:sub", obj);
		HttpResponse httpResponse = RestHttpClient.post(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName, resource.toString(), 23);
		assertEquals(201, httpResponse.getStatusCode());
	}
	
	@Test
	public void testRetreive() {
		HttpResponse httpResponse = RestHttpClient.get(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName+"/"+subTestRU);
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test
	public void testUpdate() {
		JSONObject obj = new JSONObject();
		obj.put("et", "20181228T164835");
		JSONObject resource = new JSONObject();
		resource.put("m2m:sub", obj);
		HttpResponse httpResponse = RestHttpClient.put(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName+"/"+subTestRU, resource.toString());
		assertEquals(200, httpResponse.getStatusCode());
	}
	
	@Test 
	public void TestDelete() {
		HttpResponse httpResponse = RestHttpClient.delete(originator, csePoa+"/~/"+cseId+"/"+cseName+"/"+aeName+"/"+cntName+"/"+subTestD);
		assertEquals(200, httpResponse.getStatusCode());
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
