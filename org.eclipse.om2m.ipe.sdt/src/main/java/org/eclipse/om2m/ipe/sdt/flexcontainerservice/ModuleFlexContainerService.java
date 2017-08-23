/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.flexcontainerservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;
import org.eclipse.om2m.ipe.sdt.Activator;
import org.eclipse.om2m.ipe.sdt.SDTUtil;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.datapoints.ValuedDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class ModuleFlexContainerService implements FlexContainerService {

    private static Log logger = LogFactory.getLog(ModuleFlexContainerService.class);

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
		logger.debug("DataPointFlexContainerService - getCustomAttributeValue(customAttributeName=" 
						+ customAttributeName + ")");
		Property prop = module.getPropertyByShortName(customAttributeName);
		if (prop != null) {
			logger.debug("CustomAttribute is a property, not a datapoint");
			return prop.getValue();
		}

		// retrieve the DataPoint object based on customAttributeName input parameter
		DataPoint dataPoint = module.getDataPointByShortName(customAttributeName);
		if (dataPoint == null) {
			throw new Om2mException("unknown custom attribute " + customAttributeName + " in " + module,
					ResponseStatusCode.INTERNAL_SERVER_ERROR);
		}

		// at this point, we are sure the DataPoint exist.
		String value = null;
		try {
			Object o = ((ValuedDataPoint<?>) dataPoint).getValue();
			String type = dataPoint.getDataType().getTypeChoice().getOneM2MType();
			value = SDTUtil.getStringValue(type, o);
		} catch (AccessException e) {
			e.printStackTrace();
			throw new Om2mException("unable to retrieve value of DataPoint " + dataPoint.getName() + " : " + e.getMessage(),
					ResponseStatusCode.ACCESS_DENIED);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Om2mException("unable to retrieve value of DataPoint " + dataPoint.getName() + " : " + e.getMessage(),
					ResponseStatusCode.INTERNAL_SERVER_ERROR);
		}

		logger.debug("DataPointFlexContainerService - getCustomAttributeValue(customAttributeName=" + customAttributeName
						+ ") - value=" + value);
		return value;
	}

	@Override
	public Map<String, String> getCustomAttributeValues(List<String> customAttributeNames) 
			throws Om2mException {
		try {
			Map<String, String> ret = new HashMap<String, String>();
			List<String> dpNames = new ArrayList<String>();
			for (String name : customAttributeNames) {
				Property prop = module.getPropertyByShortName(name);
				if (prop != null) {
					logger.debug("CustomAttribute " + name + " is a property, not a datapoint");
					ret.put(name, prop.getValue());
				} else if (module.getDataPointByShortName(name) != null) {
					logger.debug("CustomAttribute " + name + " is a datapoint");
					dpNames.add(name);
				} else {
					logger.warn("CustomAttribute " + name + " unknown");
					throw new Om2mException(ResponseStatusCode.INVALID_ARGUMENTS);
				}
			}
			for (Map.Entry<String, Object> entry : module.getDatapointHandler().getValues(dpNames).entrySet()) {
				DataPoint dataPoint = module.getDataPointByShortName(entry.getKey());
				String type = dataPoint.getDataType().getTypeChoice().getOneM2MType();
				ret.put(entry.getKey(), SDTUtil.getStringValue(type, entry.getValue()));
			}
			return ret;
		} catch (AccessException e) {
			e.printStackTrace();
			throw new Om2mException(e.getMessage(), e, ResponseStatusCode.ACCESS_DENIED);
		} catch (DataPointException e) {
			e.printStackTrace();
			throw new Om2mException(e.getMessage(), e, ResponseStatusCode.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Om2mException(ResponseStatusCode.INVALID_ARGUMENTS);
		}
	}

	@Override
	public void setCustomAttributeValues(List<CustomAttribute> customAttributes, 
			RequestPrimitive request) throws Om2mException {
		logger.debug("setCustomAttributeValues()");
		
		Map<String, Object> values = new HashMap<String, Object>();
		for (CustomAttribute ca : customAttributes) {
			DataPoint dataPoint = module.getDataPointByShortName(ca.getCustomAttributeName());
			if (dataPoint == null)
				// no datapoint for this attribute
				// throw a Om2mException
				throw new Om2mException(ResponseStatusCode.INVALID_ARGUMENTS);
			try {
				// retrieve type of the DataPoint
				String type = dataPoint.getDataType().getTypeChoice().getOneM2MType();
				values.put(ca.getCustomAttributeName(), 
						SDTUtil.getValue(ca.getCustomAttributeValue(), type));
			} catch (Exception e) {
				logger.info("KO: " + e.getMessage());
				throw new Om2mException(e.getMessage(), e, 
						ResponseStatusCode.CONTENTS_UNACCEPTABLE);
			}
		}
		try {
			module.getDatapointHandler().setValues(values);
		} catch (AccessException e) {
			logger.warn("KO: " + e.getMessage());
			throw new Om2mException(e.getMessage(), e, ResponseStatusCode.ACCESS_DENIED);
		} catch (Exception e) {
			logger.warn("KO: " + e.getMessage());
			throw new Om2mException(e.getMessage(), e, ResponseStatusCode.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public String getFlexContainerLocation() {
		return flexContainerLocation;
	}

}
