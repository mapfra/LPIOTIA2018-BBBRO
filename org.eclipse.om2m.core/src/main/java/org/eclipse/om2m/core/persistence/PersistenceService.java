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
package org.eclipse.om2m.core.persistence;

import java.util.concurrent.Semaphore;

import org.eclipse.om2m.persistence.service.DBService;

/**
 * Persistence service to handle persistence initialization
 *
 */
public class PersistenceService {
	
	private DBService dbService ;
	private Semaphore dbReady = new Semaphore(0);
	
	private static PersistenceService service = new PersistenceService();
	
	public static PersistenceService getInstance() {
		return service;
	}
	
	private PersistenceService() {
	}

	public DBService getDbService() {
		return dbService;
	}

	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	/**
	 * @return the dbReady
	 */
	public Semaphore getDbReady() {
		return dbReady;
	}
	
	public void resetSemaphoreDb(){
		dbReady = new Semaphore(0);
	}

}
