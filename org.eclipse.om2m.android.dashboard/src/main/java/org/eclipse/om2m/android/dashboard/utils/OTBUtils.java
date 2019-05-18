/*******************************************************************************
 * Copyright (c) 2013, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    BAREAU Cyrille <cyrille.bareau@orange.com>, 
 *    BONNARDEL Gregory <gbonnardel.ext@orange.com>, 
 *    BOLLE Sebastien <sebastien.bolle@orange.com>.
 *******************************************************************************/
package org.eclipse.om2m.android.dashboard.utils;

import java.util.Collection;
import java.util.Map;

public class OTBUtils {
	
	static public final boolean equals(final String s1, final String s2) {
		return (s1 == null) ? (s2 == null) : s1.equals(s2);
	}
	
	static public final boolean isEmpty(final String s) {
		return (s == null) || s.trim().isEmpty();
	}
	
	static public final boolean isEmpty(final Collection<?> c) {
		return (c == null) || c.isEmpty();
	}
	
	static public final boolean isEmpty(final Map<?,?> c) {
		return (c == null) || c.isEmpty();
	}
	
	static public final boolean isEmpty(final Object[] c) {
		return (c == null) || (c.length == 0);
	}

}
