package org.eclipse.om2m.commons.entities;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.om2m.commons.constants.ShortName;

@Entity(name = DBEntities.DYNAMIC_AUTHORIZATION_CONSULTATION_ENTITY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DynamicAuthorizationConsultationEntity extends ResourceEntity {
	
	// regular resource
	/** accessControlPolicyIDs = acpi */ 
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.DAC_ACP_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<AccessControlPolicyEntity> accessControlPolicyIDs;
	
	/** expirationTime = et */ 
	@Column(name=ShortName.EXPIRATION_TIME)
	private String expirationTime;
	
	/** dynamicAuthorizationConsultationIDs = daci */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.DAC_DACCHILD_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DACCHILD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultationIDs;
	
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
	
	
	// relationship between oneM2M entities
	/** link with DynamicAuthorizationConsultationEntity entity that uses a DynamicAuthorizationConsultationEntity to check rights */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.DAC_DACCHILD_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DACCHILD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	private List<DynamicAuthorizationConsultationEntity> linkedDynamicAuthorizationConsultationEntity;
	
	// parent relationship
	/** CSEBaseEntity parent */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=CSEBaseEntity.class)
	@JoinTable(
			name=DBEntities.CSEB_DAC_JOIN,
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

	public List<AccessControlPolicyEntity> getAccessControlPolicyIDs() {
		if (accessControlPolicyIDs == null) {
			accessControlPolicyIDs = new ArrayList<>();
		}
		return accessControlPolicyIDs;
	}

	public void setAccessControlPolicyIDs(List<AccessControlPolicyEntity> accessControlPolicyIDs) {
		this.accessControlPolicyIDs = accessControlPolicyIDs;
	}

	public String getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	public List<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationConsultationIDs() {
		if (dynamicAuthorizationConsultationIDs == null) {
			dynamicAuthorizationConsultationIDs = new ArrayList<>();
		}
		return dynamicAuthorizationConsultationIDs;
	}

	public void setDynamicAuthorizationConsultationIDs(
			List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultationIDs) {
		this.dynamicAuthorizationConsultationIDs = dynamicAuthorizationConsultationIDs;
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
	
	
}
