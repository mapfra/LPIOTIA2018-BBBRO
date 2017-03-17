package org.eclipse.om2m.core.entitymapper;

import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.resource.DynamicAuthorizationConsultation;

public class DynamicAuthorizationConsultationMapper extends EntityMapper<DynamicAuthorizationConsultationEntity, DynamicAuthorizationConsultation> {

	@Override
	protected void mapAttributes(DynamicAuthorizationConsultationEntity entity,
			DynamicAuthorizationConsultation resource) {
		// acpIDs
		for (AccessControlPolicyEntity acpEntity : entity.getAccessControlPolicyIDs()) {
			resource.getAccessControlPolicyIDs().add(acpEntity.getResourceID());
		}
		
		// dynamicAuthorizationEnabled
		resource.setDynamicAuthorizationEnabled(entity.getDynamicAuthorizationEnabled());
		
		// dynamicAuthorizationPoA
		resource.getDynamicAuthorisationPoA().addAll(entity.getDynamicAuthorizationPoA());
		
		// dynamicAuthorizationLifetime
		resource.setDynamicAuthorizationLifetime(entity.getDynamicAuthorizationLifetime());
	}

	@Override
	protected void mapChildResourceRef(DynamicAuthorizationConsultationEntity entity,
			DynamicAuthorizationConsultation resource) {
		
	}

	@Override
	protected void mapChildResources(DynamicAuthorizationConsultationEntity entity,
			DynamicAuthorizationConsultation resource) {
	}

	@Override
	protected DynamicAuthorizationConsultation createResource() {
		return new DynamicAuthorizationConsultation();
	}

}
