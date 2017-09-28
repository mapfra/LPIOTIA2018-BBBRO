/*
********************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceTemperatureDetectorAnnc

A SwitchButton is a device that provides button.

Created: 2017-09-28 17:26:40
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = DeviceTemperatureDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceTemperatureDetectorFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceTemperatureDetectorFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceTemperatureDetectorAnnc";
	public static final String SHORT_NAME = "deTDrAnnc";
	
	public DeviceTemperatureDetectorFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceTemperatureDetectorFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getAlarmSensor();
		getAlarmSensorAnnc();
		getTemperature();
		getTemperatureAnnc();
	}
	
	public void finalizeDeserialization() {
		if (this.alarmSensor != null) {
			setAlarmSensor(this.alarmSensor);
		}
		if (this.alarmSensorAnnc != null) {
			setAlarmSensorAnnc(this.alarmSensorAnnc);
			}
		
		if (this.temperature != null) {
			setTemperature(this.temperature);
		}
		if (this.temperatureAnnc != null) {
			setTemperatureAnnc(this.temperatureAnnc);
			}
		
	}
	
	@XmlElement(name="alSer", required=true, type=AlarmSensorFlexContainerAnnc.class)
	private AlarmSensorFlexContainer alarmSensor;
	
	
	public void setAlarmSensor(AlarmSensorFlexContainer alarmSensor) {
		this.alarmSensor = alarmSensor;
		getFlexContainerOrContainerOrSubscription().add(alarmSensor);
	}
	
	public AlarmSensorFlexContainer getAlarmSensor() {
		this.alarmSensor = (AlarmSensorFlexContainer) getResourceByName(AlarmSensorFlexContainer.SHORT_NAME);
		return alarmSensor;
	}
	
	@XmlElement(name="alSerAnnc", required=true, type=AlarmSensorFlexContainerAnnc.class)
	private AlarmSensorFlexContainerAnnc alarmSensorAnnc;
	
	
	public void setAlarmSensorAnnc(AlarmSensorFlexContainerAnnc alarmSensorAnnc) {
		this.alarmSensorAnnc = alarmSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(alarmSensorAnnc);
	}
	
	public AlarmSensorFlexContainerAnnc getAlarmSensorAnnc() {
		this.alarmSensorAnnc = (AlarmSensorFlexContainerAnnc) getResourceByName(AlarmSensorFlexContainerAnnc.SHORT_NAME);
		return alarmSensorAnnc;
	}
	
	@XmlElement(name="tempe", required=true, type=TemperatureFlexContainerAnnc.class)
	private TemperatureFlexContainer temperature;
	
	
	public void setTemperature(TemperatureFlexContainer temperature) {
		this.temperature = temperature;
		getFlexContainerOrContainerOrSubscription().add(temperature);
	}
	
	public TemperatureFlexContainer getTemperature() {
		this.temperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return temperature;
	}
	
	@XmlElement(name="tempeAnnc", required=true, type=TemperatureFlexContainerAnnc.class)
	private TemperatureFlexContainerAnnc temperatureAnnc;
	
	
	public void setTemperatureAnnc(TemperatureFlexContainerAnnc temperatureAnnc) {
		this.temperatureAnnc = temperatureAnnc;
		getFlexContainerOrContainerOrSubscription().add(temperatureAnnc);
	}
	
	public TemperatureFlexContainerAnnc getTemperatureAnnc() {
		this.temperatureAnnc = (TemperatureFlexContainerAnnc) getResourceByName(TemperatureFlexContainerAnnc.SHORT_NAME);
		return temperatureAnnc;
	}
	
}