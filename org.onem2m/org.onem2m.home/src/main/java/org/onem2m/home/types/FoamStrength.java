package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;

public abstract class FoamStrength extends EnumDataPoint<Integer> {

	static public final int zero = 1;
	static public final int medium = 2;
	static public final int maximum = 3;
	
	public FoamStrength(String name) {
		super(name, HomeDataType.FoamStrength);
		setValidValues(new Integer[] { zero, medium, maximum });	
	}
}
