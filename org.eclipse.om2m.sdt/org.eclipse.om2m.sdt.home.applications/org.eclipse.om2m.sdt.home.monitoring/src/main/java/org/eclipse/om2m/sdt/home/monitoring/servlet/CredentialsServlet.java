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
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.eclipse.om2m.sdt.home.monitoring.util.HttpSessionHelper;
import org.json.simple.JSONObject;

public class CredentialsServlet extends HttpServlet {
	
	private static Log LOGGER = LogFactory.getLog(CredentialsServlet.class);

	private static final long serialVersionUID = 1L;

	public CredentialsServlet() {
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String sessionId = request.getParameter(SessionManager.SESSION_ID_PARAMETER);
		HttpSession session = request.getSession();
//		SessionManager.Session session = SessionManager.getInstance().getSession(sessionId);
		if (session == null) {
			// no valid session =>
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		if (session.isNew()) {
			LOGGER.info("session is new");
		}
		
		HttpSessionHelper sessionHelper = new HttpSessionHelper(session);
		String name = sessionHelper.getName();
		String password = sessionHelper.getPassword();
		LOGGER.info("session: " + name + " / " + password + " / " + session.getId());

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.NAME, name);
		if (sessionHelper.getBearer() != null) {
			jsonObject.put(Constants.BEARER, sessionHelper.getBearer());
			jsonObject.put(Constants.CLIENT_ID, sessionHelper.getClientId());
		}
		if (! ((name == null) || name.isEmpty() || (password == null) || password.isEmpty())) {
			jsonObject.put(Constants.CREDENTIALS, name + ':' + password);
		}

		response.setContentType("application/json");
		response.getWriter().write(jsonObject.toJSONString());
	}

}
