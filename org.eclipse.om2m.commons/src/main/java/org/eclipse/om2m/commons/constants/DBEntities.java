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
package org.eclipse.om2m.commons.constants;

import org.eclipse.om2m.commons.entities.AccessControlContextEntity;
import org.eclipse.om2m.commons.entities.AccessControlOriginatorEntity;
import org.eclipse.om2m.commons.entities.AccessControlRuleEntity;
import org.eclipse.om2m.commons.entities.AeAnncEntity;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.CSEBaseEntity;
import org.eclipse.om2m.commons.entities.ContainerEntity;
import org.eclipse.om2m.commons.entities.ContentInstanceEntity;
import org.eclipse.om2m.commons.entities.CreatedAnnouncedResourceEntity;
import org.eclipse.om2m.commons.entities.CustomAttributeEntity;
import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.FlexContainerAnncEntity;
import org.eclipse.om2m.commons.entities.FlexContainerEntity;
import org.eclipse.om2m.commons.entities.LabelEntity;
import org.eclipse.om2m.commons.entities.MgmtObjEntity;
import org.eclipse.om2m.commons.entities.PollingChannelEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.entities.SubscriptionEntity;
import org.eclipse.om2m.commons.entities.UriMapperEntity;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;

/**
 * Class referencing all persisted entities names and their attributes.
 * 
 */
public class DBEntities {

	// private constructor
	private DBEntities() {
	};

	// ENTITIES NAMES
	/** Name of the table for hierarchical uris */
	public static final String HIERARCHICAL_URI = "huri";
	/** Name for Non hierarchical uri */
	public static final String NONHIERARCHICAL_URI = "nhuri";

	/** Name used for the persisted {@link AccessControlContextEntity} entity */
	public static final String ACCESSCONTROLCONTEXT_ENTITY = "ACC";

	/** Name used for the persisted {@link AccessControlRuleEntity} entity */
	public static final String ACCESSCONTROLRULE_ENTITY = "ACR";

	/**
	 * Name used for the persisted {@link AccessControlOriginatorEntity} entity
	 */
	public static final String ACCESSCONTROLORIGINATOR_ENTITY = "ACO";

	/** Name used for the persisted {@link AccessControlPolicy} entity */
	public static final String ACCESSCONTROLPOLICY_ENTITY = "ACP";

	/** Name used for the persisted {@link CSEBaseEntity} entity */
	public static final String CSEBASE_ENTITY = "CSEB";

	/** Name used for the persisted {@link AeEntity} entity */
	public static final String AE_ENTITY = "AE";

	/** Name used for the persisted {@link AeAnncEntity} entity */
	public static final String AE_ANNC_ENTITY = "AEA";

	/** Name used for the persisted {@link ContainerEntity} entity */
	public static final String CONTAINER_ENTITY = "CNT";
	
	/** Name used for the persisted {@link ContainerEntity} entity */
	public static final String CONTAINER_ANNC_ENTITY = "CNTA";

	/** Name used to the persisted {@link FlexContainerEntity} entity */
	public static final String FLEXCONTAINER_ENTITY = "FCNT";

	/** Name used to the persisted {@link FlexContainerAnncEntity} entity */
	public static final String FLEXCONTAINER_ANNC_ENTITY = "FCNTA";

	/** Name used for the persisted {@link ContentInstanceEntity} entity */
	public static final String CONTENTINSTANCE_ENTITY = "CIN";

	/** Name used for the persisted {@link SubscriptionEntity} entity */
	public static final String SUBSCRIPTION_ENTITY = "SUB";

	/** Name used for the persisted {@link RemoteCSEEntity} entity */
	public static final String REMOTECSE_ENTITY = "CSR";

	/** Name used for the persisted {@link UriMapperEntity} entity */
	public static final String URI_MAPPER_ENTITY = "URI_MAPPER";

	/** Name used for the persisted {@link LabelEntity} entity */
	public static final String LABEL_ENTITY = "LBL";

