/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.tester;

import org.osgi.service.log.LogService;

public class Logger {
	
	static private final String PREFIX  = "[ONEM2M.TEST] ";

	static private LogService logService;
	
	private Logger() {}
	
	static public void setLogService(final LogService log) {
		logService = log;
	}

	static public void unsetLogService() {
		logService = null;
	}

	static public void debug(final String message) {
		if (logService != null)
			logService.log(LogService.LOG_DEBUG, PREFIX + message);
		else
			System.out.println(PREFIX + "DEBUG " + message);
	}

	static public void info(final String message) {
		if (logService != null)
			logService.log(LogService.LOG_INFO, PREFIX + message);
		else
			System.out.println(PREFIX + "INFO " + message);
	}

	static public void warning(final String message) {
		if (logService != null)
			logService.log(LogService.LOG_WARNING, PREFIX + message);
		else
			System.out.println(PREFIX + "WARNING " + message);
	}

	static public void warning(final String message, final Exception e) {
		if (logService != null)
			logService.log(LogService.LOG_WARNING, PREFIX + message, e);
		else
			System.out.println(PREFIX + "WARNING " + message + ": " + e);
		if (e != null)
			e.printStackTrace();
	}

	static public void error(final String message) {
		if (logService != null)
			logService.log(LogService.LOG_ERROR, PREFIX + message);
		else
			System.out.println(PREFIX + "ERROR " + message);
	}

	static public void error(final String message, final Exception e) {
		if (logService != null)
			logService.log(LogService.LOG_ERROR, PREFIX + message, e);
		else
			System.out.println(PREFIX + "ERROR " + message + ": " + e);
		if (e != null)
			e.printStackTrace();
	}

}
