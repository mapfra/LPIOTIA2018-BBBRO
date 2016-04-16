/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/

package org.eclipse.om2m.binding.coap;

import java.math.BigInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.binding.service.RestClientService;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.StatusCode;

import ch.ethz.inf.vs.californium.coap.CoAP;
import ch.ethz.inf.vs.californium.coap.MediaTypeRegistry;
import ch.ethz.inf.vs.californium.coap.OptionSet;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;

public class CoapClient implements RestClientService {

	/** Logger: */
	private static Log LOGGER = LogFactory.getLog(CoapClient.class);
	/** implemented specific protocol name */
	private static String protocol = "coap";

	/**
	 * gets the implemented specific protocol name
	 * 
	 * @return protocol name
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Converts a protocol-independent {@link RequestIndication} object into a
	 * standard CoAP request and sends a standard CoAP request. Converts the
	 * received standard CoAP response into {@link ResponseConfirm} object and
	 * returns it back.
	 * 
	 * @param requestIndication
	 *            - protocol independent request.
	 * @return protocol independent response.
	 */
	public ResponsePrimitive sendRequest(RequestPrimitive requestPrimitive) {
        LOGGER.debug("Sending request: " +requestPrimitive);
        
        // Retrieve the url
 		String url = requestPrimitive.getTo();
 		if(!url.startsWith(protocol+"://")){
 			if (url.startsWith("://")){
 				url = protocol + url;
 			} else if (url.startsWith("//")){
 				url = protocol + ":" + url;
 			} else {
 				url = protocol + "://" + url ;
 			}
 		}
		// create the standard final response
        ResponsePrimitive responsePrimitive = new ResponsePrimitive();

		// get the Payload from requestIndication
        String representation = requestPrimitive.getContent() != null ? 
        		requestPrimitive.getContent().toString() :
        			null;

		// get the authorization (uri_query option) from requestIndication
		String from = requestPrimitive.getFrom();

		// create the code in order to create a coap request
		CoAP.Code code = null;

		// get the method from requestIndication
		BigInteger operation = requestPrimitive.getOperation();
		if (operation != null){
			if (operation.equals(Operation.CREATE)){
				code = CoAP.Code.POST;
			} else if (operation.equals(Operation.RETRIEVE)){
				code = CoAP.Code.GET;
			} else if (operation.equals(Operation.UPDATE)){
				code = CoAP.Code.PUT;
			} else if (operation.equals(Operation.DELETE)){
				code = CoAP.Code.DELETE;
			} else if (operation.equals(Operation.NOTIFY)){
				code = CoAP.Code.POST;
			}
		} else {
			return responsePrimitive;
		}
		
		// create a coap request
		Request request = new Request(code);

		// get the options of the CoAP request (which is still empty)
		OptionSet options = new OptionSet();
		// set the CoAP message Id
		int MId = (int) (1000 + Math.random() * (9001));
		request.setMID(MId);
		// request.setToken(token);
		CoAP.Type coapType = CoAP.Type.CON;
		request.setType(coapType);

		// set the request URI
		request.setURI(url);

		// set the payload
		if(representation != null){
			request.setPayload(representation);			
		}

		// set the authorization
		options.setURIQuery(from);

		request.setOptions(options);
		
		// from targetID to proxy-uri (useless in present case)
		// options.setProxyURI(requestIndication.getTargetID());

		// send the request
		request.setScheme(url);
		request.send();

		// get the response
		Response response = null;
		try {
			response = request.waitForResponse();
		} catch (InterruptedException e) {
			LOGGER.error("CoAP Client > Failed to receive response: " + e.getMessage(), e);
		}
		if (response != null) {
			if (response.getOptions().hasContentFormat(
					MediaTypeRegistry.APPLICATION_LINK_FORMAT)) {

				String linkFormat = response.getPayloadString();
				// fill in the representation of the responseConfirm
				responsePrimitive.setContent(linkFormat);
			} else {
				responsePrimitive.setContent(response.getPayloadString());
			}
		}
		// set the responseConfirm statusCode
		if(response == null){
			responsePrimitive.setResponseStatusCode(ResponseStatusCode.TARGET_NOT_REACHABLE);
		}
		CoAP.ResponseCode returncode = response.getCode();
		responsePrimitive.setResponseStatusCode(getResponseStatusCode(returncode.value));
		LOGGER.debug("CoAP Client > "+responsePrimitive);		
		return responsePrimitive;
	}

	/**
	 * Converts a standard CoAP status code into a protocol-independent
	 * {@link StatusCode} object.
	 * 
	 * @param statusCode
	 *            - standard CoAP status code.
	 * @return protocol independent status.
	 */
	private BigInteger getResponseStatusCode(int statusCode) {
		switch(statusCode){
		case 200: return ResponseStatusCode.OK;
		case 202: return ResponseStatusCode.ACCEPTED;
		case 201: return ResponseStatusCode.CREATED; 
		case 204: return ResponseStatusCode.DELETED;
		case 400: return ResponseStatusCode.BAD_REQUEST;
		case 403: return ResponseStatusCode.ACCESS_DENIED;
		case 404: return ResponseStatusCode.NOT_FOUND;
		case 405: return ResponseStatusCode.OPERATION_NOT_ALLOWED;
		case 409: return ResponseStatusCode.CONFLICT;
		case 500: return ResponseStatusCode.INTERNAL_SERVER_ERROR;
		case 501: return ResponseStatusCode.NOT_IMPLEMENTED;
		case 503: return ResponseStatusCode.SERVICE_UNAVAILABLE;
		default: return ResponseStatusCode.INTERNAL_SERVER_ERROR;
		}
	}
}