	/** Name used for the persisted {@link PollingChannelEntity} entity */
	public static final String POLLING_CHANNEL_ENTITY = "PCH";

	public static final String REQUEST_ENTITY = "REQ";

	public static final String NODE_ENTITY = "NODE";

	public static final String NODE_ANNC_ENTITY = "NODEA";

	/**
	 * Name used for the persisted (@link {@link CustomAttributeEntity} entity
	 */
	public static final String CUSTOM_ATTRIBUTE_ENTITY = "CA";

	/**
	 * Name used for the persisted {@link CreatedAnnouncedResourceEntity} entity
	 */
	public static final String ANNOUNCED_RESOURCE_ENTITY = "ANNC_RESOURCES";

	/**
	 * Name used for the persisted
	 * {@link DynamicAuthorizationConsultationEntity} entity
	 */
	public static final String DYNAMIC_AUTHORIZATION_CONSULTATION_ENTITY = "DYNAMIC_AUTHORIZATION_CONSULTATION_ENTITY";

	/** Name of the GROUP entity */
	public static final String GROUP_ENTITY = "GRP";
	// JOIN-LINKS TABLES NAMES AND COLUMNS
	// AccessControlPolicy - AccessControlRules - self privileges
	/**
	 * Name of the join table between AccessControlPolicy and its self
	 * privileges
	 */
	public static final String ACPACR_SEFPRIVILEGES = "ACP_ACR_PVS";
	/** Name of the AccessControlPolicy Resource Id in the join table */
	public static final String ACPID_COLUMN = "ACP_RI";
	/** Name of the AccessControlRule Id in the join table */
	public static final String ACCESSCONTROLRULE_ID = "ACR_ID";
	/** Name of the AccessControlRule Id in the join table */
	public static final String ACRID_COLUMN = "ACR_ID";

	// AccessControlPolicy - AccessControlRules - Privileges
	/** Name of the join table between AccessControlPolicy and its privileges */
	public static final String ACPACR_PRIVILEGES = "ACP_ACR_PV";

	// CSEBase - AccessControlPolicies
	/**
	 * Name of the join table between CSEBase and its own AccessControlPolicy
	 */
	public static final String CSEBACP_JOIN = "CSEB_ACP";
	/**
	 * Name of the join table between CSEBase and its child AccessControlPolicy
	 */
	public static final String CSEBCHILDACP_JOIN = "CSEB_CHILDACP";
	/** CSE base id in join table */
	public static final String CSEB_JOIN_ID = "CSEB_ID";
	/** ACP id in join table */
	public static final String ACP_JOIN_ID = "ACP_ID";

	// CSB - AE
	/** Name of the join table between CSEBase and ApplicationEntities */
	public static final String CSEBAE_JOIN = "CSEB_AE";

	// AE - ACP
	/** Name of the join table between AE and its AccessControlPolicies */
	public static final String AEACP_JOIN = "AE_ACP";
	/** Name of the join table between AEANNC and its AccessControlPolicies */
	public static final String AEANNCACP_JOIN = "AEANNC_ACP";
	
	// AE - DAC
	/** Name of the join table betwen AE and its DAC */
	public static final String AE_DAC_JOIN = "AE_DAC_JOIN";
	
	// AEAnnc - DAC
	public static final String AEANNC_DAC_JOIN = "AEANNC_DAC_JOIN";

	/** Id of AE in join table */
	public static final String AE_JOINID = "AE_ID";
	/** Id of AEANNC in join table */
	public static final String AEANNC_JOINID = "AEANNC_ID";

	// AE - ch ACP
	/** Name of the join table between AE and child ACP */
	public static final String AEACPCHILD_JOIN = "AE_CHACP";

	// CNT - CNT
	/**
	 * Name of the join table between ContainerEntity and its child
	 * ContainerEntities
	 */
	public static final String CNTCNTCHILD_JOIN = "CNT_CHCNT";
	/** Name of the Container ID */
	public static final String CNT_JOIN_ID = "CNT_ID";
	/** Name of the Child Container ID */
	public static final String CNTCH_JOIN_ID = "CNTCH_ID";

