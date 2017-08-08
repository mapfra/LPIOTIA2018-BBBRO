package org.eclipse.om2m.sdt.home.monitoring.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.sdt.home.monitoring.util.AeRegistration;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InCseContextServlet extends HttpServlet {

	private static Log LOGGER = LogFactory.getLog(InCseContextServlet.class);
	
	private static final long serialVersionUID = 1L;
	private static final String RESOURCE_ID = "resourceId";
	private static final String GET_NOTIFICATIONS= "/notifications";

	public InCseContextServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sessionId = req.getParameter(SessionManager.SESSION_ID_PARAMETER);
		if (!SessionManager.getInstance().checkTokenExists(sessionId)) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		String pathInfo = req.getPathInfo();
		if (pathInfo == null) {
			String cseId = Constants.CSE_ID;
			String cseName = Constants.CSE_NAME;
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().print("~/" + cseId + "/" + cseName);
		} else if (GET_NOTIFICATIONS.equals(pathInfo)) {
			// retrieve notifications
			List<JSONObject> notifications = AeRegistration.getInstance().getNotificationsAndClears(sessionId);
			JSONArray globalJson = new JSONArray();
			for(JSONObject notification : notifications) {
				globalJson.add(notification);
			}
			resp.setHeader("Content-Type", "application/json");
			resp.getWriter().print(globalJson.toJSONString());
			resp.setStatus(HttpServletResponse.SC_OK);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		LOGGER.info("doPost(subscribeTo)");

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) parser.parse(req.getReader());
		} catch (ParseException e) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
					"json payload is incorrect: valid format is {'resourceId':'url'}");
			return;
		} catch (ClassCastException e) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
					"json payload is incorrect: valid format is {'resourceId':'url'}");
			return;
		}
		
		String resourceId = null;
		String sessionId = null;
		try {
			resourceId = (String) jsonObject.get(RESOURCE_ID);
			sessionId = (String) jsonObject.get(SessionManager.SESSION_ID_PARAMETER);
			if (!AeRegistration.getInstance().createSubscription(resourceId, sessionId)) {
				resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
						"unable to create subscription");
				return;
			}
		} catch (ClassCastException e) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE,
					"json payload is incorrect: valid format is {'resourceId':'url'}");
			return;
		}
		LOGGER.debug("doPost(subscribeTo=" + resourceId + ")");
		
		resp.setStatus(HttpServletResponse.SC_OK);

	}
	

}
