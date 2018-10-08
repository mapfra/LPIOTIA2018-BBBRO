/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

ModuleClass : AirQualitySensor

ThThis ModuleClass provides capabilities for a monitoring sensor that measures the air quality.

Created: 2018-06-29 17:19:51
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.CustomAttribute;

@XmlRootElement(name = AirQualitySensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AirQualitySensorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class AirQualitySensorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "airQualitySensor";
	public static final String SHORT_NAME = "aiQSr";
		
	public AirQualitySensorFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + AirQualitySensorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
		CustomAttribute sensorOdor = new CustomAttribute();
		sensorOdor.setLongName("sensorOdor");
		sensorOdor.setShortName("senOr");
		sensorOdor.setType("xs:integer");
		getCustomAttributes().add(sensorOdor);
		CustomAttribute sensorPM10 = new CustomAttribute();
		sensorPM10.setLongName("sensorPM10");
		sensorPM10.setShortName("sePM0");
		sensorPM10.setType("xs:integer");
		getCustomAttributes().add(sensorPM10);
		CustomAttribute sensorHumidity = new CustomAttribute();
		sensorHumidity.setLongName("sensorHumidity");
		sensorHumidity.setShortName("senHy");
		sensorHumidity.setType("xs:integer");
		getCustomAttributes().add(sensorHumidity);
		CustomAttribute co2 = new CustomAttribute();
		co2.setLongName("co2");
		co2.setShortName("co2");
		co2.setType("xs:integer");
		getCustomAttributes().add(co2);
		CustomAttribute sensorPM1 = new CustomAttribute();
		sensorPM1.setLongName("sensorPM1");
		sensorPM1.setShortName("sePM1");
		sensorPM1.setType("xs:integer");
		getCustomAttributes().add(sensorPM1);
		CustomAttribute sensorPM2 = new CustomAttribute();
		sensorPM2.setLongName("sensorPM2");
		sensorPM2.setShortName("sePM2");
		sensorPM2.setType("xs:integer");
		getCustomAttributes().add(sensorPM2);
		CustomAttribute voc = new CustomAttribute();
		voc.setLongName("voc");
		voc.setShortName("voc");
		voc.setType("xs:integer");
		getCustomAttributes().add(voc);
		CustomAttribute co = new CustomAttribute();
		co.setLongName("co");
		co.setShortName("co");
		co.setType("xs:integer");
		getCustomAttributes().add(co);
		CustomAttribute ch2o = new CustomAttribute();
		ch2o.setLongName("ch2o");
		ch2o.setShortName("ch2o");
		ch2o.setType("xs:integer");
		getCustomAttributes().add(ch2o);
		CustomAttribute monitoringEnabled = new CustomAttribute();
		monitoringEnabled.setLongName("monitoringEnabled");
		monitoringEnabled.setShortName("monEd");
		monitoringEnabled.setType("xs:boolean");
		getCustomAttributes().add(monitoringEnabled);
	}

		
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}
