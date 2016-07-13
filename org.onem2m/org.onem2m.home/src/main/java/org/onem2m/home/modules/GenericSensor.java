package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class GenericSensor extends Module {
	
	private BooleanDataPoint value;
	
	public GenericSensor(final String name, final Domain domain, 
			BooleanDataPoint value) {
		this(name, domain, value, ModuleType.genericSensor.getDefinition());
	}
	
	public GenericSensor(final String name, final Domain domain, 
			BooleanDataPoint value, String containerDefinition) {
		super(name, domain, containerDefinition);

		this.value = value;
		this.value.setWritable(false);
		this.value.setDoc("True = Sensed, False = Not Sensed");
		addDataPoint(this.value);
	}

	public boolean getValue() throws DataPointException, AccessException {
		return value.getValue();
	}

}
