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
import org.eclipse.om2m.commons.resource.DeviceInfo;
import org.eclipse.om2m.commons.resource.MgmtObj;

/**
 * Device Info entity - Specialization of MgmtObj
 *
 */
@Entity(name = ShortName.DEVICE_INFO)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DeviceInfoEntity extends MgmtObjEntity {

	@Column(name = ShortName.DEVICE_LABEL)
	protected String deviceLabel;
	@Column(name = ShortName.DEVICE_TYPE)
	protected String deviceType;
	@Column(name = ShortName.DEVICE_MODEL)
	protected String model;
	@Column(name = ShortName.MANUFACTURER)
	protected String manufacturer;

	@Column(name = ShortName.FW_VERSION)
	protected String fwVersion;
	@Column(name = ShortName.SW_VERSION)
	protected String swVersion;
	@Column(name = ShortName.HW_VERSION)
	protected String hwVersion;
	@Column(name = ShortName.OS_VERSION)
	protected String osVersion;
	@Column(name = ShortName.MANUF_DET_LINKS)
	protected String manufacturerDetailsLink;
	@Column(name = ShortName.MANUF_DATE)
	protected String manufacturingDate;
	@Column(name = ShortName.DEVICE_SUB_MODEL)
	protected String subModel;
	@Column(name = ShortName.DEVICE_NAME)
	protected String deviceName;
	@Column(name = ShortName.COUNTRY)
	protected String country;
	@Column(name = ShortName.LOCATION)
	protected String location;
	@Column(name = ShortName.SYS_TIME)
	protected String systemTime;
	@Column(name = ShortName.SUPPORT_URL)
	protected String supportURL;
	@Column(name = ShortName.PRES_URL)
	protected String presentationURL;
	@Column(name = ShortName.PROTOCOL)
	protected String protocol;

	/** AccessControlPolicies linked to the MgmtObj */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name = DBEntities.DVI_ACP_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DVI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.ACP_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<AccessControlPolicyEntity> accessControlPolicies;
	
	/** List of DynamicAuthorizationConsultations*/
	@ManyToMany(fetch=FetchType.LAZY, mappedBy="linkedDeviceInfoEntities")
	@JoinTable(
			name = DBEntities.DVI_DAC_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DVI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.DAC_JOINID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<DynamicAuthorizationConsultationEntity> dynamicAuthorizationConsultations;
	
	// Database link to Subscriptions
	@OneToMany(fetch = FetchType.LAZY, targetEntity = SubscriptionEntity.class, mappedBy="parentDvi")
	@JoinTable(
			name = DBEntities.DVI_SUB_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DVI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.SUB_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected List<SubscriptionEntity> subscriptions;	
	
	// Database link to Node
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = NodeEntity.class)
	@JoinTable(
			name = DBEntities.DVI_NOD_JOIN,
			joinColumns = { @JoinColumn(name = DBEntities.DVI_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, 
			inverseJoinColumns = { @JoinColumn(name = DBEntities.NOD_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }
			)
	protected NodeEntity parentNode;

	public DeviceInfoEntity() {
		this.mgmtDefinition = MgmtDefinitionTypes.DEVICE_INFO;
	}

	/**
	 * @return the subscriptions
	 */
	@Override
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
	 * @return the parentNode
	 */
	@Override
	public NodeEntity getParentNode() {
		return parentNode;
	}

	/**
	 * @param parentNode the parentNode to set
	 */
	@Override
	public void setParentNode(NodeEntity parentNode) {
		this.parentNode = parentNode;
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
	
	
	@Override
	public List<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationConsultations() {
		if (this.dynamicAuthorizationConsultations == null) {
			this.dynamicAuthorizationConsultations = new ArrayList<>();
		}
		return dynamicAuthorizationConsultations;
	}
	
	@Override
	public void setDynamicAuthorizationConsultations(List<DynamicAuthorizationConsultationEntity> list) {
		this.dynamicAuthorizationConsultations = list;
	}

	/**
	 * @return the deviceLabel
	 */
	public String getDeviceLabel() {
		return deviceLabel;
	}

	/**
	 * @param deviceLabel the deviceLabel to set
	 */
	public void setDeviceLabel(String deviceLabel) {
		this.deviceLabel = deviceLabel;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the manufacturer
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the fwVersion
	 */
	public String getFwVersion() {
		return fwVersion;
	}

	/**
	 * @param fwVersion the fwVersion to set
	 */
	public void setFwVersion(String fwVersion) {
		this.fwVersion = fwVersion;
	}

	/**
	 * @return the swVersion
	 */
	public String getSwVersion() {
		return swVersion;
	}

	/**
	 * @param swVersion the swVersion to set
	 */
	public void setSwVersion(String swVersion) {
		this.swVersion = swVersion;
	}

	/**
	 * @return the hwVersion
	 */
	public String getHwVersion() {
		return hwVersion;
	}

	/**
	 * @param hwVersion the hwVersion to set
	 */
	public void setHwVersion(String hwVersion) {
		this.hwVersion = hwVersion;
	}

	/**
	 * @return the osVersion
	 */
	public String getOsVersion() {
		return osVersion;
	}

	/**
	 * @param osVersion the osVersion to set
	 */
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	/**
	 * @return the manufacturerDetailsLink
	 */
	public String getManufacturerDetailsLink() {
		return manufacturerDetailsLink;
	}

	/**
	 * @param manufacturerDetailsLink the manufacturerDetailsLink to set
	 */
	public void setManufacturerDetailsLink(String manufacturerDetailsLink) {
		this.manufacturerDetailsLink = manufacturerDetailsLink;
	}

	/**
	 * @return the manufacturingDate
	 */
	public String getManufacturingDate() {
		return manufacturingDate;
	}

	/**
	 * @param manufacturingDate the manufacturingDate to set
	 */
	public void setManufacturingDate(String manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	/**
	 * @return the subModel
	 */
	public String getSubModel() {
		return subModel;
	}

	/**
	 * @param subModel the subModel to set
	 */
	public void setSubModel(String subModel) {
		this.subModel = subModel;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the systemTime
	 */
	public String getSystemTime() {
		return systemTime;
	}

	/**
	 * @param systemTime the systemTime to set
	 */
	public void setSystemTime(String systemTime) {
		this.systemTime = systemTime;
	}

	/**
	 * @return the supportURL
	 */
	public String getSupportURL() {
		return supportURL;
	}

	/**
	 * @param supportURL the supportURL to set
	 */
	public void setSupportURL(String supportURL) {
		this.supportURL = supportURL;
	}

	/**
	 * @return the presentationURL
	 */
	public String getPresentationURL() {
		return presentationURL;
	}

	/**
	 * @param presentationURL the presentationURL to set
	 */
	public void setPresentationURL(String presentationURL) {
		this.presentationURL = presentationURL;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public void fillFrom(MgmtObj mgmtObj) {
		super.fillFrom(mgmtObj);
		DeviceInfo di = (DeviceInfo) mgmtObj;
		this.deviceLabel = di.getDeviceLabel();
		this.deviceType = di.getDeviceType();
		this.model = di.getModel();
		this.manufacturer = di.getManufacturer();
		this.fwVersion = di.getFwVersion();
		this.swVersion = di.getSwVersion();
		this.hwVersion = di.getHwVersion();
		this.osVersion = di.getOsVersion();
		this.manufacturerDetailsLink = di.getManufacturerDetailsLink();
		this.manufacturingDate = di.getManufacturingDate();
		this.subModel = di.getSubModel();
		this.deviceName = di.getDeviceName();
		this.country = di.getCountry();
		this.location = di.getLocation();
		this.systemTime = di.getSystemTime();
		this.supportURL = di.getSupportURL();
		this.presentationURL = di.getPresentationURL();
		this.protocol = di.getProtocol();
	}
	
}
