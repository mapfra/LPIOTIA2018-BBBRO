/*
ModuleClass : AudioVideoInputAnnc



This ModuleClass provides capabilities to control and monitor  audio video input source of device such as TV or SetTopBox.

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


@XmlRootElement(name = AudioVideoInputFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AudioVideoInputFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class AudioVideoInputFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "audioVideoInputAnnc";
	public static final String SHORT_NAME = "auVItAnnc";
	
	public AudioVideoInputFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + AudioVideoInputFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
	}
	
}