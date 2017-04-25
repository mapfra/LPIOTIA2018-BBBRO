package org.eclipse.om2m.sdt.home.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Boiling extends Module {
	
	private BooleanDataPoint keepWarm;
	private IntegerDataPoint status;
	
	
	public Boiling(String name, Domain domain, BooleanDataPoint keepWarm, IntegerDataPoint status){
		super(name, domain, ModuleType.boiling.getDefinition(), ModuleType.boiling.getLongDefinitionName(), ModuleType.boiling.getShortDefinitionName());
		
		this.keepWarm = keepWarm;
		this.keepWarm.setDoc("The current status of the keeping a drink warm after brewing enabling. “True” indicates enabled, and “False” indicates not enabled");
		this.status = status;
		this.status.setDoc("The current status of the machine which prepares the drinks. Status equals 1 means the boiling is ongoing, 0 means the boiling is not ongoing.");
		status.setReadable(true);
		addDataPoint(this.keepWarm);
		addDataPoint(this.status);
	}

	public Boolean getKeepWarm() throws DataPointException, AccessException{
		return keepWarm.getValue();
	}
	
	public int getStatus() throws DataPointException, AccessException{
		return status.getValue();
	}
	
	public void setKeepWarm(Boolean v) throws DataPointException, AccessException{
		keepWarm.setValue(v);
	}
	
	public void setStatus(int v) throws DataPointException, AccessException{
		status.setValue(v);
	}

	
	
}
