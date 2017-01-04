package org.eclipse.om2m.core.entitymapper;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.constants.ResultContent;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.resource.AEAnnc;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;
import org.eclipse.om2m.commons.resource.Subscription;

public class AeAnncMapper extends EntityMapper<AeAnncEntity, AEAnnc> {

	@Override
	protected void mapAttributes(AeAnncEntity entity, AEAnnc resource) {
		resource.setAEID(entity.getAeid());
		resource.setAppID(entity.getAppID());
		resource.setExpirationTime(entity.getExpirationTime());
		resource.setAppName(entity.getAppName());
		resource.setNodeLink(entity.getNodeLink());
		resource.setLink(entity.getLink());
		resource.setOntologyRef(entity.getOntologyRef());
		for (AccessControlPolicyEntity acpEntity : entity.getAccessControlPolicies()) {
			resource.getAccessControlPolicyIDs().add(acpEntity.getResourceID());
		}

	}

	@Override
	protected void mapChildResourceRef(AeAnncEntity entity, AEAnnc resource) {
		// ChildResourceRef FlexContainerAnnc
		for (FlexContainerAnncEntity flexContainerEntity : entity.getFlexContainerAnncs()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(flexContainerEntity.getName());
			child.setType(flexContainerEntity.getResourceType());
			child.setValue(flexContainerEntity.getResourceID());
			resource.getChildResource().add(child);
		}

		// ChildResourceRef Subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()) {
			ChildResourceRef child = new ChildResourceRef();
			child.setResourceName(sub.getName());
			child.setType(BigInteger.valueOf(ResourceType.SUBSCRIPTION));
			child.setValue(sub.getResourceID());
			resource.getChildResource().add(child);
		}


	}

	@Override
	protected void mapChildResources(AeAnncEntity entity, AEAnnc resource) {

		// ChildResourceRef FlexContainerAnnc
		for (FlexContainerAnncEntity flexContainerEntity : entity.getFlexContainerAnncs()) {
			FlexContainerAnnc fcnt = new FlexContainerAnncMapper().mapEntityToResource(flexContainerEntity,
					ResultContent.ATTRIBUTES);
			resource.getContainerOrContainerAnncOrGroup().add(fcnt);
		}
		// ChildResourceRef Subscription
		for (SubscriptionEntity sub : entity.getSubscriptions()) {
			Subscription subRes = new SubscriptionMapper().mapEntityToResource(sub, ResultContent.ATTRIBUTES);
			resource.getContainerOrContainerAnncOrGroup().add(subRes);
		}
	}

	@Override
	protected AEAnnc createResource() {
		return new AEAnnc();
	}

}
