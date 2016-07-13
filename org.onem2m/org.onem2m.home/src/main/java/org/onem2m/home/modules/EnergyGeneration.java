package org.onem2m.home.modules;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class EnergyGeneration extends Module {
	
	private FloatDataPoint powerGenerationData;
	
	private IntegerDataPoint roundingEnergyGeneration;
	private IntegerDataPoint significantDigits;
	private IntegerDataPoint multiplyingFactors;
	
	public EnergyGeneration(final String name, final Domain domain) {
		super(name, domain, ModuleType.energyGeneration.getDefinition());
	}

	public float getPowerGenerationData() throws DataPointException, AccessException {
		return powerGenerationData.getValue();
	}

	public void setPowerGenerationData(float b) throws DataPointException {
		throw new DataPointException("Not writable");
	}

	public void setPowerGenerationData(FloatDataPoint dp) {
		this.powerGenerationData = dp;
		this.powerGenerationData.setOptional(true);
		this.powerGenerationData.setWritable(false);
		this.powerGenerationData.setDoc("Amount of instaneous generation data.");
		addDataPoint(powerGenerationData);
	}

	public void setMultiplyingFactors(IntegerDataPoint dp) {
		this.multiplyingFactors = dp;
		this.multiplyingFactors.setOptional(true);
		this.multiplyingFactors.setWritable(true);
		this.multiplyingFactors.setDoc("The unit for data (multiplying factors) Ex. 1kWh, 0.1kWh, 0.01kWh etc.");
		addDataPoint(multiplyingFactors);
	}

	public Integer getMultiplyingFactors() throws DataPointException, AccessException {
		if (multiplyingFactors == null)
			throw new DataPointException("Not implemented");
		return multiplyingFactors.getValue();
	}

	public void setMultiplyingFactors(Integer i) throws DataPointException, AccessException {
		if (multiplyingFactors == null)
			throw new DataPointException("Not implemented");
		multiplyingFactors.setValue(i);
	}

	public void setRoundingEnergyGeneration(IntegerDataPoint dp) {
		this.roundingEnergyGeneration = dp;
		this.roundingEnergyGeneration.setOptional(true);
		this.roundingEnergyGeneration.setWritable(false);
		this.roundingEnergyGeneration.setDoc("This energy generation data can be calculated by using significantFigures and units.");
		addDataPoint(roundingEnergyGeneration);
	}

	public int getRoundingEnergyGeneration() throws DataPointException, AccessException {
		if (roundingEnergyGeneration == null)
			throw new DataPointException("Not implemented");
		return roundingEnergyGeneration.getValue();
	}

	public void setRoundingEnergyGeneration(int b) throws DataPointException {
		throw new DataPointException("Not writable");
	}

	public void setSignificantDigits(IntegerDataPoint dp) {
		this.significantDigits = dp;
		this.significantDigits.setOptional(true);
		this.significantDigits.setWritable(true);
		this.significantDigits.setDoc("The number of effective digits for data.");
		addDataPoint(significantDigits);
	}

	public int getSignificantDigits() throws DataPointException, AccessException {
		if (significantDigits == null)
			throw new DataPointException("Not implemented");
		return significantDigits.getValue();
	}

	public void setSignificantDigits(int b) throws DataPointException, AccessException {
		if (significantDigits == null)
			throw new DataPointException("Not implemented");
		if (b < 0)
			throw new DataPointException("Negative value");
		significantDigits.setValue(b);
	}

}
