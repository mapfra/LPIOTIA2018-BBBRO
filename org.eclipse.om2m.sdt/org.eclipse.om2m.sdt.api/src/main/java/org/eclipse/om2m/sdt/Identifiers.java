/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

import org.eclipse.om2m.sdt.types.DataType;

public interface Identifiers {
	
	String getDefinition();
	String getShortName();
	String getLongName();
	
	public interface Typed extends Identifiers {
		
		DataType getDataType();
		
	}

}
