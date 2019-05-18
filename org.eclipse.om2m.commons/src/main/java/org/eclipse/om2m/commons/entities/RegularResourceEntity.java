/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.eclipse.om2m.commons.constants.ShortName;

@MappedSuperclass
public abstract class RegularResourceEntity extends ResourceEntity {
	@Column(name=ShortName.EXPIRATION_TIME)
	protected String expirationTime;
	
	/**
	 * Gets the value of the expirationTime property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getExpirationTime() {
		return expirationTime;
	}

	/**
	 * Sets the value of the expirationTime property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setExpirationTime(String value) {
		this.expirationTime = value;
	}
	
	/**
	 * @return the accessControlPolicies
	 */
	public abstract  List<AccessControlPolicyEntity> getAccessControlPolicies();

	/**
	 * @param accessControlPolicies
	 *            the accessControlPolicies to set
	 */
	public abstract void setAccessControlPolicies(
			List<AccessControlPolicyEntity> accessControlPolicies);
	
	/**
	 * retrieves DynamicAuthorizationConsultations
	 * @return 
	 */
	public abstract List<DynamicAuthorizationConsultationEntity> getDynamicAuthorizationConsultations();
	
	/**
	 * Set dynamicAuthorizationConsultations
	 */
	public abstract void setDynamicAuthorizationConsultations(List<DynamicAuthorizationConsultationEntity> list);
}
