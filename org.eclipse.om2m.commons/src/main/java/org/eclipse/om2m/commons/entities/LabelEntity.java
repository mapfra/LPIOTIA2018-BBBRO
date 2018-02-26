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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.constants.MgmtDefinitionTypes;
import org.eclipse.om2m.commons.constants.ShortName;

/**
 * Label JPA entity
 *
 */
@Entity(name = DBEntities.LABEL_ENTITY)
public class LabelEntity {

	@Id
	@Column(name = ShortName.LABELS)
	protected String label;

	// TODO add link to RESOURCES
	
	@ManyToMany(targetEntity = CSEBaseEntity.class, mappedBy = "labelsEntities")
	protected List<AeEntity> linkedCsb;

	@ManyToMany(targetEntity = AeEntity.class, mappedBy = "labelsEntities")
	protected List<AeEntity> linkedAe;
	
	@ManyToMany(targetEntity = AeAnncEntity.class, mappedBy = "labelsEntities")
	protected List<AeAnncEntity> linkedAeA;

	@ManyToMany(targetEntity = ContainerEntity.class, mappedBy = "labelsEntities")
	protected List<ContainerEntity> linkedCnt;
	
	@ManyToMany(targetEntity = FlexContainerEntity.class, mappedBy = "labelsEntities")
	protected List<FlexContainerEntity> linkedFcnt;
	
	@ManyToMany(targetEntity = FlexContainerAnncEntity.class, mappedBy = "labelsEntities")
	protected List<FlexContainerAnncEntity> linkedFcntA;

	@ManyToMany(targetEntity = ContentInstanceEntity.class, mappedBy = "labelsEntities")
	protected List<ContentInstanceEntity> linkedCin;

	@ManyToMany(targetEntity = GroupEntity.class, mappedBy = "labelsEntities")
	protected List<ContentInstanceEntity> linkedGroup;

	@ManyToMany(targetEntity = RemoteCSEEntity.class, mappedBy = "labelsEntities")
	protected List<RemoteCSEEntity> linkedCsr;

	@ManyToMany(targetEntity = SubscriptionEntity.class, mappedBy = "labelsEntities")
	protected List<SubscriptionEntity> linkedSub;

	@ManyToMany(targetEntity = PollingChannelEntity.class, mappedBy = "labelsEntities")
	protected List<PollingChannelEntity> linkedPch;
	
	@ManyToMany(targetEntity = AccessControlPolicyEntity.class, mappedBy = "labelsEntities")
	protected List<AccessControlPolicyEntity> linkedACP;
	
	@ManyToMany(targetEntity = NodeEntity.class, mappedBy = "labelsEntities")
	protected List<NodeEntity> linkedNodes;
	
	@ManyToMany(targetEntity = NodeAnncEntity.class, mappedBy = "labelsEntities")
	protected List<NodeAnncEntity> linkedNodesA;
	
	@ManyToMany(targetEntity = AreaNwkInfoEntity.class, mappedBy = "labelsEntities")
	protected List<AreaNwkInfoEntity> linkedAni;
	
	@ManyToMany(targetEntity = AreaNwkInfoAnncEntity.class, mappedBy = "labelsEntities")
	protected List<AreaNwkInfoAnncEntity> linkedAniA;
	
	@ManyToMany(targetEntity = AreaNwkDeviceInfoEntity.class, mappedBy = "labelsEntities")
	protected List<AreaNwkDeviceInfoEntity> linkedAndi;
	
	@ManyToMany(targetEntity = AreaNwkDeviceInfoAnncEntity.class, mappedBy = "labelsEntities")
	protected List<AreaNwkDeviceInfoAnncEntity> linkedAndiA;
	
	@ManyToMany(targetEntity = DeviceInfoEntity.class, mappedBy = "labelsEntities")
	protected List<DeviceInfoEntity> linkedDvi;
	
	@ManyToMany(targetEntity = DeviceInfoAnncEntity.class, mappedBy = "labelsEntities")
	protected List<DeviceInfoAnncEntity> linkedDviA;
	
	/**
	 * @return the linkedSub
	 */
	public List<SubscriptionEntity> getLinkedSub() {
		if(linkedSub == null){
			linkedSub = new ArrayList<>();
		}
		return linkedSub;
	}

	/**
	 * @param linkedSub the linkedSub to set
	 */
	public void setLinkedSub(List<SubscriptionEntity> linkedSub) {
		this.linkedSub = linkedSub;
	}

	/**
	 * Constructor
	 */
	public LabelEntity() {
	}

