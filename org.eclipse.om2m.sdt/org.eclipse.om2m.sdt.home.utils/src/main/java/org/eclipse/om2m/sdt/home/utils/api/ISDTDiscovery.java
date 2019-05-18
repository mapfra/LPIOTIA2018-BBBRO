package org.eclipse.om2m.sdt.home.utils.api;

import java.util.List;

import org.eclipse.om2m.sdt.home.devices.GenericDevice;

public interface ISDTDiscovery {
	
	public void validateUserCredentials(final String appName, 
			final String userName, final String password) throws Exception;

	public List<GenericDevice> getDevices(final boolean cloud, final String name, 
			final String password) throws Exception;

	public List<GenericDevice> getDevices(final String cntDef, final boolean cloud,
			final String name, final String password) throws Exception;

	public GenericDevice getDevice(final String deviceId, final boolean cloud,
			final String name, final String password) throws Exception;

}
