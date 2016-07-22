/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt.testsuite;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.sdt.testsuite.TestReport.State;
import org.eclipse.om2m.ipe.sdt.testsuite.module.AbstractModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.AlarmSpeakerModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.BinarySwitchModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.ColourModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.ColourSaturationModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.FaultDetectionModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.RunModeModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.SmokeSensorModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.WaterLevelModuleTest;
import org.eclipse.om2m.ipe.sdt.testsuite.module.WaterSensorModuleTest;
import org.onem2m.sdt.Module;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class SDTModuleTestSuite {

	private final BundleContext bundleContext;
	private final CseService cseService;
	private final ServiceTracker sdtModuleServiceTracker;

	private final List<TestReport> testReports = new ArrayList<>();
	
	
	public SDTModuleTestSuite(final BundleContext pBundleContext, final CseService pCseService) {
		this.bundleContext = pBundleContext;
		this.cseService = pCseService;

		// create SDT Module Tracker
		sdtModuleServiceTracker = new ServiceTracker(bundleContext, Module.class.getName(),
				new ServiceTrackerCustomizer() {

					@Override
					public void removedService(ServiceReference reference, Object service) {
						// nothing to do as we return null in the addingService
						// method.

					}

					@Override
					public void modifiedService(ServiceReference reference, Object service) {
						// nothing to do
					}

					@Override
					public Object addingService(ServiceReference reference) {

						Module module = (Module) bundleContext.getService(reference);
						launchModuleTest(module);

						// always return null as we don't want to track any
						// service
						return null;
					}
				});
		sdtModuleServiceTracker.open();
	}

	/**
	 * Launch module test.
	 * 
	 * @param module
	 */
	private void launchModuleTest(final Module module) {

		// identify the module based on its module definition
		String moduleDefinition = module.getDefinition();

		AbstractModuleTest amt = null;
		switch (moduleDefinition) {
		case "org.onem2m.home.moduleclass.binarySwitch":
			amt = new BinarySwitchModuleTest(cseService, module);
			
			break;
		case "org.onem2m.home.moduleclass.smokeSensor":
			amt = new SmokeSensorModuleTest(cseService, module);
			
			break;
		case "org.onem2m.home.moduleclass.colourSaturation":
			amt = new ColourSaturationModuleTest(cseService, module);
			break;
		case "org.onem2m.home.moduleclass.colour":
			amt = new ColourModuleTest(cseService, module);
			break;
		case "org.onem2m.home.moduleclass.faultDetection":
			amt = new FaultDetectionModuleTest(cseService, module);
			break;
		case "org.onem2m.home.moduleclass.waterSensor":
			amt = new WaterSensorModuleTest(cseService, module);
			break;
		case "org.onem2m.home.moduleclass.runMode":
			amt = new RunModeModuleTest(cseService, module);
			break;
		case "org.onem2m.home.moduleclass.alarmSpeaker":
			amt = new AlarmSpeakerModuleTest(cseService, module);
			break;
		case "org.onem2m.home.moduleclass.waterLevel":
			amt = new WaterLevelModuleTest(cseService, module);
			break;
		default:
			break;
		}
		
		
		if (amt != null) {
			List<TestReport> tests = amt.launchTests();
			testReports.addAll(tests);
		} else {
			TestReport report = null;
			report = new TestReport("missing ModuleTest");
			report.setErrorMessage("missing TestModule for module " + module.getDefinition());
			report.setState(State.KO);
			testReports.add(report);
		}
		

		System.out.println("\n");
		System.out.println("\n");
		System.out.println("------------------------------------------------------------");
		
		for(TestReport tr : testReports) {
			System.out.println(tr.toString());
			Exception e =  tr.getException();
			if (e != null) {
				e.printStackTrace();
			}
		}

		
		System.out.println("------------------------------------------------------------");
		System.out.println("\n");
		System.out.println("\n");

	}

}
