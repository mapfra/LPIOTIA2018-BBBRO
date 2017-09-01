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
package org.eclipse.om2m.android.dashboard.cse.requests;

public class OneM2MRequestParams {
	
	private String arg1;
	private String arg2;
	
	public OneM2MRequestParams(final String arg1) {
		this.arg1 = arg1;
	}
	
	public OneM2MRequestParams(final String arg1, final String arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	public String getArg1() {
		return arg1;
	}

	public String getArg2() {
		return arg2;
	}
	
	@Override
	public String toString() {
		return arg1 + ":" + arg2;
	}

}
