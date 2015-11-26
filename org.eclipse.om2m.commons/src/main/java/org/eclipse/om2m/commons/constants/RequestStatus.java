package org.eclipse.om2m.commons.constants;

import java.math.BigInteger;

/**
 * Class representing the request status values
 *
 */
public class RequestStatus {

	/** Private status to avoir creation of this object */
	private RequestStatus(){}

	/** Represent the completed state */
	public static final BigInteger COMPLETED = BigInteger.valueOf(1);
	/** Represent the failed state */
	public static final BigInteger FAILED = BigInteger.valueOf(2);
	/** Represent the pending state */
	public static final BigInteger PENDING = BigInteger.valueOf(3);
	/** Represent the forwarded state */
	public static final BigInteger FORWARDED = BigInteger.valueOf(4);
	
}
