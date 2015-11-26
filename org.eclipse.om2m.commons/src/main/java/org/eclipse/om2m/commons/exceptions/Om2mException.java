package org.eclipse.om2m.commons.exceptions;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;

public class Om2mException extends RuntimeException {

	private static final long serialVersionUID = 3306932500552711777L;
	
	private BigInteger errorStatusCode;
	
	public Om2mException(){
		super();
		this.errorStatusCode = ResponseStatusCode.INTERNAL_SERVER_ERROR;
	}
	
	public Om2mException(BigInteger errorStatusCode){
		this.errorStatusCode = errorStatusCode;
	}

	public Om2mException(String message, BigInteger errorStatusCode){
		super(message);
		this.errorStatusCode = errorStatusCode;
	}
	
	public Om2mException(String message, Throwable cause, BigInteger errorStatusCode){
		super(message, cause);
		this.errorStatusCode = errorStatusCode;
	}

	/**
	 * @return the errorStatusCode
	 */
	public BigInteger getErrorStatusCode() {
		return errorStatusCode;
	}

	/**
	 * @param errorStatusCode the errorStatusCode to set
	 */
	public void setErrorStatusCode(BigInteger errorStatusCode) {
		this.errorStatusCode = errorStatusCode;
	}
	
}
