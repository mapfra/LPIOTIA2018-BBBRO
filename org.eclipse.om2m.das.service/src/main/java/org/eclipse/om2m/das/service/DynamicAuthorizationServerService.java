package org.eclipse.om2m.das.service;

import java.util.List;

import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.exceptions.AccessDeniedException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;

public interface DynamicAuthorizationServerService {

	/**
	 * Returns true if the DynamicAuthorizationServer is enabled.
	 */
	public boolean isEnabled();
	
	/**
	 * 
	 * @return the list of Point of Access
	 */
	public List<String> getPoA();
	
	/**
	 * 
	 * @return lifetime
	 */
	public String getLifetime();
	
	/**
	 * Check if a request could be executed by the CSE.
	 * A request is sent to the Dynamic Authorization Server in order to perform authorization phase.
	 * @param request request to be performed
	 * @param resourceEntity related resource entity
	 * @throws AccessDeniedException 
	 */
	public void authorize(RequestPrimitive request, ResourceEntity resourceEntity) throws AccessDeniedException;
	
}
