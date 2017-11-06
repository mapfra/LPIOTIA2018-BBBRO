/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb;

import org.eclipse.om2m.commons.entities.AccessControlOriginatorEntity;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.PollingChannelEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.RequestEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.persistence.mongodb.dao.AccessControlOriginatorEntityDAO;
import org.eclipse.om2m.persistence.mongodb.dao.AeByAppIdDAO;
import org.eclipse.om2m.persistence.mongodb.dao.ContainerByNameDAO;
import org.eclipse.om2m.persistence.mongodb.dao.CreatedAnnouncedResourceEntityDao;
import org.eclipse.om2m.persistence.mongodb.dao.DescContainerByParentDAO;
import org.eclipse.om2m.persistence.mongodb.dao.LabelEntityDao;
import org.eclipse.om2m.persistence.mongodb.dao.RemoteCSEByIdDAO;
import org.eclipse.om2m.persistence.mongodb.dao.UriMapperEntityDAO;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DAOFactory;


public class DAOFactoryImpl implements DAOFactory {

	@Override
	public DAO<AccessControlPolicyEntity> getAccessControlPolicyDAO() {
		return new DAOImpl<AccessControlPolicyEntity>(AccessControlPolicyEntity.class) {
		};
	}

	@Override
	public DAO<AeEntity> getAeDAO() {
		return new DAOImpl<AeEntity>(AeEntity.class) {
		};
	}

	@Override
	public DAO<ContainerEntity> getContainerDAO() {
		return new DAOImpl<ContainerEntity>(ContainerEntity.class) {
		};
	}

	@Override
	public DAO<FlexContainerEntity> getFlexContainerDAO() {
		return new DAOImpl<FlexContainerEntity>(FlexContainerEntity.class) {
		};
	}

	@Override
	public DAO<ContentInstanceEntity> getContentInstanceDAO() {
		return new DAOImpl<ContentInstanceEntity>(ContentInstanceEntity.class) {
		};
	}

	@Override
	public DAO<CSEBaseEntity> getCSEBaseDAO() {
		return new DAOImpl<CSEBaseEntity>(CSEBaseEntity.class) {
		};
	}

	@Override
	public DAO<GroupEntity> getGroupDAO() {
		return new DAOImpl<GroupEntity>(GroupEntity.class) {
		};
	}

	@Override
	public DAO<LabelEntity> getLabelDAO() {
		return new LabelEntityDao();
	}

	@Override
	public DAO<NodeEntity> getNodeEntityDAO() {
		return new DAOImpl<NodeEntity>(NodeEntity.class) {
		};
	}

	@Override
	public DAO<UriMapperEntity> getUriMapperEntity() {
		return new UriMapperEntityDAO();
	}

	@Override
	public DAO<PollingChannelEntity> getPollingChannelDAO() {
		return new DAOImpl<PollingChannelEntity>(PollingChannelEntity.class) {
		};
	}

	@Override
	public DAO<RemoteCSEEntity> getRemoteCSEDAO() {
		return new DAOImpl<RemoteCSEEntity>(RemoteCSEEntity.class) {
		};
	}

	@Override
	public DAO<RemoteCSEEntity> getRemoteCSEbyCseIdDAO() {
		return new RemoteCSEByIdDAO();
	}

	@Override
	public DAO<RequestEntity> getRequestEntityDAO() {
		return new DAOImpl<RequestEntity>(RequestEntity.class) {
		};
	}

	@Override
	public DAO<SubscriptionEntity> getSubsciptionDAO() {
		return new DAOImpl<SubscriptionEntity>(SubscriptionEntity.class) {
		};
	}

	@Override
	public DAO<AccessControlOriginatorEntity> getAccessControlOriginatorDAO() {
		return new AccessControlOriginatorEntityDAO();
	}

	@Override
	public DAO<AeAnncEntity> getAeAnncDAO() {
		return new DAOImpl<AeAnncEntity>(AeAnncEntity.class) {
		};
	}

	@Override
	public DAO<CreatedAnnouncedResourceEntity> getAnnouncedResourceDAO() {
		return new CreatedAnnouncedResourceEntityDao();
	}

	@Override
	public DAO<FlexContainerAnncEntity> getFlexContainerAnncDAO() {
		return new DAOImpl<FlexContainerAnncEntity>(FlexContainerAnncEntity.class) {
		};
	}
	
	@Override
	public DAO<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationDAO() {
		return  new DAOImpl<DynamicAuthorizationConsultationEntity>(DynamicAuthorizationConsultationEntity.class) {
		};
	}

	@Override
	public DAO<ContentInstanceEntity> getOldestDAO() {
		return  new DAOImpl<ContentInstanceEntity>(ContentInstanceEntity.class) {
		};
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
	public DAO<AeEntity> getAeByAppIdDAO() {
		return new AeByAppIdDAO();
	}

}
