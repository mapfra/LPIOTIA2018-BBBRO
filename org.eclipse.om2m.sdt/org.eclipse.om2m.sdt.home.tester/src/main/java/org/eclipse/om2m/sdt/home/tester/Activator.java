/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.tester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.AbstractDateDataPoint;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;
import org.eclipse.om2m.sdt.datapoints.ValuedDataPoint;
import org.eclipse.om2m.sdt.events.SDTEventListener;
import org.eclipse.om2m.sdt.events.SDTNotification;
import org.eclipse.om2m.sdt.home.devices.Light;
import org.eclipse.om2m.sdt.home.devices.SmartElectricMeter;
import org.eclipse.om2m.sdt.home.devices.WaterValve;
import org.eclipse.om2m.sdt.home.modules.AlarmSpeaker;
import org.eclipse.om2m.sdt.home.modules.AudioVolume;
import org.eclipse.om2m.sdt.home.types.LiquidLevel;
import org.eclipse.om2m.sdt.types.Array;
import org.eclipse.om2m.sdt.types.DataType.TypeChoice;
import org.eclipse.om2m.sdt.types.SimpleType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Activator implements SDTEventListener {

	private List<Device> devices;
	private Map<Device, ServiceRegistration> listeners;
	private List<Module> modules;
	private List<Light> lights;
	private boolean activated;
	private BundleContext context;
	private Thread notifThread;
	private Queue<SDTNotification> incomingNotifs;
	private ThreadGroup group;

	public Activator() {
		Logger.info("ctor");
		devices = new ArrayList<Device>();
		listeners = new HashMap<Device, ServiceRegistration>();
		modules = new ArrayList<Module>();
		lights = new ArrayList<Light>();
		incomingNotifs = new LinkedList<SDTNotification>();
		
		setAuthenticationThreadGroup(null);
	}

	public void activate(ComponentContext ctxt) {
		Logger.info("Activation");
		this.context = ctxt.getBundleContext();
		activated = true;

//		initDevicesTracker();
//		initModulesTracker();
		Logger.info("devices: " + devices);
		Logger.info("modules: " + modules);
		
		for (Device device : devices) {
			Dictionary<String, String> props = new Hashtable();
			props.put(SDTEventListener.DEVICES_DEFS, device.getDefinition());
			listeners.put(device, context.registerService(SDTEventListener.class.getName(),
					this, props));
		}
		activated = true;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (activated) {
					try {
						Thread.sleep(10000);
						Logger.info("check lights " + lights);
						testLights();
					} catch (Exception e) {
						Logger.warning("Error", e);
					}
					try {
						Thread.sleep(10000);
						Logger.info("check devices " + devices);
						testDevices();
					} catch (Exception e) {
						Logger.warning("Error", e);
					}
				}
			}

		}).start();
	}

	public void deactivate(ComponentContext ctxt) {
		Logger.info("Deactivation");
		devices.clear();
		modules.clear();
		lights.clear();
		for (ServiceRegistration reg : listeners.values())
			reg.unregister();
		listeners.clear();
		activated = false;
	}

	boolean up = false;

	private void testLights() throws Exception {
		for (Light light : lights) {
			Logger.info("test light " + light);//.prettyPrint());
			Logger.info("fault status: " + light.getFaultDetection().getStatus());
			Logger.info("powered: " + light.getBinarySwitch().getPowerState());
			try {
				light.getBinarySwitch().toggle();
			} catch (Exception e) {
				Logger.warning("Error", e);
			}
			boolean power = light.getBinarySwitch().getPowerState();
			Logger.info("powered: " + power);
			if (power) {
				light.getColour().setRed((int) Math.random() * 255);
				Thread.sleep(2000);
				light.getColour().setGreen((int) Math.random() * 255);
				Thread.sleep(2000);
				light.getColour().setBlue((int) Math.random() * 255);
				Thread.sleep(2000);
				light.getColourSaturation().setColourSat((int) Math.random() * 100);
				List<String> modes = light.getRunMode().getSupportedModes();
				if (! modes.isEmpty()) {
					String mode = modes.get((int) (Math.random() * modes.size()));
					Logger.info("set run mode: " + mode + " from supported " + modes);
					light.getRunMode().setOperationMode(mode);
				}
//				List<Integer> states = light.getRunState().getJobStates();
//				if (! states.isEmpty()) {
//					Integer mode = states.get((int) (Math.random() * states.size()));
//					Logger.info("set run state: " + mode + " from supported " + states);
//					light.getRunState().setJobState(mode);
//				}
			}
		}
		Thread.sleep(1000);
		for (Light light : lights) {
			Logger.info("light color: r=" + light.getColour().getRed() + ", g=" + light.getColour().getGreen() + ", b="
					+ light.getColour().getBlue());
			Logger.info("light color saturation: " + light.getColourSaturation().getColourSat());
			Logger.info("light states: " + light.getRunMode().getSupportedModes());
		}
		Thread.sleep(1000);
		for (Light light : lights) {
			try {
				for (Module mod : light.getModules()) {
					if (mod instanceof AudioVolume) {
						AudioVolume vol = (AudioVolume) mod;
						if (up)
							vol.upVolume();
						else
							vol.downVolume();
					}
				}
			} catch (Exception e) {
				Logger.warning("", e);
			}
			up = !up;
		}
	}

	private void testDevices() throws Exception {
		for (Device dev : devices) {
			Logger.info("test device " + dev);//.prettyPrint());
			if (dev instanceof WaterValve) {
				testValve((WaterValve) dev);
			}
			for (Module mod : dev.getModules()) {
				try {
					Logger.info("test module " + mod + " on device " + dev);
					Logger.info("module properties: " + mod.getProperties());
					for (DataPoint dp : mod.getDataPoints()) {
						if (dp.isReadable()) {
							Logger.info("read " + dp.getName());
							try {
								Logger.info("-> " + ((ValuedDataPoint<?>) dp).getValue());
							} catch (Exception e) {
								Logger.warning("", e);
							}
						}
						if (dp.isWritable()) {
							Logger.info("writable " + dp.getName());
							try {
								TypeChoice tc = dp.getDataType().getTypeChoice();
								Logger.info("writable " + dp.getName() + " " + tc);
								if (tc instanceof SimpleType) {
									Class<?> clazz = ((SimpleType) tc).getType().getClazz();
									if (clazz.equals(Integer.class)) {
										int val = (int) (Math.random() * 100);
										if (dp instanceof EnumDataPoint<?>) {
											List<Integer> values = ((EnumDataPoint<Integer>)dp).getValidValues();
											Logger.info("possible values " + values);
											val = values.get((int) (Math.random() * values.size()));
										}
										Logger.info("write int value " + val);
										((ValuedDataPoint<Integer>) dp).setValue(val);
									}
									else if (clazz.equals(Boolean.class)) {
										boolean val = Math.random() < 0.5;
										Logger.info("write bool value " + val);
										((ValuedDataPoint<Boolean>) dp).setValue(val);
									}
									else if (clazz.equals(String.class)) {
										String val = "test " + (int) (Math.random() * 100);
										if (dp instanceof EnumDataPoint<?>) {
											List<String> values = ((EnumDataPoint<String>)dp).getValidValues();
											Logger.info("possible values " + values);
											val = values.get((int) (Math.random() * values.size()));
										}
										Logger.info("write string value " + val);
										((ValuedDataPoint<String>) dp).setValue(val);
									}
									else if (clazz.equals(Date.class)) {
										Date val = new Date();
										Logger.info("write date value " + val);
										((AbstractDateDataPoint) dp).setValue(val);
									}
									else if (clazz.equals(Byte.class)) {
										byte val = (byte) (Math.random() * 100);
										Logger.info("write byte value " + val);
										((ValuedDataPoint<Byte>) dp).setValue(val);
									}
									else if (clazz.equals(Float.class)) {
										float val = (float) (Math.random() * 100);
										Logger.info("write float value " + val);
										((ValuedDataPoint<Float>) dp).setValue(val);
									} else {
										Logger.info("Untested " + clazz);
									}
								} else if (tc instanceof Array<?>) {
									((ArrayDataPoint<String>)dp).setValue(Arrays.asList("test1", "test2", "test3"));
								} else if (tc instanceof Array<?>) {
									((ArrayDataPoint<String>)dp).setValue(Arrays.asList("test1", "test2", "test3"));
								}
								
							} catch (Exception e) {
								Logger.warning("", e);
							}
						}
					}
					for (Action act : mod.getActions()) {
						Logger.info("action " + act);
					}
					if (mod instanceof AlarmSpeaker) {
						testSiren((AlarmSpeaker) mod);
					}
				} catch (Exception e) {
					Logger.warning("Error test " + mod + " of " + dev, e);
				}
			}
		}
	}

	private int sirenTests = 2;

	private void testSiren(AlarmSpeaker siren) throws Exception {
		if (sirenTests == 0)
			return;
		// List<String> tones = siren.getTone();
		// String tone = tones.get(1 + (int) (Math.random() * (tones.size() -
		// 1)));
		// Logger.info("set tone " + tone);
		// siren.setTone(tone);
		// try {
		// Logger.info("get maxDuration: ");
		// Logger.info(" -> " + siren.getMaxDuration());
		// } catch (Exception e) {
		// Logger.warning("", e);
		// }
		try {
			siren.setAlarmStatus(true);
			Logger.info("test on: OK");
		} catch (Exception e) {
			Logger.warning("", e);
		}
		Thread.sleep(2000);
		try {
			siren.setAlarmStatus(false);
			Logger.info("test off: OK");
		} catch (Exception e) {
			Logger.warning("", e);
		}
		sirenTests -= 1;
	}

	private int valveTests = 2;

	private void testValve(WaterValve valve) {
		if (valveTests == 0)
			return;
		try {
			int on = LiquidLevel.zero;
			try {
				on = valve.getWaterLevel().getLiquidLevel();
				Logger.info("test valve: " + on);
			} catch (Exception e) {
				Logger.warning("", e);
			}
			on = (on == LiquidLevel.zero) ? LiquidLevel.maximum
					: LiquidLevel.zero;
			valve.getWaterLevel().setLiquidLevel(on);
			Logger.info("test valve: OK");
		} catch (Exception e) {
			Logger.warning("", e);
		}
		valveTests -= 1;
	}

