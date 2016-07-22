/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.sdt;

public class Module extends ModuleClass {
	
	private String definition;

	public Module(final String name, final Domain domain, final String definition) {
		super(definition + "__" + name, domain);
		this.definition = definition;
	}
	
	public String getDefinition() {
		return definition;
	}

}
