/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.util;

public interface Constants {
	
	String APPNAME = "Home_Monitoring_Application";
	String RESOURCE_ID = "SDT_" + APPNAME;
	String FRIENDLY_HOME_MONITORING_NAME = "Home Monitoring Application";
	String ACP_HOME_MONITORING_NAME = RESOURCE_ID + "_ACP";
	String HOME_MONITORING_RESOURCE_ID = "ResourceID/" + RESOURCE_ID;
	String RESOURCE_TYPE = "ResourceType/Application";
	String POA = "HomeMonitoringPOA";
	
	String WEBAPPS = APPNAME + "/webapps/";
	String IMAGES = WEBAPPS + "images/";

	String NAME = "name";
	String CREDENTIALS = "credentials";
	String PASSWORD = "password";
	String AUTHORIZATION = "Authorization";
	String AUTHENTICATE = "WWW-Authenticate";
	String REQUESTED = "X-Requested-With";
	
}
