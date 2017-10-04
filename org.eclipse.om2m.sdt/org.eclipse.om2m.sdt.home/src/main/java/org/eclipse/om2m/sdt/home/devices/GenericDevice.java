/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.devices;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.exceptions.PropertyException;
import org.eclipse.om2m.sdt.home.types.DeviceType;
import org.eclipse.om2m.sdt.home.types.PropertyType;
import org.eclipse.om2m.sdt.types.SimpleType;

public class GenericDevice extends Device {

	private Property deviceManufacturer;
	private Property deviceSerialNum;
	private Property deviceModelName;

	private Property deviceType;
	private DeviceType type;

	private Property deviceName;
	private Property deviceSubModelName;
	private Property deviceAliasName;
	private Property deviceFirmwareVersion;
	private Property hardwareVersion;
	private Property osVersion;
	private Property protocol;
	private Property country;
	private Property location;
	private Property systemTime;
	private Property manufacturerDetailsLink;
	private Property dateOfManufacture;
	private Property supportURL;
	private Property presentationURL;
	private Property cloud;

	public GenericDevice(final String id, final String serial, final Domain domain) {
		this(id, serial, DeviceType.undefinedVendorExt, domain);
	}

	public GenericDevice(final String id, final String serial, 
			final DeviceType type, final Domain domain) {
		super(id, domain, type);
		this.type = type;
		deviceType = new Property(PropertyType.deviceType, Integer.toString(type.getValue()));
		deviceType.setType(SimpleType.Integer);
		deviceType.setDoc("Device type");
		super.addProperty(deviceType);

		deviceSerialNum = new Property(PropertyType.deviceSerialNum, serial);
		deviceSerialNum.setType(SimpleType.String);
		deviceSerialNum.setDoc("Device serial number (assigned by manufacturer)");
		super.addProperty(deviceSerialNum);

		deviceManufacturer = new Property(PropertyType.deviceManufacturer);
		deviceManufacturer.setType(SimpleType.String);
		deviceManufacturer.setDoc("Manufacturer name of the device");
		super.addProperty(deviceManufacturer);

		deviceModelName = new Property(PropertyType.deviceModelName);
		deviceModelName.setType(SimpleType.String);
		deviceModelName.setDoc("Device Model Name");
		super.addProperty(deviceModelName);
	}

	public void addProperty(Property property) {
		PropertyType type = PropertyType.fromShortName(property.getShortName());
//		if (type == null)
//			throw new IllegalAccessException("");
		addProperty(type, property.getValue());
	}

	public void addProperty(PropertyType type, String val) {
		switch (type) {
		case deviceManufacturer: setDeviceManufacturer(val); return;
		case deviceModelName: setDeviceModelName(val); return;
		case deviceName: setDeviceName(val); return;
		case deviceSubModelName: setDeviceSubModelName(val); return;
		case deviceAliasName: setDeviceAliasName(val); return;
		case deviceFirmwareVersion: setDeviceFirmwareVersion(val); return;
		case hardwareVersion: setHardwareVersion(val); return;
		case osVersion: setOsVersion(val); return;
		case protocol: setProtocol(val); return;
		case country: setCountry(val); return;
		case location: setLocation(val); return;
		case systemTime: setSystemTime(val); return;
		case manufacturerDetailsLink: setManufacturerDetailsLink(val); return;
		case dateOfManufacture: setDateOfManufacture(val); return;
		case supportURL: setSupportURL(val); return;
		case presentationURL: setPresentationURL(val); return;
		case cloud: setCloud(val); return;
		default:
			super.addProperty(new Property(type, val)); return;
		}
	}

	protected void setDeviceType(DeviceType type) {
		this.type = type;
		this.deviceType.setValue(Integer.toString(type.getValue()));
	}

	public DeviceType getDeviceType() {
		return type;
	}

	public String getSerialNumber() {
		return deviceSerialNum.getValue();
	}

	public String getDeviceManufacturer() {
		return deviceManufacturer.getValue();
	}

	public void setDeviceManufacturer(final String s) {
		deviceManufacturer.setValue(s);
	}

	public String getDeviceModelName() {
		return deviceModelName.getValue();
	}

	public void setDeviceModelName(String s) {
		deviceModelName.setValue(s);
	}
	
	public String getDeviceName() {
		return (deviceName == null) ? null : deviceName.getValue();
	}
	
	public void setDeviceName(String s) {
		if (deviceName == null) {
			deviceName = new Property(PropertyType.deviceName);
			deviceName.setType(SimpleType.String);
			deviceName.setOptional(true);
			deviceName.setDoc("Device name");
			super.addProperty(deviceName);
		}
		deviceName.setValue(s);
	}
	
