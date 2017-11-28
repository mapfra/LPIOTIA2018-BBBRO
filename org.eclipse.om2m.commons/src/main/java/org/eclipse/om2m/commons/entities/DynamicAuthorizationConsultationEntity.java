/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.entities;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.constants.MgmtDefinitionTypes;
import org.eclipse.om2m.commons.constants.ShortName;

@Entity(name = DBEntities.DYNAMIC_AUTHORIZATION_CONSULTATION_ENTITY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DynamicAuthorizationConsultationEntity extends RegularResourceEntity {
	
	/** accessControlPolicyIDs = acpi */ 
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.DAC_ACP_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<AccessControlPolicyEntity> accessControlPolicyIDs;
	
	/** dynamicAuthorizationConsultationIDs = daci */
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedDynamicAuthorizationConsultationEntity")
	@JoinTable(
			name = DBEntities.DAC_DACCHILD_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DACCHILD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultations;
	
	// dynamicAuthorizationConsultation
	/** dynamicAuthorizationEnabled = dae */
	@Column(name=ShortName.DYNAMIC_AUTHORIZATION_ENABLED)
	private Boolean dynamicAuthorizationEnabled;
	
	/** dynamicAuthorizationLifetime = dal */
	@Column(name=ShortName.DYNAMIC_AUTHORIZATION_LIFETIME)
	private String dynamicAuthorizationLifetime;

	/** dynamicAuthorizationPoA = dap */ 
	@Column(name=ShortName.DYNAMIC_AUTHORIZATION_PoA)
	private List<String> dynamicAuthorizationPoA;
	
	
	// parent relationship
	/** CSEBaseEntity parent */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=CSEBaseEntity.class)
	@JoinTable(
			name=DBEntities.CSEB_CHILDDAC_JOIN,
			inverseJoinColumns={@JoinColumn(name=DBEntities.CSEB_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			joinColumns={@JoinColumn(name=DBEntities.DAC_JOINID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	private CSEBaseEntity parentCseBase;
	
	/** RemoteCseEntity parent */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=RemoteCSEEntity.class)
	@JoinTable(
			name=DBEntities.CSR_DACCHILD_JOIN,
			joinColumns={@JoinColumn(name=DBEntities.DAC_JOINID, referencedColumnName=ShortName.RESOURCE_ID)},
			inverseJoinColumns={@JoinColumn(name=DBEntities.CSR_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	private RemoteCSEEntity parentRemoteCse;
	
	/** AeEntity parent */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=AeEntity.class)
	@JoinTable(
			name=DBEntities.AE_DACCHILD_JOIN,
			joinColumns={@JoinColumn(name=DBEntities.DAC_JOINID, referencedColumnName=ShortName.RESOURCE_ID)},
			inverseJoinColumns={@JoinColumn(name=DBEntities.AE_JOINID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	private AeEntity parentAe;
	
	
	
	// relationship between oneM2M entities
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinTable(
			name=DBEntities.CSEB_DAC_JOIN,
			joinColumns={@JoinColumn(name=DBEntities.DAC_JOINID, referencedColumnName=ShortName.RESOURCE_ID)},
			inverseJoinColumns={@JoinColumn(name=DBEntities.CSEB_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	private List<CSEBaseEntity> linkedCseBaseEntities;
	
	/** link with DynamicAuthorizationConsultationEntity entity that uses a DynamicAuthorizationConsultationEntity to check rights */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.DAC_DACCHILD_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DACCHILD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<DynamicAuthorizationConsultationEntity> linkedDynamicAuthorizationConsultationEntity;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.ACP_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<AccessControlPolicyEntity> linkedAccessControlPolicyEntities;

	@ManyToMany(fetch=FetchType.LAZY/*, mappedBy="dynamicAuthorizationConsultations"*/)
	@JoinTable(
			name = DBEntities.AE_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.AE_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<AeEntity> linkedAeEntities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.CNT_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.CNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<ContainerEntity> linkedContainerEntities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.CIN_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.CIN_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<ContentInstanceEntity> linkedContentInstanceEntites;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.FCNT_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.FCNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<FlexContainerEntity> linkedFlexContainerEntites;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.GRP_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.GRP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<GroupEntity> linkedGroupEntities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.ANDI_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ANDI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<AreaNwkDeviceInfoEntity> linkedAreaNwkDeviceInfoEntities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.ANI_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ANI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<AreaNwkInfoEntity> linkedAreaNwkInfoEntities;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.DVI_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DVI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<DeviceInfoEntity> linkedDeviceInfoEntities;

	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.NOD_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<NodeEntity> linkedNodeEntities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.CSR_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.CSR_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<RemoteCSEEntity> linkedRemoteCSEEntities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.SCH_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.SCH_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<ScheduleEntity> linkedScheduleEntities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.FCNTA_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.FCNTA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<FlexContainerAnncEntity> linkedFlexContainerAnncEntities;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.AEANNC_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.AEANNC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<AeAnncEntity> linkedAeAnncEntities;
	
	
	public List<AccessControlPolicyEntity> getAccessControlPolicies() {
		if (accessControlPolicyIDs == null) {
			accessControlPolicyIDs = new ArrayList<>();
		}
		return accessControlPolicyIDs;
	}

	public void setAccessControlPolicies(List<AccessControlPolicyEntity> accessControlPolicyIDs) {
		this.accessControlPolicyIDs = accessControlPolicyIDs;
	}
	
	public Boolean getDynamicAuthorizationEnabled() {
		return dynamicAuthorizationEnabled;
	}

	public void setDynamicAuthorizationEnabled(Boolean dynamicAuthorizationEnabled) {
		this.dynamicAuthorizationEnabled = dynamicAuthorizationEnabled;
	}

	public String getDynamicAuthorizationLifetime() {
		return dynamicAuthorizationLifetime;
	}

	public void setDynamicAuthorizationLifetime(String dynamicAuthorizationLifetime) {
		this.dynamicAuthorizationLifetime = dynamicAuthorizationLifetime;
	}

	public List<String> getDynamicAuthorizationPoA() {
		if (dynamicAuthorizationPoA == null) {
			dynamicAuthorizationPoA = new ArrayList<>();
		}
		return dynamicAuthorizationPoA;
	}

	public void setDynamicAuthorizationPoA(List<String> dynamicAuthorizationPoA) {
		this.dynamicAuthorizationPoA = dynamicAuthorizationPoA;
	}

	public List<DynamicAuthorizationConsultationEntity> getLinkedDynamicAuthorizationConsultationEntity() {
		if (linkedDynamicAuthorizationConsultationEntity == null) {
			linkedDynamicAuthorizationConsultationEntity = new ArrayList<>();
		}
		return linkedDynamicAuthorizationConsultationEntity;
	}

	public void setLinkedDynamicAuthorizationConsultationEntity(
			List<DynamicAuthorizationConsultationEntity> linkedDynamicAuthorizationConsultationEntity) {
		this.linkedDynamicAuthorizationConsultationEntity = linkedDynamicAuthorizationConsultationEntity;
	}

	public CSEBaseEntity getParentCseBase() {
		return parentCseBase;
	}

	public void setParentCseBase(CSEBaseEntity parentCseBase) {
		this.parentCseBase = parentCseBase;
	}

	public RemoteCSEEntity getParentRemoteCse() {
		return parentRemoteCse;
	}

	public void setParentRemoteCse(RemoteCSEEntity parentRemoteCse) {
		this.parentRemoteCse = parentRemoteCse;
	}

	public AeEntity getParentAe() {
		return parentAe;
	}

	public void setParentAe(AeEntity parentAe) {
		this.parentAe = parentAe;
	}
	
	@Override
	/**
	 * Retrieve linked dynamicAuthorizationConsultations
	 */
	public List<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationConsultations() {
		if (dynamicAuthorizationConsultations == null) {
			dynamicAuthorizationConsultations = new ArrayList<>();
		}
		return dynamicAuthorizationConsultations;
	}
	
	@Override
	/**
	 * Set linked dynamicAuthorizationConsultations
	 */
	public void setDynamicAuthorizationConsultations(List<DynamicAuthorizationConsultationEntity> list) {
		this.dynamicAuthorizationConsultations = list;
	}

	public List<AccessControlPolicyEntity> getLinkedAccessControlPolicyEntities() {
		if (linkedAccessControlPolicyEntities == null) {
			linkedAccessControlPolicyEntities = new ArrayList<>();
		}
		return linkedAccessControlPolicyEntities;
	}

	public void setLinkedAccessControlPolicyEntities(List<AccessControlPolicyEntity> linkedAccessControlPolicyEntities) {
		this.linkedAccessControlPolicyEntities = linkedAccessControlPolicyEntities;
	}

	public List<AeEntity> getLinkedAeEntities() {
		if (linkedAeEntities == null) {
			linkedAeEntities = new ArrayList<>();
		}
		return linkedAeEntities;
	}

	public void setLinkedAeEntities(List<AeEntity> linkedAeEntities) {
		this.linkedAeEntities = linkedAeEntities;
	}

	/**
	 * @return the linkedContainerEntities
	 */
	public List<ContainerEntity> getLinkedContainerEntities() {
		if (linkedContainerEntities == null) {
			linkedContainerEntities = new ArrayList<>();
		}
		return linkedContainerEntities;
	}

	/**
	 * @param linkedContainerEntities the linkedContainerEntities to set
	 */
	public void setLinkedContainerEntities(List<ContainerEntity> linkedContainerEntities) {
		this.linkedContainerEntities = linkedContainerEntities;
	}

	/**
	 * @return the linkedContentInstanceEntites
	 */
	public List<ContentInstanceEntity> getLinkedContentInstanceEntites() {
		if (linkedContentInstanceEntites == null) {
			linkedContentInstanceEntites = new ArrayList<>();
		}
		return linkedContentInstanceEntites;
	}

	/**
	 * @param linkedContentInstanceEntites the linkedContentInstanceEntites to set
	 */
	public void setLinkedContentInstanceEntites(List<ContentInstanceEntity> linkedContentInstanceEntites) {
		this.linkedContentInstanceEntites = linkedContentInstanceEntites;
	}

	/**
	 * @return the linkedFlexContainerEntites
	 */
	public List<FlexContainerEntity> getLinkedFlexContainerEntites() {
		if (linkedFlexContainerEntites == null) {
			linkedFlexContainerEntites = new ArrayList<>();
		}
		return linkedFlexContainerEntites;
	}

	/**
	 * @param linkedFlexContainerEntites the linkedFlexContainerEntites to set
	 */
	public void setLinkedFlexContainerEntites(List<FlexContainerEntity> linkedFlexContainerEntites) {
		this.linkedFlexContainerEntites = linkedFlexContainerEntites;
	}

	/**
	 * @return the linkedGroupEntities
	 */
	public List<GroupEntity> getLinkedGroupEntities() {
		if (linkedGroupEntities == null) {
			linkedGroupEntities = new ArrayList<>();
		}
		return linkedGroupEntities;
	}

	/**
	 * @param linkedGroupEntities the linkedGroupEntities to set
	 */
	public void setLinkedGroupEntities(List<GroupEntity> linkedGroupEntities) {
		this.linkedGroupEntities = linkedGroupEntities;
	}

	/**
	 * @return the linkedMgmtObjEntities
	 */
	public List<AreaNwkDeviceInfoEntity> getLinkedAreaNwkDeviceInfoEntities() {
		if (linkedAreaNwkDeviceInfoEntities == null) {
			linkedAreaNwkDeviceInfoEntities = new ArrayList<>();
		}
		return linkedAreaNwkDeviceInfoEntities;
	}

	/**
	 * @param linkedMgmtObjEntities the linkedMgmtObjEntities to set
	 */
	public void setLinkedAreaNwkDeviceInfoEntities(List<AreaNwkDeviceInfoEntity> linkedMgmtObjEntities) {
		this.linkedAreaNwkDeviceInfoEntities = linkedMgmtObjEntities;
	}

	/**
	 * @return the linkedAreaNwkInfoEntities
	 */
	public List<AreaNwkInfoEntity> getLinkedAreaNwkInfoEntities() {
		if(linkedAreaNwkInfoEntities == null) {
			linkedAreaNwkInfoEntities = new ArrayList<>();
		}
		return linkedAreaNwkInfoEntities;
	}

	/**
	 * @param linkedAreaNwkInfoEntities the linkedAreaNwkInfoEntities to set
	 */
	public void setLinkedAreaNwkInfoEntities(List<AreaNwkInfoEntity> linkedAreaNwkInfoEntities) {
		this.linkedAreaNwkInfoEntities = linkedAreaNwkInfoEntities;
	}

	/**
	 * @return the linkedMgmtObjEntities
	 */
	public List<DeviceInfoEntity> getLinkedDeviceInfoEntities() {
		if (linkedDeviceInfoEntities == null) {
			linkedDeviceInfoEntities = new ArrayList<>();
		}
		return linkedDeviceInfoEntities;
	}

	/**
	 * @param linkedMgmtObjEntities the linkedMgmtObjEntities to set
	 */
	public void setLinkedDeviceInfoEntities(List<DeviceInfoEntity> linkedMgmtObjEntities) {
		this.linkedDeviceInfoEntities = linkedMgmtObjEntities;
	}

	/**
	 * @return the linkedNodeEntities
	 */
	public List<NodeEntity> getLinkedNodeEntities() {
		if (linkedNodeEntities == null) {
			linkedNodeEntities = new ArrayList<>();
		}
		return linkedNodeEntities;
	}

	/**
	 * @param linkedNodeEntities the linkedNodeEntities to set
	 */
	public void setLinkedNodeEntities(List<NodeEntity> linkedNodeEntities) {
		this.linkedNodeEntities = linkedNodeEntities;
	}

	/**
	 * @return the linkedRemoteCSEEntities
	 */
	public List<RemoteCSEEntity> getLinkedRemoteCSEEntities() {
		if (linkedRemoteCSEEntities == null) {
			linkedRemoteCSEEntities = new ArrayList<>();
		}
		return linkedRemoteCSEEntities;
	}

	/**
	 * @param linkedRemoteCSEEntities the linkedRemoteCSEEntities to set
	 */
	public void setLinkedRemoteCSEEntities(List<RemoteCSEEntity> linkedRemoteCSEEntities) {
		this.linkedRemoteCSEEntities = linkedRemoteCSEEntities;
	}

	/**
	 * @return the linkedScheduleEntities
	 */
	public List<ScheduleEntity> getLinkedScheduleEntities() {
		if (linkedScheduleEntities == null) {
			linkedScheduleEntities = new ArrayList<>();
		}
		return linkedScheduleEntities;
	}

	/**
	 * @param linkedScheduleEntities the linkedScheduleEntities to set
	 */
	public void setLinkedScheduleEntities(List<ScheduleEntity> linkedScheduleEntities) {
		this.linkedScheduleEntities = linkedScheduleEntities;
	}

	/**
	 * @return the linkedFlexContainerAnncEntities
	 */
	public List<FlexContainerAnncEntity> getLinkedFlexContainerAnncEntities() {
		if (linkedFlexContainerAnncEntities == null) {
			linkedFlexContainerAnncEntities = new ArrayList<>();
		}
		return linkedFlexContainerAnncEntities;
	}

	/**
	 * @param linkedFlexContainerAnncEntities the linkedFlexContainerAnncEntities to set
	 */
	public void setLinkedFlexContainerAnncEntities(List<FlexContainerAnncEntity> linkedFlexContainerAnncEntities) {
		this.linkedFlexContainerAnncEntities = linkedFlexContainerAnncEntities;
	}

	/**
	 * @return the linkedAeAnncEntities
	 */
	public List<AeAnncEntity> getLinkedAeAnncEntities() {
		if (linkedAeAnncEntities == null) {
			linkedAeAnncEntities = new ArrayList<>();
		}
		return linkedAeAnncEntities;
	}

	/**
	 * @param linkedAeAnncEntities the linkedAeAnncEntities to set
	 */
	public void setLinkedAeAnncEntities(List<AeAnncEntity> linkedAeAnncEntities) {
		this.linkedAeAnncEntities = linkedAeAnncEntities;
	}

	/**
	 * @return the linkedCseBaseEntities
	 */
	public List<CSEBaseEntity> getLinkedCseBaseEntities() {
		if (linkedCseBaseEntities == null) {
			linkedCseBaseEntities = new ArrayList<>();
		}
		return linkedCseBaseEntities;
	}

	/**
	 * @param linkedCseBaseEntities the linkedCseBaseEntities to set
	 */
	public void setLinkedCseBaseEntities(List<CSEBaseEntity> linkedCseBaseEntities) {
		this.linkedCseBaseEntities = linkedCseBaseEntities;
	}

	public void addMgmtObj(MgmtObjEntity mgmtObjEntity) {
		BigInteger mgmtDef = mgmtObjEntity.getMgmtDefinition();
		if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			getLinkedAreaNwkInfoEntities().add((AreaNwkInfoEntity) mgmtObjEntity);
		else if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			getLinkedAreaNwkDeviceInfoEntities().add((AreaNwkDeviceInfoEntity) mgmtObjEntity);
		else if (mgmtDef.equals(MgmtDefinitionTypes.DEVICE_INFO))
			getLinkedDeviceInfoEntities().add((DeviceInfoEntity) mgmtObjEntity);
	}
	
}
