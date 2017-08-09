/*
ModuleClass : ColourSaturation



This ModuleClass describes a colour saturation value. The value  is an integer. A colourSaturation has a range of [0,100]. A  colourSaturation value of 0 means producing black and white images.  A colourSaturation value of 50 means producing device specific  normal colour images. A colourSaturation value of 100 means  producing device very colourfull images.

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


@XmlRootElement(name = ColourSaturationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ColourSaturationFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class ColourSaturationFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "colourSaturation";
	public static final String SHORT_NAME = "colSn";
	
	public ColourSaturationFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + ColourSaturationFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
}