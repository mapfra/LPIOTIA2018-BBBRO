package org.eclipse.om2m.ipe.sdt;

import org.eclipse.om2m.sdt.Device;

public interface DeviceListListener {
	
	public void notifyNewDevice(Device newDevice);
	
	public void notifyDeviceRemoved(Device toBeRemovedDevice);

}
