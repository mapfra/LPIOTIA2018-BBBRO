/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx;

public interface LIFXDiscoveredDeviceListener {

	public void notifyDeviceArrived(LIFXDevice lifxDevice);
	
	public void notifyDeviceLeft(LIFXDevice lifxDevice);
	
}
