package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class Grinder extends Module {
	
	private BooleanDataPoint useGrinder;
	
	private IntegerDataPoint grindCoarsenes;
	
	public Grinder(final String name, final Domain domain, BooleanDataPoint useGrinder, IntegerDataPoint grindCoarsenes){
		super(name, domain, ModuleType.grinder.getDefinition());
		setExtends(domain.getName(), "Grinder");
		
		this.useGrinder = useGrinder;
		this.useGrinder.setDoc("The current status of the grinder enablement");
		addDataPoint(this.useGrinder);
		
		this.grindCoarsenes = grindCoarsenes;
		this.grindCoarsenes.setDoc("The current coarseness of the object after grinding.");
		addDataPoint(this.grindCoarsenes);
	}
	
    public Grinder(final String name, final Domain domain, Map<String, DataPoint> dps) {
        this(name, domain, (BooleanDataPoint) dps.get("useGrinder"), (IntegerDataPoint) dps.get("grindCoarsenes"));
    }
	
	public boolean getUseGrinder() throws DataPointException, AccessException {
		return useGrinder.getValue();
	}

	public void setUseGrinder(boolean v) throws DataPointException, AccessException {
		this.useGrinder.setValue(v);
	}

	public int getGrindCoarsenes() throws DataPointException, AccessException  {
		return grindCoarsenes.getValue();
	}

	public void setGrindCoarsenes(int v) throws DataPointException, AccessException  {
		this.grindCoarsenes.setValue(v);
	}
	
}