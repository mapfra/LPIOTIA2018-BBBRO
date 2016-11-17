/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt.home.mocked.module;

import org.onem2m.home.modules.Lock;
import org.onem2m.home.types.LockState;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.impl.DataPointException;

public class MockedLock extends Lock {
	
	public MockedLock(String name, Domain domain) {
		super(name, domain,
			new LockState("lockState") {
				private int state = LockState.Locked;
				@Override
				public Integer doGetValue() throws DataPointException {
					return state;
				}
				@Override
				public void doSetValue(Integer v) throws DataPointException {
					state = v;
				}
			}
		);
	}

}
