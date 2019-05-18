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
 * used to indicate that the Philips Hue Gateway is not reacheable <p>
 */
public class UnknownHueGatewayException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public UnknownHueGatewayException(final String msg){
		super("UnknownHueGatewayException. The gateway's IP address has changed? \n" + msg);
	}
	
	public UnknownHueGatewayException(final String msg, final Exception e) {
		super("UnknownHueGatewayException. The gateway's IP address has changed? \n" + msg, e);
	}
	
}
