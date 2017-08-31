/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.AlertColourCode;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.home.types.Tone;

public class AlarmSpeaker extends Module {

	private Tone tone;
	private BooleanDataPoint alarmStatus;
	private AlertColourCode light;

	public AlarmSpeaker(final String name, final Domain domain, BooleanDataPoint alarmStatus) {
		super(name, domain, ModuleType.alarmSpeaker);
		
		if ((alarmStatus == null) ||
				! alarmStatus.getShortDefinitionType().equals(DatapointType.alarmStatus.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong alarmStatus datapoint: " + alarmStatus);
		}
		this.alarmStatus = alarmStatus;
		this.alarmStatus.setDoc("\"True\" indicates the alarm start while \"False\" indicates the alarm stop");
		addDataPoint(this.alarmStatus);
	}

	public AlarmSpeaker(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.alarmStatus.getShortName()));
		Tone tone = (Tone) dps.get(DatapointType.tone.getShortName());
		if (tone != null)
			setTone(tone);
		AlertColourCode light = (AlertColourCode) dps.get(DatapointType.light.getShortName());
		if (light != null)
			setLight(light);
	}

	public boolean getAlarmStatus() throws DataPointException, AccessException {
		return alarmStatus.getValue();
	}

	public void setAlarmStatus(boolean b) throws DataPointException, AccessException {
		alarmStatus.setValue(b);
	}

	protected void setAlarmStatus(BooleanDataPoint dp) throws DataPointException {
		alarmStatus = dp;
	}

	public void setTone(Tone dp) {
		tone = dp;
		tone.setDoc("Representing the tones of the alarm");
		tone.setOptional(true);
		addDataPoint(tone);
	}

	public int getTone() throws DataPointException, AccessException {
		if (tone == null)
			throw new UnsupportedOperationException("Not implemented");
		return tone.getValue();
	}

	public void setTone(int v) throws DataPointException, AccessException {
		if (tone == null)
			throw new UnsupportedOperationException("Not implemented");
		tone.setValue(v);
	}

	public void setLight(AlertColourCode dp) {
		light = dp;
		light.setDoc("Representing the lighting mode of the alarm");
		light.setOptional(true);
		addDataPoint(light);
	}

	public int getLight() throws DataPointException, AccessException {
		if (light == null)
			throw new UnsupportedOperationException("Not implemented");
		return light.getValue();
	}

	public void setLight(int v) throws DataPointException, AccessException {
		if (light == null)
			throw new UnsupportedOperationException("Not implemented");
		light.setValue(v);
	}

}
