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
package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class SubscriptionDAO extends AbstractDAO<SubscriptionEntity> {
	

	@Override
	public SubscriptionEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(SubscriptionEntity.class, id);
	}
	
	@Override
	public void update(DBTransaction dbTransaction, SubscriptionEntity resource) {
		List<LabelEntity> lbls = processLabels(dbTransaction, resource.getLabelsEntities());
		resource.setLabelsEntities(lbls);
		super.update(dbTransaction, resource);
	}
	
	@Override
	public void delete(DBTransaction dbTransaction, SubscriptionEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		
		// de-associate labels
		List<LabelEntity> labels = resource.getLabelsEntities();
		for (LabelEntity label : labels) {
			label.getLinkedSub().remove(resource);
		}
		
		if (resource.getParentAe() != null) {
			resource.getParentAe().getSubscriptions().remove(resource);
		}
		
		if (resource.getParentAcp() != null) {
			resource.getParentAcp().getChildSubscriptions().remove(resource);
		}
		
		if (resource.getParentAndi() != null) {
			resource.getParentAndi().getSubscriptions().remove(resource);
		}
		
		if (resource.getParentAni() != null) {
			resource.getParentAni().getSubscriptions().remove(resource);
		}
		
		if (resource.getParentCnt() != null) {
			resource.getParentCnt().getSubscriptions().remove(resource);
		}
		
		if (resource.getParentCsb() != null) {
			resource.getParentCsb().getSubscriptions().remove(resource);
		}
		
		if (resource.getParentCsr() != null) {
			resource.getParentCsr().getSubscriptions().remove(resource);
		}
		
		if (resource.getParentFlexCnt() != null) {
			resource.getParentFlexCnt().getSubscriptions().remove(resource);
		}
		
		if (resource.getParentGrp() != null) {
			resource.getParentGrp().getSubscriptions().remove(resource);
		}
		
		if (resource.getParentSch() != null) {
			resource.getParentSch().getSubscriptions().remove(resource);
		}
		
		transaction.getEm().remove(resource);
	}
	
}
