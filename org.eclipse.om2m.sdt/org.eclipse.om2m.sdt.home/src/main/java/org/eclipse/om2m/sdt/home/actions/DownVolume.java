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

public abstract class DownVolume extends Command {
	
	public DownVolume(String name) {
		super(name, ActionType.downVolume);
		setDoc("Decrease volume by the amount of the stepValue upto 0");
	}

	public final void downVolume() throws AccessException, ActionException {
		invoke();
	}
	
	abstract protected void doDownVolume() throws ActionException;
	
	@Override
	protected Object doInvoke() throws ActionException {
		doDownVolume();
		return null;
	}

}
