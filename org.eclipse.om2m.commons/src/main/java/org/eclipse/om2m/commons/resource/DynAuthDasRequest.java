/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java pour dynAuthDasRequest complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="dynAuthDasRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="originator" type="{http://www.onem2m.org/xml/protocols}ID"/>
 *         &lt;element name="targetedResourceType" type="{http://www.onem2m.org/xml/protocols}resourceType"/>
 *         &lt;element name="operation" type="{http://www.onem2m.org/xml/protocols}operation"/>
 *         &lt;element name="originatorIP" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ipv4Address" type="{http://www.onem2m.org/xml/protocols}ipv4" minOccurs="0"/>
 *                   &lt;element name="ipv6Address" type="{http://www.onem2m.org/xml/protocols}ipv6" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="originatorLocation" type="{http://www.onem2m.org/xml/protocols}locationRegion" minOccurs="0"/>
 *         &lt;element name="originatorRoleIDs" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction>
 *               &lt;simpleType>
 *                 &lt;list itemType="{http://www.onem2m.org/xml/protocols}roleID" />
 *               &lt;/simpleType>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="requestTimestamp" type="{http://www.onem2m.org/xml/protocols}absRelTimestamp" minOccurs="0"/>
 *         &lt;element name="targetedResourceID" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="proposedPrivilegesLifetime" type="{http://www.onem2m.org/xml/protocols}absRelTimestamp" minOccurs="0"/>
 *         &lt;element name="roleIDsFromACPs" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction>
 *               &lt;simpleType>
 *                 &lt;list itemType="{http://www.onem2m.org/xml/protocols}roleID" />
 *               &lt;/simpleType>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="tokenIDs" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction>
 *               &lt;simpleType>
 *                 &lt;list itemType="{http://www.onem2m.org/xml/protocols}tokenID" />
 *               &lt;/simpleType>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dynAuthDasRequest", propOrder = {
    "originator",
    "targetedResourceType",
    "operation",
    "originatorIP",
    "originatorLocation",
    "originatorRoleIDs",
    "requestTimestamp",
    "targetedResourceID",
    "proposedPrivilegesLifetime",
    "roleIDsFromACPs",
    "tokenIDs"
})
public class DynAuthDasRequest {

    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String originator;
    @XmlElement(required = true)
    protected BigInteger targetedResourceType;
    @XmlElement(required = true)
    protected BigInteger operation;
    protected DynAuthDasRequest.OriginatorIP originatorIP;
    protected LocationRegion originatorLocation;
    @XmlList
    protected List<String> originatorRoleIDs;
    @XmlSchemaType(name = "anySimpleType")
    protected String requestTimestamp;
    @XmlSchemaType(name = "anyURI")
    protected String targetedResourceID;
    @XmlSchemaType(name = "anySimpleType")
    protected String proposedPrivilegesLifetime;
    @XmlList
    protected List<String> roleIDsFromACPs;
    @XmlList
    protected List<String> tokenIDs;

    /**
     * Obtient la valeur de la propri�t� originator.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * D�finit la valeur de la propri�t� originator.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginator(String value) {
        this.originator = value;
    }

    /**
     * Obtient la valeur de la propri�t� targetedResourceType.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTargetedResourceType() {
        return targetedResourceType;
    }

    /**
     * D�finit la valeur de la propri�t� targetedResourceType.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTargetedResourceType(BigInteger value) {
        this.targetedResourceType = value;
    }

    /**
     * Obtient la valeur de la propri�t� operation.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOperation() {
        return operation;
    }

    /**
     * D�finit la valeur de la propri�t� operation.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOperation(BigInteger value) {
        this.operation = value;
    }

    /**
     * Obtient la valeur de la propri�t� originatorIP.
     * 
     * @return
     *     possible object is
     *     {@link DynAuthDasRequest.OriginatorIP }
     *     
     */
    public DynAuthDasRequest.OriginatorIP getOriginatorIP() {
        return originatorIP;
    }

    /**
     * D�finit la valeur de la propri�t� originatorIP.
     * 
     * @param value
     *     allowed object is
     *     {@link DynAuthDasRequest.OriginatorIP }
     *     
     */
    public void setOriginatorIP(DynAuthDasRequest.OriginatorIP value) {
        this.originatorIP = value;
    }

    /**
     * Obtient la valeur de la propri�t� originatorLocation.
     * 
     * @return
     *     possible object is
     *     {@link LocationRegion }
     *     
     */
    public LocationRegion getOriginatorLocation() {
        return originatorLocation;
    }

    /**
     * D�finit la valeur de la propri�t� originatorLocation.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationRegion }
     *     
     */
    public void setOriginatorLocation(LocationRegion value) {
        this.originatorLocation = value;
    }

    /**
     * Gets the value of the originatorRoleIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the originatorRoleIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOriginatorRoleIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOriginatorRoleIDs() {
        if (originatorRoleIDs == null) {
            originatorRoleIDs = new ArrayList<String>();
        }
        return this.originatorRoleIDs;
    }

    /**
     * Obtient la valeur de la propri�t� requestTimestamp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    /**
     * D�finit la valeur de la propri�t� requestTimestamp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestTimestamp(String value) {
        this.requestTimestamp = value;
    }

    /**
     * Obtient la valeur de la propri�t� targetedResourceID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetedResourceID() {
        return targetedResourceID;
    }

    /**
     * D�finit la valeur de la propri�t� targetedResourceID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetedResourceID(String value) {
        this.targetedResourceID = value;
    }

    /**
     * Obtient la valeur de la propri�t� proposedPrivilegesLifetime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProposedPrivilegesLifetime() {
        return proposedPrivilegesLifetime;
    }

    /**
     * D�finit la valeur de la propri�t� proposedPrivilegesLifetime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProposedPrivilegesLifetime(String value) {
        this.proposedPrivilegesLifetime = value;
    }

    /**
     * Gets the value of the roleIDsFromACPs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleIDsFromACPs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleIDsFromACPs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getRoleIDsFromACPs() {
        if (roleIDsFromACPs == null) {
            roleIDsFromACPs = new ArrayList<String>();
        }
        return this.roleIDsFromACPs;
    }

    /**
     * Gets the value of the tokenIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tokenIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTokenIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTokenIDs() {
        if (tokenIDs == null) {
            tokenIDs = new ArrayList<String>();
        }
        return this.tokenIDs;
    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ipv4Address" type="{http://www.onem2m.org/xml/protocols}ipv4" minOccurs="0"/>
     *         &lt;element name="ipv6Address" type="{http://www.onem2m.org/xml/protocols}ipv6" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ipv4Address",
        "ipv6Address"
    })
    public static class OriginatorIP {

        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String ipv4Address;
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String ipv6Address;

        /**
         * Obtient la valeur de la propri�t� ipv4Address.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIpv4Address() {
            return ipv4Address;
        }

        /**
         * D�finit la valeur de la propri�t� ipv4Address.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIpv4Address(String value) {
            this.ipv4Address = value;
        }

        /**
         * Obtient la valeur de la propri�t� ipv6Address.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIpv6Address() {
            return ipv6Address;
        }

        /**
         * D�finit la valeur de la propri�t� ipv6Address.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIpv6Address(String value) {
            this.ipv6Address = value;
        }

    }

}
