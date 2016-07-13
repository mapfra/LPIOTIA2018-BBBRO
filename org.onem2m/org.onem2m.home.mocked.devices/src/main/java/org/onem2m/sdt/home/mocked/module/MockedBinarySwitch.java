package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.actions.Toggle;
import org.onem2m.home.modules.BinarySwitch;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.home.mocked.devices.Activator;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.ActionException;
import org.onem2m.sdt.impl.DataPointException;

public class MockedBinarySwitch extends BinarySwitch {

	public MockedBinarySwitch(String name, Domain domain, BooleanDataPoint powerState) {
		super(name, domain, powerState);
		
		setToggle(new Toggle("toggle") {
			@Override
			protected void doToggle() throws ActionException {
				Activator.logger.info("Toggle binary switch");
				try {
					setPowerState(! getPowerState());
				} catch (DataPointException e) {
					throw new ActionException(e);
				} catch (AccessException e) {
					throw new ActionException(e);
				}
			}
		});
	}

}
