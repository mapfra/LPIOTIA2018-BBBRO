/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.sdt.home.monitoring.util.AuthFillter;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.osgi.framework.BundleContext;

public class LoginServlet extends HttpServlet {

	private static Log LOGGER = LogFactory.getLog(LoginServlet.class);

	private static final long serialVersionUID = 1L;

	public LoginServlet(BundleContext context) {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		LOGGER.info("login " + request.getParameterMap());
		SessionManager.Session session = AuthFillter.validateUserCredentials(request, response);
		if (session != null) {	
			response.sendRedirect("/" + Constants.WEBAPPS + "monitor.html?" 
					+ SessionManager.SESSION_ID_PARAMETER + "=" + session.getId());
		} else {
			response.sendRedirect("/" + Constants.WEBAPPS + "login.html?message=error");
		}
	}

}
