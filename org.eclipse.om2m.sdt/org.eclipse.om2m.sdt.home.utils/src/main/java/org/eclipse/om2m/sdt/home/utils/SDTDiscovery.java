/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.utils;

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
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.URIList;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.Arg;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.args.Command;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.ByteDataPoint;
import org.eclipse.om2m.sdt.datapoints.DateDataPoint;
import org.eclipse.om2m.sdt.datapoints.DateTimeDataPoint;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;
import org.eclipse.om2m.sdt.datapoints.FloatDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.datapoints.TimeDataPoint;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.devices.GenericDevice;
import org.eclipse.om2m.sdt.home.modules.GenericSensor;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.PropertyType;
import org.eclipse.om2m.sdt.home.utils.api.ISDTDiscovery;

public class SDTDiscovery implements ISDTDiscovery {

	private static class MyFlexContainer {

		private AbstractFlexContainer flex;
		private AbstractFlexContainerAnnc flexA;

		public MyFlexContainer(AbstractFlexContainer flex) {
			this.flex = flex;
		}

		public MyFlexContainer(AbstractFlexContainerAnnc flex) {
			this.flexA = flex;
		}

		public List<String> getLabels() {
			return (flex == null) ? flexA.getLabels() : flex.getLabels();
		}

		public String getContainerDefinition() {
			return (flex == null) ? flexA.getContainerDefinition() : flex.getContainerDefinition();
		}

		public CustomAttribute getCustomAttribute(String name) {
			return (flex == null) ? flexA.getCustomAttribute(name) : flex.getCustomAttribute(name);
		}

		public List<CustomAttribute> getCustomAttributes() {
			return (flex == null) ? flexA.getCustomAttributes() : flex.getCustomAttributes();
		}

		public List<ChildResourceRef> getChildResource() {
			return (flex == null) ? flexA.getChildResource() : flex.getChildResource();
		}

		public String toString() {
			return (flex == null) ? flexA.toString() : flex.toString();
		}
		
		public String getLongName() {
			return (flex == null) ? flexA.getName() : flex.getLongName();
		}
		
		public String getShortName() {
			return (flex == null) ? flexA.getName() : flex.getShortName();
		}

	}

	static private final String SEP = "/";
	static private final String SDT_IPE = "SDT_IPE";
	static private final String SDT_IPE_ANNC = "SDT_IPE_Annc";

	static private final String SDT_DEVICE_PACKAGE = GenericDevice.class.getPackage().getName();
	static private final String SDT_MODULE_PACKAGE = GenericSensor.class.getPackage().getName();

	static private final String RESOURCE_ID_SEARCH_STRING = "ResourceID/";
	static private final String NAME_SEARCH_STRING = "name/";
	static private final String CNT_DEF_SEARCH_STRING = "cntDef/";
	static private final String APPLICATION_TYPE_SEARCH_STRING = "ResourceType/Application";
	static private final String DEVICE_TYPE_SEARCH_STRING = "object.type/device";

	static private  CseService cseService;

	private String mnName;
	private Domain cloudDomain;
	private Domain localDomain;

	static public void initCseService(CseService srv) {
		cseService = srv;
	}

	public SDTDiscovery(final String mnName) throws Exception {
		this.mnName = mnName;
		cloudDomain = new Domain("CloudDevices" + mnName);
		localDomain = new Domain("LocalDevices" + mnName);
	}

	@Override
	public void validateUserCredentials(final String appName, 
			final String userName, final String password) throws Exception {
		Activator.LOGGER.info("validateUserCredentials " + appName + " " + userName + "/" + password);
		RequestPrimitive request = new RequestPrimitive();
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.RETRIEVE);
		request.setFrom(userName + ":" + password);
		request.setFilterCriteria(new FilterCriteria());
		request.getFilterCriteria().setFilterUsage(FilterUsage.DISCOVERY_CRITERIA);
		request.getFilterCriteria().getLabels().add(RESOURCE_ID_SEARCH_STRING + appName);
		request.getFilterCriteria().getLabels().add(APPLICATION_TYPE_SEARCH_STRING);
		request.setTo(//SEP + Constants.CSE_ID + SEP + Constants.CSE_NAME);
			SEP + Constants.CSE_ID + SEP + Constants.CSE_NAME + SEP + mnName);
		Activator.LOGGER.info(request);
		
