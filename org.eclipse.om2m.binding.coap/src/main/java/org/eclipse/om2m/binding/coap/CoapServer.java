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

import java.io.IOException;
import java.math.BigInteger;
import java.net.SocketException;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.ResponseTypeInfo;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.core.service.CseService;

import ch.ethz.inf.vs.californium.coap.CoAP;
import ch.ethz.inf.vs.californium.coap.CoAP.Code;
import ch.ethz.inf.vs.californium.coap.CoAP.ResponseCode;
import ch.ethz.inf.vs.californium.coap.Option;
import ch.ethz.inf.vs.californium.coap.OptionSet;
import ch.ethz.inf.vs.californium.coap.Request;
import ch.ethz.inf.vs.californium.coap.Response;
import ch.ethz.inf.vs.californium.network.Exchange;
import ch.ethz.inf.vs.californium.server.MessageDeliverer;
import ch.ethz.inf.vs.californium.server.Server;

/**
 * Server side for COAP binding
 *
 */
public class CoapServer {

	private Server server;
	private CoapMessageDeliverer msgDeliverer;
	private static int port = Integer.parseInt(System.getProperty(
			"org.eclipse.om2m.coap.port", "5683"));

	/**
	 * Start the COAP server
	 * @throws Exception
	 */
	public void startServer() throws Exception {
		server = new Server(port);
		msgDeliverer = new CoapMessageDeliverer();
		server.setMessageDeliverer(msgDeliverer);

		server.start();
	}
}

class CoapMessageDeliverer implements MessageDeliverer {

	private static Log LOGGER = LogFactory.getLog(CoapMessageDeliverer.class);

	private static CseService cse;
	private static String context = System.getProperty(
			"org.eclipse.om2m.cseBaseContext", "/");
	Request req;
	Response resp;

	@Override
	public void deliverRequest(Exchange exchange) {

		req = exchange.getRequest();
		try {
			resp = service(req);
			LOGGER.info("the response= " + resp);
		} catch (SocketException e) {
			LOGGER.error("the service failed! ", e);
		} catch (IOException e) {
			LOGGER.error("IOexception", e);
		}

		LOGGER.info("request = " + req);
		exchange.sendResponse(resp);
	}

	@Override
	public void deliverResponse(Exchange rqst, Response rspns) {
		rqst.sendResponse(rspns);
		LOGGER.info("response= " + rspns);
	}

	/**
	 * Converts a {@link CoapServerRequest} to a {@link RequestIndication} and
	 * uses it to invoke the SCL service. Converts the received
	 * {@link ResponseConfirm} to a {@link CoapServerResponse} and returns it
	 * back.
	 */

