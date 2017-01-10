package org.eclipse.om2m.sdt.home.devices;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.eclipse.om2m.sdt.Device;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Property;
import org.eclipse.om2m.sdt.home.types.DeviceType;
import org.eclipse.om2m.sdt.impl.PropertyException;
import org.eclipse.om2m.sdt.types.SimpleType;

public class GenericDevice extends Device {

	private Property propDeviceManufacturer;
	private Property propDeviceSerialNum;
	private Property propDeviceModelName;

	private DeviceType type;

	private Property propDeviceName;
	private Property propDeviceSubModelName;
	private Property propDeviceAliasName;
	private Property propDeviceFirmwareVersion;
	private Property propHardwareVersion;
	private Property propOsVersion;
	private Property propProtocol;
	private Property propCountry;
	private Property propLocation;
	private Property propSystemTime;
	private Property propManufacturerDetailsLink;
	private Property propDateOfManufacture;
	private Property propSupportURL;
	private Property propPresentationURL;

	public GenericDevice(final String id, final String serial, final Domain domain) {
		this(id, serial, DeviceType.undefinedVendorExt, domain);
	}

	public GenericDevice(final String id, final String serial, 
			final DeviceType type, final Domain domain) {
		super(id, domain, type.getDefinition());
		this.type = type;

		propDeviceSerialNum = new Property("propDeviceSerialNum", serial);
		propDeviceSerialNum.setType(SimpleType.String);
		propDeviceSerialNum.setDoc("Device serial number (assigned by manufacturer)");
		addProperty(propDeviceSerialNum);

		propDeviceManufacturer = new Property("propDeviceManufacturer");
		propDeviceManufacturer.setType(SimpleType.String);
		propDeviceManufacturer.setDoc("Manufacturer name of the device");
		addProperty(propDeviceManufacturer);

		propDeviceModelName = new Property("propDeviceModelName");
		propDeviceModelName.setType(SimpleType.String);
		propDeviceModelName.setDoc("Device Model Name");
		addProperty(propDeviceModelName);

		propDeviceName = new Property("propDeviceName");
		propDeviceName.setType(SimpleType.String);
		propDeviceName.setOptional(true);
		propDeviceName.setDoc("Device name");
		addProperty(propDeviceName);

		propDeviceSubModelName = new Property("propDeviceSubModelName");
		propDeviceSubModelName.setType(SimpleType.String);
		propDeviceSubModelName.setOptional(true);
		propDeviceSubModelName.setDoc("Device sub-modelname");
		addProperty(propDeviceSubModelName);

		propDeviceAliasName = new Property("propDeviceAliasName");
		propDeviceAliasName.setType(SimpleType.String);
		propDeviceAliasName.setOptional(true);
		propDeviceAliasName.setDoc("Device alias name");
		addProperty(propDeviceAliasName);

		propDeviceFirmwareVersion = new Property("propDeviceFirmwareVersion");
		propDeviceFirmwareVersion.setType(SimpleType.String);
		propDeviceFirmwareVersion.setOptional(true);
		propDeviceFirmwareVersion.setDoc("Device firmware version");
		addProperty(propDeviceFirmwareVersion);

		propHardwareVersion = new Property("propHardwareVersion");
		propHardwareVersion.setType(SimpleType.String);
		propHardwareVersion.setOptional(true);
		propHardwareVersion.setDoc("Device hardware version");
		addProperty(propHardwareVersion);

		propOsVersion = new Property("propOsVersion");
		propOsVersion.setType(SimpleType.String);
		propOsVersion.setOptional(true);
		propOsVersion.setDoc("Version of the operation system (defined by manufacturer)");
		addProperty(propOsVersion);

		propProtocol = new Property("propProtocol");
		propProtocol.setType(SimpleType.String);
		propProtocol.setOptional(true);
		propProtocol.setDoc("A comma separated list of MIME types for all supported communication protocol(s) of the device. Example: “application/x-alljoin;version=1.0,application/x-echonet-lite;version=1.0” indicates the device supports both AllJoyn v1.0 and Echonet Lite v1.0.");
		addProperty(propProtocol);

		propCountry = new Property("propCountry");
		propCountry.setType(SimpleType.String);
		propCountry.setOptional(true);
		propCountry.setDoc("Country code of the device");
		addProperty(propCountry);

		propLocation = new Property("propLocation");
		propLocation.setType(SimpleType.String);
		propLocation.setOptional(true);
		propLocation.setDoc("Location where the device is installed. It may be configured via the user interface provided by  the ‘presentationURL’ property or any other means.");
		addProperty(propLocation);

		propSystemTime = new Property("propSystemTime");
		propSystemTime.setType(SimpleType.Datetime);
		propSystemTime.setOptional(true);
		propSystemTime.setDoc("Reference time for the device");
		addProperty(propSystemTime);

		propManufacturerDetailsLink = new Property("propManufacturerDetailsLink");
		propManufacturerDetailsLink.setType(SimpleType.String);
		propManufacturerDetailsLink.setOptional(true);
		propManufacturerDetailsLink.setDoc("URL to manufacturer’s website");
		addProperty(propManufacturerDetailsLink);

		propDateOfManufacture = new Property("propDateOfManufacture");
		propDateOfManufacture.setType(SimpleType.Datetime);
		propDateOfManufacture.setOptional(true);
		propDateOfManufacture.setDoc("Manufacturing date of device");
		addProperty(propDateOfManufacture);

		propSupportURL = new Property("propSupportURL");
		propSupportURL.setType(SimpleType.String);
		propSupportURL.setOptional(true);
		propSupportURL.setDoc("URL that points to product support information of the device");
		addProperty(propSupportURL);

		propPresentationURL = new Property("propPresentationURL");
		propPresentationURL.setType(SimpleType.String);
		propPresentationURL.setOptional(true);
		propPresentationURL.setDoc("To quote UPnP: “the control point can retrieve a page from this URL, load the page into a web browser, and depending on the capabilities of the page, allow a user to control the device and/or view device status. The degree to which each of these can be accomplished depends on the specific capabilities of the presentation page and device.”");
		addProperty(propPresentationURL);
	}

