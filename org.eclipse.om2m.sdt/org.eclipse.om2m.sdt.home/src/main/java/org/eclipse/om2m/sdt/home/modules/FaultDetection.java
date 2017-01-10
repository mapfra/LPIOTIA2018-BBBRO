/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.BooleanDataPoint;
import org.eclipse.om2m.sdt.datapoints.IntegerDataPoint;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.home.types.ModuleType;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.DataPointException;

public class FaultDetection extends Module {
	
	private BooleanDataPoint status;
	private IntegerDataPoint code;
	private StringDataPoint description;

	public FaultDetection(final String name, final Domain domain, BooleanDataPoint status) {
		super(name, domain, ModuleType.faultDetection.getDefinition());
		
		this.status = status;
		this.status.setWritable(false);
		this.status.setDoc("Status of fault detection");
		addDataPoint(this.status);
	}
	
	public FaultDetection(final String name, final Domain domain, BooleanDataPoint status, IntegerDataPoint code, StringDataPoint description) {
		super(name, domain, ModuleType.faultDetection.getDefinition());
		
		this.status = status;
		this.status.setWritable(false);
		this.status.setDoc("Status of fault detection");
		addDataPoint(this.status);
		
		if(code!=null){
			this.code = code;
			this.code.setWritable(false);
			this.code.setDoc("Code of the fault.");
			addDataPoint(this.code);
		}
		if(description!= null){
			this.description = description;
			this.description.setWritable(false);
			this.description.setDoc("Message of the fault.");
			addDataPoint(this.description);
		}
	}

	public FaultDetection(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (BooleanDataPoint) dps.get("status"), (IntegerDataPoint)dps.get("code"), (StringDataPoint) dps.get("description"));

		IntegerDataPoint code = (IntegerDataPoint) dps.get("code");
		if (code != null)
			setCode(code);
		StringDataPoint description = (StringDataPoint) dps.get("description");
		if (description != null)
			setDescription(description);
	}

	public boolean getStatus() throws DataPointException, AccessException{
		return status.getValue();
	}

	public int getCode() throws DataPointException, AccessException {
		if (code == null)
			throw new DataPointException("Not implemented");
		return code.getValue();
	}

	public void setCode(IntegerDataPoint dp) {
		this.code = dp;
		this.code.setOptional(true);
		this.code.setWritable(false);
		this.code.setDoc("Code of the fault.");
		addDataPoint(code);
	}

	public String getDescription() throws DataPointException, AccessException {
		if (description == null)
			throw new DataPointException("Not implemented");
		return description.getValue();
	}

	public void setDescription(StringDataPoint dp) {
		this.description = dp;
		this.description.setOptional(true);
		this.description.setWritable(false);
		this.description.setDoc("Message of the fault.");
		addDataPoint(description);
	}

}
