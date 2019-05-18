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
package org.eclipse.om2m.android.dashboard.tools;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.om2m.android.dashboard.cse.models.OneM2MApplication;
import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;

public class Sorter {
	
	private static final Comparator<SDTDevice> DEV_COMPARATOR = new Comparator<SDTDevice>() {
        @Override
        public int compare(SDTDevice o1, SDTDevice o2) {
        	return o1.getSerialNumber().compareTo(o2.getSerialNumber());
        }
    };
	
    private static final Comparator<OneM2MApplication> APP_COMPARATOR = new Comparator<OneM2MApplication>() {
        @Override
        public int compare(OneM2MApplication o1, OneM2MApplication o2) {
            return o1.getRn().compareTo(o2.getRn());
        }
    };
	
	public static final SortedSet<SDTDevice> getSortedDevices(final Collection<SDTDevice> devices) {
		SortedSet<SDTDevice> ret = new TreeSet<SDTDevice>(DEV_COMPARATOR);
		ret.addAll(devices);
		return ret;
	}
	
	public static final SortedSet<OneM2MApplication> getSortedApplications() {
		return new TreeSet<OneM2MApplication>(APP_COMPARATOR);
	}
	
	public static SortedSet<OneM2MApplication> getSortedApplications(final List<OneM2MApplication> apps) {
		SortedSet<OneM2MApplication> ret = new TreeSet<OneM2MApplication>(APP_COMPARATOR);
		if (apps != null)
			for (OneM2MApplication a : apps)
				ret.add(a);
		return ret;
	}
	
}
