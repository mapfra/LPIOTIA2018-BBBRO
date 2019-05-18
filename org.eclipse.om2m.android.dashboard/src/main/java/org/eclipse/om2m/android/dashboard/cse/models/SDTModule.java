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

@JsonIgnoreProperties(ignoreUnknown=true)
public class SDTModule extends FlexContainer {
	
	private String shortname;
	private final Map<String, Object> datapoints;
	
	public SDTModule() {
		datapoints = new HashMap<String, Object>();
	}
	
	
	public Map<String, Object> getDatapoints() {
		return datapoints;
	}
	
	@JsonAnySetter
	public void setDataPoint(String name, Object value) {
		DatapointType type = DatapointType.fromShortName(name);
		if (type != null)
			datapoints.put(type.getLongName(), value);
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

}
