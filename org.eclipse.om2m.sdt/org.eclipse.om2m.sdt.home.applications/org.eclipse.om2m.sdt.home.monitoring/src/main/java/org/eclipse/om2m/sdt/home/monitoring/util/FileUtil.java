/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.monitoring.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.osgi.framework.BundleContext;


public class FileUtil {

	public static String getFileAsString(final String path,BundleContext context) {
		String res = "";
		if (context != null) {				
			URL url = context.getBundle().getResource(path);
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
				while (br.ready()) {
					res += br.readLine();
				}
			} catch (Exception e) {		
			} finally {
				try { br.close(); } 
				catch (Exception ignored) {}
			}
		}
		return res;
	}
	
	public static final boolean isEmpty(Object o) {
		return (o == null) ? true : o.toString().isEmpty();
	}

}