	public String getDeviceSubModelName() {
		return (deviceSubModelName == null) ? null : deviceSubModelName.getValue();
	}
	
	public void setDeviceSubModelName(String s) {
		if (deviceSubModelName == null) {
			deviceSubModelName = new Property(PropertyType.deviceSubModelName);
			deviceSubModelName.setType(SimpleType.String);
			deviceSubModelName.setOptional(true);
			deviceSubModelName.setDoc("Device sub-modelname");
			super.addProperty(deviceSubModelName);
		}
		deviceSubModelName.setValue(s);
	}
	
	public String getDeviceAliasName() {
		return (deviceAliasName == null) ? null : deviceAliasName.getValue();
	}
	
	public void setDeviceAliasName(String s) {
		if (deviceAliasName == null) {
			deviceAliasName = new Property(PropertyType.deviceAliasName);
			deviceAliasName.setType(SimpleType.String);
			deviceAliasName.setOptional(true);
			deviceAliasName.setDoc("Device alias name");
			super.addProperty(deviceAliasName);
		}
		deviceAliasName.setValue(s);
	}
	
	public String getDeviceFirmwareVersion() {
		return (deviceFirmwareVersion == null) ? null : deviceFirmwareVersion.getValue();
	}
	
	public void setDeviceFirmwareVersion(String s) {
		if (deviceFirmwareVersion == null) {
			deviceFirmwareVersion = new Property(PropertyType.deviceFirmwareVersion);
			deviceFirmwareVersion.setType(SimpleType.String);
			deviceFirmwareVersion.setOptional(true);
			deviceFirmwareVersion.setDoc("Device firmware version");
			super.addProperty(deviceFirmwareVersion);
		}
		deviceFirmwareVersion.setValue(s);
	}
	
	public String getHardwareVersion() {
		return (hardwareVersion == null) ? null : hardwareVersion.getValue();
	}
	
	public void setHardwareVersion(String s) {
		if (hardwareVersion == null) {
			hardwareVersion = new Property(PropertyType.hardwareVersion);
			hardwareVersion.setType(SimpleType.String);
			hardwareVersion.setOptional(true);
			hardwareVersion.setDoc("Device hardware version");
			super.addProperty(hardwareVersion);
		}
		hardwareVersion.setValue(s);
	}
	
	public String getOsVersion() {
		return (osVersion == null) ? null : osVersion.getValue();
	}
	
	public void setOsVersion(String s) {
		if (osVersion == null) {
			osVersion = new Property(PropertyType.osVersion);
			osVersion.setType(SimpleType.String);
			osVersion.setOptional(true);
			osVersion.setDoc("Version of the operation system (defined by manufacturer)");
			super.addProperty(osVersion);
		}
		osVersion.setValue(s);
	}
	
	public String getProtocol() {
		return (protocol == null) ? null : protocol.getValue();
	}
	
	public void setProtocol(String s) {
		if (protocol == null) {
			protocol = new Property(PropertyType.protocol);
			protocol.setType(SimpleType.String);
			protocol.setOptional(true);
			protocol.setDoc("A comma separated list of MIME types for all supported communication protocol(s) of the device. Example: “application/x-alljoin;version=1.0,application/x-echonet-lite;version=1.0” indicates the device supports both AllJoyn v1.0 and Echonet Lite v1.0.");
			super.addProperty(protocol);
		}
		protocol.setValue(s);
	}
	
	public String getCountry() {
		return (country == null) ? null : country.getValue();
	}
	
	public void setCountry(String s) {
		if (country == null) {
			country = new Property(PropertyType.country);
			country.setType(SimpleType.String);
			country.setOptional(true);
			country.setDoc("Country code of the device");
			super.addProperty(country);
		}
		country.setValue(s);
	}
	
	public String getLocation() {
		return (location == null) ? null : location.getValue();
	}
	
	public void setLocation(String s) {
		if (location == null) {
			location = new Property(PropertyType.location);
			location.setType(SimpleType.String);
			location.setOptional(true);
			location.setDoc("Location where the device is installed. It may be configured via the user interface provided by  the ‘presentationURL’ property or any other means.");
			super.addProperty(location);
		}
		location.setValue(s);
	}

