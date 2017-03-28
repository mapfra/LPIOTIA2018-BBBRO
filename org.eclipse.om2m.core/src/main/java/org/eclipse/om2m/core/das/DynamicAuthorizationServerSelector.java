package org.eclipse.om2m.core.das;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.om2m.commons.entities.DynamicAuthorizationConsultationEntity;
import org.eclipse.om2m.commons.entities.ResourceEntity;
import org.eclipse.om2m.commons.exceptions.AccessDeniedException;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.das.service.DynamicAuthorizationServerService;

public class DynamicAuthorizationServerSelector {

	/**
	 * singleton
	 */
	private static final DynamicAuthorizationServerSelector INSTANCE = new DynamicAuthorizationServerSelector();

	/**
	 * 
	 * @return singleton
	 */
	public static DynamicAuthorizationServerSelector getInstance() {
		return INSTANCE;
	}

	private Map<String/* PoA */, DynamicAuthorizationServerService> servers;

	/**
	 * Make private the default constructor.
	 */
	private DynamicAuthorizationServerSelector() {
		servers = new HashMap<>(2);
	}

	/**
	 * Add a server in the selector We must have only one server per PoA
	 * 
	 * @param serverToBeAdded
	 * @return true if successfully added or false if not.
	 */
	public boolean addDynamicAuthorizationServerService(DynamicAuthorizationServerService serverToBeAdded) {
		synchronized (servers) {
			if (!servers.containsKey(serverToBeAdded.getPoA())) {
				servers.put(serverToBeAdded.getPoA(), serverToBeAdded);
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove a server from the selector. The removed server won't be use any
	 * more to perform authorization process.
	 * 
	 * @param serverToBeRemoved
	 */
	public void removeDynamicAuthorizationServerService(DynamicAuthorizationServerService serverToBeRemoved) {
		synchronized (servers) {
			servers.remove(serverToBeRemoved.getPoA());
		}
	}

	/**
	 * Perform authorize request to the Dynamic Authorization Servers
	 * represented by the provided DynamicAuthorizationConsultation entities.
	 * 
	 * 
	 * @param dacesToBeUsed
	 *            dynamicAuthorizationConsultation entities to be used for the
	 *            authorization process
	 * @param request
	 *            request to be performed if the authorization is granted
	 * @param resourceEntity
	 *            resource involved in the request
	 * @throws AccessDeniedException
	 *             if the authorization is denied or if any other error occurs
	 *             during the process
	 */
	public void authorize(List<DynamicAuthorizationConsultationEntity> dacesToBeUsed, RequestPrimitive request,
			ResourceEntity resourceEntity) throws AccessDeniedException {

		// iterate over daces list in order to perform authorization process
		for(DynamicAuthorizationConsultationEntity dace : dacesToBeUsed) {
			if (authorize(dace, request, resourceEntity)) {
				// stop authorization process as soon as we grant authorization
				return;
			}
		}

		// grant authorization failed
		throw new AccessDeniedException();
	}

	/**
	 * Perform authorize request to a specific server
	 * 
	 * @param dace
	 *            dynamicAuthorizationConsultation entity to be used for the
	 *            authorization process
	 * @param request
	 *            request
	 * @param resourceEntity
	 *            resource
	 * @return true if the server grants the authorization else false (all other
	 *         cases)
	 */
	private boolean authorize(DynamicAuthorizationConsultationEntity dace, RequestPrimitive request,
			ResourceEntity resourceEntity) {
		
		List<DynamicAuthorizationServerService> serversToBeUsed = getServer(dace);
		for(DynamicAuthorizationServerService server : serversToBeUsed) {
			try {
				server.authorize(request, resourceEntity);
				return true;
			} catch (AccessDeniedException e) {
				// no need to return something
			}
		}
		
		return false;
	}

	/**
	 * Retrieve a server based on its poa
	 * 
	 * @param poA
	 *            poa of the server to be retrieved
	 * @return the server or null if no server for this poa
	 */
	private DynamicAuthorizationServerService getServer(String poA) {

		DynamicAuthorizationServerService server = null;

		synchronized (servers) {
			server = servers.get(poA);
		}

		return server;
	}

	/**
	 * Retrieve a list of server matching the dace poAs
	 * 
	 * @param dace
	 * @return list of server
	 */
	private List<DynamicAuthorizationServerService> getServer(DynamicAuthorizationConsultationEntity dace) {
		List<DynamicAuthorizationServerService> requestedServers = new ArrayList<>();
		if (dace != null) {
			List<String> poAs = dace.getDynamicAuthorizationPoA();
			for (String poA : poAs) {
				DynamicAuthorizationServerService server = getServer(poA);
				if (server != null) {
					requestedServers.add(server);
				}
			}
		}
		return requestedServers;
	}
}
