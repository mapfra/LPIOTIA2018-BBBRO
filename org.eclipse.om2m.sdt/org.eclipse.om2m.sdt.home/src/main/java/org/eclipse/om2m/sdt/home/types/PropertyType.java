/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.types;

import org.eclipse.om2m.sdt.Identifiers;

public enum PropertyType implements Identifiers {
	
	absoluteEnergyConsumption("absoluteEnergyConsumption", "abECn"),
	country("propCountry", "proCy"),
	dateOfManufacture("propDateOfManufacture", "pDOMe"),
	deviceAliasName("propDeviceAliasName", "pDANe"),
	deviceFirmwareVersion("propDeviceFirmwareVersion", "pDFVn"),
	deviceManufacturer("propDeviceManufacturer", "prDMr"),
	deviceModelName("propDeviceModelName", "pDMNe"),
	deviceName("propDeviceName", "prDNe"),
	deviceSerialNum("propDeviceSerialNum", "pDSNm"),
	deviceSubModelName("propDeviceSubModelName", "pDSMN"),
	deviceType("propDeviceType", "prDTe"),
	generationSource("generationSource", "genSe"),
	hardwareVersion("propHardwareVersion", "prHVn"),
	location("propLocation", "proLn"),
	manufacturerDetailsLink("propManufacturerDetailsLink", "pMDLk"),
	manufacturerName("manufacturerName", "manNe"),
	measuringScope("measuringScope", "meaSe"),
	osVersion("propOsVersion", "prOVn"),
	presentationURL("propPresentationURL", "pPURL"),
	protocol("propProtocol", "proPl"),
	supportURL("propSupportURL", "pSURL"),
	systemTime("propSystemTime", "prSTe"),
	
	chargingCapacity("chargingCapacity", "chaCy"),
	dischargingCapacity("dischargingCapacity", "disCy"),
	electricEnergy("electricEnergy", "eleEy"),
	material("material", "matel"),
	voltage("voltage", "volte"),
	
	openOnly("openOnly", "opeOy"),
	cloud("cloud", "cloud"),
	owner("owner", "owner"),

	undefinedVendorExt("undefinedVendorExt", "undef");
	
	private final String longDefinitionName;
	private final String shortDefinitionName;
	
	PropertyType(String longDef, String shortDef) {
		longDefinitionName = longDef;
		shortDefinitionName = shortDef;
	}

    /**
	 * @return the longDefinitionName
	 */
	@Override
	public String getLongName() {
		return longDefinitionName;
	}

	/**
	 * @return the shortDefinitionName
	 */
	@Override
	public String getShortName() {
		return shortDefinitionName;
	}

	@Override
	public String getDefinition() {
		return null;
	}

    public static PropertyType fromLongName(String def) {
        for (PropertyType c: PropertyType.values()) {
            if (c.longDefinitionName.equals(def)) {
                return c;
            }
        }
		return null;
    }

    public static PropertyType fromShortName(String def) {
        for (PropertyType c: PropertyType.values()) {
            if (c.shortDefinitionName.equals(def)) {
                return c;
            }
        }
		return null;
    }

}
