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

public class Credentials extends Module {
	
	private StringDataPoint loginName;
	private StringDataPoint password;
	private StringDataPoint token;
	
	
	public Credentials(final String name, final Domain domain) {
		super(name, domain, ModuleType.credentials);
	}
	
	public Credentials(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain);
		StringDataPoint dp = (StringDataPoint) dps.get(DatapointType.loginName.getShortName());
		if (dp != null)
			setLoginName(dp);
		dp = (StringDataPoint) dps.get(DatapointType.password.getShortName());
		if (dp != null)
			setPassword(dp);
		dp = (StringDataPoint) dps.get(DatapointType.token.getShortName());
		if (dp != null)
			setToken(dp);
	}

	public void setLoginName(StringDataPoint dp) {
		this.loginName = dp;
		this.loginName.setOptional(true);
		this.loginName.setReadable(false);
		this.loginName.setWritable(true);
		this.loginName.setDoc("The user’s login name.");
		addDataPoint(loginName);
	}

	public void setLoginName(String s) throws DataPointException, AccessException {
		if (loginName == null)
			throw new DataPointException("Not implemented");
		loginName.setValue(s);
	}

	public void setPassword(StringDataPoint dp) {
		this.password = dp;
		this.password.setOptional(true);
		this.password.setReadable(false);
		this.password.setWritable(true);
		this.password.setDoc("The user’s password.");
		addDataPoint(password);
	}

	public void setPassword(String s) throws DataPointException, AccessException {
		if (password == null)
			throw new DataPointException("Not implemented");
		password.setValue(s);
	}

	public void setToken(StringDataPoint dp) {
		this.token = dp;
		this.token.setOptional(true);
		this.token.setReadable(false);
		this.token.setWritable(true);
		this.token.setDoc("An authentication token, for example an OAuth token.");
		addDataPoint(token);
	}

	public void setToken(String s) throws DataPointException, AccessException {
		if (token == null)
			throw new DataPointException("Not implemented");
		token.setValue(s);
	}

}
