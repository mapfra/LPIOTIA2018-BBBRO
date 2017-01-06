/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.onem2m.home.modules;

import java.util.Map;

import org.onem2m.home.types.ModuleType;
import org.onem2m.sdt.DataPoint;
import org.onem2m.sdt.Domain;
import org.onem2m.sdt.Module;
import org.onem2m.sdt.impl.AccessException;
import org.onem2m.sdt.impl.DataPointException;

public class Level extends Module {
	
	private org.onem2m.home.types.LevelType quantity;
	
	private org.onem2m.home.types.LevelType status;

	public Level(final String name, final Domain domain, org.onem2m.home.types.LevelType dpQuantity, org.onem2m.home.types.LevelType dpStatus) {
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
        this(name, domain, (org.onem2m.home.types.LevelType) dps.get("quantity"), (org.onem2m.home.types.LevelType) dps.get("status"));
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