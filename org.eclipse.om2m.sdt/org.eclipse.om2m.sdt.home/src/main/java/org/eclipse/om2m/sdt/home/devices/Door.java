/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.Battery;
import org.eclipse.om2m.sdt.home.modules.DoorStatus;
import org.eclipse.om2m.sdt.home.modules.Lock;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class Door extends GenericDevice {
	
	private Battery battery;
	
	private DoorStatus doorStatus;
	
	private Lock lock;
	
	
	public Door(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceDoor, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof Battery)
			addModule((Battery)module);
		else if (module instanceof DoorStatus)
			addModule((DoorStatus)module);
		else if (module instanceof Lock)
			addModule((Lock)module);
		else 
			super.addModule(module);
	}

	public void addModule(Battery battery) {
		this.battery = battery;
		super.addModule(battery);
	}

	public void addModule(DoorStatus doorStatus) {
		this.doorStatus = doorStatus;
		super.addModule(doorStatus);
	}

	public void addModule(Lock lock) {
		this.lock = lock;
		super.addModule(lock);
	}

	public Battery getBattery() {
		return battery;
	}

	public DoorStatus getDoorStatus() {
		return doorStatus;
	}

	public Lock getLock() {
		return lock;
	}

}