	// CNT - FCNT
	/**
	 * Name of the join table between ContainerEntity and its child
	 * FlexContainerEntities
	 */
	public static final String CNT_FCNTCHILD_JOIN = "CNT_FCNTCHILD_JOIN";

	// FCNT- FCNT
	/**
	 * Name of the join table between FlexContainerEntity and its child
	 * FlexContainerEntities
	 */
	public static final String FCNT_FCNTCHILD_JOIN = "FCNT_CHFCNT";
	/** Name of the FlexContainer ID */
	public static final String FCNT_ID = "FCNT_ID";
	/** Name of the Child FlexContainer ID */
	public static final String FCNTCH_JOIN_ID = "CFCNT_ID";

	// FCNT - CNT
	/**
	 * Name of the join table between FlexContainerEntity and its child
	 * ContainerEntities
	 */
	public static final String FCNT_CNTCHILD_JOIN = "FCNT_CHCNT";

	// AE - CNT
	/**
	 * Name of the join table between ApplicationEntities and its child
	 * ContainerEntities
	 */
	public static final String AECNTCHILD_JOIN = "AE_CNT_JOIN";

	// AE -FCNT
	/**
	 * Name of the join table between Application Entities and its child
	 * FlexContainers
	 */
	public static final String AE_FCNTCHILD_JOIN = "AE_FCNT_JOIN";

	// AEANNC - FCNTA
	public static final String AEANNC_FCNT_JOIN = "AEANNC_FCNTA_JOIN";

	// CSEB - CNT
	/**
	 * Name of the join table between CSEBaseEntity and its child
	 * ContainerEntities
	 */
	public static final String CSEB_CNT_JOIN = "CSEB_CNT_JOIN";
	/** Name of the join table between CNT and ACP */
	public static final String CNTACP_JOIN = "CNT_ACP_JOIN";
	/** Name of the join table between CNT and DAC */ 
	public static final String CNT_DAC_JOIN = "CNT_DYNAMIC_AUTHORIZATION_CONSULTATION_JOIN";
	/** Name of the join table between CNT and child CIN */
	public static final String CNTCINCHILD_JOIN = "CNT_CIN_JOIN";
	/** ID of CIN in the join table */
	public static final String CINCH_JOIN_ID = "CINCH_ID";
	
	// CIN - ACP
	public static final String CIN_ACP_JOIN = "CIN_ACP_JOIN";
	public static final String CIN_JOIN_ID = "CIN_ID";

	// CIN - DAC
	public static final String CIN_DAC_JOIN = "CIN_DAC_JOIN";
	
	// CSEB - FCNT
	/**
	 * Name of the join table between CSEBaseEntity and its child
	 * FlexContainerEntities
	 */
	public static final String CSEB_FCNT_JOIN = "CSEB_FCNT_JOIN";
	/** Name of the join table between FCNT and ACP */
	public static final String FCNT_ACP_JOIN = "FCNT_ACP_JOIN";
	/** Name of the join table between FCNT and Container */
	public static final String FCNT_CNT_JOIN = "FCNT_CNT_JOIN";
	/** Name of the FlexContainer ID */
	public static final String FCNT_JOIN_ID = "FCNT_ID";
	/** Name of the join table between FCNT and DAC */
	public static final String FCNT_DAC_JOIN = "FCNT_DAC_JOIN";

	// FCNTA
	public static final String FCNTA_JOIN_ID = "FCNTA_ID";
	public static final String FCNTA_ACP_JOIN = "FCNTA_ACP_JOIN";
	public static final String FCNTA_DAC_JOIN = "FCNTA_DAC_JOIN";
	public static final String FCNTASUB_JOIN_ID = "FCNTASUB_JOIN_ID";
	public static final String FCNTA_FCNTACHILD_JOIN = "FCNTA_FCNTACHILD_JOIN";
	/** Name of the Child FlexContainerAnnc ID */
	public static final String FCNTACH_JOIN_ID = "CFCNTA_ID";

