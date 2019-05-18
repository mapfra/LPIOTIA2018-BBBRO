/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.ipe.sdt;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.onem2m.sdt.types.SimpleType;

public class SDTUtilTest {

	@Test
	public void test() {
		assertTrue(SimpleType.Boolean.getOneM2MType().equals("xs:boolean"));
		assertTrue(SimpleType.Integer.getOneM2MType().equals("xs:integer"));
		assertTrue(SimpleType.String.getOneM2MType().equals("xs:string"));
	}

}
