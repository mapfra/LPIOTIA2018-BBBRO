//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2017.03.21 à 06:52:45 PM CET 
//


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
 * <p>Classe Java pour receiverESPrimRandObject complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="receiverESPrimRandObject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esprimRandID" type="{http://www.w3.org/2001/XMLSchema}NCName"/>
 *         &lt;element name="esprimRandValue" type="{http://www.w3.org/2001/XMLSchema}NCName"/>
 *         &lt;element name="esprimRandExpiry" type="{http://www.onem2m.org/xml/protocols}absRelTimestamp"/>
 *         &lt;element name="esprimKeyGenAlgIDs">
 *           &lt;simpleType>
 *             &lt;restriction>
 *               &lt;simpleType>
 *                 &lt;list itemType="{http://www.onem2m.org/xml/protocols}esprimKeyGenAlgID" />
 *               &lt;/simpleType>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="esprimProtocolAndAlgIDs">
 *           &lt;simpleType>
 *             &lt;restriction>
 *               &lt;simpleType>
 *                 &lt;list itemType="{http://www.onem2m.org/xml/protocols}esprimProtocolAndAlgID" />
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
@XmlType(name = "receiverESPrimRandObject", propOrder = {
    "esprimRandID",
    "esprimRandValue",
    "esprimRandExpiry",
    "esprimKeyGenAlgIDs",
    "esprimProtocolAndAlgIDs"
})
public class ReceiverESPrimRandObject {

    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String esprimRandID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String esprimRandValue;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String esprimRandExpiry;
    @XmlList
    @XmlElement(required = true)
    protected List<BigInteger> esprimKeyGenAlgIDs;
    @XmlList
    @XmlElement(required = true)
    protected List<BigInteger> esprimProtocolAndAlgIDs;

    /**
     * Obtient la valeur de la propriété esprimRandID.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsprimRandID() {
        return esprimRandID;
    }

    /**
     * Définit la valeur de la propriété esprimRandID.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsprimRandID(String value) {
        this.esprimRandID = value;
    }

    /**
     * Obtient la valeur de la propriété esprimRandValue.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsprimRandValue() {
        return esprimRandValue;
    }

    /**
     * Définit la valeur de la propriété esprimRandValue.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsprimRandValue(String value) {
        this.esprimRandValue = value;
    }

    /**
     * Obtient la valeur de la propriété esprimRandExpiry.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsprimRandExpiry() {
        return esprimRandExpiry;
    }

    /**
     * Définit la valeur de la propriété esprimRandExpiry.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsprimRandExpiry(String value) {
        this.esprimRandExpiry = value;
    }

    /**
     * Gets the value of the esprimKeyGenAlgIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esprimKeyGenAlgIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsprimKeyGenAlgIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getEsprimKeyGenAlgIDs() {
        if (esprimKeyGenAlgIDs == null) {
            esprimKeyGenAlgIDs = new ArrayList<BigInteger>();
        }
        return this.esprimKeyGenAlgIDs;
    }

    /**
     * Gets the value of the esprimProtocolAndAlgIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esprimProtocolAndAlgIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsprimProtocolAndAlgIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BigInteger }
     * 
     * 
     */
    public List<BigInteger> getEsprimProtocolAndAlgIDs() {
        if (esprimProtocolAndAlgIDs == null) {
            esprimProtocolAndAlgIDs = new ArrayList<BigInteger>();
        }
        return this.esprimProtocolAndAlgIDs;
    }

}
