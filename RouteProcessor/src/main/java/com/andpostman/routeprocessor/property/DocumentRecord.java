//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.11.03 at 03:54:15 PM MSK 
//


package com.andpostman.routeprocessor.property;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="Document"/>
 *       &lt;/sequence>
 *       &lt;attribute name="RecId" type="{http://www.w3.org/2001/XMLSchema}hexBinary" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "document"
})
@XmlRootElement(name = "DocumentRecord")
@Data
public class DocumentRecord implements Serializable {

    @XmlElement(name = "Document", required = true)
    protected LotusDocumentType document;
    @XmlAttribute(name = "RecId")
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] recId;

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link LotusDocumentType }
     *     
     */
    public LotusDocumentType getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link LotusDocumentType }
     *     
     */
    public void setDocument(LotusDocumentType value) {
        this.document = value;
    }

    /**
     * Gets the value of the recId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getRecId() {
        return recId;
    }

    /**
     * Sets the value of the recId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecId(byte[] value) {
        this.recId = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Document", document)
                .append("RecID",recId)
                .toString();
    }

}
