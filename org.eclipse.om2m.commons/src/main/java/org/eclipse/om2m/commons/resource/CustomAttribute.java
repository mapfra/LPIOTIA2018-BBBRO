package org.eclipse.om2m.commons.resource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.eclipse.om2m.commons.constants.ShortName;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(CustomAttributeAdapter.class)
public class CustomAttribute {
	
	private String customAttributeName;
	
	private String customAttributeValue;
	
	private String customAttributeType;

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

	public String getCustomAttributeType() {
		return customAttributeType;
	}

	public void setCustomAttributeType(String customAttributeType) {
		this.customAttributeType = customAttributeType;
	}

	
	
}
