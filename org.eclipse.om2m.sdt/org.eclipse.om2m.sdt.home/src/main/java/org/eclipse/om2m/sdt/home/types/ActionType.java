/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.Identifiers;

public enum ActionType implements Identifiers {
	
	activateClockTimer,
	deactivateClockTimer,
	downChannel,
	downVolume,
	toggle,
	upChannel,
	upVolume,
	
	nextTrack,
	previousTrack,
	
	resetNumberValue,
	incrementNumberValue,
	decrementNumberValue;
	
	private final String longName;
	private final String shortName;
	private final String cnd;
	
	ActionType() {
		longName = toString();
		shortName = FlexContainers.getFlexShortName(longName);
		cnd = FlexContainers.getContainerDefinition(longName);
	}
    
	@Override
    public String getDefinition() {
    	return cnd;
    }

	@Override
	public String getShortName() {
		return shortName;
	}

	@Override
	public String getLongName() {
		return longName;
	}

}
