/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.netatmo.model;

public class WelcomeCamera {
	
	public static final String ID = "id";
	public static final String TYPE = "type";
	public static final String STATUS = "status";
	public static final String VPN_URL = "vpn_url";
	public static final String IS_LOCAL = "is_local";
	public static final String SD_STATUS = "sd_status";
	public static final String ALIM_STATUS = "alim_status";
	public static final String NAME = "name";
	
	private final String id;
	private final String type;
	private final String name;
	private String vpnUrl;
	private Boolean isOn;
	private Boolean sdOk;
	private Boolean alimOk;
	private Boolean useLocalUrl;

	public WelcomeCamera(final String pId, final String pType, final String pName) {
		id = pId;
		name = pName;
		type = pType;
	}

	public void setUseLocalUrl(Boolean useLocalUrl) {
		this.useLocalUrl = useLocalUrl;
	}

	public Boolean getUseLocalUrl() {
		return useLocalUrl;
	}

	public String getVpnUrl() {
		return vpnUrl;
	}

	public void setVpnUrl(String vpnUrl) {
		this.vpnUrl = vpnUrl;
	}

	public Boolean getIsOn() {
		return isOn;
	}

	public void setIsOn(Boolean isOn) {
		this.isOn = isOn;
	}

	public Boolean getSdOk() {
		return sdOk;
	}

	public void setSdOk(Boolean sdOk) {
		this.sdOk = sdOk;
	}

	public Boolean getAlimOk() {
		return alimOk;
	}

	public void setAlimOk(Boolean alimOk) {
		this.alimOk = alimOk;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Camera(id=");
		sb.append(id);
		sb.append(", name=");
		sb.append(name);
		sb.append(", type=" + type);
		sb.append(", isOn=");
		sb.append(isOn);
		sb.append(", alimOk=");
		sb.append(alimOk);
		sb.append(", sdOk=");
		sb.append(sdOk);
		sb.append(")");
		return sb.toString();
	}

}
