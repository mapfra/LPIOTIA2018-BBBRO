/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.MgmtDefinitionTypes;
import org.eclipse.om2m.commons.entities.AreaNwkDeviceInfoAnncEntity;
import org.eclipse.om2m.commons.entities.AreaNwkInfoAnncEntity;
import org.eclipse.om2m.commons.entities.DeviceInfoAnncEntity;
import org.eclipse.om2m.commons.entities.MgmtObjAnncEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class MgmtObjAnncDAO extends AbstractDAO<MgmtObjAnncEntity> {
	
	private AbstractDAO<DeviceInfoAnncEntity> deviceInfoDAO;
	private AbstractDAO<AreaNwkInfoAnncEntity> areaNwkInfoDAO;
	private AbstractDAO<AreaNwkDeviceInfoAnncEntity> areaNwkDeviceInfoDAO;
	
	public MgmtObjAnncDAO() {
		deviceInfoDAO = new AbstractDAO<DeviceInfoAnncEntity>() {
			@Override
			public DeviceInfoAnncEntity find(DBTransaction dbTransaction, Object id) {
				DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
				return transaction.getEm().find(DeviceInfoAnncEntity.class, id);
			}
			@Override
			public void delete(DBTransaction dbTransaction, DeviceInfoAnncEntity resource) {
			}
		};
		
		areaNwkInfoDAO = new AbstractDAO<AreaNwkInfoAnncEntity>() {
			@Override
			public AreaNwkInfoAnncEntity find(DBTransaction dbTransaction, Object id) {
				DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
				return transaction.getEm().find(AreaNwkInfoAnncEntity.class, id);
			}
			@Override
			public void delete(DBTransaction dbTransaction, AreaNwkInfoAnncEntity resource) {
			}
		};
		
		areaNwkDeviceInfoDAO = new AbstractDAO<AreaNwkDeviceInfoAnncEntity>() {
			@Override
			public AreaNwkDeviceInfoAnncEntity find(DBTransaction dbTransaction, Object id) {
				DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
				return transaction.getEm().find(AreaNwkDeviceInfoAnncEntity.class, id);
			}
			@Override
			public void delete(DBTransaction dbTransaction, AreaNwkDeviceInfoAnncEntity resource) {
			}
		};
	}

	@Override
	public void create(DBTransaction dbTransaction, MgmtObjAnncEntity resource) {
		BigInteger mgd = resource.getMgmtDefinition();
		if (mgd.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			areaNwkInfoDAO.create(dbTransaction, (AreaNwkInfoAnncEntity) resource);
		else if (mgd.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			areaNwkDeviceInfoDAO.create(dbTransaction, (AreaNwkDeviceInfoAnncEntity) resource);
		else if (mgd.equals(MgmtDefinitionTypes.DEVICE_INFO))
			deviceInfoDAO.create(dbTransaction, (DeviceInfoAnncEntity) resource);
		else 
			throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public MgmtObjAnncEntity find(DBTransaction dbTransaction, Object id) {
		MgmtObjAnncEntity ret = deviceInfoDAO.find(dbTransaction, id);
		if (ret != null) return ret;
		ret = areaNwkInfoDAO.find(dbTransaction, id);
		if (ret != null) return ret;
		ret = areaNwkDeviceInfoDAO.find(dbTransaction, id);
		if (ret != null) return ret;
		return null;
	}

	@Override
	public void delete(DBTransaction dbTransaction, MgmtObjAnncEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		if (resource.getParentNode() != null)
			resource.getParentNode().removeMgmtObj(resource);
		transaction.getEm().remove(resource);
		// cleaning the cache
		transaction.getEm().getEntityManagerFactory().getCache().evict(NodeEntity.class);
	}

}
