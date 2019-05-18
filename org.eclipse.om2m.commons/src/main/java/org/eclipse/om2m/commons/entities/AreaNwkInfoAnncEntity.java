/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
import javax.persistence.OneToMany;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.constants.MgmtDefinitionTypes;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.commons.resource.AnnouncedMgmtResource;
import org.eclipse.om2m.commons.resource.AreaNwkInfoAnnc;

/**
 * Area Network Info Entity - Specialization of MgmtObj
 *
 */
@Entity(name = ShortName.AREA_NWK_INFO_ANNC)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AreaNwkInfoAnncEntity extends MgmtObjAnncEntity {
	
	@Column(name = ShortName.AREA_NWK_TYPE)
	protected String areaNwkType;
	@Column(name = ShortName.LIST_DEVICES)
	protected List<String> listOfDevices;

	// link to acp
	// Database link to ACP
	@ManyToMany(fetch = FetchType.LAZY, targetEntity = AccessControlPolicyEntity.class)
	@JoinTable(
			name = DBEntities.ANIA_ACP_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.ANIA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AccessControlPolicyEntity> accessControlPolicies;
	
	/** List of DynamicAuthorizationConsultations*/
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedAreaNwkInfoAnncEntities")
	@JoinTable(
			name = DBEntities.ANIA_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.ANIA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultations;

	// Database link to Subscriptions
	@OneToMany(fetch = FetchType.LAZY, targetEntity = SubscriptionEntity.class, mappedBy="parentAni")
	@JoinTable(
			name = DBEntities.ANIA_SUB_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.ANIA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.SUB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<SubscriptionEntity> subscriptions;

	// Database link to Node
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = NodeEntity.class)
	@JoinTable(
			name = DBEntities.ANIA_NOD_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.ANIA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected NodeAnncEntity parentNode;

	/**
	 * Constructor
	 */
	public AreaNwkInfoAnncEntity() {
		this.mgmtDefinition = MgmtDefinitionTypes.AREA_NWK_INFO;
	}
	
	/**
	 * @return the areaNwkType
	 */
	public String getAreaNwkType() {
		return areaNwkType;
	}

	/**
	 * @param areaNwkType the areaNwkType to set
	 */
	public void setAreaNwkType(String areaNwkType) {
		this.areaNwkType = areaNwkType;
	}

	/**
	 * @return the listOfDevices
	 */
	public List<String> getListOfDevices() {
		return listOfDevices;
	}

	/**
	 * @param listOfDevices the listOfDevices to set
	 */
	public void setListOfDevices(List<String> listOfDevices) {
		this.listOfDevices = listOfDevices;
	}
	
	/**
	 * @return the acps
	 */
	public List<AccessControlPolicyEntity> getAccessControlPolicies() {
		if (this.accessControlPolicies == null) {
			this.accessControlPolicies = new ArrayList<>();
		}
		return accessControlPolicies;
	}

	/**
	 * @param acps the acps to set
	 */
	public void setAccessControlPolicies(List<AccessControlPolicyEntity> acps) {
		this.accessControlPolicies = acps;
	}

	/**
	 * @return the parentNode
	 */
	public NodeAnncEntity getParentNode() {
		return parentNode;
	}

	/**
	 * @param parentNode the parentNode to set
	 */
	public void setParentNode(NodeAnncEntity parentNode) {
		this.parentNode = parentNode;
	}

	/**
	 * @return the subscriptions
	 */
	public List<SubscriptionEntity> getSubscriptions() {
		if (this.subscriptions == null) {
			this.subscriptions = new ArrayList<>();
		}
		return subscriptions;
	}

	/**
	 * @param subscriptions the subscriptions to set
	 */
	public void setSubscriptions(List<SubscriptionEntity> subscriptions) {
		this.subscriptions = subscriptions;
	}
	

	@Override
	public List<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationConsultations() {
		if (dynamicAuthorizationConsultations == null) {
			dynamicAuthorizationConsultations = new ArrayList<>();
		}
		return dynamicAuthorizationConsultations;
	}
	
	@Override
	public void setDynamicAuthorizationConsultations(List<DynamicAuthorizationConsultationEntity> list) {
		this.dynamicAuthorizationConsultations = list;
	}

	@Override
	public void fillFrom(AnnouncedMgmtResource mgmtObj) {
		super.fillFrom(mgmtObj);
		AreaNwkInfoAnnc ani = (AreaNwkInfoAnnc) mgmtObj;
		this.areaNwkType = ani.getAreaNwkType();
		this.listOfDevices = ani.getListOfDevices();
	}
	
}
