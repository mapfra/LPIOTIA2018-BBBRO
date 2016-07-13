package org.onem2m.sdt.home.mocked.devices;

import java.util.List;

import org.onem2m.home.devices.FloodDetector;
import org.onem2m.home.modules.WaterSensor;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Event;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.home.mocked.module.MockedFaultDetection;
import org.onem2m.sdt.impl.DataPointException;
import org.osgi.framework.ServiceRegistration;

public class MockedFloodDetector extends FloodDetector implements MockedDevice {

	private List<ServiceRegistration> serviceRegistrations;
	
	private boolean waterAlarm = false;
	private boolean running = false;
	private WaterSensor waterSensor;

	public MockedFloodDetector(String id, String serial, Domain domain, String deviceLocation) {
		super(id, serial, domain);

		// set property
		setLocation(deviceLocation);
		
		waterSensor = new WaterSensor("waterSensor_" + id, domain, new BooleanDataPoint("alarm") {
			@Override
			public Boolean doGetValue() throws DataPointException {
				return waterAlarm;
			}
		});
		addModule(waterSensor);
				
		addModule(new MockedFaultDetection("faultDetection_" + id, domain, new BooleanDataPoint("status") {
			
			Boolean status = Boolean.FALSE;
			
			@Override
			public Boolean doGetValue() throws DataPointException {
				return status;
			}
		}));
	}

	public void registerDevice() {
		running = true;
		if (! ((serviceRegistrations == null) || serviceRegistrations.isEmpty())) {
			// already registered
			return;
		}
		serviceRegistrations = Activator.register(this);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					try {
						Thread.sleep((int) (10000 + 5000 * Math.random()));
						Activator.logger.info("Generating Alarm event");
						Event evt = new Event("ALARM");
						waterAlarm = ! waterAlarm;
						evt.addDataPoint(waterSensor.getDataPoint("alarm"));
						evt.setValue(waterAlarm);
						waterSensor.addEvent(evt);
					} catch (Throwable e) {
						Activator.logger.warning("Error generating event", e);
					}
				}
			}
		}).start();
	}

	public void unregisterDevice() {
		running = false;
		if (serviceRegistrations == null)
			return;
		for (ServiceRegistration reg : serviceRegistrations) {
			reg.unregister();
		}
		serviceRegistrations.clear();
	}

}
