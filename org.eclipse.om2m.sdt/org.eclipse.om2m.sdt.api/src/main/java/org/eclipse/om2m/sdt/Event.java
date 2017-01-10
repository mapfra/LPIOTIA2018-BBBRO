/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Event extends Element {
	
	private boolean optional;
	
	private long timestamp;
	
	private Map<String, DataPoint> dataPoints;
	
	private Object value;

	public Event(final String name) {
		super(name);
		optional = false;
		dataPoints = new HashMap<String, DataPoint>();
	}
	
	void setTimeStamp() {
		timestamp = System.currentTimeMillis();
	}
	
	public Date getTimeStamp() {
		return new Date(timestamp);
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(final boolean optional) {
		this.optional = optional;
	}

	public Collection<String> getDataPointNames() {
		return dataPoints.keySet();
	}

	public Collection<DataPoint> getDataPoints() {
		return dataPoints.values();
	}

	public DataPoint getDataPoint(final String name) {
		return dataPoints.get(name);
	}

	public void addDataPoint(final DataPoint dataPoint) {
		if (dataPoints.get(dataPoint.getName()) != null)
			throw new IllegalArgumentException();
		dataPoints.put(dataPoint.getName(), dataPoint);
	}

	public void removeDataPoint(final String name) {
		dataPoints.remove(name);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	protected String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer(t1)
			.append("<Event name=\"").append(getName())
			.append("\" timestamp=\"").append(getTimeStamp())
			.append("\">");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		prettyPrint(ret, dataPoints.values(), "Data", t2);
		return ret.append("\n").append(t1).append("</Event>").toString();
	}

}