	// SUB - ACP
	/**
	 * Name of the join table between Subscription entity and its AccessControl
	 * policies
	 */
	public static final String SUBACP_JOIN = "SUB_ACP_JOIN";
	/** name of the ID for subscription entity in the join tables */
	public static final String SUB_JOIN_ID = "SUB_ID";
	
	// SUB - DAC
	/** Name of the join table between Subscription and DynamicAuthorizationConsultation */
	public static final String SUB_DAC_JOIN = "SUB_DAC_JOIN";

	/** name of the join table between CSEBase and remoteCSE */
	public static final String CSBCSR_JOIN = "CSB_CSR";
	/** ID of the CSR in join table */
	public static final String CSR_JOIN_ID = "CSR_ID";

	// CSR - ACP
	/** Name of the join table between CSR and ACP */
	public static final String CSRACP_JOIN = "CSR_ACP_JOIN";
	
	// CSR - DAC
	/** Name of the join table between CSR and DAC */
	public static final String CSR_DAC_JOIN = "CSR_DAC_JOIN";

	/** Name of the join table between GRP & ACP */
	public static final String GRPACP_JOIN = "GRP_ACP_JOIN";
	/** Name of the join table between GRP & DAC */
	public static final String GRP_DAC_JOIN = "GRP_DAC_JOIN";
	/** ID of GRP in join table */
	public static final String GRP_JOIN_ID = "grp_id";

	/** Name of the join table between CSEB & GRP */
	public static final String CSEB_GRP_JOIN = "CSB_GRP_JOIN";
	/** Name of the join table between CSR & GRP */
	public static final String CSR_GRP_JOIN = "CSR_GRP_JOIN";
	/** Name of the join table between AE & ch GRP */
	public static final String AEGRPCHILD_JOIN = "AE_CHGRP_JOIN";
	/** Name of the join table between CSR & AE */
	public static final String CSRAECHILD_JOIN = "CSR_CHAE_JOIN";
	/** Name of the join table between CSR & ch CNT */
	public static final String CSRCNTCHILD_JOIN = "CSR_CHCNT_JOIN";
	/** Name of the join table between CSR & ch FCNT */
	public static final String CSR_FCNTCHILD_JOIN = "CSR_CHFCNT_JOIN";
	/** Name of the join table between CSR & ch GRP */
	public static final String CSRGRPCHILD_JOIN = "CSR_CHGRP_JOIN";
	/** Name of the join table between CSR & ch ACP */
	public static final String CSRACPCHILD_JOIN = "CSR_ACPCH_JOIN";
	/** Name of the join table between CSR & ch AEANNC */
	public static final String CSRAEANNCCHILD_JOIN = "CSR_AEANNCH_JOIN";

	// SUB - AE
	/** Name of the join table between AE & SUB */
	public static final String AESUB_JOIN = "AE_SUB_JOIN";
	/** Name of the join table between CNT & SUB */
	public static final String CNTSUB_JOIN = "CNT_SUB_JOIN";
	/** Name of the join table between FCNT & SUB */
	public static final String FCNTSUB_JOIN = "FCNT_SUB_JOIN";
	/** Name of the join table between CSR & SUB */
	public static final String CSRSUB_JOIN = "CSR_SUB_JOIN";
	/** Name of the join table between CSB & SUB */
	public static final String CSBSUB_JOIN = "CSB_SUB_JOIN";
	/** Name of the join table between GRP & SUB */
	public static final String GRPSUB_JOIN = "GRP_SUB_JOIN";
	/** Name of the join table between ACP & SUB */
	public static final String ACPSUB_JOIN = "ACP_SUB_JOIN";
	/** Name of the join table between SCH & SUB */
	public static final String SCHSUB_JOIN = "SCH_SUB_JOIN";
	/** Name of the join table between SCH & ACP */
	public static final String SCHACP_JOIN = "SCHACP_JOIN";
	/** Name of the join table between SCH & DAC */
	public static final String SCH_DAC_JOIN = "SCH_DAC_JOIN";
	/** ID of AEANNCSUB in join table */
	public static final String AEANNCSUB_JOIN_ID = "AEANNCSUB_JOIN_ID";

