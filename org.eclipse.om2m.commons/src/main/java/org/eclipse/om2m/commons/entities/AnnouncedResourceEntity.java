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
public abstract class AnnouncedResourceEntity extends ResourceEntity {

	@Column(name = ShortName.EXPIRATION_TIME)
	protected String expirationTime;
	@Column(name = ShortName.LINK)
	protected String link;
	
	
	/**
	 * @return the expirationTime
	 */
	public String getExpirationTime() {
		return expirationTime;
	}

	/**
	 * @param expirationTime
	 *            the expirationTime to set
	 */
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * @return the accessControlPolicies
	 */
	public abstract List<AccessControlPolicyEntity> getAccessControlPolicies();

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
