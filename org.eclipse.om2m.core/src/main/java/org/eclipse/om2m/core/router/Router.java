/*******************************************************************************
 * Copyright (c) 2013-2015 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Thierry Monteil (Project co-founder) - Management and initial specification,
 *         conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification,
 *         conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test
 *         and documentation.
 *     Guillaume Garzone - Conception, implementation, test and documentation.
 *     Francois Aissaoui - Conception, implementation, test and documentation.
 ******************************************************************************/
package org.eclipse.om2m.core.router;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.commons.resource.ErrorInfo;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.StatusCode;
import org.eclipse.om2m.commons.rest.RequestIndication;
import org.eclipse.om2m.commons.rest.ResponseConfirm;
import org.eclipse.om2m.commons.utils.XmlMapper;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.controller.APocController;
import org.eclipse.om2m.core.controller.AccessRightAnncController;
import org.eclipse.om2m.core.controller.AccessRightController;
import org.eclipse.om2m.core.controller.AccessRightsController;
import org.eclipse.om2m.core.controller.ApplicationAnncController;
import org.eclipse.om2m.core.controller.ApplicationController;
import org.eclipse.om2m.core.controller.ApplicationsController;
import org.eclipse.om2m.core.controller.AttachedDeviceController;
import org.eclipse.om2m.core.controller.AttachedDevicesController;
import org.eclipse.om2m.core.controller.ContainerAnncController;
import org.eclipse.om2m.core.controller.ContainerController;
import org.eclipse.om2m.core.controller.ContainersController;
import org.eclipse.om2m.core.controller.ContentController;
import org.eclipse.om2m.core.controller.ContentInstanceController;
import org.eclipse.om2m.core.controller.ContentInstancesController;
import org.eclipse.om2m.core.controller.Controller;
import org.eclipse.om2m.core.controller.DiscoveryController;
import org.eclipse.om2m.core.controller.ExecInstanceController;
import org.eclipse.om2m.core.controller.ExecInstancesController;
import org.eclipse.om2m.core.controller.GroupAnncController;
import org.eclipse.om2m.core.controller.GroupController;
import org.eclipse.om2m.core.controller.GroupsController;
import org.eclipse.om2m.core.controller.LocationContainerAnncController;
import org.eclipse.om2m.core.controller.LocationContainerController;
import org.eclipse.om2m.core.controller.M2MPocController;
import org.eclipse.om2m.core.controller.M2MPocsController;
import org.eclipse.om2m.core.controller.MembersContentController;
import org.eclipse.om2m.core.controller.MgmtCmdController;
import org.eclipse.om2m.core.controller.MgmtObjController;
import org.eclipse.om2m.core.controller.MgmtObjsController;
import org.eclipse.om2m.core.controller.NotificationChannelController;
import org.eclipse.om2m.core.controller.NotificationChannelsController;
import org.eclipse.om2m.core.controller.ParametersController;
import org.eclipse.om2m.core.controller.SclBaseController;
import org.eclipse.om2m.core.controller.SclController;
import org.eclipse.om2m.core.controller.SclsController;
import org.eclipse.om2m.core.controller.SubscriptionController;
import org.eclipse.om2m.core.controller.SubscriptionsController;
import org.eclipse.om2m.core.redirector.Redirector;
import org.eclipse.om2m.core.service.SclService;
/**
 * Routes a generic request to the appropriate resource controller to handle it based on the request method and URI.
 * @author <ul>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.fr ></li>
 *         <li>Marouane El kiasse < melkiasse@laas.fr > < kiasmarouane@gmail.com ></li>
 *         </ul>
 */

public class Router implements SclService {
    /** Logger */
    private static Log LOGGER = LogFactory.getLog(Router.class);
    public static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    
    /**
     * Invokes the correct resource controller method.
     * @param method - Request method
     * @param targetID - Request target id
     * @param requestingEntity - Issuer requesting entity
     * @param resource - Resource object
     * @return The generic returned response
     */

    public ResponseConfirm doRequest(String method, String targetID,String requestingEntity, Resource resource ){
        // Convert the resource object to an xml String
        String representation = null;
        if(resource!=null){
            representation = XmlMapper.getInstance().objectToXml(resource);
        }
        // Create a RequestIndication object
        RequestIndication requestIndication = new RequestIndication(method, targetID, requestingEntity, representation);
        // Call doRequest method and return the received response.
        return doRequest(requestIndication);
    }

