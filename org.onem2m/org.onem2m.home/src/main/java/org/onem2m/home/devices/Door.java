package org.onem2m.home.devices;

import org.onem2m.home.modules.DoorStatus;
import org.onem2m.home.modules.FaultDetection;
import org.onem2m.home.modules.Lock;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class Door extends GenericDevice {
	
	private FaultDetection faultDetection;
	
	private DoorStatus doorStatus;
	
	private Lock lock;
	
	
	public Door(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceDoor, domain);
	}

	public void addModule(FaultDetection faultDetection) {
		this.faultDetection = faultDetection;
		super.addModule(faultDetection);
	}

	public void addModule(DoorStatus doorStatus) {
		this.doorStatus = doorStatus;
		super.addModule(doorStatus);
	}

	public void addModule(Lock lock) {
		this.lock = lock;
		super.addModule(lock);
	}

	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

	public DoorStatus getDoorStatus() {
		return doorStatus;
	}

	public Lock getLock() {
		return lock;
	}

}