		ResponsePrimitive resp = cseService.doRequest(request);
		Activator.LOGGER.info(resp);
		BigInteger result = resp.getResponseStatusCode();
		if (! ResponseStatusCode.OK.equals(result)) {
			Activator.LOGGER.warn("Retrieve error " + result);
			throw new Exception("Internal error");
		}
		URIList uriList = (URIList) resp.getContent();
		Activator.LOGGER.info("Application URIs " + uriList);
		if (uriList.getListOfUri().isEmpty()) {
			Activator.LOGGER.warn("Application not found");
			throw new Exception("Access denied");
		}
	}

	@Override
	public List<GenericDevice> getDevices(final boolean cloud, 
			final String name, final String password) throws Exception {
		return getDevices(null, cloud, name, password);
	}

	@Override
	public List<GenericDevice> getDevices(final String cntDef, final boolean cloud,
			final String name, final String password) throws Exception {
		String cred = name + ":" + password;
		List<GenericDevice> ret = new ArrayList<GenericDevice>();
		List<String> all = retrieveDeviceURIs(cntDef, cred);
		if ((all != null) && ! all.isEmpty()) {
			for (String uri : all) {
				if (cloud) {
					if (uri.contains(SDT_IPE) && ! uri.contains(SDT_IPE_ANNC))
						ret.add(readDevice(uri, true, cred));
				} else if (uri.contains(SDT_IPE_ANNC))
					ret.add(readDevice(uri, false, cred));
			}
		}
		return ret;
	}

	@Override
	public GenericDevice getDevice(final String deviceId, final boolean cloud,
			final String name, final String password) throws Exception {
		String cred = name + ":" + password;
		List<String> labels = new ArrayList<String>();
		labels.add(DEVICE_TYPE_SEARCH_STRING);
		labels.add(NAME_SEARCH_STRING + deviceId);
		List<String> uris = retrieveDeviceURIs(labels, cred);
		if ((uris == null) || uris.isEmpty()) {
			throw new Exception("Device not found " + deviceId);
		}
		String uri = uris.get(0);
		return readDevice(uri, cloud, cred);
	}

	private List<String> retrieveDeviceURIs(final String cntDef, final String cred) 
			throws Exception {
		List<String> labels = new ArrayList<String>();
		labels.add(DEVICE_TYPE_SEARCH_STRING);
		if (cntDef != null)
			labels.add(CNT_DEF_SEARCH_STRING + cntDef);
		return retrieveDeviceURIs(labels, cred);
	}

	private List<String> retrieveDeviceURIs(List<String> labels, final String cred) 
			throws Exception {
		RequestPrimitive request = new RequestPrimitive();
		request.setTo(//Constants.SP_RELATIVE_URI_SEPARATOR +
				SEP + Constants.CSE_ID + SEP + Constants.CSE_NAME
				+ SEP + mnName);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setOperation(Operation.RETRIEVE);
		request.setFilterCriteria(new FilterCriteria());
		request.getFilterCriteria().setFilterUsage(FilterUsage.DISCOVERY_CRITERIA);
		request.getFilterCriteria().getLabels().addAll(labels);
		request.setFrom(cred);

		ResponsePrimitive resp = cseService.doRequest(request);
		if (! ResponseStatusCode.OK.equals(resp.getResponseStatusCode())) {
			throw new Exception("Could not read devices list: " + resp);
		}
		List<String> ret = ((URIList) resp.getContent()).getListOfUri();
		Activator.LOGGER.info("Device URIs found for " + labels + ": " + ret.size() + " " + ret);
		return ret;
	}

	private GenericDevice readDevice(final String uri, final boolean cloud, final String cred) 
			throws Exception {
		Activator.LOGGER.info("Get device " + uri);
		MyFlexContainer deviceFlex = retrieveFlexContainer(uri, ResultContent.ORIGINAL_RES, cred);
		Activator.LOGGER.info("Got device " + deviceFlex);
		Map<String, String> labels = new HashMap<String, String>();
		for (String label : deviceFlex.getLabels()) {
			int idx = label.indexOf('/');
			if (idx > 0)
				labels.put(label.substring(0, idx), label.substring(idx+1));
		}
		String deviceId = labels.get("id");
		CustomAttribute serialAttr = 
				deviceFlex.getCustomAttribute(PropertyType.deviceSerialNum.getShortName());
		String serial = null;
		if (serialAttr == null) {
			Activator.LOGGER.info("No serial number property. Take id instead.");
			serial = deviceId;
		} else {
			serial = serialAttr.getCustomAttributeValue();
		}
		Domain domain = cloud ? cloudDomain : localDomain;
		GenericDevice device = (GenericDevice) domain.getDevice(deviceId);
		if (device == null) {
			String cntDef = deviceFlex.getContainerDefinition();
			if (deviceId.startsWith(cntDef + "__")) {
				deviceId = deviceId.substring(cntDef.length() + 2);
			}
			String devName = cntDef.substring(cntDef.lastIndexOf('.') + 1);
			if (devName.toLowerCase().startsWith("device"))
				devName = devName.substring(6); // remove "device" prefix
			devName = Character.toUpperCase(devName.charAt(0)) + devName.substring(1);
			String className = SDT_DEVICE_PACKAGE + "." + devName;
			Class<?> clazz = Class.forName(className);
			device = (GenericDevice) clazz.getConstructor(String.class, String.class, Domain.class)
					.newInstance(deviceId, serial, domain);
			Activator.LOGGER.info("Created SDT device " + device);
		}
		for (CustomAttribute attr : deviceFlex.getCustomAttributes()) {
			Activator.LOGGER.info("dev CustomAttribute: " + attr.getCustomAttributeName()
				 + "/" + attr.getCustomAttributeValue());
			PropertyType propType = PropertyType.fromShortName(attr.getCustomAttributeName());
			if (propType != null) {
//				Property prop = new Property(propType, attr.getCustomAttributeValue());
//				device.addProperty(prop);
				device.addProperty(propType, attr.getCustomAttributeValue());
			}
		}
		// Search children resources: modules
		MyFlexContainer ctr = retrieveFlexContainer(uri, 
				ResultContent.ATTRIBUTES_AND_CHILD_REF, cred);
		for (ChildResourceRef ref : ctr.getChildResource()) {
			if (ref.getType().equals(BigInteger.valueOf(ResourceType.FLEXCONTAINER_ANNC))
					|| ref.getType().equals(BigInteger.valueOf(ResourceType.FLEXCONTAINER))) {
				Module module = readModule(ref.getValue(), cloud, cred);
				if (device.getModule(module.getName()) == null) {
					Activator.LOGGER.info("Add new module " + module);
					device.addModule(module); // module URI
				} else {
					Activator.LOGGER.info("Already present module " + module);
				}
			}
		}
		Activator.LOGGER.info("Full SDT device " + device);//.prettyPrint());
		return device;
	}

	private Module readModule(final String uri, final boolean cloud, final String cred) 
			throws Exception {
		Activator.LOGGER.info("Get module " + uri);
		MyFlexContainer moduleFlex = retrieveFlexContainer(uri, 
				ResultContent.ORIGINAL_RES, cred);
		Activator.LOGGER.info("Got module " + moduleFlex);
		Map<String,String> labels = new HashMap<String,String>();
		for (String label : moduleFlex.getLabels()) {
			int idx = label.indexOf('/');
			if (idx > 0)
				labels.put(label.substring(0, idx), label.substring(idx+1));
		}
		List<Property> props = new ArrayList<Property>();
		List<CustomAttribute> dpAttrs = new ArrayList<CustomAttribute>();
		for (CustomAttribute attr : moduleFlex.getCustomAttributes()) {
			Activator.LOGGER.info("CustomAttribute(1): " + attr.getCustomAttributeName()
					+ "/" + attr.getCustomAttributeValue());
			PropertyType propType = PropertyType.fromShortName(attr.getCustomAttributeName());
			if (propType != null) {
				Property prop = new Property(propType, attr.getCustomAttributeValue());
				props.add(prop);
			} else {
				dpAttrs.add(attr);
			}
		}
		String modName = labels.get("name");
		Domain domain = cloud ? cloudDomain : localDomain;
		Module module = (Module) domain.getModule(modName);
		if (module != null) {
			for (Property prop : props) {
				module.addProperty(prop);
			}
			Activator.LOGGER.info("Full retrieved SDT module " + module);//.prettyPrint());
			return module;
		}
		// Case new module
		String cntDef = moduleFlex.getContainerDefinition();
		int idx = cntDef.lastIndexOf('.') + 1;
		String className = SDT_MODULE_PACKAGE + "." + Character.toUpperCase(cntDef.charAt(idx)) 
				+ cntDef.substring(idx + 1);
		List<DataPoint> dps = new ArrayList<DataPoint>();
		for (CustomAttribute attr : dpAttrs) {
			DatapointType datapointType = DatapointType.fromShortName(attr.getCustomAttributeName());
			if (datapointType == null) {
				Activator.LOGGER.warn("Unknown custom attribute, neither property nor datapoint: " 
						+ attr.getCustomAttributeName());
				continue;
			}
			String type = datapointType.getDataType().getTypeChoice().getOneM2MType();
			switch (type) {
			case "xs:integer": dps.add(getIntegerDataPoint(attr, uri, cred)); break;
			case "xs:boolean": dps.add(getBooleanDataPoint(attr, uri, cred)); break;
			case "xs:string": dps.add(getStringDataPoint(attr, uri, cred)); break;
			case "xs:byte": dps.add(getByteDataPoint(attr, uri, cred)); break;
			case "xs:float": dps.add(getFloatDataPoint(attr, uri, cred)); break;
			case "xs:datetime": dps.add(getDateTimeDataPoint(attr, uri, cred)); break;
			case "xs:time": dps.add(getTimeDataPoint(attr, uri, cred)); break;
			case "xs:date": dps.add(getDateDataPoint(attr, uri, cred)); break;
			case "xs:enum": dps.add(getArrayDataPoint(attr, uri, cred)); break;
			default:
				if (type.startsWith("hd:")) {
					type = type.substring(3);
					dps.add(getEnumDataPoint(type, attr, uri, cred));
				}
				break;
			}
		}
		Map<String, DataPoint> dpsMap = new HashMap<String, DataPoint>();
		for (DataPoint dp : dps) {
			dpsMap.put(dp.getShortDefinitionType(), dp);
		}
		Class<?> clazz = Class.forName(className);
		if (modName.startsWith(cntDef + "__")) {
			modName = modName.substring(cntDef.length() + 2);
		}
		module = (Module) clazz.getConstructor(String.class, Domain.class, Map.class)
				.newInstance(modName, domain, dpsMap);
		Activator.LOGGER.info("Created SDT module " + module);
		for (Property prop : props) {
			module.addProperty(prop);
		}
		Activator.LOGGER.info("Full new SDT module " + module);//.prettyPrint());
		// Search children resources: modules
		MyFlexContainer ctr = retrieveFlexContainer(uri, 
				ResultContent.ATTRIBUTES_AND_CHILD_REF, cred);
		Activator.LOGGER.info("Children " + ctr.getChildResource());
		for (ChildResourceRef ref : ctr.getChildResource()) {
			if (ref.getType().equals(BigInteger.valueOf(ResourceType.FLEXCONTAINER_ANNC))
					|| ref.getType().equals(BigInteger.valueOf(ResourceType.FLEXCONTAINER))) {
				try {
					Action action = readAction(ref.getValue(), module, cred);
					if (module.getAction(action.getName()) == null) {
						Activator.LOGGER.info("Add new action " + action);
						module.addAction(action);
					} else {
						Activator.LOGGER.info("Already present action " + action);
					}
				} catch (Exception e) {
					Activator.LOGGER.error("Error creating action " + ref.getValue());
				}
			}
		}
		return module;
	}

	private Action readAction(final String uri, final Module module, final String cred) 
			throws Exception {
		Activator.LOGGER.info("Get action " + uri);
		final MyFlexContainer actionFlexContainer = retrieveFlexContainer(uri, 
				ResultContent.ORIGINAL_RES, cred);
		Activator.LOGGER.info("Got action " + actionFlexContainer);
		Map<String,String> labels = new HashMap<String,String>();
		for (String label : actionFlexContainer.getLabels()) {
			int idx = label.indexOf('/');
			if (idx > 0)
				labels.put(label.substring(0, idx), label.substring(idx+1));
		}
		List<Arg> args = new ArrayList<Arg>();
		final List<CustomAttribute> attributes = actionFlexContainer.getCustomAttributes();
		// 2017 07 17 - BONNARDEL Gregory
		// I commented out the next piece of code because there is no way
		// to retrieve Arg type with current SDT apis.
		// As we have only no-args action, this is not a problem.
//		for (final CustomAttribute attr : attributes) {
//			String type = attr.getCustomAttributeType();
//			Arg arg = new ValuedArg<Object>(attr.getCustomAttributeName(), 
//					new DataType(type, SimpleType.getSimpleType(type))) {
//				public void setValue(Object value) {
//					try {
//						SDTUtil.setValue(attr, value);
//						updateAttribute(uri, attr, cred);
//					} catch (Exception e) {
//						Activator.LOGGER.warn("Could not set arg", e);
//					}
//				}
//			};
//			args.add(arg);
//		}
		final String cntDef = actionFlexContainer.getContainerDefinition();
		String actionName = labels.get("name");
		if (actionName == null)
			actionName = cntDef.substring(cntDef.lastIndexOf('.') + 1);
		Action action = module.getAction(actionName);
		if (action != null) {
			Activator.LOGGER.info("Full retrieved SDT action " + action);
			return action;
		}
		action = new Command(actionName, args,
			new Identifiers() {
				@Override
				public String getShortName() {
					return actionFlexContainer.getShortName();
				}
				@Override
				public String getLongName() {
					return actionFlexContainer.getLongName();
				}
				@Override
				public String getDefinition() {
					return cntDef;
				}
			}) {
			@Override
			protected Object doInvoke() throws ActionException {
				Activator.LOGGER.info("invoke SDT action");
				try {
					return invokeAction(uri, attributes, cred);
				} catch (Exception e) {
					throw new ActionException(e);
				}
			}
		};
		//		
		Activator.LOGGER.info("Created SDT action " + action);
		return action;
	}

	private IntegerDataPoint getIntegerDataPoint(final CustomAttribute attr,
			final String uri, final String cred) {
		return new IntegerDataPoint(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(Integer val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:integer");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				try {
					return (Integer) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}

	private BooleanDataPoint getBooleanDataPoint(final CustomAttribute attr, 
			final String uri, final String cred) {
		BooleanDataPoint ret = new BooleanDataPoint(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(Boolean val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:boolean");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Boolean doGetValue() throws DataPointException {
				try {
					return (Boolean) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
		return ret;
	}

	private StringDataPoint getStringDataPoint(final CustomAttribute attr, 
			final String uri, final String cred) {
		return new StringDataPoint(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(String val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:string");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public String doGetValue() throws DataPointException {
				try {
					return (String) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}

	private ByteDataPoint getByteDataPoint(final CustomAttribute attr, 
			final String uri, final String cred) {
		return new ByteDataPoint(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(Byte val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:byte");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Byte doGetValue() throws DataPointException {
				try {
					return (Byte) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}

	private FloatDataPoint getFloatDataPoint(final CustomAttribute attr, 
			final String uri, final String cred) {
		return new FloatDataPoint(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(Float val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:float");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Float doGetValue() throws DataPointException {
				try {
					return (Float) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}

	private DateTimeDataPoint getDateTimeDataPoint(final CustomAttribute attr, 
			final String uri, final String cred) {
		return new DateTimeDataPoint(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(Date val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:datetime");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Date doGetValue() throws DataPointException {
				try {
					return (Date) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}

	private DateDataPoint getDateDataPoint(final CustomAttribute attr, 
			final String uri, final String cred) {
		return new DateDataPoint(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(Date val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:date");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Date doGetValue() throws DataPointException {
				try {
					return (Date) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}

	private TimeDataPoint getTimeDataPoint(final CustomAttribute attr, 
			final String uri, final String cred) {
		return new TimeDataPoint(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(Date val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:time");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@Override
			public Date doGetValue() throws DataPointException {
				try {
					return (Date) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}

	private ArrayDataPoint<String> getArrayDataPoint(final CustomAttribute attr, 
			final String uri, final String cred) {
		return new ArrayDataPoint<String>(DatapointType.fromShortName(attr.getCustomAttributeName())) {
			@Override
			public void doSetValue(List<String> val) throws DataPointException {
				try {
					SDTUtil.setValue(attr, val, "xs:enum");
					updateAttribute(uri, attr, cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
			@SuppressWarnings("unchecked")
			@Override
			public List<String> doGetValue() throws DataPointException {
				try {
					return (List<String>) retrieveAttribute(uri, 
							attr.getCustomAttributeName(), cred);
				} catch (Exception e) {
					throw new DataPointException(e);
				}
			}
		};
	}
	
	@SuppressWarnings("unchecked")
	private static EnumDataPoint<Integer> getEnumDataPoint(String type, final CustomAttribute attr,
			final String uri, final String cred) {
		try {
			String className = DatapointType.class.getPackage().getName() + "." 
				+ type.substring(0, 1).toUpperCase() + type.substring(1);
			Class<?> clazz = Class.forName(className);
			return (EnumDataPoint<Integer>) 
				clazz.getConstructor(Identifiers.class, EnumDataPoint.class)
					.newInstance(DatapointType.fromShortName(attr.getCustomAttributeName()), 
						new EnumDataPoint<Integer>(null) {
							@Override
							public void doSetValue(Integer val) throws DataPointException {
								try {
									SDTUtil.setValue(attr, val, "xs:enum");
									updateAttribute(uri, attr, cred);
								} catch (Exception e) {
									throw new DataPointException(e);
								}
							}
							@Override
							public Integer doGetValue() throws DataPointException {
								try {
									return (Integer) retrieveAttribute(uri, 
											attr.getCustomAttributeName(), cred);
								} catch (Exception e) {
									throw new DataPointException(e);
								}
							}
						});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private MyFlexContainer retrieveFlexContainer(final String uri, 
			final BigInteger resultContent, final String cred) throws Exception {
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.RETRIEVE);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setFilterCriteria(new FilterCriteria());
		request.getFilterCriteria().setLevel(BigInteger.ONE);
		request.setFrom(cred);
		request.setTo(uri);
		request.setResultContent(resultContent);

		ResponsePrimitive resp = cseService.doRequest(request);
		BigInteger code = resp.getResponseStatusCode();
		if (! ResponseStatusCode.OK.equals(code))
			throw new Exception("Error searching " + uri + ": " + code);
		Object ret = resp.getContent();
		if (ret instanceof AbstractFlexContainer)
			return new MyFlexContainer((AbstractFlexContainer)ret);
		else if (ret instanceof AbstractFlexContainerAnnc)
			return new MyFlexContainer((AbstractFlexContainerAnnc)ret);
		else 
			throw new Exception("Error not a FlexContainer " + uri);
	}

	static private Object retrieveAttribute(final String uri, final String attr,
			final String cred) throws Exception {
		
		DatapointType datapointType = DatapointType.fromShortName(attr);
		
		if (datapointType == null) {
			return null;
		}
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.RETRIEVE);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setFrom(cred);
		request.setTo(uri);
		request.setResultContent(ResultContent.ORIGINAL_RES);

		ResponsePrimitive resp = cseService.doRequest(request);
		Activator.LOGGER.info("read " + attr + " -> " + resp.getResponseStatusCode());
		if (! ResponseStatusCode.OK.equals(resp.getResponseStatusCode()))
			throw new Exception("Error reading cloud data: " + resp.getResponseStatusCode());
		return SDTUtil.getValue(((AbstractFlexContainer) resp.getContent()).getCustomAttribute(attr), datapointType.getDataType().getTypeChoice().getOneM2MType());
	}

	static public void updateAttribute(final String uri, 
			final CustomAttribute customAttribute, final String cred) throws Exception {
		FlexContainer flexContainer = new FlexContainer();
		flexContainer.getCustomAttributes().add(customAttribute);

		RequestPrimitive request = new RequestPrimitive();
		request.setContent(flexContainer);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResultContent(ResultContent.ORIGINAL_RES);
		request.setOperation(Operation.UPDATE);
		request.setFrom(cred);
		request.setTo(uri);

		ResponsePrimitive resp = cseService.doRequest(request);
		Activator.LOGGER.info("write " + customAttribute + " -> " + resp.getResponseStatusCode());
		if (! ResponseStatusCode.UPDATED.equals(resp.getResponseStatusCode()))
			throw new Exception("Error writing cloud data: " + resp.getResponseStatusCode());
	}

	public String invokeAction(final String uri, 
			final List<CustomAttribute> customAttributes, final String cred) throws Exception {
		FlexContainer flexContainer = new FlexContainer();
		flexContainer.getCustomAttributes().addAll(customAttributes);

		RequestPrimitive request = new RequestPrimitive();
		request.setContent(flexContainer);
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setResultContent(ResultContent.ORIGINAL_RES);
		request.setOperation(Operation.UPDATE);
		request.setFrom(cred);
		request.setTo(uri);

		ResponsePrimitive resp = cseService.doRequest(request);
		Activator.LOGGER.info("invoke " + customAttributes + " -> " + resp.getResponseStatusCode());
		if (! ResponseStatusCode.UPDATED.equals(resp.getResponseStatusCode()))
			throw new Exception("Error invoking cloud action: " + resp.getResponseStatusCode());
		CustomAttribute ret = ((AbstractFlexContainer) resp.getContent()).getCustomAttribute("output");
		return (ret == null) ? null : ret.getCustomAttributeValue();
	}

}
