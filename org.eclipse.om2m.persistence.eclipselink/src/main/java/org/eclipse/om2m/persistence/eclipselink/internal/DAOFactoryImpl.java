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
package org.eclipse.om2m.persistence.eclipselink.internal;

import org.eclipse.om2m.commons.entities.AccessControlOriginatorEntity;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.MgmtObjAnncEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.PollingChannelEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.RequestEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.AccessControlOriginatorDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.AccessControlPolicyDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.AeAnncDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.AeByAppIdDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.AeDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.CSEBaseDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.ContainerByNameDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.ContainerDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.ContentInstanceDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.CreatedAnnouncedResourceDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.DescContainerByParentDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.DynamicAuthorizationConsultationDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.FlexContainerAnncDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.FlexContainerDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.GroupDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.LabelDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.MgmtObjAnncDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.MgmtObjDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.NodeAnncDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.NodeDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.OldestDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.PollingChannelDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.RemoteCSEByIdDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.RemoteCSEDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.RequestEntityDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.SubscriptionDAO;
import org.eclipse.om2m.persistence.eclipselink.internal.dao.UriMapperDAO;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DAOFactory;

public class DAOFactoryImpl implements DAOFactory {

	@Override
	public DAO<CSEBaseEntity> getCSEBaseDAO() {
		return new CSEBaseDAO();
	}

	@Override
	public DAO<AccessControlPolicyEntity> getAccessControlPolicyDAO() {
		return new AccessControlPolicyDAO();
	}

	@Override
	public DAO<AeEntity> getAeDAO() {
		return new AeDAO();
	}
	
	@Override
	public DAO<AeEntity> getAeByAppIdDAO() {
		return new AeByAppIdDAO();
	}

	@Override
	public DAO<ContainerEntity> getContainerDAO() {
		return new ContainerDAO();
	}
	
	@Override
	public DAO<FlexContainerEntity> getFlexContainerDAO() {
		return new FlexContainerDAO();
	}
	
	@Override
	public DAO<ContainerEntity> getContainerByResourceNameDAO() {
		return new ContainerByNameDAO();
	}
	
	@Override
	public DAO<ContainerEntity> getDescContainerByParentDAO() {
		return new DescContainerByParentDAO();
	}
	

	@Override
	public DAO<ContentInstanceEntity> getContentInstanceDAO() {
		return new ContentInstanceDAO();
	}

	@Override
	public DAO<SubscriptionEntity> getSubsciptionDAO() {
		return new SubscriptionDAO();
	}

	@Override
	public DAO<RemoteCSEEntity> getRemoteCSEDAO() {
		return new RemoteCSEDAO();
	}
	
	@Override
	public DAO<UriMapperEntity> getUriMapperEntity() {
		return new UriMapperDAO();
	}

	@Override
	public DAO<RemoteCSEEntity> getRemoteCSEbyCseIdDAO() {
		return new RemoteCSEByIdDAO();
	}

	@Override
	public DAO<GroupEntity> getGroupDAO() {
		return new GroupDAO();
	}
	
	@Override
	public DAO<LabelEntity> getLabelDAO() {
		return new LabelDAO();
	}

	@Override
	public DAO<PollingChannelEntity> getPollingChannelDAO() {
		return new PollingChannelDAO();
	}

	@Override
	public DAO<RequestEntity> getRequestEntityDAO() {
		return new RequestEntityDAO();
	}

	@Override
	public DAO<NodeEntity> getNodeDAO() {
		return new NodeDAO();
	}

	@Override
	public DAO<MgmtObjEntity> getMgmtObjDAO() {
		return new MgmtObjDAO();
	}

	@Override
	public DAO<AccessControlOriginatorEntity> getAccessControlOriginatorDAO() {
		return new AccessControlOriginatorDAO();
	}
	
	@Override
	public DAO<AeAnncEntity> getAeAnncDAO() {
		return new AeAnncDAO();
	}
	
	@Override
	public DAO<CreatedAnnouncedResourceEntity> getAnnouncedResourceDAO() {
		return new CreatedAnnouncedResourceDAO();
	}

	@Override
	public DAO<org.eclipse.om2m.commons.entities.FlexContainerAnncEntity> getFlexContainerAnncDAO() {
		return new FlexContainerAnncDAO();
	}
	
	@Override
	public DAO<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationDAO() {
		return new DynamicAuthorizationConsultationDAO();
	}
	
	@Override
	public DAO<ContentInstanceEntity> getOldestDAO() {
		return new OldestDAO();
	}

	@Override
	public DAO<NodeAnncEntity> getNodeAnncDAO() {
		return new NodeAnncDAO();
	}

	@Override
	public DAO<MgmtObjAnncEntity> getMgmtObjAnncDAO() {
		return new MgmtObjAnncDAO();
	}

}
