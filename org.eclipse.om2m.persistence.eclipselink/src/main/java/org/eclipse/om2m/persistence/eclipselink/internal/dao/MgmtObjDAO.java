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
import org.eclipse.om2m.commons.entities.AreaNwkDeviceInfoEntity;
import org.eclipse.om2m.commons.entities.AreaNwkInfoEntity;
import org.eclipse.om2m.commons.entities.DeviceInfoEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class MgmtObjDAO extends AbstractDAO<MgmtObjEntity> {
	
	private AbstractDAO<DeviceInfoEntity> deviceInfoDAO;
	private AbstractDAO<AreaNwkInfoEntity> areaNwkInfoDAO;
	private AbstractDAO<AreaNwkDeviceInfoEntity> areaNwkDeviceInfoDAO;
	
	public MgmtObjDAO() {
		deviceInfoDAO = new AbstractDAO<DeviceInfoEntity>() {
			@Override
			public DeviceInfoEntity find(DBTransaction dbTransaction, Object id) {
				DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
				return transaction.getEm().find(DeviceInfoEntity.class, id);
			}
			@Override
			public void delete(DBTransaction dbTransaction, DeviceInfoEntity resource) {
			}
		};
		
		areaNwkInfoDAO = new AbstractDAO<AreaNwkInfoEntity>() {
			@Override
			public AreaNwkInfoEntity find(DBTransaction dbTransaction, Object id) {
				DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
				return transaction.getEm().find(AreaNwkInfoEntity.class, id);
			}
			@Override
			public void delete(DBTransaction dbTransaction, AreaNwkInfoEntity resource) {
			}
		};
		
		areaNwkDeviceInfoDAO = new AbstractDAO<AreaNwkDeviceInfoEntity>() {
			@Override
			public AreaNwkDeviceInfoEntity find(DBTransaction dbTransaction, Object id) {
				DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
				return transaction.getEm().find(AreaNwkDeviceInfoEntity.class, id);
			}
			@Override
			public void delete(DBTransaction dbTransaction, AreaNwkDeviceInfoEntity resource) {
			}
		};
	}

	@Override
	public void create(DBTransaction dbTransaction, MgmtObjEntity resource) {
		BigInteger mgd = resource.getMgmtDefinition();
		if (mgd.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			areaNwkInfoDAO.create(dbTransaction, (AreaNwkInfoEntity) resource);
		else if (mgd.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			areaNwkDeviceInfoDAO.create(dbTransaction, (AreaNwkDeviceInfoEntity) resource);
		else if (mgd.equals(MgmtDefinitionTypes.DEVICE_INFO))
			deviceInfoDAO.create(dbTransaction, (DeviceInfoEntity) resource);
		else 
			throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public MgmtObjEntity find(DBTransaction dbTransaction, Object id) {
		MgmtObjEntity ret = deviceInfoDAO.find(dbTransaction, id);
		if (ret != null) return ret;
		ret = areaNwkInfoDAO.find(dbTransaction, id);
		if (ret != null) return ret;
		ret = areaNwkDeviceInfoDAO.find(dbTransaction, id);
		if (ret != null) return ret;
		return null;
	}

	@Override
	public void delete(DBTransaction dbTransaction, MgmtObjEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		if (resource.getParentNode() != null)
			resource.getParentNode().removeMgmtObj(resource);
		transaction.getEm().remove(resource);
		// cleaning the cache
//		transaction.getEm().getEntityManagerFactory().getCache().evict(NodeEntity.class);
	}

}
