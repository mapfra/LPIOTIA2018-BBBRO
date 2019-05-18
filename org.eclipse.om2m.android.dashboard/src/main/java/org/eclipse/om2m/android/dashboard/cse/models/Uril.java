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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Uril {
	
	@JsonProperty("m2m:uril")
	private List<String> uril;

	public List<String> getUril() {
		return uril;
	}

	public void setUril(List<String> uril) {
		this.uril = uril;
	}

}
