package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.WaterLevel;
import org.onem2m.home.types.LiquidLevel;
import org.onem2m.sdt.Domain;

public class MockedWaterLevel extends WaterLevel {

	public MockedWaterLevel(String name, Domain domain, LiquidLevel dp) {
		super(name, domain, dp);
	}

}
