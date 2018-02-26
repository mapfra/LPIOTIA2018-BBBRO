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
import org.eclipse.om2m.commons.constants.ShortName;

/**
 * Container JPA entity
 *
 */
@Entity(name=DBEntities.FLEXCONTAINER_ENTITY)
@Inheritance(strategy = InheritanceType.JOINED)
public class FlexContainerEntity extends AnnounceableSubordinateEntity{
	@Column(name="longName")
	protected String longName;
	@Column(name="shortName")
	protected String shortName;
	
	@Column(name= ShortName.STATETAG)
	protected BigInteger stateTag;
	@Column(name= ShortName.CREATOR)
	protected String creator;
	@Column(name= ShortName.ONTOLOGY_REF)
	protected String ontologyRef;
	@Column(name=ShortName.CONTAINER_DEFINITION)
	protected String containerDefinition;
	@Column(name=ShortName.NODE_LINK)
	protected String nodeLink;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
			name = DBEntities.FCNT_CA_JOIN,
			joinColumns = {@JoinColumn(name=DBEntities.FCNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID)},
			inverseJoinColumns =  {@JoinColumn(name=DBEntities.CA_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected List<CustomAttributeEntity> customAttributes;
	
	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy="parentFlexCnt")
	@JoinTable(
			name = DBEntities.FCNTSUB_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.FCNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.SUB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<SubscriptionEntity> subscriptions;
	
	/** List of AccessControlPolicies */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.FCNT_ACP_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.FCNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AccessControlPolicyEntity> accessControlPolicies;
	
	/** List of DynamicAuthorizationConsultations*/
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedFlexContainerEntites")
	@JoinTable(
			name = DBEntities.FCNT_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.FCNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultations;
	
	/** List of child Container Entities */
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinTable(
			name=DBEntities.FCNT_CNTCHILD_JOIN,
			joinColumns={@JoinColumn(name=DBEntities.FCNT_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			inverseJoinColumns={@JoinColumn(name=DBEntities.CNTCH_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected List<ContainerEntity> childContainers;
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy="parentFlexContainer")
	@JoinTable(
			name=DBEntities.FCNT_FCNTCHILD_JOIN,
			joinColumns={@JoinColumn(name=DBEntities.FCNT_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			inverseJoinColumns={@JoinColumn(name=DBEntities.FCNTCH_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected List<FlexContainerEntity> childFlexContainers;
	
	// Database link to the possible parent Container
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=FlexContainerEntity.class)
	@JoinTable(
			name=DBEntities.FCNT_FCNTCHILD_JOIN,
			inverseJoinColumns={@JoinColumn(name=DBEntities.FCNT_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			joinColumns={@JoinColumn(name=DBEntities.FCNTCH_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected FlexContainerEntity parentFlexContainer;
	
	// Database link to the possible parent Application Entity
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=AeEntity.class)
	@JoinTable(
			name = DBEntities.AE_FCNTCHILD_JOIN,
			inverseJoinColumns = { @JoinColumn(name = DBEntities.AE_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			joinColumns = { @JoinColumn(name = DBEntities.FCNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected AeEntity parentAE;

	// Database link to the possible parent CSEBase Entity
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=CSEBaseEntity.class)
	@JoinTable(
			name=DBEntities.CSEB_FCNT_JOIN,
			inverseJoinColumns={@JoinColumn(name=DBEntities.CSEB_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			joinColumns={@JoinColumn(name=DBEntities.FCNT_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected CSEBaseEntity parentCSEB;
	
	// Database link to the possible parent remote cse Entity
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=RemoteCSEEntity.class)
	@JoinTable(
			name=DBEntities.CSR_FCNTCHILD_JOIN,
			inverseJoinColumns={@JoinColumn(name=DBEntities.CSR_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			joinColumns={@JoinColumn(name=DBEntities.FCNT_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected CSEBaseEntity parentCSR;
	
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=ContainerEntity.class)
	@JoinTable(
			name=DBEntities.CNT_FCNTCHILD_JOIN,
			joinColumns={@JoinColumn(name=DBEntities.CNT_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)},
			inverseJoinColumns={@JoinColumn(name=DBEntities.FCNT_JOIN_ID, referencedColumnName=ShortName.RESOURCE_ID)}
			)
	protected ContainerEntity parentContainer;
	
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
	 * @return the parentFlexContainer
	 */
	public FlexContainerEntity getParentFlexContainer() {
		return parentFlexContainer;
	}

	/**
	 * @param parentFlexContainer the parentFlexContainer to set
	 */
	public void setParentFlexContainer(FlexContainerEntity parentFlexContainer) {
		this.parentFlexContainer = parentFlexContainer;
	}
	
	/**
	 * @return the parent container
	 */
	public ContainerEntity getParentContainer() {
		return parentContainer;
	}
	
	/**
	 * 
	 * @param parentContainer the parentContainer to set
	 */
	public void setParentContainer(ContainerEntity parentContainer) {
		this.parentContainer = parentContainer;
	}

	/**
	 * @return the parentAE
	 */
	public AeEntity getParentAE() {
		return parentAE;
	}

	/**
	 * @param parentAE the parentAE to set
	 */
	public void setParentAE(AeEntity parentAE) {
		this.parentAE = parentAE;
	}

	/**
	 * @return the parentCSEB
	 */
	public CSEBaseEntity getParentCSEB() {
		return parentCSEB;
	}

	/**
	 * @param parentCSEB the parentCSEB to set
	 */
	public void setParentCSEB(CSEBaseEntity parentCSEB) {
		this.parentCSEB = parentCSEB;
	}

	/**
	 * @return the stateTag
	 */
	public BigInteger getStateTag() {
		return stateTag;
	}

	/**
	 * @param stateTag the stateTag to set
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
	 * @param creator the creator to set
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
	 * @param ontologyRef the ontologyRef to set
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
	 * @param containerDefinition the containerDefinition to set
	 */
	public void setContainerDefinition(String containerDefinition) {
		this.containerDefinition = containerDefinition;
	}

	/**
	 * @return the childContainers
	 */
	public List<ContainerEntity> getChildContainers() {
		if (this.childContainers == null) {
			this.childContainers = new ArrayList<>();
		}
		return childContainers;
	}

	/**
	 * @param childContainers the childContainers to set
	 */
	public void setChildContainers(List<ContainerEntity> childContainers) {
		this.childContainers = childContainers;
	}
	
	/**
	 * @return the childFlexContainers
	 */
	public List<FlexContainerEntity> getChildFlexContainers() {
		if (this.childFlexContainers == null) {
			this.childFlexContainers = new ArrayList<>();
		}
		return childFlexContainers;
	}

	/**
	 * @param childFlexContainers the childFlexContainers to set
	 */
	public void setChildFlexContainers(List<FlexContainerEntity> childFlexContainers) {
		this.childFlexContainers = childFlexContainers;
	}

	/**
	 * @return the accessControlPolicies
	 */
	public List<AccessControlPolicyEntity> getAccessControlPolicies() {
		if (accessControlPolicies == null) {
			accessControlPolicies = new ArrayList<>();
		}
		return accessControlPolicies;
	}

	/**
	 * @param accessControlPolicies the accessControlPolicies to set
	 */
	public void setAccessControlPolicies(
			List<AccessControlPolicyEntity> accessControlPolicies) {
		this.accessControlPolicies = accessControlPolicies;
	}

	
	/**
	 * @return the parentCSR
	 */
	public CSEBaseEntity getParentCSR() {
		return parentCSR;
	}

	/**
	 * @param parentCSR the parentCSR to set
	 */
	public void setParentCSR(CSEBaseEntity parentCSR) {
		this.parentCSR = parentCSR;
	}

	public String getNodeLink() {
		return nodeLink;		
	}

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
	 * @param subscriptions the subscriptions to set
	 */
	public void setSubscriptions(List<SubscriptionEntity> subscriptions) {
		this.subscriptions = subscriptions;
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
	 * @return the customAttributes
	 */
	public List<CustomAttributeEntity> getCustomAttributes() {
		if (this.customAttributes == null) {
			this.customAttributes = new ArrayList<>();
		}
		return customAttributes;
	}

	/**
	 * @param subscriptions the subscriptions to set
	 */
	public void setCustomAttributes(List<CustomAttributeEntity> customAttributes) {
		this.customAttributes = customAttributes;
	}
	
	public void createOrUpdateCustomAttribute(String name, Object value) {
		CustomAttributeEntity attToCreateOrUpdate = null;
		for(CustomAttributeEntity cae : getCustomAttributes()) {
			if (cae.getCustomAttributeName().equals(name)) {
				attToCreateOrUpdate = cae;
				break;
			}
		}
		
		if (attToCreateOrUpdate == null) {
			attToCreateOrUpdate = new CustomAttributeEntity();
			attToCreateOrUpdate.setCustomAttributeName(name);
			getCustomAttributes().add(attToCreateOrUpdate);
		}
		attToCreateOrUpdate.setCustomAttributeValue((value != null) ? value.toString() : null);
		
	}
	
}
