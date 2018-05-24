/*******************************************************************************
* Copyright (c) 2014-2018 Orange.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    BAREAU Cyrille <cyrille.bareau@orange.com>
*    BONNARDEL Gregory <gbonnardel.ext@orange.com>
*    OSKO Tomasz <tomasz.osko@orange.com>
*    RATUSZEK Przemyslaw <przemyslaw.ratuszek@orange.com>
*******************************************************************************/
package org.eclipse.om2m.hue.impl.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.hue.api.types.HueException;
import org.eclipse.om2m.hue.api.types.UnknownHueGatewayException;

/**
 * Utility class to create REST requests to send to the Hue gateway for light control<p>
 */
public class Utils {

	// ====================================================================================
	/**
     * Logger
     */
    private static Log Logger = LogFactory.getLog(Utils.class);
    
	/**
	 * @param gwPath of type "IPaddress"/api/"userName"
	 * @param apiName of type "apiName", "apiName"/"id"
	 * @return Hue gateway response in JSON format string<p>
	 * - if url is incorrect (we reach Hue gateway, but the resource path is incorrect) the response begins with : "[{"error":..." (substring(3,8)),<p>
	 * - if the send fails (unknown host, timeout exception...) the response is an empty string 
	 * @throws HueException signaling that the HTTP REST request send failed
	 * @throws UnknownHueGatewayException signaling that maybe the IP address of the gateway has changed
	 */
	static String sendGetRequest(final String gwPath, final String apiName) 
			throws HueException, UnknownHueGatewayException {
		HttpURLConnection conn = null;
		try {
			// create connection
			conn = createConnection(gwPath, apiName);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			// connection response
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new HueException("Hue Light Gateway GET failed, HTTP error code : \n\t"
						+ conn.getResponseCode());
			}

			// read response
			return read(conn);
		} 
		catch (Exception e) {
			if (e.getClass().equals(ConnectException.class) 
					|| e.getClass().equals(UnknownHostException.class)
					|| e.getClass().equals(SocketTimeoutException.class)) {
				throw new UnknownHueGatewayException("");
			} else {
				throw new HueException("Exception on send GET request : \n\t" + e.toString());
			}
		} finally {
			// close connection
			try { conn.disconnect(); } 
			catch (Exception ignored) {}
		}
	}

	/**
	 * @param gwPath of type "IPaddress"/api/"userName"
	 * @param apiName of type "apiName", "apiName"/"id", or "apiName"/"id"/"attributeToSet" 
	 * @param jsonString JSON format object coverted to string
	 * @return Hue gateway response in JSON format string<p>
	 * - if url is incorrect (we reach Hue gateway, but the resource path is incorrect) the response begins with : "[{"error":..." (substring(3,8)),<p>
	 * - if the send fails (unknown host, timeout exception...) the response is an empty string 
	 * @throws HueException signaling that the HTTP REST request send failed
	 * @throws UnknownHueGatewayException signaling that maybe the IP address of the gateway has changed
	 */
	static String sendPutRequest(final String gwPath, final String apiName, final String jsonString) 
			throws HueException, UnknownHueGatewayException{
		// url path has the following form:
		// http://"IP"/api/"userName"/"apiName"/"idElement"/"attributeToSet"
		HttpURLConnection conn = null;
		try {
			// create connection
			conn = createConnection(gwPath, apiName);
			// will send JSONObject with the http request
			conn.setDoOutput(true);
			conn.setRequestMethod("PUT");
			conn.setRequestProperty("Content-Type", "application/json");

			// send JSON format object
			OutputStream os = conn.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();

			// connection response
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new HueException("Hue Light Gateway PUT failed, HTTP error code : \n\t"
						+ conn.getResponseCode());
			}

			// read response
			return read(conn);
		} 
		catch (IOException e) {
			Logger.warn("", e);
			if (e.getClass().equals(ConnectException.class) 
					|| e.getClass().equals(UnknownHostException.class)
					|| e.getClass().equals(SocketTimeoutException.class)) {
				throw new UnknownHueGatewayException("");
			} else {
				throw new HueException("Exception on send PUT request : \n\t" + e.toString());
			}
		} finally {
			// close connection
			try { conn.disconnect(); } 
			catch (Exception ignored) {}
		}
	}
        
        /**
	 * @param gwPath of type "IPaddress"/api/"userName"
	 * @param apiName of type "apiName", "apiName"/"id", or "apiName"/"id"/"attributeToSet" 
	 * @param jsonString JSON format object coverted to string
	 * @return Hue gateway response in JSON format string<p>
	 * - if url is incorrect (we reach Hue gateway, but the resource path is incorrect) the response begins with : "[{"error":..." (substring(3,8)),<p>
	 * - if the send fails (unknown host, timeout exception...) the response is an empty string 
	 * @throws HueException signaling that the HTTP REST request send failed
	 * @throws UnknownHueGatewayException signaling that maybe the IP address of the gateway has changed
	 */
	static String sendPostRequest(final String gwPath, final String apiName, final String jsonString) 
			throws HueException, UnknownHueGatewayException{
		// url path has the following form:
		// http://"IP"/api/"userName"/"apiName"/"idElement"/"attributeToSet"
		HttpURLConnection conn = null;
		try {
			// create connection
			conn = createConnection(gwPath, apiName);
			// will send JSONObject with the http request
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			// send JSON format object
			OutputStream os = conn.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();

			// connection response
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new HueException("Hue Light Gateway POST failed, HTTP error code : " + conn.getResponseCode() + 
                                         ", response body: " + conn.getResponseMessage());
			}

			// read response
			return read(conn);
		} 
		catch (IOException e) {
			Logger.warn("", e);
			if (e.getClass().equals(ConnectException.class) 
					|| e.getClass().equals(UnknownHostException.class)
					|| e.getClass().equals(SocketTimeoutException.class)) {
				throw new UnknownHueGatewayException("");
			} else {
				throw new HueException("Exception on send POST request : \n\t" + e.toString());
			}
		} finally {
			// close connection
			try { conn.disconnect(); } 
			catch (Exception ignored) {}
		}
	}
	
	static private final HttpURLConnection createConnection(final String path, final String api) 
			throws MalformedURLException, IOException {
		String url = "http://" + path.trim() + "/" + api.trim();
		return (HttpURLConnection) new URL(url).openConnection();
	}
	
	static private final String read(final HttpURLConnection conn) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

}
