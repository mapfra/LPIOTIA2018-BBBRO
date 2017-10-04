/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.impl.lan.frame;

import java.util.Arrays;

public class LIFXProtocolHeader {

	private int type = 0;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public byte[] getBytes() {
		byte[] protocolHeader = new byte[12];
		Arrays.fill(protocolHeader, (byte) 0);
		
		protocolHeader[9] = (byte) ((type >> 8) & 0xff);
		protocolHeader[8] = (byte) (type & 0xff);
		
		return protocolHeader; 
	}
	
	public void setBytes(byte[] protocolHeader) throws Exception {
		if (protocolHeader.length != 12) {
			throw new Exception("Expecting 12 bytes, found " + protocolHeader.length + " bytes");
		}
		
		type = (protocolHeader[9] << 8) + protocolHeader[8];
	}
	
	@Override
	public String toString() {
		return "protocolHeader(type=" + type + ")" ;
	}
	
}
