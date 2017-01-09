/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl;

import java.util.ArrayList;
import java.util.List;

import com.orange.basedriver.lifx.LIFXDevice;
import com.orange.basedriver.lifx.LIFXDiscoveredDeviceListener;

public abstract class Discovery {

	protected final List<LIFXDiscoveredDeviceListener> listeners;

	public Discovery() {
		listeners = new ArrayList<>();
	}
	
	public abstract void startDiscoveryTask();
	public abstract void stopDiscoveryTask(); 

	public void addDiscoveredDeviceListener(LIFXDiscoveredDeviceListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeDiscoveredDeviceListener(LIFXDiscoveredDeviceListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	public void notifyAllListeners_DeviceArrived(LIFXDevice device) {
		Logger.getInstance().info(Discovery.class, "notifyAllListeners_DeviceArrived(device= " + device.toString() + ")");
		
		// notify
		List<LIFXDiscoveredDeviceListener> toBeNotified = new ArrayList<>();
		synchronized (listeners) {
			toBeNotified.addAll(listeners);
		}
		for (LIFXDiscoveredDeviceListener listener : toBeNotified) {
			listener.notifyDeviceArrived(device);
		}
	}
	
	public void notifyAllListeners_DeviceLeft(LIFXDevice device) {
		Logger.getInstance().info(Discovery.class, "notifyAllListeners_DeviceLeft(device= " + device.toString() + ")");
		
		// notify
		List<LIFXDiscoveredDeviceListener> toBeNotified = new ArrayList<>();
		synchronized (listeners) {
			toBeNotified.addAll(listeners);
		}
		for (LIFXDiscoveredDeviceListener listener : toBeNotified) {
			listener.notifyDeviceLeft(device);
		}
	}

}