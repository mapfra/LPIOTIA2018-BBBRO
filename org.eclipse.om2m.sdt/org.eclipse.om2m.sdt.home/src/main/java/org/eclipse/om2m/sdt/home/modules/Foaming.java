package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.FoamStrength;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Foaming extends Module{
	
	private FoamStrength foamingStrength;
	
	public Foaming(String name, Domain domain, FoamStrength dp) {
		super(name, domain, ModuleType.foaming.getDefinition());
		
		foamingStrength = dp;
		foamingStrength.setDoc("The current strength of foamed milk. A higher value indicates a milk which is more foamed.");
		addDataPoint(foamingStrength);
	}
	

    public Foaming(final String name, final Domain domain,  Map<String, DataPoint> dps) {
        this(name, domain,  (FoamStrength) dps.get("foamingStrength"));
    }

    
    public int getFoamingStrength() throws DataPointException, AccessException{
		return foamingStrength.getValue();
	}


	public void setFoamingStrength(int v)  throws DataPointException, AccessException{
		this.foamingStrength.setValue(v);
	}
	
}