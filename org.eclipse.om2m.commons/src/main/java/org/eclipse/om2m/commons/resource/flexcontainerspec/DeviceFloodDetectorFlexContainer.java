/*
Device : DeviceFloodDetector



A door is a door.

Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;


@XmlRootElement(name = DeviceFloodDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceFloodDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceFloodDetectorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceFloodDetector";
	public static final String SHORT_NAME = "deFDr";
	
	public DeviceFloodDetectorFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getWaterSensor();
	}
	
	@XmlElement(name="watSr", required=true, type=WaterSensorFlexContainer.class)
	private WaterSensorFlexContainer waterSensor;
	
	
	public void setWaterSensor(WaterSensorFlexContainer waterSensor) {
		this.waterSensor = waterSensor;
		getFlexContainerOrContainerOrSubscription().add(waterSensor);
	}
	
	public WaterSensorFlexContainer getWaterSensor() {
		this.waterSensor = (WaterSensorFlexContainer) getResourceByName(WaterSensorFlexContainer.SHORT_NAME);
		return waterSensor;
	}
	
}