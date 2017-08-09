/*
Action : upVolume



Increase volume by the amount of the stepValue up to the  maxValue.

Created: 2017-08-09 14:07:04
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;


@XmlRootElement(name = UpVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = UpVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class UpVolumeFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "upVolume";
	public static final String SHORT_NAME = "upoVe";
	
	public UpVolumeFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass.audiovolume." + UpVolumeFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
}