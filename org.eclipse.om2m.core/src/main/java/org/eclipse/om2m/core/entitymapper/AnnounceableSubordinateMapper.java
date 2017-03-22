/**
 * 
 */
package org.eclipse.om2m.core.entitymapper;

import org.eclipse.om2m.commons.entities.AnnounceableSubordinateEntity;
import org.eclipse.om2m.commons.resource.AnnounceableSubordinateResource;

/**
 * @author MPCY8647
 *
 */
public class AnnounceableSubordinateMapper extends EntityMapper<AnnounceableSubordinateEntity, AnnounceableSubordinateResource> {

	@Override
	protected void mapAttributes(AnnounceableSubordinateEntity entity, AnnounceableSubordinateResource resource) {
		// announceTo
		resource.getAnnounceTo().addAll(entity.getAnnounceTo());
		
		// announcedAttributes
		resource.getAnnouncedAttribute().addAll(entity.getAnnouncedAttribute());
		
		// expirationTime
		resource.setExpirationTime(entity.getExpirationTime());
	}

	@Override
	protected void mapChildResourceRef(AnnounceableSubordinateEntity entity, AnnounceableSubordinateResource resource) {
		
	}

	@Override
	protected void mapChildResources(AnnounceableSubordinateEntity entity, AnnounceableSubordinateResource resource) {
		
	}

	@Override
	protected AnnounceableSubordinateResource createResource() {
		return null;
	}

}
