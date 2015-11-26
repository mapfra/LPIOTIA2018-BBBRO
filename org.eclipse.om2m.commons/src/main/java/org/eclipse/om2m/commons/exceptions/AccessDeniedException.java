package org.eclipse.om2m.commons.exceptions;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;

public class AccessDeniedException extends Om2mException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2488820054507430948L;

	public AccessDeniedException(){
		super(ResponseStatusCode.ACCESS_DENIED);
	}

	public AccessDeniedException(String message){
		super(message, ResponseStatusCode.ACCESS_DENIED);
	}
	
	public AccessDeniedException(String message, Throwable cause){
		super(message, cause, ResponseStatusCode.ACCESS_DENIED);
	}
	
}