	/** Name of the join table between AE & PCH */
	public static final String AEPCH_JOIN = "AE_PCH_JOIN";
	/** Name of the join table between CSR & PCH */
	public static final String CSRPCH_JOIN = "CSR_PCH_JOIN";
	/** Name of the join table between ACP & PCH */
	public static final String ACPPCH_JOIN = "ACP_PCH_JOIN";
	/** ID of SCH in join table */
	public static final String SCH_JOIN_ID = "SCH_JOIN_ID";
	/** ID of PCH in join table */
	public static final String PCH_JOIN_ID = "PCH_JOIN_ID";

	// CSEB - REQ
	/** Name of the join table between CSEB & REQ */
	public static final String CSEB_REQ_JOIN = "CSEB_REQ_JOIN";
	/** ID of the REQ in join table */
	public static final String REQ_JOIN_ID = "REQ_JOIN_ID";

	// NODE - ACP
	public static final String ACP_NOD_JOIN = "NOD_ACP_JOIN";
	public static final String NOD_JOIN_ID = "NOD_JOIN_ID";
	public static final String CSB_NOD_CH_JOIN = "CSB_NOD_CH_JOIN";
	public static final String CSR_NOD_CH_JOIN = "CSR_NOD_CH_JOIN";
	public static final String NOD_SUB_JOIN = "NOD_SUB_JOIN";
	
	public static final String ACP_NODANNC_JOIN = "NODANNC_ACP_JOIN";
	public static final String NODANNC_JOIN_ID = "NODANNC_JOIN_ID";
	public static final String CSB_NODANNC_CH_JOIN = "CSB_NODANNC_CH_JOIN";
	public static final String CSR_NODANNC_CH_JOIN = "CSR_NODANNC_CH_JOIN";
	public static final String NODANNC_SUB_JOIN = "NODANNC_SUB_JOIN";
	
	// NODE - DAC
	public static final String NOD_DAC_JOIN = "NOD_DAC_JOIN";
	public static final String NODANNC_DAC_JOIN = "NODANNC_DAC_JOIN";


	// FCNT - CUSTOM_ATTRIBUTE
	public static final String FCNT_CA_JOIN = "FCNT_CA_JOIN";
	public static final String CA_JOIN_ID = "CA_ID";

	// MGMT OBJ
	// AreaNetworkInfo
	public static final String ANI_ACP_JOIN = "ANI_ACP_JOIN";
	public static final String ANI_DAC_JOIN = "ANI_DAC_JOIN";
	public static final String ANI_JOIN_ID = "ANI_JOIN_ID";
	public static final String ANI_SUB_JOIN = "ANI_SUB_JOIN";
	public static final String ANI_NOD_JOIN = "ANI_NOD_JOIN";
	public static final String ANI_NODANNC_JOIN = "ANI_NODANNC_JOIN";
	// AreaNetworkDeviceInfo
	public static final String ANDI_ACP_JOIN = "ANDI_ACP_JOIN";
	public static final String ANDI_DAC_JOIN = "ANDI_DAC_JOIN";
	public static final String ANDI_JOIN_ID = "ANDI_JOIN_ID";
	public static final String ANDI_SUB_JOIN = "ANDI_SUB_JOIN";
	public static final String ANDI_NOD_JOIN = "ANDI_NOD_JOIN";
	public static final String ANDI_NODANNC_JOIN = "ANDI_NODANNC_JOIN";
	// DeviceInfo
	public static final String DVI_ACP_JOIN = "DVI_ACP_JOIN";
	public static final String DVI_DAC_JOIN = "DVI_DAC_JOIN";
	public static final String DVI_JOIN_ID = "DVI_JOIN_ID";
	public static final String DVI_SUB_JOIN = "DVI_SUB_JOIN";
	public static final String DVI_NOD_JOIN = "DVI_NOD_JOIN";
	public static final String DVI_NODANNC_JOIN = "DVI_NODANNC_JOIN";

