package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class ExtendedCarbonDioxideSensor extends CarbonDioxideSensor {

	private IntegerDataPoint carbonDioxideValue;
	
	public ExtendedCarbonDioxideSensor(String name, Domain domain, BooleanDataPoint alarm, 
			IntegerDataPoint carbonDioxideValue) {
		super(name, domain, ModuleType.extendedCarbonDioxideSensor, alarm);
		
		this.carbonDioxideValue = carbonDioxideValue;
		this.carbonDioxideValue.setWritable(false);
		addDataPoint(carbonDioxideValue);
	}

	public ExtendedCarbonDioxideSensor(final String name, final Domain domain,
			Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get("alarm"),
				(IntegerDataPoint) dps.get("carbonDioxideValue"));
	}

	public int getCarbonDioxideValue() throws DataPointException, AccessException {
		return carbonDioxideValue.getValue();
	}
	
	public IntegerDataPoint getCarbonDioxide() {
		return carbonDioxideValue;
	}

}
