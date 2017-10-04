/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.enocean;

import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.actions.Toggle;
import org.eclipse.om2m.sdt.home.devices.Light;
import org.eclipse.om2m.sdt.home.driver.Utils;
import org.eclipse.om2m.sdt.home.enocean.Activator.EnOceanSDTDevice;
import org.eclipse.om2m.sdt.home.modules.BinarySwitch;
import org.eclipse.om2m.sdt.home.modules.Colour;
import org.eclipse.om2m.sdt.home.modules.ColourSaturation;
import org.eclipse.om2m.sdt.home.modules.FaultDetection;
import org.eclipse.om2m.sdt.home.modules.RunMode;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.enocean.EnOceanDevice;
import org.osgi.service.enocean.EnOceanMessage;

@SuppressWarnings("rawtypes")
public class EOLight extends Light implements EnOceanSDTDevice {

	private EnOceanDevice eoDevice;
	private Domain domain;
	private List<ServiceRegistration> registrations;
	private BundleContext context;

	public EOLight(EnOceanDevice device, Domain domain, BundleContext context) {
		super(Integer.toString(device.getChipId()), "0x" + Integer.toHexString(device.getChipId()), domain);
		setDeviceManufacturer("0x" + Integer.toHexString(device.getManufacturer()));
		this.domain = domain;
		this.eoDevice = device;
		this.context = context;

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
			Activator.logger.warning("Error addRunMode", e);
		}
		try {
			addColour();
		} catch (Exception e) {
			Activator.logger.warning("Error addColour", e);
		}
		try {
			addColourSaturation();
		} catch (Exception e) {
			Activator.logger.warning("Error addColourSaturation", e);
		}
	}

	@Override
	public void handleEvent(EnOceanMessage data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void register() {
		registrations = Utils.register(this, context);
	}

	@Override
	public void unregister() {
		for (ServiceRegistration reg : registrations) {
			reg.unregister();
		}
	}

	private void addBinarySwitch() {
		BinarySwitch binarySwitch = new BinarySwitch("BinarySwitch_" + eoDevice.getChipId(), 
			domain,
			new BooleanDataPoint(DatapointType.powerState) {
				@Override
				public Boolean doGetValue() throws DataPointException {
					return false;
				}
		});
		binarySwitch.setToggle(new Toggle("toggle_" + eoDevice.getChipId()) {
			@Override
			protected void doToggle() throws ActionException {
			}
		});
		addModule(binarySwitch);
	}

	private void addFaultDetection() {
		FaultDetection faultDetection = new FaultDetection("FaultDetection_" + eoDevice.getChipId(), 
			domain,
			new BooleanDataPoint(DatapointType.status) {
				@Override
				public Boolean doGetValue() throws DataPointException {
					return false;
				}
		});
		addModule(faultDetection);
	}

//	private void addRunState() {
//		RunState runState = new RunState("RunState_" + eoDevice.getChipId(), domain, 
//			new JobStates(new EnumDataPoint<Integer>(null) {
//				@Override
//				public void doSetValue(Integer val) throws DataPointException {
//					throw new DataPointException("Not implemented");
//				}
//				@Override
//				public Integer doGetValue() throws DataPointException {
//					return null;
//				}
//			}), 
//			new ArrayDataPoint<Integer>(DatapointType.jobStates) {
//				@Override
//				public List<Integer> doGetValue() throws DataPointException {
//					return null;
//				}
//			},
//			new MachineState(new EnumDataPoint<Integer>(null) {
//				@Override
//				public void doSetValue(Integer val) throws DataPointException {
//					throw new DataPointException("Not implemented");
//				}
//				@Override
//				public Integer doGetValue() throws DataPointException {
//					return null;
//				}
//			}), 
//			new ArrayDataPoint<Integer>(DatapointType.machineStates) {
//				@Override
//				public List<Integer> doGetValue() throws DataPointException {
//					return null;
//				}
//			});
//		addModule(runState);
//	}

	private void addRunMode() {
		RunMode runMode = new RunMode("RunMode_" + eoDevice.getChipId(), domain, 
			new ArrayDataPoint<String>(DatapointType.operationMode) {
				@Override
				public void doSetValue(List<String> values) throws DataPointException {
					throw new DataPointException("Not implemented");
				}
				@Override
				public List<String> doGetValue() throws DataPointException {
					return null;
				}
			}, 
			new ArrayDataPoint<String>(DatapointType.supportedModes) {
				@Override
				public void doSetValue(List<String> value) throws DataPointException {
					throw new DataPointException("Not implemented");
				}
				@Override
				public List<String> doGetValue() throws DataPointException {
					return null;
				}
			});
		addModule(runMode);
	}

	private void addColour() {
		Colour colour = new Colour("colour_" + eoDevice.getChipId(), domain, 
			new IntegerDataPoint(DatapointType.red) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return null;
				}
			}, 
			new IntegerDataPoint(DatapointType.green) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return null;
				}
			}, 
			new IntegerDataPoint(DatapointType.blue) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return null;
				}
			});
		addModule(colour);
	}

	private void addColourSaturation() {
		ColourSaturation colourSaturation = new ColourSaturation("colourSaturation_" + eoDevice.getChipId(), 
			domain,
			new IntegerDataPoint(DatapointType.colourSat) {
				@Override
				public void doSetValue(Integer value) throws DataPointException {
				}
				@Override
				public Integer doGetValue() throws DataPointException {
					return null;
				}
			});
		addModule(colourSaturation);
	}

	@Override
	public Integer getChipId() {
		return eoDevice.getChipId();
	}

}
