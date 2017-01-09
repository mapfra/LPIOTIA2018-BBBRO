/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.smartercoffee.communication;

import org.onem2m.home.types.TasteStrength;

import junit.framework.TestCase;

public class SmartCoffeeCommunicationTest extends TestCase {

	public void testStartBrewing() {
		System.out.println("test");
		
		SmarterCoffeeCommunication smarterCoffeeCommunication = new SmarterCoffeeCommunication(null, 0);
		smarterCoffeeCommunication.getStatus();
		
		System.out.println("waterStatus=" + smarterCoffeeCommunication.getWaterStatus());
		
		
//		smarterCoffeeCommunication.start(true, 1, TasteStrength.maximum, true);
	}
}
