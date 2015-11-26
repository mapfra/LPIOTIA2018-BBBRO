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
 *     Khalil Drira - Management and initial specification.
 *     Guillaume Garzone - Initial specification, conception, implementation, test
 *         and documentation.
 *     François Aïssaoui - Initial specification, conception, implementation, test
 *         and documentation.
 *******************************************************************************/
package org.eclipse.om2m.persistence.eclipselink.internal;

import javax.persistence.EntityManager;

import org.eclipse.om2m.persistence.service.DBTransaction;

public class DBTransactionJPAImpl implements DBTransaction {
	
	private EntityManager em ;

	/** Constructor */
	public DBTransactionJPAImpl() {
	}

	@Override
	public void open() {
		if (em == null) {
			em = DBServiceJPAImpl.createEntityManager();
		}
		em.getTransaction().begin();
	}

	@Override
	public void commit() {
		em.getTransaction().commit();
	}

	@Override
	public void close() {
		em.close();
		em = null;
	}

	@Override
	public void clear() {
		em.clear();
	}

	public EntityManager getEm() {
		return em;
	}

}
