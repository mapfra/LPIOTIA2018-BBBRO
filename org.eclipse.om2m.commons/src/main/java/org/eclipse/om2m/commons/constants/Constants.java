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
package org.eclipse.om2m.commons.constants;

/**
 * Initializes platform properties
 */
public class Constants {
	//CseBase resource properties
    /** CSE Type */
    public static final String CSE_TYPE = System.getProperty("org.eclipse.om2m.cseType","IN-CSE");
    /** CseBase id. */
    public static final String CSE_ID = System.getProperty("org.eclipse.om2m.cseBaseId","in-cse");
    /** CseBase name. */
    public static final String CSE_NAME = System.getProperty("org.eclipse.om2m.cseBaseName", "in-name");
   
    /** Default admin access right profile */
    public static final String ADMIN_PROFILE_ID = "acp_admin";
    /** Default admin requesting entity. (username/password) */
    public static final String ADMIN_REQUESTING_ENTITY = System.getProperty("org.eclipse.om2m.adminRequestingEntity","admin:admin");
    /** Default guest access right profile */
    public static final String GUEST_PROFILE_ID = "ACP_GUEST";
    /** Default guest requesting entity. (username/password) */
    public static final String GUEST_REQUESTING_ENTITY = System.getProperty("org.eclipse.om2m.guestRequestingEntity","guest:guest");
    /** Default resources expiration time. */
    public static final long EXPIRATION_TIME = 999999999;
    /** Default ContentInstances collection maximum number of instance. */
    public static final Long MAX_NBR_OF_INSTANCES = Long.valueOf(System.getProperty("org.eclipse.om2m.maxNrOfInstances","10"));
    //CseBase communication properties
    /** CseBase default communication protocol. */
    public static final String CSE_DEFAULT_PROTOCOL = System.getProperty("org.eclipse.om2m.cseBaseProtocol.default","http");
    /** CseBase ip address. */
    public static final String CSE_IP = System.getProperty("org.eclipse.om2m.cseBaseAddress","127.0.0.1");
    /** CseBase listening port. */
    public static final int CSE_PORT = Integer.parseInt(System.getProperty("org.eclipse.equinox.http.jetty.http.port","8080"));
    /** CseBase coap port. */
    public static final int COAP_PORT = Integer.parseInt(System.getProperty("org.eclipse.om2m.coapPort","5684"));
    /** listening context. */
    public static final String CSE_CONTEXT = System.getProperty("org.eclipse.om2m.cseBaseContext","/api");
    /** M2M Service Provider identifier */
    public static final String M2M_SP_ID = System.getProperty("org.eclipse.om2m.m2mSpId", "om2m.org");
    
    //The following properties are required only for ASN or MN to perform authentication on a remote IN or MN
    /** Connect to the remote CSE (if not IN) */
    public static final boolean CSE_AUTHENTICATION = Boolean.valueOf(System.getProperty("org.eclipse.om2m.cseAuthentication", "true"));
    /** Remote Cse Id. (Required only for MN or ASN)*/
    public static final String REMOTE_CSE_ID = System.getProperty("org.eclipse.om2m.remoteCseId","in-cse");
    /** Remote Cse ip address. (Required only for MN or ASN)*/
    public static final String REMOTE_CSE_IP = System.getProperty("org.eclipse.om2m.remoteCseAddress","127.0.0.1");
    /** Remote Cse listening port. (Required only for MN or ASN)*/
    public static final int REMOTE_CSE_PORT = Integer.parseInt(System.getProperty("org.eclipse.om2m.remoteCsePort","8080"));
    /** Remote Cse listening port. (Required only for MN or ASN)*/
    public static final int REMOTE_CSE_COAP_PORT = Integer.parseInt(System.getProperty("org.eclipse.om2m.remoteCseCoapPort","5683"));
    /** Remote Cse listening context. */
    public static final String REMOTE_CSE_CONTEXT = System.getProperty("org.eclipse.om2m.remoteCseContext","/api");
    /** Remote Cse Name */
    public static final String REMOTE_CSE_NAME = System.getProperty("org.eclipse.om2m.remoteCseName", "in-name");

    //Rest Method names
    /** Retrieve method name. */
    public static final String METHOD_RETREIVE = "RETRIEVE";
    /** Create method name. */
    public static final String METHOD_CREATE = "CREATE";
    /** Update method name. */
    public static final String METHOD_UPDATE = "UPDATE";
    /** Delete method name. */
    public static final String METHOD_DELETE = "DELETE";
    /** Execute method name. */
    public static final String METHOD_EXECUTE = "EXECUTE";

    //Access Control Policy Method names
    /** Create Access Control Policy method name. */
    public static final String ACP_CREATE = "CREATE";
    /** Read Access Control Policy method name. */
    public static final String ACP_READ = "READ";
    /** Write Access Control Policy method name. */
    public static final String ACP_WRITE = "WRITE";
    /** Delete Access Control Policy method name. */
    public static final String ACP_DELETE = "DELETE";
    /** Discover Access Control Policy method name. */
    public static final String ACP_DISCOVER = "DISCOVER";

    //SearchStrings prefixes
    /** Search String resource type prefix. */
    public static final String SEARCH_STRING_RES_TYPE = "ResourceType/";
    /** Search String resource id prefix. */
    public static final String SEARCH_STRING_RES_ID = "ResourceID/";
   
    // Regular expressions
    /** Regular expression for ID of resources */
    public static final String ID_REGEXPR = "^[A-Za-z0-9_-]*$" ;
	/** Prefix separator for IDs */
	public static final String PREFIX_SEPERATOR = System.getProperty("org.eclipse.om2m.resource.idseparator", "-");
	
	// Non blocking supported boolean
	public static final boolean NON_BLOCKING_SUPPORTED = Boolean.parseBoolean(System.getProperty("org.eclipse.om2m.nonblocking", "true"));

	public static final String SP_RELATIVE_URI_SEPARATOR = "~";
	public static final String ABSOLUTE_URI_SEPARATOR = "_";
	
}