package org.eclipse.om2m.ipe.sdt;

import java.util.Date;

import org.osgi.service.log.LogService;

public class Logger {
	
	private static final Logger INSTANCE = new Logger();
	
	private static final Boolean IS_DEBUG_ENABLED = Boolean.TRUE;
	private static final Boolean IS_ERROR_ENABLED = Boolean.TRUE;
	private static final Boolean IS_INFO_ENABLED = Boolean.TRUE;
	
	
	private LogService logService;

	/**
	 * Retrieves the singleton instance
	 * @return Logger
	 */
	public static Logger getInstance() {
		return INSTANCE;
	}
	
	
	private Logger() {
	}
	
	public void setLogService(LogService pLogService) {
		synchronized (this) {
			this.logService = pLogService;
		}
	}
	
	public LogService getLogService() {
		LogService toBeReturned = null;
		synchronized (this) {
			toBeReturned = logService;
		}
		return toBeReturned;
	}
	
	/**
	 * Log a info message
	 * @param clazz clazz calling this method
	 * @param message log message 
	 */
	public void logInfo(Class clazz, String message) {
		LogService currentLog = getLogService();
		if (currentLog != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(clazz.getSimpleName());
			sb.append(":");
			sb.append(message);
			
			currentLog.log(LogService.LOG_INFO, sb.toString());
			
		} else {
			if (IS_INFO_ENABLED) {
				StringBuffer sb = new StringBuffer();
				
				sb.append(new Date());
				sb.append("-");
				sb.append(clazz.getSimpleName());
				sb.append(":");
				sb.append(message);
				System.out.println(sb.toString());
			}
		}
	}
	
	/**
	 * Log a debug message
	 * @param clazz clazz calling this method
	 * @param message log message 
	 */
	public void logDebug(Class clazz, String message) {
		LogService currentLog = getLogService();
		if (currentLog != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(clazz.getSimpleName());
			sb.append(":");
			sb.append(message);
			
			currentLog.log(LogService.LOG_DEBUG, sb.toString());
			
		} else {
			if (IS_DEBUG_ENABLED) {
				StringBuffer sb = new StringBuffer();
				
				sb.append(new Date());
				sb.append("-");
				sb.append(clazz.getSimpleName());
				sb.append(":");
				sb.append(message);
				System.out.println(sb.toString());
			}
		}
	}
	
	
	/**
	 * Log error message
	 * @param clazz clazz calling this method
	 * @param message message to be logged
	 * @param e exception
	 */
	public void logError(Class clazz, String message, Throwable e) {
		LogService currentLog = getLogService();
		if (currentLog != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(clazz.getSimpleName());
			sb.append(":");
			sb.append(message);
			
			currentLog.log(LogService.LOG_ERROR, sb.toString(), e);
			
		} else {
			if (IS_ERROR_ENABLED) {
				StringBuffer sb = new StringBuffer();
				
				sb.append(new Date());
				sb.append("-");
				sb.append(clazz.getSimpleName());
				sb.append(":");
				sb.append(message);
				sb.append("Exception:");
				if (e!= null) {
					sb.append(e.getMessage());
				}
				System.out.println(sb.toString());
				if (e!=null) {
					e.printStackTrace();
				}
			}
		}
	}
	
	

	
	
}
