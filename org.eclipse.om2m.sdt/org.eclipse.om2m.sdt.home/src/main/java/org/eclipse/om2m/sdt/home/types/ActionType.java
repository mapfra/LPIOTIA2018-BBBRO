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
	
	activateClockTimer(1, "timer.activateClockTimer", "activateClockTimer", "acCTr"),
	deactivateClockTimer(2, "timer.deactivateClockTimer", "deactivateClockTimer","deCTr"),
	downChannel(3, "televisionchannel.downChannel", "downChannel", "dowCl"),
	downVolume(4, "audiovolume.downVolume", "downVolume", "dowVe"),
	toggle(5, "binaryswitch.toggle", "toggle", "togge"),
	upChannel(6, "televisionchannel.upChannel", "upChannel", "uphCl"),
	upVolume(7, "audiovolume.upVolume", "upVolume", "upVol"),
	
	resetNumberValue(8,"numbervalue.resetNumberValue", "resetNumberValue", "reNVe"), 					// Added by Andreas Kraft
	incrementNumberValue(9, "numbervalue.incrementNumberValue", "incrementNumberValue", "inNVe"), 		// Added by Andreas Kraft
	decrementNumberValue(10, "numbervalue.decrementNumberValue", "decrementNumberValue", "deNVe"); 	// Added by Andreas Kraft
	
	static private final String PATH = "org.onem2m.home.moduleclass.";
	
	private int value;
	private String def;
	private final String longDefinitionName;
	private final String shortDefinitionName;
	
	ActionType(final int v, final String s, final String longDef, final String shortDef) {
		value = v;
		def = s;
		longDefinitionName = longDef;
		shortDefinitionName = shortDef;
	}

    public int getValue() {
        return value;
    }
    
	@Override
    public String getDefinition() {
    	return PATH + def;
    }

	@Override
	public String getShortName() {
		return shortDefinitionName;
	}

	@Override
	public String getLongName() {
		return longDefinitionName;
	}

	public static ActionType fromValue(int v) {
        for (ActionType c: ActionType.values()) {
            if (c.value == v) {
                return c;
            }
        }
        throw new IllegalArgumentException("Undefined value " + v);
    }

}
