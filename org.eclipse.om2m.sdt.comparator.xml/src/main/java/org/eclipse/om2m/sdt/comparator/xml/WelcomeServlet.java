package org.eclipse.om2m.sdt.comparator.xml;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.om2m.commons.constants.Constants;

/**
 * WelcomeServlet is registered on /comparator
 * 
 * @author MPCY8647
 *
 */
public class WelcomeServlet extends HttpServlet {

	private static final String INDEX_HTML = "index.html";
	private static final String CONTEXT_PARAMETER = "?context=";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String cseContextPath = Activator.SEP + Constants.CSE_ID + Activator.SEP + Constants.CSE_NAME; 
		// redirect to index.html file
		resp.sendRedirect(Activator.SEP + Activator.CONTEXT_URI + Activator.SEP + Activator.RESOURCES_URI
				+ Activator.SEP + INDEX_HTML + CONTEXT_PARAMETER + cseContextPath);
	}
}
