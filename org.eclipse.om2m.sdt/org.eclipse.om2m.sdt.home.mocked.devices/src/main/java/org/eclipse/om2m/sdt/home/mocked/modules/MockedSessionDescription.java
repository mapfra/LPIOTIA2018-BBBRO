/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.mocked.modules;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.datapoints.StringDataPoint;
import org.eclipse.om2m.sdt.datapoints.UriDataPoint;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.modules.SessionDescription;
import org.eclipse.om2m.sdt.home.types.DatapointType;

public class MockedSessionDescription extends SessionDescription {

	public MockedSessionDescription(String name, Domain domain) {
		super(name, domain);
		setUrl(new UriDataPoint(DatapointType.url) {
			@Override
			protected URI doGetValue() throws DataPointException {
				try {
					return new URI("https://xxx.yyy.zzz");
				} catch (URISyntaxException e) {
					throw new DataPointException(e);
				}
			}
		});
		setSdp(new StringDataPoint(DatapointType.sdp) {
			@Override
			protected String doGetValue() throws DataPointException {
				return "HLS";
			}
		});
	}

}
