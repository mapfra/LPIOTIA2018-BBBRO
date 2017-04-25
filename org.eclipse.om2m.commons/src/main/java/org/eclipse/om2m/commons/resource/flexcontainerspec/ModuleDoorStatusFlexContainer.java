package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.resource.FlexContainer;

@XmlRootElement(name=ModuleDoorStatusFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name=ModuleDoorStatusFlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols/homedomain")
public class ModuleDoorStatusFlexContainer extends FlexContainer {
	
	public static final String LONG_NAME = "doorStatus";
	public static final String SHORT_NAME = "dooSe";
	
	
	public ModuleDoorStatusFlexContainer() {
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}

}
