/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.cloud;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.FilterUsage;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.URIList;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.Arg;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.args.Command;
import org.eclipse.om2m.sdt.args.ValuedArg;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.ByteDataPoint;
import org.eclipse.om2m.sdt.datapoints.DateDataPoint;
import org.eclipse.om2m.sdt.datapoints.DateTimeDataPoint;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.datapoints.TimeDataPoint;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.Camera;
import org.eclipse.om2m.sdt.home.devices.GenericDevice;
import org.eclipse.om2m.sdt.home.modules.AlarmSpeaker;
import org.eclipse.om2m.sdt.home.types.AlertColourCode;
import org.eclipse.om2m.sdt.home.types.DoorState;
import org.eclipse.om2m.sdt.home.types.FoamStrength;
import org.eclipse.om2m.sdt.home.types.LevelType;
import org.eclipse.om2m.sdt.home.types.LockState;
import org.eclipse.om2m.sdt.home.types.SupportedMode;
import org.eclipse.om2m.sdt.home.types.TasteStrength;
import org.eclipse.om2m.sdt.home.types.Tone;
import org.eclipse.om2m.sdt.types.DataType;
import org.eclipse.om2m.sdt.types.SimpleType;

public class ResourceDiscovery {

	private static final String RESOURCE_ID = "SDT_CLOUD";
	private static final String MN_CSE_NAME = "SDT_IPE_Annc";
	private static final String SEP = "/";

	// SEARCHSTRING
	private static final String RESOURCE_ID_SEARCH_STRING = "ResourceID/";
	private static final String RESOURCE_TYPE_APPLICATION_SEARCH_STRING = "ResourceType/Application";

	public static CseService cseService;
	
	public static void initCseService(CseService pCseService) {
		cseService = pCseService;
	}

	public static String validateUserCredentials(String name, String password) {
		RequestPrimitive request = new RequestPrimitive();
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setFrom(name + ":" + password);
		request.setFilterCriteria(new FilterCriteria());
		request.setOperation(Operation.RETRIEVE);
		request.getFilterCriteria().setFilterUsage(
				FilterUsage.DISCOVERY_CRITERIA);
		request.getFilterCriteria().getLabels()
				.add(RESOURCE_ID_SEARCH_STRING + RESOURCE_ID);
		request.getFilterCriteria().getLabels()
				.add(RESOURCE_TYPE_APPLICATION_SEARCH_STRING);
		request.setTargetId(SEP + Constants.CSE_ID + SEP + Constants.CSE_NAME);
		ResponsePrimitive response = cseService.doRequest(request);
		if (! ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			return null;
		}
		URIList uriList = (URIList) response.getContent();
		return (uriList.getListOfUri().size() <= 0) ? null // KO
				: uriList.getListOfUri().get(0);
	}

	static public List<String> readDeviceURIs() throws Exception {
		RequestPrimitive request = new RequestPrimitive();
//		http://127.0.0.1:8081/om2m//mn-cse/mn-name/in-name/SDT_IPE_Annc?rcn=7
		request.setTargetId(SEP + Constants.CSE_ID + SEP + Constants.CSE_NAME 
			+ SEP + Constants.REMOTE_CSE_NAME + SEP + MN_CSE_NAME);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.RETRIEVE);
		request.setFilterCriteria(new FilterCriteria());
		request.getFilterCriteria().setFilterUsage(FilterUsage.DISCOVERY_CRITERIA);
		request.getFilterCriteria().getLabels().add("object.type/device");
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
//		Activator.logger.info(request.toString(), ResourceDiscovery.class);

