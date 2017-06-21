/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.core.entitymapper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.resource.ChildResourceRef;
import org.eclipse.om2m.commons.resource.ContentInstance;

public class ContentInstanceMapper extends
		EntityMapper<ContentInstanceEntity, ContentInstance> {

	@Override
	protected ContentInstance createResource() {
		return new ContentInstance();
	}

	@Override
	protected void mapAttributes(ContentInstanceEntity entity,
			ContentInstance resource, int level, int offset) {
		if (level < 0) {
			return;
		}
		
		// announceableSubordonate attribute
		EntityMapperFactory.getAnnounceableSubordinateMapper().mapAttributes(entity, resource, level, offset);
		
		// ContentInstance attributes
		resource.setContent(entity.getContent());
		resource.setContentInfo(entity.getContentInfo());
		resource.setContentSize(BigInteger.valueOf(entity.getByteSize()));
		resource.setCreator(entity.getCreator());
		resource.setOntologyRef(entity.getOntologyRef());
		resource.setStateTag(entity.getStateTag());
	}
	
	@Override
	protected List<ChildResourceRef> getChildResourceRef(ContentInstanceEntity entity, int level, int offset) {
		return new ArrayList<>();
	}

	@Override
	protected void mapChildResourceRef(ContentInstanceEntity entity,
			ContentInstance resource, int level, int offset) {
	}

	@Override
	protected void mapChildResources(ContentInstanceEntity entity,
			ContentInstance resource, int level, int offset) {
	}

}
