/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.events.SDTEventListener;
import org.eclipse.om2m.sdt.rights.RightsManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
	
	static private final String STAR = "*";
	static private final int iDOM = 0;
	static private final int iDEV = 1;
	static private final int iDEV_ID = 2;
	static private final int iMOD = 3;
	static private final int iMOD_ID = 4;
	static private final int iDP = 5;

	private static Map<SDTEventListener, List<String[]>> listeners;
	private static Map<String, Domain> domains;
	private static RightsManager rightsManager;
	static {
		domains = new HashMap<String, Domain>();
		listeners = new HashMap<SDTEventListener, List<String[]>>();
	}

	private BundleContext context;

	public Activator() {
		Logger.info("ctor");
	}

	@Override
	public void start(BundleContext bc) throws Exception {
		Logger.info("Activation");
		this.context = bc;

		ServiceTracker rmTracker = new ServiceTracker(context, RightsManager.class.getName(), null) {
			public Object addingService(ServiceReference ref) {
				rightsManager = (RightsManager) context.getService(ref);
				Logger.info("Got Rights Manager " + rightsManager);
				return rightsManager;
			}
			public void removedService(ServiceReference ref, Object service) {
				rightsManager = null;
			}
		};
		rmTracker.open();

		ServiceTracker log = new ServiceTracker(context, LogService.class.getName(), null) {
			public Object addingService(ServiceReference ref) {
				LogService logService = (LogService) context.getService(ref);
				Logger.setLogService(logService);
				Logger.info("LogService OK");
				return logService;
			}
			public void removedService(ServiceReference ref, Object service) {
				Logger.unsetLogService();
			}
		};
		log.open();

		ServiceTracker listenersTracker = new ServiceTracker(context, SDTEventListener.class.getName(), null) {
			public Object addingService(ServiceReference ref) {
				SDTEventListener l = (SDTEventListener) context.getService(ref);
				String[] props = new String[] {
						getProp(ref.getProperty(SDTEventListener.DOMAINS)),
						getProp(ref.getProperty(SDTEventListener.DEVICES_DEFS)),
						getProp(ref.getProperty(SDTEventListener.DEVICES_IDS)),
						getProp(ref.getProperty(SDTEventListener.MODULES_DEFS)),
						getProp(ref.getProperty(SDTEventListener.MODULES_NAMES)),
						getProp(ref.getProperty(SDTEventListener.DATAPOINTS))
				};
				Logger.info("Adding listener " + l.getClass() + " for " + props[iDOM] + "/"
						+ props[iDEV] + "/" + props[iDEV_ID] + "/"
						+ props[iMOD] + "/" + props[iMOD_ID] + "/" + props[iDP]);
				List<String[]> lp = listeners.get(l);
				if (lp == null) {
					lp = new ArrayList<String[]>();
					listeners.put(l, lp);
				}
				lp.add(props);
				return l;
			}
			public void removedService(ServiceReference ref, Object service) {
				SDTEventListener l = (SDTEventListener) context.getService(ref);
				Logger.info("Remove listener " + l);
				listeners.remove(l);
			}
		};
		listenersTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Logger.info("Deactivation");
		listeners.clear();
	}

	public static Collection<SDTEventListener> getListeners(Module module, 
			DataPoint datapoint) {
		Logger.info("get listeners for " + module + " / " + datapoint + " in " + listeners);
		Collection<SDTEventListener> ret = new HashSet<SDTEventListener>();
		for (Map.Entry<SDTEventListener, List<String[]>> entry : listeners.entrySet()) {
			for (String[] props : entry.getValue()) {
				Collection<Domain> doms = new ArrayList<Domain>();
				Collection<Device> devices = new ArrayList<Device>();
				Collection<Module> modules = new ArrayList<Module>();
				Logger.info("listener props " + props[iDOM] + "/"
						+ props[iDEV] + "/" + props[iDEV_ID] + "/"
						+ props[iMOD] + "/" + props[iMOD_ID] + "/" + props[iDP]);

				Device owner = module.getOwner();
				if (props[iDOM].equals(STAR)) {
					doms.addAll(domains.values());
				} else {
					for (String d : props[iDOM].split(","))
						doms.add(domains.get(d.trim()));
				}
				for (Domain dom : doms) {
					if (props[iDEV].equals(STAR) && props[iDEV_ID].equals(STAR)) {
						devices.addAll(dom.getDevices());
					} else if (props[iDEV_ID].equals(STAR)) {
						findDevices(props[iDEV], dom, owner.getDefinition(), true, devices);
					} else if (props[iDEV].equals(STAR)) {
						findDevices(props[iDEV_ID], dom, owner.getId(), false, devices);
					} else {
						findDevices(props[iDEV], props[iDEV_ID], dom, owner, devices);
					}
				}
				Logger.debug("Associated devices " + devices);
				for (Device dev : devices) {
					if (props[iMOD].equals(STAR) && props[iMOD_ID].equals(STAR)) {
						modules.addAll(dev.getModules());
					} else if (props[iMOD_ID].equals(STAR)) {
						findModules(props[iMOD], dev, module.getDefinition(), true, modules);
					} else if (props[iMOD].equals(STAR)) {
						findModules(props[iMOD_ID], dev, module.getName(), false, modules);
					} else {
						findModules(props[iMOD], props[iMOD_ID], dev, module, modules);
					}
				}
				Logger.info("Associated modules " + modules);
				if ((props[iDP].equals(STAR) && ! modules.isEmpty())
						|| findDatapoints(props[iDP], modules, datapoint.getName())) {
					ret.add(entry.getKey());
					break;
				}
			}
		}
		return ret;
	}

	private static void findDevices(final String props, final Domain dom, final String key,
			final boolean def, Collection<Device> devices) {
		for (String d : props.split(",")) {
			String dt = d.trim();
			if (dt.equalsIgnoreCase(key)) {
				for (Device dev : dom.getDevices()) {
					if (dt.equalsIgnoreCase(def ? dev.getDefinition() : dev.getId())) {
						devices.add(dev);
					}
				}
			}
		}
	}

	private static void findDevices(final String defs, final String names, final Domain dom,
			final Device owner, Collection<Device> devices) {
		for (String d1 : defs.split(",")) {
			String dt1 = d1.trim();
			for (Device dev : dom.getDevices()) {
				if (dt1.equalsIgnoreCase(owner.getDefinition())
						&& dt1.equalsIgnoreCase(dev.getDefinition())) {
					for (String d2 : names.split(",")) {
						String dt2 = d2.trim();
						if (dt2.equalsIgnoreCase(owner.getId())
								&& dt2.equalsIgnoreCase(dev.getId())) {
							devices.add(dev);
						}
					}
				}
			}
		}
	}

	private static void findModules(final String props, final Device dev, final String key,
			final boolean def, Collection<Module> modules) {
		for (String d : props.split(",")) {
			String dt = d.trim();
			if (dt.equalsIgnoreCase(key)) {
				for (Module mod : dev.getModules()) {
					if (dt.equalsIgnoreCase(def ? mod.getDefinition() : mod.getName())) {
						modules.add(mod);
					}
				}
			}
		}
	}

	private static void findModules(final String defs, final String names, final Device dev,
			final Module module, Collection<Module> modules) {
		for (String d1 : defs.split(",")) {
			String dt1 = d1.trim();
			for (Module mod : dev.getModules()) {
				if (dt1.equalsIgnoreCase(module.getDefinition())
						&& dt1.equalsIgnoreCase(mod.getDefinition())) {
					for (String d2 : names.split(",")) {
						String dt2 = d2.trim();
						if (dt2.equalsIgnoreCase(module.getName())
								&& dt2.equalsIgnoreCase(mod.getName())) {
							modules.add(mod);
						}
					}
				}
			}
		}
	}

	private static boolean findDatapoints(final String names, 
			final Collection<Module> modules, final String dpName) {
		for (String d : names.split(",")) {
			String dt = d.trim();
			for (Module mod : modules) {
				if (dt.equalsIgnoreCase(dpName)) {
					for (DataPoint dp : mod.getDataPoints()) {
						if (dt.equalsIgnoreCase(dp.getName()))  {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	static public final boolean isGrantedReadAccess(final DataPoint dp) {
		return (rightsManager == null) ? true : rightsManager.isGrantedReadAccess(dp);
	}

	static public final boolean isGrantedWriteAccess(final DataPoint dp) {
		return (rightsManager == null) ? true : rightsManager.isGrantedWriteAccess(dp);
	}

	static public final boolean isGrantedEventAccess(final SDTEventListener l,
			final DataPoint dp) {
		return (rightsManager == null) ? true : rightsManager.isGrantedEventAccess(l, dp);
	}

	static public final boolean isGrantedAccess(final Action action) {
		return (rightsManager == null) ? true : rightsManager.isGrantedAccess(action);
	}

	public static final void addDomain(final Domain domain) {
		domains.put(domain.getName(), domain);
	}
	
	static private final String getProp(final Object o) {
		if (o == null)
			return STAR;
		String s = o.toString().trim();
		return (s.isEmpty() || s.equals(STAR)) ? STAR : s;
	}

}
