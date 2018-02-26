/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
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
import javax.persistence.OneToMany;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.constants.MgmtDefinitionTypes;
import org.eclipse.om2m.commons.constants.ShortName;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = DBEntities.NODE_ANNC_ENTITY)
public class NodeAnncEntity extends AnnouncedResourceEntity {

	// linked ACP
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.ACP_NODANNC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NODANNC_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AccessControlPolicyEntity> linkedAcps;
	
	/** List of DynamicAuthorizationConsultations*/
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedNodeAnncEntities")
	@JoinTable(
			name = DBEntities.NODANNC_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NODANNC_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultations;

	// node id
	@Column(name = ShortName.NODE_ID)
	protected String nodeID;

	// hosted CSE LINK
	@Column(name = ShortName.HOSTED_CSE_LINK)
	protected String hostedCSELink;

	// hosted APP LINK
	@Column(name = ShortName.HOSTED_SRV_LINK)
	protected String hostedServiceLinks;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
			name = DBEntities.CSB_NODANNC_CH_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NODANNC_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.CSEB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected CSEBaseEntity parentCsb;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(
			name = DBEntities.CSR_NODANNC_CH_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NODANNC_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.CSR_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected RemoteCSEEntity parentCsr;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(
			name = DBEntities.NODANNC_SUB_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NODANNC_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.SUB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<SubscriptionEntity> childSubscriptions;

	// Database link to AreaNwkInfo Entity
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(
			name = DBEntities.ANI_NODANNC_JOIN,
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ANI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			joinColumns = { @JoinColumn(name = DBEntities.NODANNC_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AreaNwkInfoAnncEntity> childAreaNwkInfoEntities;
	
	// Database link to AreaNwkDeviceInfo entity
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(
			name = DBEntities.ANDI_NODANNC_JOIN,
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ANDI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			joinColumns = { @JoinColumn(name = DBEntities.NODANNC_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AreaNwkDeviceInfoAnncEntity> childAreaNwkDeviceInfoEntities;
	
	// Database link to DeviceInfo entity
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(
			name = DBEntities.DVI_NODANNC_JOIN,
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DVI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			joinColumns = { @JoinColumn(name = DBEntities.NODANNC_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DeviceInfoAnncEntity> childDeviceInfoEntities;

	/**
	 * @return the accessControlPolicies
	 */
	public List<AccessControlPolicyEntity> getAccessControlPolicies() {
		if (this.linkedAcps == null) {
			this.linkedAcps = new ArrayList<>();
		}
		return linkedAcps;
	}
	
	/**
	 * @param accessControlPolicies the accessControlPolicies to set
	 */
	public void setAccessControlPolicies(List<AccessControlPolicyEntity> accessControlPolicies) {
		this.linkedAcps = accessControlPolicies;
	}
	
	/**
	 * @return the nodeID
	 */
	public String getNodeID() {
		return nodeID;
	}
	
	/**
	 * @param nodeID the nodeID to set
	 */
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	
	/**
	 * @return the hostedCSELink
	 */
	public String getHostedCSELink() {
		return hostedCSELink;
	}
	
	/**
	 * @param hostedCSELink the hostedCSELink to set
	 */
	public void setHostedCSELink(String hostedCSELink) {
		this.hostedCSELink = hostedCSELink;
	}
	
	/**
	 * @return the hostedAppLink
	 */
	public String getHostedServiceLinks() {
		return hostedServiceLinks;
	}
	
	/**
	 * @param hostedCSELink the hostedAppLink to set
	 */
	public void setHostedServiceLinks(String hostedServiceLinks) {
		this.hostedServiceLinks = hostedServiceLinks;
	}
	
	/**
	 * @return the parentCsb
	 */
	public CSEBaseEntity getParentCsb() {
		return parentCsb;
	}
	
	/**
	 * @param parentCsb the parentCsb to set
	 */
	public void setParentCsb(CSEBaseEntity parentCsb) {
		this.parentCsb = parentCsb;
	}
	
	/**
	 * @return the parentCsr
	 */
	public RemoteCSEEntity getParentCsr() {
		return parentCsr;
	}
	
	/**
	 * @param parentCsr the parentCsr to set
	 */
	public void setParentCsr(RemoteCSEEntity parentCsr) {
		this.parentCsr = parentCsr;
	}
	
	/**
	 * @return the childSubscriptions
	 */
	public List<SubscriptionEntity> getChildSubscriptions() {
		return childSubscriptions;
	}
	
	/**
	 * @param childSubscriptions the childSubscriptions to set
	 */
	public void setChildSubscriptions(List<SubscriptionEntity> childSubscriptions) {
		this.childSubscriptions = childSubscriptions;
	}
	
	/**
	 * @return the childAreaNwkInfoEntities
	 */
	public List<AreaNwkInfoAnncEntity> getChildAreaNwkInfoEntities() {
		if (this.childAreaNwkInfoEntities == null) {
			this.childAreaNwkInfoEntities = new ArrayList<>();
		}
		return childAreaNwkInfoEntities;
	}
	
	/**
	 * @param childAreaNwkInfoEntities the childAreaNwkInfoEntities to set
	 */
	public void setChildAreaNwkInfoAnncEntities(List<AreaNwkInfoAnncEntity> childAreaNwkInfoEntities) {
		this.childAreaNwkInfoEntities = childAreaNwkInfoEntities;
	}
	
	/**
	 * @return the childAreaNwkDeviceInfoEntities
	 */
	public List<AreaNwkDeviceInfoAnncEntity> getChildAreaNwkDeviceInfoEntities() {
		if (this.childAreaNwkDeviceInfoEntities == null) {
			this.childAreaNwkDeviceInfoEntities = new ArrayList<>();
		}
		return childAreaNwkDeviceInfoEntities;
	}
	
	/**
	 * @param childAreaNwkDeviceInfoEntities the childAreaNwkDeviceInfoEntities to set
	 */
	public void setChildAreaNwkDeviceInfoAnncEntities(List<AreaNwkDeviceInfoAnncEntity> childAreaNwkDeviceInfoEntities) {
		this.childAreaNwkDeviceInfoEntities = childAreaNwkDeviceInfoEntities;
	}
	
	/**
	 * @return the childDeviceInfoEntities
	 */
	public List<DeviceInfoAnncEntity> getChildDeviceInfoEntities() {
		if (this.childDeviceInfoEntities == null) {
			this.childDeviceInfoEntities = new ArrayList<>();
		}
		return childDeviceInfoEntities;
	}
	
	/**
	 * @param childDeviceInfoEntities the childDeviceInfoEntities to set
	 */
	public void setChildDeviceInfoAnncEntities(List<DeviceInfoAnncEntity> childDeviceInfoEntities) {
		this.childDeviceInfoEntities = childDeviceInfoEntities;
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

	public List<MgmtObjAnncEntity> getMgmtObjEntities() {
		List<MgmtObjAnncEntity> ret = new ArrayList<MgmtObjAnncEntity>();
		ret.addAll(getChildAreaNwkInfoEntities());
		ret.addAll(getChildAreaNwkDeviceInfoEntities());
		ret.addAll(getChildDeviceInfoEntities());
		return ret;
	}

	public void addMgmtObj(MgmtObjAnncEntity mgmtObj) {
		BigInteger mgmtDef = mgmtObj.getMgmtDefinition();
		if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			getChildAreaNwkInfoEntities().add((AreaNwkInfoAnncEntity) mgmtObj);
		else if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			getChildAreaNwkDeviceInfoEntities().add((AreaNwkDeviceInfoAnncEntity) mgmtObj);
		else if (mgmtDef.equals(MgmtDefinitionTypes.DEVICE_INFO))
			getChildDeviceInfoEntities().add((DeviceInfoAnncEntity) mgmtObj);
	}

	public void removeMgmtObj(MgmtObjAnncEntity mgmtObj) {
		BigInteger mgmtDef = mgmtObj.getMgmtDefinition();
		if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			getChildAreaNwkInfoEntities().remove((AreaNwkInfoAnncEntity) mgmtObj);
		else if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			getChildAreaNwkDeviceInfoEntities().remove((AreaNwkDeviceInfoAnncEntity) mgmtObj);
		else if (mgmtDef.equals(MgmtDefinitionTypes.DEVICE_INFO))
			getChildDeviceInfoEntities().remove((DeviceInfoAnncEntity) mgmtObj);
	}

}
