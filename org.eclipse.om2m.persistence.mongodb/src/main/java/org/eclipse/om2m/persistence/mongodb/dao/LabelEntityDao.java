/*******************************************************************************
 * Copyright (c) 2014 - 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *******************************************************************************/
package org.eclipse.om2m.persistence.mongodb.dao;

import static com.mongodb.client.model.Filters.eq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.MgmtObjAnncEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeAnncEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.persistence.mongodb.Constants;
import org.eclipse.om2m.persistence.mongodb.DBServiceImpl;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBTransaction;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

public class LabelEntityDao implements DAO<LabelEntity>, Constants {
	
	private static final Log LOGGER = LogFactory.getLog(LabelEntityDao.class);

	@Override
	public void create(DBTransaction dbTransaction, LabelEntity resource) {
		// nothing to do
	}

	@Override
	public LabelEntity find(DBTransaction dbTransaction, Object id) {
		LOGGER.info("LabelEntityDAO.find(id=" + id + ")");
		
		//{"LabelsEntities" : {"label":"object.type/module"}}
		LabelEntity labelEntity = null;
		
		FindIterable<Document> elements = DBServiceImpl.getInstance().getResourceCollection()
				.find(eq("LabelsEntities", eq("label", id)));
		
		if (elements != null) {
			labelEntity = new LabelEntity((String) id);
			for (MongoCursor<Document> cursor = elements.iterator(); cursor.hasNext();) {
				Document element = cursor.next();
				Integer resourceType = element.getInteger(RES_TYPE);
				switch (resourceType) {
				case ResourceType.AE:
					AeEntity aeEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), AeEntity.class);
					labelEntity.getLinkedAe().add(aeEntity);
					break;
				case ResourceType.AE_ANNC:
					AeAnncEntity aeAnncEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), AeAnncEntity.class);
					labelEntity.getLinkedAeA().add(aeAnncEntity);
					break;
				case(ResourceType.CONTENT_INSTANCE):
					ContentInstanceEntity contentInstanceEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), ContentInstanceEntity.class);
					labelEntity.getLinkedCin().add(contentInstanceEntity);
					break;
				case(ResourceType.CONTAINER):
					ContainerEntity containerEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), ContainerEntity.class);
					labelEntity.getLinkedCnt().add(containerEntity);
					break;
				case(ResourceType.GROUP):
					GroupEntity groupEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), GroupEntity.class);
//					labelEntity.getLinkedGroup().add(groupEntity);
					break;
				case(ResourceType.REMOTE_CSE):
					RemoteCSEEntity remoteCseEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), RemoteCSEEntity.class);
					labelEntity.getLinkedCsr().add(remoteCseEntity);
					break;
				case(ResourceType.CSE_BASE):
					CSEBaseEntity cseBaseEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), CSEBaseEntity.class);
//					labelEntity.getLinkedCsb();
					break;
				case (ResourceType.FLEXCONTAINER):
					FlexContainerEntity flexContainerEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), FlexContainerEntity.class);
					labelEntity.getLinkedFcnt().add(flexContainerEntity);
//					result.addAll(labelEntity.getLinkedFcnt());
					break;
				case (ResourceType.FLEXCONTAINER_ANNC):
					FlexContainerAnncEntity flexContainerAnncEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), FlexContainerAnncEntity.class);
					labelEntity.getLinkedFcntA().add(flexContainerAnncEntity);
					break;
				case (ResourceType.ACCESS_CONTROL_POLICY):
					AccessControlPolicyEntity accessControlPolicyEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), AccessControlPolicyEntity.class);
					labelEntity.getLinkedACP().add(accessControlPolicyEntity);
					break;
				case(ResourceType.NODE):
					NodeEntity nodeEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), NodeEntity.class);
					labelEntity.getLinkedNodes().add(nodeEntity);
					break;
				case(ResourceType.NODE_ANNC):
					NodeAnncEntity nodeAnncEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), NodeAnncEntity.class);
					labelEntity.getLinkedNodesA().add(nodeAnncEntity);
					break;
				case(ResourceType.MGMT_OBJ): 
					MgmtObjEntity mgmtObjEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), MgmtObjEntity.class);
					labelEntity.addMgmtObj(mgmtObjEntity);
					break;
				case(ResourceType.MGMT_OBJ_ANNC): 
					MgmtObjAnncEntity mgmtObjAnncEntity = DBServiceImpl.getInstance().getGson().fromJson(element.toJson(), MgmtObjAnncEntity.class);
					labelEntity.addMgmtObjA(mgmtObjAnncEntity);
					break;
				default:
					break;
				}
				
			}
		}
		return labelEntity;
	}

	@Override
	public void update(DBTransaction dbTransaction, LabelEntity resource) {
	}

	@Override
	public void delete(DBTransaction dbTransaction, LabelEntity resource) {
	}

}