	protected void setDeviceType(DeviceType type) {
		this.type = type;
	}

	public DeviceType getDeviceType() {
		return type;
	}

	public String getSerialNumber() {
		return propDeviceSerialNum.getValue();
	}

	public String getDeviceManufacturer() {
		String s = propDeviceManufacturer.getValue();
		return (s == null) ? "Undefined" : s;
	}

	public void setDeviceManufacturer(final String s) {
		propDeviceManufacturer.setValue(s);
	}

	public String getDeviceModelName() {
		String s = propDeviceModelName.getValue();
		return (s == null) ? "Undefined" : s;
	}

	public void setDeviceModelName(String s) {
		propDeviceModelName.setValue(s);
	}
	
	public String getDeviceName() {
		return propDeviceName.getValue();
	}
	
	public void setDeviceName(String s) {
		propDeviceName.setValue(s);
	}
	
	public String getDeviceSubModelName() {
		return propDeviceSubModelName.getValue();
	}
	
	public void setDeviceSubModelName(String s) {
		propDeviceSubModelName.setValue(s);
	}
	
	public String getDeviceAliasName() {
		return propDeviceAliasName.getValue();
	}
	
	public void setDeviceAliasName(String s) {
		propDeviceAliasName.setValue(s);
	}
	
	public String getDeviceFirmwareVersion() {
		return propDeviceFirmwareVersion.getValue();
	}
	
	public void setDeviceFirmwareVersion(String s) {
		propDeviceFirmwareVersion.setValue(s);
	}
	
	public String getHardwareVersion() {
		return propHardwareVersion.getValue();
	}
	
	public void setHardwareVersion(String s) {
		propHardwareVersion.setValue(s);
	}
	
	public String getOsVersion() {
		return propOsVersion.getValue();
	}
	
	public void setOsVersion(String s) {
		propOsVersion.setValue(s);
	}
	
	public String getProtocol() {
		return propProtocol.getValue();
	}
	
	public void setProtocol(String s) {
		propProtocol.setValue(s);
	}
	
	public String getCountry() {
		return propCountry.getValue();
	}
	
	public void setCountry(String s) {
		propCountry.setValue(s);
	}
	
	public String getLocation() {
		return propLocation.getValue();
	}
	
	public void setLocation(String s) {
		propLocation.setValue(s);
	}
	
	public Date getSystemTime() throws PropertyException {
		try {
			String s = propSystemTime.getValue();
			return (s == null) ? null : new Date(Long.parseLong(s));
		} catch (NumberFormatException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setSystemTime(Date s) {
		propSystemTime.setValue((s == null) ? null : Long.toString(s.getTime()));
	}
	
	public URL getManufacturerDetailsLink() throws PropertyException {
		try {
			String s = propManufacturerDetailsLink.getValue();
			return (s == null) ? null : new URL(s);
		} catch (MalformedURLException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setManufacturerDetailsLink(URL s) {
		propManufacturerDetailsLink.setValue((s == null) ? null : s.toString());
	}
	
	public Date getDateOfManufacture() throws PropertyException {
		try {
			String s = propDateOfManufacture.getValue();
			return (s == null) ? null : new Date(Long.parseLong(s));
		} catch (NumberFormatException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setDateOfManufacture(Date s) {
		propDateOfManufacture.setValue((s == null) ? null : Long.toString(s.getTime()));
	}
	
	public URL getSupportURL() throws PropertyException {
		try {
			String s = propSupportURL.getValue();
			return (s == null) ? null : new URL(s);
		} catch (MalformedURLException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setSupportURL(URL s) {
		propSupportURL.setValue((s == null) ? null : s.toString());
	}
	
	public URL getPresentationURL() throws PropertyException {
		try {
			String s = propPresentationURL.getValue();
			return (s == null) ? null : new URL(s);
		} catch (MalformedURLException e) {
			throw new PropertyException("Implementation Error");
		}
	}
	
	public void setPresentationURL(URL s) {
		propPresentationURL.setValue((s == null) ? null : s.toString());
	}
	
}
