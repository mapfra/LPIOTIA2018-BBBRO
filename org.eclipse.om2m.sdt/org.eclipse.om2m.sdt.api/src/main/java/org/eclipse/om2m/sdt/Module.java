/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt;

public class Module extends ModuleClass {
	
	private final String definition;
	private final String longDefinitionName;
	private final String shortDefinitionName;

	public Module(final String id, final Domain domain, final Identifiers ids) {
		super(ids.getDefinition() + "__" + id, domain);
		this.definition = ids.getDefinition();
		this.longDefinitionName = ids.getLongName();
		this.shortDefinitionName = ids.getShortName();
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
	
}
