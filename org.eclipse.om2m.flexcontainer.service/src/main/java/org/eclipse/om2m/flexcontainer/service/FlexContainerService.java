/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.flexcontainer.service;

import java.util.List;
import java.util.Map;

import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.RequestPrimitive;

public interface FlexContainerService {

	/**
	 * Get the most updated value of a custom attribute
	 * 
	 * @param name
	 *            name of the custom attribute
	 * @return the most updated value of the custom attribute
	 */
	public String getCustomAttributeValue(String customAttributeName) throws Om2mException;

	/**
	 * Get the most updated values of a list of custom attributes
	 * 
	 * @param customAttributeNames
	 *            name of the custom attributes
	 * @return the most updated values of the custom attributes
	 */
	public Map<String, String> getCustomAttributeValues(List<String> customAttributeNames) throws Om2mException;

	/**
	 * Set a new value for a customAttribute
	 * 
	 * @param customAttributes
	 *            custom attributes
	 * @param requestPrimitive
	 *            request primitive
	 */
	public void setCustomAttributeValues(List<CustomAttribute> customAttributes, RequestPrimitive requestPrimitive)
			throws Om2mException;

	/**
	 * Retrieve the flexContainerLocation
	 * 
	 * @return flexContainer location
	 */
	public String getFlexContainerLocation();

}
