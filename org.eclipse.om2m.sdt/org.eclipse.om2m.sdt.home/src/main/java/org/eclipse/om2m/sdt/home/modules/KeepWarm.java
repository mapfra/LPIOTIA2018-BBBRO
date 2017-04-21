package org.eclipse.om2m.sdt.home.modules;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class KeepWarm extends BinarySwitch {
	
	private IntegerDataPoint time;
	
	public KeepWarm(String name, Domain domain, BooleanDataPoint keepWarmSwitch){
		
		super(name, domain, ModuleType.keepWarm.getDefinition(), keepWarmSwitch);
		
	}

	public int getTime() throws DataPointException, AccessException {
		return time.getValue();
	}

	public void setTime(IntegerDataPoint time){
		this.time = time;
		addDataPoint(this.time);
	}
	
}
