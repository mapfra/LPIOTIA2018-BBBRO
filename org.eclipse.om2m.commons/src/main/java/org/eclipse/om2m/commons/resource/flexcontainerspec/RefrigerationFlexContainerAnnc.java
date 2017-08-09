/*
ModuleClass : RefrigerationAnnc



This ModuleClass describes a refrigeration function. This is not  a Refrigerator device. The filter state is a read-only value  providing the percentage life time remaining for the water filter.  RapidFreeze is a boolean that controls the rapid freeze capability  if present. RapidCool is a boolean that controls the rapid cool  capability if present. Defrost is a boolean that controls the  defrost cycle if present.

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


@XmlRootElement(name = RefrigerationFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = RefrigerationFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class RefrigerationFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "refrigerationAnnc";
	public static final String SHORT_NAME = "refrnAnnc";
	
	public RefrigerationFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + RefrigerationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
}