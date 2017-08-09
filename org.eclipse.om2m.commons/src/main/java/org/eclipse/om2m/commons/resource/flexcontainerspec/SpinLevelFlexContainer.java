/*
ModuleClass : SpinLevel



This ModuleClass provides capabilities to control and monitor  the level of spin. It is intended to be part of objects which use  spinning function such as a washing machine and a dryer.

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


@XmlRootElement(name = SpinLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = SpinLevelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class SpinLevelFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "spinLevel";
	public static final String SHORT_NAME = "spiLl";
	
	public SpinLevelFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + SpinLevelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
}