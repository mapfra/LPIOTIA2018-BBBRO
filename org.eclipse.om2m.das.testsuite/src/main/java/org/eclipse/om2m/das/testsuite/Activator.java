package org.eclipse.om2m.das.testsuite;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.das.testsuite.ae.DASServiceTest_AccessDenied;
import org.eclipse.om2m.das.testsuite.ae.DASServiceTest_Ae;
import org.eclipse.om2m.das.testsuite.ae.DASServiceTest_AeAnnc;
import org.eclipse.om2m.das.testsuite.ae.DASServiceTest_FlexContainer;
import org.eclipse.om2m.das.testsuite.ae.DASServiceTest_FlexContainerAnnc;
import org.eclipse.om2m.das.testsuite.ae.DASServiceTest_IndirectDACIs;
import org.eclipse.om2m.das.testsuite.ae.DASServiceTest_TwoDAS;
import org.eclipse.om2m.das.testsuite.crud.CreateDAS_Application_Test;
import org.eclipse.om2m.das.testsuite.crud.CreateDAS_CseBase_Test;
import org.eclipse.om2m.das.testsuite.crud.CreateDAS_RemoteCSE_Test;
import org.eclipse.om2m.das.testsuite.crud.DeleteDASTest;
import org.eclipse.om2m.das.testsuite.crud.RetrieveDASTest;
import org.eclipse.om2m.das.testsuite.crud.UpdateDASTest;
import org.eclipse.om2m.das.testsuite.dacis.AeAnncDacisTest;
import org.eclipse.om2m.das.testsuite.dacis.AeDacisTest;
import org.eclipse.om2m.das.testsuite.dacis.DynamicAuthorizationConsultationDacisTest;
import org.eclipse.om2m.das.testsuite.dacis.FlexContainerAnncDacisTest;
import org.eclipse.om2m.das.testsuite.dacis.FlexContainerDacisTest;
import org.eclipse.om2m.das.testsuite.dacis.RemoteCseDacisTest;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	private static List<Class<? extends Test>> testClasses = new ArrayList<>();
	private List<Test> tests = new ArrayList<>();

	static {
		// add all testClasses
		testClasses.add(CreateDAS_CseBase_Test.class);
		testClasses.add(UpdateDASTest.class);
		testClasses.add(RetrieveDASTest.class);
		testClasses.add(DeleteDASTest.class);
		testClasses.add(CreateDAS_Application_Test.class);
		testClasses.add(CreateDAS_RemoteCSE_Test.class);
		testClasses.add(FlexContainerDacisTest.class);
		testClasses.add(AeDacisTest.class);
		testClasses.add(DynamicAuthorizationConsultationDacisTest.class);
		testClasses.add(RemoteCseDacisTest.class);
		testClasses.add(AeAnncDacisTest.class); 	
		testClasses.add(FlexContainerAnncDacisTest.class);
		testClasses.add(DASServiceTest_Ae.class);
		testClasses.add(DASServiceTest_AccessDenied.class);
		testClasses.add(DASServiceTest_TwoDAS.class);
		testClasses.add(DASServiceTest_FlexContainer.class);
		testClasses.add(DASServiceTest_FlexContainerAnnc.class);
		testClasses.add(DASServiceTest_AeAnnc.class);
		testClasses.add(DASServiceTest_IndirectDACIs.class);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		
		CseService cseService = null;
		ServiceReference<CseService> serviceRef = context.getServiceReference(CseService.class);
		if (serviceRef != null) {
			cseService = context.getService(serviceRef);
		}
		
		if (cseService == null) {
			printNoCseService();
			return;
		}

		for(Class<? extends Test> c : testClasses) {
			try {
				Constructor<? extends Test> constructor = c.getConstructor(CseService.class);
					Test t = constructor.newInstance(cseService);
					
					t.performTest();
					
					tests.add(t);
					
					
					t.cleanUp();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		printAllTestsReports();
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		this.context = null;

		printAllTestsReports();
		
		tests.clear();
		
	}
	
	public static BundleContext getBundleContext() {
		return context;
	}
	
	private void printNoCseService() {
		System.out.println("############################################################################################");
		System.out.println("#                                                                                          #");
		System.out.println("# Test reports: NO CseService                                                              #");
		System.out.println("#                                                                                          #");
		System.out.println("############################################################################################");
	}
	
	private void printAllTestsReports() {
		System.out.println("############################################################################################");
		System.out.println("#                                                                                          #");
		System.out.println("# Test reports:                                                                            #");
		System.out.println("#                                                                                          #");
		System.out.println("############################################################################################");
		for(Test t : tests) {
			t.printTestReport();
		}
		System.out.println("############################################################################################");
		System.out.println("#                                                                                          #");
		System.out.println("# Test reports end                                                                         #");
		System.out.println("#                                                                                          #");
		System.out.println("############################################################################################");
	}

}
