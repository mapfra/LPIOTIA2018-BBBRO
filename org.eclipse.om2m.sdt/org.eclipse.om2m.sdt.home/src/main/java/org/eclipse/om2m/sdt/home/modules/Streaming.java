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
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Streaming extends Module {

	private final StringDataPoint url;
	private final StringDataPoint login;
	private final StringDataPoint password;
	private final StringDataPoint format;

	public Streaming(String name, Domain domain, 
			final StringDataPoint url, final StringDataPoint login, 
			final StringDataPoint password, final StringDataPoint format) {
		super(name, domain, ModuleType.streaming);
		if ((url == null) ||
				! url.getShortDefinitionType().equals(DatapointType.url.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong url datapoint: " + url);
		}
		this.url = url;
		this.url.setWritable(false);
		addDataPoint(this.url);
		
		if ((login == null) ||
				! login.getShortDefinitionType().equals(DatapointType.login.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong login datapoint: " + login);
		}
		this.login = login;
		this.login.setWritable(false);
		addDataPoint(this.login);
		
		if ((password == null) ||
				! password.getShortDefinitionType().equals(DatapointType.password.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong password datapoint: " + password);
		}
		this.password = password;
		this.password.setWritable(false);
		addDataPoint(this.password);
		
		if ((format == null) ||
				! format.getShortDefinitionType().equals(DatapointType.format.getShortName())) {
			domain.removeModule(getName());
			throw new IllegalArgumentException("Wrong format datapoint: " + format);
		}
		this.format = format;
		this.format.setWritable(false);
		addDataPoint(this.format);
	}

	public Streaming(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain, (StringDataPoint) dps.get(DatapointType.url.getShortName()),
				(StringDataPoint) dps.get(DatapointType.login.getShortName()),
				(StringDataPoint) dps.get(DatapointType.password.getShortName()),
				(StringDataPoint) dps.get(DatapointType.format.getShortName()));
	}
	
	public String getUrlValue() throws DataPointException, AccessException {
		return url.getValue();
	}
	
	public String getLoginValue() throws DataPointException, AccessException {
		return login.getValue();
	}
	
	public String getPasswordValue() throws DataPointException, AccessException {
		return password.getValue();
	}
	
	public String getFormatValue()  throws DataPointException, AccessException {
		return format.getValue();
	}

}
