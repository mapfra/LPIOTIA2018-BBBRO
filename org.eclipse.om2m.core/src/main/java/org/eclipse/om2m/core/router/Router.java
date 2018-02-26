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
package org.eclipse.om2m.core.router;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ResponseType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.exceptions.BadRequestException;
import org.eclipse.om2m.commons.exceptions.NotImplementedException;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.controller.AEController;
import org.eclipse.om2m.core.controller.AccessControlPolicyController;
import org.eclipse.om2m.core.controller.AEAnncController;
import org.eclipse.om2m.core.controller.CSEBaseController;
import org.eclipse.om2m.core.controller.ContainerController;
import org.eclipse.om2m.core.controller.ContentInstanceController;
import org.eclipse.om2m.core.controller.Controller;
import org.eclipse.om2m.core.controller.DiscoveryController;
import org.eclipse.om2m.core.controller.DynamicAuthorizationConsultationController;
import org.eclipse.om2m.core.controller.FanOutPointController;
import org.eclipse.om2m.core.controller.FlexContainerAnncController;
import org.eclipse.om2m.core.controller.FlexContainerController;
import org.eclipse.om2m.core.controller.GroupController;
import org.eclipse.om2m.core.controller.LatestOldestController;
import org.eclipse.om2m.core.controller.LatestOldestController.SortingPolicy;
import org.eclipse.om2m.core.controller.MgmtObjAnncController;
import org.eclipse.om2m.core.controller.MgmtObjController;
import org.eclipse.om2m.core.controller.NodeAnncController;
import org.eclipse.om2m.core.controller.NodeController;
import org.eclipse.om2m.core.controller.PollingChannelController;
import org.eclipse.om2m.core.controller.PollingChannelUriController;
import org.eclipse.om2m.core.controller.RemoteCSEController;
import org.eclipse.om2m.core.controller.RequestController;
import org.eclipse.om2m.core.controller.SubscriptionController;
import org.eclipse.om2m.core.datamapper.DataMapperSelector;
import org.eclipse.om2m.core.nblocking.NonBlockingHandler;
import org.eclipse.om2m.core.redirector.Redirector;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.core.urimapper.UriMapper;
/**
 * Routes a generic request to the appropriate resource controller to handle it based on the request method and URI.
 */

public class Router implements CseService {
	/** Logger */
	private static Log LOGGER = LogFactory.getLog(Router.class);

