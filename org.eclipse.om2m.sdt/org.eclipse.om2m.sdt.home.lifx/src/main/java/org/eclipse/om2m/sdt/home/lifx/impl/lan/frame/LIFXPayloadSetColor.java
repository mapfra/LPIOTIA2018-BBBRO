/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.impl.lan.frame;

import java.util.Arrays;

public class LIFXPayloadSetColor extends LIFXPayload {
	
	protected static final int TYPE = 102;
	
	// color
	private final int hue;
	private final int saturation;
	private final int brightness;
	private final int kelvin;
	
	// duration
	private final int duration;
	
	
	
	public LIFXPayloadSetColor(int hue, int saturation, int brightness, int kelvin, int duration) {
		super();
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
		this.kelvin = kelvin;
		this.duration = duration;
	}

	@Override
	public int getType() {
		return TYPE;
	}

	@Override
	public void unmarshal() throws Exception {
		// nothing to do
	}
	
	@Override
	public void marshal() {
		payload = new byte[13];
		Arrays.fill(payload, (byte) 0);
		
		// reserved (1 bytes)
		
		// color (8 bytes) : hue (2 bytes) + saturation (2 bytes) + brightness (2 bytes) + kelvin (2 bytes)
		payload[1] = (byte) (hue & 0xff);
		payload[2] = (byte) ((hue >> 8) & 0xff);
		payload[3] = (byte) (saturation & 0xff);
		payload[4] = (byte) ((saturation >> 8) & 0xff);
		payload[5] = (byte) (brightness & 0xff);
		payload[6] = (byte) ((brightness >> 8) & 0xff);
		payload[7] = (byte) (kelvin & 0xff);
		payload[8] = (byte) ((kelvin >> 8) & 0xff);
		
		// duration (4 bytes)
		payload[9] = (byte) (duration & 0xff);
		payload[10] = (byte) ((duration >> 8) & 0xff);
		payload[11] = (byte) ((duration >> 16) & 0xffff);
		payload[12] = (byte) ((duration >> 24) & 0xffffff);
		
	}

}
