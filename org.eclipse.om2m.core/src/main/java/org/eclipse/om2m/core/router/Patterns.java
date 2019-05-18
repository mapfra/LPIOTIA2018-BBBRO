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

package org.eclipse.om2m.core.router;

import java.util.regex.Pattern;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ShortName;
import org.eclipse.om2m.persistence.service.DAO;
import org.eclipse.om2m.persistence.service.DBService;

/**
 * Patterns used as regular expressions and conditions
 *
 */
public class Patterns implements Constants {
	
//	private static Log LOGGER = LogFactory.getLog(Patterns.class);

	/** All short name for filtering */
	private static final String ALL_SHORT_NAMES = ShortName.ACP + "|" + ShortName.AE + "|" + ShortName.CNT +
			"|" + ShortName.CIN + "|" + ShortName.REMOTE_CSE + "|" + ShortName.LATEST + "|" + ShortName.OLDEST +
			"|" + ShortName.GROUP + "|" + ShortName.FANOUTPOINT + "|" + ShortName.SUB + "|" + ShortName.PCH + 
			"|" + ShortName.POLLING_CHANNEL_URI + "|" + ShortName.REQ + 
			"|" + ShortName.NODE + "|" + ShortName.MGO + 
			"|" + ShortName.FCNT + "|" + ShortName.DAC;
	
//	private static final String NON_HIERARCHICAL_ID = "(" + Constants.PREFIX_SEPERATOR +"(\\b\\w+\\b)?)" ;
//	
//	private static final Pattern UNAUTHORIZED_NAMES = Pattern.compile(ShortName.ACP + NON_HIERARCHICAL_ID + "?|" + 
//					ShortName.AE + NON_HIERARCHICAL_ID + "?|" + ShortName.CNT + NON_HIERARCHICAL_ID + "?|" +
//					ShortName.CIN + NON_HIERARCHICAL_ID + "?|" + ShortName.REMOTE_CSE + NON_HIERARCHICAL_ID + "?|" +
//					ShortName.LATEST + NON_HIERARCHICAL_ID + "?|" + ShortName.OLDEST + NON_HIERARCHICAL_ID + "?|" +
//					ShortName.GROUP + NON_HIERARCHICAL_ID + "?|" + ShortName.FANOUTPOINT + NON_HIERARCHICAL_ID + "?|" +
//					ShortName.SUB + NON_HIERARCHICAL_ID + "?|" + ShortName.PCH + "?|" + 
//					ShortName.POLLING_CHANNEL_URI + "?|" + ShortName.REQ + "?|" + 
//					ShortName.NODE + "?|" + ShortName.MGO + "?|" + 
//					ShortName.FCNT + "?|" + ShortName.DAC + "?");
	
	/** Main id string */
	public final String ID_STRING = "([A-Za-z0-9_\\-~#]|\\.)+";
	
	/** Main id pattern */
	public final Pattern ID_PATTERN = Pattern.compile(ID_STRING);
	
    /** CseBase resource uri pattern. */
    public final Pattern CSE_BASE_PATTERN= Pattern.compile("/" + Constants.CSE_ID);
    
