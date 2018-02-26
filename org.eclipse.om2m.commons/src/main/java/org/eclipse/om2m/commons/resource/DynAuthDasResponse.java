/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour dynAuthDasResponse complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="dynAuthDasResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dynamicACPInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="grantedPrivileges" type="{http://www.onem2m.org/xml/protocols}setOfAcrs" minOccurs="0"/>
 *                   &lt;element name="privilegesLifetime" type="{http://www.onem2m.org/xml/protocols}absRelTimestamp" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="tokens" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction>
 *               &lt;simpleType>
 *                 &lt;list itemType="{http://www.onem2m.org/xml/protocols}dynAuthJWT" />
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
@XmlType(name = "dynAuthDasResponse", propOrder = {
    "dynamicACPInfo",
    "tokens"
})
public class DynAuthDasResponse {

    protected DynAuthDasResponse.DynamicACPInfo dynamicACPInfo;
    @XmlList
    protected List<String> tokens;

    /**
     * Obtient la valeur de la propri�t� dynamicACPInfo.
     * 
     * @return
     *     possible object is
     *     {@link DynAuthDasResponse.DynamicACPInfo }
     *     
     */
    public DynAuthDasResponse.DynamicACPInfo getDynamicACPInfo() {
        return dynamicACPInfo;
    }

    /**
     * D�finit la valeur de la propri�t� dynamicACPInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link DynAuthDasResponse.DynamicACPInfo }
     *     
     */
    public void setDynamicACPInfo(DynAuthDasResponse.DynamicACPInfo value) {
        this.dynamicACPInfo = value;
    }

    /**
     * Gets the value of the tokens property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tokens property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTokens().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getTokens() {
        if (tokens == null) {
            tokens = new ArrayList<String>();
        }
        return this.tokens;
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
     *         &lt;element name="grantedPrivileges" type="{http://www.onem2m.org/xml/protocols}setOfAcrs" minOccurs="0"/>
     *         &lt;element name="privilegesLifetime" type="{http://www.onem2m.org/xml/protocols}absRelTimestamp" minOccurs="0"/>
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
        "grantedPrivileges",
        "privilegesLifetime"
    })
    public static class DynamicACPInfo {

        protected SetOfAcrs grantedPrivileges;
        @XmlSchemaType(name = "anySimpleType")
        protected String privilegesLifetime;

        /**
         * Obtient la valeur de la propri�t� grantedPrivileges.
         * 
         * @return
         *     possible object is
         *     {@link SetOfAcrs }
         *     
         */
        public SetOfAcrs getGrantedPrivileges() {
            return grantedPrivileges;
        }

        /**
         * D�finit la valeur de la propri�t� grantedPrivileges.
         * 
         * @param value
         *     allowed object is
         *     {@link SetOfAcrs }
         *     
         */
        public void setGrantedPrivileges(SetOfAcrs value) {
            this.grantedPrivileges = value;
        }

        /**
         * Obtient la valeur de la propri�t� privilegesLifetime.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrivilegesLifetime() {
            return privilegesLifetime;
        }

        /**
         * D�finit la valeur de la propri�t� privilegesLifetime.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrivilegesLifetime(String value) {
            this.privilegesLifetime = value;
        }

    }

}
