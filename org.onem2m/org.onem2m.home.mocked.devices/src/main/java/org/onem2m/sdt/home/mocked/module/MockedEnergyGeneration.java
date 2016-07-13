package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.EnergyGeneration;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.DataPointException;

public class MockedEnergyGeneration extends EnergyGeneration {

	public MockedEnergyGeneration(String name, Domain domain) {
		super(name, domain);

		addDataPoint(new FloatDataPoint("powerGenerationData") {
			
			Float power = new Float(0);
			
			@Override
			public Float doGetValue() throws DataPointException {
				return power;
			}
		});
		
		addDataPoint(new IntegerDataPoint("roundingEnergyGeneration") {
			
			Integer roundingEneryGeneration = new Integer(0);
		
			@Override
			public Integer doGetValue() throws DataPointException {
				return roundingEneryGeneration;
			}
		});
		
		addDataPoint(new IntegerDataPoint("significantDigits") {
			
			Integer significantDigits = new Integer(1);
			
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				significantDigits = value;
			}
			
			@Override
			public Integer doGetValue() throws DataPointException {
				return significantDigits;
			}
		});
		
		addDataPoint(new IntegerDataPoint("multiplyingFactors") {
			
			Integer multiplyingFactors = new Integer(2);
			
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				multiplyingFactors = value;
			}
			
			@Override
			public Integer doGetValue() throws DataPointException {
				return multiplyingFactors;
			}
		});
	}

}
