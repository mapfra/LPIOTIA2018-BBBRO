/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;

public abstract class PlayerMode extends EnumDataPoint<PlayerMode.Values> {
	
	static public enum Values {
		stop, play, pause, resume, record, rewind, fastRewind, forward, fastForward, searchPrevious, searchNext
	}
	
	public PlayerMode() {
		this(DatapointType.currentPlayerMode);
	}
	
	public PlayerMode(DatapointType dt) {
		super(dt, HomeDataType.PlayerMode);
		setValidValues(Values.values());
	}

}
