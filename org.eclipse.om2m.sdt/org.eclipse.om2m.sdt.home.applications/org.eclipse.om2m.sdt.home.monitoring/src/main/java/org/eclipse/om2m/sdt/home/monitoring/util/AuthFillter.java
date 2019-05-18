/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.sdt.home.monitoring.authentication.service.AuthenticationInfo;
import org.eclipse.om2m.sdt.home.monitoring.authentication.service.AuthenticationService;

public class AuthFillter implements Constants {
	
	private static Log LOGGER = LogFactory.getLog(AuthFillter.class);

	public static void validateUserCredentials(HttpServletRequest request, HttpServletResponse response,
			List<AuthenticationService> authenticationServices) throws IOException {
//		boolean isValid = false;
		String name = "";
		String password = "";
		String bearer = null;
		String clientId = null;
		String serviceName = null;
		String sessionId = null;
		
		HttpSession httpSession = request.getSession();
		sessionId = httpSession.getId();
		
		if ((request.getParameter(NAME) != null) && (request.getParameter(PASSWORD) != null)) {
			name = request.getParameter(NAME);
			password = request.getParameter(PASSWORD);
			LOGGER.info("parameters " + name + "/" + password);
		} else if (request.getHeader(AUTHORIZATION) != null) {
			LOGGER.info("Headers Authorization " + request.getHeader(AUTHORIZATION) 
					+ REQUESTED + request.getHeader(REQUESTED));
			response.addHeader(AUTHENTICATE, "Basic");
			response.addHeader(AUTHORIZATION, request.getHeader(AUTHORIZATION));
			if (request.getHeader(REQUESTED) != null)
				response.addHeader(REQUESTED, request.getHeader(REQUESTED));
			String authHeader = request.getHeader(AUTHORIZATION);
			String cred = new String(Base64.decodeBase64(authHeader.substring(6).getBytes()));
			int idx = cred.indexOf(":");
			name = cred.substring(0, idx);
			password = cred.substring(idx + 1);
			LOGGER.info("headers " + name + "/" + password);
		} else if ((serviceName = request.getParameter("serviceName")) != null) {
			AuthenticationService as = getAuthenticationService(serviceName, authenticationServices);
			if (as != null) {
				AuthenticationInfo ai = as.getAuthenticationInfo(sessionId);
				bearer = ai.getAccessToken();
				clientId = ai.getClientId();
				name = as.getEndUserInfo(sessionId).getUserId();
			}
		} else 
			LOGGER.info("void");
		
		boolean createNewSession = false;
		if (bearer != null) {
			// check bearer
			createNewSession = true;
		} else {
			String result = ResourceDiscovery.validateUserCredentials(name, password);
			LOGGER.info("result=" + result);
			if (result != null) {
				createNewSession = true;
			}
		}
			
		if (createNewSession) {
			// create new session
			HttpSessionHelper sessionHelper = new HttpSessionHelper(httpSession);
			sessionHelper.setAuthenticationUser(Boolean.TRUE);
			sessionHelper.setName(name);
			sessionHelper.setPassword(password);
			sessionHelper.setBearer(bearer);
			sessionHelper.setClientId(clientId);
			sessionHelper.setServiceName(serviceName);
		}

		if (/*! isValid && */request.getHeader(REQUESTED) != null) {
			response.addHeader(AUTHENTICATE, "Basic");
			LOGGER.info(REQUESTED + " " + name + "/" + password);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, null);
			return;
		}
		LOGGER.info("authenticated " + name + "/" + password);
	}
	
	private static AuthenticationService getAuthenticationService(String name, 
			List<AuthenticationService> authenticationServices) {
		for (AuthenticationService as : authenticationServices) {
			if (name.equals(as.getServiceName())) {
				return as;
			}
		}
		return null;
	}
	
}
