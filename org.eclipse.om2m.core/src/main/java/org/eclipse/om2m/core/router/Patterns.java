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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.om2m.core.dao.DAO;
import org.eclipse.om2m.core.dao.DAOFactory;

public class Patterns {
	
	/** Resource id pattern. */
    public static final String idPattern="(?!(sclBase|scls|scl|applications|application|applicationAnnc|containers|container|content|subscriptions|subscription|"
            + "groups|group|accessRights|accessRight|discovery|mgmtObjs|mgmtObj|mgmtCmd|attahchedDevices|attachedDevice|notificationChannels|"
            + "notificationChannel|execInstances|execInstance|parameters|parameter|m2mPocs|m2mPoc))\\b\\w+\\b";

    /** Announced resource id pattern. */
    public static final String ID_ANNC_PATTERN = "\\w+";

    /** SclBase resource uri pattern. */
    public static final Pattern SCL_BASE_PATTERN = Pattern.compile(Constants.SCL_ID+"/*");

    /** Re-targeting uri pattern. */
    public static final Pattern RETARGETING_PATTERN = Pattern.compile("(?!"+Constants.SCL_ID+")\\b\\w+\\b/*.*");

    /** Scls resource uri pattern. */
    public static final Pattern SCLS_PATTERN = Pattern.compile(SCL_BASE_PATTERN+"/+scls/*");

    /** Scl resource uri pattern. */
    public static final Pattern SCL_PATTERN = Pattern.compile(SCLS_PATTERN+"/+"+idPattern+"/*");

    /** Applications resource uri pattern. */
    public static final Pattern APPLICATIONS_PATTERN = Pattern.compile("("+SCL_BASE_PATTERN+"|"+SCL_PATTERN+")"+"/+applications/*");

    /** Application resource uri pattern. */
    public static final Pattern APPLICATION_PATTERN = Pattern.compile(APPLICATIONS_PATTERN+"/+"+idPattern+"(?<!Annc)/*");

    /** Interworking proxy unit uri pattern. */
    public static final Pattern IPU_PATTERN = Pattern.compile(APPLICATION_PATTERN+"/"+idPattern+"/*.*");

    /** ApplicationAnnc resource uri pattern. */
    public static final Pattern APPLICATION_ANNC_PATTERN = Pattern.compile(APPLICATIONS_PATTERN+"/+"+ID_ANNC_PATTERN+"Annc/*");

    /** Containers resource uri pattern. */
    public static final Pattern CONTAINERS_PATTERN = Pattern.compile("("+SCL_BASE_PATTERN+"|"+SCL_PATTERN+"|"+APPLICATION_PATTERN+"|"+APPLICATION_ANNC_PATTERN+")"+"/+containers/*");

    /** Container resource uri pattern. */
    public static final Pattern CONTAINER_PATTERN = Pattern.compile(CONTAINERS_PATTERN+"/+"+idPattern+"(?<!Annc)(?<!Loc)(?<!LocAnnc)/*");

    /** ContainerAnnc resource uri pattern. */
    public static final Pattern CONTAINER_ANNC_PATTERN = Pattern.compile(CONTAINERS_PATTERN+"/+"+ID_ANNC_PATTERN+"(?<!Loc)(?<!LocAnnc)Annc/*");

    /** LocationContainer resource uri pattern. */
    public static final Pattern LOCATION_CONTAINER_PATTERN = Pattern.compile(CONTAINERS_PATTERN+"/+"+idPattern+"Loc/*");

    /** LocationContainerAnnc resource uri pattern. */
    public static final Pattern LOCATION_CONTAINER_ANNC_PATTERN = Pattern.compile(CONTAINERS_PATTERN+"/+"+idPattern+"LocAnnc/*");

    /** ContentInstances resource uri pattern. */
    public static final Pattern CONTENT_INSTANCES_PATTERN = Pattern.compile("("+CONTAINER_PATTERN+"|"+LOCATION_CONTAINER_PATTERN+")"+"/+contentInstances/*");

    /** ContentInstance resource uri pattern. */
    public static final Pattern CONTENT_INSTANCE_PATTERN = Pattern.compile(CONTENT_INSTANCES_PATTERN+"/+"+idPattern+"/*");

    /** Content resource uri pattern. */
    public static final Pattern CONTENT_PATTERN = Pattern.compile(CONTENT_INSTANCE_PATTERN+"/+content/*");

    /** AccessRights resource uri pattern. */
    public static final Pattern ACCESS_RIGHTS_PATTERN = Pattern.compile("("+SCL_BASE_PATTERN+"|"+SCL_PATTERN+"|"+APPLICATION_PATTERN+"|"+APPLICATION_ANNC_PATTERN+")"+"/+accessRights/*");

    /** AccessRight resource uri pattern. */
    public static final Pattern ACCESS_RIGHT_PATTERN = Pattern.compile(ACCESS_RIGHTS_PATTERN+"/+"+idPattern+"(?<!Annc)/*");

