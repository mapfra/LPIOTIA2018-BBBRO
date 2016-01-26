/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.core.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.AccessControlPolicyEntity;
import org.eclipse.om2m.commons.entities.GroupEntity;
import org.eclipse.om2m.commons.resource.AggregatedResponse;
import org.eclipse.om2m.commons.resource.PrimitiveContent;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.router.Router;

/**
 * Controller for fan out point handling (virtual resource)
 *
 */
public class FanOutPointController extends Controller {

	/**
	 * Fan out the request verifying access rights etc
	 * @param request
	 * @return response primitive including aggregated response(s)
	 */
	protected ResponsePrimitive fanOutRequest(RequestPrimitive request) {
		String targetGroup = request.getTargetId();
		AggregatedResponse aggResp = new AggregatedResponse();
		ArrayList<RequestPrimitive> requests = new ArrayList<>();
		ResponsePrimitive resp = new ResponsePrimitive(request);

		// retrieve the parent group
		GroupEntity group = dbs.getDAOFactory().getGroupDAO().find(transaction, targetGroup);
		// check authorization of the originator 
		List<AccessControlPolicyEntity> acpList = group.getAccessControlPolicies();
		checkACP(acpList, request.getFrom(), request.getOperation()) ;
		
		// TODO validate member types if not retrieve

		ArrayList<FanOutSender> threads = new ArrayList<>();
		// fanout request to each member
		for (String to : group.getMemberIDs()){
			RequestPrimitive fanRequest = request.cloneParameters();
			fanRequest.setTo(to);
			fanRequest.setTargetId(to);
			fanRequest.setReturnContentType(MimeMediaType.OBJ);
			requests.add(fanRequest);
			FanOutSender t = new FanOutSender(fanRequest);
			threads.add(t);
			t.start();
		}

		// aggregate responses
		for (FanOutSender t : threads) {
			try {
				t.join();
				aggResp.getResponsePrimitive().add(t.getResp());
			} catch (InterruptedException e) {
				LOGGER.debug("Fan out thread interrupted", e);
			}
		}

		// sub group creation?

		resp.setResponseStatusCode(ResponseStatusCode.OK);
		resp.setContent(aggResp);
		return resp;
	}

	@Override
	public ResponsePrimitive doCreate(RequestPrimitive request) {
		// fan out the request
		return fanOutRequest(request);
	}

	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		// fan out the request
		return fanOutRequest(request);
	}

	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {
		// fan out the request
		return fanOutRequest(request);
	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		// fan out the request
		return fanOutRequest(request);
	}


	/**
	 * Thread to fan out request
	 *
	 */
	class FanOutSender extends Thread {
		/** Request to fan out */
		private RequestPrimitive reqToFanOut;
		/** Response primitive returned by the router */
		private ResponsePrimitive resp ;
		/**
		 * Constructor
		 * @param req to fan out
		 */
		public FanOutSender(RequestPrimitive req) {
			this.reqToFanOut = req;
		}

		/**
		 * Send the request
		 */
		@Override
		public void run() {
			resp = new Router().doRequest(reqToFanOut);
			resp.setPrimitiveContent(new PrimitiveContent());
			resp.getPritimitiveContent().getAny().add(resp.getContent());
		}

		/**
		 * @return the reqToFanOut
		 */
		public RequestPrimitive getReqToFanOut() {
			return reqToFanOut;
		}

		/**
		 * @param reqToFanOut the reqToFanOut to set
		 */
		public void setReqToFanOut(RequestPrimitive reqToFanOut) {
			this.reqToFanOut = reqToFanOut;
		}

		/**
		 * @return the resp
		 */
		public ResponsePrimitive getResp() {
			return resp;
		}

		/**
		 * @param resp the resp to set
		 */
		public void setResp(ResponsePrimitive resp) {
			this.resp = resp;
		}


	}

}
