package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.FlexContainer;

@XmlRootElement(name=ModuleExtendedCarbonDioxideSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name=ModuleExtendedCarbonDioxideSensorFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain")
public class ModuleExtendedCarbonDioxideSensorFlexContainer extends FlexContainer {
	
	public static final String LONG_NAME = "energyOverloadCircuitBreaker";
	public static final String SHORT_NAME = "eOCBr";
	
	
	public ModuleExtendedCarbonDioxideSensorFlexContainer() {
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}

}