	public Date getSystemTime() throws PropertyException {
		if (systemTime == null)
			return null;
		try {
			String s = systemTime.getValue();
			return (s == null) ? null : new Date(Long.parseLong(s));
		} catch (NumberFormatException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setSystemTime(Date s) {
		if (s != null)
			setSystemTime(Long.toString(s.getTime()));
	}
	
	private void setSystemTime(String s) {
		if (systemTime == null) {
			systemTime = new Property(PropertyType.systemTime);
			systemTime.setType(SimpleType.Datetime);
			systemTime.setOptional(true);
			systemTime.setDoc("Reference time for the device");
			super.addProperty(systemTime);
		}
		if (s != null)
			systemTime.setValue(s);
	}
	
	public URL getManufacturerDetailsLink() throws PropertyException {
		if (manufacturerDetailsLink == null)
			return null;
		try {
			String s = manufacturerDetailsLink.getValue();
			return (s == null) ? null : new URL(s);
		} catch (MalformedURLException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setManufacturerDetailsLink(URL s) {
		if (s != null)
			setManufacturerDetailsLink(s.toString());
	}
	
	private void setManufacturerDetailsLink(String s) {
		if (manufacturerDetailsLink == null) {
			manufacturerDetailsLink = new Property(PropertyType.manufacturerDetailsLink);
			manufacturerDetailsLink.setType(SimpleType.String);
			manufacturerDetailsLink.setOptional(true);
			manufacturerDetailsLink.setDoc("URL to manufacturer’s website");
			super.addProperty(manufacturerDetailsLink);
		}
		if (s != null)
			manufacturerDetailsLink.setValue(s);
	}
	
	public Date getDateOfManufacture() throws PropertyException {
		if (dateOfManufacture == null)
			return null;
		try {
			String s = dateOfManufacture.getValue();
			return (s == null) ? null : new Date(Long.parseLong(s));
		} catch (NumberFormatException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setDateOfManufacture(Date s) {
		if (s != null)
			setDateOfManufacture(Long.toString(s.getTime()));
	}
	
	private void setDateOfManufacture(String s) {
		if (dateOfManufacture == null) {
			dateOfManufacture = new Property(PropertyType.dateOfManufacture);
			dateOfManufacture.setType(SimpleType.Datetime);
			dateOfManufacture.setOptional(true);
			dateOfManufacture.setDoc("Manufacturing date of device");
			super.addProperty(dateOfManufacture);
		}
		if (s != null)
			dateOfManufacture.setValue(s);
	}
	
	public URL getSupportURL() throws PropertyException {
		if (supportURL == null)
			return null;
		try {
			String s = supportURL.getValue();
			return (s == null) ? null : new URL(s);
		} catch (MalformedURLException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setSupportURL(URL s) {
		if (s != null)
			setSupportURL(s.toString());
	}
	
	private void setSupportURL(String s) {
		if (supportURL == null) {
			supportURL = new Property(PropertyType.supportURL);
			supportURL.setType(SimpleType.String);
			supportURL.setOptional(true);
			supportURL.setDoc("URL that points to product support information of the device");
			super.addProperty(supportURL);
		}
		if (s != null)
			supportURL.setValue(s);
	}
	
	public URL getPresentationURL() throws PropertyException {
		if (presentationURL == null)
			return null;
		try {
			String s = presentationURL.getValue();
			return (s == null) ? null : new URL(s);
		} catch (MalformedURLException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setPresentationURL(URL s) {
		if (s != null)
			setPresentationURL(s.toString());
	}
	
	private void setPresentationURL(String s) {
		if (presentationURL == null) {
			presentationURL = new Property(PropertyType.presentationURL);
			presentationURL.setType(SimpleType.String);
			presentationURL.setOptional(true);
			presentationURL.setDoc("To quote UPnP: “the control point can retrieve a page from this URL, load the page into a web browser, and depending on the capabilities of the page, allow a user to control the device and/or view device status. The degree to which each of these can be accomplished depends on the specific capabilities of the presentation page and device.”");
			super.addProperty(presentationURL);
		}
		if (s != null)
			presentationURL.setValue(s);
	}
	
	public boolean getCloud() throws PropertyException {
		if (cloud == null)
			throw new PropertyException("Not implemented");
		return Boolean.parseBoolean(cloud.getValue());
	}
	
	public void setCloud(boolean s) {
		setCloud(Boolean.toString(s));
	}
	
	private void setCloud(String s) {
		if (cloud == null) {
			cloud = new Property(PropertyType.cloud);
			cloud.setType(SimpleType.Boolean);
			cloud.setOptional(true);
			cloud.setDoc("Device managed from the cloud");
			super.addProperty(cloud);
		}
		cloud.setValue(s);
	}
	
}
