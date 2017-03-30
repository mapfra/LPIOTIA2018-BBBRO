package org.eclipse.om2m.persistence.eclipselink.internal.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerAnncEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.PollingChannelEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.RemoteCseAnncEntity;
import org.eclipse.om2m.commons.entities.RequestEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.entities.ScheduleEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.commons.resource.M2MServiceSubscriptionProfile;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.persistence.eclipselink.internal.DAOFactoryImpl;
import org.eclipse.om2m.persistence.eclipselink.internal.DBServiceJPAImpl;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.util.DynamicAuthorizationConsultationUtil;

public class DynamicAuthorizationConsultationUtilImpl implements DynamicAuthorizationConsultationUtil {

	@Override
	public List<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationConsultations(
			String nonHierarchicalUri) {
		List<DynamicAuthorizationConsultationEntity> daces = new ArrayList<>();

		DBTransactionJPAImpl transaction = new DBTransactionJPAImpl();
		transaction.open();

		// prepare and execute request request
		String req = "SELECT uri FROM " + DBEntities.URI_MAPPER_ENTITY + " uri WHERE uri.nonHierarchicalUri = '"
				+ nonHierarchicalUri + "'";
		TypedQuery<UriMapperEntity> q = transaction.getEm().createQuery(req, UriMapperEntity.class);

		// retrieve result
		UriMapperEntity uriMapperEntity = q.getSingleResult();
		
		transaction.close();

		// load resources and
		if (uriMapperEntity != null) {
			// retrieve resourceType
			int resourceType = uriMapperEntity.getResourceType();

			// retrieve nonHierarchical uri
			String nonHierarchicalResourcerUri = uriMapperEntity.getNonHierarchicalUri();

			// retrieve list of DACES and store them in list
			daces.addAll(getAllDynamicAuthorizationConsultations(nonHierarchicalResourcerUri, resourceType));

		}

		return daces;
	}

