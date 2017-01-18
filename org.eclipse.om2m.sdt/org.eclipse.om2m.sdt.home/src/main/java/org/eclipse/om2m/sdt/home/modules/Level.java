/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.home.modules;

import java.util.Map;

import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.Domain;
import org.eclipse.om2m.sdt.Module;
import org.eclipse.om2m.sdt.exceptions.AccessException;
import org.eclipse.om2m.sdt.exceptions.DataPointException;
import org.eclipse.om2m.sdt.home.types.ModuleType;

public class Level extends Module {
	
	private org.eclipse.om2m.sdt.home.types.LevelType quantity;
	
	private org.eclipse.om2m.sdt.home.types.LevelType status;

	public Level(final String name, final Domain domain, org.eclipse.om2m.sdt.home.types.LevelType dpQuantity, org.eclipse.om2m.sdt.home.types.LevelType dpStatus) {
		super(name, domain, ModuleType.level.getDefinition());

		if(dpQuantity != null){
			quantity = dpQuantity;
			quantity.setDoc("The desired quantity of supplies to be used; e.g. of rinse liquid, of water, of milk in a cup of coffee.");
			addDataPoint(quantity);
		}
		if(dpStatus != null){
			status = dpStatus;
			status.setDoc("The current status of supplies e.g. of water, of coffee beans.");
			addDataPoint(status);
		}
	}
	
	
    public Level(final String name, final Domain domain, Map<String, DataPoint> dps) {
        this(name, domain, (org.eclipse.om2m.sdt.home.types.LevelType) dps.get("quantity"), (org.eclipse.om2m.sdt.home.types.LevelType) dps.get("status"));
    }

	
	public int getQuantity() throws DataPointException, AccessException {
		return quantity.getValue();
	}

	public void setQuantity(int v) throws DataPointException, AccessException {
		quantity.setValue(v);
	}
	

	public int getStatus() throws DataPointException, AccessException {
		return status.getValue();
	}

	public void setStatus(int v) throws DataPointException, AccessException {
		status.setValue(v);
	}

}