package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		SessionManager.Session session = null;
		if (sessionId != null) {
			session = SessionManager.getInstance().removeSession(sessionId);
			AeRegistration.getInstance().deassociateSubscriptionAndSessions(session.getId());
		}
		
		response.sendRedirect("/" + Constants.APPNAME + "/webapps/login.html");
	}
	
}
