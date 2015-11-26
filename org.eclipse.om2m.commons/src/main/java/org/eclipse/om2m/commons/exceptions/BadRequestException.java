package org.eclipse.om2m.commons.exceptions;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;

public class BadRequestException extends Om2mException{

	private static final long serialVersionUID = 3726239351682437398L;

	public BadRequestException() {
		super(ResponseStatusCode.BAD_REQUEST);
	}
	
	public BadRequestException(String message){
		super(message, ResponseStatusCode.BAD_REQUEST);
	}
	
	public BadRequestException(String message, Throwable cause){
		super(message, cause, ResponseStatusCode.BAD_REQUEST);
	}
	
}
