/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.testsuite.flexcontainer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {

	/**
	 * CSE Service tracker
	 */
	private ServiceTracker<CseService, CseService> cseServiceTracker;

	/**
	 * HTTP service tracker
	 */
	private ServiceTracker<HttpService, HttpService> httpServiceTracker;

	/**
	 * Data mapper service tracker
	 */
	private ServiceTracker<DataMapperService, DataMapperService> dataMapperServiceTracker;

	/**
	 * CSE service instance
	 */
	private CseService currentCseService;

	/**
	 * Http Service instance
	 */
	private HttpService currentHttpService;

	/**
	 * Data mapper service
	 */
	private DataMapperService currentDataMapperService;

	/**
	 * current bundle context
	 */
	private BundleContext bundleContext;

	@Override
	public void start(BundleContext context) throws Exception {
		bundleContext = context;

		// http service tracker
		httpServiceTracker = new ServiceTracker<>(bundleContext, HttpService.class,
				new ServiceTrackerCustomizer<HttpService, HttpService>() {

					@Override
					public HttpService addingService(ServiceReference<HttpService> arg0) {
						if (currentHttpService == null) {
							currentHttpService = bundleContext.getService(arg0);
							return currentHttpService;
						}
						return null;
					}

					@Override
					public void modifiedService(ServiceReference<HttpService> arg0, HttpService arg1) {

					}

					@Override
					public void removedService(ServiceReference<HttpService> arg0, HttpService arg1) {

					}
				});
		httpServiceTracker.open();

		// data mapper service tracker
		dataMapperServiceTracker = new ServiceTracker<>(bundleContext, DataMapperService.class,
				new ServiceTrackerCustomizer<DataMapperService, DataMapperService>() {

					@Override
					public DataMapperService addingService(ServiceReference<DataMapperService> arg0) {
						if (currentDataMapperService == null) {
							currentDataMapperService = bundleContext.getService(arg0);
							return currentDataMapperService;
						}
						return null;
					}

					@Override
					public void modifiedService(ServiceReference<DataMapperService> arg0, DataMapperService arg1) {
					}

					@Override
					public void removedService(ServiceReference<DataMapperService> arg0, DataMapperService arg1) {
					}
				});
		dataMapperServiceTracker.open();

		cseServiceTracker = new ServiceTracker<>(context, CseService.class,
				new ServiceTrackerCustomizer<CseService, CseService>() {

					@Override
					public CseService addingService(ServiceReference<CseService> arg0) {
						// a new CseService is available
						if (currentCseService == null) {
							CseService cseService = bundleContext.getService(arg0);
							setCseServiceAndStartTesting(cseService);
							return cseService;

						}
						return null;
					}

					@Override
					public void modifiedService(ServiceReference<CseService> arg0, CseService arg1) {
						// nothing to do
					}

					@Override
					public void removedService(ServiceReference<CseService> arg0, CseService arg1) {
						unsetCseServiceAndStopTesting();

					}
				});

		cseServiceTracker.open();

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		cseServiceTracker.close();

		cseServiceTracker = null;
	}

	private void setCseServiceAndStartTesting(CseService cseService) {

		List<FlexContainerTestSuite> tests = new ArrayList<>();
		
		currentCseService = cseService;
		try {
			BinarySwitchFlexContainerTest test1 = new BinarySwitchFlexContainerTest(currentCseService);
			test1.executeTestsAndPrintReports();
			tests.add(test1);

			LocationFlexContainerTest test2 = new LocationFlexContainerTest(currentCseService);
			test2.executeTestsAndPrintReports();
			tests.add(test2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if ((currentHttpService != null) && (dataMapperServiceTracker != null)) {
			SubscriptionTest subscriptionTest = new SubscriptionTest(cseService, currentHttpService,
					currentDataMapperService);
			subscriptionTest.executeTestsAndPrintReports();
		}
		
		
		AccessControlPolicyTest acpTest = new AccessControlPolicyTest(currentCseService);
		acpTest.executeTestsAndPrintReports();
		tests.add(acpTest);
		
		FaultDetectionFlexContainerTest faultDetectionTest = new FaultDetectionFlexContainerTest(currentCseService);
		faultDetectionTest.executeTestsAndPrintReports();
		tests.add(faultDetectionTest);
		
		RunModeFlexContainerTest runModeTest = new RunModeFlexContainerTest(cseService);
		runModeTest.executeTestsAndPrintReports();
		tests.add(runModeTest);
		
		// 2017 07 17 - BONNARDEL Gregory
		// Light module does not exist anymore
		// should be replaced by Color ?
//		LightFlexContainerTest light = new LightFlexContainerTest(currentCseService);
//		light.executeTestsAndPrintReports();
//		tests.add(light);
		
		EnergyConsumptionFlexContainerTest energyConsumptionTest = new EnergyConsumptionFlexContainerTest(currentCseService);
		energyConsumptionTest.executeTestsAndPrintReports();
		tests.add(energyConsumptionTest);
		
		WaterSensorFlexContainerTest waterSensorTest = new WaterSensorFlexContainerTest(currentCseService);
		waterSensorTest.executeTestsAndPrintReports();
		tests.add(waterSensorTest);
		
		AlarmSpeakerFlexContainerTest alarmSpeakerTest = new AlarmSpeakerFlexContainerTest(currentCseService);
		alarmSpeakerTest.executeTestsAndPrintReports();
		tests.add(alarmSpeakerTest);
		
		LightDeviceFlexContainerTest lightDeviceTest = new LightDeviceFlexContainerTest(currentCseService);
		lightDeviceTest.executeTestsAndPrintReports();
		tests.add(lightDeviceTest);
		
		SmartElectricMeterFlexContainerTest smartElectricMeterTest = new SmartElectricMeterFlexContainerTest(currentCseService);
		smartElectricMeterTest.executeTestsAndPrintReports();
		tests.add(smartElectricMeterTest);
		
		FloodDetectorFlexContainerTest floodDetectorTest = new FloodDetectorFlexContainerTest(currentCseService);
		floodDetectorTest.executeTestsAndPrintReports();
		tests.add(floodDetectorTest);
		
		GasValveFlexContainerTest gasValveTest = new GasValveFlexContainerTest(currentCseService);
		gasValveTest.executeTestsAndPrintReports();
		tests.add(gasValveTest);
		
		WarningDeviceFlexContainerTest warningDeviceTest = new WarningDeviceFlexContainerTest(currentCseService);
		warningDeviceTest.executeTestsAndPrintReports();
		tests.add(warningDeviceTest);
		
		SmokeDetectorFlexContainerTest smokeDetectorTest = new SmokeDetectorFlexContainerTest(currentCseService);
		smokeDetectorTest.executeTestsAndPrintReports();
		tests.add(smokeDetectorTest);
		
		WaterValveFlexContainerTest waterValveTest = new WaterValveFlexContainerTest(currentCseService);
		waterValveTest.executeTestsAndPrintReports();
		tests.add(waterValveTest);
		
		CallbackTest callbackTest = new CallbackTest(currentCseService, bundleContext);
		callbackTest.executeTestsAndPrintReports();
		tests.add(callbackTest);
		
		
		System.out.println("");
		System.out.println("#####################################################################################");
		for(FlexContainerTestSuite test : tests) {
			test.printTestReports();
		}
	}

	private void unsetCseServiceAndStopTesting() {
		currentCseService = null;
	}

}
