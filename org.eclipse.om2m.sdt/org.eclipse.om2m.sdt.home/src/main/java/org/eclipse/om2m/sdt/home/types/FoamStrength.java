package org.eclipse.om2m.sdt.home.types;

import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.datapoints.ClonedEnum;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public class FoamStrength extends ClonedEnum {

	static public final int zero = 1;
	static public final int medium = 2;
	static public final int maximum = 3;
	
	static private List<Integer> values = Arrays.asList(
			zero, medium, maximum
	);

	public FoamStrength(EnumDataPoint<Integer> dp) {
		this(DatapointType.foamingStrength, dp);
	}
	
	public FoamStrength(Identifiers name, EnumDataPoint<Integer> dp) {
		super(name, HomeDataType.FoamStrength, dp);
		setValidValues(values);	
	}
	
}
