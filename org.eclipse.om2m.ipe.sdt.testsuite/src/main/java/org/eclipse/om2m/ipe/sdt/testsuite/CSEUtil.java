/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite;

import java.util.List;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.DiscoveryResultType;
import org.eclipse.om2m.commons.constants.FilterUsage;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.core.service.CseService;

public class CSEUtil {
	
	public static ResponsePrimitive retrieveEntity(CseService cseService, String flexContainerLocation) {
		
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.RETRIEVE);
		request.setTo(flexContainerLocation);
		
		return cseService.doRequest(request);
		
	}
	
	public static ResponsePrimitive updateFlexContainerEntity(CseService cseService, String flexContainerLocation, FlexContainer flexContainer) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.UPDATE);
		request.setContent(flexContainer);
		request.setTo(flexContainerLocation);
		
		return cseService.doRequest(request);
	}
	
	public static ResponsePrimitive discovery(final CseService cseService, final List<String> labels, final String to) {
		
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.RETRIEVE);
		request.setDiscoveryResultType(DiscoveryResultType.HIERARCHICAL);
		FilterCriteria filterCriteria = new FilterCriteria();
		filterCriteria.getLabels().addAll(labels);
		filterCriteria.setFilterUsage(FilterUsage.DISCOVERY_CRITERIA);
		request.setFilterCriteria(filterCriteria);
		request.setTo(to);
		
		return cseService.doRequest(request);
	}
	
	public static ResponsePrimitive createSubscription(final CseService cseService, final Subscription subscription, final String subscriptionLocation, final String subscriptionName) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.CREATE);
		request.setContent(subscription);
		request.setTo(subscriptionLocation);
		request.setResourceType(ResourceType.SUBSCRIPTION);
		request.setName(subscriptionName);
		
		return cseService.doRequest(request);
	}
	
	public static ResponsePrimitive deleteResource(final CseService cseService, final String resourceUrl) {
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.DELETE);
		request.setTo(resourceUrl);
		
		return cseService.doRequest(request);
	}

}
