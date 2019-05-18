package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.om2m.sdt.home.monitoring.Activator;
import org.eclipse.om2m.sdt.home.monitoring.authentication.service.AuthenticationService;

public class AuthenticationServiceServlet extends HttpServlet {

	public static final String SERVLET_PATH = "/authentication_servlet";

	private final Activator activator;

	public AuthenticationServiceServlet(Activator activator) {
		this.activator = activator;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		String requestInfo = req.getPathInfo();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String sessionId = null;
		
		// retrieve httpsession id
		HttpSession httpSession = req.getSession();
		if (httpSession != null) {
			sessionId = httpSession.getId();
		}
		if ((requestInfo != null) || (sessionId == null)) {
			resp.setStatus(404);
			return;
		}
		String ret = "[";
		boolean first = true;
		for (AuthenticationService s : activator.getAuthenticationServices()) {
			if (first) first = false;
			else ret += ",";
			ret += "{\"authenticationPage\":\"" 
					+ s.getAuthenticationPage(sessionId, serverName, serverPort)
				+ "\",\"serviceName\":\"" + s.getServiceName() + "\"}";
		}
		ret += "]";
		resp.setHeader("Content-Type", "application/json");
		resp.getWriter().println(ret);
		resp.setStatus(200);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestInfo = req.getPathInfo();
		// TO BE DELETED
		if (requestInfo == null) {
			String sessionId = req.getParameter("sessionId");
		} else {
			resp.setStatus(404);
		}
	}

}
