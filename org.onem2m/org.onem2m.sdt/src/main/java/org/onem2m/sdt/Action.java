package org.onem2m.sdt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.onem2m.sdt.types.DataType;

public class Action extends Element {
	
	private boolean optional;

	private Map<String, Arg> args;
	
	private DataType type;

	private String definition;

	private Device owner;
	

	public Action(final String name, final String definition) {
		super(definition + "__" + name);
		optional = false;
		this.args = new HashMap<String, Arg>();
		this.definition = definition;
	}
	
	public String getDefinition() {
		return definition;
	}

	public DataType getDataType() {
		return type;
	}

	public void setDataType(final DataType type) {
		this.type = type;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(final boolean optional) {
		this.optional = optional;
	}

	public Collection<String> getArgNames() {
		return args.keySet();
	}

	public Collection<Arg> getArgs() {
		return args.values();
	}

	public Arg getArg(final String name) {
		return args.get(name);
	}

	public void addArg(final Arg arg) {
		this.args.put(arg.getName(), arg);
	}

	public void removeArg(final String name) {
		this.args.remove(name);
	}

	@Override
	protected String prettyPrint(String t1) {
		String t2 = t1 + "\t";
		StringBuffer ret = new StringBuffer(t1).append("<Action name=\"")
				.append(getName()).append("\">");
		if (getDoc() != null) ret.append("\n").append(t2).append(getDoc());
		if (type != null) ret.append("\n").append(type.prettyPrint(t2));
		prettyPrint(ret, args.values(), "Args", t2);
		return ret.append("\n").append(t1).append("</Action>").toString();
	}
	
	void setOwner(Device owner) {
		this.owner = owner;
	}

	public Device getOwner() {
		return owner;
	}

}
