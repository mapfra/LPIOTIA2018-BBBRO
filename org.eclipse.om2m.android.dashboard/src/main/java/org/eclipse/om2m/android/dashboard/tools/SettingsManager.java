/*******************************************************************************
 * Copyright (c) 2013, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *    BOLLE Sebastien <sebastien.bolle@orange.com>.
 *******************************************************************************/
package org.eclipse.om2m.android.dashboard.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class SettingsManager {

	private static final SettingsManager INSTANCE = new SettingsManager();
	
	private static SharedPreferences settings = null;
	
	private static String WIZARD_APPLICATION_SIMPLE 	= "wiz_application_simple";
	private static String WIZARD_APPLICATION_DETAILS 	= "wiz_application_details";
	private static String WIZARD_DEVICE_SIMPLE 			= "wiz_device_simple";
	private static String WIZARD_DEVICE_DETAILS 		= "wiz_device_details";
	private static String WIZARD_CONFIG 				= "wiz_config";
	
	private static String LANGUAGE 						= "language";
	private static String DEFAULT_LANGUAGE 				= "en";
	
	private static String DEVICE_NAME                   = "deviceName";
	public static String DEVICE_NAME_WITH_ALIAS        = "pDANe";
	public static String DEVICE_NAME_WITH_NAME         = "prDNe";
	private static String DEFAULT_DEVICE_NAME           = DEVICE_NAME_WITH_NAME;
	
	private static String CSE_HOSTNAME                = "cse_hostname";              
	private static String DEFAULT_CSE_HOSTNAME        = "10.0.10.103"; //"http://192.168.1.11:8080/~/dt-in-cse/dt-in-name";
	private static String CSE_PORT                    = "cse_port";
	private static String DEFAULT_CSE_PORT            = "8080";
	private static String CSE_ID                      = "cse_id";
	private static String DEFAULT_CSE_ID              = "in-cse";
	private static String CSE_NAME                    = "cse_name";
	private static String DEFAULT_CSE_NAME            = "in-name";
	private static String CSE_LOGIN                   = "cse_login";
	private static String DEFAULT_CSE_LOGIN           = "admin";
	private static String CSE_PWD                     = "cse_pwd";
	private static String DEFAULT_CSE_PWD             = "admin";
	
	private static boolean DEFAULT_WIZARD_ACTIVATION = false;
	
	private SettingsManager() {
	}
	
	public static SettingsManager getInstance(Context context) {
    	// get ip in options
        settings = context.getSharedPreferences("MyOTBSettings", Context.MODE_PRIVATE);		
        return INSTANCE;
    }
	
	public boolean isWizardApplicationSimpleActivated() {
		return settings.getBoolean(WIZARD_APPLICATION_SIMPLE, DEFAULT_WIZARD_ACTIVATION);
	}
	
	public void setWizardApplicationSimple(boolean activated) {
		Editor editor = settings.edit();
		editor.putBoolean(WIZARD_APPLICATION_SIMPLE, activated);
		editor.commit();
	}
	
	public boolean isWizardApplicationDetailsActivated() {
		return settings.getBoolean(WIZARD_APPLICATION_DETAILS, DEFAULT_WIZARD_ACTIVATION);
	}
	
	public void setWizardApplicationDetails(boolean activated) {
		Editor editor = settings.edit();
		editor.putBoolean(WIZARD_APPLICATION_DETAILS, activated);
		editor.commit();
	}
	
	public boolean isWizardDeviceSimpleActivated() {
		return settings.getBoolean(WIZARD_DEVICE_SIMPLE, DEFAULT_WIZARD_ACTIVATION);
	}
	
	public void setWizardDeviceSimple(boolean activated) {
		Editor editor = settings.edit();
		editor.putBoolean(WIZARD_DEVICE_SIMPLE, activated);
		editor.commit();
	}
	
	public boolean isWizardDeviceDetailsActivated() {
		return settings.getBoolean(WIZARD_DEVICE_DETAILS, DEFAULT_WIZARD_ACTIVATION);
	}
	
	public void setWizardDeviceDetails(boolean activated) {
		Editor editor = settings.edit();
		editor.putBoolean(WIZARD_DEVICE_DETAILS, activated);
		editor.commit();
	}
	
	public boolean isWizardConfigActivated() {
		return settings.getBoolean(WIZARD_CONFIG, DEFAULT_WIZARD_ACTIVATION);
	}
	
	public void setWizardConfig(boolean activated) {
		Editor editor = settings.edit();
		editor.putBoolean(WIZARD_CONFIG, activated);
		editor.commit();
	}

	public String getLanguage() {
		return settings.getString(LANGUAGE, DEFAULT_LANGUAGE);
	}
	
	public void setLanguage(String language) {
		Editor editor = settings.edit();
		editor.putString(LANGUAGE, language);
		editor.commit();
	}
	
	public String getCSEHostname() {
		return settings.getString(CSE_HOSTNAME, DEFAULT_CSE_HOSTNAME);
	}
	
	public void setCSEHostname(String cseHostname) {
		Editor editor = settings.edit();
		editor.putString(CSE_HOSTNAME, cseHostname);
		editor.commit();
	}
	
	public String getCSEPort() {
		return settings.getString(CSE_PORT, DEFAULT_CSE_PORT);
	}
	
	public void setCSEPort(String csePort) {
		Editor editor = settings.edit();
		editor.putString(CSE_PORT, csePort);
		editor.commit();
	}
	
	public String getCSEId() {
		return settings.getString(CSE_ID, DEFAULT_CSE_ID);
	}
	
	public void setCSEId(String cseId) {
		Editor editor = settings.edit();
		editor.putString(CSE_ID, cseId);
		editor.commit();
	}
	
	public String getCSEName() {
		return settings.getString(CSE_NAME, DEFAULT_CSE_NAME);
	}
	
	public void setCSEName(String cseName) {
		Editor editor = settings.edit();
		editor.putString(CSE_NAME, cseName);
		editor.commit();
	}
	
	public String getCSELogin() {
		return settings.getString(CSE_LOGIN, DEFAULT_CSE_LOGIN);
	}
	
	public void setCSELogin(String login) {
		Editor editor = settings.edit();
		editor.putString(CSE_LOGIN, login);
		editor.commit();
	}
	
	public String getCSEPwd() {
		return settings.getString(CSE_PWD, DEFAULT_CSE_PWD);
	}
	
	public void setCSEPwd(String pwd) {
		Editor editor = settings.edit();
		editor.putString(CSE_PWD, pwd);
		editor.commit();
	}
	
	public void setDeviceName(String deviceName) {
		Editor editor = settings.edit();
		editor.putString(DEVICE_NAME, deviceName);
		editor.commit();
	}
	
	public String getDeviceName() {
		return settings.getString(DEVICE_NAME, DEFAULT_DEVICE_NAME);
	}
	
}
