/**
 * 
 */
package org.eclipse.om2m.core.entitymapper;

import java.util.List;

import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AnnounceableSubordinateEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.resource.AnnounceableResource;

/**
 * @author MPCY8647
 *
 */
public class AnnounceableSubordonateEntity_AnnounceableResourceMapper
		extends EntityMapper<AnnounceableSubordinateEntity, AnnounceableResource> {

	@Override
	protected void mapAttributes(AnnounceableSubordinateEntity entity, AnnounceableResource resource) {
		// expiration time
		resource.setExpirationTime(entity.getExpirationTime());

		// access control policy ids
		for (AccessControlPolicyEntity acpe : entity.getAccessControlPolicies()) {
			resource.getAccessControlPolicyIDs().add(acpe.getResourceID());
		}

		// dynamic authorization consultation ids
		List<String> dacis = resource.getDynamicAuthorizationConsultationIDs();
		for (DynamicAuthorizationConsultationEntity dace : entity.getDynamicAuthorizationConsultations()) {
			dacis.add(dace.getResourceID());
		}

		// announceTo
		resource.getAnnounceTo().addAll(entity.getAnnounceTo());

		// announcedAttribute
		resource.getAnnouncedAttribute().addAll(entity.getAnnouncedAttribute());
	}

	@Override
	protected void mapChildResourceRef(AnnounceableSubordinateEntity entity, AnnounceableResource resource) {
	}

	@Override
	protected void mapChildResources(AnnounceableSubordinateEntity entity, AnnounceableResource resource) {
	}

	@Override
	protected AnnounceableResource createResource() {
		return null;
	}

}
