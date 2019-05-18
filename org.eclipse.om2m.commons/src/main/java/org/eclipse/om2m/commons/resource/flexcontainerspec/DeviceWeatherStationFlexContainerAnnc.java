/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

Device : DeviceWeatherStationAnnc



Created: 2018-07-04 10:25:10
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;

@XmlRootElement(name = DeviceWeatherStationFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWeatherStationFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWeatherStationFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "deviceWeatherStationAnnc";
	public static final String SHORT_NAME = "deWSnAnnc";
	
	public DeviceWeatherStationFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.device." + DeviceWeatherStationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
    }
	
	public void finalizeSerialization() {
		getAirQualitySensor();
		getAirQualitySensorAnnc();
		getAcousticSensor();
		getAcousticSensorAnnc();
		getAnemometer();
		getAnemometerAnnc();
		getBarometer();
		getBarometerAnnc();
		getUvSensor();
		getUvSensorAnnc();
    }
	
	public void finalizeDeserialization() {
		if (this.airQualitySensor != null) {
			setAirQualitySensor(this.airQualitySensor);
		}
		if (this.airQualitySensorAnnc != null) {
			setAirQualitySensorAnnc(this.airQualitySensorAnnc);
		}
		if (this.acousticSensor != null) {
			setAcousticSensor(this.acousticSensor);
		}
		if (this.acousticSensorAnnc != null) {
			setAcousticSensorAnnc(this.acousticSensorAnnc);
		}
		if (this.anemometer != null) {
			setAnemometer(this.anemometer);
		}
		if (this.anemometerAnnc != null) {
			setAnemometerAnnc(this.anemometerAnnc);
		}
		if (this.barometer != null) {
			setBarometer(this.barometer);
		}
		if (this.barometerAnnc != null) {
			setBarometerAnnc(this.barometerAnnc);
		}
		if (this.uvSensor != null) {
			setUvSensor(this.uvSensor);
		}
		if (this.uvSensorAnnc != null) {
			setUvSensorAnnc(this.uvSensorAnnc);
		}
	}

	@XmlElement(name=AirQualitySensorFlexContainer.SHORT_NAME, required=true, type=AirQualitySensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirQualitySensorFlexContainer airQualitySensor;
		
	public void setAirQualitySensor(AirQualitySensorFlexContainer airQualitySensor) {
		this.airQualitySensor = airQualitySensor;
		getFlexContainerOrContainerOrSubscription().add(airQualitySensor);
	}
	
	public AirQualitySensorFlexContainer getAirQualitySensor() {
		this.airQualitySensor = (AirQualitySensorFlexContainer) getResourceByName(AirQualitySensorFlexContainer.SHORT_NAME);
		return airQualitySensor;
	}
	
	@XmlElement(name=AirQualitySensorFlexContainerAnnc.SHORT_NAME, required=true, type=AirQualitySensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AirQualitySensorFlexContainerAnnc airQualitySensorAnnc;
		
	public void setAirQualitySensorAnnc(AirQualitySensorFlexContainerAnnc airQualitySensorAnnc) {
		this.airQualitySensorAnnc = airQualitySensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(airQualitySensorAnnc);
	}
	
	public AirQualitySensorFlexContainerAnnc getAirQualitySensorAnnc() {
		this.airQualitySensorAnnc = (AirQualitySensorFlexContainerAnnc) getResourceByName(AirQualitySensorFlexContainerAnnc.SHORT_NAME);
		return airQualitySensorAnnc;
	}
	
	@XmlElement(name=AcousticSensorFlexContainer.SHORT_NAME, required=true, type=AcousticSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AcousticSensorFlexContainer acousticSensor;
		
	public void setAcousticSensor(AcousticSensorFlexContainer acousticSensor) {
		this.acousticSensor = acousticSensor;
		getFlexContainerOrContainerOrSubscription().add(acousticSensor);
	}
	
	public AcousticSensorFlexContainer getAcousticSensor() {
		this.acousticSensor = (AcousticSensorFlexContainer) getResourceByName(AcousticSensorFlexContainer.SHORT_NAME);
		return acousticSensor;
	}
	
	@XmlElement(name=AcousticSensorFlexContainerAnnc.SHORT_NAME, required=true, type=AcousticSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AcousticSensorFlexContainerAnnc acousticSensorAnnc;
		
	public void setAcousticSensorAnnc(AcousticSensorFlexContainerAnnc acousticSensorAnnc) {
		this.acousticSensorAnnc = acousticSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(acousticSensorAnnc);
	}
	
	public AcousticSensorFlexContainerAnnc getAcousticSensorAnnc() {
		this.acousticSensorAnnc = (AcousticSensorFlexContainerAnnc) getResourceByName(AcousticSensorFlexContainerAnnc.SHORT_NAME);
		return acousticSensorAnnc;
	}
	
	@XmlElement(name=AnemometerFlexContainer.SHORT_NAME, required=true, type=AnemometerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AnemometerFlexContainer anemometer;
		
	public void setAnemometer(AnemometerFlexContainer anemometer) {
		this.anemometer = anemometer;
		getFlexContainerOrContainerOrSubscription().add(anemometer);
	}
	
	public AnemometerFlexContainer getAnemometer() {
		this.anemometer = (AnemometerFlexContainer) getResourceByName(AnemometerFlexContainer.SHORT_NAME);
		return anemometer;
	}
	
	@XmlElement(name=AnemometerFlexContainerAnnc.SHORT_NAME, required=true, type=AnemometerFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private AnemometerFlexContainerAnnc anemometerAnnc;
		
	public void setAnemometerAnnc(AnemometerFlexContainerAnnc anemometerAnnc) {
		this.anemometerAnnc = anemometerAnnc;
		getFlexContainerOrContainerOrSubscription().add(anemometerAnnc);
	}
	
	public AnemometerFlexContainerAnnc getAnemometerAnnc() {
		this.anemometerAnnc = (AnemometerFlexContainerAnnc) getResourceByName(AnemometerFlexContainerAnnc.SHORT_NAME);
		return anemometerAnnc;
	}
	
	@XmlElement(name=BarometerFlexContainer.SHORT_NAME, required=true, type=BarometerFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BarometerFlexContainer barometer;
		
	public void setBarometer(BarometerFlexContainer barometer) {
		this.barometer = barometer;
		getFlexContainerOrContainerOrSubscription().add(barometer);
	}
	
	public BarometerFlexContainer getBarometer() {
		this.barometer = (BarometerFlexContainer) getResourceByName(BarometerFlexContainer.SHORT_NAME);
		return barometer;
	}
	
	@XmlElement(name=BarometerFlexContainerAnnc.SHORT_NAME, required=true, type=BarometerFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private BarometerFlexContainerAnnc barometerAnnc;
		
	public void setBarometerAnnc(BarometerFlexContainerAnnc barometerAnnc) {
		this.barometerAnnc = barometerAnnc;
		getFlexContainerOrContainerOrSubscription().add(barometerAnnc);
	}
	
	public BarometerFlexContainerAnnc getBarometerAnnc() {
		this.barometerAnnc = (BarometerFlexContainerAnnc) getResourceByName(BarometerFlexContainerAnnc.SHORT_NAME);
		return barometerAnnc;
	}
	
	@XmlElement(name=UvSensorFlexContainer.SHORT_NAME, required=true, type=UvSensorFlexContainer.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private UvSensorFlexContainer uvSensor;
		
	public void setUvSensor(UvSensorFlexContainer uvSensor) {
		this.uvSensor = uvSensor;
		getFlexContainerOrContainerOrSubscription().add(uvSensor);
	}
	
	public UvSensorFlexContainer getUvSensor() {
		this.uvSensor = (UvSensorFlexContainer) getResourceByName(UvSensorFlexContainer.SHORT_NAME);
		return uvSensor;
	}
	
	@XmlElement(name=UvSensorFlexContainerAnnc.SHORT_NAME, required=true, type=UvSensorFlexContainerAnnc.class, namespace="http://www.onem2m.org/xml/protocols/homedomain")
	private UvSensorFlexContainerAnnc uvSensorAnnc;
		
	public void setUvSensorAnnc(UvSensorFlexContainerAnnc uvSensorAnnc) {
		this.uvSensorAnnc = uvSensorAnnc;
		getFlexContainerOrContainerOrSubscription().add(uvSensorAnnc);
	}
	
	public UvSensorFlexContainerAnnc getUvSensorAnnc() {
		this.uvSensorAnnc = (UvSensorFlexContainerAnnc) getResourceByName(UvSensorFlexContainerAnnc.SHORT_NAME);
		return uvSensorAnnc;
	}
	
}
