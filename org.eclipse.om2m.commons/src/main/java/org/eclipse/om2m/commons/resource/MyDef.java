package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="myDef"/*, namespace="http://www.onem2m.org/xml/protocols/homedomain"*/)
@XmlType(name="myDef")
public class MyDef /* extends FlexContainer */ {
	
	public MyDef() {
//		setLongName("myDef");
//		setShortName("myDef");
	}

}
