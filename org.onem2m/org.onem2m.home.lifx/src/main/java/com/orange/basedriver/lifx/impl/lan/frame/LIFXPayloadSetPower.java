/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl.lan.frame;

public class LIFXPayloadSetPower extends LIFXPayload {

	protected static final int TYPE = 117;

	private final int level;
	private final int duration;

	public LIFXPayloadSetPower(final int pLevel, final int pDuration) {
		level = pLevel;
		duration = pDuration;
	}

	public LIFXPayloadSetPower() {
		level = -1;
		duration = -1;
	}

	@Override
	public int getType() {
		return 117;
	}

	@Override
	public void unmarshal() throws Exception {
		
	}

	@Override
	public void marshal() {
		payload = new byte[6];
		// level
		payload[0] = (byte) (level & 0xff);
		payload[1] = (byte) ((level >> 8) & 0xff);
		
		// duration
		payload[2] = (byte) (duration & 0xff);
		payload[3] = (byte) ((duration >> 8) & 0xff);
		payload[4] = (byte) ((duration >> 16) & 0xff);
		payload[5] = (byte) ((duration >> 24) & 0xff);
		
	}

	@Override
	public String toString() {
		return "SetPower(level=" + level + ", duration=" + duration + ")";
	}
}
