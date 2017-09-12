/*****************************************************************************************
* SENSINOV ("COMPANY") CONFIDENTIAL
* Copyright (c) 2016 SENSINOV, All Rights Reserved.
*
* NOTICE:  All information contained herein is, and remains the property of COMPANY. 
* The intellectual and technical concepts contained herein are proprietary to COMPANY 
* and may be covered by France and Foreign Patents, patents in process, and are protected 
* by trade secret or copyright law. Dissemination of this information or reproduction 
* of this material is strictly forbidden unless prior written permission is obtained
* from COMPANY. Access to the source code contained herein is hereby forbidden to 
* anyone except current COMPANY employees, managers or contractors who have executed 
* Confidentiality and Non-disclosure agreements explicitly covering such access.
*
* The copyright notice above does not evidence any actual or intended publication or 
* disclosure  of  this source code, which includes * information that is confidential 
* and/or proprietary, and is a trade secret, of  COMPANY. ANY REPRODUCTION, MODIFICATION,
* DISTRIBUTION, PUBLIC  PERFORMANCE, OR PUBLIC DISPLAY OF OR THROUGH USE  OF THIS  SOURCE 
* CODE  WITHOUT  THE EXPRESS WRITTEN CONSENT OF COMPANY IS STRICTLY PROHIBITED, AND IN 
* VIOLATION OF APPLICABLE * LAWS AND INTERNATIONAL TREATIES. THE RECEIPT OR POSSESSION OF
* THIS SOURCE CODE AND/OR RELATED INFORMATION DOES NOT CONVEY OR IMPLY ANY RIGHTS TO 
* REPRODUCE, DISCLOSE OR DISTRIBUTE ITS CONTENTS, OR TO MANUFACTURE, USE, OR SELL ANYTHING
* THAT IT  MAY DESCRIBE, IN WHOLE OR IN PART.                
*******************************************************************************************/

package org.eclipse.om2m.adn.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.om2m.adn.tests.TestConfig;


public class RestHttpClient {
	 static DefaultHttpClient httpclient=null;

	 public static void init(){
		
		 httpclient = new DefaultHttpClient();
         
		 if(TestConfig.cseProtocol.equals("https")){
			try{
	            KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());
	            FileInputStream instream = new FileInputStream(new File("keystore.jks"));
	            try {
	                trustStore.load(instream, "keystore".toCharArray());
	            }catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}catch (CertificateException e) {
					e.printStackTrace();
				}finally {
	                try { instream.close(); } catch (Exception ignore) {}
	            }
					 SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore, "keystore",trustStore);
					 socketFactory.setHostnameVerifier((X509HostnameVerifier) SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				     Scheme sch = new Scheme("https", 8443, socketFactory);
				     httpclient.getConnectionManager().getSchemeRegistry().register(sch);
				}catch (Exception e){
					e.printStackTrace();
				}
			 }
		 
	 }
	 
	 
	public static HttpResponse get(String originator, String uri) {
		if(httpclient==null){
			 init();
		 }
		System.out.println("HTTP GET "+uri);

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
		if(httpclient==null){
			 init();
		 }
		System.out.println("HTTP PUT "+uri+"\n"+body);
		
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
		if(httpclient==null){
			 init();
		 }
		System.out.println("HTTP POST "+uri+"\n"+body);


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
		if(httpclient==null){
			 init();
		 }
		System.out.println("HTTP DELETE "+uri);

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