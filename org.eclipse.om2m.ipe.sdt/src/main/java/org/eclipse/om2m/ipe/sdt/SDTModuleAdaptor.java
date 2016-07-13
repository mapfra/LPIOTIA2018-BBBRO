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
import org.onem2m.sdt.impl.DataPointException;

public class SDTModuleAdaptor {

	private static final String SDT_IPE_SUBSCRIPTION_NAME = "SDT_IPE_SUBSCRIPTION";

	private final Module module;

	private final CseService cseService;

	private final String parentLocation;

	private final String resourceLocation;

	private final Map<String, SDTActionAdaptor> actions;

	private ModuleFlexContainerService moduleFlexContainerService;

	private ModuleSDTListener moduleSDTListener;

	public SDTModuleAdaptor(final Module pModule, final CseService pCseService, final String pParentLocation) {
		this.module = pModule;
		this.cseService = pCseService;
		this.parentLocation = pParentLocation;
		this.resourceLocation = this.parentLocation + "/" + this.module.getName();

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
				"publishModuleFromOM2MTree(name=" + this.module.getName() + ", parentLocation=" + parentLocation + ")");

		FlexContainer flexContainer = new FlexContainer();
		flexContainer.setContainerDefinition(this.module.getDefinition());

		// labels
		flexContainer.getLabels().add("name/" + this.module.getName());
		flexContainer.getLabels().add("pid/" + this.module.getPid());
		flexContainer.getLabels().add("device.id/" + this.module.getOwner().getId());
		flexContainer.getLabels().add("device.name/" + this.module.getOwner().getName());
		flexContainer.getLabels().add("object.type/module");
		flexContainer.getLabels().add("OTB.CATEGORY/Read");
		flexContainer.getLabels().add("OTB.CATEGORY/Write");
		flexContainer.getLabels().add("OTB.CATEGORY/Notify");

		/// each DataPoint is a custom attribute
		for (DataPoint d : module.getDataPoints()) {
			CustomAttribute customAttribute = new CustomAttribute();
			customAttribute.setCustomAttributeName(d.getName());
			customAttribute.setCustomAttributeType(SDTUtil.simpleTypeToOneM2MType(d.getDataType().getTypeChoice()));
			Object value = null;
			try {
				if (d instanceof AbstractDateDataPoint) {
					value = ((AbstractDateDataPoint) d).getStringValue();
				} else if (d instanceof ArrayDataPoint<?>) {
					StringBuffer sb = new StringBuffer();
					List<?> values = ((ArrayDataPoint<?>) d).getValue();
					for (Object i : values) {
						sb.append(i.toString());
						sb.append(",");
					}
					if (sb.length() > 0) {
						// remove last comma
						sb.setLength(sb.length() - 1);
						value = sb.toString();
					} else {
						value = "";
					}

				} else {
					value = ((ValuedDataPoint<Object>) d).getValue();
				}
			} catch (DataPointException e) {
				// how to handle this exception?
				// should we stop module publishing step in oneM2M tree ?
				// should we continue to publish module in oneM2M tree ?
			} catch (Exception e) {
				e.printStackTrace();
			}
			customAttribute.setCustomAttributeValue((value != null ? value.toString() : null));

			Logger.getInstance().logInfo(SDTModuleAdaptor.class,
					"add DataPoint customAttribute(name=" + customAttribute.getCustomAttributeName() + ", type="
							+ customAttribute.getCustomAttributeType() + ", value="
							+ customAttribute.getCustomAttributeValue() + ")");

			flexContainer.getCustomAttributes().add(customAttribute);
		}

		// SDT properties are customAttribute of the module FlexContainer
		for (Property sdtProperty : module.getProperties()) {
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

		ResponsePrimitive responsePrimitive = CseUtil.sendCreateFlexContainerRequest(cseService, flexContainer,
				parentLocation, this.module.getName());
		if (!responsePrimitive.getResponseStatusCode().equals(ResponseStatusCode.CREATED)) {
			Logger.getInstance()
					.logError(SDTModuleAdaptor.class,
							"publishModuleFromOM2MTree(name=" + this.module.getName() + ", parentLocation="
									+ parentLocation + ") : failed to publish:" + responsePrimitive.getContent(),
							null);
			return false;
		} else {
			FlexContainer createdFlexContainer = (FlexContainer) responsePrimitive.getContent();
			// create a ModuleFlexContainerService
			moduleFlexContainerService = new ModuleFlexContainerService(module, createdFlexContainer.getResourceID());
			moduleFlexContainerService.register();
		}

		// publish actions
		for (Action action : module.getActions()) {
			SDTActionAdaptor actionAdaptor = new SDTActionAdaptor(cseService, action, resourceLocation, module);
			if (actionAdaptor.publishActionIntoOM2MTree()) {
				actions.put(action.getName(), actionAdaptor);
			} else {
				// in case of any error, unpublish all oneM2M resource related
				// to this module
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
				"unpublishModuleFromOM2MTree(name=" + this.module.getName() + ", location=" + resourceLocation + ")");

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
