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

package org.eclipse.om2m.core.dao;

/**
 * Provides some utility methods for database management.
 * @author <ul>
 *         <li>Francois Aissaoui < aissaoui@laas.fr > </li>
 *         <li>Guillaume Garzone < garzone@laas.fr > </li>       
 *         </ul>
 */
public class DBUtil {
	
	private DBUtil() {
		//UTILITY CLASS
	}

	/** Initial request with parameters to replace */
	private static final String LIKE_REQUEST_BASE = "SELECT o FROM :tableName o WHERE o.uri LIKE :uriToLoad";
	
	/**
	 * The method generates a String request for JPA to retrieve objects from a specified 
	 * table and starting with the specified URI.
	 * @param tableName table to select
	 * @param uriToLoad starting uri
	 * @return
	 */
	protected static String generateLikeRequest(String tableName, String uriToLoad){
		String request = LIKE_REQUEST_BASE;
		request = request.replace(":tableName", tableName);
		request = request.replace(":uriToLoad", "'" + uriToLoad + "%'");
		return request ; 
	}
	
	/**
	 * The method generates a String request for JPA to retrieve objects from a specified 
	 * table and starting with the specified URI.
	 * This version also orders the results by creation time.  
	 * @param tableName table to select
	 * @param uriToLoad starting uri
	 * @return
	 */
	protected static String generateLikeRequestOrderByCreationTime(String tableName, String uriToLoad){
		String request = generateLikeRequest(tableName, uriToLoad);
		request += " ORDER BY o.creationTime" ;
		return request ; 
	}
	
}
