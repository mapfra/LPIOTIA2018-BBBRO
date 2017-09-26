/*
ModuleClass : EnergyGeneration



This ModuleClass provides the information about generation data  on electric generator devices such as a photo voltaic power system,  fuel cells, or microgeneration.

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


@XmlRootElement(name = EnergyGenerationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = EnergyGenerationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class EnergyGenerationFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "energyGeneration";
	public static final String SHORT_NAME = "eneGn";
	
	public EnergyGenerationFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + EnergyGenerationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}