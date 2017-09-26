/*
Action : downChannel



Change the current channel to the previous channel in the  stored list of available channels. If the current channel is the  first one in the list, the new set channel may be the last one in  the list.

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


@XmlRootElement(name = DownChannelFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DownChannelFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class DownChannelFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "downChannelAnnc";
	public static final String SHORT_NAME = "dowClAnnc";
	
	public DownChannelFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass.televisionchannel." + DownChannelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}