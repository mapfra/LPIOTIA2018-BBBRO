package org.onem2m.home.modules;

import java.util.Map;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class Noise extends Module {

	private IntegerDataPoint noise;
	
	public Noise(String name, Domain domain, IntegerDataPoint noiseDataPoint) {
		super(name, domain, ModuleType.noise.getDefinition());
		this.noise = noiseDataPoint;
		this.noise.setWritable(false);
		addDataPoint(this.noise);
	}

	public Noise(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (IntegerDataPoint) dps.get("noise"));
	}

	public int getNoise() throws DataPointException, AccessException {
		return noise.getValue();
	}
	
}
