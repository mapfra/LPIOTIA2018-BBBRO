/*
ModuleClass : AudioVolume



This ModuleClass provides capabilities to control and monitor  volume.

Created: 2017-07-17 15:25:54
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.eclipse.om2m.commons.resource.AbstractFlexContainer;


@XmlRootElement(name = AudioVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AudioVolumeFlexContainer.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class AudioVolumeFlexContainer extends AbstractFlexContainer {
	
	public static final String LONG_NAME = "audioVolume";
	public static final String SHORT_NAME = "audVe";
	
	public AudioVolumeFlexContainer () {
		setContainerDefinition("org.onem2m.home.moduleclass." + LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getUpVolume();
		getDownVolume();
	}
	
	@XmlElement(name=UpVolumeFlexContainer.SHORT_NAME, required=true, type=UpVolumeFlexContainer.class)
	private UpVolumeFlexContainer upVolume;
	
	
	public void setUpVolume(UpVolumeFlexContainer upVolume) {
		this.upVolume = upVolume;
		getFlexContainerOrContainerOrSubscription().add(upVolume);
	}
	
	public UpVolumeFlexContainer getUpVolume() {
		this.upVolume = (UpVolumeFlexContainer) getResourceByName(UpVolumeFlexContainer.SHORT_NAME);
		return upVolume;
	}
	
	@XmlElement(name=DownVolumeFlexContainer.SHORT_NAME, required=true, type=DownVolumeFlexContainer.class)
	private DownVolumeFlexContainer downVolume;
	
	
	public void setDownVolume(DownVolumeFlexContainer downVolume) {
		this.downVolume = downVolume;
		getFlexContainerOrContainerOrSubscription().add(downVolume);
	}
	
	public DownVolumeFlexContainer getDownVolume() {
		this.downVolume = (DownVolumeFlexContainer) getResourceByName(DownVolumeFlexContainer.SHORT_NAME);
		return downVolume;
	}
	
}