package org.onem2m.sdt.events;

import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Device;
import org.onem2m.sdt.ModuleClass;

public class SDTNotification {

	private String name;
	private Object val;
	private Device device;
	private ModuleClass module;
	private DataPoint dp;
	
	public SDTNotification(String name, Object val, Device device, ModuleClass module, DataPoint dp) {
		this.name = name;
		this.val = val;
		this.device = device;
		this.module = module;
		this.dp = dp;
	}
	
	public String getName() {
		return name;
	}
	
	public Object getValue() {
		return val;
	}
	
	public Device getDevice() {
		return device;
	}
	
	public ModuleClass getModule() {
		return module;
	}
	
	public DataPoint getDataPoint() {
		return dp;
	}
	
	public String toString() {
		return "<SDTNotification " + name + "=" + val + " device=" + device 
				+ " module=" + module + " datapoint=" + dp + "/>";
	}
	
}
