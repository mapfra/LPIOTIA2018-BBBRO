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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FlexContainerAnnounced extends FlexContainer {
	
	private String lnk;

	public String getLnk() {
		return lnk;
	}

	public void setLnk(String lnk) {
		this.lnk = lnk;
	}

}
