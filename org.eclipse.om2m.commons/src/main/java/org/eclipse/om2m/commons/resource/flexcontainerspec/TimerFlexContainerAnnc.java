/*
ModuleClass : TimerAnnc



This ModuleClass provides capabilities to monitor and control  the times when the appliance executes its operations (i.e. when it  starts, when it ends).

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


@XmlRootElement(name = TimerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TimerFlexContainerAnnc.SHORT_NAME, namespace = "http://www.onem2m.org/xml/protocols/homedomain")
public class TimerFlexContainerAnnc extends AbstractFlexContainerAnnc {
	
	public static final String LONG_NAME = "timerAnnc";
	public static final String SHORT_NAME = "timerAnnc";
	
	public TimerFlexContainerAnnc () {
		setContainerDefinition("org.onem2m.home.moduleclass." + TimerFlexContainer.LONG_NAME);
		setLongName(LONG_NAME);
		setShortName(SHORT_NAME);
	}
	
	public void finalizeSerialization() {
		getActivateClockTimerAnnc();
		getDeactivateClockTimerAnnc();
	}
	
	@XmlElement(name=ActivateClockTimerFlexContainerAnnc.SHORT_NAME, required=true, type=ActivateClockTimerFlexContainerAnnc.class)
	private ActivateClockTimerFlexContainerAnnc activateClockTimerAnnc;
	
	
	public void setActivateClockTimer(ActivateClockTimerFlexContainerAnnc activateClockTimerAnnc) {
		this.activateClockTimerAnnc = activateClockTimerAnnc;
		getFlexContainerOrContainerOrSubscription().add(activateClockTimerAnnc);
	}
	
	public ActivateClockTimerFlexContainerAnnc getActivateClockTimerAnnc() {
		this.activateClockTimerAnnc = (ActivateClockTimerFlexContainerAnnc) getResourceByName(ActivateClockTimerFlexContainerAnnc.SHORT_NAME);
		return activateClockTimerAnnc;
	}
	
	@XmlElement(name=DeactivateClockTimerFlexContainerAnnc.SHORT_NAME, required=true, type=DeactivateClockTimerFlexContainerAnnc.class)
	private DeactivateClockTimerFlexContainerAnnc deactivateClockTimerAnnc;
	
	
	public void setDeactivateClockTimer(DeactivateClockTimerFlexContainerAnnc deactivateClockTimerAnnc) {
		this.deactivateClockTimerAnnc = deactivateClockTimerAnnc;
		getFlexContainerOrContainerOrSubscription().add(deactivateClockTimerAnnc);
	}
	
	public DeactivateClockTimerFlexContainerAnnc getDeactivateClockTimerAnnc() {
		this.deactivateClockTimerAnnc = (DeactivateClockTimerFlexContainerAnnc) getResourceByName(DeactivateClockTimerFlexContainerAnnc.SHORT_NAME);
		return deactivateClockTimerAnnc;
	}
	
}