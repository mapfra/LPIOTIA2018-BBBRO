/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.impl.lan.frame;

public class LIFXPayloadStateService extends LIFXPayload {

	public int getService() {
		return service;
	}

	public int getPort() {
		return port;
	}

	protected static final int TYPE = 3;
	
	private int service;
	private int port;
	
	@Override
	public int getType() {
		return TYPE;
	}

	public void unmarshal() throws Exception {
		if (payload.length != 5) {
			throw new Exception("expecting 5 bytes, found " + payload.length);
		}
		
		service = payload[0];
		port = (payload[1] & 0xffff) + ((payload[2] << 8) & 0xffff) + ((payload[3] << 16) & 0xffffff) + ((payload[4] << 24) & 0xffffffff); 
		
		
		
	}

	@Override
	public String toString() {
		return "StateServicePayload(type=" + TYPE + ", service=" + service + ", port=" + port + ")";
	}
	
	
}
