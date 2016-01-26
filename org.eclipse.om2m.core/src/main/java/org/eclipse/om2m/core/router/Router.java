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
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.controller.AEController;
import org.eclipse.om2m.core.controller.AccessControlPolicyController;
import org.eclipse.om2m.core.controller.CSEBaseController;
import org.eclipse.om2m.core.controller.ContainerController;
import org.eclipse.om2m.core.controller.ContentInstanceController;
import org.eclipse.om2m.core.controller.Controller;
import org.eclipse.om2m.core.controller.DiscoveryController;
import org.eclipse.om2m.core.controller.FanOutPointController;
import org.eclipse.om2m.core.controller.GroupController;
import org.eclipse.om2m.core.controller.LatestOldestController;
import org.eclipse.om2m.core.controller.LatestOldestController.SortingPolicy;
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
						response.setErrorMessage("Non blocking request is not supported");
						return response;					
					}
				}
			}

			// Check if the data type has been set
			if (request.getRequestContentType() == null){
				request.setRequestContentType(MimeMediaType.XML);
			}
			if (request.getReturnContentType() == null){
				request.setReturnContentType(MimeMediaType.XML);
			}

			// Check if the data type requested is supported
			if (request.getRequestContentType() != MimeMediaType.OBJ && request.getRequestContentType()!=null){
				if (!DataMapperSelector.getDataMapperList().containsKey(request.getRequestContentType())){
					response.setResponseStatusCode(ResponseStatusCode.NOT_IMPLEMENTED);
					response.setErrorMessage(request.getRequestContentType()+" is not supported.");
					return response;
				}
			}
			if (request.getReturnContentType() != MimeMediaType.OBJ && request.getReturnContentType()!=null){
				if (!DataMapperSelector.getDataMapperList().containsKey(request.getReturnContentType())){
					response.setResponseStatusCode(ResponseStatusCode.NOT_IMPLEMENTED);
					response.setErrorMessage(request.getReturnContentType()+" is not supported.");
					return response;
				}
			}
			
			// Check if the operation has been provided
			if(request.getOperation() == null){
				throw new BadRequestException("No Operation provided");
			}
			
			// URI Handling
			if(request.getTargetId() == null){
				request.setTargetId(request.getTo());
			}
			
			if(request.getTargetId().startsWith("~")){
				request.setTargetId(request.getTargetId().substring(1));
			} 

			// Check if the SP-ID is provided
			if(request.getTargetId().startsWith("//") || request.getTargetId().startsWith("_")){
				String uri = request.getTargetId().substring(2);
				String spId = uri.split("/")[0];
				if (!spId.equals(Constants.M2M_SP_ID)){
					throw new ResourceNotFoundException("Not the current SP Domain.");
				} else {
					request.setTargetId(uri.replace(spId, ""));
				}
			} else if (!request.getTargetId().startsWith("/")){
				request.setTargetId("/" + Constants.CSE_ID + "/" + request.getTargetId());
			}

			// Remove the last "/" from the request uri if exist.
			if(request.getTargetId().endsWith("/")){
				request.setTargetId(request.getTargetId().substring(0,request.getTargetId().length()-1));
			}

			// Redirection case
			if (!Patterns.match(Patterns.NON_RETARGETING_PATTERN, request.getTargetId())){
				return Redirector.retarget(request);
			}

			Controller controller = null ; 
			// Case of hierarchical URI, retrieve the non-hierarchical URI of the resource
			if (!Patterns.match(Patterns.NON_HIERARCHICAL_PATTERN, request.getTargetId())){
				if(request.getTargetId().endsWith("/" + ShortName.LATEST)){
					controller = new LatestOldestController(SortingPolicy.LATEST);
					request.setTargetId(request.getTargetId() + "/");
					request.setTargetId(request.getTargetId().replace("/"+ShortName.LATEST+"/", ""));
				}
				if (request.getTargetId().endsWith("/" + ShortName.OLDEST)){
					controller = new LatestOldestController(SortingPolicy.OLDEST);
					request.setTargetId(request.getTargetId() + "/");
					request.setTargetId(request.getTargetId().replace("/"+ShortName.OLDEST+"/", ""));
				}
				if (request.getTargetId().endsWith(Patterns.FANOUT_POINT_MATCH)) {
					controller = new FanOutPointController();
					request.setTargetId(request.getTargetId().replaceAll(Patterns.FANOUT_POINT_MATCH, ""));
				}
				String nonHierarchicalUri = UriMapper.getNonHierarchicalUri(request.getTargetId());
				if (nonHierarchicalUri == null){
					throw new ResourceNotFoundException("Resource not found");
				}
				request.setTargetId(nonHierarchicalUri);
			}

			// Notify case
			if(request.getOperation().equals(Operation.NOTIFY)){
				return Redirector.retargetNotify(request);
			}

			// Discovery case
			if (request.getFilterCriteria() != null){
				controller = new DiscoveryController();
			}

			// Determine the appropriate resource controller
			// In case of a CREATE, the resource type determine the controller
			if (controller == null){
				if (request.getOperation().equals(Operation.CREATE)){
					controller = getResourceControllerFromRT(request.getResourceType());
				} else {
					controller = getResourceControllerFromURI(request.getTargetId());
				}        		
			}

			if (controller!=null){
				LOGGER.info("ResourceController ["+ controller.getClass().getSimpleName()+"]");
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
					}
				}
			} else {
				response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
				response.setErrorMessage("Malformed URI");
			}
		} catch (Om2mException om2mException){
			response.setResponseStatusCode(om2mException.getErrorStatusCode());
			response.setErrorMessage(om2mException.getMessage());
			LOGGER.debug("Om2m exception catched in Controller", om2mException);
		} catch(Exception e){
			response.setResponseStatusCode(ResponseStatusCode.INTERNAL_SERVER_ERROR);
			response.setErrorMessage("Router internal error");
			LOGGER.error("Router internal error", e);
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
		// Match the resource controller with an uri pattern and return it, otherwise return null
		if (Patterns.match(Patterns.CSE_BASE_PATTERN, uri)){
			return new CSEBaseController();
		}
		if (Patterns.match(Patterns.AE_PATTERN, uri)){
			return new AEController();
		}
		if (Patterns.match(Patterns.ACP_PATTERN, uri)){
			return new AccessControlPolicyController();
		}
		if (Patterns.match(Patterns.CONTAINER_PATTERN, uri)){
			return new ContainerController();
		}
		if (Patterns.match(Patterns.CONTENTINSTANCE_PATTERN, uri)) {
			return new ContentInstanceController();
		}
		if (Patterns.match(Patterns.REMOTE_CSE_PATTERN, uri)) {
			return new RemoteCSEController();
		}
		if(Patterns.match(Patterns.GROUP_PATTERN, uri)){
			return new GroupController();
		}
		if (Patterns.match(Patterns.NODE_PATTERN, uri)) {
			return new NodeController();
		}
		if(Patterns.match(Patterns.SUBSCRIPTION_PATTERN, uri)){
			return new SubscriptionController();
		}
		if(Patterns.match(Patterns.POLLING_CHANNEL_PATTERN, uri)){
			return new PollingChannelController();
		}
		if(Patterns.match(Patterns.POLLING_CHANNEL_URI_PATTERN, uri)){
			return new PollingChannelUriController();
		}
		if(Patterns.match(Patterns.REQUEST_PATTERN, uri)){
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
			throw new BadRequestException("Resource type is missing");
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
		default : 
			return null;
		}
	}
}
