package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;
import org.onem2m.sdt.types.DataType;

public abstract class LiquidLevel extends EnumDataPoint<Integer> {
	
	static public final int zero = 1;
	static public final int low = 2;
	static public final int medium = 3;
	static public final int high = 4;
	static public final int maximum = 5;
	
	public LiquidLevel(String name) {
		super(name, DataType.LiquidLevel);
		setValidValues(new Integer[] { zero, low, medium, high, maximum });
	}

}
