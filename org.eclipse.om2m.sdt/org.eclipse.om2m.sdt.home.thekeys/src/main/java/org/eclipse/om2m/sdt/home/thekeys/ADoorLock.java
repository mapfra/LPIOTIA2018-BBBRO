/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.thekeys;

import org.eclipse.om2m.sdt.home.types.DoorState;

public abstract class ADoorLock {

	private String id;
	private boolean lockState = true; // locked
	private int doorState = DoorState.Closed;
	private boolean status;
	private int code;
	private String message;
	
	public ADoorLock(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getSerial() {
		return id;
	}
	
	public abstract void test() throws Exception;
	
	public abstract void open() throws Exception;
	
	public abstract void disconnect() throws Exception;
	
	public abstract void close() throws Exception;
	
	public abstract void cancel() throws Exception;

	public boolean getLockState() {
		return lockState;
	}
	
	protected void setLockState(boolean s) {
		lockState = s;
	}

	public int getDoorState() {
		return doorState;
	}
	
	protected void setDoorState(int s) {
		doorState = s;
	}

	public boolean getStatus() {
		return status;
	}

	protected void setStatus(boolean status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	protected void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	protected void setMessage(String message) {
		this.message = message;
	}

}