	/**
	 * Constructor with label in argument
	 * @param string label to store
	 */
	public LabelEntity(String string) {
		this.label = string;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	
	
	/**
	 * @return the linkedCsb
	 */
	public List<AeEntity> getLinkedCsb() {
		if (this.linkedCsb == null) {
			this.linkedCsb = new ArrayList<>();
		}
		return linkedCsb;
	}

	/**
	 * @param linkedCsb the linkedCsb to set
	 */
	public void setLinkedCsb(List<AeEntity> linkedCsb) {
		this.linkedCsb = linkedCsb;
	}


	public void setLinkedACP(List<AccessControlPolicyEntity> pLinkedACPs) {
		this.linkedACP = pLinkedACPs;
	}
	
	public List<AccessControlPolicyEntity> getLinkedACP() {
		if (this.linkedACP == null) {
			this.linkedACP = new ArrayList<>();
		}
		return this.linkedACP;
	}
	
	/**
	 * @return the linkedAeA
	 */
	public List<AeAnncEntity> getLinkedAeA() {
		if (this.linkedAeA == null) {
			this.linkedAeA = new ArrayList<>();
		}
		return linkedAeA;
	}

	/**
	 * @param linkedAeA the linkedAeA to set
	 */
	public void setLinkedAeA(List<AeAnncEntity> linkedAeA) {
		this.linkedAeA = linkedAeA;
	}
	
	/**
	 * @return the linkedAe
	 */
	public List<AeEntity> getLinkedAe() {
		if (this.linkedAe == null) {
			this.linkedAe = new ArrayList<>();
		}
		return linkedAe;
	}


	/**
	 * @param linkedAe the linkedAe to set
	 */
	public void setLinkedAe(List<AeEntity> linkedAe) {
		this.linkedAe = linkedAe;
	}

	/**
	 * @return the linkedCnt
	 */
	public List<ContainerEntity> getLinkedCnt() {
		if (this.linkedCnt == null) {
			this.linkedCnt = new ArrayList<>();
		}
		return linkedCnt;
	}

	/**
	 * @param linkedCnt the linkedCnt to set
	 */
	public void setLinkedCnt(List<ContainerEntity> linkedCnt) {
		this.linkedCnt = linkedCnt;
	}
	
	/**
	 * @return the linkedFcnt
	 */
	public List<FlexContainerEntity> getLinkedFcnt() {
		if (this.linkedFcnt == null) {
			this.linkedFcnt = new ArrayList<>();
		}
		return linkedFcnt;
	}

	/**
	 * @param linkedFcnt the linkedFcnt to set
	 */
	public void setLinkedFcnt(List<FlexContainerEntity> linkedFcnt) {
		this.linkedFcnt = linkedFcnt;
	}
	
	/**
	 * @return the linkedFcntA
	 */
	public List<FlexContainerAnncEntity> getLinkedFcntA() {
		if (this.linkedFcntA == null) {
			this.linkedFcntA = new ArrayList<>();
		}
		return linkedFcntA;
	}

	/**
	 * @param linkedFcntA the linkedFcnt to set
	 */
	public void setLinkedFcntA(List<FlexContainerAnncEntity> linkedFcntA) {
		this.linkedFcntA = linkedFcntA;
	}

	/**
	 * @return the linkedCin
	 */
	public List<ContentInstanceEntity> getLinkedCin() {
		if (this.linkedCin == null) {
			this.linkedCin = new ArrayList<>();
		}
		return linkedCin;
	}

	/**
	 * @param linkedCin the linkedCin to set
	 */
	public void setLinkedCin(List<ContentInstanceEntity> linkedCin) {
		this.linkedCin = linkedCin;
	}

	/**
	 * @return the linkedGroup
	 */
	public List<ContentInstanceEntity> getLinkedGroup() {
		if (linkedGroup == null) {
			linkedGroup = new ArrayList<>();
		}
		return linkedGroup;
	}

	/**
	 * @param linkedGroup the linkedGroup to set
	 */
	public void setLinkedGroup(List<ContentInstanceEntity> linkedGroup) {
		this.linkedGroup = linkedGroup;
	}

	/**
	 * @return the linkedCsr
	 */
	public List<RemoteCSEEntity> getLinkedCsr() {
		if (this.linkedCsr == null) {
			this.linkedCsr = new ArrayList<>();
		}
		return linkedCsr;
	}

	/**
	 * @param linkedCsr the linkedCsr to set
	 */
	public void setLinkedCsr(List<RemoteCSEEntity> linkedCsr) {
		this.linkedCsr = linkedCsr;
	}

	/**
	 * @return the linkedPch
	 */
	public List<PollingChannelEntity> getLinkedPch() {
		if (this.linkedPch == null) {
			this.linkedPch = new ArrayList<>();
		}
		return linkedPch;
	}

	/**
	 * @param linkedPch the linkedPch to set
	 */
	public void setLinkedPch(List<PollingChannelEntity> linkedPch) {
		this.linkedPch = linkedPch;
	}
	
	/**
	 * @return the linkedNode
	 */
	public List<NodeEntity> getLinkedNodes() {
		if (this.linkedNodes == null) {
			this.linkedNodes = new ArrayList<>();
		}
		return linkedNodes;
	}

	/**
	 * @param linkedNode the linkedNode to set
	 */
	public void setLinkedNodes(List<NodeEntity> linkedNode) {
		this.linkedNodes = linkedNode;
	}
	
	/**
	 * @return the linkedNode
	 */
	public List<NodeAnncEntity> getLinkedNodesA() {
		if (this.linkedNodesA == null) {
			this.linkedNodesA = new ArrayList<>();
		}
		return linkedNodesA;
	}

	/**
	 * @param linkedNode the linkedNode to set
	 */
	public void setLinkedNodesA(List<NodeAnncEntity> linkedNode) {
		this.linkedNodesA = linkedNode;
	}

	/**
	 * @return the linkedAni
	 */
	public List<AreaNwkInfoEntity> getLinkedAni() {
		if (this.linkedAni == null) {
			this.linkedAni = new ArrayList<>();
		}
		return linkedAni;
	}

	/**
	 * @param linkedAni the linkedAni to set
	 */
	public void setLinkedAni(List<AreaNwkInfoEntity> linkedAni) {
		this.linkedAni = linkedAni;
	}

	/**
	 * @return the linkedAni
	 */
	public List<AreaNwkInfoAnncEntity> getLinkedAniA() {
		if (this.linkedAniA == null) {
			this.linkedAniA = new ArrayList<>();
		}
		return linkedAniA;
	}

	/**
	 * @param linkedAni the linkedAni to set
	 */
	public void setLinkedAniA(List<AreaNwkInfoAnncEntity> linkedAni) {
		this.linkedAniA = linkedAni;
	}

	/**
	 * @return the linkedAndi
	 */
	public List<AreaNwkDeviceInfoEntity> getLinkedAndi() {
		if (this.linkedAndi == null) {
			this.linkedAndi = new ArrayList<>();
		}
		return linkedAndi;
	}

	/**
	 * @param linkedAndi the linkedAndi to set
	 */
	public void setLinkedAndi(List<AreaNwkDeviceInfoEntity> linkedAndi) {
		this.linkedAndi = linkedAndi;
	}

	/**
	 * @return the linkedAndi
	 */
	public List<AreaNwkDeviceInfoAnncEntity> getLinkedAndiA() {
		if (this.linkedAndiA == null) {
			this.linkedAndiA = new ArrayList<>();
		}
		return linkedAndiA;
	}

	/**
	 * @param linkedAndi the linkedAndi to set
	 */
	public void setLinkedAndiA(List<AreaNwkDeviceInfoAnncEntity> linkedAndi) {
		this.linkedAndiA = linkedAndi;
	}

	/**
	 * @return the linkedDvi
	 */
	public List<DeviceInfoEntity> getLinkedDvi() {
		if (this.linkedDvi == null) {
			this.linkedDvi = new ArrayList<>();
		}
		return linkedDvi;
	}

	/**
	 * @param linkedDvi the linkedDvi to set
	 */
	public void setLinkedDvi(List<DeviceInfoEntity> linkedDvi) {
		this.linkedDvi = linkedDvi;
	}

	/**
	 * @return the linkedDvi
	 */
	public List<DeviceInfoAnncEntity> getLinkedDviA() {
		if (this.linkedDviA == null) {
			this.linkedDviA = new ArrayList<>();
		}
		return linkedDviA;
	}

	/**
	 * @param linkedDvi the linkedDvi to set
	 */
	public void setLinkedDviA(List<DeviceInfoAnncEntity> linkedDvi) {
		this.linkedDviA = linkedDvi;
	}

	public void addMgmtObj(MgmtObjEntity mgmtObjEntity) {
		BigInteger mgmtDef = mgmtObjEntity.getMgmtDefinition();
		if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			getLinkedAni().add((AreaNwkInfoEntity) mgmtObjEntity);
		else if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			getLinkedAndi().add((AreaNwkDeviceInfoEntity) mgmtObjEntity);
		else if (mgmtDef.equals(MgmtDefinitionTypes.DEVICE_INFO))
			getLinkedDvi().add((DeviceInfoEntity) mgmtObjEntity);
	}

	public void addMgmtObjA(MgmtObjAnncEntity mgmtObjAnncEntity) {
		BigInteger mgmtDef = mgmtObjAnncEntity.getMgmtDefinition();
		if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_INFO))
			getLinkedAniA().add((AreaNwkInfoAnncEntity) mgmtObjAnncEntity);
		else if (mgmtDef.equals(MgmtDefinitionTypes.AREA_NWK_DEVICE_INFO))
			getLinkedAndiA().add((AreaNwkDeviceInfoAnncEntity) mgmtObjAnncEntity);
		else if (mgmtDef.equals(MgmtDefinitionTypes.DEVICE_INFO))
			getLinkedDviA().add((DeviceInfoAnncEntity) mgmtObjAnncEntity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;			
		}
		if (obj == null){
			return false;			
		}
		if (getClass() != obj.getClass()){
			return false;			
		}
		LabelEntity other = (LabelEntity) obj;
		if (label == null) {
			if (other.label != null){
				return false;				
			}
		} else if (!label.equals(other.label)){
			return false;			
		}
		return true;
	}
	
}
