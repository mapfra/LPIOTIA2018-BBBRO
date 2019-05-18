/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.sdt.datapoints.ValuedDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;

public class Module extends ModuleClass {
	
	static public interface DatapointHandler {
		void setValues(Map<String, Object> values) throws DataPointException, AccessException;
		public Map<String, Object> getValues(List<String> names) throws DataPointException, AccessException;
	}
	
	private final String definition;
	private final String longDefinitionName;
	private final String shortDefinitionName;
	
	private DatapointHandler handler = new DatapointHandler() {
		@Override
		public void setValues(Map<String, Object> values)
				throws DataPointException, AccessException {
			Module.this.dosetValues(values);
		}
		@Override
		public Map<String, Object> getValues(List<String> names)
				throws DataPointException, AccessException {
			return Module.this.dogetValues(names);
		}
	};

	public Module(final String id, final Domain domain, final Identifiers ids) {
		super(ids.getDefinition() + "__" + id, domain);
		this.definition = ids.getDefinition();
		this.longDefinitionName = ids.getLongName();
		this.shortDefinitionName = ids.getShortName();
	}
	
	public String getDefinition() {
		return definition;
	}

	/**
	 * @return the longDefinitionName
	 */
	public String getLongDefinitionName() {
		return longDefinitionName;
	}

	/**
	 * @return the shortDefinitionName
	 */
	public String getShortDefinitionName() {
		return shortDefinitionName;
	}
	
	public void setDatapointHandler(DatapointHandler handler) {
		this.handler = handler;
	}
	
	public DatapointHandler getDatapointHandler() {
		return handler;
	}
	
	@SuppressWarnings("unchecked")
	private void dosetValues(Map<String, Object> values) throws DataPointException, AccessException {
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			DataPoint dp = getDataPointByShortName(entry.getKey());
			if (dp != null) { // Ignore unknown datapoints
				((ValuedDataPoint<Object>)dp).setValue(entry.getValue());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> dogetValues(List<String> names) throws DataPointException, AccessException {
		Map<String, Object> ret = new HashMap<String, Object>();
		for (String name : names) {
			DataPoint dp = getDataPointByShortName(name);
			if (dp != null) { // Ignore unknown datapoints
				ret.put(name, ((ValuedDataPoint<Object>)dp).getValue());
			}
		}
		return ret;
	}
	
}
