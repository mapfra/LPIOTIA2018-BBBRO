/*******************************************************************************
* Copyright (c) 2014-2018 Orange.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    BAREAU Cyrille <cyrille.bareau@orange.com>
*    BONNARDEL Gregory <gbonnardel.ext@orange.com>
*    OSKO Tomasz <tomasz.osko@orange.com>
*    RATUSZEK Przemyslaw <przemyslaw.ratuszek@orange.com>
*******************************************************************************/
package org.eclipse.om2m.hue.impl.controller;

public interface HueConstants {
	
	String LIGHTS = "lights";
	String GROUPS = "groups";
	String ERROR = "error";
	String EMPTY = "{}";

	String NAME = "name";
	String MODEL_ID = "modelid";
	String TYPE = "type";
	String SW_VERSION = "swversion";
	String STATE = "state";
	String REACHABLE = "reachable";

	String SAT = "sat";
	String BRI = "bri";
	String HUE = "hue";		
	String ALERT = "alert";
	String EFFECT = "effect";
	String ON = "on";
	String ACTION = "action";
	
	String OSGI_DEVICE_FRIENDLY_NAME = "DEVICE_FRIENDLY_NAME";
	String OSGI_DEVICE_MANUFACTURER = "DEVICE_MANUFACTURER";
	String OSGI_DEVICE_PRODUCT_CLASS = "DEVICE_PRODUCT_CLASS";

	String HUE_CATEGORY = "Hue";
	String HUE_FRIENDLY_NAME = "friendlyName";
	String HUE_MANUFACTURER = "manufacturer";
	String HUE_MANUFACTURER_URL = "manufacturerURL";
	String HUE_DEVICE_TYPE = "deviceType";
	String HUE_MODEL_DESCRIPTION = "modelDescription";
	String HUE_MODEL_NUMBER = "modelNumber";
	String HUE_MODEL_NAME = "modelName";
	String HUE_MODEL_URL = "modelURL";
	String HUE_SERIAL_NUMBER = "serialNumber";
	String HUE_URL_BASE = "URLBase";
	String HUE_PAIRING_MESSAGE = "{\"devicetype\":\"onem2m#mn-cse-hue-app\"}";
	
	String DEVICE = "device";
	String ROOT = "root";

}
