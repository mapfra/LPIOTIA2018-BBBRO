package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.eclipse.om2m.sdt.home.monitoring.util.HttpSessionHelper;
import org.osgi.service.http.HttpContext;

public class MyHttpContext implements HttpContext {

	private final HttpContext defaultHttpContext;

	public MyHttpContext(HttpContext defaultHttpContext) {
		this.defaultHttpContext = defaultHttpContext;
	}

	@Override
	public String getMimeType(String arg0) {
		return defaultHttpContext.getMimeType(arg0);
	}

	@Override
	public URL getResource(String arg0) {
		return defaultHttpContext.getResource(arg0);
	}

	@Override
	public boolean handleSecurity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// some urls do not require any security
		if (checkUrl(request)) {
			return true;
		}

		HttpSession httpSession = request.getSession();
		if (httpSession != null) {
			HttpSessionHelper sessionHelper = new HttpSessionHelper(httpSession);
			if (sessionHelper.getAuthenticatedUser()) {
				return true;
			}
		}

		// if we reach out this point, it means that the user is not authenticated
		// redirect to login page
		response.sendRedirect(Constants.WEBAPPS + "login.html");
		return true;
	}

	private boolean checkUrl(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath() + "/";
		String pathInfo = request.getPathInfo();
		System.out.println(contextPath);
		System.out.println("servletPath=" + servletPath);

		if (servletPath.equals("/Home_Monitoring_Application/security/login/")) {
			return true;
		}

		if (servletPath.equals("/Home_Monitoring_Application/authentication_servlet/")
			&& request.getMethod().equals("GET")) {
				return true;
		}

		if (servletPath.equals(Constants.WEBAPPS)
			&& (pathInfo.equals("/login.html")
				|| pathInfo.startsWith("/css/") || pathInfo.startsWith("/images/")
				|| pathInfo.startsWith("/js/") || pathInfo.startsWith("/fonts/")
				|| pathInfo.startsWith("/resources/"))) {
			return true;
		}
		
		return false;
	}

}
