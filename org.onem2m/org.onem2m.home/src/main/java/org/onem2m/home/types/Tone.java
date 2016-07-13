package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;
import org.onem2m.sdt.types.DataType;

public abstract class Tone extends EnumDataPoint<Integer> {
	
	static public final int Fire = 1;
	static public final int Theft = 2;
	static public final int Emergency = 3;
	static public final int Doorbell = 4;
	static public final int DeviceFail = 5;
	static public final int Silent = 6;
	
	public Tone(String name) {
		super(name, DataType.Tone);
		setValidValues(new Integer[] { Fire, Theft, Emergency, Doorbell, DeviceFail, Silent });
	}
	
}
