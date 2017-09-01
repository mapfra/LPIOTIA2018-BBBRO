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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MRequestParams;

import com.google.gson.Gson;

import android.content.Context;
import android.os.AsyncTask;

abstract public class OneM2MRequest<T> extends AsyncTask<Object, Void, T>  {
	
	static public Map<OneM2MReqType, String> errors = new HashMap<OneM2MReqType, String>();

	public enum OneM2MReqType {
		DEVICES,
		DEVICES_BY_TYPES,
		DEVICE,
		APPLICATIONS,
		APPLICATION,
		DEBUG,
		INCSE_STATUS
	}
		
	// ================================================================================
	
	protected OneM2MReqType requestType;
	protected OneM2MListener listener;
	protected Context context;
	protected String errorMessage;

	protected Map<OneM2MReqType, String> requestDebug = new HashMap<OneM2MReqType, String>();
	
	// ================================================================================
		
	protected OneM2MRequest(OneM2MReqType requestType, Context context,
			OneM2MListener listener) {
		this.requestType = requestType;
		this.context = context;
		this.listener = listener;
	}
	
	// ================================================================================
	
	@Override
	protected T doInBackground(Object... args) {	
		requestDebug.clear();
		
		OneM2MRequestParams param = null;
		if ((args != null) && (args.length > 0))
			param = (OneM2MRequestParams) args[0];
		StringBuffer msg = new StringBuffer();
		if (param != null)
			msg.append("(").append(param).append(")");
		try {
			T resp = getResponse(param);
			requestDebug.put(requestType, msg.append(new Gson().toJson(resp)).toString());
			return resp;
		} catch (Exception e) {
			errorMessage = e.getMessage();
			requestDebug.put(requestType, msg.insert(0, "ERROR " + errorMessage).toString());
			errors.put(requestType, msg.insert(0, "ERROR " + errorMessage).toString());
			return null;
		}
	}
	
	// Has to be implemented for each request type
	abstract protected T getResponse(OneM2MRequestParams param);

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		if (result == null) {
			listener.onOneM2MError(requestType, errorMessage);
		} else {
			listener.onOneM2MResponse(requestType, result);
		}
	}

}
