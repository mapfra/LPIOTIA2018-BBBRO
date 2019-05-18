/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.net.URI;
import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.datapoints.UriDataPoint;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.DatapointType;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class SessionDescription extends Module {
	
	private UriDataPoint url;
	private StringDataPoint sdp;

	public SessionDescription(final String name, final Domain domain) {
		super(name, domain, ModuleType.sessionDescription);
	}
	
	public SessionDescription(final String name, final Domain domain, Map<String, DataPoint> dps) {
		this(name, domain);
		UriDataPoint url = (UriDataPoint) dps.get(DatapointType.url.getShortName());
		if (url != null)
			setUrl(url);
		StringDataPoint sdp = (StringDataPoint) dps.get(DatapointType.sdp.getShortName());
		if (sdp != null)
			setSdp(sdp);
	}
	
	public void setUrl(UriDataPoint dp) {
		this.url = dp;
		url.setOptional(true);
		url.setWritable(false);
		url.setDoc("A URL at which the specified media can be accessed.");
		addDataPoint(url);
	}

	public URI getUrl() throws DataPointException, AccessException {
		if (url == null)
			throw new DataPointException("Not implemented");
		return url.getValue();
	}

	public void setSdp(StringDataPoint dp) {
		sdp = dp;
		sdp.setOptional(true);
		sdp.setWritable(false);
		sdp.setDoc("Media description using SDP. One or more comma separated multiple SDP lines (SDP media or attribute line) can be included using SDP description syntax as defined in the SDP specification in RFC4566.");
		addDataPoint(sdp);
	}
	
	public String getSdp() throws DataPointException, AccessException {
		if (sdp == null)
			throw new DataPointException("Not implemented");
		return sdp.getValue();
	}

}
