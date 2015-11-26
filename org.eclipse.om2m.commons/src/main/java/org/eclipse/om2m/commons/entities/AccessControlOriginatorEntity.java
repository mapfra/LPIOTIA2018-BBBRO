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
package org.eclipse.om2m.commons.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.eclipse.om2m.commons.constants.DBEntities;

/**
 * Access Control Originator JPA Entity
 *
 */
@Entity(name=DBEntities.ACCESSCONTROLORIGINATOR_ENTITY)
public class AccessControlOriginatorEntity {
	
	@Id
	protected String originatorID;

	public AccessControlOriginatorEntity() {	
	}
	
	public AccessControlOriginatorEntity(String originatorID){
		this.originatorID = originatorID;
	}
	
	/**
	 * @param originatorID the originatorID to set
	 */
	public void setOriginatorID(String originatorID) {
		this.originatorID = originatorID;
	}

	/**
	 * @return the originatorID
	 */
	public String getOriginatorID() {
		return originatorID;
	}
	
	

}
