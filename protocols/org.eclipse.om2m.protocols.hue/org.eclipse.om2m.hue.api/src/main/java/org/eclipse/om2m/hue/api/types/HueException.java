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
*******************************************************************************/
package org.eclipse.om2m.hue.api.types;

/**
 * <p>
 * used to indicate exceptions like : <p>
 * - HTTP request errors <p>
 * - REST resource not available <p>
 */
public class HueException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public HueException(final String msg) {
		super("HueException : " + msg);
	}

	public HueException(final String msg, final Exception e) {
		super("HueException : " + msg, e);
	}
	
}
