package org.eclipse.om2m.persistence.eclipselink.internal.dao;

import java.util.List;

import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.AreaNwkDeviceInfoEntity;
import org.eclipse.om2m.commons.entities.AreaNwkInfoEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.DeviceInfoEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.NodeEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.ScheduleEntity;
import org.eclipse.om2m.persistence.eclipselink.internal.DBTransactionJPAImpl;
import org.eclipse.om2m.persistence.service.DBTransaction;

public class DynamicAuthorizationConsultationDAO extends AbstractDAO<DynamicAuthorizationConsultationEntity> {

	@Override
	public DynamicAuthorizationConsultationEntity find(DBTransaction dbTransaction, Object id) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		return transaction.getEm().find(DynamicAuthorizationConsultationEntity.class, id);
	}
	
	@Override
	public void update(DBTransaction dbTransaction, DynamicAuthorizationConsultationEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		List<LabelEntity> lbls = processLabels(dbTransaction, resource.getLabelsEntities());
		resource.setLabelsEntities(lbls);
		transaction.getEm().merge(resource);
	}

	@Override
	public void delete(DBTransaction dbTransaction, DynamicAuthorizationConsultationEntity resource) {
		DBTransactionJPAImpl transaction = (DBTransactionJPAImpl) dbTransaction;
		if (resource.getParentCseBase() != null) {
			resource.getParentCseBase().getChildDynamicAuthorizationConsultation().remove(resource);
			transaction.getEm().merge(resource.getParentCseBase());
		}
		if (resource.getParentAe() != null) {
			resource.getParentAe().getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(resource.getParentAe());
		}
		if (resource.getParentRemoteCse() != null) {
			resource.getParentRemoteCse().getChildDynamicAuthorizationConsultation().remove(resource);
			transaction.getEm().merge(resource.getParentRemoteCse());
		}
		
		// remove relation with ACPs
		for(AccessControlPolicyEntity acpe : resource.getAccessControlPolicies()) {
			acpe.getLinkedDynamicAuthorizationConsultation().remove(resource);
			transaction.getEm().merge(acpe);
		}
		
		// remove relation with DynamicAuthorizationConsultation
		for(DynamicAuthorizationConsultationEntity dace : resource.getDynamicAuthorizationConsultations()) {
			dace.getLinkedDynamicAuthorizationConsultationEntity().remove(resource);
			transaction.getEm().merge(dace);
		}

		// remove link with ACPs
		for(AccessControlPolicyEntity acpe : resource.getLinkedAccessControlPolicyEntities()) {
			acpe.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(acpe);
		}
		
		// remove link with AeAnnc
		for(AeAnncEntity aae : resource.getLinkedAeAnncEntities()) {
			aae.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(aae);
		}
		
		// remove link with Ae
		for(AeEntity ae : resource.getLinkedAeEntities()) {
			ae.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(ae);
		}
		
		// remove link with AreaNwkDeviceInfo
		for(AreaNwkDeviceInfoEntity andie : resource.getLinkedAreaNwkDeviceInfoEntities()) {
			andie.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(andie);
		}
		
		// remove link with AreaNwkInfo 
		for(AreaNwkInfoEntity anie : resource.getLinkedAreaNwkInfoEntities()) {
			anie.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(anie);
		}
		
		// remove link with DeviceInfo
		for(DeviceInfoEntity dvie : resource.getLinkedDeviceInfoEntities()) {
			dvie.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(dvie);
		}
		
		// remove link with Container
		for(ContainerEntity ce : resource.getLinkedContainerEntities()) {
			ce.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(ce);
		}
		
		// remove link with ContentInstance
		for(ContentInstanceEntity cie : resource.getLinkedContentInstanceEntites()) {
			cie.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(cie);
		}
		
		// remove link with CseBase
		for(CSEBaseEntity cbe : resource.getLinkedCseBaseEntities()) {
			cbe.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(cbe);
		}
		
		// remove link with DynamicAuthorizationConsultation
		for(DynamicAuthorizationConsultationEntity dace : resource.getLinkedDynamicAuthorizationConsultationEntity()) {
			dace.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(dace);
		}
		
		// remove link with flexContainerAnnc 
		for(FlexContainerAnncEntity fae : resource.getLinkedFlexContainerAnncEntities()) {
			fae.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(fae);
		}
		
		// remove link with flexContainer
		for(FlexContainerEntity fe : resource.getLinkedFlexContainerEntites()) {
			fe.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(fe);
		}
		
		// remove link with group
		for(GroupEntity ge : resource.getLinkedGroupEntities()) {
			ge.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(ge);
		}
		
		// remove link with node
		for(NodeEntity ne : resource.getLinkedNodeEntities()) {
			ne.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(ne);
		}
		
		// remove link with RemoteCSE
		for(RemoteCSEEntity rce : resource.getLinkedRemoteCSEEntities()) {
			rce.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(rce);
		}
		
		// remove link with Schedule
		for(ScheduleEntity se : resource.getLinkedScheduleEntities()) {
			se.getDynamicAuthorizationConsultations().remove(resource);
			transaction.getEm().merge(se);
		}
		
		transaction.getEm().remove(resource);
		transaction.getEm().flush();
	}

}