	public Response service(Request request) throws SocketException,
			IOException {
		LOGGER.info("----------------------------------------------------------------------------------------------");
		int mid = request.getMID();
		byte[] token = request.getToken();
		RequestPrimitive requestPrimitive = new RequestPrimitive();
		ResponsePrimitive responsePrimitive = new ResponsePrimitive();
		OptionSet options = request.getOptions();
		LOGGER.info(options.toString());
		LOGGER.info("Coap incoming URI: " + request.getURI());

		String targetId = "";
		for (String s : request.getOptions().getURIPaths()) {
			targetId += s + "/";
		}
		if (targetId.endsWith("/")) {
			targetId = targetId.substring(0, targetId.length() - 1);
		}

		if (context.length() > 1) {
			String localContext = context.substring(1);
			targetId = targetId.substring(localContext.length());
		}
		if (targetId.startsWith("/~") || targetId.startsWith("/_")) {
			targetId = targetId.substring(1);
		}

		requestPrimitive.setTo(request.getURI());
		requestPrimitive.setTargetId(targetId);
		requestPrimitive.setContent(request.getPayloadString());

		List<Option> optionsList = options.asSortedList();
		for (int i = 0; i < optionsList.size(); i++) {
			switch (optionsList.get(i).getNumber()) {
			case 256:
				requestPrimitive.setFrom(optionsList.get(i).getStringValue());
				break;
			case 257:
				requestPrimitive.setRequestIdentifier(optionsList.get(i)
						.getStringValue());
				break;
			case 258:
				requestPrimitive.setName(optionsList.get(i).getStringValue());
				break;
			case 259:
				requestPrimitive.setOriginatingTimestamp(optionsList.get(i)
						.getStringValue());
				break;
			case 260:
				requestPrimitive.setRequestExpirationTimestamp(optionsList.get(
						i).getStringValue());
				break;
			case 261:
				requestPrimitive.setResultExpirationTimestamp(optionsList
						.get(i).getStringValue());
				break;
			case 262:
				requestPrimitive.setOperationExecutionTime(optionsList.get(i)
						.getStringValue());
				break;
			case 264:
				requestPrimitive.setEventCategory(optionsList.get(i)
						.getStringValue());
				break;
			case 266:
				requestPrimitive.setGroupRequestIdentifier(optionsList.get(i)
						.getStringValue());
				break;
			case 267:
				requestPrimitive.setResourceType(new BigInteger(optionsList
						.get(i).getValue()));
				break;
			}
		}

		LOGGER.info("URIQueries: " + options.getURIQueries());
		mapParameters(request, requestPrimitive);

		requestPrimitive.setOperation(getOneM2MOperation(request.getCode(),
				requestPrimitive.getResourceType()));

		if (options.getContentFormat() == 41) {
			requestPrimitive.setRequestContentType(MimeMediaType.XML);

		} else if (options.getContentFormat() == 50) {
			requestPrimitive.setRequestContentType(MimeMediaType.JSON);

		}
		if (options.getAccept() == 41) {
			requestPrimitive.setReturnContentType(MimeMediaType.XML);
		} else if (options.getAccept() == 50) {
			requestPrimitive.setReturnContentType(MimeMediaType.JSON);
		}

		// requestPrimitive.setTargetId(requestPrimitive.getTargetId().split("\\?")[0]);

		if (cse != null) {
			LOGGER.info("check point: Execute requestPrimitive..");
			responsePrimitive = cse.doRequest(requestPrimitive);
			LOGGER.info("check point: Execute requestPrimitive.. OK");

		} else {
			responsePrimitive
					.setResponseStatusCode(ResponseStatusCode.SERVICE_UNAVAILABLE);
			responsePrimitive.setErrorMessage("CSE service is not available.");
		}

		ResponseCode statusCode = getCoapStatusCode(responsePrimitive
				.getResponseStatusCode());
		LOGGER.info("check point : The code is " + statusCode);

		Response response = new Response(statusCode);
		response.setMID(mid);
		response.setToken(token);
		if (request.getType().equals(CoAP.Type.CON)) {
			response.setType(CoAP.Type.ACK);
		}
		if (responsePrimitive.getResponseStatusCode() != null) {
			response.getOptions().addOption(
					new Option(265, responsePrimitive.getResponseStatusCode().intValue()));
		}
		if (responsePrimitive.getRequestIdentifier() != null) {
			response.getOptions().addOption(
					new Option(257, responsePrimitive.getRequestIdentifier()));
		}
		if (responsePrimitive.getContent() != null) {
			response.setPayload(responsePrimitive.getContent().toString());
		}
		if (responsePrimitive.getLocation() != null) {
			response.getOptions().addOption(
					new Option(8, "~" + responsePrimitive.getLocation()));
		}
		if (requestPrimitive.getOperation() == Operation.RETRIEVE) {
			if (requestPrimitive.getReturnContentType().equals(
					MimeMediaType.XML)) {
				response.getOptions().addOption(new Option(12, 41));
			} else if (requestPrimitive.getReturnContentType().equals(
					MimeMediaType.JSON)) {
				response.getOptions().addOption(new Option(12, 50));
			}
		}
		return response;
	}

