/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.FilterUsage;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.FilterCriteria;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.URIList;
import org.eclipse.om2m.core.service.CseService;

public class ResourceDiscovery {

	public static CseService cseService;
	private static Log LOGGER = LogFactory.getLog(ResourceDiscovery.class);
	// DISCOVERY parameter
	private static final String SEARCH_STRING_DISCOVERY_PARAMETER = "searchString";

	// SEARCHSTRING
	private static final String RESOURCE_ID_SEARCH_STRING = "ResourceID/";
	private static final String RESOURCE_TYPE_APPLICATION_SEARCH_STRING = "ResourceType/Application";

	public static void initCseService(CseService pCseService) {
		cseService = pCseService;
	}

	public static String validateUserCredentials(String name, String password) {
		LOGGER.info("validateUserCredentials " + name + "/" + password);
		RequestPrimitive request = new RequestPrimitive();
		request.setReturnContentType(MimeMediaType.OBJ);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setFrom(name + ":" + password);
		request.setFilterCriteria(new FilterCriteria());
		request.setOperation(Operation.RETRIEVE);
		request.getFilterCriteria().setFilterUsage(FilterUsage.DISCOVERY_CRITERIA);
		request.getFilterCriteria().getLabels().add(RESOURCE_ID_SEARCH_STRING + Constants.RESOURCE_ID);
		request.getFilterCriteria().getLabels().add(RESOURCE_TYPE_APPLICATION_SEARCH_STRING);
		request.setTo("/" + org.eclipse.om2m.commons.constants.Constants.CSE_ID
				+ "/" + org.eclipse.om2m.commons.constants.Constants.CSE_NAME);
		ResponsePrimitive response = cseService.doRequest(request);
		if (! ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
			LOGGER.info("KO");
			return null;
		}
		URIList uriList = (URIList) response.getContent();
		LOGGER.info("OK " + uriList);
		return ((uriList == null) || uriList.getListOfUri().isEmpty()) ? null
				: uriList.getListOfUri().get(0);
	}

//	public static List<String> retrievesAllDevices(String name, String password) {
//		LOGGER.info("retrievesAllDevices");
//		RequestPrimitive request = new RequestPrimitive();
//		request.setTargetId(org.eclipse.om2m.commons.constants.Constants.SP_RELATIVE_URI_SEPARATOR
//				+ "/" + org.eclipse.om2m.commons.constants.Constants.CSE_ID
//				+ "/" + org.eclipse.om2m.commons.constants.Constants.CSE_NAME
//				/*+ "/" + MN_CSE_NAME*/);
//		request.setReturnContentType(MimeMediaType.OBJ);
//		request.setRequestContentType(MimeMediaType.OBJ);
//		request.setOperation(Operation.RETRIEVE);
//		request.setFilterCriteria(new FilterCriteria());
//		request.getFilterCriteria().setFilterUsage(FilterUsage.DISCOVERY_CRITERIA);
//		request.getFilterCriteria().getLabels().add("object.type/device");
//		request.setFrom(name + ":" + password);
//
//		ResponsePrimitive response = cseService.doRequest(request);
//		if (ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
//			URIList uriList = (URIList) response.getContent();
//			return uriList.getListOfUri();
//		}
//
//		return new ArrayList<String>();
//	}
//
//	public static String retrievesAllDevicesWithState(String name, String password) {
//		LOGGER.info("retrievesAllDevicesWithState");
//		List<String> deviceUris = retrievesAllDevices(name, password);
//		String jsonString = "";
//		// iterate over the list of device uri.
//		for (String deviceUri : deviceUris) {
//			Resource deviceFlex = retrieveDevice(deviceUri, name, password);
//			if (deviceFlex != null) {
//				String deviceId = null;
//				List<String> labels = getLabels(deviceFlex);
//				if (deviceFlex != null) {
//					for (String label : labels) {
//						if (label.startsWith("id/")) {
//							deviceId = label.substring(3);
//						}
//					}
//				}
//
//				String deviceName = getLabelValue(labels, "name");
//				String moduleUri = findDeviceStateModule((Resource)deviceFlex);
//				String stateAttribute = findStateAttribute(findStateModuleDefinition(getDefinition(deviceFlex)));
//				String deviceState = retrieveDeviceState(moduleUri, name, password);
//
//				jsonString += (!jsonString.isEmpty() ? "," : "") + "{\"id\":\""
//						+ deviceId + "\"," + "\"name\":\"" + deviceName
//						+ "\",\"state\":" + deviceState + ",\"moduleUri\":\""
//						+ moduleUri + "\",\"attributeName\":\""
//						+ stateAttribute + "\"}";
//			}
//		}
//		LOGGER.info("retrievesAllDevicesWithState " + jsonString);
//		return jsonString.length() > 0 ? "[" + jsonString + "]" : "{}";
//	}
//
//	private static String findStateModuleDefinition(String deviceCntDef) {
//		if (deviceCntDef != null) {
//			if (deviceCntDef.equals(DeviceType.deviceLight.getDefinition())
//					|| deviceCntDef.equals(DeviceType.deviceGasValve.getDefinition()))
//				return ModuleType.binarySwitch.getDefinition();
//			if (deviceCntDef.equals(DeviceType.deviceWaterValve.getDefinition()))
//				return ModuleType.liquidLevel.getDefinition();
//			if (deviceCntDef.equals(DeviceType.deviceFloodDetector.getDefinition()))
//				return ModuleType.waterSensor.getDefinition();
//			if (deviceCntDef.equals(DeviceType.deviceSmokeDetector.getDefinition()))
//				return ModuleType.smokeSensor.getDefinition();
////			switch (deviceCntDef.toLowerCase()) {
////			case "org.onem2m.home.device.devicelight":
////			case "org.onem2m.home.device.devicegasvalve":
////				return "org.onem2m.home.moduleclass.binaryswitch";
////			case "org.onem2m.home.device.devicewatervalve":
////				return "org.onem2m.home.moduleclass.waterlevel";
////			case "org.onem2m.home.device.deviceflooddetector":
////				return "org.onem2m.home.moduleclass.watersensor";
////			case "org.onem2m.home.device.devicesmokedetector":
////				return "org.onem2m.home.moduleclass.smokesensor";
////			}
//		}
//		return null;
//	}
//
//	private static String findStateAttribute(String moduleFlexCntDef) {
//		if (moduleFlexCntDef != null) {
//			if (moduleFlexCntDef.equals(ModuleType.smokeSensor.getDefinition())
//					|| moduleFlexCntDef.equals(ModuleType.waterSensor.getDefinition()))
//				return DatapointType.alarm.getShortName();
//			if (moduleFlexCntDef.equals(ModuleType.binarySwitch.getDefinition()))
//				return DatapointType.powerState.getShortName();
//			if (moduleFlexCntDef.equals(ModuleType.liquidLevel.getDefinition()))
//				return DatapointType.liquidLevel.getShortName();
////			switch (moduleFlexCntDef.toLowerCase()) {
////			case "org.onem2m.home.moduleclass.binaryswitch":
////				return "powerState";
////			case "org.onem2m.home.moduleclass.watersensor":
////			case "org.onem2m.home.moduleclass.smokesensor":
////				return "alarm";
////			case "org.onem2m.home.moduleclass.waterlevel":
////				return "liquidlevel";
////			}
//		}
//		return null;
//	}
//
//	private static String findDeviceStateModule(Resource deviceFlex) {
//		String deviceDefinition = getDefinition(deviceFlex);
//		List<ChildResourceRef> childResourceRefs = null;
//		if (deviceFlex instanceof AbstractFlexContainer) {
//			childResourceRefs = ((AbstractFlexContainer) deviceFlex).getChildResource();
//		} else if (deviceFlex instanceof FlexContainerAnnc) {
//			childResourceRefs = ((FlexContainerAnnc) deviceFlex).getChildResource();
//		}
//		String stateModule = null;
//		if (deviceDefinition != null) {
//			stateModule = findStateModuleDefinition(deviceDefinition); 
//		}
//
//		if ((stateModule != null)  && (childResourceRefs != null)) {
//			String moduleUri = null;
//			for (ChildResourceRef ref : childResourceRefs) {
//				if (ref.getType().equals(BigInteger.valueOf(ResourceType.FLEXCONTAINER_ANNC)) 
//						|| ref.getType().equals(BigInteger.valueOf(ResourceType.FLEXCONTAINER))) {
//					if (ref.getResourceName().toLowerCase().contains(stateModule)) {
//						moduleUri = ref.getValue();
//						break;
//					}
//				}
//			}
//			return moduleUri;
//		}
//		return null;
//	}
//
//	public static String retrieveDeviceState(String moduleUri, String name, String password) {
//		if (moduleUri != null) {
//			LOGGER.info("retrieveDeviceState " + moduleUri);
//			RequestPrimitive request = new RequestPrimitive();
//			request.setFrom(name + ":" + password);
//			request.setReturnContentType(MimeMediaType.OBJ);
//			request.setRequestContentType(MimeMediaType.OBJ);
//			request.setOperation(Operation.RETRIEVE);
//			request.setTargetId(moduleUri);
//			request.setResultContent(ResultContent.ORIGINAL_RES);
//			ResponsePrimitive response = cseService.doRequest(request);
//			AbstractFlexContainer moduleFlex = null;
//			if (ResponseStatusCode.OK.equals(response.getResponseStatusCode())) {
//				moduleFlex = (AbstractFlexContainer) response.getContent();
//			}
//			if (moduleFlex != null) {
//				String stateAttribute = findStateAttribute(moduleFlex.getContainerDefinition());
//				if (stateAttribute != null) {
//					CustomAttribute stateCustomAttribute = moduleFlex.getCustomAttribute(stateAttribute);
//					if (stateCustomAttribute != null) {
//						String ret = stateCustomAttribute.getCustomAttributeValue();
//						LOGGER.info("OK " + ret);
//						return ret;
//					}
//				}
//			}
//		}
//		LOGGER.info("KO");
//		return null;
//	}
//
//	private static Resource retrieveDevice(String deviceUri, String name, String password) {
//		RequestPrimitive request = new RequestPrimitive();
//		request.setOperation(Operation.RETRIEVE);
//		request.setReturnContentType(MimeMediaType.OBJ);
//		request.setRequestContentType(MimeMediaType.OBJ);
//		request.setFrom(name + ":" + password);
//		request.setTargetId(deviceUri);
//		request.setResultContent(ResultContent.ATTRIBUTES_AND_CHILD_REF);
//
//		ResponsePrimitive response = cseService.doRequest(request);
//		return (Resource) (ResponseStatusCode.OK.equals(response.getResponseStatusCode())
//				? response.getContent() : null);
//	}
//
//	public static String changeDeviceState(String name, String password,
//			String moduleUri, String attributeName, boolean state) {
//		LOGGER.info("changeDeviceState " + attributeName + "/" + state + " " + moduleUri);
//		FlexContainer moduleFlexContainer = new FlexContainer();
//		CustomAttribute customAttribute = new CustomAttribute();
//		customAttribute.setCustomAttributeName(attributeName);
//		customAttribute.setCustomAttributeType("xs:boolean");
//		customAttribute.setCustomAttributeValue(Boolean.toString(state));
//		moduleFlexContainer.getCustomAttributes().add(customAttribute);
//
//		RequestPrimitive request = new RequestPrimitive();
//		request.setContent(moduleFlexContainer);
//		request.setReturnContentType(MimeMediaType.OBJ);
//		request.setRequestContentType(MimeMediaType.OBJ);
//		request.setResultContent(ResultContent.ORIGINAL_RES);
//		request.setOperation(Operation.UPDATE);
//		request.setFrom(name + ":" + password);
//		request.setTargetId(moduleUri);
//
//		ResponsePrimitive response = cseService.doRequest(request);
//		return response.getResponseStatusCode().toString();
//	}
//
//	private static String getLabelValue(final List <String> labels, final String labelName) {
//		if (labels != null) {
//			for (String label : labels) {
//				if (label.startsWith(labelName)) {
//					return label.substring(labelName.length() + 1);
//				}
//			}
//		}
//		return null;
//	}
//
//	private static List<String> getLabels(Resource flex) {
//		if (flex instanceof FlexContainerAnnc) {
//			return ((FlexContainerAnnc) flex).getLabels();
//		} else if (flex instanceof AbstractFlexContainer) {
//			return ((AbstractFlexContainer) flex).getLabels();
//		}
//		return new ArrayList<String>();
//	}
//
//	private static String getDefinition(Resource flex) {
//		if (flex instanceof AbstractFlexContainer) {
//			return ((AbstractFlexContainer) flex).getContainerDefinition();
//		}
//		if (flex instanceof FlexContainerAnnc) {
//			return ((FlexContainerAnnc) flex).getContainerDefinition();
//		}
//		return null;
//	}

}
