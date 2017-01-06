package org.onem2m.home.modules;

import java.util.Map;

import org.onem2m.home.types.ModuleType;
import org.onem2m.home.types.TasteStrength;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class Brewing extends Module{
	
	private IntegerDataPoint cupsNumber;
	
	private BooleanDataPoint keepWarm;
	
	private TasteStrength strength;
	
	private IntegerDataPoint status;
	
	//TODO add properties maxCupsNumber

	public Brewing(String name, Domain domain, IntegerDataPoint cupsNumber, BooleanDataPoint keepWarm, TasteStrength strength, IntegerDataPoint status) {
		super(name, domain, ModuleType.brewing.getDefinition());
		
		this.cupsNumber = cupsNumber;
		this.cupsNumber.setDoc("The current number of the cups requested to brew.");
		addDataPoint(this.cupsNumber);
		
		this.keepWarm = keepWarm;
		this.keepWarm.setDoc("The current status of the keeping a drink warm after brewing enabling. “True” indicates enabled, and “False” indicates not enabled.");
		addDataPoint(this.keepWarm);
	
		this.strength = strength;
		this.strength.setDoc("The current strength of the drink taste. A higher value indicates a stronger taste.");
		addDataPoint(this.strength);
		
		this.status = status;
		this.status.setDoc("The current status of the machine which prepares the drinks. Status equals 1 means the brewing is ongoing, 0 means the brewing is not ongoing.");
		addDataPoint(this.status);

	}
	
	public Brewing(final String name, final Domain domain, Map<String, DataPoint> dps) {
	        this(name, domain, (IntegerDataPoint) dps.get("cupsNumber"), (BooleanDataPoint) dps.get("keepWarm"), (TasteStrength) dps.get("strength"), (IntegerDataPoint) dps.get("status"));
	}

	public int getCupsNumber() throws DataPointException, AccessException {
			return cupsNumber.getValue();
		}

	public void setCupsNumber(int v) throws DataPointException, AccessException {
			cupsNumber.setValue(v);
		}	
	
	public boolean getKeepWarm() throws DataPointException, AccessException {
		return keepWarm.getValue();
	}

	public void setKeepWarm(boolean v) throws DataPointException, AccessException {
		keepWarm.setValue(v);
	}
	
	public int getStrength() throws DataPointException, AccessException {
		return strength.getValue();
	}

	public void setStrengthy(int v) throws DataPointException, AccessException {
		strength.setValue(v);
	}
	
	public int getStatus() throws DataPointException, AccessException {
		return status.getValue();
	}

	public void setStatus(int v) throws DataPointException, AccessException {
		status.setValue(v);
	}

}