	/**
	 * 
	 * @param resourceId
	 * @param resourceType
	 * @return
	 */
	private List<DynamicAuthorizationConsultationEntity> getAllDynamicAuthorizationConsultations(String resourceId,
			int resourceType) {
		List<DynamicAuthorizationConsultationEntity> daces = new ArrayList<>();

		ResourceEntity resourceEntity = null;
		DBTransactionJPAImpl dbTransaction = new DBTransactionJPAImpl();
		dbTransaction.open();

		switch (resourceType) {
		case ResourceType.ACCESS_CONTROL_POLICY:
			AccessControlPolicyEntity acpEntity = DBServiceJPAImpl.getInstance().getDAOFactory()
					.getAccessControlPolicyDAO().find(dbTransaction, resourceId);
			resourceEntity = acpEntity;
			if (acpEntity != null) {
				daces.addAll(acpEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.ACCESS_CONTROL_POLICY_ANNC:
			// TODO ?
			break;
		case ResourceType.AE:
			AeEntity aeEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getAeDAO().find(dbTransaction,
					resourceId);
			resourceEntity = aeEntity;
			if (aeEntity != null) {
				daces.addAll(aeEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.AE_ANNC:
			AeAnncEntity aeAnncEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getAeAnncDAO()
					.find(dbTransaction, resourceId);
			resourceEntity = aeAnncEntity;
			if (aeAnncEntity != null) {
				daces.addAll(aeAnncEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.CONTAINER:
			ContainerEntity containerEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getContainerDAO()
					.find(dbTransaction, resourceId);
			resourceEntity = containerEntity;
			if (containerEntity != null) {
				daces.addAll(containerEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.CONTAINER_ANNC:
			// TODO ?
			break;
		case ResourceType.CONTENT_INSTANCE:
			ContentInstanceEntity contentInstanceEntity = DBServiceJPAImpl.getInstance().getDAOFactory()
					.getContentInstanceDAO().find(dbTransaction, resourceId);
			resourceEntity = contentInstanceEntity;
			if (contentInstanceEntity != null) {
				daces.addAll(contentInstanceEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.CONTENT_INSTANCE_ANNC:
			// TODO ?
			break;
		case ResourceType.CSE_BASE:
			CSEBaseEntity cseBaseEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getCSEBaseDAO()
					.find(dbTransaction, resourceId);
			resourceEntity = cseBaseEntity;
			if (cseBaseEntity != null) {
				daces.addAll(cseBaseEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.DELIVERY:
			// TODO ?
			break;
		case ResourceType.DYNAMIC_AUTHORIZATION_CONSULTATION:
			DynamicAuthorizationConsultationEntity dynamicAuthorizationConsultationEntity = DBServiceJPAImpl
					.getInstance().getDAOFactory().getDynamicAuthorizationDAO().find(dbTransaction, resourceId);
			resourceEntity = dynamicAuthorizationConsultationEntity;
			if (dynamicAuthorizationConsultationEntity != null) {
				daces.addAll(dynamicAuthorizationConsultationEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.EVENT_CONFIG:
			// TODO ?
			break;
		case ResourceType.EXEC_INSTANCE:
			// TODO ?
			break;
		case ResourceType.FLEXCONTAINER:
			FlexContainerEntity flexContainerEntity = DBServiceJPAImpl.getInstance().getDAOFactory()
					.getFlexContainerDAO().find(dbTransaction, resourceId);
			resourceEntity = flexContainerEntity;
			if (flexContainerEntity != null) {
				daces.addAll(flexContainerEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.FLEXCONTAINER_ANNC:
			FlexContainerAnncEntity flexContainerAnncEntity = DBServiceJPAImpl.getInstance().getDAOFactory()
					.getFlexContainerAnncDAO().find(dbTransaction, resourceId);
			resourceEntity = flexContainerAnncEntity;
			if (flexContainerAnncEntity != null) {
				daces.addAll(flexContainerAnncEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.GROUP:
			GroupEntity groupEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getGroupDAO().find(dbTransaction, resourceId);
			resourceEntity = groupEntity;
			if (groupEntity != null) {
				daces.addAll(groupEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.GROUP_ANNC:
			// TODO ?
			break;
		case ResourceType.LOCATION_POLICY:
			// TODO ?
			break;
		case ResourceType.LOCATION_POLICY_ANNC:
			// TODO ?
			break;
		case ResourceType.M2M_SERVICE_SUBSCRIPTION_PROFILE:
			// TODO ?
			break;
		case ResourceType.MGMT_CMD:
			// TODO ?
			break;
		case ResourceType.MGMT_OBJ:
			// TODO ?
			break;
		case ResourceType.MGMT_OBJ_ANNC:
			// TODO ?
			break;
		case ResourceType.NODE:
			NodeEntity nodeEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getNodeEntityDAO().find(dbTransaction, resourceId);
			resourceEntity = nodeEntity;
			if (nodeEntity != null) {
				daces.addAll(nodeEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.NODE_ANNC:
			// TODO ?
			break;
		case ResourceType.POLLING_CHANNEL:
			PollingChannelEntity pollingChannelEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getPollingChannelDAO().find(dbTransaction, resourceId);
			resourceEntity = pollingChannelEntity;
			// no dynamicAuthorizationConsultation
			break;
		case ResourceType.REMOTE_CSE:
			RemoteCSEEntity remoteCseEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getRemoteCSEDAO()
					.find(dbTransaction, resourceId);
			resourceEntity = remoteCseEntity;
			if (remoteCseEntity != null) {
				daces.addAll(remoteCseEntity.getDynamicAuthorizationConsultations());
			}
			break;
		case ResourceType.REMOTE_CSE_ANNC:
			// TODO ?
			break;
		case ResourceType.REQUEST:
			RequestEntity requestEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getRequestEntityDAO().find(dbTransaction, resourceId);
			resourceEntity = requestEntity;
			// no dynamicAuthorizationConsultation
			break;
		case ResourceType.SCHEDULE:
			// TODO ?
			break;
		case ResourceType.SCHEDULE_ANNC:
			// TODO ?
			break;
		case ResourceType.SERVICE_SUBSCRIBED_APP_RULE:
			// TODO ?
			break;
		case ResourceType.SERVICE_SUBSCRIBED_NODE:
			// TODO ?
			break;
		case ResourceType.STATS_COLLECT:
			// TODO ?
			break;
		case ResourceType.STATS_CONFIG:
			// TODO ?
			break;
		case ResourceType.SUBSCRIPTION:
			SubscriptionEntity subscriptionEntity = DBServiceJPAImpl.getInstance().getDAOFactory().getSubsciptionDAO().find(dbTransaction, resourceId);
			resourceEntity = subscriptionEntity;
			if (subscriptionEntity != null) {
				daces.addAll(subscriptionEntity.getDynamicAuthorizationConsultations());
			}
			break;
		}
		
		dbTransaction.close();

		if ((resourceEntity != null) && (daces.isEmpty()) && (resourceEntity.getParentID() != null)) {
			// recursive call on the parent
			daces.addAll(getDynamicAuthorizationConsultations(resourceEntity.getParentID()));
		}

		return daces;
	}

}
