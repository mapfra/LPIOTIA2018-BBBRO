/**
 * 
 */
package org.eclipse.om2m.core.entitymapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.entities.AnnounceableSubordinateEntity;
import org.eclipse.om2m.commons.resource.AnnounceableSubordinateResource;
import org.eclipse.om2m.commons.resource.ChildResourceRef;

/**
 * @author MPCY8647
 *
 */
public class AnnounceableSubordinateMapper extends EntityMapper<AnnounceableSubordinateEntity, AnnounceableSubordinateResource> {

	@Override
	protected void mapAttributes(AnnounceableSubordinateEntity entity, AnnounceableSubordinateResource resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announceTo
		resource.getAnnounceTo().addAll(entity.getAnnounceTo());
		
		// announcedAttributes
		resource.getAnnouncedAttribute().addAll(entity.getAnnouncedAttribute());
		
		// expirationTime
		resource.setExpirationTime(entity.getExpirationTime());
	}

	@Override
	protected List<ChildResourceRef> getChildResourceRef(AnnounceableSubordinateEntity entity, int level, int offset) {
		return new ArrayList<ChildResourceRef>();
	}
	
	@Override
	protected void mapChildResourceRef(AnnounceableSubordinateEntity entity, AnnounceableSubordinateResource resource, int level, int offset) {
		
	}

	@Override
	protected void mapChildResources(AnnounceableSubordinateEntity entity, AnnounceableSubordinateResource resource, int level, int offset) {
		
	}

	@Override
	protected AnnounceableSubordinateResource createResource() {
		return null;
	}

}
