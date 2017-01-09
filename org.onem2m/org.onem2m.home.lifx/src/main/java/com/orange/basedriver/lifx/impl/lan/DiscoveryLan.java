/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl.lan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.orange.basedriver.lifx.LIFXDevice;
import com.orange.basedriver.lifx.impl.Discovery;
import com.orange.basedriver.lifx.impl.Logger;
import com.orange.basedriver.lifx.impl.lan.frame.LIFXGlobalFrame;
import com.orange.basedriver.lifx.lan.LIFXDiscoveryListener;

public class DiscoveryLan extends Discovery  implements LIFXDiscoveryListener {

	private Server server;

	private final Map<String, LIFXDeviceLan> lifxDevices = new HashMap<>();
	private Timer discoveryTimer;
	private Timer undiscoveryTimer;
	private TimerTask discoveryTimerTask;
	private TimerTask undiscoveryTimerTask;
	

	public DiscoveryLan() {
		server = Server.getInstance();
		server.addLIFXDiscoveryListener(this);
		
		discoveryTimerTask = new TimerTask() {
			
			@Override
			public void run() {
				// launch a discovery
				launchDiscovery();
			}
		};
		
		undiscoveryTimerTask = new TimerTask() {
			
			@Override
			public void run() {
				// clone lifxDevices
				Map<String, LIFXDeviceLan> map = new HashMap<>();
				synchronized (lifxDevices) {
					map.putAll(lifxDevices);
				}
				
				List<String> deviceKeysToBeRemoved = new ArrayList();
				
				// iterate over the cloned map
				for(String key : map.keySet()) {
					LIFXDeviceLan lifxDeviceLan = map.get(key);
					if (System.currentTimeMillis() - lifxDeviceLan.getLastDataFromDevice() > 120000) {
						
						// remove device from list 
						deviceKeysToBeRemoved.add(lifxDeviceLan.getMacAddress());
					}
				}
				

				for(String keyToRemove : deviceKeysToBeRemoved) {
					removeLIFXDevice(keyToRemove);
				}
				
				deviceKeysToBeRemoved.clear();
				deviceKeysToBeRemoved = null;
				
				
			}
		};
		
		
	}
	
	public void startDiscoveryTask() {
		discoveryTimer = new Timer();
		discoveryTimer.scheduleAtFixedRate(discoveryTimerTask, 0, 15000);
		
		undiscoveryTimer = new Timer();
		undiscoveryTimer.scheduleAtFixedRate(undiscoveryTimerTask, 0, 60000);
	}
	
	public void stopDiscoveryTask() {
		server.removeLIFXDiscoveryListener(this);
		
		discoveryTimer.cancel();
		discoveryTimer = null;
		
		undiscoveryTimer.cancel();
		undiscoveryTimer = null;
	}
	
	
	public void launchDiscovery() {
		Logger.getInstance().info(DiscoveryLan.class, "launchDiscovery()");
		LIFXGlobalFrame lifxGlobalFrame = new LIFXGlobalFrame();

		lifxGlobalFrame.getFrame().setPayloadSize(0);
		lifxGlobalFrame.getFrame().setTagged(true);
		lifxGlobalFrame.getFrame().setSource(4);
		lifxGlobalFrame.getFrameAddress().setTarget(new byte[6]);
		lifxGlobalFrame.getFrameAddress().setResRequired(true);

		lifxGlobalFrame.getProtocolHeader().setType(2);

		try {
			server.sendLIFXGlobalFrame(lifxGlobalFrame, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public LIFXDevice getLIFXDevice(String macAddress) {
		LIFXDevice device = null;
		synchronized (lifxDevices) {
			device = lifxDevices.get(macAddress);
		}
		return device;
	}

	public synchronized void addLIFXDevice(LIFXDeviceLan device) {
		synchronized (lifxDevices) {
			if (!lifxDevices.containsKey(device.getMacAddress())) {
				lifxDevices.put(device.getMacAddress(), device);
				notifyAllListeners_DeviceArrived(device);
			}
		}
	}
	
	public synchronized void removeLIFXDevice(String id) {
		Logger.getInstance().info(DiscoveryLan.class, "removeLIFXDevice(id=" + id + ")");
		synchronized (lifxDevices) {
			LIFXDeviceLan lifxDeviceLan = lifxDevices.remove(id);
			if (lifxDeviceLan != null) {
				notifyAllListeners_DeviceLeft(lifxDeviceLan);
			}
		}
	}

	public Map<String, LIFXDeviceLan> getLIFXDevices() {
		Map<String, LIFXDeviceLan> toBeReturned = new HashMap<>();
		synchronized (lifxDevices) {
			toBeReturned.putAll(lifxDevices);
		}
		return toBeReturned;
	}

	@Override
	public void notifyStateService(LIFXGlobalFrame frame) {
		String macAddress = frame.getFrameAddress().getTargetAsString();
		LIFXDeviceLan lifxDevice = (LIFXDeviceLan) getLIFXDevice(macAddress);
		if (lifxDevice == null) {
			lifxDevice = new LIFXDeviceLan(macAddress, frame.getFrameAddress().getTarget(),
					frame.getRemoteHost());
			addLIFXDevice(lifxDevice);
		} else {
			// receive a discovery packet from device ==> up
			lifxDevice.notifyDiscoveryPacket();
		}
		
	}

	
	private int getCurrentNumberOfDevices() {
		int numberOfDevices = 0;
		synchronized (lifxDevices) {
			numberOfDevices = lifxDevices.size();
		}
		return numberOfDevices;
	}
}
