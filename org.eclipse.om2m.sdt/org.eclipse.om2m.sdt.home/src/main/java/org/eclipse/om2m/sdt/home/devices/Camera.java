/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.home.modules.Credentials;
import org.eclipse.om2m.sdt.home.modules.MotionSensor;
import org.eclipse.om2m.sdt.home.modules.PersonSensor;
import org.eclipse.om2m.sdt.home.modules.PlayerControl;
import org.eclipse.om2m.sdt.home.modules.SessionDescription;
import org.eclipse.om2m.sdt.home.types.DeviceType;

public class Camera extends GenericDevice {

	private MotionSensor motionSensor;
	private SessionDescription sessionDescription;
	private PlayerControl playerControl;
	private PersonSensor personSensor;
	private Credentials credentials;
	
	public Camera(String id, String serial, Domain domain) {
		super(id, serial, DeviceType.deviceCamera, domain);
	}
	
	public void addModule(Module module) {
		if (module instanceof MotionSensor)
			setMotionSensor((MotionSensor)module);
		else if (module instanceof PersonSensor)
			setPersonSensor((PersonSensor)module);
		else if (module instanceof SessionDescription)
			setSessionDescription((SessionDescription)module);
		else if (module instanceof PlayerControl)
			setPlayerControl((PlayerControl)module);
		else if (module instanceof Credentials)
			setCredentials((Credentials)module);
		else 
			super.addModule(module);
	}

	public void setMotionSensor(MotionSensor mod) {
		this.motionSensor = mod;
		super.addModule(motionSensor);
	}
	
	public MotionSensor getMotionSensor() {
		return motionSensor;
	}
	
	public void setPersonSensor(PersonSensor mod) {
		this.personSensor = mod;
		super.addModule(personSensor);
	}
	
	public PersonSensor getPersonSensor() {
		return personSensor;
	}
	
	public void setSessionDescription(SessionDescription mod) {
		this.sessionDescription = mod;
		super.addModule(sessionDescription);
	}
		
	public SessionDescription getSessionDescription() {
		return sessionDescription;
	}
	
	public void setPlayerControl(PlayerControl mod) {
		this.playerControl = mod;
		super.addModule(playerControl);
	}
		
	public PlayerControl getPlayerControl() {
		return playerControl;
	}
	
	public void setCredentials(Credentials mod) {
		this.credentials = mod;
		super.addModule(credentials);
	}
		
	public Credentials getCredentials() {
		return credentials;
	}

}
