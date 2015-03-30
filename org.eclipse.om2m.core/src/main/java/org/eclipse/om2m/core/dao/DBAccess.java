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
 *     Guillaume Garzone - Conception, implementation, test and documentation.
 *     Francois Aissaoui - Conception, implementation, test and documentation.
 ******************************************************************************/

package org.eclipse.om2m.core.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.constants.Constants;
import org.eclipse.persistence.config.PersistenceUnitProperties;
/**
 * Class handling the connection to the database using JPA - EclipseLink. 
 * It satisfies the singleton pattern. 
 * @author <ul>
 *         <li>Francois Aissaoui < aissaoui@laas.fr > </li>
 *         <li>Guillaume Garzone < garzone@laas.fr > </li>       
 *         </ul>
 */
public class DBAccess {

	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(DBAccess.class);
	
	/** Local instance of the object */
	private static DBAccess dbAccess = new DBAccess();
	
	/** EntityManagerFactory connected to the DB */
	private EntityManagerFactory emf ; 
	
	/**
	 * Private Main constructor initializing the database.
	 */
	private DBAccess(){
	}
	
	/**
	 * Gets an instance of the current class.
	 * The first time, it loads all informations from persistence.xml
	 * and initializes the database.
	 * @return instance of DBAccess
	 */
	public static DBAccess getInstance(){
		return dbAccess;
	}
	
	/**
	 * Returns an EntityManager to access the database.
	 * @return Entity Manager 
	 */
	public static EntityManager createEntityManager(){
		return getInstance().emf.createEntityManager();
	}

	/**
	 * Closes the connection to the database.
	 */
	public void close() {
		if (emf != null){
			emf.close();
		}
	}
	
	public void init(){
		LOGGER.info("Intializing DataBase...");
		try{			
			Map<Object,Object> properties = new HashMap<Object, Object>() ; 
			properties.put(PersistenceUnitProperties.JDBC_DRIVER, Constants.DB_DRIVER);
			properties.put(PersistenceUnitProperties.JDBC_URL, Constants.DB_URL);
			properties.put(PersistenceUnitProperties.JDBC_USER, Constants.DB_USER);
			properties.put(PersistenceUnitProperties.JDBC_PASSWORD, Constants.DB_PASSWORD);
			
			if (Constants.DB_RESET){
				properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE);
			} else {
				properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
			}
			
			LOGGER.info("Creating new EntityManagerFactory...");
			emf = Persistence.createEntityManagerFactory("om2mdb", properties);
		} catch (Exception e){
			LOGGER.error("Error in creation of EntityManagerFactory",e);
		}
		if (emf != null){
			LOGGER.info("DataBase initialized.");
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			em.close();
		} else {
			LOGGER.error("ERROR initializing Database: EntityManagerFactory is null!");
		}
	}
	
}
