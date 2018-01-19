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
@Entity(name = DBEntities.NODE_ENTITY)
public class NodeEntity extends AnnounceableSubordinateEntity {

	// linked ACP
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.ACP_NOD_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AccessControlPolicyEntity> linkedAcps;
	
	/** List of DynamicAuthorizationConsultations*/
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedNodeEntities")
	@JoinTable(
			name = DBEntities.NOD_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
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

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=CSEBaseEntity.class)
	@JoinTable(
			name = DBEntities.CSB_NOD_CH_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.CSEB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected CSEBaseEntity parentCsb;

	@ManyToOne(fetch=FetchType.LAZY, targetEntity=RemoteCSEEntity.class)
	@JoinTable(
			name = DBEntities.CSR_NOD_CH_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.CSR_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected RemoteCSEEntity parentCsr;

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(
			name = DBEntities.NOD_SUB_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.SUB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<SubscriptionEntity> subscriptions;

	// Database link to AreaNwkInfo Entity
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="parentNode")
	@JoinTable(
			name = DBEntities.ANI_NOD_JOIN,
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ANI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			joinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AreaNwkInfoEntity> childAreaNwkInfoEntities;
	
	// Database link to AreaNwkDeviceInfo entity
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="parentNode")
	@JoinTable(
			name = DBEntities.ANDI_NOD_JOIN,
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ANDI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			joinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AreaNwkDeviceInfoEntity> childAreaNwkDeviceInfoEntities;
	
	// Database link to DeviceInfo entity
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="parentNode")
	@JoinTable(
			name = DBEntities.DVI_NOD_JOIN,
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DVI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			joinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DeviceInfoEntity> childDeviceInfoEntities;

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
	public void setAccessControlPolicies(
			List<AccessControlPolicyEntity> accessControlPolicies) {
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
	public List<SubscriptionEntity> getSubscriptions() {
		return subscriptions;
	}
	
	/**
	 * @param childSubscriptions the childSubscriptions to set
	 */
	public void setSubscriptions(List<SubscriptionEntity> subscriptions) {
		this.subscriptions = subscriptions;
	}
	
	/**
	 * @return the childAreaNwkInfoEntities
	 */
	public List<AreaNwkInfoEntity> getChildAreaNwkInfoEntities() {
		if (this.childAreaNwkInfoEntities == null) {
			this.childAreaNwkInfoEntities = new ArrayList<>();
		}
		return childAreaNwkInfoEntities;
	}
	
	/**
	 * @param childAreaNwkInfoEntities the childAreaNwkInfoEntities to set
	 */
	public void setChildAreaNwkInfoEntities(List<AreaNwkInfoEntity> childAreaNwkInfoEntities) {
		this.childAreaNwkInfoEntities = childAreaNwkInfoEntities;
	}
	
	/**
	 * @return the childAreaNwkDeviceInfoEntities
	 */
	public List<AreaNwkDeviceInfoEntity> getChildAreaNwkDeviceInfoEntities() {
		if (this.childAreaNwkDeviceInfoEntities == null) {
			this.childAreaNwkDeviceInfoEntities = new ArrayList<>();
		}
		return childAreaNwkDeviceInfoEntities;
	}
	
	/**
	 * @param childAreaNwkDeviceInfoEntities the childAreaNwkDeviceInfoEntities to set
	 */
	public void setChildAreaNwkDeviceInfoEntities(List<AreaNwkDeviceInfoEntity> childAreaNwkDeviceInfoEntities) {
		this.childAreaNwkDeviceInfoEntities = childAreaNwkDeviceInfoEntities;
	}
	
	/**
	 * @return the childDeviceInfoEntities
	 */
	public List<DeviceInfoEntity> getChildDeviceInfoEntities() {
		if (this.childDeviceInfoEntities == null) {
			this.childDeviceInfoEntities = new ArrayList<>();
		}
		return childDeviceInfoEntities;
	}
	
	/**
	 * @param childDeviceInfoEntities the childDeviceInfoEntities to set
	 */
	public void setChildDeviceInfoEntities(List<DeviceInfoEntity> childDeviceInfoEntities) {
		this.childDeviceInfoEntities = childDeviceInfoEntities;
	}

	public List<MgmtObjEntity> getMgmtObjEntities() {
		List<MgmtObjEntity> ret = new ArrayList<MgmtObjEntity>();
		ret.addAll(getChildAreaNwkInfoEntities());
		ret.addAll(getChildAreaNwkDeviceInfoEntities());
		ret.addAll(getChildDeviceInfoEntities());
		return ret;
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

	public void addMgmtObj(MgmtObjEntity mgmtObj) {
		BigInteger mgmtDef = mgmtObj.getMgmtDefinition();
		if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			getChildAreaNwkInfoEntities().add((AreaNwkInfoEntity) mgmtObj);
		else if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			getChildAreaNwkDeviceInfoEntities().add((AreaNwkDeviceInfoEntity) mgmtObj);
		else if (mgmtDef.equals(MgmtDefinitionTypes.DEVICE_INFO))
			getChildDeviceInfoEntities().add((DeviceInfoEntity) mgmtObj);
	}

	public void removeMgmtObj(MgmtObjEntity mgmtObj) {
		BigInteger mgmtDef = mgmtObj.getMgmtDefinition();
		if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			getChildAreaNwkInfoEntities().remove((AreaNwkInfoEntity) mgmtObj);
		else if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			getChildAreaNwkDeviceInfoEntities().remove((AreaNwkDeviceInfoEntity) mgmtObj);
		else if (mgmtDef.equals(MgmtDefinitionTypes.DEVICE_INFO))
			getChildDeviceInfoEntities().remove((DeviceInfoEntity) mgmtObj);
	}

}
