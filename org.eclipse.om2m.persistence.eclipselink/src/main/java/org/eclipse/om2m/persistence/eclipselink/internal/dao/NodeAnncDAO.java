/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
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
		for (LabelEntity label : resource.getLabelsEntities()) {
			label.getLinkedNodesA().remove(resource);
		}
		if (resource.getParentCsb() != null) {
			resource.getParentCsb().getChildAnncNodes().remove(resource);
		}
		if (resource.getParentCsr() != null) {
			resource.getParentCsr().getChildAnncNodes().remove(resource);
		}
		transaction.getEm().remove(resource);
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
