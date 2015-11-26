package org.eclipse.om2m.commons.exceptions;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;

public class ConflictException extends Om2mException {

	private static final long serialVersionUID = 1174293111515837251L;

	public ConflictException(){
		super(ResponseStatusCode.CONFLICT);
	}
	
	public ConflictException(String message){
		super(message, ResponseStatusCode.CONFLICT);
	}
	
	public ConflictException(String message, Throwable cause){
		super(message, cause, ResponseStatusCode.CONFLICT);
	}
}
