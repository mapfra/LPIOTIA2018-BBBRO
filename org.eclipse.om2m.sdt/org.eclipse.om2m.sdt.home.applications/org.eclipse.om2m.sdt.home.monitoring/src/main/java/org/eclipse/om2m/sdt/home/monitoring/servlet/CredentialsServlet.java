package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;

public class CredentialsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CredentialsServlet(BundleContext context) {
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("name");
		String password = (String)session.getAttribute("password");
		String cred = name + ':' + password;

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();	
		StringBuilder json= new StringBuilder();
		json.append(cred);

		out.write(json.toString());	
	}

}
