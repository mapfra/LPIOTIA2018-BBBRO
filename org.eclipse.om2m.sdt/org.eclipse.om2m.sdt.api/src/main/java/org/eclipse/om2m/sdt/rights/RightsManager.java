/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.sdt.rights;

import org.eclipse.om2m.sdt.Action;
import org.eclipse.om2m.sdt.DataPoint;
import org.eclipse.om2m.sdt.events.SDTEventListener;

public interface RightsManager {
	
	boolean isGrantedReadAccess(DataPoint dataPoint);
	
	boolean isGrantedWriteAccess(DataPoint dataPoint);
	
	boolean isGrantedAccess(Action action);
	
	boolean isGrantedEventAccess(final SDTEventListener l, final DataPoint dp);
	
	void authenticateListener(SDTEventListener listener);

}