//	private void initDevicesTracker() {
//		ServiceTracker st = new ServiceTracker(this.context, Device.class.getName(), null) {
//			public Object addingService(ServiceReference ref) {
//				Logger.info("add Device ref " + ref);
//				Device device = (Device) this.context.getService(ref);
//				setDevice(device);
//				return device;
//			}
//
//			public void removedService(ServiceReference ref, Object service) {
//				unsetDevice((Device) this.context.getService(ref));
//			}
//		};
//		st.open();
//	}
//
//	private void initModulesTracker() {
//		ServiceTracker st = new ServiceTracker(this.context, Module.class.getName(), null) {
//			public Object addingService(ServiceReference ref) {
//				Logger.info("add Module ref " + ref);
//				Module mod = (Module) this.context.getService(ref);
//				setModule(mod);
//				return mod;
//			}
//
//			public void removedService(ServiceReference ref, Object service) {
//				unsetModule((Module) this.context.getService(ref));
//			}
//		};
//		st.open();
//	}

	public void setDevice(Device device) {
		try {
			Logger.info("Added SDT device " + device);
			device.prettyPrint();
			if (device instanceof Light) {
				synchronized (lights) {
					lights.add((Light) device);
				}
			} else {
				synchronized (devices) {
					devices.add(device);
					if (activated) {
						Dictionary<String, String> props = new Hashtable();
						props.put(SDTEventListener.DEVICES_IDS, device.getId());
						listeners.put(device, 
							context.registerService(SDTEventListener.class.getName(), this, props));
					}
				}
			}
		} catch (Exception e) {
			Logger.warning("Error adding device", e);
		}
	}

	public void unsetDevice(Device device) {
		ServiceRegistration reg = listeners.remove(device);
		if (reg != null) {
			reg.unregister();
		}
		if (device instanceof Light) {
			synchronized (lights) {
				lights.remove((Light) device);
			}
		} else {
			synchronized (devices) {
				devices.remove(device);
			}
		}
		Logger.info("Removed SDT device " + device);
	}

	public void setModule(Module mod) {
		synchronized (modules) {
			modules.add(mod);
		}
		Logger.info("Added SDT module " + mod);
//		mod.prettyPrint();
	}

	public void unsetModule(Module mod) {
		synchronized (modules) {
			modules.remove(mod);
		}
		Logger.info("Removed SDT module " + mod);
	}
	
	public void setLight(Light light) {
		Logger.info("got light: " + light);
		lights.add(light);
	}
	
	public void unsetLight(Light light) {
		Logger.info("remove light: " + light);
		lights.remove(light);
	}
	
	public void setSiren(AlarmSpeaker siren) {
		try {
			Logger.info("got siren: " + siren);
			testSiren(siren);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void unsetSiren(AlarmSpeaker siren) {
		Logger.info("remove siren: " + siren);
	}

	@Override
	public void handleNotification(SDTNotification notif) {
		Logger.info("Received notif " + notif);
		synchronized (incomingNotifs) {
			incomingNotifs.add(notif);
			incomingNotifs.notify();
		}
	}

	@Override
	public void setAuthenticationThreadGroup(ThreadGroup group) {
		this.group = group;
		notifThread = new Thread(this.group, new Runnable() {
			@Override
			public void run() {
				while (true) {
					SDTNotification notif = null;
					synchronized (incomingNotifs) {
						if (incomingNotifs.isEmpty()) {
							try {
								incomingNotifs.wait();
							} catch (InterruptedException e) {
							}
						}
						notif = incomingNotifs.poll();
					}
					if (notif != null) {
						Logger.info("Process notif " + notif);
						if (notif.getDevice() instanceof SmartElectricMeter) {
							try {
								Logger.info("power: " + notif.getValue() + " / " +
									((SmartElectricMeter)notif.getDevice()).getEnergyConsumption().getPower());
							} catch (Exception e) {
								Logger.warning("", e);
							}
						}
					}
				}
			}
		});
		notifThread.start();
	}
	
	public void setLog(LogService log) {
		Logger.setLogService(log);
	}
	
	public void unsetLog(LogService log) {
		Logger.unsetLogService();
	}
	
}
