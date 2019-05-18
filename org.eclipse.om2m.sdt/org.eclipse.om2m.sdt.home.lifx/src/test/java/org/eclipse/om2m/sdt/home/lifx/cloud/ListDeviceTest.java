/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import junit.framework.TestCase;

public class ListDeviceTest extends TestCase {

	private static final String TOKEN = "c9d25974b9b977052623d25de0deed93ea432b2ee11b4ce6debafa11ab5f4560";
	
	public void testGetLigths() {
		
		
		String url = "https://api.lifx.com/v1/lights/all";
		
		try {
			URL u = new URL(url);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) u.openConnection();
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Authorization", "Bearer " + TOKEN);
			httpUrlConnection.setDoOutput(false);
			httpUrlConnection.setDoInput(true);
			
			httpUrlConnection.connect();
			
			Map<String, List<String>> headers = httpUrlConnection.getHeaderFields();
			for(String k : headers.keySet()) {
				List<String> headerValue = headers.get(k);
				System.out.println("k=" + k + ", value=" + headerValue.toString());
			}
			
			System.out.println("responseCode=" + httpUrlConnection.getResponseCode());
			
			InputStream is = httpUrlConnection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			String finalLine = "";
			while((line = br.readLine()) != null) {
				System.out.println(line);
				finalLine += line;
				
			}
			JSONParser parser = new JSONParser();
			JSONArray jsonObject = (JSONArray) parser.parse(finalLine);
			System.out.println(jsonObject.toJSONString());
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
