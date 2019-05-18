/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.utils;

import org.osgi.service.log.LogService;

public class Logger {
	
	static private final String PREFIX  = "[ONEM2M.SDT";
	
	static private final String[] LEVELS  = new String[] {
		"ERROR ", "WARNING ", "INFO ", "DEBUG "
	};

	static private LogService logService;
	
	private Logger() {}
	
	static public void setLogService(final LogService log) {
		logService = log;
	}

	static public void unsetLogService() {
		logService = null;
	}

	static public void debug(final String message) {
		print(LogService.LOG_DEBUG, null, message);
	}

	static public void debug(final String message, Class clazz) {
		print(LogService.LOG_DEBUG, clazz, message);
	}

	static public void info(final String message) {
		print(LogService.LOG_INFO, null, message);
	}

	static public void info(final String message, Class clazz) {
		print(LogService.LOG_INFO, clazz, message);
	}

	static public void warning(final String message) {
		print(LogService.LOG_WARNING, null, message);
	}

	static public void warning(final String message, final Exception e) {
		print(LogService.LOG_WARNING, null, message, e);
	}

	static public void warning(final String message, Class clazz) {
		print(LogService.LOG_WARNING, clazz, message);
	}

	static public void warning(final String message, Class clazz, final Exception e) {
		print(LogService.LOG_WARNING, clazz, message, e);
	}

	static public void error(final String message) {
		print(LogService.LOG_ERROR, null, message);
	}

	static public void error(final String message, final Exception e) {
		print(LogService.LOG_ERROR, null, message, e);
	}

	static public void error(final String message, Class clazz) {
		print(LogService.LOG_ERROR, clazz, message);
	}

	static public void error(final String message, Class clazz, final Exception e) {
		print(LogService.LOG_ERROR, clazz, message, e);
	}

	static private void print(int level, Class clazz, final String message) {
		String msg = PREFIX + ((clazz == null) ? "] " : "." + clazz.getSimpleName() + "] ") + message;
		if (logService != null)
			logService.log(level, msg);
		else
			System.out.println(LEVELS[level-1] + msg);
	}

	static private void print(int level, Class clazz, final String message, final Exception e) {
		String msg = PREFIX + ((clazz == null) ? "] " : "." + clazz.getSimpleName() + "] ") + message;
		if (logService != null)
			logService.log(level, msg, e);
		else
			System.out.println(LEVELS[level-1] + msg + ": " + e.getMessage());
		if (e != null)
			e.printStackTrace();
	}

}
