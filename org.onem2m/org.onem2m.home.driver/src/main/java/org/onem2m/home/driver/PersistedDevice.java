package org.onem2m.home.driver;

import java.util.Dictionary;
import java.util.Enumeration;

import org.onem2m.home.devices.GenericDevice;
import org.onem2m.sdt.Property;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

public class PersistedDevice implements ManagedService {

	private GenericDevice device;
	private ServiceRegistration registration;
	private Logger logger;
	
	PersistedDevice(GenericDevice device) {
		this.device = device;
		logger = new Logger(device.getProtocol());
	}
	
	void setRegistration(ServiceRegistration registration) {
		this.registration = registration;
	}

	@Override
	public void updated(Dictionary props) throws ConfigurationException {
		if ((props != null) && updateDevice(props)) {
			try {
				registration.setProperties(props);
			} catch (Exception e) {
				throw new ConfigurationException(null, null, e);
			}
		}
	}
	
	public boolean updateDevice(Dictionary props) {
		boolean modified = false;
		for (Enumeration keys = props.keys(); keys.hasMoreElements(); ) {
			String key = (String)keys.nextElement();
			String val = (String)props.get(key);
			Property old = device.getProperty(key);
			if (old == null) {
				// Not a valid property: ignore (cannot add dynamically new properties)
				logger.info("Unknown property (not SDT): " + key + "/" + val);
			} else if (Utils.equals((String)old.getValue(), val)) {
				// No change, ignore
				logger.info("Unchanged property: " + key + "/" + val);
			} else {
				old.setValue(val);
				modified = true;
				logger.info("Set persisted property: " + key + "/" + val);
			}
		}
		return modified;
	}

}
