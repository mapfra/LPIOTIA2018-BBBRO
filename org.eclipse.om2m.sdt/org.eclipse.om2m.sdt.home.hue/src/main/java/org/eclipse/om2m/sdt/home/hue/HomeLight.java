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
package org.eclipse.om2m.sdt.home.hue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.hue.api.HueLightDevice;
import org.eclipse.om2m.hue.api.types.AlertMode;
import org.eclipse.om2m.hue.api.types.LightEffect;
import org.eclipse.om2m.hue.api.types.LightState;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.Toggle;
import org.eclipse.om2m.sdt.home.devices.Light;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.Brightness;
import org.eclipse.om2m.sdt.home.modules.Colour;
import org.eclipse.om2m.sdt.home.modules.ColourSaturation;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.RunMode;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.device.Constants;

@SuppressWarnings("rawtypes")
public class HomeLight extends Light {
	
	static private final int RED = 0;
	static private final int GREEN = 1;
	static private final int BLUE = 2;

	static private final String SRED = DatapointType.red.getShortName();
	static private final String SGREEN = DatapointType.green.getShortName();
	static private final String SBLUE = DatapointType.blue.getShortName();

	private HueLightDevice hueLight;
	private Domain domain;
	private List<ServiceRegistration> registrations;

	public HomeLight(HueLightDevice device, Domain domain) {
		super(device.getId(), 
				(String) device.getProperties().get(Constants.DEVICE_SERIAL),
				domain);
		this.domain = domain;
		this.hueLight = device;

		try {
			addBinarySwitch();
		} catch (Exception e) {
			Activator.logger.warning("Error addBinarySwitch", e);
		}
		try {
			addFaultDetection();
		} catch (Exception e) {
			Activator.logger.warning("Error addFaultDetection", e);
		}
		try {
//			addRunState();
			addRunMode();
		} catch (Exception e) {
			Activator.logger.warning("Error addRunState", e);
		}
		try {
			addColour();
		} catch (Exception e) {
			Activator.logger.warning("Error addLight", e);
		}
		try {
			addColourSaturation();
		} catch (Exception e) {
			Activator.logger.warning("Error addLight", e);
		}
		//JW 2018.04.16
		try {
			addBrightness();
		} catch (Exception e) {
			Activator.logger.warning("addBrightness", e);
		}
	}

	public void register(BundleContext context) {
		registrations = Utils.register(this, context);
	}

	void unregister() {
		for (ServiceRegistration reg : registrations) {
			reg.unregister();
		}
		domain.removeDevice(getName());
	}

	private void addBinarySwitch() {
		BinarySwitch binarySwitch = new BinarySwitch("BinarySwitch_" + getId(), domain,
			new BooleanDataPoint(DatapointType.powerState) {
				@Override
				public void doSetValue(Boolean v) throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					hueLight.setState(new LightState(v));
				}
				@Override
				public Boolean doGetValue() throws DataPointException {
					return hueLight.isReachable() && hueLight.isOn();
				}
			});
		binarySwitch.setToggle(new Toggle("toggle") {
			@Override
			protected void doToggle() throws ActionException {
				if (! hueLight.isReachable()) {
					throw new ActionException("Not reachable");
				}
				boolean on = hueLight.isOn();
				hueLight.setState(new LightState(!on));
			}
		});
		addModule(binarySwitch);
	}

	private void addFaultDetection() {
		FaultDetection faultDetection = new FaultDetection("FaultDetection_" + getId(), domain,
			new BooleanDataPoint(DatapointType.status) {
				@Override
				public Boolean doGetValue() throws DataPointException {
					return ! hueLight.isReachable();
				}
			});
		addModule(faultDetection);
	}

	private void addRunMode() {
		RunMode runMode = new RunMode("RunMode_" + getId(), domain, 
				new ArrayDataPoint<String>(DatapointType.operationMode) {
			@Override
			public void doSetValue(List<String> values) throws DataPointException {
				if ((values == null) || values.isEmpty())
					return;
				if (! hueLight.isReachable()) {
					throw new DataPointException("Not reachable");
				}
				LightState state = new LightState(hueLight.isOn());
				for (String value : values) {//.split(",")) {
					if (value.equals("effect.none"))
						state.setEffect(LightEffect.NONE);
					else if (value.equals("effect.colorloop"))
						state.setEffect(LightEffect.COLORLOOP);
					else if (value.equals("alert.none"))
						state.setAlert(AlertMode.NONE);
					else if (value.equals("alert.lselect"))
						state.setAlert(AlertMode.L_SELECT);
					else if (value.equals("alert.select"))
						state.setAlert(AlertMode.SELECT);
				}
				hueLight.setState(state);
			}

			@Override
			public List<String> doGetValue() throws DataPointException {
				if (!hueLight.isReachable()) {
					throw new DataPointException("Not reachable");
				}
				LightState state = hueLight.getState();
				return Arrays.asList("effect." + LightEffect.getLightEffect(state.getEffect()),
						"alert." + AlertMode.getAlertMode(state.getAlert()));
			}
		}, 
		new ArrayDataPoint<String>(DatapointType.supportedModes) {
			@Override
			public void doSetValue(List<String> value) throws DataPointException {
				throw new DataPointException("Not implemented");
			}

			@Override
			public List<String> doGetValue() throws DataPointException {
				return Arrays.asList("effect.none", "effect.colorloop",
						"alert.none", "alert.lselect", "alert.select");
			}
		});
		addModule(runMode);
	}

