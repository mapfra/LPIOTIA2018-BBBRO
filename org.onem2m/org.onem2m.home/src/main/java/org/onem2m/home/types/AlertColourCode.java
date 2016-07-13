package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;

public abstract class AlertColourCode extends EnumDataPoint<Integer> {
	
	static public final int Red = 1;
	static public final int Green = 2;
	
	public AlertColourCode(String name) {
		super(name);
		setValidValues(new Integer[] { Red, Green });
	}

}
