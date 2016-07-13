package org.onem2m.home.modules;

import java.util.Date;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.datapoints.DateDataPoint;
import org.onem2m.sdt.datapoints.TimeDataPoint;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class Clock extends Module {
	
	private TimeDataPoint currentTime;
	private DateDataPoint currentDate;

	public Clock(final String name, final Domain domain, TimeDataPoint currentTime, DateDataPoint currentDate) {
		super(name, domain, ModuleType.clock.getDefinition());
		
		this.currentDate = currentDate;
		currentDate.setDoc("Information of the current date");
		addDataPoint(currentDate);
		
		this.currentTime = currentTime;
		currentTime.setDoc("Information of the current time");
		addDataPoint(currentTime);
	}

	public Date getCurrentTime() throws DataPointException, AccessException {
		return currentTime.getValue();
	}

	public String getStringCurrentTime() throws DataPointException, AccessException {
		return currentTime.getStringValue();
	}

	public void setCurrentTime(Date value) throws DataPointException, AccessException {
		currentTime.setValue(value);
	}

	public Date getCurrentDate() throws DataPointException, AccessException {
		return currentDate.getValue();
	}

	public String getStringCurrentDate() throws DataPointException, AccessException {
		return currentDate.getStringValue();
	}

	public void setCurrentDate(Date value) throws DataPointException, AccessException {
		currentDate.setValue(value);
	}

}
