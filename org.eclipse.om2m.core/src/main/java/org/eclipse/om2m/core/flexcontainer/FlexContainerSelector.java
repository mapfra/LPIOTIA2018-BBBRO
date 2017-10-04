/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.core.flexcontainer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.flexcontainer.service.FlexContainerService;

public class FlexContainerSelector {

	private static final Map<String, FlexContainerService> flexContainerServices = new HashMap<>();

	private static Log LOGGER = LogFactory.getLog(FlexContainerSelector.class);

	/**
	 * Retrieve a FlexContainerService
	 * 
	 * @param flexContainerLocation
	 * @return
	 */
	public static FlexContainerService getFlexContainerService(String flexContainerLocation) {
		LOGGER.debug("getFlexContainerService(flexContainerLocation=" + flexContainerLocation + ")");

		if (flexContainerLocation == null) {
			return null;
		}

		String location;
		if (flexContainerLocation.startsWith("~")) {
			location = flexContainerLocation.substring(1);
		} else {
			location = flexContainerLocation;
		}

		FlexContainerService fcs = null;
		synchronized (flexContainerServices) {
			fcs = flexContainerServices.get(location);
		}

		LOGGER.debug("getFlexContainerService(flexContainerLocation=" + flexContainerLocation + ") - fcs=" + fcs);

		return fcs;

	}

	/**
	 * Add a newly discovery FlexContainerService in the map.
	 * 
	 * @param flexContainerLocation
	 *            location of the flexContainer
	 * @param flexContainerService
	 *            FlexContainerService to be added into the map
	 */
	public static void addFlexContainerService(String flexContainerLocation,
			FlexContainerService flexContainerService) {
		synchronized (flexContainerServices) {
			flexContainerServices.put(flexContainerLocation, flexContainerService);
		}
	}

	/**
	 * Remove a no more available FlexContainerService from the map
	 * 
	 * @param serviceToBeRemoved
	 *            service to be removed
	 */
	public static void removeFlexContainerService(FlexContainerService serviceToBeRemoved) {
		synchronized (flexContainerServices) {
			flexContainerServices.values().remove(serviceToBeRemoved);
		}
	}

}
