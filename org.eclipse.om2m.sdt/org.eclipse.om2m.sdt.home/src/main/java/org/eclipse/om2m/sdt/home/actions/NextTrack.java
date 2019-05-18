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

public abstract class NextTrack extends Command {

	public NextTrack(String name) {
		super(name, ActionType.nextTrack);
		setDoc("Go forward to a next chapter, section or similar marker in the media.");
	}

	public final void nextTrack() throws ActionException, AccessException {
		invoke();
	}
	
	abstract protected void doNextTrack() throws ActionException;
	
	@Override
	protected Object doInvoke() throws ActionException {
		doNextTrack();
		return null;
	}

}
