/*
ModuleClass : Brightness



This ModuleClass describes the brightness of a light, e.g. from  a lamp. Brightness is scaled as a percentage. A lamp or a monitor  can be adjusted to a level of light between very dim (0% is the  minimum brightness) and very bright (100% is the maximum  brightness).

Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;


@XmlRootElement(name = BrightnessFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = BrightnessFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class BrightnessFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "brightness";
	public static final String SHORT_NAME = "brigs";
	
	public BrightnessFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
}