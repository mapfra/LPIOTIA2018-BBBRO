/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.actions;

import org.eclipse.om2m.sdt.args.BooleanArg;
import org.eclipse.om2m.sdt.impl.AccessException;
import org.eclipse.om2m.sdt.impl.ActionException;
import org.eclipse.om2m.sdt.impl.Command;

public abstract class Volume extends Command {
	
	private BooleanArg upArg;

	public Volume(String name) {
		super(name, "org.onem2m.home.actions.volume");
		setDoc("Increase/Decrease volume by the amount of the stepValue upto the maxValue");
		upArg = new BooleanArg("up");
		addArg(upArg);
	}

	public final void upOrDown(final boolean up) throws ActionException, AccessException {
		upArg.setValue(up);
		invoke();
	}
	
	abstract protected void doUpOrDown(final boolean up) throws ActionException;
	
	@Override
	protected Object doInvoke() throws ActionException {
		doUpOrDown(upArg.getValue());
		return null;
	}

}
