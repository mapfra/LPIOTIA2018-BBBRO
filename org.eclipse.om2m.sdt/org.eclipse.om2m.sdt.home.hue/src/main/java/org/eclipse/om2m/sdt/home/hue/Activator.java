/*******************************************************************************
* Copyright (c) 2014-2018 Orange.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    BAREAU Cyrille <cyrille.bareau@orange.com>
*    BONNARDEL Gregory <gbonnardel.ext@orange.com>
*******************************************************************************/
package org.eclipse.om2m.sdt.home.hue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.hue.api.HueBridgeDevice;
import org.eclipse.om2m.hue.api.HueLightDevice;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.HomeDomain;
import org.eclipse.om2m.sdt.home.driver.Logger;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Activator {
	
	static private final String PROTOCOL = "Hue";
	
	private Domain domain;
	private List<ServiceReference> refs;
	private Map<String,HomeLight> lights;
	private boolean activated;
	private BundleContext context;
	
	static Logger logger;

	public Activator() {
		logger = new Logger(PROTOCOL);
		logger.info("ctor");
		this.domain = new HomeDomain("Philips Hue Domain");
		refs = new ArrayList<ServiceReference>();
		lights = new HashMap<String,HomeLight>();
	}

	public void activate(final ComponentContext componentContext) throws Exception {
		this.context = componentContext.getBundleContext();
		logger.info("Activating");

		for (ServiceReference ref : refs) {
			try {
				addLight(ref);
			} catch (Throwable t) {
				logger.error("", t);
			}
		}
		refs.clear();
		activated = true;
		logger.info("Activated " + domain.prettyPrint());
	}
	
	public void deactivate(final ComponentContext componentContext) throws Exception {
		activated = false;
		for (HomeLight light : lights.values()) {
			light.unregister();
		}
		lights.clear();
	}
	
	public void setHueLightDevice(final ServiceReference ref) {
		logger.info("Add Hue light " + ref);
		if (activated) {
			try {
				addLight(ref);
			} catch (Throwable t) {
				logger.error("", t);
			}
		} else {
			refs.add(ref);
		}
	}

	public void unsetHueLightDevice(final ServiceReference ref) {
		HueLightDevice hue = (HueLightDevice) this.context.getService(ref);
		logger.info("Removed Hue light " + hue);
		HomeLight light = lights.remove(hue.getId());
		if (light != null) {
			light.unregister();
			domain.removeDevice(light.getId());
		}
	}
	
	public void setHueBridgeDevice(final HueBridgeDevice bridge) {
		logger.info("Add Hue bridge " + bridge);
//		try {
//			Logger.info("bridge lights " + bridge.getLights());
//		} catch (Throwable e) {
//			Logger.warning("pb", e);
//		}
	}
	
	public void unsetHueBridgeDevice(final HueBridgeDevice bridge) {
		logger.info("Removed Hue bridge " + bridge);
	}
	
	public void setLog(final LogService logService) {
		logger.setLogService(logService);
	}
	
	public void unsetLog(final LogService logService) {
		logger.unsetLogService();
	}
	
	private void addLight(ServiceReference ref) {
		if (Boolean.TRUE.equals(ref.getProperty("otb.proxied"))) {
			logger.info("proxied Hue light, ignore");
			return;
		}
		HueLightDevice hue = (HueLightDevice) this.context.getService(ref);
		logger.info("Added Hue light " + hue);
		HomeLight light = new HomeLight(hue, domain);
		light.setProtocol(PROTOCOL);
		Utils.setProperties(ref, light);
		for (String prop : ref.getPropertyKeys()) {
			Object val = ref.getProperty(prop);
			logger.info("prop " + prop + " / " + val);
			if (prop.equals("UPnP.device.manufacturerURL"))
				try { light.setManufacturerDetailsLink(new URL(val.toString())); }
				catch (MalformedURLException e) {}
			else if (prop.equals("UPnP.presentationURL"))
				try { light.setPresentationURL(new URL(val.toString())); }
				catch (MalformedURLException e) {}
			else if (prop.equals("UPnP.device.modelURL"))
				try { light.setSupportURL(new URL(val.toString())); }
				catch (MalformedURLException e) {}
		}
		light.register(context);
		lights.put(hue.getId(), light);
    }

}
