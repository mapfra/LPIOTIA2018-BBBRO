/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.datapoints;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.om2m.sdt.Identifiers;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.types.DataType;

public abstract class UriDataPoint extends ValuedDataPoint<URI> {

	public UriDataPoint(Identifiers name) {
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
