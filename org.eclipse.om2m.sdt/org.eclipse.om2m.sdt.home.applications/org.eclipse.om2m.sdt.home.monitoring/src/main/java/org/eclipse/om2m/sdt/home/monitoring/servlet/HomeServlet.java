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

import org.eclipse.om2m.sdt.home.monitoring.util.AuthFillter;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.osgi.framework.BundleContext;


public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public HomeServlet(BundleContext context) {
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {		
		if (AuthFillter.validateUserCredentials(request, response)) {			
			response.sendRedirect("/" + Constants.APPNAME + "/monitor/home");	
		} else {
			response.sendRedirect("/" + Constants.APPNAME + "/webapps/login.html");	
		}

	}

}
