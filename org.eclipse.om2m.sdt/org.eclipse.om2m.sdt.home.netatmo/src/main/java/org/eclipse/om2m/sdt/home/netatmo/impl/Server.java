/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.eclipse.om2m.sdt.home.netatmo.model.Event;
import org.eclipse.om2m.sdt.home.netatmo.model.Home;
import org.eclipse.om2m.sdt.home.netatmo.model.Person;
import org.eclipse.om2m.sdt.home.netatmo.model.Token;
import org.eclipse.om2m.sdt.home.netatmo.model.WeatherStation;
import org.eclipse.om2m.sdt.home.netatmo.model.WeatherStationModule;
import org.eclipse.om2m.sdt.home.netatmo.model.WelcomeCamera;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Server {
	
	static class Response {
		int code;
		JSONObject json;
		String message;
	}

	private static final String CLIENT_ID_NAME = "client_id";
	private static final String CLIENT_SECRET_NAME = "client_secret";
	private static final String GRANT_TYPE_NAME = "grant_type";
	private static final String USERNAME_NAME = "username";
	private static final String PASSWORD_NAME = "password";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String SCOPE_NAME = "scope";
	private static final String HOME_ID = "home_id";
	private static final String ACCESS_TOKEN = "access_token";
	private static final String EXPIRE_IN = "expire_in";
	private static final String SIZE = "size";
	private static final String BODY = "body";
	private static final String DEVICES = "devices";
	private static final String HOMES = "homes";
	private static final String ON = "on";
	
	private static final String GRANT_TYPE_VALUE = "password";
	private static final String GRANT_TYPE_REFRESH = "refresh_token";

	private static final String NETATMO_CLOUD_BASE_URL = "https://api.netatmo.com";
	private static final String OAUTH_TOKEN_URL = "/oauth2/token";
	private static final String GET_HOME_DATA = "/api/gethomedata";
	private static final String GET_STATIONS_DATA = "/api/getstationsdata";

	private final String clientId;
	private final String clientSecret;
	private final String grantType;
	private final String username;
	private final String password;
	private Token token;

	public Server(String pClientId, String pClientSecret, String pUsername, String pPassword) {
		clientId = pClientId;
		clientSecret = pClientSecret;
		username = pUsername;
		password = pPassword;
		grantType = GRANT_TYPE_VALUE;
	}
	
	protected Token getToken() {
		synchronized (this) {
			if (token == null) {
				// authenticate
				token = authenticate();
			} else if (! token.isValid()) {
				token = refreshToken(token);
			}
		}
		return token;
	}

	public List<WeatherStation> getStationsData() {
		List<WeatherStation> wss = new ArrayList<>();

		Token currentToken = getToken();
		if (currentToken == null) {
			Activator.logger.info("current token is null", Server.class);
			return wss;
		}

		try {
			// retrieve weather stations data
			Response resp = post(ACCESS_TOKEN + "=" + currentToken.getAccessToken(),
					NETATMO_CLOUD_BASE_URL + GET_STATIONS_DATA);
			
			if (resp.code == HttpURLConnection.HTTP_OK) {
				JSONObject body = (JSONObject) resp.json.get(BODY);
				for (Object deviceObject : (JSONArray) body.get(DEVICES)) {
					JSONObject device = (JSONObject) deviceObject;
					WeatherStation ws = (WeatherStation) retrieveWeatherStationData(device);

					// for each module
					JSONArray modules = (JSONArray) device.get(WeatherStation.MODULES);
					for (Object module : modules) {
						ws.addOrUpdateModule(retrieveWeatherStationData((JSONObject) module));
					}
					wss.add(ws);
				}
			} else { // ko
				Activator.logger.info("bad getstationsdata (code=" + resp.code
						+ ", message=" + resp.message + ")", Server.class);
				if (resp.code == 403) {
					// invalid token
					currentToken.invalidateToken();
				}
			}
		} catch (IOException e) {
			Activator.logger.warning("unable to open connection", Server.class, e);
		}
		return wss;
	}

	/**
	 * @param deviceObject
	 * @return
	 */
	private WeatherStationModule retrieveWeatherStationData(JSONObject deviceJsonObject) {
		// id + name
		String id = (String) deviceJsonObject.get(WeatherStation.ID);
		String name = (String) deviceJsonObject.get(WeatherStation.NAME);
		WeatherStation ws = new WeatherStation(id, name);

		// dashboard_data
		JSONObject dashboardData = (JSONObject) deviceJsonObject.get(WeatherStation.DASHBOARD_DATA);

		// datatypes
		JSONArray dataTypesJsonArray = (JSONArray) deviceJsonObject.get(WeatherStation.DATA_TYPE);
		for (Object dataTypeObject : dataTypesJsonArray) {
			ws.addDataType((String) dataTypeObject);
		}

		List<String> dataTypes = ws.getDataTypes();
		if (dataTypes.contains(WeatherStation.PRESSURE)) {
			ws.setAbsolutePressure(getDoubleValue(dashboardData.get(WeatherStation.ABSOLUTE_PRESSURE)));
			ws.setPressure(getDoubleValue(dashboardData.get(WeatherStation.PRESSURE)));
		}

		if (dataTypes.contains(WeatherStation.TEMPERATURE_DATA_TYPE)) {
			ws.setCurrentTemperature(getDoubleValue(dashboardData.get(WeatherStation.TEMPERATURE)));
			ws.setTemperatureTrend((String) dashboardData.get(WeatherStation.TEMPERATURE_TREND));
			ws.setMinTemperature(getDoubleValue(dashboardData.get(WeatherStation.MIN_TEMPERATURE)));
			ws.setMaxTemperature(getDoubleValue(dashboardData.get(WeatherStation.MAX_TEMPERATURE)));
		}

		if (dataTypes.contains(WeatherStation.HUMIDITY_DATA_TYPE)) {
			ws.setHumidity((long) dashboardData.get(WeatherStation.HUMIDITY));
		}

		if (dataTypes.contains(WeatherStation.CO2_DATA_TYPE)) {
			ws.setCo2((long) dashboardData.get(WeatherStation.CO2));
		}

		if (dataTypes.contains(WeatherStation.NOISE_DATA_TYPE)) {
			ws.setNoise((long) dashboardData.get(WeatherStation.NOISE));
		}
		
		// date
		ws.setDate((long) dashboardData.get(WeatherStation.DATE));
		return ws;
	}

	public List<Home> getHomeData(String homeId, Long eventSize) {
		Token currentToken = getToken();
		List<Home> homes = new ArrayList<>();

		if (currentToken == null) {
			Activator.logger.info("current token is null");
			return homes;
		}
		try {
			StringBuffer sb = new StringBuffer();
			// access token - mandatory
			sb.append(ACCESS_TOKEN).append("=").append(currentToken.getAccessToken());
			// homeId - optional
			if (homeId != null) {
				sb.append("&").append(HOME_ID).append("=").append(homeId);
			}
			// size (nb of events) - optional
			if (eventSize != null) {
				sb.append("&").append(SIZE).append("=").append(eventSize);
			}

			Response resp = post(sb.toString(), NETATMO_CLOUD_BASE_URL + GET_HOME_DATA);
			
			if (resp.code != HttpURLConnection.HTTP_OK) {
				Activator.logger.info("bad gethomedata (code=" + resp.code
						+ ", message=" + resp.message + ")", Server.class);
				if (resp.code == 403) {
					// invalid token
					currentToken.invalidateToken();
				}
				return homes;
			}
			
			// retrieve body
			JSONObject body = (JSONObject) resp.json.get(BODY);

			// retrieve homes
			JSONArray homesJson = (JSONArray) body.get(HOMES);
			if ((homesJson == null) || homesJson.isEmpty()) {
				Activator.logger.info("no homes !!!!", Server.class);
				return homes;
			}
			
			for (Object homeObject : homesJson) {
				// each object is a JSONObject
				JSONObject jsHome = (JSONObject) homeObject;

				// create home
				Home home = new Home((String) jsHome.get(Home.ID), (String) jsHome.get(Home.NAME));
				homes.add(home);

				// parse persons
				JSONArray personsJsonArray = (JSONArray) jsHome.get(Home.PERSONS);
				if ((personsJsonArray == null) || personsJsonArray.isEmpty()) {
					Activator.logger.info("no persons into " + home, Server.class);
				} else {
					for (Object personObject : personsJsonArray) {
						JSONObject jsPerson = (JSONObject) personObject;
						Person person = new Person((String) jsPerson.get(Person.ID),
								(String) jsPerson.get(Person.PSEUDO));
						person.setLastSeen((Long) jsPerson.get(Person.LAST_SEEN));
						person.setOutOfSight((Boolean) jsPerson.get(Person.OUT_OF_SIGHT));

						home.addOrUpdatePerson(person);
					}
				}

				// parse cameras
				JSONArray camerasJsonArray = (JSONArray) jsHome.get(Home.CAMERAS);
				if ((camerasJsonArray == null) || camerasJsonArray.isEmpty()) {
					Activator.logger.info("no cameras into " + home, Server.class);
				} else {
					for (Object cameraObject : camerasJsonArray) {
						JSONObject jsCam = (JSONObject) cameraObject;
						WelcomeCamera camera = new WelcomeCamera(
								(String) jsCam.get(WelcomeCamera.ID),
								(String) jsCam.get(WelcomeCamera.TYPE),
								(String) jsCam.get(WelcomeCamera.NAME));
						camera.setAlimOk(ON.equals((String) jsCam.get(WelcomeCamera.ALIM_STATUS)));
						camera.setIsOn(ON.equals((String) jsCam.get(WelcomeCamera.STATUS)));
						camera.setSdOk(ON.equals((String) jsCam.get(WelcomeCamera.SD_STATUS)));
						camera.setVpnUrl((String) jsCam.get(WelcomeCamera.VPN_URL));

						home.addOrUpdateCamera(camera);
					}
				}

				// parse events
				List<Event> events = new ArrayList<>();
				JSONArray eventsJsonArray = (JSONArray) jsHome.get(Home.EVENTS);
				if ((eventsJsonArray == null) || eventsJsonArray.isEmpty()) {
					Activator.logger.info("no events into " + home, Server.class);
				} else {
					for (Object eventObject : eventsJsonArray) {
						JSONObject jsEvent = (JSONObject) eventObject;
						Event event = new Event((String) jsEvent.get(Event.ID),
								(String) jsEvent.get(Event.TYPE),
								(Long) jsEvent.get(Event.TIME),
								(String) jsEvent.get(Event.CAMERA_ID),
								(String) jsEvent.get(Event.PERSON_ID));
						events.add(event);
					}
				}
				home.setEvents(events);
			}
		} catch (IOException e) {
			Activator.logger.warning("unable to open connection", Server.class, e);
		}
		return homes;
	}

	/**
	 * Authenticate request
	 * 
	 * @return a valid Token
	 */
	private Token authenticate() {
		try {
			StringBuffer sb = new StringBuffer();
			// client id
			sb.append(CLIENT_ID_NAME).append("=").append(clientId);
			// client secret
			sb.append("&").append(CLIENT_SECRET_NAME).append("=").append(clientSecret);
			// grant type
			sb.append("&").append(GRANT_TYPE_NAME).append("=").append(grantType);
			// username
			sb.append("&").append(USERNAME_NAME).append("=").append(username);
			// password
			sb.append("&").append(PASSWORD_NAME).append("=").append(password);
			// scope
			sb.append("&").append(SCOPE_NAME).append("=").append("read_camera+access_camera+read_station");

			Response resp = post(sb.toString(), NETATMO_CLOUD_BASE_URL + OAUTH_TOKEN_URL);
			if (resp.code == HttpURLConnection.HTTP_OK) {
				Token token = new Token((String) resp.json.get(ACCESS_TOKEN),
						(String) resp.json.get(REFRESH_TOKEN), (Long) resp.json.get(EXPIRE_IN));
				Activator.logger.info("received token=" + token.toString());
				return token;
			} else {
				Activator.logger.info("bad auth (code=" + resp.code + ", message=" + resp.message + ")",
						Server.class);
			}
		} catch (IOException e) {
			Activator.logger.warning("unable to open connection: " + e.getMessage(), Server.class);
		}
		return null;
	}

	private Token refreshToken(Token toBeRefreshed) {
		try {
			StringBuffer sb = new StringBuffer();
			// client id
			sb.append(CLIENT_ID_NAME).append("=").append(clientId);
			// client secret
			sb.append("&").append(CLIENT_SECRET_NAME).append("=").append(clientSecret);
			// grant type
			sb.append("&").append(GRANT_TYPE_NAME).append("=").append(GRANT_TYPE_REFRESH);
			// refreshToken
			sb.append("&").append(REFRESH_TOKEN).append("=").append(token.getRefreshToken());
			
			Response resp = post(sb.toString(), NETATMO_CLOUD_BASE_URL + OAUTH_TOKEN_URL);
			if (resp.code == HttpURLConnection.HTTP_OK) {
				Token token = new Token((String) resp.json.get(ACCESS_TOKEN),
						(String) resp.json.get(REFRESH_TOKEN), (Long) resp.json.get(EXPIRE_IN));
				Activator.logger.info("received token=" + token.toString());
				return token;
			} else {
				Activator.logger.info("bad auth (code=" + resp.code + ", message=" + resp.message + ")",
						Server.class);
			}
		} catch (IOException e) {
			Activator.logger.warning("unable to open connection", Server.class, e);
		}
		return null;
	}
	
	private static final Double getDoubleValue(final Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Long) {
			return ((Long) object).doubleValue();
		}
		// double
		return (object instanceof Double) ? (Double) object : null;
	}
	
	private Response post(String data, String url) throws IOException {
		HttpURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

		connection.getOutputStream().write(data.getBytes());
		connection.connect();

		Response resp = new Response();
		resp.code = connection.getResponseCode();
		if (resp.code == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			try {
				resp.json = (JSONObject) new JSONParser().parse(br);
			} catch (ParseException e) {
				throw new IOException(e);
			}
		} else {
			resp.message = connection.getResponseMessage();
		}
		connection.disconnect();
		return resp;
	}

}