	/**
	 * Converts a {@link StatusCode} object into a standard CoAP status code .
	 *
	 * @param statusCode
	 *            - protocol-independent status code.
	 * @param isEmptyBody
	 *            - request body existence
	 * @return standard CoAP status code.
	 */
	public static ResponseCode getCoapStatusCode(BigInteger statusCode) {
		if (statusCode.equals(ResponseStatusCode.OK)) {
			return ResponseCode.CONTENT;
		} else if (statusCode.equals(ResponseStatusCode.CREATED)) {
			return ResponseCode.CREATED;
		} else if (statusCode.equals(ResponseStatusCode.ACCEPTED)) {
			return ResponseCode.VALID;
		} else if (statusCode.equals(ResponseStatusCode.DELETED)) {
			return ResponseCode.DELETED;
		} else if (statusCode.equals(ResponseStatusCode.UPDATED)) {
			return ResponseCode.CHANGED;
		} else if (statusCode.equals(ResponseStatusCode.BAD_REQUEST)
				|| statusCode.equals(ResponseStatusCode.CONTENTS_UNACCEPTABLE)
				|| statusCode
						.equals(ResponseStatusCode.MAX_NUMBER_OF_MEMBER_EXCEEDED)
				|| statusCode
						.equals(ResponseStatusCode.MEMBER_TYPE_INCONSISTENT)
				|| statusCode.equals(ResponseStatusCode.INVALID_CMDTYPE)
				|| statusCode.equals(ResponseStatusCode.INSUFFICIENT_ARGUMENTS)
				|| statusCode.equals(ResponseStatusCode.ALREADY_COMPLETED)
				|| statusCode
						.equals(ResponseStatusCode.COMMAND_NOT_CANCELLABLE)) {
			return ResponseCode.BAD_REQUEST;
		} else if (statusCode.equals(ResponseStatusCode.ACCESS_DENIED)
				|| statusCode
						.equals(ResponseStatusCode.SUBSCRIPTION_CREATOR_HAS_NO_PRIVILEGE)
				|| statusCode.equals(ResponseStatusCode.NO_PRIVILEGE)
				|| statusCode.equals(ResponseStatusCode.ALREADY_EXISTS)
				|| statusCode
						.equals(ResponseStatusCode.TARGET_NOT_SUBSCRIBABLE)
				|| statusCode
						.equals(ResponseStatusCode.SUBSCRIPTION_HOST_HAS_NO_PRIVILEGE)) {
			return ResponseCode.FORBIDDEN;
		} else if (statusCode.equals(ResponseStatusCode.NOT_FOUND)
				|| statusCode.equals(ResponseStatusCode.TARGET_NOT_REACHABLE)
				|| statusCode
						.equals(ResponseStatusCode.EXTERNAL_OBJECT_NOT_FOUND)
				|| statusCode
						.equals(ResponseStatusCode.EXTERNAL_OBJECT_NOT_REACHABLE)) {
			return ResponseCode.NOT_FOUND;
		} else if (statusCode.equals(ResponseStatusCode.OPERATION_NOT_ALLOWED)) {
			return ResponseCode.METHOD_NOT_ALLOWED;
		} else if (statusCode.equals(ResponseStatusCode.REQUEST_TIMEOUT)) {
			return ResponseCode.SERVICE_UNAVAILABLE;
		} else if (statusCode.equals(ResponseStatusCode.CONFLICT)
				|| statusCode
						.equals(ResponseStatusCode.GROUP_REQUEST_IDENTIFIER_EXISTS)) {
			return ResponseCode.INTERNAL_SERVER_ERROR;
		} else if (statusCode.equals(ResponseStatusCode.INTERNAL_SERVER_ERROR)
				|| statusCode
						.equals(ResponseStatusCode.SUBSCRIPTION_VERIFICATION_INITIATION_FAILED)
				|| statusCode
						.equals(ResponseStatusCode.MGMT_SESSION_CANNOT_BE_ESTABLISHED)
				|| statusCode
						.equals(ResponseStatusCode.MGMT_SESSION_ESTABLISHMENT_TIMEOUT)
				|| statusCode.equals(ResponseStatusCode.MGMT_CONVERSION_ERROR)
				|| statusCode
						.equals(ResponseStatusCode.MGMT_CANCELATION_FAILURE)) {
			return ResponseCode.INTERNAL_SERVER_ERROR;
		} else if (statusCode.equals(ResponseStatusCode.NOT_IMPLEMENTED)
				|| statusCode
						.equals(ResponseStatusCode.NON_BLOCKING_REQUEST_NOT_SUPPORTED)) {
			return ResponseCode.NOT_IMPLEMENTED;
		} else if (statusCode.equals(ResponseStatusCode.SERVICE_UNAVAILABLE)) {
			return ResponseCode.SERVICE_UNAVAILABLE;
		}
		return ResponseCode.SERVICE_UNAVAILABLE;
	}

