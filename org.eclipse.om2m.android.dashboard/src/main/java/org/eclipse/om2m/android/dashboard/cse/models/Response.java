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

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class Response {
	
//	private SDTDevice sdtDevice;
	private Map<String, Object> any = new HashMap<String, Object>();
	
//	public SDTDevice getSdtDevice() {
//		return sdtDevice;
//	}
//
//	public void setSdtDevice(SDTDevice sdtDevice) {
//		this.sdtDevice = sdtDevice;
//	}
	
	@JsonAnyGetter
	public Map<String, Object> getAny() {
		return any;
	}
	
	@JsonAnySetter
	public void setAny(String name, Object value) {
		any.put(name, value);
	}

}
