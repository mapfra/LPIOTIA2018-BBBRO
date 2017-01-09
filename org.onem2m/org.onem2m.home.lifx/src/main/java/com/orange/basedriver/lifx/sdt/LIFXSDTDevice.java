/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.sdt;

import org.onem2m.home.devices.Light;
import org.onem2m.home.modules.BinarySwitch;
import org.onem2m.home.modules.Colour;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.DataPointException;

import com.orange.basedriver.lifx.LIFXDevice;

public class LIFXSDTDevice extends Light {
	
	private final LIFXDevice lifxDevice;
	
	public LIFXSDTDevice(Domain domain, LIFXDevice pLIFXDevice) {
		super(computeLifxDeviceId(pLIFXDevice), computeLifxDeviceSerial(pLIFXDevice), domain);
		this.lifxDevice = pLIFXDevice;
		
		setDeviceAliasName("LIFX Color Bubble");
		setDeviceManufacturer("LIFX");
		setDeviceModelName("Color 1000");
		try {
			
			setDeviceName(pLIFXDevice.getLabel());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setProtocol("LIFX");
		
		// binary switch module
		BinarySwitch binarySwitch = new BinarySwitch(getSerialNumber() + "_BinarySwitch", domain, new BooleanDataPoint("powerState") {
			
			@Override
			protected Boolean doGetValue() throws DataPointException {
				int lifxPower;
				try {
					lifxPower = lifxDevice.getPower(false);
				} catch (Exception e) {
					throw new DataPointException("Error when retrieving power state:" + e.getMessage());
				}
				// at this point, we are sure 
				if (lifxPower == 0) {
					// off
					return false;
				} else {
					// on
					return true;
				}
				
			}
			
			@Override
			protected void doSetValue(Boolean value) throws DataPointException {
				try {
					lifxDevice.setPower((value ? 65535 : 0), 0);
				} catch (Exception e) {
					throw new DataPointException("Error when setting power state:" + e.getMessage());
				}
			}
		});
		addModule(binarySwitch);
		
		Colour colourModule = new Colour(getSerialNumber() + "_Colour", domain, new IntegerDataPoint("red") {
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				int color;
				try {
					color = getColor(0);
				} catch (Exception e) {
					throw new DataPointException(e.getMessage());
				}
				return color;
			}
			
			@Override
			protected void doSetValue(Integer value) throws DataPointException {
				try {
					setColor(0, value);
				} catch (Exception e) {
					throw new DataPointException(e.getMessage());
				}
			}
		},  new IntegerDataPoint("green") {
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				int color;
				try {
					color = getColor(1);
				} catch (Exception e) {
					throw new DataPointException(e.getMessage());
				}
				return color;
			}
			
			@Override
			protected void doSetValue(Integer value) throws DataPointException {
				try {
					setColor(1, value);
				} catch (Exception e) {
					throw new DataPointException(e.getMessage());
				}
			}
			
		},  new IntegerDataPoint("blue") {
			
			@Override
			protected Integer doGetValue() throws DataPointException {
				int color;
				try {
					color = getColor(2);
				} catch (Exception e) {
					throw new DataPointException(e.getMessage());
				}
				return color;
			}
			
			@Override
			protected void doSetValue(Integer value) throws DataPointException {
				try {
					setColor(2, value);
				} catch (Exception e) {
					throw new DataPointException(e.getMessage());
				}
			}
		});
		
