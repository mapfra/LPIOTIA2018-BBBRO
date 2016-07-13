package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class ColourSaturation extends Module {
	
	private IntegerDataPoint colourSaturation;

	public ColourSaturation(final String name, final Domain domain, IntegerDataPoint colourSaturation) {
		super(name, domain, ModuleType.colourSaturation.getDefinition());
		setExtends(domain.getName(), "ColourSaturation");
		this.colourSaturation = colourSaturation;
		addDataPoint(this.colourSaturation);
	}
	
	public int getColourSaturation() throws DataPointException, AccessException {
		return colourSaturation.getValue();
	}
	
	public void setColourSaturation(int value) throws DataPointException, AccessException {
		colourSaturation.setValue(value);
	}
	
}
