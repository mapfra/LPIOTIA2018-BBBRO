/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.flexcontainerspec.FlexContainerFactory;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.flexcontainerservice.ActionFlexContainerService;
import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.Arg;
import org.eclipse.om2m.sdt.Module;

public class SDTActionAdaptor {

    private static Log logger = LogFactory.getLog(SDTActionAdaptor.class);

	private static final String SEP = "/";

	private final boolean hasToBeAnnounced;
	private final CseService cseService;
	private final String parentLocation;
	private final String resourceLocation;
	private final String resourceName;
	private final Action action;
	private final Module module;
	private final String announceCseId;

	private ActionFlexContainerService actionFlexContainerService;

	public SDTActionAdaptor(final CseService pCseService, final Action pAction, 
			final String pParentLocation, final Module pModule, final String announceCseId, final boolean hasToBeAnnounced) {
		this.cseService = pCseService;
		this.hasToBeAnnounced = hasToBeAnnounced;
		this.action = pAction;
		this.parentLocation = pParentLocation;
		this.resourceName = action.getName();
		this.resourceLocation = parentLocation + SEP + resourceName;
		this.module = pModule;
		this.announceCseId = announceCseId;
	}

	public boolean publishActionIntoOM2MTree() {
		logger.info("publishActionFromOM2MTree(name=" + this.action.getName() 
				+ ", location=" + resourceLocation + ")");

		AbstractFlexContainer actionFlexContainer = FlexContainerFactory.getSpecializationFlexContainer(action.getShortDefinitionName());
		actionFlexContainer.setName(resourceName);
		actionFlexContainer.setContainerDefinition(action.getDefinition());
		actionFlexContainer.setLongName(action.getLongDefinitionName());
		actionFlexContainer.setShortName(action.getShortDefinitionName());
		if (hasToBeAnnounced) {
			actionFlexContainer.getAnnounceTo().add(SEP + announceCseId);
		}
		
		actionFlexContainer.getLabels().add("pid/" + module.getPid());
		actionFlexContainer.getLabels().add("object.type/action");
		actionFlexContainer.getLabels().add("OTB.CATEGORY/Run");

		for (Arg arg : action.getArgs()) {
			CustomAttribute customAttribute = new CustomAttribute();
			customAttribute.setCustomAttributeName(arg.getName());
			customAttribute.setCustomAttributeType(arg.getDataType().getTypeChoice().getOneM2MType());
			actionFlexContainer.getCustomAttributes().add(customAttribute);
		}

		ResponsePrimitive response = CseUtil.sendCreateFlexContainerRequest(cseService, 
				actionFlexContainer, parentLocation);
		if (! response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			logger.error("unable to create a FlexContainer for action " + action.getName() 
					+ ":" + response.getContent(), null);
			return false;
		}
		AbstractFlexContainer createdFlexContainer = (AbstractFlexContainer) response.getContent(); 
		actionFlexContainerService = new ActionFlexContainerService(action, 
				createdFlexContainer.getResourceID());
		actionFlexContainerService.register();

		logger.debug("publishActionFromOM2MTree(name=" + this.action.getName()
				+ ", location=" + resourceLocation + ") - OK");
		return true;
	}

	public void unpublishActionFromOM2MTree() {
		logger.info("unpublishActionFromOM2MTree(name=" + this.action.getName() 
				+ ", location=" + resourceLocation + ")");
		CseUtil.sendDeleteRequest(cseService, resourceLocation);
	}

}
