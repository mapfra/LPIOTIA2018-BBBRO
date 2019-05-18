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
import javax.persistence.OneToMany;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.constants.ShortName;

@Entity(name = DBEntities.FLEXCONTAINER_ANNC_ENTITY)
@Inheritance(strategy = InheritanceType.JOINED)
public class FlexContainerAnncEntity extends AnnouncedResourceEntity {
	@Column(name="longName")
	protected String longName;
	@Column(name="shortName")
	protected String shortName;
	
	@Column(name = ShortName.STATETAG)
	protected BigInteger stateTag;
	@Column(name = ShortName.CREATOR)
	protected String creator;
	@Column(name = ShortName.ONTOLOGY_REF)
	protected String ontologyRef;
	@Column(name = ShortName.CONTAINER_DEFINITION)
	protected String containerDefinition;
	@Column(name=ShortName.NODE_LINK)
	protected String nodeLink;

	/** List of DynamicAuthorizationConsultations*/
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedFlexContainerAnncEntities")
	@JoinTable(
			name = DBEntities.FCNTA_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.FCNTA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultations;

	// Database link to Subscriptions
	@OneToMany(fetch = FetchType.LAZY, targetEntity = SubscriptionEntity.class, cascade = CascadeType.ALL)
	@JoinTable(name = DBEntities.FCNTASUB_JOIN_ID, joinColumns = {
			@JoinColumn(name = DBEntities.FCNTA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, inverseJoinColumns = {
					@JoinColumn(name = DBEntities.SUB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) })
	protected List<SubscriptionEntity> subscriptions;

	/** AccessControlPolicies linked to the FCNTA */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = DBEntities.FCNTA_ACP_JOIN, joinColumns = {
			@JoinColumn(name = DBEntities.FCNTA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, inverseJoinColumns = {
					@JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) })
	protected List<AccessControlPolicyEntity> accessControlPolicies;
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="parentFlexContainerAnnc")
	@JoinTable(
			name=DBEntities.FCNTA_FCNTACHILD_JOIN,
			joinColumns={@JoinColumn(name=DBEntities.FCNTA_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			inverseJoinColumns={@JoinColumn(name=DBEntities.FCNTACH_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected List<FlexContainerAnncEntity> childFlexContainerAnncs;
	
	// Database link to the possible parent FlexContainerAnnc
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=FlexContainerAnncEntity.class)
	@JoinTable(
			name=DBEntities.FCNTA_FCNTACHILD_JOIN,
			inverseJoinColumns={@JoinColumn(name=DBEntities.FCNTA_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			joinColumns={@JoinColumn(name=DBEntities.FCNTACH_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected FlexContainerAnncEntity parentFlexContainerAnnc;
	
	// Database link to the possible parent AEAnnc
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=AeAnncEntity.class)
	@JoinTable(
			name=DBEntities.AEANNC_FCNT_JOIN,
			inverseJoinColumns={@JoinColumn(name=DBEntities.AEANNC_JOINID, referencedColumnName=ShortName.RESOURCE_ID)},
			joinColumns={@JoinColumn(name=DBEntities.FCNTA_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected AeAnncEntity parentAeAnnc;
	
	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @param longName the longName to set
	 */
	public void setLongName(String longName) {
		this.longName = longName;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * @return the stateTag
	 */
	public BigInteger getStateTag() {
		return stateTag;
	}

	/**
	 * @param stateTag
	 *            the stateTag to set
	 */
	public void setStateTag(BigInteger stateTag) {
		this.stateTag = stateTag;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the ontologyRef
	 */
	public String getOntologyRef() {
		return ontologyRef;
	}

	/**
	 * @param ontologyRef
	 *            the ontologyRef to set
	 */
	public void setOntologyRef(String ontologyRef) {
		this.ontologyRef = ontologyRef;
	}

	/**
	 * @return the containerDefinition
	 */
	public String getContainerDefinition() {
		return containerDefinition;
	}

	/**
	 * @param containerDefinition
	 *            the containerDefinition to set
	 */
	public void setContainerDefinition(String containerDefinition) {
		this.containerDefinition = containerDefinition;
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
	 * @param subscriptions
	 *            the subscriptions to set
	 */
	public void setSubscriptions(List<SubscriptionEntity> subscriptions) {
		this.subscriptions = subscriptions;
	}

	/**
	 * @return the accessControlPolicies
	 */
	public List<AccessControlPolicyEntity> getAccessControlPolicies() {
		if (this.accessControlPolicies == null) {
			this.accessControlPolicies = new ArrayList<>();
		}
		return accessControlPolicies;
	}

	/**
	 * @param accessControlPolicies
	 *            the accessControlPolicies to set
	 */
	public void setAccessControlPolicies(List<AccessControlPolicyEntity> accessControlPolicies) {
		this.accessControlPolicies = accessControlPolicies;
	}

	public List<FlexContainerAnncEntity> getChildFlexContainerAnncs() {
		if (childFlexContainerAnncs == null) {
			childFlexContainerAnncs = new ArrayList<>();
		}
		return childFlexContainerAnncs;
	}

	public void setChildFlexContainerAnncs(List<FlexContainerAnncEntity> childFlexContainerAnncs) {
		this.childFlexContainerAnncs = childFlexContainerAnncs;
	}

	public FlexContainerAnncEntity getParentFlexContainerAnnc() {
		return parentFlexContainerAnnc;
	}

	public void setParentFlexContainerAnnc(FlexContainerAnncEntity parentFlexContainerAnnc) {
		this.parentFlexContainerAnnc = parentFlexContainerAnnc;
	}

	public AeAnncEntity getParentAeAnnc() {
		return parentAeAnnc;
	}

	public void setParentAeAnnc(AeAnncEntity parentAeAnnc) {
		this.parentAeAnnc = parentAeAnnc;
	}

	public String getNodeLink() {
		return nodeLink;		
	}

	public void setNodeLink(String nodeLink) {
		this.nodeLink = nodeLink;
	}
	
}
