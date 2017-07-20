package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.om2m.sdt.home.monitoring.util.AuthFillter;
import org.eclipse.om2m.sdt.home.monitoring.util.Constants;
import org.eclipse.om2m.sdt.home.monitoring.util.FileUtil;
import org.osgi.framework.BundleContext;


public class MonitorHomeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BundleContext context;

	public MonitorHomeServlet(BundleContext context) {
		this.context = context;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (AuthFillter.validateUserCredentials(request, response)) {			
			String monitorHome = FileUtil.getFileAsString("webapps/monitor.html", context);
			monitorHome = monitorHome.replace("%USERNAME%", 
					request.getSession().getAttribute("name").toString());
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(monitorHome);		
		} else if (request.getHeader("X-Requested-With") == null)
			response.sendRedirect("/" + Constants.APPNAME + "/webapps/login.html");
	}

}
