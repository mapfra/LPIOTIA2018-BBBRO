package org.onem2m.home.devices;

import org.onem2m.home.modules.AlarmSpeaker;
import org.onem2m.home.modules.FaultDetection;
import org.onem2m.home.types.DeviceType;
import org.onem2m.sdt.Domain;

public class WarningDevice extends GenericDevice {
	
	private AlarmSpeaker alarmSpeaker;
	private FaultDetection faultDetection;
	
	public WarningDevice(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceWarningDevice, domain);
	}

	public void addModule(AlarmSpeaker alarmSpeaker) {
		this.alarmSpeaker = alarmSpeaker;
		super.addModule(alarmSpeaker);
	}

	public void addModule(FaultDetection faultDetection) {
		this.faultDetection = faultDetection;
		super.addModule(faultDetection);
	}

	public AlarmSpeaker getAlarmSpeaker() {
		return alarmSpeaker;
	}

	public FaultDetection getFaultDetection() {
		return faultDetection;
	}

}