    /** AccessRightAnnc resource uri pattern. */
    public static final Pattern ACCESS_RIGHT_ANNC_PATTERN = Pattern.compile(ACCESS_RIGHTS_PATTERN+"/+"+ID_ANNC_PATTERN+"Annc/*");

    /** Groups resource uri pattern. */
    public static final Pattern GROUPS_PATTERN = Pattern.compile("("+SCL_BASE_PATTERN+"|"+SCL_PATTERN+"|"+APPLICATION_PATTERN+"|"+APPLICATION_ANNC_PATTERN+")"+"/+groups/*");

    /** Group resource uri pattern. */
    public static final Pattern GROUP_PATTERN = Pattern.compile(GROUPS_PATTERN+"/+"+idPattern+"(?<!Annc)/*");

    /** MembersContent resource uri pattern. */
    public static final Pattern MEMBERS_CONTENT_PATTERN = Pattern.compile(GROUP_PATTERN+"/+"+idPattern+"/*");

    /** GroupAnnc resource uri pattern. */
    public static final Pattern GROUP_ANNC_PATTERN = Pattern.compile(GROUPS_PATTERN+"/+"+ID_ANNC_PATTERN+"Annc/*");

    /** Discovery resource uri pattern. */
    public static final Pattern DISCOVERY_PATTERN = Pattern.compile(SCL_BASE_PATTERN+"/+discovery/*.*");

    /** AttachedDevices resource uri pattern. */
    public static final Pattern ATTACHED_DEVICES_PATTERN = Pattern.compile(SCL_PATTERN+"/+attachedDevices/*");

    /** AttachedDevice resource uri pattern. */
    public static final Pattern ATTACHED_DEVICE_PATTERN = Pattern.compile(ATTACHED_DEVICES_PATTERN+"/+"+idPattern+"/*");

    /** MgmtObjs resource uri pattern. */
    public static final Pattern MGMT_OBJS_PATTERN = Pattern.compile("("+SCLS_PATTERN+"|"+SCL_PATTERN+"|"+APPLICATIONS_PATTERN+"|"+ATTACHED_DEVICE_PATTERN+")"+"/+mgmtObjs/*");

    /** MgmtObj resource uri pattern. */
    public static final Pattern MGMT_OBJ_PATTERN = Pattern.compile(MGMT_OBJS_PATTERN+"/+"+idPattern+"Obj/*");

    /** Parameters resource uri pattern. */
    public static final Pattern PARAMETERS_PATTERN = Pattern.compile(MGMT_OBJ_PATTERN+"/+parameters/*");

    /** Parameter resource uri pattern. */
    public static final Pattern PARAMETER_PATTERN = Pattern.compile(PARAMETERS_PATTERN+"/+"+idPattern+"/*");

    /** MgmtCmd resource uri pattern. */
    public static final Pattern MGMT_CMD_PATTERN = Pattern.compile(MGMT_OBJS_PATTERN+"/+"+idPattern+"Cmd/*");

    /** ExecInstances resource uri pattern. */
    public static final Pattern EXEC_INSTANCES_PATTERN = Pattern.compile(MGMT_CMD_PATTERN+"/+execInstances/*");

    /** ExecInstance resource uri pattern. */
    public static final Pattern EXEC_INSTANCE_PATTERN = Pattern.compile(EXEC_INSTANCES_PATTERN+"/+"+idPattern+"/*");

    /** NotificationChannels resource uri pattern. */
    public static final Pattern NOTIFICATION_CHANNELS_PATTERN = Pattern.compile("("+SCL_PATTERN+"|"+APPLICATION_PATTERN+")"+"/+notificationChannels/*");

    /** NotificationChannel resource uri pattern. */
    public static final Pattern NOTIFICATION_CHANNEL_PATTERN = Pattern.compile(NOTIFICATION_CHANNELS_PATTERN+"/+"+idPattern+"/*");

    /** M2mPocs resource uri pattern. */
    public static final Pattern M2M_POCS_PATTERN = Pattern.compile(SCL_PATTERN+"/+m2mPocs/*");

    /** M2mPoc resource uri pattern. */
    public static final Pattern M2M_POC_PATTERN = Pattern.compile(M2M_POCS_PATTERN+"/+"+idPattern+"/*");

