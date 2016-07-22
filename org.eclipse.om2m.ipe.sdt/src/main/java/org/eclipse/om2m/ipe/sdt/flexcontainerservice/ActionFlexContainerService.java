/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.flexcontainerservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.ipe.sdt.Activator;
import org.onem2m.sdt.Action;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.ActionException;
import org.onem2m.sdt.impl.Command;
import org.osgi.framework.ServiceRegistration;

public class ActionFlexContainerService implements FlexContainerService {

	private final Action action;
	private final String resourceId;
	private ServiceRegistration serviceRegistration;
	
	public ActionFlexContainerService(final Action pAction, final String pResourceId) {
		action = pAction;
		resourceId = pResourceId;
	}
	
	public void register() {
		if (serviceRegistration != null) {
			return;
		}
		serviceRegistration = Activator.registerFlexContainerService(this);
	}

	public void unregister() {
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
			serviceRegistration = null;
		}
	}

	@Override
	public String getCustomAttributeValue(String customAttributeName) throws Om2mException {
		// no value
		return null;
	}

	@Override
	public void setCustomAttributeValues(List<CustomAttribute> customAttributes, RequestPrimitive requestPrimitive)
			throws Om2mException {

		boolean actionToBeInvoked = false;
		Map<String, Object> args = null;
		
		// two possibles cases to invoke action
		if (requestPrimitive.getContent() != null) {
			// if content is not null, we need to have custom attribute in case of an invocation
			
			if (customAttributes.size() != 0) {
				 args = new HashMap<>();
				// retrieve argument value from custom attribute
				for(String argName : action.getArgNames()) {
					CustomAttribute ca = getCustomAttribute(customAttributes, argName);
					if (ca != null) {
						args.put(argName, ca.getCustomAttributeValue());
					}
				}
				
				actionToBeInvoked = true;
			} /* else nothing to do */
		
		} else {
			actionToBeInvoked = true;
		}
		
		if (actionToBeInvoked) {
			// invoke action
			try {
				Object response = ((Command) action).invoke(args);
				if (response != null) {
					CustomAttribute output = new CustomAttribute();
					output.setCustomAttributeName("output");
					output.setCustomAttributeType("xs:string");
					output.setCustomAttributeValue(response.toString());
					customAttributes.add(output);
				}
				
			} catch (ActionException e) {
				throw new Om2mException("action execution failed:" + e.getMessage(), ResponseStatusCode.BAD_REQUEST);
			} catch (AccessException e) {
				throw new Om2mException("action execution failed:" + e.getMessage(), ResponseStatusCode.ACCESS_DENIED);
			}
		}
		
	}

	@Override
	public String getFlexContainerLocation() {
		return resourceId;
	}
	
	/**
	 * Retrieve a CustomAttribute based on its name
	 * 
	 * @param customAttributes
	 *            list of custom attribute
	 * @param name
	 *            name of the CustomAttribute to be retrieved
	 * @return the customAttribute instance if found in the list or null
	 */
	private static CustomAttribute getCustomAttribute(List<CustomAttribute> customAttributes, String name) {
		if (customAttributes != null) {
			for (CustomAttribute ca : customAttributes) {
				if (name.equals(ca.getCustomAttributeName())) {
					return ca;
				}
			}
		}
		return null;
	}
}
