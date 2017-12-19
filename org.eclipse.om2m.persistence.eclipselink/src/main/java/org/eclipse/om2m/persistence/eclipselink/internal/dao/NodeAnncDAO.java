/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class NodeAnncDAO extends AbstractDAO<NodeAnncEntity>{

	@Override
	public NodeAnncEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(NodeAnncEntity.class, id);
	}

	@Override
	public void delete(DBTransaction dbTransaction, NodeAnncEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		transaction.getEm().remove(resource);
		transaction.getEm().getEntityManagerFactory().getCache().evict(CSEBaseEntity.class);
		transaction.getEm().getEntityManagerFactory().getCache().evict(RemoteCSEEntity.class);
	}
	
	@Override
	public void update(DBTransaction dbTransaction, NodeAnncEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		List<LabelEntity> lbls = processLabels(dbTransaction, resource.getLabelsEntities());
		resource.setLabelsEntities(lbls);
		transaction.getEm().merge(resource);
		super.update(dbTransaction, resource);
//		List<LabelEntity> lbls = processLabels(dbTransaction, resource.getLabelsEntities());
//		resource.setLabelsEntities(lbls);
//		super.update(dbTransaction, resource);
	}

}
