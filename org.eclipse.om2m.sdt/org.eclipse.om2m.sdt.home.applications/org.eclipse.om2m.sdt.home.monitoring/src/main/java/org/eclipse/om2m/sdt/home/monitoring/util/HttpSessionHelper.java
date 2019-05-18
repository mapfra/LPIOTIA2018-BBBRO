package org.eclipse.om2m.sdt.home.monitoring.util;

import javax.servlet.http.HttpSession;

public class HttpSessionHelper {
	
	private final HttpSession httpSession;
	
	private static final String NAME = "name";
	private static final String PASSWORD = "password";
	private static final String BEARER = "bearer";
	private static final String CLIENT_ID = "clientId";
	private static final String SERVICE_NAME = "serviceName";
	private static final String AUTHENTICATED_USER = "authenticatedUser";
	
	public HttpSessionHelper(HttpSession pHttpSession) {
		this.httpSession = pHttpSession;
	}
	
	public void setName(String name) {
		httpSession.setAttribute(NAME, name);
	}
	
	public String getName() {
		return (String) httpSession.getAttribute(NAME);
	}
	
	public void setPassword(String password) {
		httpSession.setAttribute(PASSWORD, password);
	}
	
	public String getPassword() {
		return (String) httpSession.getAttribute(PASSWORD);
	}
	
	public void setBearer(String bearer) {
		httpSession.setAttribute(BEARER, bearer);
	}
	
	public String getBearer() {
		return (String) httpSession.getAttribute(BEARER);
	}
	
	public void setClientId(String clientId) {
		httpSession.setAttribute(CLIENT_ID, clientId);
	}
	
	public String getClientId() {
		return (String) httpSession.getAttribute(CLIENT_ID);
	}
	
	public void setServiceName(String serviceName) {
		httpSession.setAttribute(SERVICE_NAME, serviceName);
	}
	
	public String getServiceName() {
		return (String) httpSession.getAttribute(SERVICE_NAME);
	}
	
	public void setAuthenticationUser(Boolean authenticatedUser) {
		httpSession.setAttribute(AUTHENTICATED_USER, authenticatedUser);
	}
	
	public boolean getAuthenticatedUser() {
		Boolean b = (Boolean) httpSession.getAttribute(AUTHENTICATED_USER);
		return (b == null) ? false : b.booleanValue();
	}
	
}
