/*******************************************************************************
 * Copyright (c) 2013, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *    BOLLE Sebastien <sebastien.bolle@orange.com>.
 *******************************************************************************/
package org.eclipse.om2m.android.dashboard.cse;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.eclipse.om2m.android.dashboard.cse.models.Container;
import org.eclipse.om2m.android.dashboard.cse.models.FlexContainerAnnounced;
import org.eclipse.om2m.android.dashboard.cse.models.OneM2MApplication;
import org.eclipse.om2m.android.dashboard.cse.models.Response;
import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;
import org.eclipse.om2m.android.dashboard.cse.models.SDTModule;
import org.eclipse.om2m.android.dashboard.cse.models.Uril;
import org.eclipse.om2m.android.dashboard.tools.SettingsManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;

import android.content.Context;
import android.util.Log;

public class OneM2MConnector {
	
	private static final String FILTER = "fu";
	private static final String RETURN_CONTENT = "rcn";
	private static final String LABEL = "lbl";
	private static final String TYPE = "ty";
	private static final String HTTP = "http://";
	private static final String APP_TYPE = "ResourceType/Application";
	private static final String DEV_TYPE = "object.type/device";

	private static final OneM2MConnector INSTANCE = new OneM2MConnector();
	static public List<String> errors = new ArrayList<String>();

	private Map<String, SDTDevice> currentDevices = new HashMap<String, SDTDevice>();
	
	private RestTemplate GETRestTemplate = null;
	private RestTemplate POSTRestTemplate = null;
	private HttpHeaders requestHeaders;
	private String inCseBaseUrl;
	private String inCseUrl;
	private String serverBaseUrl;
	private MappingJackson2HttpMessageConverter jacksonConverter;
	private Context context;

	public static final OneM2MConnector getInstance(Context context) {
		INSTANCE.context = context;
		INSTANCE.requestHeaders = new HttpHeaders();
		INSTANCE.requestHeaders.set("X-M2M-Origin", 
				SettingsManager.getInstance(context).getCSELogin() + ":" 
						+ SettingsManager.getInstance(context).getCSEPwd());
		INSTANCE.requestHeaders.set("Accept", "application/json");
		INSTANCE.requestHeaders.set("Content-Type", "application/json");
		String srv = SettingsManager.getInstance(context).getCSEHostname() 
				+ ":" + SettingsManager.getInstance(context).getCSEPort();
		INSTANCE.serverBaseUrl = srv.startsWith(HTTP) ? srv : HTTP + srv;
		INSTANCE.inCseBaseUrl = INSTANCE.serverBaseUrl + "/~" ;
		INSTANCE.inCseUrl = INSTANCE.inCseBaseUrl + "/" 
				+ SettingsManager.getInstance(context).getCSEId() 
				+ "/" + SettingsManager.getInstance(context).getCSEName();

		return INSTANCE;
	}

	private OneM2MConnector() {
		List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
//		supportedMediaTypes.add(new MediaType("application", "json", Charset.forName("UTF-8")));
		supportedMediaTypes.add(new MediaType("application", "json", Charset.forName("ISO-8859-1")));
		

		jacksonConverter = new MappingJackson2HttpMessageConverter();
		jacksonConverter.setSupportedMediaTypes(supportedMediaTypes);
		jacksonConverter.getObjectMapper().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, 
				false);
		
		GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
		gsonConverter.setSupportedMediaTypes(supportedMediaTypes);

		ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse resp) {
				try {
//					InputStream body = resp.getBody();
//					BufferedReader br = new BufferedReader(new InputStreamReader(body));
//					String line = null;
//					while((line = br.readLine()) != null) {
//						Log.d(OneM2MConnector.class.getName(), line);
//					}
					if (resp.getRawStatusCode() != HttpStatus.SC_OK) {
						Log.e(OneM2MConnector.class.getName(), "invalid response, expected 200 OK , found " + resp.getRawStatusCode());
						return true;
						
					}
				} catch (Exception e) {
					Log.e(OneM2MConnector.class.getName(), "hasError: " + e.getMessage(), e);
				}
				return false;
			}

			@Override
			public void handleError(ClientHttpResponse resp) {
				try {
					
					Log.e(OneM2MConnector.class.getName(), "handleError " + resp.getRawStatusCode() + " " 
							+ resp.getStatusText());
				} catch (Exception e) {
					Log.e(OneM2MConnector.class.getName(), "handleError " + e.getMessage());
				}
			}
		};

		// init rest object
		GETRestTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> getMessageConverters = new ArrayList<HttpMessageConverter<?>>();
