package org.onem2m.home.driver;

import org.osgi.service.log.LogService;

public class Logger {
	
	static private final String PREFIX  = "[ONEM2M.";
	
	static private final String[] LEVELS  = new String[] {
		"ERROR ", "WARNING ", "INFO ", "DEBUG "
	};
	
	private String protocol;

	private LogService logService;
	
	public Logger(String protocol) {
		this.protocol = protocol;
	}
	
	public void setLogService(final LogService log) {
		logService = log;
	}

	public void unsetLogService() {
		logService = null;
	}

	public void debug(final String message) {
		print(LogService.LOG_DEBUG, null, message);
	}

	public void debug(final String message, Class clazz) {
		print(LogService.LOG_DEBUG, clazz, message);
	}

	public void info(final String message) {
		print(LogService.LOG_INFO, null, message);
	}

	public void info(final String message, Class clazz) {
		print(LogService.LOG_INFO, clazz, message);
	}

	public void warning(final String message) {
		print(LogService.LOG_WARNING, null, message);
	}

	public void warning(final String message, final Throwable e) {
		print(LogService.LOG_WARNING, null, message, e);
	}

	public void warning(final String message, Class clazz) {
		print(LogService.LOG_WARNING, clazz, message);
	}

	public void warning(final String message, Class clazz, final Throwable e) {
		print(LogService.LOG_WARNING, clazz, message, e);
	}

	public void error(final String message) {
		print(LogService.LOG_ERROR, null, message);
	}

	public void error(final String message, final Throwable e) {
		print(LogService.LOG_ERROR, null, message, e);
	}

	public void error(final String message, Class clazz) {
		print(LogService.LOG_ERROR, clazz, message);
	}

	public void error(final String message, Class clazz, final Throwable e) {
		print(LogService.LOG_ERROR, clazz, message, e);
	}

	private void print(int level, Class clazz, final String message) {
		String msg = PREFIX + protocol + ((clazz == null) ? "] " : "." + clazz.getSimpleName() + "] ") + message;
		if (logService != null)
			logService.log(level, msg);
		else
			System.out.println(LEVELS[level-1] + msg);
	}

	private void print(int level, Class clazz, final String message, final Throwable e) {
		String msg = PREFIX + protocol + ((clazz == null) ? "] " : "." + clazz.getSimpleName() + "] ") + message;
		if (logService != null)
			logService.log(level, msg, e);
		else
			System.out.println(LEVELS[level-1] + msg + ": " + e.getMessage());
		if (e != null)
			e.printStackTrace();
	}

}
