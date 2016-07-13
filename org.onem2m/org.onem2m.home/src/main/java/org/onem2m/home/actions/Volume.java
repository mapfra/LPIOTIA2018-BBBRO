package org.onem2m.home.actions;

import java.util.Map;

import org.onem2m.sdt.args.BooleanArg;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.ActionException;
import org.onem2m.sdt.impl.Command;

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
		check();
		doUpOrDown(upArg.getValue());
	}
	
	abstract protected void doUpOrDown(final boolean up) throws ActionException;
	
	@Override
	public Object invoke(Map<String, Object> arguments) throws ActionException, AccessException {
		
		upOrDown((boolean) arguments.get("up"));

		return null;
	}

}
