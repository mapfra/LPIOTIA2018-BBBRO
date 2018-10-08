/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class AcousticSensor extends Module {
	
	private IntegerDataPoint acousticStatus;
	private FloatDataPoint loudness;
	
	public AcousticSensor(final String name, final Domain domain, FloatDataPoint loudness) {
		super(name, domain, ModuleType.acousticSensor);

		if ((loudness == null) ||
				! loudness.getShortName().equals(DatapointType.loudness.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong loudness datapoint: " + loudness);
		}
		this.loudness = loudness;
		this.loudness.setWritable(false);
		this.loudness.setDoc("The unit of measure of the loudness is \"dB2\".");
		addDataPoint(this.loudness);
	}
	
	public AcousticSensor(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (FloatDataPoint) dps.get(DatapointType.loudness.getShortName()));
		IntegerDataPoint acousticStatus = (IntegerDataPoint) dps.get(DatapointType.acousticStatus.getShortName());
		if (acousticStatus != null)
			setAcousticStatus(acousticStatus);
	}
	
	public float getLoudness() throws DataPointException, AccessException {
		return loudness.getValue();
	}

	public void setAcousticStatus(IntegerDataPoint dp) {
		acousticStatus = dp;
		acousticStatus.setOptional(true);
		acousticStatus.setWritable(false);
		acousticStatus.setDoc("The acousticStatus is expressed in percent, whereas a value of 0 means \"no sound\" and a value of 100 means \"most noisy\".");
		addDataPoint(acousticStatus);
	}
	
	public int getAcousticStatus() throws DataPointException, AccessException {
		if (acousticStatus == null)
			throw new DataPointException("Not implemented");
		return acousticStatus.getValue();
	}

}
