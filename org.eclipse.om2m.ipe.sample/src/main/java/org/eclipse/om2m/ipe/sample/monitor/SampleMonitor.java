package org.eclipse.om2m.ipe.sample.monitor;

import org.eclipse.om2m.ipe.sample.controller.SampleController;

/**
 * This class is simply to show the architecture and to reflect the action
 * from the real devices. Here we simulate the reception of the switch signal.
 * @author aissaoui
 *
 */
public class SampleMonitor {
	
	/**
	 * Switch on or off a specific lamp
	 * @param lampId
	 */
	public static void switchLamp(String lampId){
		SampleController.toggleLamp(lampId);
	}
	
	/**
	 * Toggle all lamps 
	 */
	public static void switchAll(){
		SampleController.toogleAll();
	}

}