	// ANNOUNCED RESOURCE
	public static final String REMOTE_RESOURCE_ID = "REMOTE_RESOURCE_ID";
	public static final String LOCAL_RESOURCE_ID = "LOCAL_RESOURCE_ID";
	public static final String ANNOUNCE_CSE_ID = "ANNOUNCE_CSE_ID";

	// Announced MGMT OBJ
	// AreaNetworkAnncInfo
	public static final String ANIA_ACP_JOIN = "ANIA_ACP_JOIN";
	public static final String ANIA_DAC_JOIN = "ANIA_DAC_JOIN";
	public static final String ANIA_JOIN_ID = "ANIA_JOIN_ID";
	public static final String ANIA_SUB_JOIN = "ANIA_SUB_JOIN";
	public static final String ANIA_NOD_JOIN = "ANIA_NOD_JOIN";
	public static final String ANIA_NODANNC_JOIN = "ANIA_NODANNC_JOIN";
	// AreaNetworkAnncDeviceInfo
	public static final String ANDIA_ACP_JOIN = "ANDIA_ACP_JOIN";
	public static final String ANDIA_DAC_JOIN = "ANDIA_DAC_JOIN";
	public static final String ANDIA_JOIN_ID = "ANDIA_JOIN_ID";
	public static final String ANDIA_SUB_JOIN = "ANDIA_SUB_JOIN";
	public static final String ANDIA_NOD_JOIN = "ANDIA_NOD_JOIN";
	public static final String ANDIA_NODANNC_JOIN = "ANDIA_NODANNC_JOIN";
	// AnncDeviceInfo
	public static final String DVIA_ACP_JOIN = "DVIA_ACP_JOIN";
	public static final String DVIA_DAC_JOIN = "DVIA_DAC_JOIN";
	public static final String DVIA_JOIN_ID = "DVIA_JOIN_ID";
	public static final String DVIA_SUB_JOIN = "DVIA_SUB_JOIN";
	public static final String DVIA_NOD_JOIN = "DVIA_NOD_JOIN";
	public static final String DVIA_NODANNC_JOIN = "DVIA_NODANNC_JOIN";

	// DynamicAuthorisationConsultation DAC
	public static final String DAC_JOINID = "DAC_JOINID";

	// DynamicAuthorizationConsutation-AccessControlPolicies
	public static final String DAC_ACP_JOIN = "DAC_ACP_JOIN";
	public static final String ACP_DAC_JOIN = "ACP_DAC_JOIN";

	// DynamicAuthorizationConsultation - DynamicAuthorizationConsultation
	/**
	 * Name of the join table for link a DynamicAuthorizationConsultation entity
	 * with a child of type DynamicAuthorizationConsultation
	 */
	public static final String DAC_DACCHILD_JOIN = "DAC_DACCHILD_JOIN";
	/** Name of the Child DynamicAuthorizationConsultation ID */
	public static final String DACCHILD_JOIN_ID = "DACCHILD_JOIN_ID";

	// DynamicAuthorizationConsultation - CseBase
	/**
	 * name of the join table between a DynamicAuthorizationConsultation entity
	 * and its parent of type CseBase
	 */
	public static final String CSEB_CHILDDAC_JOIN = "CSEB_CHILDDAC_JOIN";
	/** name of the join table between a CseBase and its DynamicAuthorizationConsultationIDs */ 
	public static final String CSEB_DAC_JOIN = "CSEB_DAC_JOIN";

	// Dynamic AuthorizationConsultation - RemoteCSE
	/**
	 * name of the join table between a DynamicAuthorizationConsultation entity
	 * and its parent of type RemoceCse
	 */
	public static final String CSR_DACCHILD_JOIN = "CSR_DACCHILD_JOIN";

	// Dynamic AuthorizationConsultion - AE
	/**
	 * name of the join table between a DynamicAuthorizationConsultation entity
	 * and its parent of type Ae
	 */
	public static final String AE_DACCHILD_JOIN = "AE_DACCHILD_JOIN";

}
