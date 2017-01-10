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

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.flexcontainerservice.ModuleFlexContainerService;
import org.onem2m.sdt.Action;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.Property;
import org.onem2m.sdt.datapoints.AbstractDateDataPoint;
import org.onem2m.sdt.datapoints.ArrayDataPoint;
import org.onem2m.sdt.datapoints.ValuedDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class SDTModuleAdaptor {

	private static final String SEP = "/";
	private static final String SDT_IPE_SUBSCRIPTION_NAME = "SDT_IPE_SUBSCRIPTION";

	private final Module module;
	private final CseService cseService;
	private final String parentLocation;
	private final String resourceLocation;
	private final String announceCseId;
	private final Map<String, SDTActionAdaptor> actions;
	private ModuleFlexContainerService moduleFlexContainerService;
	private ModuleSDTListener moduleSDTListener;

	public SDTModuleAdaptor(final Module pModule, final CseService pCseService, 
			final String pParentLocation, final String pAnnounceCseId) {
		this.module = pModule;
		this.cseService = pCseService;
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
		Logger.getInstance().logInfo(SDTModuleAdaptor.class,
				"publishModuleFromOM2MTree(name=" + this.module.getName() 
				+ ", parentLocation=" + parentLocation + ")");

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition(this.module.getDefinition());
		if (announceCseId != null) {
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
			customAttribute.setCustomAttributeName(dp.getName());
			customAttribute.setCustomAttributeType(dp.getDataType().getTypeChoice().getOneM2MType());
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
				Logger.getInstance().logInfo(SDTModuleAdaptor.class,
						"Error reading datapoint " + dp + ": " + e.getMessage());
			} catch (AccessException e) {
				// how to handle this exception?
				// should we stop module publishing step in oneM2M tree ?
				// should we continue to publish module in oneM2M tree ?
				Logger.getInstance().logInfo(SDTModuleAdaptor.class,
						"Error accessing datapoint " + dp + ": " + e.getMessage());
			} catch (Exception e) {
				Logger.getInstance().logError(SDTModuleAdaptor.class,
						"Error in datapoint " + dp, e);
			}
			customAttribute.setCustomAttributeValue(value);

			Logger.getInstance().logInfo(SDTModuleAdaptor.class,
					"add DataPoint customAttribute(name=" + customAttribute.getCustomAttributeName() 
					+ ", type=" + customAttribute.getCustomAttributeType() 
					+ ", value=" + customAttribute.getCustomAttributeValue() + ")");

			flexContainer.getCustomAttributes().add(customAttribute);
		}

		// SDT properties are customAttribute of the module FlexContainer
		for (Property sdtProperty : module.getProperties()) {
			if (sdtProperty.getType() != null) {
				CustomAttribute caForSdtProperty = new CustomAttribute();
				caForSdtProperty.setCustomAttributeName(sdtProperty.getName());
				caForSdtProperty.setCustomAttributeValue(sdtProperty.getValue());
				caForSdtProperty.setCustomAttributeType(sdtProperty.getType().getOneM2MType());

				Logger.getInstance().logDebug(SDTDeviceAdaptor.class,
						"create a new CustomAttribute (name=" + caForSdtProperty.getCustomAttributeName()
						+ ", value=" + caForSdtProperty.getCustomAttributeValue() 
						+ ", type=" + caForSdtProperty.getCustomAttributeType() + ")");
				flexContainer.getCustomAttributes().add(caForSdtProperty);
			}
		}

		ResponsePrimitive resp = CseUtil.sendCreateFlexContainerRequest(cseService, flexContainer,
				parentLocation, this.module.getName());
		if (! resp.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			Logger.getInstance().logError(SDTModuleAdaptor.class,
					"publishModuleFromOM2MTree(name=" + this.module.getName() + ", parentLocation="
							+ parentLocation + ") : failed to publish:" + resp.getContent(),
					null);
			return false;
		}
		FlexContainer createdFlexContainer = (FlexContainer) resp.getContent();
		// create a ModuleFlexContainerService
		moduleFlexContainerService = new ModuleFlexContainerService(module, 
				createdFlexContainer.getResourceID());
		moduleFlexContainerService.register();

		// publish actions
		for (Action action : module.getActions()) {
			SDTActionAdaptor actionAdaptor = new SDTActionAdaptor(cseService, action, 
					resourceLocation, module, announceCseId);
			if (actionAdaptor.publishActionIntoOM2MTree()) {
				actions.put(action.getName(), actionAdaptor);
			} else {
				// in case of any error, unpublish all oneM2M resource related to this module
				unpublishModuleFromOM2MTree();
				return false;
			}
		}

		moduleSDTListener = new ModuleSDTListener(module, cseService, resourceLocation);
		moduleSDTListener.register();
		return true;
	}

	public void unpublishModuleFromOM2MTree() {
		Logger.getInstance().logInfo(SDTModuleAdaptor.class,
				"unpublishModuleFromOM2MTree(name=" + this.module.getName() 
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
		CseUtil.sendDeleteRequest(cseService, resourceLocation);
	}

}