//	private void addRunState() {
//		RunState runState = new RunState("RunState_" + getId(), domain, 
//			new JobStates(new EnumDataPoint<Integer>(null) {
//				@Override
//				public void doSetValue(Integer val) throws DataPointException {
//					throw new DataPointException("Not implemented");
//				}
//				@Override
//				public Integer doGetValue() throws DataPointException {
//					LightState state = hueLight.getState();
//					int effect = state.getEffect();
//					if (effect == LightEffect.COLORLOOP)
//						return JobStates.colorloop;
//					int alert = state.getAlert();
//					if (alert == AlertMode.L_SELECT)
//						return JobStates.lselect;
//					if (alert == AlertMode.SELECT)
//						return JobStates.select;
//					return JobStates.noeffect;
//				}
//			}), 
//			new ArrayDataPoint<Integer>(DatapointType.jobStates) {
//				@Override
//				public List<Integer> doGetValue() throws DataPointException {
//					return Arrays.asList(JobStates.noeffect, JobStates.colorloop, 
//							JobStates.noalert, JobStates.lselect, JobStates.select);
//				}
//			},
//			new MachineState(new EnumDataPoint<Integer>(null) {
//				@Override
//				public void doSetValue(Integer val) throws DataPointException {
//					throw new DataPointException("Not implemented");
//				}
//				@Override
//				public Integer doGetValue() throws DataPointException {
//					if (! hueLight.isReachable())
//						return MachineState.error;
//					return hueLight.isOn() ? MachineState.active : MachineState.stopped;
//				}
//			}), 
//			new ArrayDataPoint<Integer>(DatapointType.machineStates) {
//				@Override
//				public List<Integer> doGetValue() throws DataPointException {
//					return Arrays.asList(MachineState.error, 
//							MachineState.active, MachineState.stopped);
//				}
//			});
//		addModule(runState);
//	}

	private void addColour() {
		Colour colour = new Colour("colour_" + getId(), domain, 
			new IntegerDataPoint(DatapointType.red) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					setColor(RED, value);
				}
	
				@Override
				public Integer doGetValue() throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					return getColor(RED);
				}
			}, 
			new IntegerDataPoint(DatapointType.green) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					setColor(GREEN, value);
				}
	
				@Override
				public Integer doGetValue() throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					return getColor(GREEN);
				}
			}, 
			new IntegerDataPoint(DatapointType.blue) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					setColor(BLUE, value);
				}
	
				@Override
				public Integer doGetValue() throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					return getColor(BLUE);
				}
			});
		colour.setDatapointHandler(new Module.DatapointHandler() {
			@Override
			public void setValues(Map<String, Object> values)
					throws DataPointException, AccessException {
				setColors((Integer) values.get(SRED),
						(Integer) values.get(SGREEN),
						(Integer) values.get(SBLUE));
			}

			@Override
			public Map<String, Object> getValues(List<String> names)
					throws DataPointException, AccessException {
				int[] colors = getColors();
				Map<String, Object> ret = new HashMap<String, Object>();
				for (String s : names) {
					if (s.equals(SRED)) ret.put(SRED, colors[RED]);
					else if (s.equals(SGREEN)) ret.put(SGREEN, colors[GREEN]);
					else if (s.equals(SBLUE)) ret.put(SBLUE, colors[BLUE]);
				}
				return ret;
			}
		});
		addModule(colour);
	}

	private void addColourSaturation() {
		ColourSaturation colourSaturation = new ColourSaturation("colourSaturation_" + getId(), domain,
			new IntegerDataPoint(DatapointType.colourSat) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					// get current state
					LightState currentLightState = hueLight.getState();
					// set saturation
					currentLightState.setSat((int) ((double) value * 2.55d));
					hueLight.setState(currentLightState);
				}
	
				@Override
				public Integer doGetValue() throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					// get current state
					LightState currentLightState = hueLight.getState();
					return (int) ((double) currentLightState.getSat() / 2.55d);
				}
			});
		addModule(colourSaturation);
	}
	
	// JW 2018.04.16
	private void addBrightness() {
		
		Brightness brightness = new Brightness("brightness_" + getId(), domain,
			new IntegerDataPoint(DatapointType.brightness) {
			
				@Override
				public void doSetValue(Integer value) throws DataPointException {
					
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					LightState currentLightState = hueLight.getState();
					currentLightState.setBri((int) ((double) value * 2.55d));
					hueLight.setState(currentLightState);
					
				}

				@Override
				protected Integer doGetValue() throws DataPointException {
					if (! hueLight.isReachable()) {
						throw new DataPointException("Not reachable");
					}
					LightState currentLightState = hueLight.getState();
					return (int) ((double) currentLightState.getBri() / 2.55d);
				}
		});
		addModule(brightness);
	}

	private void setColor(int colorIndex, int colorValue) {
		// get current state
		LightState currentLightState = hueLight.getState();
		int h = Math.round(((float) currentLightState.getHue()) / 65565f * 360f);
		int s = Math.round(((float) currentLightState.getBri()) / 255f * 100f );
		int v = Math.round(((float) currentLightState.getSat()) / 255f * 100f);

		// convert current color in rgb
		int[] rgb = HSVtoRGB(h, s, v);

		// update red value
		rgb[colorIndex] = colorValue;

		// compute HSV
		int[] hsv = RGBtoHSB(rgb[RED], rgb[GREEN], rgb[BLUE]);

		// set new state
		LightState state = new LightState(hueLight.isOn());
		state.setHue((int) ((float)hsv[0] /360 * 65535f));
		state.setSat((int) ((float)hsv[2] * 2.55f));
		state.setBri((int) ((float)hsv[1] * 2.55f));

		hueLight.setState(state);
	}

	private void setColors(Integer red, Integer green, Integer blue) {
		// get current state
		LightState currentLightState = hueLight.getState();
		//int h = Math.round(((float) currentLightState.getHue()) / 65565f * 360f);
		int h = Math.round(((float) currentLightState.getHue()) / 65535f * 360f);
		int s = Math.round(((float) currentLightState.getBri()) / 255f * 100f );
		int v = Math.round(((float) currentLightState.getSat()) / 255f * 100f);

		// convert current color in rgb
		int[] rgb = HSVtoRGB(h, s, v);

		// update values
		if (red != null)
			rgb[RED] = red;
		if (green != null)
			rgb[GREEN] = green;
		if (blue != null)
			rgb[BLUE] = blue;

		// compute HSV
		int[] hsv = RGBtoHSB(rgb[RED], rgb[GREEN], rgb[BLUE]);

		// set new state
		LightState state = new LightState(hueLight.isOn());
		state.setHue((int) ((float)hsv[0] /360 * 65535f));
		// JW 2016.04.18
		// state.setSat((int) ((float)hsv[2] * 2.55f));
		// state.setBri((int) ((float)hsv[1] * 2.55f));
		state.setSat((int) ((float)hsv[1] * 2.55f));
		state.setBri((int) ((float)hsv[2] * 2.55f));

		hueLight.setState(state);
	}

	private Integer getColor(int colorIndex) {
		// get current state
		LightState currentLightState = hueLight.getState();
		int h = Math.round(((float)currentLightState.getHue()) / 65535f * 360f);
		// JW 2018.04.18
		// int s = Math.round(((float) currentLightState.getBri()) / 255f * 100f);
		// int v = Math.round(((float) currentLightState.getSat()) / 255f * 100f);
		int v = Math.round(((float) currentLightState.getBri()) / 255f * 100f);
		int s = Math.round(((float) currentLightState.getSat()) / 255f * 100f);

		// convert current color in rgb
		int[] rgb = HSVtoRGB(h, s, v);
		return new Integer(rgb[colorIndex]);
	}

	private int[] getColors() {
		// get current state
		LightState currentLightState = hueLight.getState();
		int h = Math.round(((float)currentLightState.getHue()) / 65535f * 360f);
		// JW 2018.04.18
		// int s = Math.round(((float) currentLightState.getBri()) / 255f * 100f);
		// int v = Math.round(((float) currentLightState.getSat()) / 255f * 100f);
		int v = Math.round(((float) currentLightState.getBri()) / 255f * 100f);
		int s = Math.round(((float) currentLightState.getSat()) / 255f * 100f);

		// convert current color in rgb
		return HSVtoRGB(h, s, v);
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
		float hue = 0, saturation = 0;
		int[] hsbvals = new int[3];

		int cmax = (r > g) ? r : g;
		if (b > cmax)
			cmax = b;
		int cmin = (r < g) ? r : g;
		if (b < cmin)
			cmin = b;

		float brightness = ((float) cmax) / 255.0f;
		if (cmax != 0)
			saturation = ((float) (cmax - cmin)) / ((float) cmax);
		if (saturation != 0) {
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
