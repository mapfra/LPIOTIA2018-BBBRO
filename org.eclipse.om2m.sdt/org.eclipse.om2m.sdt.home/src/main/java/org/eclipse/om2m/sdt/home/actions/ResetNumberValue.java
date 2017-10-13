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

public abstract class ResetNumberValue extends Command {

	public ResetNumberValue(String name) {
		super(name, ActionType.resetNumberValue);
		setDoc("Reset the \"numberValue\" to its \"defaultValue\".");
	}

	public final void resetNumberValue() throws ActionException, AccessException {
		invoke();
	}
	
	abstract protected void doResetNumberValue() throws ActionException;
	
	@Override
	protected Object doInvoke() throws ActionException {
		// toggle does not have any argument
		doResetNumberValue();
		return null;
	}

}
