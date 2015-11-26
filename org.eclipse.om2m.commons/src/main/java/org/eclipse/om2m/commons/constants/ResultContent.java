/*******************************************************************************
 * Copyright (c) 2013-2015 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Khalil Drira - Management and initial specification.
 *     Guillaume Garzone - Initial specification, conception, implementation, test
 *         and documentation.
 *     François Aïssaoui - Initial specification, conception, implementation, test
 *         and documentation.
 *******************************************************************************/
package org.eclipse.om2m.commons.constants;

import java.math.BigInteger;

/**
 * Constants for result content
 *
 */
public class ResultContent {
	/** Private constructor */
	private ResultContent(){}
	
	public static final BigInteger NOTHING = BigInteger.valueOf(0);
	public static final BigInteger ATTRIBUTES = BigInteger.valueOf(1);
	public static final BigInteger HIERARCHICAL_ADRESS = BigInteger.valueOf(2);
	public static final BigInteger HIERARCHICAL_AND_ATTRIBUTES = BigInteger.valueOf(3);
	public static final BigInteger ATTRIBUTES_AND_CHILD_RES = BigInteger.valueOf(4);
	public static final BigInteger ATTRIBUTES_AND_CHILD_REF = BigInteger.valueOf(5);
	public static final BigInteger CHILD_REF = BigInteger.valueOf(6);
	public static final BigInteger ORIGINAL_RES = BigInteger.valueOf(7);
	
}
