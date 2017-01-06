package org.onem2m.home.modules;

import java.util.Map;

import org.onem2m.home.types.FoamStrength;
import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

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