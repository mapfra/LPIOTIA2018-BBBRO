/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.args.Command;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.NextTrack;
import org.eclipse.om2m.sdt.home.actions.PreviousTrack;
import org.eclipse.om2m.sdt.home.types.ActionType;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.home.types.PlayerMode;

public class PlayerControl extends Module {
	
	private PlayerMode currentPlayerMode;
	private StringDataPoint currentPlayerModeName;
	private ArrayDataPoint<PlayerMode.Values> supportedPlayerModes;
	private FloatDataPoint speedFactor;
	
	private Action nextTrack;
	private Action previousTrack;
	
	public PlayerControl(final String name, final Domain domain, 
			PlayerMode currentPlayerMode, ArrayDataPoint<PlayerMode.Values> supportedPlayerModes) {
		super(name, domain, ModuleType.playerControl);

		if ((currentPlayerMode == null) ||
				! currentPlayerMode.getShortName().equals(DatapointType.currentPlayerMode.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong currentPlayerMode datapoint: " + currentPlayerMode);
		}
		this.currentPlayerMode = currentPlayerMode;
		this.currentPlayerMode.setWritable(true);
		this.currentPlayerMode.setDoc("The current mode of the player.");
		addDataPoint(this.currentPlayerMode);
		
		if ((supportedPlayerModes == null) ||
				! supportedPlayerModes.getShortName().equals(DatapointType.supportedPlayerModes.getShortName())) {
			throw new IllegalArgumentException("Wrong supportedPlayerModes datapoint: " + supportedPlayerModes);
		}
		this.supportedPlayerModes = supportedPlayerModes;
		this.supportedPlayerModes.setWritable(false);
		this.supportedPlayerModes.setDoc("List of supported modes for a player.");
		addDataPoint(this.supportedPlayerModes);
	}
	
	public PlayerControl(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, 
			(PlayerMode) dps.get(DatapointType.currentPlayerMode.getShortName()), 
			(ArrayDataPoint<PlayerMode.Values>) dps.get(DatapointType.supportedPlayerModes.getShortName()));
		StringDataPoint currentPlayerModeName = (StringDataPoint) dps.get(DatapointType.currentPlayerModeName.getShortName());
		if (currentPlayerModeName != null)
			setCurrentPlayerModeName(currentPlayerModeName);
		FloatDataPoint speedFactor = (FloatDataPoint) dps.get(DatapointType.speedFactor.getShortName());
		if (speedFactor != null)
			setSpeedFactor(speedFactor);
	}
	
	public void addAction(Action action) {
		if (action.getShortDefinitionName().equals(ActionType.nextTrack.getShortName())) {
			this.nextTrack = action;
			super.addAction(nextTrack);
		} else if (action.getShortDefinitionName().equals(ActionType.previousTrack.getShortName())) {
			this.previousTrack = action;
			super.addAction(previousTrack);
		} else {
			throw new IllegalArgumentException("Wrong action: " + action);
		}
	}

	public void setNextTrack(NextTrack nextTrack) {
		addAction(nextTrack);
	}

	public void setPreviousTrack(PreviousTrack previousTrack) {
		addAction(previousTrack);
	}
	
	public void nextTrack() throws ActionException, AccessException {
		if (nextTrack == null)
			throw new ActionException("Not implemented");
		((Command)nextTrack).invoke(null);
	}
	
	public void previousTrack() throws ActionException, AccessException {
		if (previousTrack == null)
			throw new ActionException("Not implemented");
		((Command)previousTrack).invoke(null);
	}

	public PlayerMode.Values getPlayerMode() throws DataPointException, AccessException {
		return currentPlayerMode.getValue();
	}

	public void setPlayerMode(PlayerMode.Values v) throws DataPointException, AccessException {
		currentPlayerMode.setValue(v);
	}

	public List<PlayerMode.Values> getSupportedPlayerModes() throws DataPointException, AccessException {
		return supportedPlayerModes.getValue();
	}

	public void setCurrentPlayerModeName(StringDataPoint sv) {
		currentPlayerModeName = sv;
		currentPlayerModeName.setWritable(false);
		currentPlayerModeName.setOptional(true);
		currentPlayerModeName.setDoc("Name of current player mode in string. This can be used when “currentPlayerMode” is vendor-specific.");
		addDataPoint(currentPlayerModeName);
	}

	public String getCurrentPlayerModeName() throws DataPointException, AccessException {
		if (currentPlayerModeName == null)
			throw new DataPointException("Not implemented");
		return currentPlayerModeName.getValue();
	}

	public void setSpeedFactor(FloatDataPoint mv) {
		speedFactor = mv;
		speedFactor.setWritable(true);
		speedFactor.setOptional(true);
		speedFactor.setDoc("The optional factor of speeding up or slowing down playback, rewind or fast forward.");
		addDataPoint(speedFactor);
	}

	public float getSpeedFactor() throws DataPointException, AccessException {
		if (speedFactor == null)
			throw new DataPointException("Not implemented");
		return speedFactor.getValue();
	}

	public void setSpeedFactor(float v) throws DataPointException, AccessException {
		if (speedFactor == null)
			throw new DataPointException("Not implemented");
		speedFactor.setValue(v);
	}

}
