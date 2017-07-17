/*
Device : DeviceSmokeDetector



A SmokeDetector is a device that triggers alarm in case of fire detection.

Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;


@XmlRootElement(name = DeviceSmokeDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceSmokeDetectorFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceSmokeDetectorFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceSmokeDetector";
	public static final String SHORT_NAME = "deSDr";
	
	public DeviceSmokeDetectorFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getSmokeSensor();
	}
	
	@XmlElement(name="smoSr", required=true, type=SmokeSensorFlexContainer.class)
	private SmokeSensorFlexContainer smokeSensor;
	
	
	public void setSmokeSensor(SmokeSensorFlexContainer smokeSensor) {
		this.smokeSensor = smokeSensor;
		getFlexContainerOrContainerOrSubscription().add(smokeSensor);
	}
	
	public SmokeSensorFlexContainer getSmokeSensor() {
		this.smokeSensor = (SmokeSensorFlexContainer) getResourceByName(SmokeSensorFlexContainer.SHORT_NAME);
		return smokeSensor;
	}
	
}