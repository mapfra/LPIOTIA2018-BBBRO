package org.eclipse.om2m.sdt.home.monitoring.authentication.service;

public interface AuthenticationService {
	
	/*
	 * Return the authentication page (url)
	 */
	public String getAuthenticationPage(String sessionId, String serverName, int serverPort);

	/**
	 * Service name
	 * @return
	 */
	public Object getServiceName();
	
	/**
	 * authentication info
	 * @return
	 */
	public AuthenticationInfo getAuthenticationInfo(String sessionId);
	
	/**
	 * Retrieve end user info.
	 * @return end user info
	 */
	public UserInfo getEndUserInfo(String sessionId);
	

}
