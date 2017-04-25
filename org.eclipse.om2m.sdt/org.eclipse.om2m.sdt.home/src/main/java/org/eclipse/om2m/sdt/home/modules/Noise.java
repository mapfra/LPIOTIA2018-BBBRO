package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Noise extends Module {

	private IntegerDataPoint noise;
	
	public Noise(String name, Domain domain, IntegerDataPoint noiseDataPoint) {
		super(name, domain, ModuleType.noise.getDefinition(),
				ModuleType.noise.getLongDefinitionName(), ModuleType.noise.getShortDefinitionName());
		this.noise = noiseDataPoint;
		this.noise.setWritable(false);
		this.noise.setLongDefinitionType("noise");
		this.noise.setShortDefinitionType("noise");
		addDataPoint(this.noise);
	}

	public Noise(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (IntegerDataPoint) dps.get("noise"));
	}

	public int getNoise() throws DataPointException, AccessException {
		return noise.getValue();
	}
	
}
