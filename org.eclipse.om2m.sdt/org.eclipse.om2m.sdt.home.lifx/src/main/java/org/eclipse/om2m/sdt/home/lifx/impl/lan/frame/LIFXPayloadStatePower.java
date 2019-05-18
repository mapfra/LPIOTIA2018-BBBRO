/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.impl.lan.frame;

public class LIFXPayloadStatePower extends LIFXPayload {

	protected static final int TYPE = 118;
	
	private int level;
	
	@Override
	public int getType() {
		return TYPE;
	}
	
	

	public int getLevel() {
		return level;
	}



	@Override
	public void unmarshal() throws Exception {
		level = (payload[0] & 0xff) + ((payload[1] << 8) & 0xffff); 
	}
	
	@Override
	public String toString() {
		return "StatePower(level=" + level + ")";
	}

}
