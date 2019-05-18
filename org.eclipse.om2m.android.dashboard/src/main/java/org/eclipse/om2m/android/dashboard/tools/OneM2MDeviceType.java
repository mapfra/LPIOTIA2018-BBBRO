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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.sdt.home.types.DeviceType;

import org.eclipse.om2m.android.dashboard.R;

import android.content.res.Resources;
import android.content.res.TypedArray;

public class OneM2MDeviceType {
	
	private static final String DEFAULT = "Device";
	
	private static OneM2MDeviceType UNDEFINED;
	
	private static List<OneM2MDeviceType> values = new ArrayList<OneM2MDeviceType>();
	
	private String key;
	private String locale;
	private int icon;

	static public void initialize(Resources res) {
		values.clear();
		TypedArray deviceItems = res.obtainTypedArray(R.array.devices);
		for (int i = 0; i < deviceItems.length(); i += 3) {
			String dev = deviceItems.getString(i);
			if (dev.equals("undefined"))
				UNDEFINED = new OneM2MDeviceType(dev,
						deviceItems.getString(i+1),
						deviceItems.getResourceId(i+2, -1));
			else if (dev.equals("default"))
				values.add(new OneM2MDeviceType(DEFAULT,
						deviceItems.getString(i+1),
						deviceItems.getResourceId(i+2, -1)));
			else for (DeviceType dt : DeviceType.values()) {
				if (dev.equals(dt.name())) {
					values.add(new OneM2MDeviceType(dev.substring(6), // remove device prefix
							deviceItems.getString(i+1),
							deviceItems.getResourceId(i+2, -1)));
					break;
				}
			}
		}
	}
	
	private OneM2MDeviceType(String key, String locale, int icon) {
		this.key = key;
		this.locale = locale;
		this.icon = (icon < 0) ? R.drawable.otb_picto_dev_cat_sdt : icon;
	}

    public int getIcon() {
        return icon;
    }

    public String getLocale() {
        return locale;
    }
    
    public String getType() {
    	return key;
    }

    public static OneM2MDeviceType fromValue(final String s) {
    	int idx = s.lastIndexOf('.');
    	String dev = (idx < 0) ? s : s.substring(idx+1);
    	if (dev.startsWith("device"))
    		dev = dev.substring(6);
        for (OneM2MDeviceType dt : values) {
       		if (dt.key.equals(dev))
       			return dt;
        }
        return UNDEFINED;
    }
    
    public static String getConvertedType(final String type) {
		OneM2MDeviceType dt = fromValue(type);
		return (dt == null) ? DEFAULT : dt.getType();
	}

	public static List<OneM2MDeviceType> getValues() {
		return values;
	}
	
	public String toString() {
		return "<OTBDeviceType " + key + " " + locale + " " + icon + "/>";
	}

}