    /** Subscriptions resource uri pattern. */
    public static final Pattern SUBSCRIPTIONS_PATTERN = Pattern.compile("("+SCL_BASE_PATTERN+"|"+SCL_PATTERN+"|"+SCLS_PATTERN+"|"+APPLICATIONS_PATTERN+"|"+APPLICATION_PATTERN+
            "|"+CONTAINERS_PATTERN+"|"+CONTAINER_PATTERN+"|"+CONTENT_INSTANCES_PATTERN+"|"+ACCESS_RIGHTS_PATTERN+"|"+ACCESS_RIGHT_PATTERN+
            "|"+GROUPS_PATTERN+"|"+GROUP_PATTERN+"|"+MGMT_OBJS_PATTERN+"|"+MGMT_OBJ_PATTERN+"|"+MGMT_CMD_PATTERN+"|"+ATTACHED_DEVICES_PATTERN+
            "|"+ATTACHED_DEVICE_PATTERN+"|"+PARAMETERS_PATTERN+"|"+PARAMETER_PATTERN+"|"+EXEC_INSTANCES_PATTERN+"|"+EXEC_INSTANCE_PATTERN+
            "|"+LOCATION_CONTAINER_PATTERN+")"+"/+subscriptions/*");

    /** Subscription resource uri pattern. */
    public static final Pattern SUBSCRIPTION_PATTERN = Pattern.compile(SUBSCRIPTIONS_PATTERN+"/+"+idPattern+"/*");

	/**
	 * match uri with a pattern.
	 * @param pattern - pattern
	 * @param uri - resource uri
	 * @return true if matched, otherwise false.
	 */
	public static boolean match(Pattern pattern, String uri) {
	    // Match uri with pattern
	    Matcher m = pattern.matcher(uri);
	    if (!m.matches()){
	        return false;
	    }
	    return true;
	}

	/**
	 * Get the DAO corresponding to the resource described in the URI. 
	 * @param uri
	 * @return DAO
	 */
	public static DAO getDAO(String uri){

        // Match the resource DAO with an uri pattern and return it, otherwise return null*
        if(Patterns.match(Patterns.SCL_BASE_PATTERN,uri)){
            return DAOFactory.getSclBaseDAO();
        }
        
        if(Patterns.match(Patterns.SCL_PATTERN,uri)){
            return DAOFactory.getSclDAO();
        }
        
        if(Patterns.match(Patterns.APPLICATION_PATTERN,uri)){
            return DAOFactory.getApplicationDAO();
        }
        if(Patterns.match(Patterns.APPLICATION_ANNC_PATTERN,uri)){
            return DAOFactory.getApplicationAnncDAO();
        }
        
        if(Patterns.match(Patterns.CONTAINER_PATTERN,uri)){
            return DAOFactory.getContainerDAO();
        }
        if(Patterns.match(Patterns.CONTAINER_ANNC_PATTERN,uri)){
            return DAOFactory.getContainerAnncDAO();
        }
        if(Patterns.match(Patterns.LOCATION_CONTAINER_PATTERN,uri)){
            return DAOFactory.getLocationContainerDAO();
        }
        if(Patterns.match(Patterns.LOCATION_CONTAINER_ANNC_PATTERN,uri)){
            return DAOFactory.getLocationContainerAnncDAO();
        }
        
        if(Patterns.match(Patterns.CONTENT_INSTANCE_PATTERN,uri) ){
            return DAOFactory.getContentInstanceDAO();
        }
        
        if(Patterns.match(Patterns.SUBSCRIPTION_PATTERN,uri)){
            return DAOFactory.getSubscriptionDAO();
        }
        
        if(Patterns.match(Patterns.ACCESS_RIGHT_PATTERN,uri) ){
            return DAOFactory.getAccessRightDAO();
        }
        if(Patterns.match(Patterns.ACCESS_RIGHT_ANNC_PATTERN,uri)){
            return DAOFactory.getAccessRightAnncDAO();
        }
        
        if(Patterns.match(Patterns.GROUP_PATTERN,uri)){
            return DAOFactory.getGroupDAO();
        }
        if(Patterns.match(Patterns.GROUP_ANNC_PATTERN,uri)){
            return DAOFactory.getGroupAnncDAO();
        }
       
        if(Patterns.match(Patterns.MGMT_OBJ_PATTERN,uri)){
            return DAOFactory.getMgmtObjDAO();
        }
        
        if(Patterns.match(Patterns.PARAMETER_PATTERN,uri)){
            return null;
        }
        if(Patterns.match(Patterns.MGMT_CMD_PATTERN,uri)){
            return DAOFactory.getMgmtCmdDAO();
        }
        
        if(Patterns.match(Patterns.EXEC_INSTANCE_PATTERN,uri)){
            return DAOFactory.getExecInstanceDAO();
        }
       
        if(Patterns.match(Patterns.ATTACHED_DEVICE_PATTERN,uri)){
            return DAOFactory.getAttachedDeviceDAO();
        }
        
        if(Patterns.match(Patterns.NOTIFICATION_CHANNEL_PATTERN,uri)){
            return DAOFactory.getNotificationChannelDAO();
        }
       
        if(Patterns.match(Patterns.M2M_POC_PATTERN,uri)){
            return DAOFactory.getM2MPocDAO();
        }

        return null;    
	}
	
}
