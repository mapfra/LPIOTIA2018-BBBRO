package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.om2m.commons.constants.Constants;

public class InCseContextServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public InCseContextServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cseId = Constants.CSE_ID;
		String cseName = Constants.CSE_NAME;
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().print("~/" + cseId + "/" + cseName);
	}

}
