/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.events;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.ModuleClass;

public class SDTNotification {

	private String name;
	private Object val;
	private Device device;
	private ModuleClass module;
	private DataPoint dp;
	
	public SDTNotification(String name, Object val, Device device, ModuleClass module, DataPoint dp) {
		this.name = name;
		this.val = val;
		this.device = device;
		this.module = module;
		this.dp = dp;
	}
	
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return val;
	}
	
	public Device getDevice() {
		return device;
	}
	
	public ModuleClass getModule() {
		return module;
	}
	
	public DataPoint getDataPoint() {
		return dp;
	}
	
	public String toString() {
		return "<SDTNotification " + name + "=" + val + " device=" + device 
				+ " module=" + module + " datapoint=" + dp + "/>";
	}
	
}
