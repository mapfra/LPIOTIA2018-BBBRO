package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class Brightness extends Module {

	private IntegerDataPoint brightness;

	public Brightness(final String name, final Domain domain, IntegerDataPoint brightness) {
		super(name, domain, ModuleType.brightness.getDefinition());
		
		this.brightness = brightness;
		this.brightness.setDoc("Current sensed or set value for Brightness");
		addDataPoint(this.brightness);
	}

	public int getBrightness() throws DataPointException, AccessException {
		return brightness.getValue();
	}

	public void setBrightness(int v) throws DataPointException, AccessException {
		brightness.setValue(v);
	}
	
}
