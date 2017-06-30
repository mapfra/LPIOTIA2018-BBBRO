package org.eclipse.om2m.sdt.home.types;

import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.datapoints.ClonedEnum;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public class TasteStrength extends ClonedEnum {

	static public final int zero = 1;
	static public final int sensitive = 2;
	static public final int medium = 3;
	static public final int strong = 4;
	static public final int maximum = 5;
	
	static private List<Integer> values = Arrays.asList(
			zero, sensitive, medium, strong, maximum
	);

	public TasteStrength(EnumDataPoint<Integer> dp) {
		this(DatapointType.strength, dp);
	}

	public TasteStrength(Identifiers name, EnumDataPoint<Integer> dp) {
		super(name, HomeDataType.TasteStrength, dp);
		setValidValues(values);	
	}

}
