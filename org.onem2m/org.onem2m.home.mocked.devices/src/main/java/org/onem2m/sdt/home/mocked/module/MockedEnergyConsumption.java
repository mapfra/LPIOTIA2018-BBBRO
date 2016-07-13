package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.EnergyConsumption;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;
import org.onem2m.sdt.impl.DataPointException;

public class MockedEnergyConsumption extends EnergyConsumption {

	public MockedEnergyConsumption(String name, Domain domain, FloatDataPoint value) {
		super(name, domain, value);
		
		// absolute energy consumption data
		setAbsoluteEnergyConsumption(new FloatDataPoint("absoluteEnergyConsumption") {
			
			Float dataPointValue = (float) 0;
			
			@Override
			public void doSetValue(Float value) throws DataPointException {
				dataPointValue = value;
			}
			
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// rounding energy consumption data
		setRoundingEnergyConsumption(new IntegerDataPoint("roundingEnergyConsumption") {
			
			Integer dataPointValue = 0;
			
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				dataPointValue = value;
			}
			
			@Override
			public Integer doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// significant figures
		setSignificantDigits(new IntegerDataPoint("significantDigits") {
			
			Integer dataPointValue = 0;
			
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				dataPointValue = value;
			}
			
			@Override
			public Integer doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		
		// multiplying factors
		setMultiplyingFactors(new IntegerDataPoint("multiplyingFactors") {
			
			Integer dataPointValue = 0;
			
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				dataPointValue = value;
			}
			
			@Override
			public Integer doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		
		// voltage
		setVoltage(new FloatDataPoint("voltage") {
			
			Float dataPointValue = (float) 0;
			
			@Override
			public void doSetValue(Float value) throws DataPointException {
				dataPointValue = value;
			}
			
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// current
		setCurrent(new FloatDataPoint("current") {
			
			Float dataPointValue = (float) 0;
			
			@Override
			public void doSetValue(Float value) throws DataPointException {
				dataPointValue = value;
			}
			
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
		
		// frequency
		setFrequency(new FloatDataPoint("frequency") {
			
			Float dataPointValue = (float) 0;
			
			@Override
			public void doSetValue(Float value) throws DataPointException {
				dataPointValue = value;
			}
			
			@Override
			public Float doGetValue() throws DataPointException {
				return dataPointValue;
			}
		});
	}

}
