/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FlexContainerFactory;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.flexcontainerservice.ModuleFlexContainerService;
import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.datapoints.AbstractDateDataPoint;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.datapoints.ValuedDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;

public class SDTModuleAdaptor {

    private static Log logger = LogFactory.getLog(SDTModuleAdaptor.class);

	private static final String SEP = "/";
	private static final String SDT_IPE_SUBSCRIPTION_NAME = "SDT_IPE_SUBSCRIPTION";

	private final boolean hasToBeAnnounced;
	private final Module module;
	private final String parentLocation;
	private final String resourceLocation;
	private final String announceCseId;
	private final Map<String, SDTActionAdaptor> actions;
	private ModuleFlexContainerService moduleFlexContainerService;
	private ModuleSDTListener moduleSDTListener;

	public SDTModuleAdaptor(final Module pModule, final String pParentLocation, final String pAnnounceCseId, final boolean hasToBeAnnounced) {
		this.module = pModule;
		this.hasToBeAnnounced = hasToBeAnnounced;
		this.parentLocation = pParentLocation;
		this.resourceLocation = this.parentLocation + SEP + this.module.getName();
		this.announceCseId = pAnnounceCseId;
		actions = new HashMap<>();
	}

	/**
	 * Publish the Module into the SDT tree. A new FlexContainer is created to
	 * represent the SDT Module. FlexContainer is located under parentLocation.
	 * 
	 * @return true if the FlexContainer has been successfully published
	 */
	@SuppressWarnings("unchecked")
	public boolean publishModuleIntoOM2MTree() {
		logger.info("publishModuleFromOM2MTree(name=" + this.module.getName() 
				+ ", parentLocation=" + parentLocation + ")");

		AbstractFlexContainer flexContainer = FlexContainerFactory.getSpecializationFlexContainer(this.module.getShortDefinitionName());
		flexContainer.setName(this.module.getName());
		flexContainer.setContainerDefinition(this.module.getDefinition());
		flexContainer.setLongName(this.module.getLongDefinitionName());
		flexContainer.setShortName(this.module.getShortDefinitionName());
		if (hasToBeAnnounced) {
			flexContainer.getAnnounceTo().add(SEP + announceCseId);	
		}

		// labels
		flexContainer.getLabels().add("name/" + this.module.getName());
		flexContainer.getLabels().add("pid/" + this.module.getPid());
		flexContainer.getLabels().add("device.id/" + this.module.getOwner().getId());
		flexContainer.getLabels().add("device.name/" + this.module.getOwner().getName());
		flexContainer.getLabels().add("object.type/module");
		flexContainer.getLabels().add("cntDef/" + this.module.getDefinition());
		flexContainer.getLabels().add("OTB.CATEGORY/Read");
		flexContainer.getLabels().add("OTB.CATEGORY/Write");
		flexContainer.getLabels().add("OTB.CATEGORY/Notify");

		/// each DataPoint is a custom attribute
		for (DataPoint dp : module.getDataPoints()) {
			CustomAttribute customAttribute = new CustomAttribute();
			String customAttributeName = dp.getShortDefinitionType();
			if (customAttributeName == null) {
				customAttributeName = dp.getName();
			}
			customAttribute.setCustomAttributeName(customAttributeName);
			String value = null;
			try {
				if (dp instanceof AbstractDateDataPoint) {
					value = ((AbstractDateDataPoint) dp).getStringValue();
				} else if (dp instanceof ArrayDataPoint<?>) {
					List<?> values = ((ArrayDataPoint<?>) dp).getValue();
					if (values != null) {
						StringBuffer sb = new StringBuffer();
						boolean first = true;
						for (Object i : values) {
							if (first) first = false;
							else sb.append(",");
							sb.append(i.toString());
						}
						value = sb.toString();
					}
				} else {
					Object val = ((ValuedDataPoint<Object>) dp).getValue();
					if (val != null)
						value = val.toString();
				}
			} catch (DataPointException e) {
				// how to handle this exception?
				// should we stop module publishing step in oneM2M tree ?
				// should we continue to publish module in oneM2M tree ?
				logger.info("Error reading datapoint " + dp + ": " + e.getMessage());
			} catch (AccessException e) {
				// how to handle this exception?
				// should we stop module publishing step in oneM2M tree ?
				// should we continue to publish module in oneM2M tree ?
				logger.info("Error accessing datapoint " + dp + ": " + e.getMessage());
			} catch (Exception e) {
				logger.error("Error in datapoint " + dp, e);
			}
			customAttribute.setCustomAttributeValue(value);

			logger.info("add DataPoint customAttribute(" + customAttribute + ")");
			flexContainer.getCustomAttributes().add(customAttribute);
		}

		// SDT properties are customAttribute of the module FlexContainer
		for (Property sdtProperty : module.getProperties()) {
			if (sdtProperty.getType() != null) {
				
				if ((sdtProperty.getValue() == null) && (sdtProperty.isOptional())) {
					continue;
				}
				
				CustomAttribute caForSdtProperty = new CustomAttribute();
				caForSdtProperty.setCustomAttributeName(sdtProperty.getShortName());
				caForSdtProperty.setCustomAttributeValue(sdtProperty.getValue());

				logger.info("add Property customAttribute(" + caForSdtProperty + ")");
				flexContainer.getCustomAttributes().add(caForSdtProperty);
			}
		}
		logger.info("customAttributes: " + flexContainer.getCustomAttributes());

		ResponsePrimitive resp = CseUtil.sendCreateFlexContainerRequest(flexContainer,
				parentLocation);
		if (! resp.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			logger.error("publishModuleFromOM2MTree(name=" + this.module.getName() 
					+ ", parentLocation=" + parentLocation + ") : failed to publish:" + resp.getContent(),
					null);
			return false;
		}
		AbstractFlexContainer createdFlexContainer = (AbstractFlexContainer) resp.getContent();
		// create a ModuleFlexContainerService
		moduleFlexContainerService = new ModuleFlexContainerService(module, 
				createdFlexContainer.getResourceID());
		moduleFlexContainerService.register();

		// publish actions
		for (Action action : module.getActions()) {
			SDTActionAdaptor actionAdaptor = new SDTActionAdaptor(action, 
					resourceLocation, module, announceCseId, hasToBeAnnounced);
			if (actionAdaptor.publishActionIntoOM2MTree()) {
				actions.put(action.getName(), actionAdaptor);
			} else {
				// in case of any error, unpublish all oneM2M resource related to this module
				unpublishModuleFromOM2MTree();
				return false;
			}
		}

		moduleSDTListener = new ModuleSDTListener(module, resourceLocation);
		moduleSDTListener.register();
		return true;
	}

	public void unpublishModuleFromOM2MTree() {
		logger.info("unpublishModuleFromOM2MTree(name=" + this.module.getName() 
				+ ", location=" + resourceLocation + ")");

		moduleSDTListener.unregister();
		moduleSDTListener = null;

		for (SDTActionAdaptor sdtActionAdaptor : actions.values()) {
			sdtActionAdaptor.unpublishActionFromOM2MTree();
		}
		actions.clear();

		if (moduleFlexContainerService != null) {
			moduleFlexContainerService.unregister();
		}

		// remove Module FlexContainer
		CseUtil.sendDeleteRequest(resourceLocation);
	}

}
