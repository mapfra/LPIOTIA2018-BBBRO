/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.orange.basedriver.lifx.impl.Discovery;

public class DiscoveryCloud extends Discovery {

	private static final String LIGHT_URL = "https://api.lifx.com/v1/lights/";
	private static final String ALL_LIGHTS = "https://api.lifx.com/v1/lights/all";
	private static URL ALL_LIGHTS_URL;
	static {
		try {
			ALL_LIGHTS_URL = new URL(ALL_LIGHTS);
		} catch (MalformedURLException e) {
		}
	}

	private static final String TOKEN = "c9d25974b9b977052623d25de0deed93ea432b2ee11b4ce6debafa11ab5f4560";

	private static String authenticationToken = TOKEN;

	private Map<String, LIFXDeviceCloud> devices = new HashMap<>();;

	private Timer timer;
	private TimerTask timerTask;

	public DiscoveryCloud() {
		timerTask = new TimerTask() {

			@Override
			public void run() {
				retrieveLIFXDevices();

			}
		};
	}

	public void retrieveLIFXDevices() {
		JSONArray jsonArray = retrieveLIFXDevice(null);

		if (jsonArray != null) {

			for (Iterator it = jsonArray.iterator(); it.hasNext();) {
				JSONObject jsonLifxDevice = (JSONObject) it.next();

				LIFXDeviceCloud lifxDeviceCloud = LIFXDeviceCloud.fromJson(jsonLifxDevice);
				addOrRemoveLIFXDevice(lifxDeviceCloud);
			}
		}
	}

	/**
	 * 
	 * @param url
	 *            if null, retrieve all devices
	 * @param lifxDevice
	 */
	public static JSONArray retrieveLIFXDevice(String url) {
		try {
			HttpURLConnection httpUrlConnection = null;
			if (url != null) {
				httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
			} else {
				httpUrlConnection = (HttpURLConnection) ALL_LIGHTS_URL.openConnection();
			}
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Authorization", "Bearer " + authenticationToken);
			httpUrlConnection.setDoOutput(false);
			httpUrlConnection.setDoInput(true);

			httpUrlConnection.connect();

			if (httpUrlConnection.getResponseCode() == 200) {
				// OK
				InputStream is = httpUrlConnection.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = null;
				String finalLine = "";
				while ((line = br.readLine()) != null) {
					finalLine += line;

				}
				JSONParser parser = new JSONParser();
				JSONArray jsonObject = (JSONArray) parser.parse(finalLine);

				return jsonObject;

			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void addOrRemoveLIFXDevice(LIFXDeviceCloud device) {
		if (device.isConnected()) {
			boolean deviceAdded = false;
			synchronized (devices) {
				if (!devices.containsKey(device.getId())) {
					devices.put(device.getId(), device);
					deviceAdded = true;
				}
			}
			if (deviceAdded) {
				notifyAllListeners_DeviceArrived(device);
			}
		} else {
			// device disconnected
			boolean deviceRemoved = false;
			// 2016 11 28 - BONNARDEL Gregory
			// do not remove LIFX device
//			synchronized (devices) {
//				if (devices.containsKey(device.getId())) {
//					devices.remove(device.getId());
//					deviceRemoved = true;
//				}
//			}

			if (deviceRemoved) {
				notifyAllListeners_DeviceLeft(device);
			}
		}
	}

	@Override
	public void startDiscoveryTask() {
		timer = new Timer();
		timer.schedule(timerTask, 0, 30000);
	}

	@Override
	public void stopDiscoveryTask() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		
	}

	public static void updateLightState(LIFXDeviceCloud lifxDevice) {
		JSONArray jsonArray = retrieveLIFXDevice(LIGHT_URL + lifxDevice.getId());
		if (jsonArray != null) {
			lifxDevice.updateLightState((JSONObject) jsonArray.get(0));
		}
	}

	public static void setLightPower(LIFXDeviceCloud lifxDevice, String power, Double hue, Double saturation,
			Long kelvin, Double brightness, Double duration) throws MalformedURLException, IOException {
		HttpURLConnection httpUrlConnection = null;
		httpUrlConnection = (HttpURLConnection) new URL(LIGHT_URL + lifxDevice.getId() + "/state").openConnection();
		httpUrlConnection.setRequestMethod("PUT");
		httpUrlConnection.setRequestProperty("Authorization", "Bearer " + authenticationToken);

		String data = "{";
		if (power != null) {
			data += "\"power\":\"" + power + "\",";
		}
		
		if (brightness != null) {
			data += "\"brightness\":" + brightness + ",";
		}
		if ((hue != null) || (saturation != null) || (kelvin != null)) {
			data += "\"color\":\"";
			if (hue != null) {
				data += "hue:" + hue + " ";
			}
			if (kelvin != null) {
				data += "kelvin:" + kelvin + " ";
			}
			if (saturation != null) {
				data += "saturation:" + saturation;
			}
			
			data += "\",";
		}
		if (duration != null) {
			data += "\"duration\":" + duration + ",";
		}
		if (data.endsWith(",")) {
			data = data.substring(0, data.length() - 1);
		}
		data += "}";
		

		httpUrlConnection.setDoOutput(true);
		httpUrlConnection.setDoInput(true);

		httpUrlConnection.getOutputStream().write(data.getBytes());
		httpUrlConnection.connect();

		if (httpUrlConnection.getResponseCode() == 207) {

		}
	}

	public List<LIFXDeviceCloud> getLIFXDeviceCloud() {
		List<LIFXDeviceCloud> toBeReturned = new ArrayList<>();
		synchronized (devices) {
			toBeReturned.addAll(devices.values());
		}

		return toBeReturned;
	}

}
