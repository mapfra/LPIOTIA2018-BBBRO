/*
Device : DeviceDoorAnnc



A door is a door.

Created: 2017-08-09 15:38:05
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = DeviceDoorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceDoorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceDoorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceDoorAnnc";
	public static final String SHORT_NAME = "devDrAnnc";
	
	public DeviceDoorFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceDoorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getBattery();
		getBatteryAnnc();
		getDoorStatus();
		getDoorStatusAnnc();
		getLock();
		getLockAnnc();
	}
	
	@XmlElement(name="batty", required=true, type=BatteryFlexContainerAnnc.class)
	private BatteryFlexContainer battery;
	
	
	public void setBattery(BatteryFlexContainer battery) {
		this.battery = battery;
		getFlexContainerOrContainerOrSubscription().add(battery);
	}
	
	public BatteryFlexContainer getBattery() {
		this.battery = (BatteryFlexContainer) getResourceByName(BatteryFlexContainer.SHORT_NAME);
		return battery;
	}
	
	@XmlElement(name="battyAnnc", required=true, type=BatteryFlexContainerAnnc.class)
	private BatteryFlexContainerAnnc batteryAnnc;
	
	
	public void setBattery(BatteryFlexContainerAnnc batteryAnnc) {
		this.batteryAnnc = batteryAnnc;
		getFlexContainerOrContainerOrSubscription().add(batteryAnnc);
	}
	
	public BatteryFlexContainerAnnc getBatteryAnnc() {
		this.batteryAnnc = (BatteryFlexContainerAnnc) getResourceByName(BatteryFlexContainerAnnc.SHORT_NAME);
		return batteryAnnc;
	}
	
	@XmlElement(name="dooSs", required=true, type=DoorStatusFlexContainerAnnc.class)
	private DoorStatusFlexContainer doorStatus;
	
	
	public void setDoorStatus(DoorStatusFlexContainer doorStatus) {
		this.doorStatus = doorStatus;
		getFlexContainerOrContainerOrSubscription().add(doorStatus);
	}
	
	public DoorStatusFlexContainer getDoorStatus() {
		this.doorStatus = (DoorStatusFlexContainer) getResourceByName(DoorStatusFlexContainer.SHORT_NAME);
		return doorStatus;
	}
	
	@XmlElement(name="dooSsAnnc", required=true, type=DoorStatusFlexContainerAnnc.class)
	private DoorStatusFlexContainerAnnc doorStatusAnnc;
	
	
	public void setDoorStatus(DoorStatusFlexContainerAnnc doorStatusAnnc) {
		this.doorStatusAnnc = doorStatusAnnc;
		getFlexContainerOrContainerOrSubscription().add(doorStatusAnnc);
	}
	
	public DoorStatusFlexContainerAnnc getDoorStatusAnnc() {
		this.doorStatusAnnc = (DoorStatusFlexContainerAnnc) getResourceByName(DoorStatusFlexContainerAnnc.SHORT_NAME);
		return doorStatusAnnc;
	}
	
	@XmlElement(name="lock", required=true, type=LockFlexContainerAnnc.class)
	private LockFlexContainer lock;
	
	
	public void setLock(LockFlexContainer lock) {
		this.lock = lock;
		getFlexContainerOrContainerOrSubscription().add(lock);
	}
	
	public LockFlexContainer getLock() {
		this.lock = (LockFlexContainer) getResourceByName(LockFlexContainer.SHORT_NAME);
		return lock;
	}
	
	@XmlElement(name="lockAnnc", required=true, type=LockFlexContainerAnnc.class)
	private LockFlexContainerAnnc lockAnnc;
	
	
	public void setLock(LockFlexContainerAnnc lockAnnc) {
		this.lockAnnc = lockAnnc;
		getFlexContainerOrContainerOrSubscription().add(lockAnnc);
	}
	
	public LockFlexContainerAnnc getLockAnnc() {
		this.lockAnnc = (LockFlexContainerAnnc) getResourceByName(LockFlexContainerAnnc.SHORT_NAME);
		return lockAnnc;
	}
	
}