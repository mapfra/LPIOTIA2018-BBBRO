/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.orange.basedriver.lifx.impl;

import org.osgi.service.log.LogService;

public class Logger {
	
	private LogService logService;
	
	private static final Logger INSTANCE = new Logger();
	
	public static Logger getInstance() {
		return INSTANCE;
	}
	
	public void setLogService(LogService pLogService) {
		logService = pLogService;
	}
	
	public void info(Class clazz, String msg) {
		if (logService != null) {
			logService.log(LogService.LOG_INFO, "[" + clazz.getName() + "] " + msg);
		}
	}
	
}
