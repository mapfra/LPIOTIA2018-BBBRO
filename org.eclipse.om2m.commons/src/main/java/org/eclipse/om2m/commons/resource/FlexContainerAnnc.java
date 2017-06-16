package org.eclipse.om2m.commons.resource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.om2m.commons.constants.ShortName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = ShortName.FCNTA)
public class FlexContainerAnnc extends AnnouncedResource {
	@XmlElement(name = ShortName.STATETAG, required = true)
	@XmlSchemaType(name = "nonNegativeInteger")
	protected BigInteger stateTag;
	@XmlElement(name = ShortName.CREATOR, required = true)
	protected String creator;
	@XmlSchemaType(name = "anyURI")
	@XmlElement(name = ShortName.ONTOLOGY_REF)
	protected String ontologyRef;
	@XmlSchemaType(name = "anyURI")
	@XmlElement(name = ShortName.CONTAINER_DEFINITION)
	protected String containerDefinition;
	@XmlElement(name = ShortName.CHILD_RESOURCE)
	protected List<ChildResourceRef> childResource;
	@XmlElements({
			@XmlElement(name = ShortName.CNT, namespace = "http://www.onem2m.org/xml/protocols", type = Container.class),
			@XmlElement(name = ShortName.FCNT, namespace = "http://www.onem2m.org/xml/protocols", type = AbstractFlexContainer.class),
			@XmlElement(name = ShortName.SUB, namespace = "http://www.onem2m.org/xml/protocols", type = Subscription.class) })
	protected List<Resource> flexContainerOrContainerOrSubscription;

	@XmlAnyElement()
	protected List<CustomAttribute> customAttributes;

	public List<CustomAttribute> getCustomAttributes() {
		if (customAttributes == null) {
			customAttributes = new ArrayList<CustomAttribute>();
		}
		return customAttributes;
	}

	public void setCustomAttributes(List<CustomAttribute> customAttributes) {
		this.customAttributes = customAttributes;
	}

	@XmlTransient
	public List<String> getCustomAttributeNames() {
		List<String> names = new ArrayList<String>();

		for (CustomAttribute ca : getCustomAttributes()) {
			names.add(ca.getCustomAttributeName());
		}

		return names;
	}

	@XmlTransient
	public CustomAttribute getCustomAttribute(String name) {
		for (CustomAttribute ca : getCustomAttributes()) {
			if (ca.getCustomAttributeName().equals(name)) {
				return ca;
			}
		}
		return null;
	}

	/**
	 * Gets the value of the stateTag property.
	 * 
	 * @return possible object is {@link BigInteger }
	 * 
	 */
	public BigInteger getStateTag() {
		return stateTag;
	}

	/**
	 * Sets the value of the stateTag property.
	 * 
	 * @param value
	 *            allowed object is {@link BigInteger }
	 * 
	 */
	public void setStateTag(BigInteger value) {
		this.stateTag = value;
	}

	/**
	 * Gets the value of the creator property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Sets the value of the creator property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCreator(String value) {
		this.creator = value;
	}

	/**
	 * Gets the value of the ontologyRef property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOntologyRef() {
		return ontologyRef;
	}

	/**
	 * Sets the value of the ontologyRef property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setOntologyRef(String value) {
		this.ontologyRef = value;
	}

	/**
	 * Gets the value of the containerDefinition property.
	 * 
	 * @return object is {@link String}
	 */
	public String getContainerDefinition() {
		return containerDefinition;
	}

	/**
	 * Sets the value of the containerDefinition property.
	 * 
	 * @param value
	 *            allowed object is {@link String}
	 */
	public void setContainerDefinition(String value) {
		this.containerDefinition = value;
	}

	/**
	 * Gets the value of the childResource property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the childResource property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getChildResource().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link ChildResourceRef }
	 * 
	 * 
	 */
	public List<ChildResourceRef> getChildResource() {
		if (childResource == null) {
			childResource = new ArrayList<ChildResourceRef>();
		}
		return this.childResource;
	}

	/**
	 * Gets the value of the flexContainerOrContainerOrSubscription property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the flexContainerOrContainerOrSubscription
	 * property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getFlexContainerOrContainerOrSubscription().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link AbstractFlexContainer } {@link Container } {@link Subscription }
	 * 
	 * 
	 */
	public List<Resource> getFlexContainerOrContainerOrSubscription() {
		if (flexContainerOrContainerOrSubscription == null) {
			flexContainerOrContainerOrSubscription = new ArrayList<Resource>();
		}
		return this.flexContainerOrContainerOrSubscription;
	}
}
