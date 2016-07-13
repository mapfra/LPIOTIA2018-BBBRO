package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class SmokeSensor extends AbstractAlarmSensor {
	
	private IntegerDataPoint detectedTime;
	
	public SmokeSensor(final String name, final Domain domain, BooleanDataPoint alarm) {
		super(name, domain, alarm, ModuleType.smokeSensor,
				"The detection of smoke.");
	}

	public void setDetectedTime(IntegerDataPoint dp) {
		detectedTime = dp;
		detectedTime.setOptional(true);
		detectedTime.setDoc("The time the smoke is detected.");
		addDataPoint(detectedTime);
	}
	
	public int getDetectedTime() throws DataPointException, AccessException {
		if (detectedTime == null)
			throw new DataPointException("Not implemented");
		return detectedTime.getValue();
	}

	public void setDetectedTime(int v) throws DataPointException, AccessException {
		if (detectedTime == null)
			throw new DataPointException("Not implemented");
		detectedTime.setValue(v);
	}

}
