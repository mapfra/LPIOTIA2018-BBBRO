/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.driver;

import org.osgi.service.log.LogService;

public class Logger {
	
	static private final String PREFIX  = "[ONEM2M.";
	
	static private final String[] LEVELS  = new String[] {
		"ERROR ", "WARNING ", "INFO ", "DEBUG "
	};

	static private LogService logService;
	
	private String protocol;

	
	public Logger(String protocol) {
		this.protocol = protocol;
	}
	
	public final void setLogService(final LogService log) {
		logService = log;
	}

	public final void unsetLogService() {
		logService = null;
	}

	public final void debug(final String message) {
		print(LogService.LOG_DEBUG, null, message);
	}

	public final void debug(final String message, final Class<?> clazz) {
		print(LogService.LOG_DEBUG, clazz, message);
	}

	public final void info(final String message) {
		print(LogService.LOG_INFO, null, message);
	}

	public final void info(final String message, final Class<?> clazz) {
		print(LogService.LOG_INFO, clazz, message);
	}

	public final void warning(final String message) {
		print(LogService.LOG_WARNING, null, message);
	}

	public final void warning(final String message, final Throwable e) {
		print(LogService.LOG_WARNING, null, message, e);
	}

	public final void warning(final String message, final Class<?> clazz) {
		print(LogService.LOG_WARNING, clazz, message);
	}

	public final void warning(final String message, final Class<?> clazz, final Throwable e) {
		print(LogService.LOG_WARNING, clazz, message, e);
	}

	public final void error(final String message) {
		print(LogService.LOG_ERROR, null, message);
	}

	public final void error(final String message, final Throwable e) {
		print(LogService.LOG_ERROR, null, message, e);
	}

	public final void error(final String message, final Class<?> clazz) {
		print(LogService.LOG_ERROR, clazz, message);
	}

	public final void error(final String message, final Class<?> clazz, final Throwable e) {
		print(LogService.LOG_ERROR, clazz, message, e);
	}

	private final void print(final int level, final Class<?> clazz, final String message) {
		String msg = PREFIX + protocol + ((clazz == null) ? "] " : "." + clazz.getSimpleName() + "] ") + message;
		if (logService != null)
			logService.log(level, msg);
		else
			System.out.println(LEVELS[level-1] + msg);
	}

	private final void print(final int level, final Class<?> clazz, final String message, final Throwable e) {
		String msg = PREFIX + protocol + ((clazz == null) ? "] " : "." + clazz.getSimpleName() + "] ") + message;
		if (logService != null)
			logService.log(level, msg, e);
		else
			System.out.println(LEVELS[level-1] + msg + ": " + e.getMessage());
		if (e != null)
			e.printStackTrace();
	}

}
