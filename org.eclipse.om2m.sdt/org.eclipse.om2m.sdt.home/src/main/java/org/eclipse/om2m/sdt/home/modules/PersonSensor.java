package org.eclipse.om2m.sdt.home.modules;

import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

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
