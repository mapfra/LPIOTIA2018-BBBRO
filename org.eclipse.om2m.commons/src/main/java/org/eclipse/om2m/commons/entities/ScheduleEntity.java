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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.constants.ShortName;

/**
 * Schedule JPA entity
 *
 */
@Entity(name = ShortName.SCHEDULE)
@Inheritance(strategy = InheritanceType.JOINED)
public class ScheduleEntity extends AnnounceableSubordinateEntity {
	
	protected List<String> scheduleEntries;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = RemoteCSEEntity.class, mappedBy = "linkedSchedule")
	protected RemoteCSEEntity linkedCsr;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = CSEBaseEntity.class, mappedBy = "linkedSchedule")
	protected CSEBaseEntity linkedCsb;

	@OneToOne(fetch = FetchType.LAZY, targetEntity = SubscriptionEntity.class, mappedBy = "childSchedule")
	protected SubscriptionEntity parentSub;
	
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy="parentSch")
	@JoinTable(
			name = DBEntities.SCHSUB_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.SCH_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.SUB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<SubscriptionEntity> subscriptions;
	
	/** AccessControlPolicies linked to the ScheduleEntity */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.SCHACP_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.SCH_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AccessControlPolicyEntity> accessControlPolicies;
	
	/** List of DynamicAuthorizationConsultations*/
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedScheduleEntities")
	@JoinTable(
			name = DBEntities.SCH_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.SCH_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultations;
	

	/**
	 * @return the scheduleEntries
	 */
	public List<String> getScheduleEntries() {
		return scheduleEntries;
	}

	/**
	 * @param scheduleEntries the scheduleEntries to set
	 */
	public void setScheduleEntries(List<String> scheduleEntries) {
		this.scheduleEntries = scheduleEntries;
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

	/**
	 * @return the linkedCsr
	 */
	public RemoteCSEEntity getLinkedCsr() {
		return linkedCsr;
	}

	/**
	 * @param linkedCsr the linkedCsr to set
	 */
	public void setLinkedCsr(RemoteCSEEntity linkedCsr) {
		this.linkedCsr = linkedCsr;
	}

	/**
	 * @return the linkedCsb
	 */
	public CSEBaseEntity getLinkedCsb() {
		return linkedCsb;
	}

	/**
	 * @param linkedCsb the linkedCsb to set
	 */
	public void setLinkedCsb(CSEBaseEntity linkedCsb) {
		this.linkedCsb = linkedCsb;
	}

	/**
	 * @return the linkedSub
	 */
	public SubscriptionEntity getLinkedSub() {
		return parentSub;
	}

	/**
	 * @param linkedSub the linkedSub to set
	 */
	public void setLinkedSub(SubscriptionEntity linkedSub) {
		this.parentSub = linkedSub;
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
	public void setAccessControlPolicies(
			List<AccessControlPolicyEntity> accessControlPolicies) {
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

	
	
}