    /**
     * Invokes required resource controller method.
     * @param requestIndication - The generic request to handle
     * @return The generic returned response
     */
    public ResponseConfirm doRequest(RequestIndication requestIndication) {
        LOGGER.info(requestIndication);
        ResponseConfirm  responseConfirm = new ResponseConfirm();

        // Check requesting entity not null.
        if(requestIndication.getRequestingEntity()==null){
            return new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_AUTHORIZATION_NOT_ADDED,"Requesting Entity should not be null"));
        }

        // Remove the first "/" from the request uri if exist.
        if(requestIndication.getTargetID().startsWith("/")){
            requestIndication.setTargetID(requestIndication.getTargetID().substring(1));
        }
        // Remove the last "/" from the request uri if exist.
        if(requestIndication.getTargetID().endsWith("/")){
            requestIndication.setTargetID(requestIndication.getTargetID().substring(0,requestIndication.getTargetID().length()-1));
        }
    	readWriteLock.readLock().lock();

        // Retagreting case
        if(Patterns.match(Patterns.RETARGETING_PATTERN,requestIndication.getTargetID())){
        	responseConfirm  = new Redirector().retarget(requestIndication); 
        }else{
        	long begInitController = System.currentTimeMillis();
	        // Determine the appropriate resource controller
	        Controller controller = getResourceController(requestIndication.getTargetID(),requestIndication.getMethod(),requestIndication.getRepresentation());
	        long endInitController = System.currentTimeMillis();
	        LOGGER.debug("***************** Time init controller : " + (endInitController - begInitController));
	        // Select the resource controller method and invoke it.
	        if(controller!=null){
	
		            LOGGER.info("ResourceController ["+controller.getClass().getSimpleName()+"]");
		            try{
		            		long deb = System.currentTimeMillis();
			                switch(requestIndication.getMethod()){
			                case Constants.METHOD_RETREIVE: responseConfirm = controller.doRetrieve(requestIndication);
			                break;
			                case Constants.METHOD_CREATE: responseConfirm = controller.doCreate(requestIndication);
			                break;
			                case Constants.METHOD_UPDATE:  responseConfirm = controller.doUpdate(requestIndication);
			                break;
			                case Constants.METHOD_DELETE:  responseConfirm = controller.doDelete(requestIndication);
			                break;
			                case Constants.METHOD_EXECUTE: responseConfirm = controller.doExecute(requestIndication);
			                break;
			                default: responseConfirm = new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Bad Method"));
			                break;
		                }
			                LOGGER.debug("***************** Time in controller :" + (System.currentTimeMillis() - deb)) ; 
		            }catch(Exception e){
		                LOGGER.error("Controller Internal Error",e);
		                responseConfirm =  new ResponseConfirm(new ErrorInfo(StatusCode.STATUS_INTERNAL_SERVER_ERROR,"Controller Internal Error"));
		            }
	        }else{
	            responseConfirm = new  ResponseConfirm(new ErrorInfo(StatusCode.STATUS_BAD_REQUEST,"Bad TargetID"));
	        }
        }
        readWriteLock.readLock().unlock();

        LOGGER.info(responseConfirm);
        return responseConfirm;
    }

    /**
     * Finds requried resource controller based on uri patterns.
     * @param uri - Generic request uri
     * @param method - Generic request method
     * @param representation - Resource representation
     * @return The matched resource controller otherwise null
     */
    public Controller getResourceController(String uri, String method, String representation){

        // Match the resource controller with an uri pattern and return it, otherwise return null*
        if(Patterns.match(Patterns.SCL_BASE_PATTERN,uri)){
            return new SclBaseController();
        }

        if(Patterns.match(Patterns.SCLS_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)){
            return new SclsController();
        }
        if(Patterns.match(Patterns.SCL_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)|| (Patterns.match(Patterns.SCLS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE))){
            return new SclController();
        }
        if(Patterns.match(Patterns.APPLICATIONS_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)){
            return new ApplicationsController();
        }
        // In some cases it is required to know the resource name to detemine the required resource controller.
        // This is the reason why resource representation is added as parameter for some methods.
        if(Patterns.match(Patterns.APPLICATION_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE) || (Patterns.match(Patterns.APPLICATIONS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE) && !representation.contains(":applicationAnnc"))){
            return new ApplicationController();
        }
        if(Patterns.match(Patterns.APPLICATION_ANNC_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE) || (Patterns.match(Patterns.APPLICATIONS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE) && representation.contains(":applicationAnnc"))){
            return new ApplicationAnncController();
        }
        if(Patterns.match(Patterns.IPU_PATTERN,uri)){
            // will forward to a RestClientController or IPUController;
            return new APocController();
        }
        if(Patterns.match(Patterns.CONTAINERS_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)){
            return new ContainersController();
        }
        if(Patterns.match(Patterns.CONTAINER_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)|| (Patterns.match(Patterns.CONTAINERS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE) && !representation.contains(":containerAnnc"))){
            return new ContainerController();
        }
        if(Patterns.match(Patterns.CONTAINER_ANNC_PATTERN,uri)&& !method.equals(Constants.METHOD_CREATE) || (Patterns.match(Patterns.CONTAINERS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE) && representation.contains(":containerAnnc"))){
            return new ContainerAnncController();
        }
        if(Patterns.match(Patterns.LOCATION_CONTAINER_PATTERN,uri)){
            return new LocationContainerController();
        }
        if(Patterns.match(Patterns.LOCATION_CONTAINER_ANNC_PATTERN,uri)){
            return new LocationContainerAnncController();
        }
        if(Patterns.match(Patterns.CONTENT_INSTANCES_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)){
            return new ContentInstancesController();
        }
        if(Patterns.match(Patterns.CONTENT_INSTANCE_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)|| (Patterns.match(Patterns.CONTENT_INSTANCES_PATTERN,uri) && method.equals(Constants.METHOD_CREATE))){
            return new ContentInstanceController();
        }
        if(Patterns.match(Patterns.CONTENT_PATTERN,uri)){
            return new ContentController();
        }
        if(Patterns.match(Patterns.SUBSCRIPTIONS_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)){
            return new SubscriptionsController();
        }
        if(Patterns.match(Patterns.SUBSCRIPTION_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)|| (Patterns.match(Patterns.SUBSCRIPTIONS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE))){
            return new SubscriptionController();
        }
        if(Patterns.match(Patterns.ACCESS_RIGHTS_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)){
            return new AccessRightsController();
        }
        if(Patterns.match(Patterns.ACCESS_RIGHT_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE) || (Patterns.match(Patterns.ACCESS_RIGHTS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE) && !representation.contains(":accessRightAnnc"))){
            return new AccessRightController();
        }
        if(Patterns.match(Patterns.ACCESS_RIGHT_ANNC_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE) || (Patterns.match(Patterns.ACCESS_RIGHTS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE) && representation.contains(":accessRightAnnc"))){
            return new AccessRightAnncController();
        }
        if(Patterns.match(Patterns.GROUPS_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE)){
            return new GroupsController();
        }
        if(Patterns.match(Patterns.GROUP_PATTERN,uri)&& !method.equals(Constants.METHOD_CREATE) || (Patterns.match(Patterns.GROUPS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE) && !representation.contains(":groupAnnc"))){
            return new GroupController();
        }
        if(Patterns.match(Patterns.GROUP_ANNC_PATTERN,uri) && !method.equals(Constants.METHOD_CREATE) || (Patterns.match(Patterns.GROUPS_PATTERN,uri) && method.equals(Constants.METHOD_CREATE) && representation.contains(":groupAnnc"))){
            return new GroupAnncController();
        }
        if(Patterns.match(Patterns.MEMBERS_CONTENT_PATTERN,uri)){
            return new MembersContentController();
        }
        if(Patterns.match(Patterns.DISCOVERY_PATTERN,uri)){
            return new DiscoveryController();
        }
        if(Patterns.match(Patterns.MGMT_OBJS_PATTERN,uri)){
            return new MgmtObjsController();
        }
        if(Patterns.match(Patterns.MGMT_OBJ_PATTERN,uri)){
            return new MgmtObjController();
        }
        if(Patterns.match(Patterns.PARAMETERS_PATTERN,uri)){
            return new ParametersController();
        }
        if(Patterns.match(Patterns.PARAMETER_PATTERN,uri)){
            return null;
        }
        if(Patterns.match(Patterns.MGMT_CMD_PATTERN,uri)){
            return new MgmtCmdController();
        }
        if(Patterns.match(Patterns.EXEC_INSTANCES_PATTERN,uri)){
            return new ExecInstancesController();
        }
        if(Patterns.match(Patterns.EXEC_INSTANCE_PATTERN,uri)){
            return new ExecInstanceController();
        }
        if(Patterns.match(Patterns.ATTACHED_DEVICES_PATTERN,uri)){
            return new AttachedDevicesController();
        }
        if(Patterns.match(Patterns.ATTACHED_DEVICE_PATTERN,uri)){
            return new AttachedDeviceController();
        }
        if(Patterns.match(Patterns.NOTIFICATION_CHANNELS_PATTERN,uri)){
            return new NotificationChannelsController();
        }
        if(Patterns.match(Patterns.NOTIFICATION_CHANNEL_PATTERN,uri)){
            return new NotificationChannelController();
        }
        if(Patterns.match(Patterns.M2M_POCS_PATTERN,uri)){
            return new M2MPocsController();
        }
        if(Patterns.match(Patterns.M2M_POC_PATTERN,uri)){
            return new M2MPocController();
        }

        return null;
    }
}
