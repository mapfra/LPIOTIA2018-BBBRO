package org.onem2m.home.actions;

import java.util.Map;

import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.ActionException;
import org.onem2m.sdt.impl.Command;

public abstract class Toggle extends Command {

	public Toggle(String name) {
		super(name, "org.onem2m.home.actions.toggle");
		setDoc("Toggle the switch.");
	}

	public final void toggle() throws ActionException, AccessException {
		check();
		doToggle();
	}
	
	abstract protected void doToggle() throws ActionException;
	
	@Override
	public Object invoke(Map<String, Object> arguments) throws ActionException, AccessException {
		// toggle does not have any argument
		toggle();
		
		return null;
	}

}
