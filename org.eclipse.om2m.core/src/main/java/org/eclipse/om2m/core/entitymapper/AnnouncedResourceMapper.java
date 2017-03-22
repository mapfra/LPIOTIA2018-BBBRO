/**
 * 
 */
package org.eclipse.om2m.core.entitymapper;

import java.util.List;

import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AnnouncedResourceEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.resource.AnnouncedResource;

/**
 * @author MPCY8647
 *
 */
public class AnnouncedResourceMapper extends EntityMapper<AnnouncedResourceEntity, AnnouncedResource> {

	@Override
	protected void mapAttributes(AnnouncedResourceEntity entity, AnnouncedResource resource) {
		// expiration time
		resource.setExpirationTime(entity.getExpirationTime());
		
		// link
		resource.setLink(entity.getLink());
		
		// access control policy ids 
		for (AccessControlPolicyEntity acpe : entity.getAccessControlPolicies()) {
			resource.getAccessControlPolicyIDs().add(acpe.getResourceID());
		}
		
		// dynamic authorization consultation ids
		List<String> dacis = resource.getDynamicAuthorizationConsultationIDs();
		for (DynamicAuthorizationConsultationEntity dace : entity.getDynamicAuthorizationConsultations()) {
			dacis.add(dace.getResourceID());
		}
		
	}

	@Override
	protected void mapChildResourceRef(AnnouncedResourceEntity entity, AnnouncedResource resource) {
	}

	@Override
	protected void mapChildResources(AnnouncedResourceEntity entity, AnnouncedResource resource) {
	}

	@Override
	protected AnnouncedResource createResource() {
		return null;
	}

}
