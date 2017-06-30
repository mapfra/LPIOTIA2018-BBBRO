package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.home.types.TasteStrength;

public class Brewing extends Module{

	private IntegerDataPoint cupsNumber;
	private TasteStrength strength;

	public Brewing(String name, Domain domain, IntegerDataPoint cupsNumber, TasteStrength strength) {
		super(name, domain, ModuleType.brewing);

		if ((cupsNumber == null) ||
				! cupsNumber.getShortDefinitionType().equals(DatapointType.cupsNumber.getShortName())) {
			domain.removeDevice(name);
			throw new IllegalArgumentException("Wrong cupsNumber datapoint: " + cupsNumber);
		}
		this.cupsNumber = cupsNumber;
		this.cupsNumber.setDoc("The current number of the cups requested to brew.");
		addDataPoint(this.cupsNumber);

		if ((strength == null) ||
				! strength.getShortDefinitionType().equals(DatapointType.strength.getShortName())) {
			domain.removeDevice(name);
			throw new IllegalArgumentException("Wrong strength datapoint: " + strength);
		}
		this.strength = strength;
		this.strength.setDoc("The current strength of the drink taste. A higher value indicates a stronger taste.");
		addDataPoint(this.strength);
	}

	public Brewing(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (IntegerDataPoint) dps.get(DatapointType.cupsNumber.getShortName()),
				(TasteStrength) dps.get(DatapointType.strength.getShortName()));
	}

	public int getCupsNumber() throws DataPointException, AccessException {
		return cupsNumber.getValue();
	}

	public void setCupsNumber(int v) throws DataPointException, AccessException {
		cupsNumber.setValue(v);
	}	

	public int getStrength() throws DataPointException, AccessException {
		return strength.getValue();
	}

	public void setStrength(int v) throws DataPointException, AccessException {
		strength.setValue(v);
	}

}