/*
ModuleClass : RelativeHumidity



This ModuleClass provides the capability for the device to  report the humidity based on a specified rule that is vendor  discretionary.

Created: 2017-08-09 15:38:05
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = RelativeHumidityFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = RelativeHumidityFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class RelativeHumidityFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "relativeHumidity";
	public static final String SHORT_NAME = "relHy";
	
	public RelativeHumidityFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + RelativeHumidityFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
}