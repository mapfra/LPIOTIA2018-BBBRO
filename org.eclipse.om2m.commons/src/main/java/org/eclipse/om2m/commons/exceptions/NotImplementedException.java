package org.eclipse.om2m.commons.exceptions;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;

public class NotImplementedException extends Om2mException {

	private static final long serialVersionUID = 7817692540186528160L;

	public NotImplementedException(){
		super(ResponseStatusCode.NOT_IMPLEMENTED);
	}
	
	public NotImplementedException(String message){
		super(message, ResponseStatusCode.NOT_IMPLEMENTED);
	}
	
	public NotImplementedException(String message, Throwable cause){
		super(message, cause, ResponseStatusCode.NOT_IMPLEMENTED);
	}
}
