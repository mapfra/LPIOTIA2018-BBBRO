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

package org.eclipse.om2m.android.dashboard.cse;

import org.eclipse.om2m.android.dashboard.cse.OneM2MRequest.OneM2MReqType;

public interface OneM2MListener {
		
	public void onOneM2MResponse(OneM2MReqType requestType, Object response);
	
	public void onOneM2MError(OneM2MReqType requestType, String msg);

}
