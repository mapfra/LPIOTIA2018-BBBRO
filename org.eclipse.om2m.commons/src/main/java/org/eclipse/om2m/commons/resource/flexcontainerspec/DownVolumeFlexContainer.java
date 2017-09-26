/*
Action : downVolume



Decrease volume by the amount of the stepValue down to 0.

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


@XmlRootElement(name = DownVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DownVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DownVolumeFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "downVolume";
	public static final String SHORT_NAME = "dowVe";
	
	public DownVolumeFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass.audiovolume." + DownVolumeFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}