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
package org.eclipse.om2m.android.dashboard.cse.requests;


import org.eclipse.om2m.android.dashboard.cse.OneM2MConnector;
import org.eclipse.om2m.android.dashboard.cse.OneM2MListener;
import org.eclipse.om2m.android.dashboard.cse.OneM2MRequest;
import org.eclipse.om2m.android.dashboard.cse.models.SDTDevice;

import android.content.Context;

public class OneM2MGetDeviceRequest extends OneM2MRequest<SDTDevice> {

	public OneM2MGetDeviceRequest(Context context, OneM2MListener listener) {
		super(OneM2MReqType.DEVICE, context, listener);
	}

	@Override
	protected SDTDevice getResponse(OneM2MRequestParams param) {
		return OneM2MConnector.getInstance(context).getDevice(param.getArg1());
	}

}
