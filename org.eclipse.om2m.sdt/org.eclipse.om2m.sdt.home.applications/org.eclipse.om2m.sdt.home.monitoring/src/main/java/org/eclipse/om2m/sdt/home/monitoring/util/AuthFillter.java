package org.eclipse.om2m.sdt.home.monitoring.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AuthFillter {
	
	private static Log LOGGER = LogFactory.getLog(AuthFillter.class);

	public static boolean validateUserCredentials(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		boolean isValid=false;
		String name = "";
		String password = "";
		if (request.getParameter("name") != null && request.getParameter("password") != null) {
			name = request.getParameter("name");
			password = request.getParameter("password");
			LOGGER.debug("parameters " + name + "/" + password);
		} else if (session.getAttribute("name") != null && session.getAttribute("password") != null) {
			name = session.getAttribute("name").toString();
			password = session.getAttribute("password").toString();
			LOGGER.debug("attributes " + name + "/" + password);
		} else if (request.getHeader("Authorization") != null) {
			LOGGER.debug("Headers Authorization " + request.getHeader("Authorization") 
					+ "/X-Requested-With " + request.getHeader("X-Requested-With"));
			response.addHeader("WWW-Authenticate", "Basic");
			response.addHeader("Authorization", request.getHeader("Authorization"));
			if (request.getHeader("X-Requested-With") != null)
				response.addHeader("X-Requested-With", request.getHeader("X-Requested-With"));
			String authHeader = request.getHeader("Authorization");
			String cred = new String(Base64.decodeBase64(authHeader.substring(6).getBytes()));
			int idx = cred.indexOf(":");
			name = cred.substring(0, idx);
			password = cred.substring(idx + 1);
		}
		String result = ResourceDiscovery.validateUserCredentials(name, password);
		if (result != null) {
			isValid = true;
			session.setAttribute("name", name);
			session.setAttribute("password", password);
		}

		if (! isValid && request.getHeader("X-Requested-With") != null) {
			response.addHeader("WWW-Authenticate", "Basic");
			LOGGER.debug("X-Requested-With " + name + "/" + password + " auth=" + isValid);
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, null);
		}
		LOGGER.debug(name + "/" + password + " auth=" + isValid);
		return isValid;
	}
	
}
