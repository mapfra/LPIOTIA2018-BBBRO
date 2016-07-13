package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class AtmosphericPressureSensor extends Module {

	private FloatDataPoint atmosphericPressure;

	public AtmosphericPressureSensor(final String name, final Domain domain, FloatDataPoint atmosphericPressure) {
		super(name, domain, ModuleType.atmosphericPressureSensor.getDefinition());
		
		this.atmosphericPressure = atmosphericPressure;
		this.atmosphericPressure.setWritable(false);
		this.atmosphericPressure.getDataType().setUnitOfMeasure("Mbar");
		this.atmosphericPressure.setDoc("Current Atmospheric Pressure In Mbar");
		addDataPoint(this.atmosphericPressure);
	}

	public float getAtmosphericPressure() throws DataPointException, AccessException {
		return atmosphericPressure.getValue();
	}

}
