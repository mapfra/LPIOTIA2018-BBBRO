/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.flexcontainerservice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.ipe.sdt.Activator;
import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.args.Command;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.types.DataType;
import org.osgi.framework.ServiceRegistration;

public class ActionFlexContainerService implements FlexContainerService {

    private static Log logger = LogFactory.getLog(ActionFlexContainerService.class);

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
	public Map<String, String> getCustomAttributeValues(List<String> customAttributeNames) 
			throws Om2mException {
		// no value
		return Collections.emptyMap();
	}

	@Override
	public void setCustomAttributeValues(List<CustomAttribute> customAttributes, 
			RequestPrimitive requestPrimitive) throws Om2mException {
		logger.debug("setCustomAttributeValues(" + customAttributes + ")");

//		boolean actionToBeInvoked = false;
		Map<String, Object> args = null;
		
		// two possibles cases to invoke action
		if (requestPrimitive.getContent() != null) {
			// if content is not null, we need to have custom attribute in case of an invocation
			
			if (customAttributes.size() != 0) {
				 args = new HashMap<>();
				// retrieve argument value from custom attribute
				try {
					for (String argName : action.getArgNames()) {
						CustomAttribute ca = getCustomAttribute(customAttributes, argName);
						if (ca != null) {
							DataType type = DataType.getDataType(ca.getType());
							args.put(argName,
								type.getTypeChoice().fromString(ca.getValue()));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

//		if (actionToBeInvoked) {
			// invoke action
			try {
				logger.debug("setCustomAttributeValues(" + customAttributes + ") - invoke action");
				Object response = ((Command) action).invoke(args);
				if (response != null) {
					CustomAttribute output = new CustomAttribute();
					output.setShortName("output");
					output.setValue(response.toString());
					customAttributes.add(output);
				}
			} catch (ActionException e) {
				logger.info("setCustomAttributeValues(" + customAttributes 
						+ ") - KO: " + e.getMessage());
				throw new Om2mException("action execution failed:" + e.getMessage(), ResponseStatusCode.BAD_REQUEST);
			} catch (AccessException e) {
				logger.info("setCustomAttributeValues(" + customAttributes 
						+ ") - KO: " + e.getMessage());
				throw new Om2mException("action execution failed:" + e.getMessage(), ResponseStatusCode.ACCESS_DENIED);
			}
//		}
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
				if (name.equals(ca.getShortName())) {
					return ca;
				}
			}
		}
		return null;
	}

}
