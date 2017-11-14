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

import org.eclipse.om2m.sdt.home.monitoring.util.AeRegistration;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.osgi.framework.BundleContext;

public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public LogoutServlet(BundleContext context) {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String sessionId = request.getParameter(SessionManager.SESSION_ID_PARAMETER);
		if (sessionId != null) {
			SessionManager.Session session = SessionManager.getInstance().removeSession(sessionId);
			AeRegistration.getInstance().deassociateSubscriptionAndSessions(session.getId());
		}
		response.sendRedirect("/" + Constants.WEBAPPS + "login.html");
	}
	
}
