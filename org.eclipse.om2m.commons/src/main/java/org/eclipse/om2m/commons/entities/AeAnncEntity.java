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

/**
 * Ae announced JPA entity
 *
 */
@Entity(name = DBEntities.AE_ANNC_ENTITY)
@Inheritance(strategy = InheritanceType.JOINED)
public class AeAnncEntity extends AnnouncedResourceEntity {


	@Column(name = ShortName.APP_NAME)
	protected String appName;
	@Column(name = ShortName.APP_ID, nullable = false)
	protected String appID;
	@Column(name = ShortName.AE_ID, nullable = false)
	protected String aeid;
	@Column(name = ShortName.POA)
	protected List<String> pointOfAccess;
	@Column(name = ShortName.ONTOLOGY_REF)
	protected String ontologyRef;
	@Column(name = ShortName.NODE_LINK)
	protected String nodeLink;

	// Database link to Subscriptions
	@OneToMany(fetch = FetchType.LAZY, targetEntity = SubscriptionEntity.class, cascade = CascadeType.ALL)
	@JoinTable(name = DBEntities.AEANNCSUB_JOIN_ID, joinColumns = {
			@JoinColumn(name = DBEntities.AEANNC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, inverseJoinColumns = {
					@JoinColumn(name = DBEntities.SUB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) })
	protected List<SubscriptionEntity> subscriptions;

	/** AccessControlPolicies linked to the AE */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = DBEntities.AEANNCACP_JOIN, joinColumns = {
			@JoinColumn(name = DBEntities.AEANNC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, inverseJoinColumns = {
					@JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) })
	protected List<AccessControlPolicyEntity> accessControlPolicies;
	
	/** List of DynamicAuthorizationConsultations*/
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedAeAnncEntities")
	@JoinTable(
			name = DBEntities.AEANNC_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.AEANNC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultations;

	// Database link to remoteCse parent
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = RemoteCSEEntity.class)
	@JoinTable(name = DBEntities.CSRAEANNCCHILD_JOIN, inverseJoinColumns = {
			@JoinColumn(name = DBEntities.CSR_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, joinColumns = {
					@JoinColumn(name = DBEntities.AEANNC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) })
	protected RemoteCSEEntity parentCsr;
	
	@OneToMany(fetch=FetchType.LAZY, targetEntity=FlexContainerAnncEntity.class, mappedBy="parentAeAnnc", cascade = CascadeType.ALL)
	@JoinTable(
			name=DBEntities.AEANNC_FCNT_JOIN,
			joinColumns={@JoinColumn(name=DBEntities.AEANNC_JOINID, referencedColumnName=ShortName.RESOURCE_ID)},
			inverseJoinColumns={@JoinColumn(name=DBEntities.FCNTA_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
	)
	protected List<FlexContainerAnncEntity> flexContainerAnncs;

	// TODO add link cnt
	// TODO add link cntA
	// TODO add link grp
	// TODO add link grpA
	// TODO add link acpA
	// TODO add link pch

	

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the appID
	 */
	public String getAppID() {
		return appID;
	}

	/**
	 * @param appID
	 *            the appID to set
	 */
	public void setAppID(String appID) {
		this.appID = appID;
	}

	/**
	 * @return the aeid
	 */
	public String getAeid() {
		return aeid;
	}

	/**
	 * @param aeid
	 *            the aeid to set
	 */
	public void setAeid(String aeid) {
		this.aeid = aeid;
	}

	/**
	 * @return the pointOfAccess
	 */
	public List<String> getPointOfAccess() {
		if (this.pointOfAccess == null) {
			this.pointOfAccess = new ArrayList<>();
		}
		return pointOfAccess;
	}

	/**
	 * @param pointOfAccess
	 *            the pointOfAccess to set
	 */
	public void setPointOfAccess(List<String> pointOfAccess) {
		this.pointOfAccess = pointOfAccess;
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
	 * @return the nodeLink
	 */
	public String getNodeLink() {
		return nodeLink;
	}

	/**
	 * @param nodeLink
	 *            the nodeLink to set
	 */
	public void setNodeLink(String nodeLink) {
		this.nodeLink = nodeLink;
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

	public List<FlexContainerAnncEntity> getFlexContainerAnncs() {
		if (flexContainerAnncs == null) {
			flexContainerAnncs = new ArrayList<>();
		}
		return flexContainerAnncs;
	}

	public void setFlexContainerAnncs(List<FlexContainerAnncEntity> flexContainerAnncs) {
		this.flexContainerAnncs = flexContainerAnncs;
	}
	
	

}
