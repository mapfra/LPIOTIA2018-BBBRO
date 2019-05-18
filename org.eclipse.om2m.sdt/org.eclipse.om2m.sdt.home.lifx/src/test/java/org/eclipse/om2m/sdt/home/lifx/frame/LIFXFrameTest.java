/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.lifx.frame;

import org.eclipse.om2m.sdt.home.lifx.impl.lan.frame.LIFXFrame;

import junit.framework.TestCase;

public class LIFXFrameTest extends TestCase {

	public void testFrame() throws Exception {
		LIFXFrame frame = new LIFXFrame();
		frame.setTagged(true);
		frame.setPayloadSize(12);
		frame.setSource(124);
		
		byte[] frameBytes =  frame.getBytes();
		assertTrue(frameBytes.length == 8);
		
		for(byte b : frameBytes) {
			System.out.println(b);
		}
		System.out.println(frameBytes.toString());
	}
	
}
