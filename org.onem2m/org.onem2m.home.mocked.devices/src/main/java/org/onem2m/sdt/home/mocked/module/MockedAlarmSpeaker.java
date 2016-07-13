package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.AlarmSpeaker;
import org.onem2m.home.types.Tone;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.datapoints.BooleanDataPoint;
import org.onem2m.sdt.impl.DataPointException;

public class MockedAlarmSpeaker extends AlarmSpeaker {
	
	private Integer tone = Tone.Silent;

	public MockedAlarmSpeaker(String name, Domain domain, BooleanDataPoint alarmStatus) {
		super(name, domain, alarmStatus);
		
		// tone
		setTone(new Tone("tone") {
			@Override
			public void doSetValue(Integer value) throws DataPointException {
				tone = value;
				System.out.println("tone " + tone);
			}
			@Override
			public Integer doGetValue() throws DataPointException {
				return tone;
			}
		});
	}

}
