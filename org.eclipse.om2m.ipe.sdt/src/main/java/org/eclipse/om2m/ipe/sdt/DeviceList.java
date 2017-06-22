package org.eclipse.om2m.ipe.sdt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.sdt.Device;

/**
 * This class holds the list of available SDT devices.
 * This class uses the listener pattern to notify about device lifecycle
 * @author MPCY8647
 *
 */
public class DeviceList implements DeviceListListener {
	
	private final static DeviceList INSTANCE = new DeviceList();
	
	private List<Device> devices;
	private List<DeviceListListener> listeners;
	
	/**
	 * Private constructor.
	 * Initializes internal data structures.
	 */
	private DeviceList() {
		devices = new ArrayList<>();
		listeners = new ArrayList<>();
	}
	
	public static DeviceList getInstance() {
		return INSTANCE;
	}
	
	
	/**
	 * Add a device
	 * @param pDevice device to be added
	 */
	public void addDevice(Device pDevice) {
		synchronized (devices) {
			devices.add(pDevice);
			notifyNewDevice(pDevice);
		}
	}
	
	/**
	 * Remove a device from list
	 * @param pDevice device to be removed
	 */
	public void removeDevice(Device pDevice) {
		synchronized (devices) {
			if (devices.remove(pDevice)) {
				notifyDeviceRemoved(pDevice);
			}
		}
	}
	
	/**
	 * Return a duplicated list of available device
	 * @return
	 */
	public List<Device> getDevices() {
		List<Device> toBeReturned = new ArrayList<>();
		synchronized (devices) {
			toBeReturned.addAll(devices);
		}
		return toBeReturned;
	}
	
	/**
	 * Add a listener and send to it the current list of devices
	 * @param listenerToBeAdded listener to be added
	 */
	public void addListenerAndSend(DeviceListListener listenerToBeAdded) {
		synchronized (listeners) {
			listeners.add(listenerToBeAdded);
		}
		
		for(Device device : getDevices()){
			try {
				listenerToBeAdded.notifyNewDevice(device);
			} catch (Exception e) {
				// silent
			}
		}
	}
	
	/**
	 * Retrieve a duplicated list of listeners
	 * @return listeners
	 */
	public List<DeviceListListener> getListeners() {
		List<DeviceListListener> toBeReturned = new ArrayList<>();
		synchronized (listeners) {
			toBeReturned.addAll(listeners);
		}
		return toBeReturned;
	}
	
	/**
	 * Remove a listener.
	 * The to-be-removed listener is notified about device removed through notification
	 * @param listenerToBeRemoved
	 */
	public void removeListener(DeviceListListener listenerToBeRemoved) {
		for(Device device : getDevices()){
			try {
				listenerToBeRemoved.notifyDeviceRemoved(device);
			} catch (Exception e) {
				// silent
			}
		}
		
		synchronized (listeners) {
			listeners.remove(listenerToBeRemoved);
		}
		
	}

	
	@Override
	/**
	 * Retrieve all listeners and notify them about new device
	 */
	public void notifyNewDevice(Device newDevice) {
		for(DeviceListListener listener : getListeners()) {
			try {
				listener.notifyNewDevice(newDevice);
			} catch (Exception e) {
			}
		}
	}

	@Override
	/**
	 * Retrieve all listeners and notify them about device removal
	 */
	public void notifyDeviceRemoved(Device toBeRemovedDevice) {
		for(DeviceListListener listener : getListeners()) {
			try {
				listener.notifyDeviceRemoved(toBeRemovedDevice);
			} catch (Exception e) {
			}
		}
	}


}
