package org.eclipse.om2m.core.flexcontainer;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.om2m.flexcontainer.service.FlexContainerService;

public class FlexContainerSelector {

	private static final Map<String, FlexContainerService> flexContainerServices = new HashMap<>();

	/**
	 * Retrieve a FlexContainerService
	 * 
	 * @param flexContainerLocation
	 * @return
	 */
	public static FlexContainerService getFlexContainerService(String flexContainerLocation) {
		Logger.getLogger(FlexContainerSelector.class.getName()).log(Level.INFO,
				"getFlexContainerService(flexContainerLocation=" + flexContainerLocation + ")");
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
		
		Logger.getLogger(FlexContainerSelector.class.getName()).log(Level.INFO,
				"getFlexContainerService(flexContainerLocation=" + flexContainerLocation + ") - fcs=" + fcs);
		
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
