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

import org.eclipse.om2m.sdt.home.monitoring.util.AeRegistration;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;

public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public LogoutServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// invalidate session
		
		HttpSession httpSession = request.getSession();
		if (httpSession != null) {
			AeRegistration.getInstance().deassociateSubscriptionAndSessions(httpSession.getId());
			httpSession.invalidate();
		}
		response.sendRedirect(Constants.WEBAPPS + "login.html");
	}
	
}
