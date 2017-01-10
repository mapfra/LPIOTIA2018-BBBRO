package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public abstract class TasteStrength extends EnumDataPoint<Integer> {

	static public final int zero = 1;
	static public final int sensitive = 2;
	static public final int medium = 3;
	static public final int strong = 4;
	static public final int maximum = 5;
	
	public TasteStrength(String name) {
		super(name, HomeDataType.TasteStrength);
		setValidValues(new Integer[] { zero, sensitive, medium, strong, maximum  });	
	}
}
