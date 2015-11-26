package org.eclipse.om2m.commons.exceptions;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;

/**
 * Used when validating the group member type. 
 *
 */
public class MemberTypeInconsistentException extends Om2mException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -54825276156105600L;

	public MemberTypeInconsistentException(){
		super(ResponseStatusCode.MEMBER_TYPE_INCONSISTENT);
	}
	
	public MemberTypeInconsistentException(String message){
		super(message, ResponseStatusCode.MEMBER_TYPE_INCONSISTENT);
	}
	
	public MemberTypeInconsistentException(String message, Throwable cause){
		super(message, cause, ResponseStatusCode.MEMBER_TYPE_INCONSISTENT);
	}
}

