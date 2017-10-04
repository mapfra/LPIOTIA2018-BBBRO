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
import org.eclipse.om2m.sdt.home.modules.AlarmSpeaker;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class WarningDevice extends GenericDevice {
	
	private AlarmSpeaker alarmSpeaker;
	private FaultDetection faultDetection;
	
	public WarningDevice(final String id, final String serial, final Domain domain) {
		super(id, serial, DeviceType.deviceWarningDevice, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof AlarmSpeaker)
			addModule((AlarmSpeaker)module);
		else if (module instanceof FaultDetection)
			addModule((FaultDetection)module);
		else 
			super.addModule(module);
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
