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
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.sdt.home.monitoring.Activator;
import org.eclipse.om2m.sdt.home.monitoring.util.AuthFillter;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.eclipse.om2m.sdt.home.monitoring.util.HttpSessionHelper;

public class LoginServlet extends HttpServlet {

	private static Log LOGGER = LogFactory.getLog(LoginServlet.class);

	private static final long serialVersionUID = 1L;

	private final Activator activator;

	public LoginServlet(Activator pActivator) {
		this.activator = pActivator;
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.info("login " + request.getParameterMap());
		HttpSession session = request.getSession(true);
		
		if (session.isNew()) {
			LOGGER.info("session new !");
		}
		AuthFillter.validateUserCredentials(request, response,
				activator.getAuthenticationServices());
		HttpSessionHelper sessionHelper = new HttpSessionHelper(session);
		LOGGER.info("session: " + sessionHelper.getName() + " / " + sessionHelper.getPassword() + " / " + session.getId());

		if (sessionHelper.getAuthenticatedUser()) {
			response.sendRedirect(Constants.WEBAPPS + "monitor.html");
		} else {
			response.sendRedirect(Constants.WEBAPPS + "login.html?message=error");
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AuthFillter.validateUserCredentials(request, response,
				activator.getAuthenticationServices());
		HttpSessionHelper sessionHelper = new HttpSessionHelper(request.getSession());
		if (sessionHelper.getAuthenticatedUser()) {
			response.sendRedirect(Constants.WEBAPPS + "monitor.html");
		} else {
			response.sendRedirect(Constants.WEBAPPS + "login.html?message=error");
		}
	}

}
