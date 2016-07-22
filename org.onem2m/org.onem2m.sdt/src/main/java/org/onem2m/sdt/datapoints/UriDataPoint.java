/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.datapoints;

import java.net.URI;
import java.net.URISyntaxException;

import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;
import org.onem2m.sdt.types.DataType;

public abstract class UriDataPoint extends ValuedDataPoint<URI> {

	public UriDataPoint(String name) {
		super(name, DataType.Uri);
	}
	
	public void setValue(String v) throws DataPointException, AccessException {
		try {
			setValue(new URI(v));
		} catch (URISyntaxException e) {
			throw new DataPointException("Incorrect value", e);
		}
	}

}
