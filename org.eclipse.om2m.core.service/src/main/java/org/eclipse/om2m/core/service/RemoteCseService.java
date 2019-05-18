package org.eclipse.om2m.core.service;

import java.util.List;

import org.eclipse.om2m.commons.entities.RemoteCSEEntity;

/**
 * This interface lists the RemoteCSE available on the CSE.
 * 
 * 
 * @author MPCY8647
 *
 */
public interface RemoteCseService {
	
	public static final String REMOTE_CSE_TOPIC = "org/eclipse/om2m/remoteCse";
	public static final String REMOTE_CSE_ID_PROPERTY = "remoteCseId";
	public static final String REMOTE_CSE_NAME_PROPERTY = "remoteCseName";
	public static final String REMOTE_CSE_POA = "remoteCsePoa";
	public static final String OPERATION_PROPERTY = "operation";
	public static final String ADD_OPERATION_VALUE = "add";
	public static final String REMOVE_OPERATION_VALUE = "remove";
	
	public List<RemoteCSEEntity> getRemoteCses();

}
