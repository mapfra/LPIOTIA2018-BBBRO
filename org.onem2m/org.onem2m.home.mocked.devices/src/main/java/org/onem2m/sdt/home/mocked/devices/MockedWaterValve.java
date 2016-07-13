package org.onem2m.sdt.home.mocked.devices;

import java.util.List;

import org.onem2m.home.devices.WaterValve;
import org.onem2m.home.types.LiquidLevel;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.onem2m.sdt.home.mocked.module.MockedWaterLevel;
import org.onem2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class MockedWaterValve extends WaterValve implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;

	public MockedWaterValve(String id, String serial, Domain domain,
			String deviceLocation) {
		super(id, serial, domain);

		// set property
		setLocation(deviceLocation);

		// Datapoints
		addModule(new MockedWaterLevel("waterLevel_" + id, domain, new LiquidLevel("liquidLevel") {
			
			Integer openLevel = LiquidLevel.zero;
			
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				openLevel = value;
				System.out.println("openLevel set " + value);
			}
			
			@Override
			public Integer doGetValue() throws DataPointException {
				return openLevel;
			}
		}));
		
		addModule(new MockedFaultDetection("faultDetection_" + id, domain, new BooleanDataPoint("status") {
			
			Boolean status = Boolean.FALSE;
			
			@Override
			public void doSetValue(Boolean value) throws DataPointException {
				status = value;
			}
			
			@Override
			protected Boolean doGetValue() throws DataPointException {
				return status;
			}
		}));
	}

	public void registerDevice() {
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			// already registered
			return;
		}
		serviceRegistrations = Activator.register(this);
	}

	public void unregisterDevice() {
		if (serviceRegistrations == null)
			return;
		for (ServiceRegistration reg : serviceRegistrations) {
			reg.unregister();
		}
		serviceRegistrations.clear();
	}

}
