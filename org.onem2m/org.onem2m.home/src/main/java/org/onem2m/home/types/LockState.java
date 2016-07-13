package org.onem2m.home.types;

import org.onem2m.sdt.datapoints.EnumDataPoint;

public abstract class LockState extends EnumDataPoint<Integer> {
	
	static public final int Locked  = 1;
	static public final int Unlocked = 2;
	static public final int NotfullyLocked = 3;
	static public final int Unknown = 4;
	
	public LockState(String name) {
		super(name);
		setValidValues(new Integer[] { Locked, Unlocked, NotfullyLocked, Unknown });
	}

}
