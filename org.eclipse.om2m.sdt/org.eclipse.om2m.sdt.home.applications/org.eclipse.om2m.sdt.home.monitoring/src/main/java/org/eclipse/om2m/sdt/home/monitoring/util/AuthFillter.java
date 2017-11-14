/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.sdt.home.monitoring.servlet.SessionManager;

public class AuthFillter implements Constants {
	
	private static Log LOGGER = LogFactory.getLog(AuthFillter.class);

	public static SessionManager.Session validateUserCredentials(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		boolean isValid = false;
		String name = "";
		String password = "";
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
		} else
			LOGGER.info("void");
		String result = ResourceDiscovery.validateUserCredentials(name, password);
		LOGGER.info("result=" + result);
		if (result != null) {
			// create new session
			return SessionManager.getInstance().createNewSession(name, password);
		}

		if (/*! isValid && */request.getHeader(REQUESTED) != null) {
			response.addHeader(AUTHENTICATE, "Basic");
			LOGGER.info(REQUESTED + " " + name + "/" + password);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, null);
		}
		LOGGER.info(name + "/" + password);
		return null;
	}
	
}
