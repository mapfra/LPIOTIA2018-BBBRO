/*******************************************************************************
 * Copyright (c) 2016- 2017 SENSINOV (www.sensinov.com)
 * 41 Rue de la découverte 31676 Labège - France 
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.client.java.tools;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RestHttpClient {
	
	public static HttpResponse get(String originator, String uri) {
		System.out.println("HTTP GET "+uri);
		CloseableHttpClient httpclient = HttpClientBuilder.create()
				.disableAutomaticRetries()
				.build();
		HttpGet httpGet= new HttpGet(uri);
		
		httpGet.addHeader("x-m2m-ri",generateRI());
		httpGet.addHeader("x-m2m-origin",originator);
		httpGet.addHeader("Accept","application/json");

		HttpResponse httpResponse = new HttpResponse();
		
		try {
			CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpGet);
			try{
				httpResponse.setStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
				httpResponse.setBody(EntityUtils.toString(closeableHttpResponse.getEntity()));
			}finally{
				closeableHttpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("HTTP Response "+httpResponse.getStatusCode()+"\n"+httpResponse.getBody());
		return httpResponse;	
	}
	
	public static HttpResponse put(String originator, String uri, String body) {
		System.out.println("HTTP PUT "+uri+"\n"+body);
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPut httpPut= new HttpPut(uri);
		
		httpPut.addHeader("X-M2M-RI",generateRI());
		httpPut.addHeader("X-M2M-Origin",originator);
		httpPut.addHeader("Content-Type","application/json");
		httpPut.addHeader("Accept","application/json");

		HttpResponse httpResponse = new HttpResponse();
		try {
			CloseableHttpResponse closeableHttpResponse =null;
			try {
				httpPut.setEntity(new StringEntity(body));
				closeableHttpResponse= httpclient.execute(httpPut);
				httpResponse.setStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
				httpResponse.setBody(EntityUtils.toString(closeableHttpResponse.getEntity()));
				
			}finally{
				closeableHttpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("HTTP Response "+httpResponse.getStatusCode()+"\n"+httpResponse.getBody());

		return httpResponse ;	
	}
	
	public static HttpResponse post(String originator, String uri, String body, int ty) {
		System.out.println("HTTP POST "+uri+"\n"+body);

		final CloseableHttpClient httpclient = HttpClientBuilder.create()
				.disableAutomaticRetries()
				.build();
		final HttpPost httpPost = new HttpPost(uri);
		
		httpPost.addHeader("X-M2M-RI",generateRI());
		httpPost.addHeader("X-M2M-Origin",originator);
		httpPost.addHeader("Accept","application/json");
		
		httpPost.addHeader("Content-Type","application/json;ty="+ty);
				
		final HttpResponse httpResponse = new HttpResponse();

			try{
				httpPost.setEntity(new StringEntity(body));
						try {
							CloseableHttpResponse closeableHttpResponse=null;
							closeableHttpResponse = httpclient.execute(httpPost);
							httpResponse.setStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
							httpResponse.setBody(EntityUtils.toString(closeableHttpResponse.getEntity()));
						} catch (ClientProtocolException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("HTTP Response "+httpResponse.getStatusCode()+"\n"+httpResponse.getBody());
		return httpResponse ;	
	}
	
	public static HttpResponse delete(String originator, String uri) {
		System.out.println("HTTP DELETE "+uri);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(uri);

		httpDelete.addHeader("X-M2M-RI",generateRI());
		httpDelete.addHeader("X-M2M-Origin",originator);
		httpDelete.addHeader("Accept","application/json");


		HttpResponse httpResponse = new HttpResponse();
		try {
			CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpDelete);
			try {
				httpResponse.setStatusCode(closeableHttpResponse.getStatusLine().getStatusCode());
				httpResponse.setBody(EntityUtils.toString(closeableHttpResponse.getEntity()));				
			}finally{
				closeableHttpResponse.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.out.println("HTTP Response "+httpResponse.getStatusCode()+"\n"+httpResponse.getBody());
		return httpResponse ;	
	}	
	
	public static String generateRI(){
		Integer random = (int)(Math.random()*1000)+100000;
		return random.toString();
	}
}