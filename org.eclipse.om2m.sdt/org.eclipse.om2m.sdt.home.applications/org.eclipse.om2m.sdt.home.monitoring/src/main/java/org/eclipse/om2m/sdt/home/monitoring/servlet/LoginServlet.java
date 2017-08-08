package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.om2m.sdt.home.monitoring.util.AuthFillter;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.osgi.framework.BundleContext;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public LoginServlet(BundleContext context) {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		SessionManager.Session session = null;
		if ((session = AuthFillter.validateUserCredentials(request, response)) != null) {	
			response.sendRedirect("/" + Constants.APPNAME + "/monitor/home?" + SessionManager.SESSION_ID_PARAMETER + "=" + session.getId());
		} else {
			response.sendRedirect("/" + Constants.APPNAME + "/webapps/login.html?message=error");
		}
	}
	
	

}
