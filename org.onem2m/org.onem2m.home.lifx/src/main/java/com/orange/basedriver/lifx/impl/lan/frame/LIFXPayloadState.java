/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl.lan.frame;

import java.util.Arrays;

public class LIFXPayloadState extends LIFXPayload {

	protected static final int TYPE = 107;

	// HSBK
	private int hue;
	private int saturation;
	private int brightness;
	private int kelvin;

	private int power /* between 0 and 65535 */;
	private String label;

	@Override
	public int getType() {
		return TYPE;
	}

	public int getHue() {
		return hue;
	}

	public int getSaturation() {
		return saturation;
	}

	public int getBrightness() {
		return brightness;
	}

	public int getKelvin() {
		return kelvin;
	}

	public int getPower() {
		return power;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public void unmarshal() throws Exception {
		// color (64 bits)
		hue = ((payload[1] << 8) & 0xffff) + (payload[0] & 0xff);
		saturation = ((payload[3] << 8) & 0xffff) + (payload[2] & 0xff);
		brightness = ((payload[5] << 8) & 0xffff) + (payload[4] & 0xff);
		kelvin = ((payload[7] << 8) & 0xffff) + (payload[6] & 0xff);

		// reserved (16 bits) => index 8 & 9

		// power (16 bits)
		power = ((payload[11] << 8) & 0xffff) + (payload[10] & 0xff);

		// label (32 bytes)
		label = new String(Arrays.copyOfRange(payload, 12, 44));

		// reserved (64 bits)

	}

	@Override
	public String toString() {
		return "State(color={hue=" + hue + ", saturation=" + saturation + ", brightness=" + brightness + ", kelvin="
				+ kelvin + "}, power=" + power + ", label=" + label + ")";
	}

}
