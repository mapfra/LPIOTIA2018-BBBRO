package org.eclipse.om2m.commons.exceptions;

/**
 * Exception used when a member of a group is not found 
 * during its validation.
 * 
 *
 */
public class MemberNonFoundException extends BadRequestException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 599962012717246758L;
	
	public MemberNonFoundException(String message, Throwable cause){
		super(message, cause);
	}
	
	/**
	 * Main constructor with the uri of the not found member
	 * @param memberUri
	 */
	public MemberNonFoundException(String message){
		super(message);
	}

}
