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
import org.eclipse.om2m.android.dashboard.cse.models.OneM2MApplication;

import android.content.Context;

public class OneM2MGetApplicationRequest extends OneM2MRequest<OneM2MApplication> {

	public OneM2MGetApplicationRequest(Context context, OneM2MListener listener) {
		super(OneM2MReqType.APPLICATION, context, listener);
	}

	@Override
	protected OneM2MApplication getResponse(OneM2MRequestParams param) {
		OneM2MApplication application = OneM2MConnector.getInstance(context).getApplication(param.getArg1());
		return application;
	}


}