	private static BigInteger getOneM2MOperation(Code code, BigInteger oneM2M_TY) {
		BigInteger result = null;
		switch (code) {
		case GET:
			return Operation.RETRIEVE;
		case POST:
			if (oneM2M_TY != null) {
				result = Operation.CREATE;
			} else {
				result = Operation.NOTIFY;
			}
			return result;
		case PUT:
			return Operation.UPDATE;
		case DELETE:
			return Operation.DELETE;
		default:
			return null;
		}
	}

	private void mapParameters(Request request, RequestPrimitive primitive) {
		List<String> params = request.getOptions().getURIQueries();
		String name, value;
		FilterCriteria filterCriteria = new FilterCriteria();

		for (int i = 0; i < params.size(); i++) {
			if (params.get(i).split("=").length == 2) {
				name = params.get(i).split("=")[0];
				value = params.get(i).split("=")[1];

				if (name.equals(CoapParameters.RESPONSE_TYPE)) {
					if (primitive.getResponseTypeInfo() == null) {
						primitive.setResponseTypeInfo(new ResponseTypeInfo());
					}
					primitive.getResponseTypeInfo().setResponseType(
							new BigInteger(value));
				}
				if (name.equals(CoapParameters.RESULT_CONTENT)) {
					primitive.setResultContent(new BigInteger(value));
				}
				if (name.equals(CoapParameters.RESULT_PERSISTENCE)) {
					try {
						Duration duration = DatatypeFactory.newInstance()
								.newDuration(value);
						primitive.setResultPersistence(duration);
					} catch (DatatypeConfigurationException e) {
						LOGGER.debug("Error in Duration creation", e);
					}
				}
				if (name.equals(CoapParameters.DELIVERY_AGGREGATION)) {
					primitive.setDeliveryAggregation(Boolean
							.parseBoolean(value));
				}

				if (name.equals(CoapParameters.DISCOVERY_RESULT_TYPE)) {
					primitive.setDiscoveryResultType(new BigInteger(value));
				}

				if (name.equals(CoapParameters.FILTER_USAGE)) {
					filterCriteria.setFilterUsage(new BigInteger(value));
				}

				if (name.equals(CoapParameters.LIMIT)) {
					filterCriteria.setLimit(new BigInteger(value));
				}
				if (name.equals(CoapParameters.LABELS)) {
					filterCriteria.getLabels().add(value);
				}
				if (name.equals(CoapParameters.RESOURCE_TYPE)) {
					filterCriteria.setResourceType(new BigInteger(value));
				}
				if (name.equals("ty")) {
					primitive.setResourceType(new BigInteger(value));
				}
			}
		}

		if (filterCriteria.getFilterUsage() != null) {
			primitive.setFilterCriteria(filterCriteria);
		}

	}

	public static CseService getCse() {
		return cse;
	}

	public static void setCse(CseService cse) {
		CoapMessageDeliverer.cse = cse;
	}

}