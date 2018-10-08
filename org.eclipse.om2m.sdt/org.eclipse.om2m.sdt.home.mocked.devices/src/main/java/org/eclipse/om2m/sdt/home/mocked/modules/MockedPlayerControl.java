package org.eclipse.om2m.sdt.home.mocked.modules;

import java.util.Arrays;
import java.util.List;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.ArrayDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.PlayerControl;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.PlayerMode;

public class MockedPlayerControl extends PlayerControl {
	
	static private List<PlayerMode.Values> supportedPlayerModes = 
			Arrays.asList(PlayerMode.Values.values());

	static private PlayerMode.Values currentPlayerMode = 
			PlayerMode.Values.values()[(int)(Math.random() * supportedPlayerModes.size())];
	
	public MockedPlayerControl(final String name, final Domain domain) {
		super(name, domain, 
			new PlayerMode() {
				@Override
				protected PlayerMode.Values doGetValue() throws DataPointException {
					return currentPlayerMode;
				}
				@Override
				protected void doSetValue(PlayerMode.Values v) throws DataPointException {
					currentPlayerMode = v;
				}
			},
			new ArrayDataPoint<PlayerMode.Values>(DatapointType.supportedPlayerModes) {
				@Override
				public List<PlayerMode.Values> doGetValue() throws DataPointException {
					return supportedPlayerModes;
				}
			});
	}

}