    /** AccessControlPolicy uri pattern MAY BE NOT COMPLETE */
    public final Pattern ACP_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.ACP + Constants.PREFIX_SEPERATOR + ID_STRING );
    
    public final Pattern AE_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + "(C|S)" + ID_STRING);
    
    public final Pattern AEANNC_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.AE_ANNC + ID_STRING);
    
    public final Pattern CONTAINER_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.CNT + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern DYNAMIC_AUTHORIZATION_CONSULTATION_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.DAC + Constants.PREFIX_SEPERATOR + ID_STRING);

    public final Pattern FLEXCONTAINER_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.FCNT + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern FLEXCONTAINER_ANNC_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.FCNTA + Constants.PREFIX_SEPERATOR + ID_STRING);

    public final Pattern CONTENTINSTANCE_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.CIN + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern REMOTE_CSE_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.REMOTE_CSE + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern GROUP_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.GROUP + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern SUBSCRIPTION_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.SUB + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern POLLING_CHANNEL_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.PCH + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern POLLING_CHANNEL_URI_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.POLLING_CHANNEL_URI + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern REQUEST_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.REQ + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern NON_RETARGETING_PATTERN = Pattern.compile("/" + Constants.CSE_ID + "(/("+ID_STRING+")?)*"); 
    
    public final String FANOUT_POINT_MATCH = "/" + ShortName.FANOUTPOINT ;
    
    public final Pattern NODE_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.NODE + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern NODE_ANNC_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.NODE_ANNC + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern NMGMT_OBJ_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.MGO + Constants.PREFIX_SEPERATOR + ID_STRING);
    
    public final Pattern NMGMT_OBJ_ANNC_PATTERN = Pattern.compile(CSE_BASE_PATTERN + "/" + ShortName.MGOA + Constants.PREFIX_SEPERATOR + ID_STRING);

    /** Non-hierarchical URI pattern */
    public final Pattern NON_HIERARCHICAL_PATTERN = Pattern.compile(
    		"(" + CSE_BASE_PATTERN + "/(" + ALL_SHORT_NAMES + ")" + Constants.PREFIX_SEPERATOR + ID_STRING + ")|(" + CSE_BASE_PATTERN+ ")|" +
    		AE_PATTERN.pattern()); 
    
    /** Hierarchical URI Pattern */
    public final Pattern HIERARCHICAL_PATTERN = Pattern.compile(
    		CSE_BASE_PATTERN + "(/" + Constants.CSE_NAME + "(/"+ ID_PATTERN +")*)?"
    		);
    
	/**
	 * match uri with a pattern.
	 * @param pattern - pattern
	 * @param uri - resource uri
	 * @return true if matched, otherwise false.
	 */
	public boolean match(Pattern pattern, String uri) {
	    // Match uri with pattern
		return pattern.matcher(uri).matches();
	}
	
	/**
	 * Retrieve the corresponding DAO from the URI
	 * @param uri uri of the targetted resource
	 * @param db database service
	 * @return DAO corresponding to the resource, null if not found
	 */
	public DAO<?> getDAO(String uri, DBService db){
		if (match(CSE_BASE_PATTERN, uri)){
			return db.getDAOFactory().getCSEBaseDAO();
		}
		if (match(ACP_PATTERN, uri)){
			return db.getDAOFactory().getAccessControlPolicyDAO();
		}
		if (match(AE_PATTERN,uri)){
			return db.getDAOFactory().getAeDAO();
		}
		if (match(AEANNC_PATTERN, uri)) {
			return db.getDAOFactory().getAeAnncDAO();
		}
		if(match(CONTAINER_PATTERN, uri)){
			return db.getDAOFactory().getContainerDAO();
		}
		if (match(DYNAMIC_AUTHORIZATION_CONSULTATION_PATTERN, uri)) {
			return db.getDAOFactory().getDynamicAuthorizationDAO();
		}
		if (match(FLEXCONTAINER_PATTERN, uri)) {
			return db.getDAOFactory().getFlexContainerDAO();
		}
		if (match(FLEXCONTAINER_ANNC_PATTERN, uri)) {
			return db.getDAOFactory().getFlexContainerAnncDAO();
		}
		if(match(CONTENTINSTANCE_PATTERN, uri)) {
			return db.getDAOFactory().getContentInstanceDAO();
		}
		if(match(REMOTE_CSE_PATTERN, uri)) {
			return db.getDAOFactory().getRemoteCSEDAO();
		}
		if(match(GROUP_PATTERN, uri)){
			return db.getDAOFactory().getGroupDAO();
		}
		if(match(SUBSCRIPTION_PATTERN, uri)){
			return db.getDAOFactory().getSubsciptionDAO();
		}
		if(match(POLLING_CHANNEL_PATTERN, uri)){
			return db.getDAOFactory().getPollingChannelDAO();
		}
		if(match(REQUEST_PATTERN, uri)){
			return db.getDAOFactory().getRequestEntityDAO();
		}
		if (match(NODE_PATTERN, uri)) {
			return db.getDAOFactory().getNodeDAO();
		}
		if (match(NMGMT_OBJ_PATTERN, uri)) {
			return db.getDAOFactory().getMgmtObjDAO();
		}
		if (match(NODE_ANNC_PATTERN, uri)) {
			return db.getDAOFactory().getNodeAnncDAO();
		}
		if (match(NMGMT_OBJ_ANNC_PATTERN, uri)) {
			return db.getDAOFactory().getMgmtObjAnncDAO();
		}
		return null;
	}

	/**
	 * Method used to check the validity of the resource name provided
	 * @param resourceName
	 * @return
	 */
	public boolean checkResourceName(String resourceName){
		return match(ID_PATTERN, resourceName);
	}
}
