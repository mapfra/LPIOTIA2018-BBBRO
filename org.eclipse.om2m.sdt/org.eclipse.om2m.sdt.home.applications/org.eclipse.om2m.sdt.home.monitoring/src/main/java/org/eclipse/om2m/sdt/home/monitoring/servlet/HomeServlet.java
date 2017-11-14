/*******************************************************************************
 * Copyright (c) 2013-2014 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test
 *         and documentation.
 ******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.eclipse.om2m.sdt.home.monitoring.util.FileUtil;
import org.osgi.framework.BundleContext;

public class HomeServlet extends HttpServlet {
	
	private static Log LOGGER = LogFactory.getLog(HomeServlet.class);

	private static final long serialVersionUID = 1L;

	public HomeServlet(BundleContext context) {
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// check session
		LOGGER.info("home " + request.getParameterMap());
		String sessionId = request.getParameter(SessionManager.SESSION_ID_PARAMETER);
		if ((sessionId != null) && SessionManager.getInstance().checkTokenExists(sessionId)) {
			response.sendRedirect("/" + Constants.APPNAME + "/monitor/home");
		} else {
			String redirect = "/" + Constants.WEBAPPS + "login.html";
			String name = request.getParameter(Constants.NAME);
			String password = request.getParameter(Constants.PASSWORD);
			if (! (FileUtil.isEmpty(name) || FileUtil.isEmpty(password))) {
				redirect += "?" + Constants.NAME + "=" + name 
						+ "&" + Constants.PASSWORD + "=" + password;
			}
			response.sendRedirect(redirect);	
		}
	}

}
