/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.datapoints.AbstractDateDataPoint;
import org.eclipse.om2m.sdt.datapoints.EnumDataPoint;
import org.eclipse.om2m.sdt.datapoints.ValuedDataPoint;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;
import org.eclipse.om2m.sdt.types.DataType;
import org.eclipse.om2m.sdt.types.SimpleType;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class DeviceDiscoveryTestSuite {

	private static final String DEVICE_PREFIX = "DEVICE_";

	private final BundleContext bundleContext;
	private final CseService cseService;

	private final ServiceTracker deviceServiceTracker;
	private final List<Device> devices;

	public DeviceDiscoveryTestSuite(BundleContext context, CseService pCseService) {
		this.bundleContext = context;
		this.cseService = pCseService;

		devices = new ArrayList<>();

		deviceServiceTracker = new ServiceTracker(bundleContext, Device.class.getName(),
				new ServiceTrackerCustomizer() {

					@Override
					public void removedService(ServiceReference reference, Object service) {
						removeDevice((Device) service);
					}

					@Override
					public void modifiedService(ServiceReference reference, Object service) {
					}

					@Override
					public Object addingService(ServiceReference reference) {
						Device device = (Device) bundleContext.getService(reference);
						addDevice(device);

						checkDevice(device);
						return device;
					}
				});
		deviceServiceTracker.open();
	}

	private void addDevice(Device device) {
		synchronized (devices) {
			devices.add(device);
		}
	}

	private void removeDevice(Device device) {
		synchronized (devices) {
			devices.remove(device);
		}
	}

	private void checkDevice(Device device) {
		System.out.println("\n\ncheckDevice(" + device.getId() + "," + device.getName() + ")");

		String deviceLocation = Activator.SDT_IPE_LOCATION + "/" + DEVICE_PREFIX + device.getId();

		// device
		ResponsePrimitive response = CSEUtil.retrieveEntity(cseService, deviceLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			System.out.println("DEVICE NOT FOUND invalid response status code: " + response.getResponseStatusCode());
			return;
		}

		FlexContainer deviceFlexContainer = (FlexContainer) response.getContent();

		if (!checkName(deviceFlexContainer, DEVICE_PREFIX + device.getId())) {
			System.out.println("invalid name");
			return;
		}

		if (!checkContainerDefinition(deviceFlexContainer, device.getDefinition())) {
			System.out.println("invalid definition");
			return;
		}

		// to be fixed: properties are now hold by deviceProperty flexContainer
		for (String propertyName : device.getPropertyNames()) {
			Property property = device.getProperty(propertyName);
			if (property.getName().startsWith("prop")) {
				if (!checkCustomAttribute(deviceFlexContainer, propertyName, property.getValue())) {
					System.out.println(
							"invalid customAttribute name=" + propertyName + ", expectedValue=" + property.getValue());
					return;
				}
			} else {
				System.out.println("current property(name=" + property.getName() + ", value=" + property.getValue() + ") is not a standard property");
			}
		}

		for (Module module : device.getModules()) {
			String moduleLocation = deviceLocation + "/" + module.getName();
			if (!checkModule(module, moduleLocation)) {
				System.out.println("invalid module " + module.getName() + " for device " + device.getDefinition());
				return;
			}
		}

		System.out.println("checkDevice(" + device.getId() + "," + device.getName() + "): OK");
	}

	private boolean checkName(FlexContainer flexContainer, String expectedName) {
		System.out.println("checkName(expectedName=" + expectedName + ", currentName=" + flexContainer.getName() + ")");
		if ((expectedName == null) && (flexContainer.getName() != null)) {
			return false;
		}

		return expectedName.equals(flexContainer.getName());
	}

	private boolean checkContainerDefinition(FlexContainer flexContainer, String expectedContainerDefinition) {
		System.out.println("checkContainerDefinition(expectedContainerDefinition=" + expectedContainerDefinition
				+ ", currentContainerDefinition=" + flexContainer.getContainerDefinition() + ")");
		if ((expectedContainerDefinition == null) && (flexContainer.getContainerDefinition() != null)) {
			return false;
		}

		return expectedContainerDefinition.equals(flexContainer.getContainerDefinition());
	}

	private boolean checkCustomAttribute(FlexContainer flexContainer, String attributeName, String attributeValue) {
		System.out.println("checkCustomAttribute(name=" + attributeName + ", expectedValue=" + attributeValue + ")");

		CustomAttribute customAttribute = flexContainer.getCustomAttribute(attributeName);
		if (customAttribute == null) {
			System.out.println("checkCustomAttribute(name=" + attributeName + ", expectedValue=" + attributeValue
					+ ") - unexisting customAttribute");
			return false;
		}
		// at this point, we are sure the CustomAttribute exist

		if (attributeValue == null) {
			if (customAttribute.getCustomAttributeValue() != null) {
				System.out.println("checkCustomAttribute(name=" + attributeName + ", expectedValue=" + attributeValue
						+ ") - expecting null value");
				return false;
			} else {
				return true;
			}
		}

		boolean result = attributeValue.equals(customAttribute.getCustomAttributeValue());
		if (!result) {
			System.out.println("checkCustomAttribute(name=" + attributeName + ", expectedValue=" + attributeValue
					+ ") - found " + customAttribute.getCustomAttributeValue() + "- foundType="
					+ customAttribute.getCustomAttributeValue().getClass());
		}

		return result;
	}

	private boolean checkModule(Module module, String moduleLocation) {
		System.out.println("checkModule(name=" + module.getName() + ")");
		ResponsePrimitive response = CSEUtil.retrieveEntity(cseService, moduleLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			System.out.println("invalid response status code: " + response.getResponseStatusCode());
			return false;
		}

		FlexContainer moduleFlexContainer = (FlexContainer) response.getContent();

		if (!checkName(moduleFlexContainer, module.getName())) {
			System.out.println("invalid module name");
			return false;
		}

		if (!checkContainerDefinition(moduleFlexContainer, module.getDefinition())) {
			System.out.println("invalid module definition");
			return false;
		}

		// check module properties

		FlexContainer moduleClassPropertyFlexContainer = (FlexContainer) response.getContent();
		for (Property property : module.getProperties()) {
			if (!checkCustomAttribute(moduleClassPropertyFlexContainer, property.getName(), property.getValue())) {
				System.out.println("invalid customProperty (" + property.getName() + ")");
				// return false;
			}
		}

		// DataPoint checking
		for (DataPoint dataPoint : module.getDataPoints()) {
			String value = null;
			try {
				Object o = ((ValuedDataPoint<Object>) dataPoint).getValue();
				if (dataPoint instanceof AbstractDateDataPoint) {
					value = ((AbstractDateDataPoint) dataPoint).getStringValue();
				} else {
					value = (o != null ? o.toString() : null);
				}
			} catch (DataPointException e) {
				// exception to retrieve datapoint value
				System.out.println("unable to retrieve value of dataPoint " + dataPoint.getName());
				return false;
			} catch (AccessException e) {
				// exception to retrieve datapoint value
				System.out.println("unable to retrieve value of dataPoint " + dataPoint.getName());
				return false;
			}
			if (!checkCustomAttribute(moduleFlexContainer, dataPoint.getName(), value)) {
				System.out.println("invalid custom attribute for dataPoint " + dataPoint.getName());
				return false;
			}

			// writable datapoint
			// to be done in ModuleTest
		}

		for (Action action : module.getActions()) {
			if (!checkAction(action, moduleLocation + "/" + action.getName())) {
				System.out.println("invalid Action " + action.getName());
				return false;
			}
		}

		return true;
	}

	private boolean writeDataPoint(Module module, String moduleLocation, DataPoint dataPoint) {
		System.out.println("writeDataPoint(moduleName=" + module.getName() + ", moduleLocation=" + moduleLocation
				+ ", dataPointName=" + dataPoint.getName());

		// current value
		Object currentValue = null;
		try {
			currentValue = ((ValuedDataPoint<Object>) dataPoint).getValue();
		} catch (DataPointException e) {
			e.printStackTrace();
			System.out.println("unable to retrieve current value of DataPoint " + dataPoint.getName());
			return false;
		} catch (AccessException e) {
			e.printStackTrace();
			System.out.println("unable to retrieve current value of DataPoint " + dataPoint.getName());
			return false;
		}
		;

		// at this point, current value was successfully retrieved

		// prepare the newValue
		String newValue = null;

		// based on the type of DataPoint
		if (dataPoint.getDataType().equals(DataType.Boolean)) {
			Boolean currentValueBoolean = (Boolean) currentValue;
			newValue = Boolean.valueOf(!currentValueBoolean.booleanValue()).toString();
		} else if (dataPoint.getDataType().equals(DataType.Integer)) {
			Integer currentValueInteger = (Integer) currentValue;

			// colour
			if ("red".equals(dataPoint.getName()) || "green".equals(dataPoint.getName())
					|| "blue".equals(dataPoint.getName())) {
				// value between 0 and 255
				newValue = String.valueOf(Math.round(Math.random() * 255d));
			}
		}

		if (newValue != null) {
			// set new value through the ipe
			FlexContainer updateFc = new FlexContainer();
			CustomAttribute dataPointCA = new CustomAttribute();
			dataPointCA.setCustomAttributeName(dataPoint.getName());
			dataPointCA
					.setCustomAttributeType("xs:" + ((SimpleType) dataPoint.getDataType().getTypeChoice()).getType());
			dataPointCA.setCustomAttributeValue(newValue);
			updateFc.getCustomAttributes().add(dataPointCA);

			// send request
			ResponsePrimitive response = CSEUtil.updateFlexContainerEntity(cseService, moduleLocation, updateFc);
			if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
				System.out.println("fail to update DataPoint " + dataPoint.getName() + " throught the IPE:"
						+ response.getContent());
				return false;
			}

			// retrieve new value through the DataPoint object
			try {
				currentValue = ((ValuedDataPoint<Object>) dataPoint).getValue();

			} catch (DataPointException e) {
				e.printStackTrace();
				System.out.println("unable to retrieve new value of DataPoint " + dataPoint.getName());
				return false;
			} catch (AccessException e) {
				e.printStackTrace();
				System.out.println("unable to retrieve new value of DataPoint " + dataPoint.getName());
				return false;
			}
			;
			if (!newValue.equals(currentValue.toString())) {
				System.out.println("invalid new Value for DataPoint:" + dataPoint.getName() + "- found =" + currentValue
						+ ",expected:" + newValue);
				return false;
			}

		} else {
			System.out.println(
					"new value is null for DataPoint " + dataPoint.getName() + ", type=" + dataPoint.getDataType());
//			return false;
		}

		return true;
	}

	public boolean checkAction(Action action, String actionLocation) {
		System.out.println("checkAction(name=" + action.getName() + ", location=" + actionLocation + ")");

		ResponsePrimitive response = CSEUtil.retrieveEntity(cseService, actionLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			System.out.println("invalid response status code: " + response.getResponseStatusCode());
			return false;
		}
		FlexContainer actionFlexContainer = (FlexContainer) response.getContent();

		if (!checkContainerDefinition(actionFlexContainer, action.getDefinition())) {
			System.out.println("invalid container definition, expected:" + action.getDefinition() + ", found:"
					+ actionFlexContainer.getContainerDefinition());
			return false;
		}

		if (!checkName(actionFlexContainer, action.getName())) {
			System.out.println("invalid name");
			return false;
		}

		for (String name : action.getArgNames()) {
			CustomAttribute ca = actionFlexContainer.getCustomAttribute(name);
			if (ca == null) {
				System.out.println("no customAttribute for argument " + name);
				return false;
			}
		}

		// execute action
		FlexContainer executionFlexContainer = new FlexContainer();
		for (String name : action.getArgNames()) {
			CustomAttribute ca = new CustomAttribute();
			executionFlexContainer.getCustomAttributes().add(ca);
			ca.setCustomAttributeName(name);
			ca.setCustomAttributeType("xs:" + action.getArg(name).getDataType().getName());
			ca.setCustomAttributeValue("12");
		}
		response = CSEUtil.updateFlexContainerEntity(cseService, actionLocation, executionFlexContainer);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			System.out
					.println("unable to invoke action " + action.getName() + ", reason :" + response.getContent());
			return false;
		}

		return true;
	}

	// Gregory BONNARDEL - 2016 05 20 : TO BE REMOVED
	public boolean checkDataPoint(DataPoint dataPoint, String dataPointLocation) {
		System.out.println("checkDataPoint(name=" + dataPoint.getName() + ", location=" + dataPointLocation + ")");

		// retrieve
		ResponsePrimitive response = CSEUtil.retrieveEntity(cseService, dataPointLocation);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			// KO
			System.out.println("invalid response status code: " + response.getResponseStatusCode());
			return false;
		}
		FlexContainer dataPointFlexContainer = (FlexContainer) response.getContent();

		if (!checkContainerDefinition(dataPointFlexContainer, "org.onem2m.home.datapoint")) {
			System.out.println("invalid container definition");
			return false;
		}

		if (dataPointFlexContainer.getCustomAttributes().size() != 3) {
			System.out.println("invalid number of customAttributes");
			return false;
		}

		if (!checkCustomAttribute(dataPointFlexContainer, "name", dataPoint.getName())) {
			System.out.println("invalid name customAttribute");
			return false;
		}

		if (!checkCustomAttribute(dataPointFlexContainer, "type", "xs:" + dataPoint.getDataType().getName())) {
			System.out.println("invalid name customAttribute");
			return false;
		}

		try {
			Object value = ((ValuedDataPoint<String>) dataPoint).getValue();

			if (!checkCustomAttribute(dataPointFlexContainer, "value", (value != null ? value.toString() : null))) {
				System.out.println("invalid value customAttribute");
				return false;
			}
		} catch (DataPointException e) {
			System.out.println("unable to get value of DataPoint " + dataPoint.getName());
		} catch (AccessException e) {
			System.out.println("unable to get value of DataPoint " + dataPoint.getName());
		}

		// update
		FlexContainer flexContainerToBeUpdated = new FlexContainer();
		CustomAttribute value = new CustomAttribute();
		value.setCustomAttributeName("value");
		value.setCustomAttributeType("xs:string");
		String typedRandomValue = randomValue(dataPoint.getDataType().getName());
		value.setCustomAttributeValue(typedRandomValue);
		flexContainerToBeUpdated.getCustomAttributes().add(value);

		response = CSEUtil.updateFlexContainerEntity(cseService, dataPointLocation, flexContainerToBeUpdated);
		if (!ResponseStatusCode.UPDATED.equals(response.getResponseStatusCode())) {
			// KO
			System.out.println("unable to update value of DataPoint " + dataPointLocation + ", new value ="
					+ typedRandomValue + ", reason:" + response.getContent());
			return false;
		}

		// check new value directly from SDT layer
		try {
			Object dataPointValueObject = ((ValuedDataPoint<Object>) dataPoint).getValue();
			String dataPointValueString;
			if (dataPointValueObject != null) {
				dataPointValueString = dataPointValueObject.toString();
			} else {
				dataPointValueString = null;
			}

			boolean error = false;

			if (dataPointValueString == null) {
				if (typedRandomValue != null) {
					error = true;
				}
			} else {
				if (!dataPointValueString.equals(typedRandomValue)) {
					if (dataPoint.getDataType().getName().equals("enum")) {
						String values = (String) ((EnumDataPoint) dataPoint).getValue();
						if (!values.contains(typedRandomValue)) {
							error = true;
						} // else NO error

					} else {
						error = true;
					}
				}
			}

			if (error) {
				System.out.println("dataPointLocation=" + dataPointLocation + ",expected value=" + typedRandomValue
						+ ", found value=" + dataPointValueString);
				return false;
			}

		} catch (DataPointException e) {
			System.out.println("unable to retrieve value from DataPoint:" + e.getMessage());
			e.printStackTrace();
			return false;
		} catch (AccessException e) {
			System.out.println("unable to retrieve value from DataPoint:" + e.getMessage());
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static String randomValue(String type) {

		Random random = new Random();
		switch (type) {
		case "boolean":
			return Boolean.toString(random.nextBoolean());
		case "integer":
			return Integer.toString(random.nextInt());
		case "float":
			return Float.toString(random.nextFloat());
		case "string":
			return random.toString();
		case "enum":
			return "enum1";
		}

		return null;
	}

}
