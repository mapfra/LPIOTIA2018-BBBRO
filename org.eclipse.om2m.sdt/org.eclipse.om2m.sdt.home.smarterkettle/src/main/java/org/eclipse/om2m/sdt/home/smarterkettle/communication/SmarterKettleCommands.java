/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.smarterkettle.communication;

public interface SmarterKettleCommands {
	
	byte START_KETTLE = 21; //START KETTLE
	byte STOP_KETTLE = 22; //STOP KETTLE
	byte SHEDULE_TEST = 65;
	byte END_OF_MESSAGE = 126; //ENDING MESSAGE
	
}
