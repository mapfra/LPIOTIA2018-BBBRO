/*
Action : upChannel



Change the current channel to the next channel in the stored  list of available channels. If the current channel is the last one  in the list, the new set channel may be the first one in the list.

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


@XmlRootElement(name = UpChannelFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = UpChannelFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class UpChannelFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "upChannelAnnc";
	public static final String SHORT_NAME = "uphClAnnc";
	
	public UpChannelFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass.televisionchannel." + UpChannelFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	
	public void finalizeSerialization() {
	}
	
	public void finalizeDeserialization() {
	}
	
}