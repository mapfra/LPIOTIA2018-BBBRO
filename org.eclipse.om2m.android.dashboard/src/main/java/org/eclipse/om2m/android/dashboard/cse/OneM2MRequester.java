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
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.om2m.android.dashboard.cse.OneM2MRequest.OneM2MReqType;
import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MGetApplicationRequest;
import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MGetApplicationsRequest;
import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MGetDeviceRequest;
import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MGetDevicesRequest;
import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MGetIncseStatusRequest;
import org.eclipse.om2m.android.dashboard.cse.requests.OneM2MRequestParams;

import android.content.Context;

public class OneM2MRequester   {

	private static final OneM2MRequester INSTANCE = new OneM2MRequester();
	
	private Map<String, Map<OneM2MReqType, Timer>> currentPollings = new HashMap<String, Map<OneM2MReqType,Timer>>();

	public final static int DEFAULT_POLLING_DELAY = 15;
	
	// ================================================================================

	abstract class PollingTask extends TimerTask {
		protected OneM2MReqType requestType;
		protected OneM2MRequestParams params = null;
		protected Context context = null;
		protected OneM2MListener listener = null;

		public PollingTask(OneM2MReqType requestType, OneM2MRequestParams params2, 
				Context context, OneM2MListener listener) {
			this.requestType = requestType;
			this.params = params2;
			this.context = context;
			this.listener = listener;
		}
	}

	// ================================================================================

	private OneM2MRequester() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}		
	}

	public static OneM2MRequester getInstance() {			
		return INSTANCE;
	}

	// ================================================================================


	public void sendRequest(OneM2MReqType requestType, OneM2MRequestParams params, 
			Context context, OneM2MListener listener) {
		OneM2MRequest<?> request = null;

		switch(requestType) {
		case DEVICES:
			request = new OneM2MGetDevicesRequest(context, listener);
			break;
		case DEVICES_BY_TYPES:
//			request = new OTBHabDevicesByTypesRequest(context, listener);	
			break;
		case DEVICE:
			request = new OneM2MGetDeviceRequest(context, listener);
			break;
		case APPLICATIONS:
			request = new OneM2MGetApplicationsRequest(context, listener);
			break;
		case APPLICATION:
			request = new OneM2MGetApplicationRequest(context, listener);
			break;	
		case INCSE_STATUS:
			request = new OneM2MGetIncseStatusRequest(context, listener);
			break;
		case DEBUG:
//			request = new OTBHabDebugRequest(context, listener);
			break;
		}

		request.execute(params);
	}

	public void sendPollingRequest(OneM2MReqType requestType, OneM2MRequestParams params, 
			Context context, OneM2MListener listener, int pollingDelay) {
		// possible cases :
		//   1- this caller have active polling => check if it is for the same request
		//      . yes => cancel current polling and this request and restart it with nex polling delay
		//      . no => create a new polling for this request
		//   2- this caller does not have active polling => no checking & create polling		

		// get caller class name (= identifier)
		String callerName = new Throwable().getStackTrace()[1].getClassName(); 
		
		Map<OneM2MReqType, Timer> pollingsForCaller = currentPollings.get(callerName);
		if (pollingsForCaller == null) {
			pollingsForCaller = new HashMap<OneM2MReqType, Timer>();
			currentPollings.put(callerName, pollingsForCaller);
		}

		Timer pollingTimer = pollingsForCaller.get(requestType);
		if (pollingTimer != null) {
			// there is a polling for the same request => cancel current one + then create a new one (= restart)
			pollingTimer.cancel();
		}

		pollingTimer = new Timer();
		pollingTimer.scheduleAtFixedRate(
				new PollingTask(requestType, params, context, listener) {
					@Override
					public void run() {
						sendRequest(requestType, params, context, listener);					
					}
				}, 
				0,  
				pollingDelay * 1000);

		// add/replace timer
		pollingsForCaller.put(requestType, pollingTimer);
	}

	public void cancelMyPollingRequests() {
		// get caller class name (= identifier)
		String callerName = new Throwable().getStackTrace()[1].getClassName(); 
		
		Map<OneM2MReqType, Timer> pollingsForCaller = currentPollings.get(callerName);
		if (pollingsForCaller != null) {
			for (OneM2MReqType reqType : pollingsForCaller.keySet()) {
				Timer pollingTimer = pollingsForCaller.get(reqType);
				pollingTimer.cancel();
			}
		}
		currentPollings.remove(callerName);
	}

}
