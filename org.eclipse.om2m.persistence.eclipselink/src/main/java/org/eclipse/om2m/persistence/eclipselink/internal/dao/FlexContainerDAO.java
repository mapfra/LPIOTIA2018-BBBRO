/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.RemoteCseAnncEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class FlexContainerDAO extends AbstractDAO<FlexContainerEntity>{


	@Override
	public FlexContainerEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(FlexContainerEntity.class, id);
	}

	@Override
	public void delete(DBTransaction dbTransaction, FlexContainerEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		
		// de-associate labels
		List<LabelEntity> labels = resource.getLabelsEntities();
		for (LabelEntity label : labels) {
			label.getLinkedFcnt().remove(resource);
		}
		
		transaction.getEm().remove(resource);
		transaction.getEm().getEntityManagerFactory().getCache().evict(CSEBaseEntity.class);
		transaction.getEm().getEntityManagerFactory().getCache().evict(AeEntity.class);
		transaction.getEm().getEntityManagerFactory().getCache().evict(RemoteCSEEntity.class);
		transaction.getEm().getEntityManagerFactory().getCache().evict(RemoteCseAnncEntity.class);
		transaction.getEm().getEntityManagerFactory().getCache().evict(AeAnncEntity.class);
		transaction.getEm().getEntityManagerFactory().getCache().evict(LabelEntity.class);
	}

}
