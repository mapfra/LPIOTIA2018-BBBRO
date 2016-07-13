package org.onem2m.sdt.home.mocked.devices;

import java.util.List;

import org.onem2m.home.devices.WarningDevice;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.home.mocked.module.MockedAlarmSpeaker;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.onem2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class MockedWarningDevice extends WarningDevice implements MockedDevice {

	private boolean alarmStatus;
	private List<ServiceRegistration> serviceRegistrations;

	public MockedWarningDevice(String id, String serial, Domain domain, String location) {
		super(id, serial, domain);

		// set property
		setLocation(location);
		
		// Alarm Speaker
		addModule(new MockedAlarmSpeaker("alarmSpeaker_" + id, domain, new BooleanDataPoint("alarmStatus") {
			@Override
			public void doSetValue(Boolean value) throws DataPointException {
				alarmStatus = value;
				System.out.println("set alarmStatus " + value);
			}
			@Override
			public Boolean doGetValue() throws DataPointException {
				return alarmStatus;
			}
		}));
		
		// FaultDetection
		addModule(new MockedFaultDetection("faultDetection_" + id, domain, new BooleanDataPoint("status") {
			
			Boolean status = Boolean.FALSE;
			
			@Override
			public void doSetValue(Boolean value) throws DataPointException {
				status = value;
			}
			
			@Override
			public Boolean doGetValue() throws DataPointException {
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
