/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.eclipse.om2m.commons.constants.DBEntities;

@Entity(name=DBEntities.ANNOUNCED_RESOURCE_ENTITY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CreatedAnnouncedResourceEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name=DBEntities.REMOTE_RESOURCE_ID)
	private String remoteAnnouncedId;
	
	@Column(name=DBEntities.LOCAL_RESOURCE_ID)
	private String localAnnounceableId;
	
	@Column(name=DBEntities.ANNOUNCE_CSE_ID)
	private String announceCseId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRemoteAnnouncedId() {
		return remoteAnnouncedId;
	}

	public void setRemoteAnnouncedId(String remoteAnnouncedId) {
		this.remoteAnnouncedId = remoteAnnouncedId;
	}

	public String getLocalAnnounceableId() {
		return localAnnounceableId;
	}

	public void setLocalAnnounceableId(String localAnnounceableId) {
		this.localAnnounceableId = localAnnounceableId;
	}

	public String getAnnounceCseId() {
		return announceCseId;
	}

	public void setAnnounceCseId(String announceCseId) {
		this.announceCseId = announceCseId;
	}
	
	
	
	

}
