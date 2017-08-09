/*
ModuleClass : PowerSaveAnnc



This ModuleClass provides capabilities to enable power saving  mode and monitor the current status.

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


@XmlRootElement(name = PowerSaveFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PowerSaveFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class PowerSaveFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "powerSaveAnnc";
	public static final String SHORT_NAME = "powSeAnnc";
	
	public PowerSaveFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + PowerSaveFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
}