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

public abstract class UpVolume extends Command {
	
//	private BooleanArg upArg;

	public UpVolume(String name) {
		super(name, ActionType.upVolume);
		setDoc("Increase volume by the amount of the stepValue upto the maxValue");
//		upArg = new BooleanArg("up");
//		addArg(upArg);
	}

//	public final void upOrDown(final boolean up) throws ActionException, AccessException {
//		upArg.setValue(up);
//		invoke();
//	}
//	
//	abstract protected void doUpOrDown(final boolean up) throws ActionException;
//	
//	@Override
//	protected Object doInvoke() throws ActionException {
//		doUpOrDown(upArg.getValue());
//		return null;
//	}

	public final void upVolume() throws ActionException, AccessException {
		invoke();
	}
	
	abstract protected void doUpVolume() throws ActionException;
	
	@Override
	protected Object doInvoke() throws ActionException {
		doUpVolume();
		return null;
	}

}
