/*******************************************************************************
 * Copyright (c) 2014, 2017 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.datamapping.jaxb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractMapperTest {
	
	public String readFile(String filename) {
		StringBuffer sb = new StringBuffer();
		File file = new File(filename);
		try {
			BufferedReader buffReader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = buffReader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			// sb.setLength(sb.length()-1);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return sb.toString();
	}


}
