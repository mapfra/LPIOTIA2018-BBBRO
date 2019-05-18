/*******************************************************************************
 * Copyright (c) 2017 Deutsche Telekom.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.actions;

import org.eclipse.om2m.sdt.args.Command;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.ActionException;
import org.eclipse.om2m.sdt.home.types.ActionType;

public abstract class DecrementNumberValue extends Command {

	public DecrementNumberValue(String name) {
		super(name, ActionType.decrementNumberValue);
		setDoc("Decrement the \"numberValue\" by the value of \"step\", down to the value of \"minimum\".");
	}

	public final void decrementNumberValue() throws ActionException, AccessException {
		invoke();
	}
	
	abstract protected void doDecrementNumberValue() throws ActionException;
	
	@Override
	protected Object doInvoke() throws ActionException {
		// toggle does not have any argument
		doDecrementNumberValue();
		return null;
	}

}
