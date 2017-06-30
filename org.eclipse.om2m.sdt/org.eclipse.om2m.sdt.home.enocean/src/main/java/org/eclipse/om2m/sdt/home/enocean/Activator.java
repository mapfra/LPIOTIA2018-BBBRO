/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.enocean;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.HomeDomain;
import org.eclipse.om2m.sdt.home.devices.GenericDevice;
import org.eclipse.om2m.sdt.home.driver.Logger;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.enocean.EnOceanDevice;
import org.osgi.service.enocean.EnOceanEvent;
import org.osgi.service.enocean.EnOceanMessage;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Activator implements BundleActivator, EventHandler {
	
	static private final String PROTOCOL = "EnOcean";
	
	public interface EnOceanSDTDevice {
		public void register();
		public void unregister();
		public void handleEvent(final EnOceanMessage event);
		public Integer getChipId();
	}
	
	private Domain domain;
	private Map<Integer, EnOceanSDTDevice> sdtDevices;
	private boolean activated;
	private BundleContext context;
	private ServiceTracker enOceanDeviceTracker;
	private ServiceRegistration reg;
	static Logger logger;

	public Activator() {
    	logger = new Logger(PROTOCOL);
		logger.info("ctor");
		this.domain = new HomeDomain("EnOcean Domain");
		sdtDevices = new HashMap<Integer, EnOceanSDTDevice>();
	}

	@Override
	public void start(BundleContext bc) throws Exception {
		logger.info("Activation");
		this.context = bc;
		activated = true;

		ServiceTracker log = new ServiceTracker(context, LogService.class.getName(), null) {
            public Object addingService(ServiceReference ref) {
            	LogService logService = (LogService) context.getService(ref);
        		logger.setLogService(logService);
        		logger.info("LogService OK");
                return logService;
            }
            public void removedService(ServiceReference ref, Object service) {
        		logger.unsetLogService();
            }
		};
		log.open();
		
		initDevicesTracker();

		Dictionary<String, String[]> ht = new Hashtable<String, String[]>();
		ht.put(org.osgi.service.event.EventConstants.EVENT_TOPIC,
				new String[] { EnOceanEvent.TOPIC_MSG_RECEIVED, });
		reg = context.registerService(EventHandler.class.getName(), this, ht);

		activated = true;
		logger.info("Activated " + domain.prettyPrint());
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		logger.info("Deactivation " + sdtDevices);
		for (EnOceanSDTDevice dev : sdtDevices.values()) {
			dev.unregister();
		}
		sdtDevices.clear();
		reg.unregister();
		activated = false;
	}

	private void initDevicesTracker() {
		enOceanDeviceTracker = new ServiceTracker(context, EnOceanDevice.class.getName(), null) {
			public void removedService(ServiceReference ref, Object service) {
            	EnOceanDevice device = (EnOceanDevice) service;
        		logger.info("Removed EnOcean device " + device);
        		EnOceanSDTDevice dev = sdtDevices.remove(device.getChipId());
        		if (dev != null) {
        			dev.unregister();
        		}
            }
            public Object addingService(ServiceReference ref) {
        		if ((! activated) || Boolean.TRUE.equals(ref.getProperty("otb.proxied"))) {
					return null;
				}
        		EnOceanSDTDevice dev = createSDTDevice(ref);
        		if (dev != null) {
        			logger.info("created device: " + dev);
        			try {
        				Utils.setProperties(ref, (GenericDevice) dev);
        				((GenericDevice) dev).setProtocol(PROTOCOL);        				
        			} catch (Exception ignored) {
        			}
        			try {
						dev.register();
					} catch (Exception e) {
						logger.error("Error registering device", e);
						return null;
					}
        			sdtDevices.put(dev.getChipId(), dev);
        		}
        		return dev;
            }
        };
        enOceanDeviceTracker.open(true);
	}

	@Override
	public void handleEvent(final Event event) {
		logger.debug("Received EnOcean event: " + event);
		int chipId = Integer.parseInt(event.getProperty(EnOceanDevice.CHIP_ID).toString());
		logger.debug("EnOcean device: " + chipId);
		EnOceanSDTDevice device = sdtDevices.get(chipId);
		if (device == null) {
			logger.debug("Event for a non-registered device!");
		} else {
			logger.info("process evt " + event + " on " + chipId + " for device " + device);
			device.handleEvent((EnOceanMessage) event
					.getProperty(EnOceanEvent.PROPERTY_MESSAGE));
		}
	}

	private EnOceanSDTDevice createSDTDevice(ServiceReference<?> ref) {
		logger.info("Added EnOcean ref " + ref);
		for (String key : ref.getPropertyKeys()) {
			Object prop = ref.getProperty(key);
			if (prop instanceof String[]) {
				logger.info("prop " + key + " -> " + Arrays.asList((String[]) prop));
			} else {
				logger.info("prop " + key + " -> " + prop);
			}
		}
    	EnOceanDevice device = (EnOceanDevice) this.context.getService(ref);
		logger.info("Added EnOcean device " + device);
		String serial = (String) ref.getProperty(Utils.DEVICE_SERIAL);
		if (serial == null)
			serial = "" + device.getChipId();
//		eoDevices.put(device.getChipId(), device);
		logger.info("process device " + device.getChipId() + " profile: "
				+ String.format("0x%02X", device.getRorg()) + "/" + device.getRorg() + " : " 
				+ String.format("0x%02X", device.getFunc()) + "/" + device.getFunc() + " : " 
				+ String.format("0x%02X", device.getType()) + "/" + device.getType() + " "
				+ device.getManufacturer());
		switch (device.getRorg()) {
		case 0xF6: // RPS Telegram
			switch (device.getFunc()) {
			case 0x02: // Rocker Switch, 2 Rocker
				return createF602Detector(device, serial);
			case 0x03: // Rocker Switch, 4 Rocker
				break;
			case 0x04: // Position Switch, Home & Office Application
				break;
			case 0x05: // Detectors
				return createF605Detector(device, serial);
			case 0x10: // Mechanical Handle
				break;
			case 0x63:
				// ELTAKO smoke detector device
				return createSmokeDetector(device, serial);
			default:
				if (ref.getProperty(Utils.DEVICE_FRIENDLY_NAME).equals("F6-02-01")) {
					logger.info(Utils.DEVICE_FRIENDLY_NAME + " = F6-02-01. Try F602");
					return createF602Detector(device, serial);
				} else {
					logger.info("Unprocessed RPS (F6): " + device.getFunc() + ". Try F605 (Detector)");
					return createF602Detector(device, serial);
				}
			}
			break;

		case 0xD5: // 1BS Telegram
			logger.info("Unprocessed 1BS (D5): " + device.getFunc());
			break;
			
		case 0xA5: // 4BS Telegram
			switch (device.getFunc()) {
			case 0x02: // Temperature Sensors
				return createTemperatureSensor(device, domain, context);
			case 0x04: // Temperature & Humidity Sensors
				break;
			case 0x05: // Barometric Sensors
				break;
			case 0x06: // Light Sensors
				break;
			case 0x07: // Occupancy Sensors
				break;
			case 0x08: // Light, Temperature & Occupancy Sensors
				break;
			case 0x09: // Gas Sensors
				break;
			case 0x10: // Room Operating Panel
				break;
			case 0x11: // Controller Status
				break;
			case 0x12: // Automated Meter Reading (AMR)
				break;
			case 0x13: // Environmental  Applications
				break;
			case 0x14: // Multi-Func Sensors
				break;
			case 0x20: // HVAC Components
				break;
			case 0x30: // Digital Input
				break;
			case 0x37: // Energy Management
				break;
			case 0x38: // Central Commands
				break;
			case 0x3F: // Universal
				break;
			default:
				logger.info("Unprocessed 4BS (A5): " + device.getFunc());
				break;
			}
			
		case 0xD2: // VLD Telegram
			switch (device.getFunc()) {
			case 0x00: // Room Control Panel (RCP)
				break;
			case 0x01: // Electronic Switches & Dimmers with Energy Measurement & Local Control
				break;
			case 0x02: // Sensors for Temperature, Illumination, Occupancy & Smoke
				break;
			case 0x03: // Light, Switching + Blind Control
				break;
			case 0x04: // C02, Humidity, Temperature, Day/Night & Autonomy
				break;
			case 0x05: // Blinds Control for Position & Angle
				break;
			case 0x10: // Room COntrol Panels with Temperature & Fan Speed Control, Room Status Information
				break;
			case 0x20: // Fan Control
				break;
			case 0x30: // Floor Heating Controls & Automated Meter Reading
				break;
			case 0x31: // Automated Meter Reading Gateway
				break;
			case 0xA0: // Standard Valve
				return createD2AOStandardValve(device, serial);
			default:
				logger.info("Unprocessed VLD (D2): " + device.getFunc());
				break;
			}
		default:
			break;
		}
		logger.warning("Not implemented!");
		return null;
	}
	
	private EnOceanSDTDevice createD2AOStandardValve(EnOceanDevice device, String serial) {
		if (device.getType() != 0x01) {
			logger.error("Non valid EEP");
			return null;
		}
		try {
			return new EOWaterValve(device, domain, serial, context);
		} catch (Exception e) {
			logger.error("Error creating water valve", e);
			return null;
		}
	}

	private EnOceanSDTDevice createTemperatureSensor(EnOceanDevice device,
			Domain domain, BundleContext context) {
		return null;
	}

	private EnOceanSDTDevice createF605Detector(EnOceanDevice device, String serial) {
		try {
			return new EOFloodDetector(device, domain, context);
		} catch (Exception e) {
			logger.error("Error creating flood detector", e);
			return null;
		}
	}
	
	private EnOceanSDTDevice createSmokeDetector(EnOceanDevice device, String serial) {
		try {
			return new EOSmokeDetector(device, domain, context);
		} catch (Exception e) {
			logger.error("Error creating F6-02 detector", e);
			return null;
		}
	}

	private EnOceanSDTDevice createF602Detector(EnOceanDevice device, String serial) {
//		if (device.getType() != 0x01) {
//			HLog.error("Non valid EEP");
//			return null;
//		}
		try {
			return new EOLightBlindControl(device, domain, context);
		} catch (Exception e) {
			logger.error("Error creating F6-02 detector", e);
			return null;
		}
	}

}
