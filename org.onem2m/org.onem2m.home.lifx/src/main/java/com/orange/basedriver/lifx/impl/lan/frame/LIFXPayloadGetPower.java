/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl.lan.frame;

public class LIFXPayloadGetPower extends LIFXPayload {
	
	protected final static int TYPE = 116;

	@Override
	public int getType() {
		return TYPE;
	}

	@Override
	public void unmarshal() throws Exception {
		// nothing
	}
	
	@Override
	public void marshal() {
		// nothing to do
	}

}