		ResponsePrimitive response = cseService.doRequest(request);
		if (! ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			throw new Exception("Could not read devices list: " + response);
		}
		return ((URIList) response.getContent()).getListOfUri();
//		for (String uri : ((URIList) response.getContent()).getListOfUri()) {
//			try {
//				GenericDevice device = readDevice(uri);
//				ret.put(device.getId(), device);
//			} catch (Exception e) {
//				Activator.logger.error("Error reading device " + uri, e);
//			}
//		}
//		return ret;
	}

	static public GenericDevice readDevice(String uri) throws Exception {
		Activator.logger.info("Get device " + uri);
		FlexContainer deviceFlexContainer = (FlexContainer) retrieveFlexContainer(uri,
				ResultContent.ORIGINAL_RES);
		if (deviceFlexContainer == null) {
			throw new Exception("Could not read device " + uri);
		}
		Activator.logger.info("Got device " + deviceFlexContainer);
		Map<String, String> labels = new HashMap<String, String>();
		for (String label : deviceFlexContainer.getLabels()) {
			int idx = label.indexOf('/');
			if (idx > 0)
				labels.put(label.substring(0, idx), label.substring(idx+1));
		}
		String deviceId = labels.get("id");
		CustomAttribute serialAttr = deviceFlexContainer.getCustomAttribute("propDeviceSerialNum");
		String serial = null;
		if (serialAttr == null) {
			Activator.logger.info("No serial number property. Take id instead.");
			serial = deviceId;
		} else {
			serial = serialAttr.getCustomAttributeValue();
		}
		GenericDevice device = (GenericDevice) Activator.DOMAIN.getDevice(deviceId);
		if (device == null) {
			String cntDef = deviceFlexContainer.getContainerDefinition();
			if (deviceId.startsWith(cntDef + "__")) {
				deviceId = deviceId.substring(cntDef.length() + 2);
			}
			String devName = cntDef.substring(cntDef.lastIndexOf('.') + 1);
			if (devName.toLowerCase().startsWith("device"))
				devName = devName.substring(6); // remove "device" prefix
			devName = Character.toUpperCase(devName.charAt(0)) + devName.substring(1);
			String className = Camera.class.getPackage().getName() + "." + devName;
			Class<?> clazz = Class.forName(className);
			device = (GenericDevice) clazz.getConstructor(String.class, String.class, Domain.class)
					.newInstance(deviceId, serial, Activator.DOMAIN);
			Activator.logger.info("Created SDT device " + device);
		}
		for (CustomAttribute attr : deviceFlexContainer.getCustomAttributes()) {
			device.setProperty(attr.getCustomAttributeName(),
					attr.getCustomAttributeValue(),
					attr.getCustomAttributeType());
		}
		// Search children resources: modules
		FlexContainerAnnc ctr = (FlexContainerAnnc)retrieveFlexContainer(uri, ResultContent.ATTRIBUTES_AND_CHILD_REF);
		for (ChildResourceRef ref : ctr.getChildResource()) {
			if (ref.getType().equals(BigInteger.valueOf(ResourceType.FLEXCONTAINER_ANNC))) {
				Module module = readModule(ref.getValue());
				if (device.getModule(module.getName()) == null) {
					Activator.logger.info("Add new module " + module);
					device.addModule(module); // module URI
				} else {
					Activator.logger.info("Already present module " + module);
				}
			}
		}
		Activator.logger.info("Full SDT device " + device);//.prettyPrint());
		return device;
	}

	private static Module readModule(String uri) throws Exception {
		Activator.logger.info("Get module " + uri);
		FlexContainer moduleFlexContainer = (FlexContainer) retrieveFlexContainer(uri,
				ResultContent.ORIGINAL_RES);
		if (moduleFlexContainer == null) {
			throw new Exception("Could not read module " + uri);
		}
		Activator.logger.info("Got module " + moduleFlexContainer);
		Map<String,String> labels = new HashMap<String,String>();
		for (String label : moduleFlexContainer.getLabels()) {
			int idx = label.indexOf('/');
			if (idx > 0)
				labels.put(label.substring(0, idx), label.substring(idx+1));
		}
		List<Property> props = new ArrayList<Property>();
		for (CustomAttribute attr : moduleFlexContainer.getCustomAttributes()) {
			if (attr.getCustomAttributeName().startsWith("prop")) {
				Property prop = new Property(attr.getCustomAttributeName(), attr.getCustomAttributeName(), 
						attr.getCustomAttributeValue());
				prop.setType(SimpleType.getSimpleType(attr.getCustomAttributeType()));
				props.add(prop);
			}
		}
		String modName = labels.get("name");
		Module module = (Module) Activator.DOMAIN.getModule(modName);
		if (module != null) {
			for (Property prop : props) {
				module.setProperty(prop.getName(), prop.getShortName(), prop.getValue());
			}
			Activator.logger.info("Full retrieved SDT module " + module);//.prettyPrint());
			return module;
		}
		// Case new module
		String cntDef = moduleFlexContainer.getContainerDefinition();
		int idx = cntDef.lastIndexOf('.') + 1;
		String className = AlarmSpeaker.class.getPackage().getName() + "." 
				+ Character.toUpperCase(cntDef.charAt(idx)) 
				+ cntDef.substring(idx + 1);
		List<DataPoint> dps = new ArrayList<DataPoint>();
		for (CustomAttribute attr : moduleFlexContainer.getCustomAttributes()) {
			String type = attr.getCustomAttributeType();
			if (! attr.getCustomAttributeName().startsWith("prop")) {
				switch (type) {
				case "xs:integer": dps.add(getIntegerDataPoint(attr, uri)); break;
				case "xs:boolean": dps.add(getBooleanDataPoint(attr, uri)); break;
				case "xs:string": dps.add(getStringDataPoint(attr, uri)); break;
				case "xs:byte": dps.add(getByteDataPoint(attr, uri)); break;
				case "xs:float": dps.add(getFloatDataPoint(attr, uri)); break;
				case "xs:datetime": dps.add(getDateTimeDataPoint(attr, uri)); break;
				case "xs:time": dps.add(getTimeDataPoint(attr, uri)); break;
				case "xs:date": dps.add(getDateDataPoint(attr, uri)); break;
				case "xs:enum": dps.add(getArrayDataPoint(attr, uri)); break;
				case "hd:alertColourCode": dps.add(getAlertColourCode(attr, uri)); break;
				case "hd:doorState": dps.add(getDoorState(attr, uri)); break;
				case "hd:foamStrength": dps.add(getFoamStrength(attr, uri)); break;
				case "hd:level": dps.add(getLevel(attr, uri)); break;
				case "hd:lockState": dps.add(getLockState(attr, uri)); break;
				case "hd:supportedMode": dps.add(getSupportedMode(attr, uri)); break;
				case "hd:tasteStrength": dps.add(getTasteStrength(attr, uri)); break;
				case "hd:tone": dps.add(getTone(attr, uri)); break;

				default:
					break;
				}
			} 
		}
		Map<String, DataPoint> dpsMap = new HashMap<String, DataPoint>();
		for (DataPoint dp : dps) {
			dpsMap.put(dp.getName(), dp);
		}
		Class<?> clazz = Class.forName(className);
		if (modName.startsWith(cntDef + "__")) {
			modName = modName.substring(cntDef.length() + 2);
		}
		module = (Module) clazz.getConstructor(String.class, Domain.class, Map.class)
				.newInstance(modName, Activator.DOMAIN, dpsMap);
		Activator.logger.info("Created SDT module " + module);
		for (Property prop : props) {
			module.addProperty(prop);
		}
		Activator.logger.info("Full new SDT module " + module);//.prettyPrint());
		// Search children resources: modules
		FlexContainerAnnc ctr = (FlexContainerAnnc)retrieveFlexContainer(uri, ResultContent.ATTRIBUTES_AND_CHILD_REF);
		Activator.logger.info("Children " + ctr.getChildResource());
		for (ChildResourceRef ref : ctr.getChildResource()) {
			if (ref.getType().equals(BigInteger.valueOf(ResourceType.FLEXCONTAINER_ANNC))) {
				try {
					Action action = readAction(ref.getValue(), module);
					if (module.getAction(action.getName()) == null) {
						Activator.logger.info("Add new action " + action);
						module.addAction(action);
					} else {
						Activator.logger.info("Already present action " + action);
					}
				} catch (Exception e) {
					Activator.logger.error("Error creating action " + ref.getValue());
				}
			}
		}
		return module;
	}
	
	private static Action readAction(final String uri, final Module module) throws Exception {
		Activator.logger.info("Get action " + uri);
		FlexContainer actionFlexContainer = (FlexContainer) retrieveFlexContainer(uri,
				ResultContent.ORIGINAL_RES);
		if (actionFlexContainer == null) {
			throw new Exception("Could not read action " + uri);
		}
		Activator.logger.info("Got action " + actionFlexContainer);
		Map<String,String> labels = new HashMap<String,String>();
		for (String label : actionFlexContainer.getLabels()) {
			int idx = label.indexOf('/');
			if (idx > 0)
				labels.put(label.substring(0, idx), label.substring(idx+1));
		}
		List<Arg> args = new ArrayList<Arg>();
		final List<CustomAttribute> attributes = actionFlexContainer.getCustomAttributes();
		for (final CustomAttribute attr : attributes) {
			String type = attr.getCustomAttributeType();
			Arg arg = new ValuedArg<Object>(attr.getCustomAttributeName(), 
					new DataType(type, SimpleType.getSimpleType(type))) {
				public void setValue(Object value) {
					try {
						SDTUtil.setValue(attr, value);
						updateAttribute(uri, attr);
					} catch (Exception e) {
						Activator.logger.warning("Could not set arg", e);
					}
				}
			};
			args.add(arg);
		}
		String cntDef = actionFlexContainer.getContainerDefinition();
		String actionName = labels.get("name");
		if (actionName == null)
			actionName = cntDef.substring(cntDef.lastIndexOf('.') + 1);
		Action action = module.getAction(actionName);
		if (action != null) {
			Activator.logger.info("Full retrieved SDT action " + action);
			return action;
		}
		action = new Command(actionName, cntDef, args, actionFlexContainer.getLongName(), actionFlexContainer.getShortName()) {
			@Override
			protected Object doInvoke() throws ActionException {
				Activator.logger.info("invoke SDT action");
				try {
					return invokeAction(uri, attributes);
				} catch (Exception e) {
					throw new ActionException(e);
				}
			}
		};
//		
		Activator.logger.info("Created SDT action " + action);
		return action;
	}

	private static IntegerDataPoint getIntegerDataPoint(final CustomAttribute attr,
			final String uri) {
		return new IntegerDataPoint(attr.getCustomAttributeName()) {
//			public Integer getValue() throws DataPointException, AccessException {
//				return doGetValue();
//			}
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static BooleanDataPoint getBooleanDataPoint(final CustomAttribute attr,
			final String uri) {
		BooleanDataPoint ret = new BooleanDataPoint(attr.getCustomAttributeName()) {
//			public Boolean getValue() throws DataPointException, AccessException {
//				return doGetValue();
//			}
			@Override
			public void doSetValue(Boolean val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Boolean doGetValue() throws DataPointException {
				try {
					return (Boolean) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
		return ret;
	}
	
	private static StringDataPoint getStringDataPoint(final CustomAttribute attr,
			final String uri) {
		return new StringDataPoint(attr.getCustomAttributeName()) {
//			public String getValue() throws DataPointException, AccessException {
//				return doGetValue();
//			}
			@Override
			public void doSetValue(String val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public String doGetValue() throws DataPointException {
				try {
					return (String) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static ByteDataPoint getByteDataPoint(final CustomAttribute attr,
			final String uri) {
		return new ByteDataPoint(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Byte val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Byte doGetValue() throws DataPointException {
				try {
					return (Byte) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static FloatDataPoint getFloatDataPoint(final CustomAttribute attr,
			final String uri) {
		return new FloatDataPoint(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Float val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Float doGetValue() throws DataPointException {
				try {
					return (Float) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static DateTimeDataPoint getDateTimeDataPoint(final CustomAttribute attr,
			final String uri) {
		return new DateTimeDataPoint(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Date val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Date doGetValue() throws DataPointException {
				try {
					return (Date) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static DateDataPoint getDateDataPoint(final CustomAttribute attr,
			final String uri) {
		return new DateDataPoint(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Date val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Date doGetValue() throws DataPointException {
				try {
					return (Date) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static TimeDataPoint getTimeDataPoint(final CustomAttribute attr,
			final String uri) {
		return new TimeDataPoint(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Date val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Date doGetValue() throws DataPointException {
				try {
					return (Date) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static ArrayDataPoint<String> getArrayDataPoint(final CustomAttribute attr,
			final String uri) {
		return new ArrayDataPoint<String>(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(List<String> val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@SuppressWarnings("unchecked")
			@Override
			public List<String> doGetValue() throws DataPointException {
				try {
					return (List<String>) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static AlertColourCode getAlertColourCode(final CustomAttribute attr,
			final String uri) {
		return new AlertColourCode(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static DoorState getDoorState(final CustomAttribute attr,
			final String uri) {
		return new DoorState(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static LevelType getLevel(final CustomAttribute attr, final String uri) {
		return new LevelType(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static LockState getLockState(final CustomAttribute attr,
			final String uri) {
		return new LockState(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static SupportedMode getSupportedMode(final CustomAttribute attr,
			final String uri) {
		return new SupportedMode(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static Tone getTone(final CustomAttribute attr, final String uri) {
		return new Tone(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static FoamStrength getFoamStrength(final CustomAttribute attr, final String uri) {
		return new FoamStrength(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	private static TasteStrength getTasteStrength(final CustomAttribute attr, final String uri) {
		return new TasteStrength(attr.getCustomAttributeName()) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val);
					updateAttribute(uri, attr);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, attr.getCustomAttributeName());
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}

	private static Object retrieveFlexContainer(String uri, BigInteger resultContent) {
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.RETRIEVE);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
//		request.setFrom(name + ":" + pwd);
		request.setTargetId(uri);
		request.setResultContent(resultContent);
//		Activator.logger.info(request.toString(), ResourceDiscovery.class);

		ResponsePrimitive response = cseService.doRequest(request);
//		Activator.logger.info(response.toString(), ResourceDiscovery.class);
		return ResponseStatusCode.OK.equals(response.getResponseStatusCode())
				? response.getContent() : null;
	}

	private static Object retrieveAttribute(String uri, String attr) throws Exception {
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.RETRIEVE);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(uri);
		request.setResultContent(ResultContent.ORIGINAL_RES);
		Activator.logger.info("read " + attr + " -> " + request.toString(), ResourceDiscovery.class);

		ResponsePrimitive response = cseService.doRequest(request);
//		Activator.logger.info(response.toString(), ResourceDiscovery.class);
		if (! ResponseStatusCode.OK.equals(response.getResponseStatusCode()))
			throw new Exception("Error reading cloud data: " + response.getResponseStatusCode());
		return SDTUtil.getValue(((FlexContainer) response.getContent()).getCustomAttribute(attr));
	}

	public static void updateAttribute(final String uri, 
			final CustomAttribute customAttribute) throws Exception {
		FlexContainer flexContainer = new FlexContainer();
		flexContainer.getCustomAttributes().add(customAttribute);

		RequestPrimitive request = new RequestPrimitive();
		request.setContent(flexContainer);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResultContent(ResultContent.ORIGINAL_RES);
		request.setOperation(Operation.UPDATE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(uri);
		Activator.logger.info("write " + customAttribute.getCustomAttributeName() 
				+ " -> " + request.toString(), ResourceDiscovery.class);

		ResponsePrimitive response = cseService.doRequest(request);
		Activator.logger.info(response.toString(), ResourceDiscovery.class);
		if (! ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode()))
			throw new Exception("Error writing cloud data: " + response.getResponseStatusCode());
	}

	public static String invokeAction(final String uri, 
			final List<CustomAttribute> customAttributes) throws Exception {
		FlexContainer flexContainer = new FlexContainer();
		flexContainer.getCustomAttributes().addAll(customAttributes);

		RequestPrimitive request = new RequestPrimitive();
		request.setContent(flexContainer);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResultContent(ResultContent.ORIGINAL_RES);
		request.setOperation(Operation.UPDATE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTargetId(uri);
		Activator.logger.info("write " + customAttributes 
				+ " -> " + request.toString(), ResourceDiscovery.class);

		ResponsePrimitive response = cseService.doRequest(request);
		Activator.logger.info(response.toString(), ResourceDiscovery.class);
		if (! ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode()))
			throw new Exception("Error invoking cloud action: " + response.getResponseStatusCode());
		CustomAttribute ret = ((FlexContainer) response.getContent()).getCustomAttribute("output");
		return (ret == null) ? null : ret.getCustomAttributeValue();
	}

}
