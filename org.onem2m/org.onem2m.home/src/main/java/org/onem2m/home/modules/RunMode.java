package org.onem2m.home.modules;

import java.util.Arrays;
import java.util.List;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.ArrayDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class RunMode extends Module {
	
	private ArrayDataPoint<String> operationMode;
	private ArrayDataPoint<String> supportedModes;

	public RunMode(final String name, final Domain domain,
			ArrayDataPoint<String> operationMode,
			ArrayDataPoint<String> supportedModes) {
		super(name, domain, ModuleType.runMode.getDefinition());
		
		this.operationMode = operationMode;
		this.operationMode.setDoc("Comma separated list of the currently active mode(s)");
		addDataPoint(this.operationMode);
		
		this.supportedModes = supportedModes;
		this.supportedModes.setDoc("Comma separated list of possible modes the device supports");
		addDataPoint(this.supportedModes);
	}

	public List<String> getOperationMode() throws DataPointException, AccessException {
		return operationMode.getValue();
	}

	public void setOperationMode(List<String> v) throws DataPointException, AccessException {
		this.operationMode.setValue(v);
	}

	public void setOperationMode(String v) throws DataPointException, AccessException {
		this.operationMode.setValue(Arrays.asList(v));
	}

	public List<String> getSupportedModes() throws DataPointException, AccessException {
		return supportedModes.getValue();
	}

	public void setSupportedModes(List<String> v) throws DataPointException, AccessException {
		this.supportedModes.setValue(v);
	}

}
