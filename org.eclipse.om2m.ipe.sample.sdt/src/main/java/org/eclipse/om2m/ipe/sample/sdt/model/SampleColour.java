/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sample.sdt.model;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Event;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.Colour;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class SampleColour extends Colour {

	public SampleColour(String name, Domain domain) {
		// Default color: yellow (rgb: 255,255,0)
		super(name, domain,
			new IntegerDataPoint(DatapointType.red) {
				private Integer v = 255;
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					v = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return v;
				}
			}, 
			new IntegerDataPoint(DatapointType.green) {
				private Integer v = 255;
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					v = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return v;
				}
			}, 
			new IntegerDataPoint(DatapointType.blue) {
				private Integer v = 0;
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					v = value;
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return v;
				}
			}
		);
	}

	public void setRed(int v) throws DataPointException, AccessException {
		int old = getRed();
		super.setRed(v);
		if (old != v) {
			Event evt = new Event("Set RED " + getOwner().getId());
			evt.addDataPoint(getDataPointByShortName(DatapointType.red.getShortName()));
			evt.setValue(v);
			addEvent(evt);
		}
	}

	public void setGreen(int v) throws DataPointException, AccessException {
		int old = getGreen();
		super.setGreen(v);
		if (old != v) {
			Event evt = new Event("Set GREEN " + getOwner().getId());
			evt.addDataPoint(getDataPointByShortName(DatapointType.green.getShortName()));
			evt.setValue(v);
			addEvent(evt);
		}
	}
	
	public void setBlue(int v) throws DataPointException, AccessException {
		int old = getBlue();
		super.setBlue(v);
		if (old != v) {
			Event evt = new Event("Set BLUE " + getOwner().getId());
			evt.addDataPoint(getDataPointByShortName(DatapointType.blue.getShortName()));
			evt.setValue(v);
			addEvent(evt);
		}
	}
	
}
