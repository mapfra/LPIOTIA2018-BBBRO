/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.Volume;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class AudioVolume extends Module {
	
	private BooleanDataPoint muteEnabled;
	private IntegerDataPoint volumePercentage;
	private IntegerDataPoint stepValue;
	private IntegerDataPoint maxValue;
	
	private Volume volume;
	
	public AudioVolume(final String name, final Domain domain, 
			IntegerDataPoint volumePercentage, BooleanDataPoint muteEnabled) {
		super(name, domain, ModuleType.audioVolume.getDefinition(),
				ModuleType.audioVolume.getLongDefinitionName(), ModuleType.audioVolume.getShortDefinitionName());

		this.muteEnabled = muteEnabled;
		this.muteEnabled.setDoc("The current status of the mute enablement. \"True\" indicates enabaled, and \"False\" indicates not enabled.");
		this.muteEnabled.setLongDefinitionType("muteEnabled");
		this.muteEnabled.setShortDefinitionType("mutEd");
		addDataPoint(this.muteEnabled);
		
		this.volumePercentage = volumePercentage;
		this.volumePercentage.setDoc("The rounded percentage of the current volume in the range of [0, maxValue]. 0% shall mean no sound produced.");
		this.volumePercentage.setLongDefinitionType("volumePercentage");
		this.volumePercentage.setShortDefinitionType("volPe");
		addDataPoint(this.volumePercentage);
	}
	
	public AudioVolume(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, 
			(IntegerDataPoint) dps.get("volPe"), 
			(BooleanDataPoint) dps.get("mutEd"));
		IntegerDataPoint stepValue = (IntegerDataPoint) dps.get("steVe");
		if (stepValue != null)
			setStepValue(stepValue);
		IntegerDataPoint maxValue = (IntegerDataPoint) dps.get("maxVe");
		if (maxValue != null)
			setMaxValue(maxValue);
	}
	
	public void addAction(Action action) {
		if (action instanceof Volume)
			setVolume((Volume)action);
		else
			super.addAction(action);
	}

	public Volume getVolume() {
		return volume;
	}

	public void setVolume(Volume volume) {
		this.volume = volume;
		super.addAction(volume);
	}
	
	public void upOrDown(final boolean up) throws ActionException, AccessException {
		if (volume == null)
			throw new ActionException("Not implemented");
		volume.upOrDown(up);
	}

	public boolean getMuteEnabled() throws DataPointException, AccessException {
		return muteEnabled.getValue();
	}

	public void setMuteEnabled(boolean v) throws DataPointException, AccessException {
		muteEnabled.setValue(v);
	}

	public int getVolumePercentage() throws DataPointException, AccessException {
		return volumePercentage.getValue();
	}

	public void setVolumePercentage(int v) throws DataPointException, AccessException {
		volumePercentage.setValue(v);
	}

	public void setStepValue(IntegerDataPoint sv) {
		stepValue = sv;
		stepValue.setWritable(false);
		stepValue.setOptional(true);
		stepValue.setDoc("Step value used by UpVolume and DownVolume.");
		stepValue.setLongDefinitionType("stepValue");
		stepValue.setShortDefinitionType("steVe");
		addDataPoint(stepValue);
	}

	public int getStepValue() throws DataPointException, AccessException {
		if (stepValue == null)
			throw new DataPointException("Not implemented");
		return stepValue.getValue();
	}

	public void setMaxValue(IntegerDataPoint mv) {
		maxValue = mv;
		maxValue.setWritable(false);
		maxValue.setOptional(true);
		maxValue.setDoc("Maximum value allowed for Volume.");
		maxValue.setLongDefinitionType("maxValue");
		maxValue.setShortDefinitionType("maxVe");
		addDataPoint(maxValue);
	}

	public int getMaxValue() throws DataPointException, AccessException {
		if (maxValue == null)
			throw new DataPointException("Not implemented");
		return maxValue.getValue();
	}

}
