package org.eclipse.om2m.sdt.home.mocked.devices;

import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.home.devices.DeviceNumberDevice;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedBinarySwitch;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedFaultDetection;
import org.eclipse.om2m.sdt.home.mocked.modules.MockedNumberValue;
import org.osgi.framework.ServiceRegistration;

public class MockedDeviceNumberDevice extends DeviceNumberDevice implements MockedDevice {
	
	private List<ServiceRegistration> serviceRegistrations;

	public MockedDeviceNumberDevice(String id, String serial, Domain domain) {
		super(id, serial, domain);
		
		// binarySwitch
		addModule(new MockedBinarySwitch("binarySwitch_" + id, domain));
		
		// faultDetection
		addModule(new MockedFaultDetection("faultDetection_" + id, domain));
		
		// numberValue
		addModule(new MockedNumberValue("numberValue_" + id, domain));
		
	}

	@Override
	public void registerDevice() {
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			return;
		}
		serviceRegistrations = Activator.register(this);
	}

	@Override
	public void unregisterDevice() {
		if (serviceRegistrations == null)
			return;
		for (ServiceRegistration reg : serviceRegistrations) {
			reg.unregister();
		}
		serviceRegistrations.clear();

	}

}
