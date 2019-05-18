/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.events;

public interface SDTEventListener {
	
	String DOMAINS 			= "sdt.domains.ids";
	String DEVICES_DEFS 	= "sdt.devices.definitions";
	String DEVICES_IDS		= "sdt.devices.ids";
	String MODULES_DEFS 	= "sdt.modules.definitions";
	String MODULES_NAMES 	= "sdt.modules.names";
	String DATAPOINTS 		= "sdt.datapoints.names";
	
	void handleNotification(SDTNotification notif);
	
	void setAuthenticationThreadGroup(ThreadGroup group);

}
