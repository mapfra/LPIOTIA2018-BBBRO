/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : GeoLocation

This ModuleClass provides the capability to get or set geo-location information.

Created: 2018-06-29 17:19:52
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = GeoLocationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = GeoLocationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class GeoLocationFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "geoLocation";
	public static final String SHORT_NAME = "geoLn";
		
	public GeoLocationFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + GeoLocationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute altitude = new CustomAttribute();
		altitude.setLongName("altitude");
		altitude.setShortName("altie");
		altitude.setType("xs:float");
		getCustomAttributes().add(altitude);
		CustomAttribute verticalAccuracy = new CustomAttribute();
		verticalAccuracy.setLongName("verticalAccuracy");
		verticalAccuracy.setShortName("verAy");
		verticalAccuracy.setType("xs:float");
		getCustomAttributes().add(verticalAccuracy);
		CustomAttribute heading = new CustomAttribute();
		heading.setLongName("heading");
		heading.setShortName("headg");
		heading.setType("xs:float");
		getCustomAttributes().add(heading);
		CustomAttribute targetLongitude = new CustomAttribute();
		targetLongitude.setLongName("targetLongitude");
		targetLongitude.setShortName("tarL0");
		targetLongitude.setType("xs:float");
		getCustomAttributes().add(targetLongitude);
		CustomAttribute headingAccuracy = new CustomAttribute();
		headingAccuracy.setLongName("headingAccuracy");
		headingAccuracy.setShortName("heaAy");
		headingAccuracy.setType("xs:float");
		getCustomAttributes().add(headingAccuracy);
		CustomAttribute latitude = new CustomAttribute();
		latitude.setLongName("latitude");
		latitude.setShortName("latie");
		latitude.setType("xs:float");
		getCustomAttributes().add(latitude);
		CustomAttribute horizontalAccuracy = new CustomAttribute();
		horizontalAccuracy.setLongName("horizontalAccuracy");
		horizontalAccuracy.setShortName("horAy");
		horizontalAccuracy.setType("xs:float");
		getCustomAttributes().add(horizontalAccuracy);
		CustomAttribute targetLatitude = new CustomAttribute();
		targetLatitude.setLongName("targetLatitude");
		targetLatitude.setShortName("tarLe");
		targetLatitude.setType("xs:float");
		getCustomAttributes().add(targetLatitude);
		CustomAttribute targetAltitude = new CustomAttribute();
		targetAltitude.setLongName("targetAltitude");
		targetAltitude.setShortName("tarAe");
		targetAltitude.setType("xs:float");
		getCustomAttributes().add(targetAltitude);
		CustomAttribute longitude = new CustomAttribute();
		longitude.setLongName("longitude");
		longitude.setShortName("longe");
		longitude.setType("xs:float");
		getCustomAttributes().add(longitude);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
