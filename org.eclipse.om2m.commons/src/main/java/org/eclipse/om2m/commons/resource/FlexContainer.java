package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name=FlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name=FlexContainer.SHORT_NAME, namespace="http://www.onem2m.org/xml/protocols")
public class FlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "flexContainer";
	public static final String SHORT_NAME = "fcnt";
	
	
	public FlexContainer() {
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}

}
