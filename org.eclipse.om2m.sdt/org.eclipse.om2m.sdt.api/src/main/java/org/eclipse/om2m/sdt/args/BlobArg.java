/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.args;

import org.eclipse.om2m.sdt.types.DataType;

public class BlobArg extends ValuedArg<byte[]> {

	public BlobArg(String name) {
		super(name, DataType.Blob);
	}

//	public void setValue(String v) {
//		setValue(Base64.decodeBase64(v));
//	}
//	
//	public void setValue(byte[] v) {
//		setValue(Base64.encodeBase64(v));
//	}

}
