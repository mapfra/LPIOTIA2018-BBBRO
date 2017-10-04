/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
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

public abstract class Toggle extends Command {

	public Toggle(String name) {
		super(name, ActionType.toggle);
		setDoc("Toggle the switch.");
	}

	public final void toggle() throws ActionException, AccessException {
		invoke();
	}
	
	abstract protected void doToggle() throws ActionException;
	
	@Override
	protected Object doInvoke() throws ActionException {
		// toggle does not have any argument
		doToggle();
		return null;
	}

}