//		getMessageConverters.add(gsonConverter);
		getMessageConverters.add(jacksonConverter);
//		getMessageConverters.add(new StringHttpMessageConverter());
//		getMessageConverters.add(new ByteArrayHttpMessageConverter());
//		getMessageConverters.add(new ResourceHttpMessageConverter());
		GETRestTemplate.setMessageConverters(getMessageConverters);
		GETRestTemplate.setErrorHandler(errorHandler);

		POSTRestTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> postMessageConverters = new ArrayList<HttpMessageConverter<?>>();
		postMessageConverters.add(new FormHttpMessageConverter());
		postMessageConverters.add(new StringHttpMessageConverter());
//		postMessageConverters.add(gsonConverter);
		postMessageConverters.add(jacksonConverter);
		POSTRestTemplate.setMessageConverters(postMessageConverters);
		POSTRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		POSTRestTemplate.setErrorHandler(errorHandler);
	}

	public List<OneM2MApplication> getApplications() {
		try {
			List<OneM2MApplication> applications = new ArrayList<OneM2MApplication>();
			Uril uril = discovery(APP_TYPE, 2);
			for (String appRi : uril.getUril()) {
				// get application
				OneM2MApplication application = getApplication(appRi);
				applications.add(application);
			}
			return applications;
		} catch (Exception e) {
			Log.e(OneM2MConnector.class.getName(), e.getMessage(), e);
			return new ArrayList<OneM2MApplication>();
		}
	}

	public OneM2MApplication getApplication(String appRi) {
		OneM2MApplication application = null;
		try {
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			String url = inCseBaseUrl + appRi;
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			builder.queryParam(RETURN_CONTENT, 4);
			Response response =  GETRestTemplate.exchange(builder.build().encode().toUri(), 
					HttpMethod.GET, requestEntity, Response.class).getBody();
			if (! response.getAny().isEmpty()) {
				String key = response.getAny().keySet().iterator().next();
				Log.d(getClass().getName(), "key=" + key);
				Object value = response.getAny().get(key);
				application = jacksonConverter.getObjectMapper().convertValue(value, 
						OneM2MApplication.class);
				
				if (application.getContainers() != null) {
					for (Container container :application.getContainers()) {
						if (container.getRn().equals("ICON")) {
							application.setIconUrl(getContent(container));
						} else if (container.getRn().equals("PRESENTATION_URL")) {
							application.setPresentationUrl(getContent(container));
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e(OneM2MConnector.class.getName(), e.getMessage(), e);
		}
		return application;
	}
	
	private final String getContent(final Container container) {
		String ret = container.getCin().get(0).getCon();
		return ret.startsWith(HTTP) ? ret : serverBaseUrl + ret;
	}

	public synchronized List<SDTDevice> getDevices() {
		try {
			// perform a discovery on SDT device FlexContainer 
			Uril uril = discovery(DEV_TYPE, 28);
			for (String u: uril.getUril()) {
				Log.i(getClass().getName(), u);
				
				// retrieve device if it is a new device
				if (!checkDevice(u)) {
					SDTDevice device = getDevice(u);
					if (device != null) {
						device.setMapRi(u);
						addCurrentDevice(u, device);
					}
				}
			}
				
			// perform discovery on announced SDT device FlexContainer
			Uril announcedUril = discovery(DEV_TYPE, 10028);
			for (String u: announcedUril.getUril()) {
				Log.i(getClass().getName(), "announced resource=" + u);
				if (!checkDevice(u)) {
					FlexContainerAnnounced fca = getAnnouncedDevice(u);
					if (fca != null) {
						SDTDevice device = getDevice(fca.getLnk());
						if (device != null) {
							device.setMapRi(u);
							addCurrentDevice(u, device);
						}
					}
				}
			}
			
			// remove old devices
			for (SDTDevice device : getCurrentDevices()) {
				String mapRi = device.getMapRi();
				if ((! uril.getUril().contains(mapRi)) 
						&& (! announcedUril.getUril().contains(mapRi))) {
					removeCurrentDevice(device);
				}
			}
		} catch (Exception e) {
			Log.e(OneM2MConnector.class.getName(), e.getMessage(), e);
		}
		return getCurrentDevices();
	}

	public Map<String, List<SDTDevice>> getDevicesByTypes() {
		Map<String, List<SDTDevice>> devicesByTypes = new HashMap<String, List<SDTDevice>>();
		try {
			for (SDTDevice sdtDevice : getDevices()) {
				String cnd = sdtDevice.getCnd();
				List<SDTDevice> sdtDevicesPerCndList = devicesByTypes.get(cnd);
				if (sdtDevicesPerCndList == null) {
					sdtDevicesPerCndList = new ArrayList<SDTDevice>();
					devicesByTypes.put(cnd, sdtDevicesPerCndList);
				}
				sdtDevicesPerCndList.add(sdtDevice);
			}
		} catch (Exception e) {
			Log.e(OneM2MConnector.class.getName(), e.getMessage(), e);
		}
		return devicesByTypes;
	}

	public boolean isConnectedWithInCse() {
		try {
			URL url = new URL(inCseUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setRequestMethod("GET");
			return httpUrlConnection.getResponseCode() != -1;
		} catch (Exception e) {
			return false;
		}
	}

	private Uril discovery(String label, Integer resourceType) {
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(inCseUrl);
		builder.queryParam(FILTER, 1);
		if (label != null) {
			builder.queryParam(LABEL, label);
		}
		if (resourceType != null) {
			builder.queryParam(TYPE, resourceType);
		}
		return GETRestTemplate.exchange(builder.build().encode().toUri(), 
				HttpMethod.GET, requestEntity, Uril.class).getBody();
	}

	public SDTDevice getDevice(String deviceRi) {
		SDTDevice sdtDevice = null;
		try {
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			String url = inCseBaseUrl + deviceRi;
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
			builder.queryParam(RETURN_CONTENT, 4);
			Response response =  GETRestTemplate.exchange(builder.build().encode().toUri(), 
					HttpMethod.GET, requestEntity, Response.class).getBody();
			if (response.getAny().isEmpty()) {
				return null;
			}

			String key = response.getAny().keySet().iterator().next();
			Log.d(getClass().getName(), "key=" + key);
			sdtDevice = jacksonConverter.getObjectMapper().convertValue(response.getAny().get(key), 
					SDTDevice.class);

			for (String keyOther : sdtDevice.getOthers().keySet()) {
				if (keyOther.startsWith("hd:")) {
					// module
					Object moduleValue = sdtDevice.getOthers().get(keyOther);
					SDTModule sdtModule = jacksonConverter.getObjectMapper()
							.convertValue(moduleValue, SDTModule.class);
					sdtModule.setShortname(keyOther);
					sdtDevice.addModule(sdtModule);
				}
			}
		} catch (Exception e) {
			Log.d(OneM2MConnector.class.getName(), "getDevice() failed : " + e.getMessage(), e);
//			Toast.makeText(context, "getDevice " + e, Toast.LENGTH_LONG).show();
		}	
		return sdtDevice;
	}
	
	private FlexContainerAnnounced getAnnouncedDevice(String announcedDeviceId) {
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		String url = inCseBaseUrl + announcedDeviceId;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		builder.queryParam(RETURN_CONTENT, 4);
		Response response =  GETRestTemplate.exchange(builder.build().encode().toUri(), 
				HttpMethod.GET, requestEntity, Response.class).getBody();
		if (response.getAny().isEmpty()) {
			return null;
		}
		String key = response.getAny().keySet().iterator().next();
		Log.d(getClass().getName(), "key=" + key);
		Object value = response.getAny().get(key);
		return jacksonConverter.getObjectMapper().convertValue(value, 
				FlexContainerAnnounced.class);
	}
	
	private void addCurrentDevice(String key, SDTDevice deviceToBeAdded) {
		synchronized (currentDevices) {
			currentDevices.put(key, deviceToBeAdded);
		}
	}
	
	private void removeCurrentDevice(SDTDevice deviceToBeRemoved) {
		synchronized (currentDevices) {
			currentDevices.values().remove(deviceToBeRemoved);
		}
	}
	
	private boolean checkDevice(String ri) {
		synchronized (currentDevices) {
			return currentDevices.containsKey(ri);
		}
	}
	
	private List<SDTDevice> getCurrentDevices() {
		List<SDTDevice> toBeReturned = new ArrayList<SDTDevice>();
		synchronized (currentDevices) {
			toBeReturned.addAll(currentDevices.values());
		}
		return toBeReturned;
	}
	
}
