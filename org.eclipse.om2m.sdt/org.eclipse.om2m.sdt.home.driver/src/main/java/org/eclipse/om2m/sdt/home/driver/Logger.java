/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.driver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
	
	private static Log LOG = LogFactory.getLog(Logger.class);
	static private final String PREFIX  = "[ONEM2M.";
	
	private String protocol;

	
	public Logger(String protocol) {
		this.protocol = protocol;
	}
	
	public final void debug(final String message) {
		LOG.debug(createLogMsg(null, message));
	}

	public final void debug(final String message, final Class<?> clazz) {
		LOG.debug(createLogMsg(clazz, message));
	}

	public final void info(final String message) {
		LOG.info(createLogMsg(null, message));
	}

	public final void info(final String message, final Class<?> clazz) {
		LOG.info(createLogMsg( clazz, message));
	}

	public final void warning(final String message) {
		LOG.warn(createLogMsg(null, message));
	}

	public final void warning(final String message, final Throwable e) {
		LOG.warn(createLogMsg(null, message), e);
	}

	public final void warning(final String message, final Class<?> clazz) {
		LOG.warn(createLogMsg(clazz, message));
	}

	public final void warning(final String message, final Class<?> clazz, final Throwable e) {
		LOG.warn(createLogMsg( clazz, message), e);
	}

	public final void error(final String message) {
		LOG.error(createLogMsg(null, message));
	}

	public final void error(final String message, final Throwable e) {
		LOG.error(createLogMsg(null, message), e);
	}	

	public final void error(final String message, final Class<?> clazz) {
		LOG.error(createLogMsg(clazz, message));
	}

	public final void error(final String message, final Class<?> clazz, final Throwable e) {
		LOG.error(createLogMsg(clazz, message), e);

	}

	private final String createLogMsg(final Class<?> clazz, final String message) {
		return PREFIX + protocol + ((clazz == null) ? "] " : "." + clazz.getSimpleName() + "] ") + message;		
	}
	
	// Method kept for compatibility reasons
	public <LogService> void setLogService(LogService log) {}
	
	// Method kept for compatibility reasons
	public void unsetLogService( ) {}
}
