/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.onem2m.sdt.Device;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.Property;

public class SDTDeviceAdaptor {

	private final String DEVICE_PREFIX = "DEVICE_";
	
	private final String parentLocation;

	private final String resourceLocation;

	private final String resourceName;

	private final Device device;
	
	private final String adminAcpResource;

	private final CseService cseService;

	private boolean isPublished = false;

	private final Map<Module, SDTModuleAdaptor> modules;

	/**
	 * 
	 * @param pParentLocation
	 * @param pResourceName
	 * @param pDevice
	 */
	public SDTDeviceAdaptor(final String pParentLocation, final Device pDevice, final CseService pCseService, final String pAdminAcpResource) {
		this.parentLocation = pParentLocation;
		this.device = pDevice;
		this.resourceName = DEVICE_PREFIX + device.getId();
		this.resourceLocation = parentLocation + "/" + resourceName;
		this.cseService = pCseService;

		this.adminAcpResource = pAdminAcpResource;

		modules = new HashMap<>();
	}

	/**
	 * Publish the SDT Device as a FlexContainer entity into the oneM2M tree
	 */
	public boolean publishIntoOM2MTree() {

		Logger.getInstance().logInfo(SDTDeviceAdaptor.class,
				"publishIntoOM2MTree(flexContainerName=" + resourceName + ", parentLocation:" + parentLocation);
		
		FlexContainer flexContainer = new FlexContainer();
		// set container definition with the value of the Device definition
		flexContainer.setContainerDefinition(device.getDefinition());
		flexContainer.getAccessControlPolicyIDs().add(adminAcpResource);
		
		// labels
		flexContainer.getLabels().add("id/" + this.device.getId());
		flexContainer.getLabels().add("name/" + this.device.getName());
		flexContainer.getLabels().add("pid/" + this.device.getPid());
		flexContainer.getLabels().add("object.type/device");
		flexContainer.getLabels().add("OTB.CATEGORY/Read");

		// if we reach this point, we are sure the FlexContainer resource has
		// been created into the oneM2M tree
		isPublished = true;

		// SDT properties are customAttribute of the device FlexContainer
		for (Property sdtProperty : device.getProperties()) {
			Logger.getInstance().logDebug(SDTDeviceAdaptor.class, "handle SDT Property (name=" + sdtProperty.getName()
					+ ", value=" + sdtProperty.getValue() + ", type=" + sdtProperty.getType() + ")");

			if (sdtProperty.getType() != null) {
				CustomAttribute customAttributeForSdtProperty = new CustomAttribute();
				customAttributeForSdtProperty.setCustomAttributeName(sdtProperty.getName());
				customAttributeForSdtProperty.setCustomAttributeValue(sdtProperty.getValue());
				customAttributeForSdtProperty
						.setCustomAttributeType(SDTUtil.simpleTypeToOneM2MType(sdtProperty.getType()));

				Logger.getInstance().logDebug(SDTDeviceAdaptor.class,
						"create a new CustomAttribute (name=" + customAttributeForSdtProperty.getCustomAttributeName()
								+ ", value=" + customAttributeForSdtProperty.getCustomAttributeValue() + ", type="
								+ customAttributeForSdtProperty.getCustomAttributeType() + ")");
				flexContainer.getCustomAttributes().add(customAttributeForSdtProperty);
			}
		}
		
		ResponsePrimitive response = CseUtil.sendCreateFlexContainerRequest(cseService, flexContainer, parentLocation,
				resourceName);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			Logger.getInstance().logError(SDTDeviceAdaptor.class, "unable to create a FlexContainer for SDT Device "
					+ resourceName + " : " + response.getContent(), null);
			return false;
		}

		// Modules (must be done now because Device FlexContainer is the parent
		// of each Module
		for (Module module : this.device.getModules()) {
			SDTModuleAdaptor sdtModuleAdaptor = new SDTModuleAdaptor(module, cseService, resourceLocation);
			if (sdtModuleAdaptor.publishModuleIntoOM2MTree()) {
				modules.put(module, sdtModuleAdaptor);
			} else {
				Logger.getInstance().logError(SDTDeviceAdaptor.class, "unable to publish module " + module.getName(), null);
				
				// unpublish all resources related to this SDT Device
				unpublishIntoOM2MTree();
				
				return false;
			}
			
		}

		return true;

	}

	/**
	 * Unpublish oneM2M resource representing the SDT Device.
	 */
	public void unpublishIntoOM2MTree() {
		Logger.getInstance().logInfo(SDTDeviceAdaptor.class,
				"unpublish SDT Device (flexContainerLocation=" + resourceLocation + ")");

		for (SDTModuleAdaptor module : modules.values()) {
			module.unpublishModuleFromOM2MTree();
		}

		// send DELETE request for Device FlexContainer
		CseUtil.sendDeleteRequest(cseService, resourceLocation);
		
	}

}
