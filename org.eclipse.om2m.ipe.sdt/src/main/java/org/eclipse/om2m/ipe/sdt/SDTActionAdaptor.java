package org.eclipse.om2m.ipe.sdt;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.CustomAttribute;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.flexcontainerservice.ActionFlexContainerService;
import org.onem2m.sdt.Action;
import org.onem2m.sdt.Arg;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.types.SimpleType;

public class SDTActionAdaptor {

	private final CseService cseService;

	private final String parentLocation;

	private final String resourceLocation;

	private final String resourceName;

	private final Action action;
	
	private final Module module;
	
	private ActionFlexContainerService actionFlexContainerService;

	public SDTActionAdaptor(final CseService pCseService, final Action pAction, final String pParentLocation, final Module pModule) {
		this.cseService = pCseService;
		this.action = pAction;
		this.parentLocation = pParentLocation;
		this.resourceName = action.getName();
		this.resourceLocation = parentLocation + "/" + resourceName;
		this.module = pModule;

	}

	public boolean publishActionIntoOM2MTree() {
		Logger.getInstance().logInfo(SDTActionAdaptor.class,
				"publishActionFromOM2MTree(name=" + this.action.getName() + ", location=" + resourceLocation + ")");

		FlexContainer actionFlexContainer = new FlexContainer();
		actionFlexContainer.setContainerDefinition(action.getDefinition());
		
		actionFlexContainer.getLabels().add("pid/" + module.getPid());
		actionFlexContainer.getLabels().add("object.type/action");
		actionFlexContainer.getLabels().add("OTB.CATEGORY/Run");

		for (Arg argument : action.getArgs()) {
			CustomAttribute customAttribute = new CustomAttribute();
			customAttribute.setCustomAttributeName(argument.getName());
			customAttribute.setCustomAttributeType(
					SDTUtil.simpleTypeToOneM2MType((SimpleType) argument.getDataType().getTypeChoice()));
			actionFlexContainer.getCustomAttributes().add(customAttribute);
		}

		ResponsePrimitive response = CseUtil.sendCreateFlexContainerRequest(cseService, actionFlexContainer,
				parentLocation, resourceName);
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			Logger.getInstance().logError(SDTModuleAdaptor.class,
					"unable to create a FlexContainer for action " + this.action.getName() + ":" + response.getContent(), null);
			return false;
		} else {
			FlexContainer createdFlexContainer = (FlexContainer) response.getContent(); 
			actionFlexContainerService = new ActionFlexContainerService(action, createdFlexContainer.getResourceID());
			actionFlexContainerService.register();
			
		}
		

		Logger.getInstance().logDebug(SDTActionAdaptor.class, "publishActionFromOM2MTree(name=" + this.action.getName()
				+ ", location=" + resourceLocation + ") - OK");
		return true;
	}

	public void unpublishActionFromOM2MTree() {
		Logger.getInstance().logInfo(SDTActionAdaptor.class,
				"unpublishActionFromOM2MTree(name=" + this.action.getName() + ", location=" + resourceLocation + ")");
		CseUtil.sendDeleteRequest(cseService, resourceLocation);
	}

}
