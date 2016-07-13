package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;

public abstract class DoorState extends EnumDataPoint<Integer> {
	
	static public final int Closed = 1;
	static public final int Open = 2;
	static public final int Opening = 3;
	static public final int Closing = 4;
	static public final int Stopped = 5;
	
	public DoorState(String name) {
		super(name);
		setValidValues(new Integer[] { Closed, Open, Opening, Closing, Stopped });
	}

}
