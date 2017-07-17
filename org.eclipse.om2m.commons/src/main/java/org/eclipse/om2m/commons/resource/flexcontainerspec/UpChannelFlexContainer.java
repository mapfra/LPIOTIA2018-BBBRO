/*
Action : upChannel



Change the current channel to the next channel in the stored  list of available channels. If the current channel is the last one  in the list, the new set channel may be the first one in the list.

Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;


@XmlRootElement(name = UpChannelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = UpChannelFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class UpChannelFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "upChannel";
	public static final String SHORT_NAME = "uphCl";
	
	public UpChannelFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass.televisionchannel." + LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
}