	/**
	 * Invokes required resource controller method.
	 * @param request - The generic request to handle
	 * @return The generic returned response
	 */
	public ResponsePrimitive doRequest(RequestPrimitive request) {

		LOGGER.info("Received request in Router: " + request.toString());
		ResponsePrimitive response = new ResponsePrimitive(request);
		Patterns patterns = new Patterns();
		
		String contentFormat = System.getProperty("org.eclipse.om2m.registration.contentFormat", MimeMediaType.XML);

		try{

			// Non blocking request handling
			if (request.getResponseTypeInfo() != null 
					&& request.getResponseTypeInfo().getResponseType() != null){
				if (request.getResponseTypeInfo().getResponseType().equals(ResponseType.NON_BLOCKING_REQUEST_SYNCH)
						|| request.getResponseTypeInfo().getResponseType().equals(ResponseType.NON_BLOCKING_REQUEST_ASYNCH)){
					if(Constants.NON_BLOCKING_SUPPORTED){
						return NonBlockingHandler.handle(request);					
					} else {
						response.setResponseStatusCode(ResponseStatusCode.NON_BLOCKING_REQUEST_NOT_SUPPORTED);
						response.setContent("Non blocking request is not supported");
						response.setContentType(MimeMediaType.TEXT_PLAIN);
						return response;
					}
				}
			}

			// Check if the data type has been set
			if (request.getRequestContentType() == null){
				request.setRequestContentType(contentFormat);
				LOGGER.info("No Content-Type parameter set, setting to default: " + request.getRequestContentType());
			}
			if (request.getReturnContentType() == null){
				request.setReturnContentType(contentFormat);
				LOGGER.info("No Accept parameter set, setting to default: " + request.getReturnContentType());
			}

			// Check if the data type requested is supported
			if (request.getRequestContentType() != MimeMediaType.OBJ && request.getRequestContentType()!=null){
				if (!DataMapperSelector.getDataMapperList().containsKey(request.getRequestContentType())){
					throw new NotImplementedException(request.getRequestContentType()+" is not supported.");
				}
			}
			if (request.getReturnContentType() != MimeMediaType.OBJ && request.getReturnContentType()!=null){
				if (!DataMapperSelector.getDataMapperList().containsKey(request.getReturnContentType())){
					throw new NotImplementedException(request.getReturnContentType()+" is not supported.");
				}
			}

			// Check if the operation has been provided
			if(request.getOperation() == null){
				throw new BadRequestException("No Operation provided");
			}

			// URI Handling
			if (request.getTo() == null) {
				throw new BadRequestException("No To/TargetId parameter provided");
			} 
			
			if(request.getTo().startsWith("~")){
				request.setTo(request.getTo().substring(1));
			} 

			// Check if the SP-ID is provided
			if(request.getTo().startsWith("//") || request.getTo().startsWith("_")){
				String uri = request.getTo().substring(2);
				String spId = uri.split("/")[0];
				if (!spId.equals(Constants.M2M_SP_ID)){
					throw new ResourceNotFoundException("Not the current SP Domain (" + spId + ")");
				} else {
					request.setTo(uri.replace(spId, ""));
				}
			} else if (!request.getTo().startsWith("/")){
				request.setTo("/" + Constants.CSE_ID + "/" + request.getTo());
			}

			// Remove the last "/" from the request uri if exist.
			if(request.getTo().endsWith("/")){
				request.setTo(request.getTo().substring(0,request.getTo().length()-1));
			}

			getQueryStringFromTargetId(request);

			// Redirection case
			if (!patterns.match(patterns.NON_RETARGETING_PATTERN, request.getTo())){
				LOGGER.info("Request targeting another CSE, forwarding to Redirector: " + request.getTo());
				return Redirector.retarget(request);
			}
			LOGGER.info("Request handling in the current CSE: " + request.getTo());

			Controller controller = null ; 
			// Case of hierarchical URI, retrieve the non-hierarchical URI of the resource
			if (patterns.match(patterns.HIERARCHICAL_PATTERN, request.getTo())){
				if(request.getTo().contains(patterns.FANOUT_POINT_MATCH + "/")){
					int foptIndex = request.getTo().indexOf(patterns.FANOUT_POINT_MATCH);
					String uri = request.getTo().substring(0, foptIndex);
					String suffix = request.getTo()
							.substring(
									foptIndex + patterns.FANOUT_POINT_MATCH.length(), 
									request.getTo().length()
									);
					controller = new FanOutPointController(suffix);
					request.setTo(uri);
					LOGGER.info("Fan Out request received: [grp uri: " + uri + ", suffix: " + suffix + "]");
				} if (request.getTo().endsWith(patterns.FANOUT_POINT_MATCH)) {
					controller = new FanOutPointController();
					request.setTo(request.getTo().replaceAll(patterns.FANOUT_POINT_MATCH, ""));
					LOGGER.info("Fan Out request received: [grp uri: " + request.getTo()+ "]");
				} 
				if(request.getTo().endsWith("/" + ShortName.LATEST)){
					controller = new LatestOldestController(SortingPolicy.LATEST);
					request.setTo(request.getTo() + "/");
					request.setTo(request.getTo().replace("/"+ShortName.LATEST+"/", ""));
				}
				if (request.getTo().endsWith("/" + ShortName.OLDEST)){
					controller = new LatestOldestController(SortingPolicy.OLDEST);
					request.setTo(request.getTo() + "/");
					request.setTo(request.getTo().replace("/"+ShortName.OLDEST+"/", ""));
				}
				String nonHierarchicalUri = UriMapper.getNonHierarchicalUri(request.getTo());
				if (nonHierarchicalUri == null){
					throw new ResourceNotFoundException("Resource not found");
				}
				request.setTo(nonHierarchicalUri);
				LOGGER.debug("Changing to unstructured uri for routing to: " + request.getTo());
			}

			// Notify case
			if(request.getOperation().equals(Operation.NOTIFY)){
				return Redirector.retargetNotify(request);
			}

			// Discovery case
			if ((request.getFilterCriteria() != null) && (request.getFilterCriteria().getFilterUsage() != null)
					&& (request.getFilterCriteria().getFilterUsage().intValue() == 1)){
				controller = new DiscoveryController();
			}

			// Determine the appropriate resource controller
			// In case of a CREATE, the resource type determine the controller
			if (controller == null){
				if (request.getOperation().equals(Operation.CREATE)){
					controller = getResourceControllerFromRT(request.getResourceType());
				} else {
					controller = getResourceControllerFromURI(request.getTo());
				}        		
			}

			if (controller!=null){
				LOGGER.info("ResourceController to be used ["+ controller.getClass().getSimpleName()+"]");
				// Perform the request in the specific controller
				response = controller.doRequest(request);
				if(request.getResultContent() != null && request.getResultContent().equals(ResultContent.NOTHING)){
					response.setContent(null);
				} else {
					if(response.getContent() != null && !(response.getContent() instanceof String)
							&& !request.getReturnContentType().equals(MimeMediaType.OBJ)){
						String representation = DataMapperSelector.getDataMapperList().
								get(request.getReturnContentType()).objToString(response.getContent());
						response.setContent(representation);
						response.setContentType(request.getReturnContentType());
					}
				}
			} else {
				throw new BadRequestException("Malformed URI");
			}
		} catch (Om2mException om2mException){
			response.setResponseStatusCode(om2mException.getErrorStatusCode());
			response.setContent(om2mException.getMessage());
			response.setContentType(MimeMediaType.TEXT_PLAIN);
			LOGGER.error("OM2M exception caught in Router: " + om2mException.getMessage(), om2mException);
			LOGGER.debug("OM2M exception caught in Router", om2mException);
		} catch(Exception e){
			LOGGER.error("Router internal error", e);
			response.setResponseStatusCode(ResponseStatusCode.INTERNAL_SERVER_ERROR);
			response.setContent("Router internal error");
			response.setContentType(MimeMediaType.TEXT_PLAIN);
		}

		LOGGER.info("Response in Router= " + response);
		return response;
	}

