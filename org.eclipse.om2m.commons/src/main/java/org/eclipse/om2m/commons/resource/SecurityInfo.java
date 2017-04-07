//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2017.03.21 à 06:52:45 PM CET 
//


package org.eclipse.om2m.commons.resource;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour securityInfo complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propriété securityInfoType.
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
     * Définit la valeur de la propriété securityInfoType.
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
     * Obtient la valeur de la propriété dasRequest.
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
     * Définit la valeur de la propriété dasRequest.
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
     * Obtient la valeur de la propriété dasResponse.
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
     * Définit la valeur de la propriété dasResponse.
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
     * Obtient la valeur de la propriété esprimRandObject.
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
     * Définit la valeur de la propriété esprimRandObject.
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
     * Obtient la valeur de la propriété esprimObject.
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
     * Définit la valeur de la propriété esprimObject.
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
     * Obtient la valeur de la propriété escertkeMessage.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getEscertkeMessage() {
        return escertkeMessage;
    }

    /**
     * Définit la valeur de la propriété escertkeMessage.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setEscertkeMessage(byte[] value) {
        this.escertkeMessage = value;
    }

}
