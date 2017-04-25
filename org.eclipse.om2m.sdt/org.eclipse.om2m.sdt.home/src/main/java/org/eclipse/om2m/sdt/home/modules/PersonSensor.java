package org.eclipse.om2m.sdt.home.modules;

import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class PersonSensor extends Module {

	private final ArrayDataPoint<String> detectedPersons;

	public PersonSensor(final String name, final Domain domain,
			final ArrayDataPoint pDetectedPerson) {
		super(name, domain, ModuleType.personSensor.getDefinition(),
				ModuleType.personSensor.getLongDefinitionName(),
				ModuleType.personSensor.getShortDefinitionName());
		detectedPersons = pDetectedPerson;
		detectedPersons.setWritable(false);
		detectedPersons.setLongDefinitionType("detectedPersons");
		detectedPersons.setShortDefinitionType("dedPs");
		addDataPoint(detectedPersons);
	}

	public PersonSensor(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (ArrayDataPoint<String>) dps.get("detectedPersons"));
	}

	public List<String> getDetectedPersons() throws DataPointException, AccessException  {
		return detectedPersons.getValue();
	}

}
