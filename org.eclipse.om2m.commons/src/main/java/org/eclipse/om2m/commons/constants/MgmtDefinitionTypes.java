/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.commons.constants;

import java.math.BigInteger;

/**
 * Class for management definition types constants
 *
 */
public class MgmtDefinitionTypes {

	public static final BigInteger FIRMWARE = BigInteger.valueOf(1001);
	public static final BigInteger SOFTWARE = BigInteger.valueOf(1002);
	public static final BigInteger MEMORY = BigInteger.valueOf(1003);
	public static final BigInteger AREA_NWK_INFO = BigInteger.valueOf(1004);
	public static final BigInteger AREA_NWK_DEVICE_INFO = BigInteger.valueOf(1005);
	public static final BigInteger BATTERY = BigInteger.valueOf(1006);
	public static final BigInteger DEVICE_INFO = BigInteger.valueOf(1007);
	public static final BigInteger DEVICE_CAPABILITY = BigInteger.valueOf(1008);
	public static final BigInteger REBOOT = BigInteger.valueOf(1009);
	public static final BigInteger EVENT_LOG = BigInteger.valueOf(1010);
	public static final BigInteger CMDH_POLICY = BigInteger.valueOf(1011);
	public static final BigInteger ACTIVE_CMDH_POLICY = BigInteger.valueOf(1012);
	public static final BigInteger CMDH_DEFAULTS = BigInteger.valueOf(1013);
	public static final BigInteger CMDH_DEF_EC_VALUE = BigInteger.valueOf(1014);
	public static final BigInteger CMDH_EC_DEF_PARAM_VALUES = BigInteger.valueOf(1015);
	public static final BigInteger CMDH_LIMITS = BigInteger.valueOf(1016);
	public static final BigInteger CMDH_NETWORK_ACCESS_RULES = BigInteger.valueOf(1017);
	public static final BigInteger CMDH_NW_ACCESS_RULE = BigInteger.valueOf(1018);
	public static final BigInteger CMDH_BUFFER = BigInteger.valueOf(1019);
	
	public static String getShortName(BigInteger type) {
		if (type.equals(FIRMWARE))
			return ShortName.FIRMWARE;
		if (type.equals(SOFTWARE))
			return ShortName.SOFTWARE;
		if (type.equals(MEMORY))
			return ShortName.MEMORY;
		if (type.equals(AREA_NWK_INFO))
			return ShortName.AREA_NWK_INFO;
		if (type.equals(AREA_NWK_DEVICE_INFO))
			return ShortName.AREA_NWK_DEVICE_INFO;
		if (type.equals(BATTERY))
			return ShortName.BATTERY;
		if (type.equals(DEVICE_INFO))
			return ShortName.DEVICE_INFO;
		if (type.equals(DEVICE_CAPABILITY))
			return ShortName.DEVICE_CAPABILITY;
		if (type.equals(REBOOT))
			return ShortName.REBOOT;
		if (type.equals(EVENT_LOG))
			return ShortName.EVENT_LOG;
		if (type.equals(CMDH_POLICY))
			return ShortName.CMDH_POLICY;
		if (type.equals(ACTIVE_CMDH_POLICY))
			return ShortName.ACTIVE_CMDH_POLICY;
		if (type.equals(CMDH_DEFAULTS))
			return ShortName.CMDH_DEFAULTS;
		if (type.equals(CMDH_DEF_EC_VALUE))
			return ShortName.CMDH_DEF_EC_VALUE;
		if (type.equals(CMDH_EC_DEF_PARAM_VALUES))
			return ShortName.CMDH_EC_DEF_PARAM_VALUES;
		if (type.equals(CMDH_LIMITS))
			return ShortName.CMDH_LIMITS;
		if (type.equals(CMDH_NETWORK_ACCESS_RULES))
			return ShortName.CMDH_NETWORK_ACCESS_RULES;
		if (type.equals(CMDH_NW_ACCESS_RULE))
			return ShortName.CMDH_NW_ACCESS_RULE;
		if (type.equals(CMDH_BUFFER))
			return ShortName.CMDH_BUFFER;
		return null;
	}
	
}
