package org.eclipse.om2m.flexcontainer.service;

import java.util.List;

import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.RequestPrimitive;

public interface FlexContainerService {

	/**
	 * Get the most updated value of a custom attribute
	 * 
	 * @param customAttributeName
	 *            name of the custom attribute
	 * @return the most updated value of the custom attribute
	 */
	public String getCustomAttributeValue(String customAttributeName) throws Om2mException;

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