		addModule(colourModule);
		
		
	}


	private static String computeLifxDeviceId(LIFXDevice pLIFXDevice) {
		return pLIFXDevice.getId().replaceAll(":", "_");
	}


	private static String computeLifxDeviceSerial(LIFXDevice pLIFXDevice) {
		return pLIFXDevice.getId().replaceAll(":", "_");
	}

	
	private void setColor(int colorIndex, int colorValue) throws Exception {
		// get current state
		int h = Math.round((float)(lifxDevice.getHue() / 65565d * 360d));
		int s = Math.round((float)  (lifxDevice.getSaturation() / 65535d * 100d));
		int v = Math.round((float) (lifxDevice.getBrightness() / 65535d * 100d));

		// convert current color in rgb
		int[] rgb = HSVtoRGB(h, s, v);

		// update red value
		rgb[colorIndex] = colorValue;

		// compute HSV
		int[] hsv = RGBtoHSB((int) rgb[0], (int) rgb[1], (int) rgb[2]);

		// set new state
		double newHue = ((double)hsv[0]) / 360d * 65535d;
		double newSaturation = ((double) hsv[1]) / 100d * 65535d;
		double newBrightness = ((double) hsv[2]) / 100d * 65535d;
		
		lifxDevice.setLightState(65535, newHue, newSaturation, lifxDevice.getKelvin(), newBrightness, 0);
	}

	private Integer getColor(int colorIndex) throws Exception {
		// get current state
		int h = Math.round((float)(lifxDevice.getHue() / 65565d * 360d));
		int s = Math.round((float)  (lifxDevice.getSaturation() / 65535d * 100d));
		int v = Math.round((float) (lifxDevice.getBrightness() / 65535d * 100d));

		// convert current color in rgb
		int[] rgb = HSVtoRGB(h, s, v);
		return new Integer(rgb[colorIndex]);
	}

	/**
	 * Convert RGB color to HSV color
	 * 
	 * @param r
	 *            red. between 0 and 255
	 * @param g
	 *            green. between 0 and 255
	 * @param b
	 *            blue. between 0 and 255
	 * @return hsv color (index 0: h between 0 and 360; index 1: s between 0 and
	 *         100, index 2: v between 0 and 100)
	 */
	protected static int[] RGBtoHSB(int r, int g, int b) {
		float hue, saturation, brightness;
		int[] hsbvals = new int[3];

		int cmax = (r > g) ? r : g;
		if (b > cmax)
			cmax = b;
		int cmin = (r < g) ? r : g;
		if (b < cmin)
			cmin = b;

		brightness = ((float) cmax) / 255.0f;
		if (cmax != 0)
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		else
			saturation = 0;
		if (saturation == 0)
			hue = 0;
		else {
			float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
			float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
			float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
			if (r == cmax)
				hue = bluec - greenc;
			else if (g == cmax)
				hue = 2.0f + redc - bluec;
			else
				hue = 4.0f + greenc - redc;
			hue = hue / 6.0f;
			if (hue < 0)
				hue = hue + 1.0f;
		}
		hsbvals[0] = Math.round(hue * 360f);
		hsbvals[1] = Math.round(saturation * 100f);
		hsbvals[2] = Math.round(brightness * 100f);

		return hsbvals;
	}

	/**
	 * Convert HSV color into RGB color
	 * 
	 * @param h
	 *            hue. between 0 and 360 (degres)
	 * @param s
	 *            saturation. between 0 and 100 (%)
	 * @param v
	 *            brighness. between 0 and 100 (%)
	 * @return rgb color (index 0: r between 0 and 255; index 1: g between 0 and
	 *         255; index 2 : b between 0 255).
	 */
	protected static int[] HSVtoRGB(int h, int s, int v) {
		float hh, p, q, t, ff;
		float i;
		int[] out = new int[3];
		float r, g, b, hFloat, sFloat, vFloat;

		hFloat = ((float) h) / 360f;
		sFloat = ((float) s) / 100f;
		vFloat = ((float) v) / 100f;

		if (s <= 0.0) { // < is bogus, just shuts up warnings
			out[0] = (int) (vFloat * 255);
			out[1] = (int) (vFloat * 255);
			out[2] = (int) (vFloat * 255);
			return out;
		}

		hh = hFloat * 6;
		if (hh == 6)
			hh = 0.0f;
		i = (float) Math.floor(hh);
		ff = hh - i;
		p = v * (1.0f - sFloat);
		q = v * (1.0f - (sFloat * ff));
		t = v * (1.0f - (sFloat * (1.0f - ff)));

		switch ((int) i) {
		case 0:
			r = v;
			g = (float) t;
			b = (float) p;
			break;
		case 1:
			r = (float) q;
			g = v;
			b = (float) p;
			break;
		case 2:
			r = (float) p;
			g = v;
			b = (float) t;
			break;

		case 3:
			r = (float) p;
			g = (float) q;
			b = v;
			break;
		case 4:
			r = (float) t;
			g = (float) p;
			b = v;
			break;
		case 5:
		default:
			r = v;
			g = (float) p;
			b = (float) q;
			break;
		}

		out[0] = Math.round(r * 2.55f);
		out[1] = Math.round(g * 2.55f);
		out[2] = Math.round(b * 2.55f);

		return out;
	}
	
	

	
	

}
