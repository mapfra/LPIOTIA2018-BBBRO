package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class Boiler extends Module {
	
	private BooleanDataPoint status;

	public Boiler(final String name, final Domain domain, BooleanDataPoint status) {
		super(name, domain, ModuleType.boiler.getDefinition());
		
		this.status = status;
		this.status.setDoc("The status of boiling");
		addDataPoint(status);
	}

	public boolean getStatus() throws DataPointException, AccessException {
		return status.getValue();
	}

	public void setStatus(boolean v) throws DataPointException, AccessException {
		status.setValue(v);
	}

}
