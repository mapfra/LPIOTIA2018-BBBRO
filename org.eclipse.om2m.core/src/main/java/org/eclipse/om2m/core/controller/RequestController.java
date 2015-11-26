package org.eclipse.om2m.core.controller;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.entities.RequestEntity;
import org.eclipse.om2m.commons.exceptions.AccessDeniedException;
import org.eclipse.om2m.commons.exceptions.ResourceNotFoundException;
import org.eclipse.om2m.commons.resource.Request;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.entitymapper.EntityMapperFactory;
import org.eclipse.om2m.core.urimapper.UriMapper;

public class RequestController extends Controller {

	@Override
	public ResponsePrimitive doCreate(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
		return response;
	}

	@Override
	public ResponsePrimitive doRetrieve(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		
		RequestEntity requestEntity = dbs.getDAOFactory().getRequestEntityDAO().
				find(transaction, request.getTargetId());
		if(requestEntity == null){
			throw new ResourceNotFoundException();
		}
		
		// Check authorization
		if(!request.getFrom().equals(requestEntity.getOriginator())
				&& !request.getFrom().equals(Constants.ADMIN_REQUESTING_ENTITY)){
			throw new AccessDeniedException();
		}
		
		Request requestResource = EntityMapperFactory.getRequestMapper()
				.mapEntityToResource(requestEntity, request);
		response.setContent(requestResource);
		response.setResponseStatusCode(ResponseStatusCode.OK);
		return response;
	}

	@Override
	public ResponsePrimitive doUpdate(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
		return response;
	}

	@Override
	public ResponsePrimitive doDelete(RequestPrimitive request) {
		ResponsePrimitive response = new ResponsePrimitive(request);
		
		RequestEntity requestEntity = dbs.getDAOFactory().
				getRequestEntityDAO().find(transaction, request.getTargetId());
		if(requestEntity == null){
			throw new ResourceNotFoundException();
		}
		
		// Check authorization
		if(!request.getFrom().equals(requestEntity.getOriginator())
				&& !request.getFrom().equals(Constants.ADMIN_REQUESTING_ENTITY)){
			throw new AccessDeniedException();
		}
		
		UriMapper.deleteUri(requestEntity.getHierarchicalURI());
		
		dbs.getDAOFactory().getRequestEntityDAO().delete(transaction, requestEntity);
		transaction.commit();
		response.setResponseStatusCode(ResponseStatusCode.DELETED);
		return response;
	}

}
