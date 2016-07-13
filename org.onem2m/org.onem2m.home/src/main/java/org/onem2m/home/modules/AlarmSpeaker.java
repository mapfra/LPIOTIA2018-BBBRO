package org.onem2m.home.modules;

import org.onem2m.home.types.AlertColourCode;
import org.onem2m.home.types.ModuleType;
import org.onem2m.home.types.Tone;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class AlarmSpeaker extends Module {

	private Tone tone;
	private BooleanDataPoint alarmStatus;
	private AlertColourCode light;

	public AlarmSpeaker(final String name, final Domain domain, BooleanDataPoint alarmStatus) {
		super(name, domain, ModuleType.alarmSpeaker.getDefinition());
		
		this.alarmStatus = alarmStatus;
		this.alarmStatus.setDoc("\"True\" indicates the alarm start while \"False\" indicates the alarm stop");
		addDataPoint(this.alarmStatus);
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
