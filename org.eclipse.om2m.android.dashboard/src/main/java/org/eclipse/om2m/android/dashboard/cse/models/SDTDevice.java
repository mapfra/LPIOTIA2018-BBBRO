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
package org.eclipse.om2m.android.dashboard.cse.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SDTDevice extends FlexContainer {
	
	@JsonProperty("pDSNm")
	private String serialNumber;
	
	@JsonProperty("prDNe")
	private String deviceName;
	
	@JsonProperty("pDANe")
	private String deviceAliasName;
	
	@JsonProperty("prDMr")
	private String manufacturer;
	
	@JsonProperty("pDMNe")
	private Object model;
	
	@JsonProperty("prDTe")
	private String type;
	
//	private String description;
	
	@JsonProperty("proLn")
	private String location;
	
	@JsonProperty("proPl")
	private String protocol;
	
	private String mapRi;
	
	private Map<String, Object> others;
	
	private final Map<String, SDTModule> modules;
	
	
	public SDTDevice(final String pRi, final String pCnd) {
		this();
		setRi(pRi);
		setCnd(pCnd);
	}
	
	public SDTDevice() {
		this.modules = new HashMap<String, SDTModule>();
		this.others = new HashMap<String, Object>();
	}
	

	public String getManufacturer() {
		return manufacturer;
	}

	public Object getModel() {
		return model;
	}

	public String getType() {
		return type;
	}

	public String getLocation() {
		return location;
	}

	public String getProtocol() {
		return protocol;
	}

	public Map<String, SDTModule> getModules() {
		return modules;
	}
	
	public void addModule(SDTModule sdtModule) {
		this.modules.put(sdtModule.getShortname(), sdtModule);
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public Map<String, Object> getOthers() {
		return others;
	}
	
	@JsonAnySetter
	public void setOther(String name, Object value) {
		others.put(name, value);
	}

	public String getDeviceAliasName() {
		return deviceAliasName;
	}

	public String getMapRi() {
		return mapRi;
	}

	public void setMapRi(String mapRi) {
		this.mapRi = mapRi;
	}
	
}
