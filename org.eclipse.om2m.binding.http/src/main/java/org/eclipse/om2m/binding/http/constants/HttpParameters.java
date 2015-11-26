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
package org.eclipse.om2m.binding.http.constants;

public class HttpParameters {

	/** Private constructor */
	private HttpParameters() {
	}

	public static final String RESPONSE_TYPE = "rt";
	public static final String RESULT_PERSISTENCE = "rp";
	public static final String RESULT_CONTENT = "rc";
	public static final String DELIVERY_AGGREGATION = "da";
	public static final String CREATED_BEFORE = "crb";
	public static final String CREATED_AFTER = "cra";
	public static final String MODIFIED_SINCE = "ms";
	public static final String UNMODIFIED_SINCE = "us";
	public static final String STATE_TAG_SMALLER = "sts";
	public static final String STATE_TAG_BIGGER = "stb";
	public static final String EXPIRE_BEFORE = "exb";
	public static final String EXPIRE_AFTER = "exa";
	public static final String LABELS = "lbl";
	public static final String RESOURCE_TYPE = "rty";
	public static final String SIZE_ABOVE = "sza";
	public static final String SIZE_BELOW = "szb";
	public static final String CONTENT_TYPE = "cty";
	public static final String LIMIT = "lim";
	public static final String ATTRIBUTE = "atr";
	public static final String FILTER_USAGE = "fu";
	public static final String DISCOVERY_RESULT_TYPE = "drt";
}
