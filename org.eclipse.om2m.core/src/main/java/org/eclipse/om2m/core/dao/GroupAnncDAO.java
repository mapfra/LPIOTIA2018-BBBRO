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
 * 		conception and documentation.
 *     Mahdi Ben Alaya (Project co-founder) - Management and initial specification, 
 * 		conception, implementation, test and documentation.
 *     Christophe Chassot - Management and initial specification.
 *     Khalil Drira - Management and initial specification.
 *     Yassine Banouar - Initial specification, conception, implementation, test 
 * 		and documentation.
 *     Guillaume Garzone - Conception, implementation, test and documentation.
 *     Francois Aissaoui - Conception, implementation, test and documentation.
 ******************************************************************************/
package org.eclipse.om2m.core.dao;

import javax.persistence.EntityManager;

import org.eclipse.om2m.commons.resource.GroupAnnc;

/**
 * Implements CRUD Methods for {@link GroupAnnc} resource persistence.
 *
 * @author <ul>
 *         <li>Yessine Feki < yfeki@laas.fr > < yessine.feki@ieee.org ></li>
 *         <li>Mahdi Ben Alaya < ben.alaya@laas.fr > < benalaya.mahdi@gmail.com
 *         ></li>
 *         <li>Yassine Banouar < ybanouar@laas.fr > < yassine.banouar@gmail.com
 *         ></li>
 *         </ul>
 */
public class GroupAnncDAO extends DAO<GroupAnnc> {

	/**
	 * Retrieves the {@link GroupAnnc} resource from the Database based on its
	 * uri
	 * 
	 * @param uri
	 *            - uri of the {@link GroupAnnc} resource to retrieve
	 * @return The requested {@link GroupAnnc} resource otherwise null
	 */
	public GroupAnnc find(String uri, EntityManager em) {
		// Return the resource from the DB
		return em.find(GroupAnnc.class, uri);
	}

	/**
	 * Deletes the {@link GroupAnnc} resource from the DataBase without
	 * validating the transaction
	 * 
	 * @param resource
	 *            - The {@link GroupAnnc} resource to delete
	 */
	public void delete(GroupAnnc resource, EntityManager em) {
		// Delete the resource
		em.remove(resource);
	}
}
