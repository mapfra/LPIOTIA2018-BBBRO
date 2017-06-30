package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class ExtendedCarbonDioxideSensor extends CarbonDioxideSensor {

	private IntegerDataPoint carbonDioxideValue;
	
	public ExtendedCarbonDioxideSensor(String name, Domain domain, BooleanDataPoint alarm, 
			IntegerDataPoint carbonDioxideValue) {
		super(name, domain, ModuleType.extendedCarbonDioxideSensor, alarm);
		
		if ((carbonDioxideValue == null) ||
				! carbonDioxideValue.getShortDefinitionType().equals(DatapointType.carbonDioxideValue.getShortName())) {
			domain.removeDevice(name);
			throw new IllegalArgumentException("Wrong carbonDioxideValue datapoint: " + carbonDioxideValue);
		}
		this.carbonDioxideValue = carbonDioxideValue;
		this.carbonDioxideValue.setWritable(false);
		addDataPoint(carbonDioxideValue);
	}

	public ExtendedCarbonDioxideSensor(final String name, final Domain domain,
			Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get(DatapointType.alarm.getShortName()),
				(IntegerDataPoint) dps.get(DatapointType.carbonDioxideValue.getShortName()));
	}

	public int getCarbonDioxideValue() throws DataPointException, AccessException {
		return carbonDioxideValue.getValue();
	}
	
	public IntegerDataPoint getCarbonDioxide() {
		return carbonDioxideValue;
	}

}
