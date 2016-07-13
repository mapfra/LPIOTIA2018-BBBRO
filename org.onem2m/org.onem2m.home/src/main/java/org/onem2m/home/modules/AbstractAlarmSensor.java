package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class AbstractAlarmSensor extends Module {
	
	private BooleanDataPoint alarm;
	
	public AbstractAlarmSensor(final String name, final Domain domain, BooleanDataPoint alarm) {
		this(name, domain, alarm, ModuleType.abstractAlarmSensor);
	}
	
	public AbstractAlarmSensor(final String name, final Domain domain, 
			BooleanDataPoint alarm, ModuleType type) {
		this(name, domain, alarm, type, "True = Sensed, False = Not Sensed");
	}
	
	public AbstractAlarmSensor(final String name, final Domain domain, 
			BooleanDataPoint alarm, ModuleType type, String doc) {
		super(name, domain, type.getDefinition());

		this.alarm = alarm;
		this.alarm.setWritable(false);
		this.alarm.setDoc(doc);
		addDataPoint(this.alarm);
	}

	public boolean getAlarm() throws DataPointException, AccessException {
		return alarm.getValue();
	}

}
