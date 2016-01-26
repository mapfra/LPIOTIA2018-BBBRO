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
package org.eclipse.om2m.core.redirector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.constants.CSEType;
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.AeEntity;
import org.eclipse.om2m.commons.entities.RemoteCSEEntity;
import org.eclipse.om2m.commons.exceptions.Om2mException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.RemoteCSE;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.comm.RestClient;
import org.eclipse.om2m.core.interworking.IpeSelector;
import org.eclipse.om2m.core.persistence.PersistenceService;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBService;
import org.eclipse.om2m.persistence.service.DBTransaction;

/**
 * Re-target the REST request to the Distant CSE registered in the
 * {@link RemoteCSE} children.
 *
 */
public class Redirector {

	private static Log LOGGER = LogFactory.getLog(Redirector.class);

	/**
	 * Re-targets a request to a Distant SCL registered in the sclCollection.
	 * 
	 * @param requestIndication
	 *            - The generic request to handle.
	 * @return The generic returned response.
	 */
	public static ResponsePrimitive retarget(RequestPrimitive request) {
		String remoteCseId = "";
		ResponsePrimitive response = new ResponsePrimitive(request);

		try {
			remoteCseId = "/" + request.getTargetId().split("/")[1];
		} catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.debug("Remote cse not found", e);
			throw new ResourceNotFoundException("Remote cse not found", e);
		}

		// get the database service
		DBService dbs = PersistenceService.getInstance().getDbService();
		DBTransaction transaction = dbs.getDbTransaction();
		transaction.open();

		// get the dao of the parent
		DAO<RemoteCSEEntity> dao = dbs.getDAOFactory().getRemoteCSEbyCseIdDAO();
		RemoteCSEEntity csrEntity = dao.find(transaction, remoteCseId);
		if (csrEntity != null) {
			// test if remote cse is reachable
			if (!csrEntity.isRequestReachability()) {
				throw new Om2mException("Remote Cse is not request reachable", 
						ResponseStatusCode.TARGET_NOT_REACHABLE);
			}
			// get Point of Access
			String url = "";
			if (!csrEntity.getPointOfAccess().isEmpty()) {
				boolean done = false;
				int i = 0;
				// iterating on points of access while target are not reachable
				while (!done & i < csrEntity.getPointOfAccess().size()) {
					url = csrEntity.getPointOfAccess().get(i);
					if(!url.endsWith("/")){
						url += "/";
					}
					String cseId = (csrEntity.getRemoteCseId().startsWith("/")? 
							csrEntity.getRemoteCseId().replace("/", ""):
								csrEntity.getRemoteCseId());
					if (request.getTargetId().startsWith("/" + cseId)){
						url += "~" + request.getTargetId();
					} else {
						url += request.getTargetId();
					}
					request.setTo(url);
					response = RestClient.sendRequest(request);
					if(!(response.getResponseStatusCode()
							.equals(ResponseStatusCode.TARGET_NOT_REACHABLE))){
						done = true;
						if(i > 0){
							String poa = csrEntity.getPointOfAccess().get(i);
							csrEntity.getPointOfAccess().remove(i);
							csrEntity.getPointOfAccess().add(0, poa);
							dbs.getDAOFactory().getRemoteCSEDAO().update(transaction, csrEntity);
							transaction.commit();
						}
					}
					i++;
				}
			} else {
				// TODO to improve w/ polling channel policy
				throw new Om2mException("The point of access parameter is missing", ResponseStatusCode.TARGET_NOT_REACHABLE);
			}
		} else {
			if (!Constants.CSE_TYPE.equalsIgnoreCase(CSEType.IN)) {
				String url = "http://" + Constants.REMOTE_CSE_IP + ":"
						+ Constants.REMOTE_CSE_PORT
						+ Constants.REMOTE_CSE_CONTEXT;
				if (Constants.REMOTE_CSE_CONTEXT.length() > 1) {
					url += "/";
				}
				url += request.getTargetId();
				request.setTo(url);
				// transfer the request and get the response
				response = RestClient.sendRequest(request);
			} else {
				// case nothing found
				response.setResponseStatusCode(ResponseStatusCode.NOT_FOUND);
			}
		}
		transaction.close();
		return response;
	}

	public static ResponsePrimitive retargetNotify(RequestPrimitive request){
		ResponsePrimitive response = new ResponsePrimitive(request);
		DBService dbs = PersistenceService.getInstance().getDbService();
		DBTransaction dbt = dbs.getDbTransaction();
		dbt.open();
		// get the AE 
		AeEntity ae =  dbs.getDAOFactory().getAeDAO().find(dbt, request.getTargetId());
		if(ae == null){
			dbt.close();
			throw new ResourceNotFoundException("AE resource " + request.getTargetId() + " not found.");
		}
		// Get point of access
		if(ae.getPointOfAccess().isEmpty() || !(ae.isRequestReachable())){
			response.setResponseStatusCode(ResponseStatusCode.TARGET_NOT_REACHABLE);
			response.setErrorMessage("AE has no Point of Access");
		} else {
			boolean done = false ;
			int i = 0;
			// for each PoA
			while( !done && (i < ae.getPointOfAccess().size()) ){
				String poa = ae.getPointOfAccess().get(i);
				// if the PoA is a local IPE
				if(IpeSelector.getInterworkingList().containsKey(poa)){
					try{
						LOGGER.info("Sending notification to IPE: " + poa);
						response = IpeSelector.getInterworkingList().get(poa).doExecute(request);						
					} catch (Om2mException om2mE){
						LOGGER.info("Om2m exception caught in Redirector: " + om2mE.getMessage() );
						throw om2mE;
					} catch (Exception e){
						LOGGER.error("Exception caught in IPE execution");
						throw new Om2mException("IPE Internal Error", e, ResponseStatusCode.INTERNAL_SERVER_ERROR);
					}
					done = true;
				} else {
					request.setTo(poa);
					response = RestClient.sendRequest(request);
					if(!response.getResponseStatusCode().equals(ResponseStatusCode.TARGET_NOT_REACHABLE)){
						done = true;
						if(i > 0){
							ae.getPointOfAccess().remove(i);
							ae.getPointOfAccess().add(0, poa);
							dbs.getDAOFactory().getAeDAO().update(dbt, ae);
							dbt.commit();						
						}
					}
				}
				i++;
			}
		}
		dbt.close();
		return response;
	}

}
