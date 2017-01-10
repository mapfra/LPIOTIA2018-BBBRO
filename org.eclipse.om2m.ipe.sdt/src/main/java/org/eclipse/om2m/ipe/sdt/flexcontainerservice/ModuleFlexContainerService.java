/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.flexcontainerservice;

import java.util.List;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.ipe.sdt.Activator;
import org.eclipse.om2m.ipe.sdt.Logger;
import org.eclipse.om2m.ipe.sdt.SDTUtil;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.datapoints.AbstractDateDataPoint;
import org.eclipse.om2m.sdt.datapoints.ValuedDataPoint;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class ModuleFlexContainerService implements FlexContainerService {

	private final Module module;

	private final String flexContainerLocation;

	private ServiceRegistration serviceRegistration = null;

	public ModuleFlexContainerService(Module pModule, String pFlexContainerLocation) {
		this.module = pModule;
		this.flexContainerLocation = pFlexContainerLocation;
	}

	public void register() {
		if (serviceRegistration != null) {
			return;
		}
		serviceRegistration = Activator.registerFlexContainerService(this);
	}

	public void unregister() {
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
			serviceRegistration = null;
		}
	}

	@Override
	public String getCustomAttributeValue(String customAttributeName) throws Om2mException {
		Logger.getInstance().logDebug(ModuleFlexContainerService.class,
				"DataPointFlexContainerService - getCustomAttributeValue(customAttributeName=" 
						+ customAttributeName + ")");
		Property prop = module.getProperty(customAttributeName);
		if (prop != null) {
			Logger.getInstance().logDebug(ModuleFlexContainerService.class,
					"CustomAttribute is a property, not a datapoint");
			return prop.getValue();
		}

		// retrieve the DataPoint object based on customAttributeName input parameter
		DataPoint dataPoint = module.getDataPoint(customAttributeName);
		if (dataPoint == null) {
			throw new Om2mException("unknown custom attribute " + customAttributeName + " in " + module,
					ResponseStatusCode.INTERNAL_SERVER_ERROR);
		}

		// at this point, we are sure the DataPoint exist.
		String value = null;
		try {
			Object o = ((ValuedDataPoint<?>) dataPoint).getValue();
			if (o == null) {
				value = null;
			} else if (dataPoint instanceof AbstractDateDataPoint) {
				value = ((AbstractDateDataPoint) dataPoint).getStringValue();
			} else {
				value = o.toString();
			}
		} catch (DataPointException e) {
			e.printStackTrace();
			throw new Om2mException("unable to retrieve value of DataPoint " + dataPoint.getName() + " : " + e.getMessage(),
					ResponseStatusCode.INTERNAL_SERVER_ERROR);
		} catch (AccessException e) {
			e.printStackTrace();
			throw new Om2mException("unable to retrieve value of DataPoint " + dataPoint.getName() + " : " + e.getMessage(),
					ResponseStatusCode.ACCESS_DENIED);
		}

		Logger.getInstance().logDebug(ModuleFlexContainerService.class,
				"DataPointFlexContainerService - getCustomAttributeValue(customAttributeName=" + customAttributeName
						+ ") - value=" + value);
		return value;
	}

	@Override
	public void setCustomAttributeValues(List<CustomAttribute> customAttributes, RequestPrimitive requestPrimitive)
			throws Om2mException {
		Logger.getInstance().logDebug(ModuleFlexContainerService.class, "setCustomAttributeValue()");

		for (CustomAttribute ca : customAttributes) {
			DataPoint dataPoint = module.getDataPoint(ca.getCustomAttributeName());
			if (dataPoint != null) {
				// the custom attribute is a dataPoint
				ValuedDataPoint<Object> valuedDataPoint = (ValuedDataPoint<Object>) dataPoint;
				String msg = "setCustomAttributeValue(dataPointName=" + dataPoint.getName() 
						+ ", newValue=" + ca.getCustomAttributeValue() + ") - ";

				// retrieve type of the DataPoint
				String type = dataPoint.getDataType().getTypeChoice().getOneM2MType();
				Object value = null;
				try {
					value = SDTUtil.getValue(ca.getCustomAttributeValue(), type);
				} catch (Exception e) {
					Logger.getInstance().logInfo(ModuleFlexContainerService.class, 
							msg + "KO: " + e.getMessage());
					throw new Om2mException(e.getMessage(), e, ResponseStatusCode.CONTENTS_UNACCEPTABLE);
				}
				try {
					valuedDataPoint.setValue(value);
					Logger.getInstance().logDebug(ModuleFlexContainerService.class, msg + "OK");
				} catch (AccessException e) {
					Logger.getInstance().logDebug(ModuleFlexContainerService.class, 
							msg + "KO: " + e.getMessage());
					throw new Om2mException(e.getMessage(), e, ResponseStatusCode.ACCESS_DENIED);
				} catch (Exception e) {
					Logger.getInstance().logDebug(ModuleFlexContainerService.class, 
							msg + "KO: " + e.getMessage());
					throw new Om2mException(e.getMessage(), e, ResponseStatusCode.INTERNAL_SERVER_ERROR);
				}
			} else {
				// no datapoint for this attribute
				// throw a Om2mException
				throw new Om2mException(ResponseStatusCode.INVALID_ARGUMENTS);
			}
		}
	}

	@Override
	public String getFlexContainerLocation() {
		return flexContainerLocation;
	}

}
