package org.onem2m.home.modules;

import java.util.List;
import java.util.Map;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.ArrayDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class PersonSensor extends Module {

	private final ArrayDataPoint<String> detectedPersons;

	public PersonSensor(final String name, final Domain domain,
			final ArrayDataPoint pDetectedPerson) {
		super(name, domain, ModuleType.personSensor.getDefinition());
		detectedPersons = pDetectedPerson;
		detectedPersons.setWritable(false);
		addDataPoint(detectedPersons);
	}

	public PersonSensor(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (ArrayDataPoint<String>) dps.get("detectedPersons"));
	}

	public List<String> getDetectedPersons() throws DataPointException, AccessException  {
		return detectedPersons.getValue();
	}

}
