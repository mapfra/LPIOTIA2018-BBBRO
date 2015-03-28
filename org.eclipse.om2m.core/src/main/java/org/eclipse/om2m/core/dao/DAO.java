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

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * The DAO (Data Object Access) Pattern is used to make separation between data
 * access layer and the business layer of an application. It allows better
 * control of changes that might be made on the system of data storage and
 * therefore to prepare a migration from one system to another (DB to XML files
 * for example). This is done by separating data access (BDD) and business
 * objects (POJOs).
 * 
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li> 
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com
 *         ></li> <li>Yassine Banouar < ybanouar@laas.fr > <
 *         yassine.banouar@gmail.com ></li>
 *         </ul>
 */

public abstract class DAO<T> {
	protected static Log LOGGER = LogFactory.getLog(DAO.class);

	/**
	 * Generic create method: persists the resource in the database.
	 * 
	 * @param resource
	 *            - The resource to create
	 * @param em
	 *            - EntityManager used for the transaction
	 */
	public void create(T resource, EntityManager em){
		// for more complex operations, override this method
		em.persist(resource);
	}

	/**
	 * Abstract find resource method in database based on its uri. It returns
	 * collections with sub-resources references.
	 * 
	 * @param uri
	 *            - The uri of the resource to find
	 * @param em
	 *            - EntityManager used for the transaction
	 * @return The resource if it is found otherwise null
	 */
	public abstract T find(String uri, EntityManager em);

	/**
	 * Generic update method: Update the resource in database.
	 * 
	 * @param resource
	 *            - The updated resource.
	 * @param em
	 *            - EntityManager used for the transaction
	 */
	public void update(T resource, EntityManager em) {
		// for more complex operations, override this method
		em.flush();
	}

	/**
	 * Abstract delete resource method from the DataBase without validating the
	 * transaction.
	 * 
	 * @param resource
	 *            - The resource to delete.
	 * @param em
	 *            - The entity manager to use
	 * 
	 */
	public abstract void delete(T resource, EntityManager em);
}
