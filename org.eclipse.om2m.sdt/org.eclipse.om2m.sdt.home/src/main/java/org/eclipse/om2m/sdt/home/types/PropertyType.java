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
	country("country", "couny"),
	dateOfManufacture("dateOfManufacture", "daOMe"),
	deviceAliasName("deviceAliasName", "deANe"),
	deviceFirmwareVersion("deviceFirmwareVersion", "deFVn"),
	deviceManufacturer("deviceManufacturer", "devMr"),
	deviceModelName("deviceModelName", "deMNe"),
	deviceName("deviceName", "devNe"),
	deviceSerialNum("deviceSerialNum", "deSNm"),
	deviceSubModelName("deviceSubModelName", "dSMNe"),
	deviceType("deviceType", "devTe"),
	generationSource("generationSource", "genSe"),
	hardwareVersion("hardwareVersion", "harVn"),
	location("location", "locan"),
	manufacturerDetailsLink("manufacturerDetailsLink", "maDLk"),
	manufacturerName("manufacturerName", "manNe"),
	measuringScope("measuringScope", "meaSe"),
	osVersion("osVersion", "oseVn"),
	presentationURL("presentationURL", "prURL"),
	protocol("protocol", "protl"),
	supportURL("supportURL", "suURL"),
	systemTime("systemTime", "sysTe"),
	
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
