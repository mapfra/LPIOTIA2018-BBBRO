/*
ModuleClass : HotWaterSupplyAnnc



This ModuleClass provides the information about the status of  supplying hot water into tanks or bath tubes.

Created: 2017-09-26 14:17:12
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = HotWaterSupplyFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = HotWaterSupplyFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class HotWaterSupplyFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "hotWaterSupplyAnnc";
	public static final String SHORT_NAME = "hoWSyAnnc";
	
	public HotWaterSupplyFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + HotWaterSupplyFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}