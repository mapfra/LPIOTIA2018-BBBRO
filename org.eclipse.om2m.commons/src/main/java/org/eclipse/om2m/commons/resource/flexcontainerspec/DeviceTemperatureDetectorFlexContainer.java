/*
Device : DeviceTemperatureDetector



A SwitchButton is a device that provides button.

Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;


@XmlRootElement(name = DeviceTemperatureDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceTemperatureDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceTemperatureDetectorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceTemperatureDetector";
	public static final String SHORT_NAME = "deTDr";
	
	public DeviceTemperatureDetectorFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getAlarmSensor();
		getTemperature();
	}
	
	@XmlElement(name="alSer", required=true, type=AlarmSensorFlexContainer.class)
	private AlarmSensorFlexContainer alarmSensor;
	
	
	public void setAlarmSensor(AlarmSensorFlexContainer alarmSensor) {
		this.alarmSensor = alarmSensor;
		getFlexContainerOrContainerOrSubscription().add(alarmSensor);
	}
	
	public AlarmSensorFlexContainer getAlarmSensor() {
		this.alarmSensor = (AlarmSensorFlexContainer) getResourceByName(AlarmSensorFlexContainer.SHORT_NAME);
		return alarmSensor;
	}
	
	@XmlElement(name="tempe", required=true, type=TemperatureFlexContainer.class)
	private TemperatureFlexContainer temperature;
	
	
	public void setTemperature(TemperatureFlexContainer temperature) {
		this.temperature = temperature;
		getFlexContainerOrContainerOrSubscription().add(temperature);
	}
	
	public TemperatureFlexContainer getTemperature() {
		this.temperature = (TemperatureFlexContainer) getResourceByName(TemperatureFlexContainer.SHORT_NAME);
		return temperature;
	}
	
}