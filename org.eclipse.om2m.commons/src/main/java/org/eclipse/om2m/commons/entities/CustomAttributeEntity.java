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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import org.eclipse.om2m.commons.constants.DBEntities;
import org.eclipse.om2m.commons.constants.ShortName;

@Entity(name = DBEntities.CUSTOM_ATTRIBUTE_ENTITY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class CustomAttributeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = ShortName.RESOURCE_ID)
	protected long resourceID;

	@Column(name = ShortName.CUSTOM_ATTRIBUTE_NAME)
	protected String name;

	@Column(name = ShortName.CUSTOM_ATTRIBUTE_TYPE)
	protected String type;
	
	@Column(name = ShortName.CUSTOM_ATTRIBUTE_VALUE)
	protected String value;
	
	@ManyToOne(targetEntity = FlexContainerEntity.class)
	@JoinTable(name = DBEntities.FCNT_CA_JOIN, joinColumns = {
			@JoinColumn(name = DBEntities.CA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, inverseJoinColumns = {
					@JoinColumn(name = DBEntities.FCNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) })
	protected FlexContainerEntity parentFlexContainer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public FlexContainerEntity getParentFlexContainer() {
		return parentFlexContainer;
	}

	public void setParentFlexContainer(FlexContainerEntity parentFlexContainer) {
		this.parentFlexContainer = parentFlexContainer;
	}

	public long getResourceID() {
		return resourceID;
	}

	public void setResourceID(long resourceID) {
		this.resourceID = resourceID;
	}

	@Override
	public String toString() {
		return "<CustomAttributeEntity " + name + "/" + type + "/" + value + "/>";
	}

}
