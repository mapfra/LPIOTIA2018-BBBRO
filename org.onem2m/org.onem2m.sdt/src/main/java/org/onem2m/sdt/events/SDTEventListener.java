package org.onem2m.sdt.events;

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
