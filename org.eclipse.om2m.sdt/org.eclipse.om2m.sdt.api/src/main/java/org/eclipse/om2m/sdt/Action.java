/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.sdt.types.DataType;

public class Action extends Element {
	
	private boolean optional;

	private Map<String, Arg> args;
	
	private DataType type;

	private final String definition;
	private final String longDefinitionName;
	private final String shortDefinitionName;
	
	private Device owner;

	private Module parent;

	public Action(final String id, final Identifiers identifiers) {
		super(identifiers.getDefinition() + "__" + id);
		optional = false;
		this.args = new HashMap<String, Arg>();
		this.definition = identifiers.getDefinition();
		this.longDefinitionName = identifiers.getLongName();
		this.shortDefinitionName = identifiers.getShortName();
	}
	
	public String getDefinition() {
		return definition;
	}
	
	/**
	 * @return the longDefinitionName
	 */
	public String getLongDefinitionName() {
		return longDefinitionName;
	}

	/**
	 * @return the shortDefinitionName
	 */
	public String getShortDefinitionName() {
		return shortDefinitionName;
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

	void setParent(Module parent) {
		this.parent = parent;
	}

	public Module getParent() {
		return parent;
	}

}
