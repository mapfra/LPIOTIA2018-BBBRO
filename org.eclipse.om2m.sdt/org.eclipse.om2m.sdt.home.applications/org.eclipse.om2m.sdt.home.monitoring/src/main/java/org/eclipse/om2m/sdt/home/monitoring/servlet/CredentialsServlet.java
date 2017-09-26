/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.osgi.framework.BundleContext;

public class CredentialsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CredentialsServlet(BundleContext context) {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sessionId = request.getParameter(SessionManager.SESSION_ID_PARAMETER);

		if ((sessionId == null) || (!SessionManager.getInstance().checkTokenExists(sessionId))) {
			// no valid session =>
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		SessionManager.Session session = SessionManager.getInstance().getSession(sessionId);
		if (session == null) {
			// no valid session =>
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
						return;
		}

		String name = session.getName();
		String password = session.getPassword();
		String cred = name + ':' + password;

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", name);
		jsonObject.put("credentials", cred);

		out.write(jsonObject.toJSONString());
	}

}