	/**
	 * Finds required resource controller based on uri patterns.
	 * @param uri - Generic request uri
	 * @param method - Generic request method
	 * @param representation - Resource representation
	 * @return The matched resource controller otherwise null
	 */
	protected Controller getResourceControllerFromURI(String uri){
		Patterns patterns = new Patterns();
		// Match the resource controller with an uri pattern and return it, otherwise return null
		if (patterns.match(patterns.CSE_BASE_PATTERN, uri)){
			return new CSEBaseController();
		}
		if (patterns.match(patterns.AE_PATTERN, uri)){
			return new AEController();
		}
		if (patterns.match(patterns.AEANNC_PATTERN, uri)){
			return new AEAnncController();
		}
		if (patterns.match(patterns.ACP_PATTERN, uri)){
			return new AccessControlPolicyController();
		}
		if (patterns.match(patterns.CONTAINER_PATTERN, uri)){
			return new ContainerController();
		}
		if (patterns.match(patterns.DYNAMIC_AUTHORIZATION_CONSULTATION_PATTERN, uri)){
			return new DynamicAuthorizationConsultationController();
		}
		if (patterns.match(patterns.FLEXCONTAINER_PATTERN, uri)) {
			return new FlexContainerController();
		}
		if (patterns.match(patterns.FLEXCONTAINER_ANNC_PATTERN, uri)) {
			return new FlexContainerAnncController();
		}
		if (patterns.match(patterns.CONTENTINSTANCE_PATTERN, uri)) {
			return new ContentInstanceController();
		}
		if (patterns.match(patterns.REMOTE_CSE_PATTERN, uri)) {
			return new RemoteCSEController();
		}
		if (patterns.match(patterns.GROUP_PATTERN, uri)){
			return new GroupController();
		}
		if (patterns.match(patterns.NODE_PATTERN, uri)) {
			return new NodeController();
		}
		if (patterns.match(patterns.NODE_ANNC_PATTERN, uri)) {
			return new NodeAnncController();
		}
		if (patterns.match(patterns.NMGMT_OBJ_PATTERN, uri)) {
			return new MgmtObjController();
		}
		if (patterns.match(patterns.NMGMT_OBJ_ANNC_PATTERN, uri)) {
			return new MgmtObjAnncController();
		}
		if (patterns.match(patterns.SUBSCRIPTION_PATTERN, uri)){
			return new SubscriptionController();
		}
		if (patterns.match(patterns.POLLING_CHANNEL_PATTERN, uri)){
			return new PollingChannelController();
		}
		if (patterns.match(patterns.POLLING_CHANNEL_URI_PATTERN, uri)){
			return new PollingChannelUriController();
		}
		if (patterns.match(patterns.REQUEST_PATTERN, uri)){
			return new RequestController();
		}
		return null;
	}

