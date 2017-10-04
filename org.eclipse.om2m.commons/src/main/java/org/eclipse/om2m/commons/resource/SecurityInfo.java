/*******************************************************************************
 * Copyright (c) 2014, 2016 Orange.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.om2m.commons.resource;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour securityInfo complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="securityInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="securityInfoType" type="{http://www.onem2m.org/xml/protocols}securityInfoType" minOccurs="0"/>
 *         &lt;element name="dasRequest" type="{http://www.onem2m.org/xml/protocols}dynAuthDasRequest" minOccurs="0"/>
 *         &lt;element name="dasResponse" type="{http://www.onem2m.org/xml/protocols}dynAuthDasResponse" minOccurs="0"/>
 *         &lt;element name="esprimRandObject" type="{http://www.onem2m.org/xml/protocols}receiverESPrimRandObject" minOccurs="0"/>
 *         &lt;element name="esprimObject" type="{http://www.onem2m.org/xml/protocols}e2eCompactJWE" minOccurs="0"/>
 *         &lt;element name="escertkeMessage" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "securityInfo", propOrder = {
    "securityInfoType",
    "dasRequest",
    "dasResponse",
    "esprimRandObject",
    "esprimObject",
    "escertkeMessage"
})
public class SecurityInfo {

    protected BigInteger securityInfoType;
    protected DynAuthDasRequest dasRequest;
    protected DynAuthDasResponse dasResponse;
    protected ReceiverESPrimRandObject esprimRandObject;
    protected String esprimObject;
    protected byte[] escertkeMessage;

    /**
     * Obtient la valeur de la propri�t� securityInfoType.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSecurityInfoType() {
        return securityInfoType;
    }

    /**
     * D�finit la valeur de la propri�t� securityInfoType.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSecurityInfoType(BigInteger value) {
        this.securityInfoType = value;
    }

    /**
     * Obtient la valeur de la propri�t� dasRequest.
     * 
     * @return
     *     possible object is
     *     {@link DynAuthDasRequest }
     *     
     */
    public DynAuthDasRequest getDasRequest() {
        return dasRequest;
    }

    /**
     * D�finit la valeur de la propri�t� dasRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link DynAuthDasRequest }
     *     
     */
    public void setDasRequest(DynAuthDasRequest value) {
        this.dasRequest = value;
    }

    /**
     * Obtient la valeur de la propri�t� dasResponse.
     * 
     * @return
     *     possible object is
     *     {@link DynAuthDasResponse }
     *     
     */
    public DynAuthDasResponse getDasResponse() {
        return dasResponse;
    }

    /**
     * D�finit la valeur de la propri�t� dasResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link DynAuthDasResponse }
     *     
     */
    public void setDasResponse(DynAuthDasResponse value) {
        this.dasResponse = value;
    }

    /**
     * Obtient la valeur de la propri�t� esprimRandObject.
     * 
     * @return
     *     possible object is
     *     {@link ReceiverESPrimRandObject }
     *     
     */
    public ReceiverESPrimRandObject getEsprimRandObject() {
        return esprimRandObject;
    }

    /**
     * D�finit la valeur de la propri�t� esprimRandObject.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceiverESPrimRandObject }
     *     
     */
    public void setEsprimRandObject(ReceiverESPrimRandObject value) {
        this.esprimRandObject = value;
    }

    /**
     * Obtient la valeur de la propri�t� esprimObject.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsprimObject() {
        return esprimObject;
    }

    /**
     * D�finit la valeur de la propri�t� esprimObject.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsprimObject(String value) {
        this.esprimObject = value;
    }

    /**
     * Obtient la valeur de la propri�t� escertkeMessage.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEscertkeMessage() {
        return escertkeMessage;
    }

    /**
     * D�finit la valeur de la propri�t� escertkeMessage.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEscertkeMessage(byte[] value) {
        this.escertkeMessage = value;
    }

}
