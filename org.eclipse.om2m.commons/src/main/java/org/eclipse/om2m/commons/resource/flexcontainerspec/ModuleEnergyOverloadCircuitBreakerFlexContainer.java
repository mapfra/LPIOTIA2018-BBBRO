package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;

@XmlRootElement(name=ModuleEnergyOverloadCircuitBreakerFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name=ModuleEnergyOverloadCircuitBreakerFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain")
public class ModuleEnergyOverloadCircuitBreakerFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "extendedCarbonDioxideSensor";
	public static final String SHORT_NAME = "eCDSr";
	
	
	public ModuleEnergyOverloadCircuitBreakerFlexContainer() {
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}

}
