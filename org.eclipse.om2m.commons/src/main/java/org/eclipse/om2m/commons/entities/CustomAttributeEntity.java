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
	@Column(name=ShortName.RESOURCE_ID)
	protected long resourceID;

	@Column(name= ShortName.CUSTOM_ATTRIBUTE_NAME)
	protected String customAttributeName;
	
	@Column(name = ShortName.CUSTOM_ATTRIBUTE_VALUE)
	protected String customAttributeValue;
	
	@ManyToOne(targetEntity = FlexContainerEntity.class)
	@JoinTable(name = DBEntities.FCNT_CA_JOIN, joinColumns = {
			@JoinColumn(name = DBEntities.CA_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) }, inverseJoinColumns = {
					@JoinColumn(name = DBEntities.FCNT_JOIN_ID, referencedColumnName = ShortName.RESOURCE_ID) })
	protected FlexContainerEntity parentFlexContainer;

	public String getCustomAttributeName() {
		return customAttributeName;
	}

	public void setCustomAttributeName(String customAttributeName) {
		this.customAttributeName = customAttributeName;
	}

	public String getCustomAttributeValue() {
		return customAttributeValue;
	}

	public void setCustomAttributeValue(String customAttributeValue) {
		this.customAttributeValue = customAttributeValue;
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
	
	
	

}