	/**
	 * In the case of a CREATE operation, the controller is determined by the 
	 * resource type argument from the generic request primitive.
	 * @param resourceType 
	 * @return the matching controller
	 */
	protected Controller getResourceControllerFromRT(BigInteger resourceType){
		// test in case resourceType is null
		if (resourceType == null) {
			throw new BadRequestException("Resource type is missing for creation request");
		}
		// switch case
		switch(resourceType.intValue()){
		case ResourceType.CSE_BASE:
			return new CSEBaseController();
		case ResourceType.ACCESS_CONTROL_POLICY:	
			return new AccessControlPolicyController();
		case ResourceType.AE:
			return new AEController();
		case ResourceType.CONTAINER:
			return new ContainerController();
		case ResourceType.CONTENT_INSTANCE :
			return new ContentInstanceController();
		case ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION:
			return new DynamicAuthorizationConsultationController();
		case ResourceType.REMOTE_CSE :
			return new RemoteCSEController();
		case ResourceType.GROUP:
			return new GroupController();
		case ResourceType.NODE:
			return new NodeController();
		case ResourceType.SUBSCRIPTION:
			return new SubscriptionController();
		case ResourceType.POLLING_CHANNEL:
			return new PollingChannelController();
		case ResourceType.FLEXCONTAINER:
			return new FlexContainerController(); 
		case ResourceType.AE_ANNC:
			return new AEAnncController();
		case ResourceType.FLEXCONTAINER_ANNC:
			return new FlexContainerAnncController();
		case ResourceType.MGMT_OBJ:
			return new MgmtObjController(); 
		case ResourceType.MGMT_OBJ_ANNC:
			return new MgmtObjAnncController(); 
		case ResourceType.NODE_ANNC:
			return new NodeAnncController();
		default : 
			throw new NotImplementedException("ResourceType: " + resourceType + " is not implemented");
		}
	}

	private static void getQueryStringFromTargetId(RequestPrimitive request){
		
		if (request.getTo().contains("#")) {
			request.getQueryStrings().put("#", new ArrayList());
		}
		if(request.getTo().contains("?")){
			String query = request.getTo().split("\\?")[1];
			Map<String, List<String>> parameters = new HashMap<String, List<String>>();
			if (query != null) {
				String[] pairs = query.split("[&]");
				for (String pair : pairs) {
					String[] param = pair.split("[=]");

					String key = null;
					String value = null;
					if (param.length > 0) {
						key = param[0];
					}
					if (param.length > 1) {
						value = param[1];
					}
					if (parameters.containsKey(key)) {
						parameters.get(key).add(value);
					} else {
						List<String> values = new ArrayList<String>();
						values.add(value);
						parameters.put(key,values);
					}
				}
			}
			request.getQueryStrings().putAll(parameters);
			
		}

	}

}
