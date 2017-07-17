/*
Device : DeviceWaterValve



A WaterValve is a device that controls liquid flux.

Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;


@XmlRootElement(name = DeviceWaterValveFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DeviceWaterValveFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DeviceWaterValveFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "deviceWaterValve";
	public static final String SHORT_NAME = "deWVe";
	
	public DeviceWaterValveFlexContainer () {
		setContainerDefinition("org.onem2m.home.device." + LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getWaterLevel();
	}
	
	@XmlElement(name="watLl", required=true, type=LiquidLevelFlexContainer.class)
	private LiquidLevelFlexContainer waterLevel;
	
	
	public void setWaterLevel(LiquidLevelFlexContainer waterLevel) {
		this.waterLevel = waterLevel;
		getFlexContainerOrContainerOrSubscription().add(waterLevel);
	}
	
	public LiquidLevelFlexContainer getWaterLevel() {
		this.waterLevel = (LiquidLevelFlexContainer) getResourceByName(LiquidLevelFlexContainer.SHORT_NAME);
		return waterLevel;
	}
	
}