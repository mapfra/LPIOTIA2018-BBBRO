/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.cloud.modules;

import java.util.Map;

import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.FloatDataPoint;
import org.onem2m.sdt.datapoints.IntegerDataPoint;

public class EnergyConsumption extends org.onem2m.home.modules.EnergyConsumption {
	
	public EnergyConsumption(final String name, final Domain domain, Map<String, DataPoint> dps) {
		super(name, domain, (FloatDataPoint) dps.get("power"));
		
		IntegerDataPoint roundingEnergyConsumption = (IntegerDataPoint) dps.get("roundingEnergyConsumption");
		if (roundingEnergyConsumption != null)
			setRoundingEnergyConsumption(roundingEnergyConsumption);
		IntegerDataPoint significantDigits = (IntegerDataPoint) dps.get("significantDigits");
		if (significantDigits != null)
			setSignificantDigits(significantDigits);
		IntegerDataPoint multiplyingFactors = (IntegerDataPoint) dps.get("multiplyingFactors");
		if (multiplyingFactors != null)
			setMultiplyingFactors(multiplyingFactors);
		FloatDataPoint absoluteEnergyConsumption = (FloatDataPoint) dps.get("absoluteEnergyConsumption");
		if (absoluteEnergyConsumption != null)
			setAbsoluteEnergyConsumption(absoluteEnergyConsumption);
		FloatDataPoint voltage = (FloatDataPoint) dps.get("voltage");
		if (voltage != null)
			setVoltage(voltage);
		FloatDataPoint current = (FloatDataPoint) dps.get("current");
		if (current != null)
			setCurrent(current);
		FloatDataPoint frequency = (FloatDataPoint) dps.get("frequency");
		if (frequency != null)
			setFrequency(frequency);
	}

}
