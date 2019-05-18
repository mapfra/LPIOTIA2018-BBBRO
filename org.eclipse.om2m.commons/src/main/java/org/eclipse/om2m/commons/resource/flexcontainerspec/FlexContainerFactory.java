/*
********************************************************************************
 * Copyright (c) 2014, 2018 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ********************************************************************************

FlexContainerFactory : FlexContainerFactory

Created: 2018-06-29 17:19:56
*/

package org.eclipse.om2m.commons.resource.flexcontainerspec;

import org.eclipse.om2m.commons.resource.AbstractFlexContainer;
import org.eclipse.om2m.commons.resource.AbstractFlexContainerAnnc;
import org.eclipse.om2m.commons.resource.FlexContainer;
import org.eclipse.om2m.commons.resource.FlexContainerAnnc;

public class FlexContainerFactory {

	static private final String PREFIX = "create";
	
	static private final ObjectFactory factory = ObjectFactory.getInstance();

	public static AbstractFlexContainer getSpecializationFlexContainer(String shortName) {
		try {
			String method = PREFIX + Character.toUpperCase(shortName.charAt(0)) + shortName.substring(1);
			return (AbstractFlexContainer) invoke(method);
		} catch (Exception e) {
			try {
				return (AbstractFlexContainer) invoke(PREFIX + shortName);
			} catch (Exception e2) {
				return new FlexContainer();
			}
		}
	}

	public static AbstractFlexContainerAnnc getSpecializationFlexContainerAnnc(String shortName) {
		try {
			String method = PREFIX + Character.toUpperCase(shortName.charAt(0)) + shortName.substring(1);
			return (AbstractFlexContainerAnnc) invoke(method);
		} catch (Exception e) {
			try {
				return (AbstractFlexContainerAnnc) invoke(PREFIX + shortName);
			} catch (Exception e2) {
				return new FlexContainerAnnc();
			}
		}
	}
	
	private static final Object invoke(final String method) throws Exception {
		return ObjectFactory.class.getMethod(method, new Class[0])
				.invoke(factory, new Object[0]);
	}

}
