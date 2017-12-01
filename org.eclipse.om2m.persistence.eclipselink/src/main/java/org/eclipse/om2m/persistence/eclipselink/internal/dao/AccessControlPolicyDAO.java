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

import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AccessControlRuleEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

/**
 * DAO for the {@link AccessControlPolicyEntity} resource
 */
public class AccessControlPolicyDAO extends AbstractDAO<AccessControlPolicyEntity> {

	@Override
	public void create(DBTransaction dbTransaction, AccessControlPolicyEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		List<LabelEntity> lbls = processLabels(dbTransaction, resource.getLabelsEntities());
		resource.setLabelsEntities(lbls);
		
		// persist self privilege
		for(AccessControlRuleEntity acre : resource.getSelfPrivileges()) {
			acre.setSelfAccessControlPolicy(resource);
			transaction.getEm().persist(acre);
		}
		
		// persist privileges
		for(AccessControlRuleEntity acre : resource.getPrivileges()) {
			acre.setAccessControlPolicy(resource);
			transaction.getEm().persist(acre);
		}
		
		transaction.getEm().persist(resource);
	}

	@Override
	public AccessControlPolicyEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(AccessControlPolicyEntity.class, id);
	}

	@Override
	public void update(DBTransaction dbTransaction, AccessControlPolicyEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		List<LabelEntity> lbls = processLabels(dbTransaction, resource.getLabelsEntities());
		resource.setLabelsEntities(lbls);
		transaction.getEm().merge(resource);
	}

	@Override
	public void delete(DBTransaction dbTransaction, AccessControlPolicyEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		
		// remove application link
		for (AeEntity ae : resource.getLinkedAes()) {
			ae.getAccessControlPolicies().remove(resource);
		}

		// remove flexContainer link
		for (FlexContainerEntity entity : resource.getLinkedFlexContainers()) {
			entity.getAccessControlPolicies().remove(resource);
		}

		// remove flexContainerA link
		for (FlexContainerAnncEntity entity : resource.getLinkedFlexContainerAs()) {
			entity.getAccessControlPolicies().remove(resource);
		}

		// remove node link
		for (NodeEntity entity : resource.getLinkedNodes()) {
			entity.getAccessControlPolicies().remove(resource);
		}

		// remove mgmt objects link
		for (MgmtObjEntity entity : resource.getMgmtObjEntities()) {
			entity.getAccessControlPolicies().remove(resource);
		}

		if (resource.getParentAE() != null) {
			resource.getParentAE().getChildAccessControlPolicies().remove(resource);
		}
		
		if (resource.getParentCse() != null) {
			resource.getParentCse().getChildAccessControlPolicies().remove(resource);
		}
		
		if (resource.getParentCsr() != null) {
			resource.getParentCsr().getChildAcps().remove(resource);
		}

		transaction.getEm().remove(resource);
//		transaction.getEm().getEntityManagerFactory().getCache().evictAll();
	}

}
