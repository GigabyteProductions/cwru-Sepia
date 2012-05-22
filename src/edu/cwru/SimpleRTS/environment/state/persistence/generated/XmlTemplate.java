//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.21 at 03:51:56 PM EDT 
//


package edu.cwru.SimpleRTS.environment.state.persistence.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Template complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Template">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="foodCost" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="goldCost" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="woodCost" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="timeCost" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="upgradePrerequisite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;/sequence>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="unitPrerequisite" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Template", propOrder = {
    "id",
    "foodCost",
    "goldCost",
    "woodCost",
    "timeCost",
    "name",
    "upgradePrerequisite",
    "unitPrerequisite"
})
@XmlSeeAlso({
    XmlUnitTemplate.class,
    XmlUpgradeTemplate.class
})
public abstract class XmlTemplate {

    @XmlElement(name = "ID")
    protected int id;
    protected int foodCost;
    protected int goldCost;
    protected int woodCost;
    protected int timeCost;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(type = Integer.class)
    protected List<Integer> upgradePrerequisite;
    @XmlElement(type = Integer.class)
    protected List<Integer> unitPrerequisite;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setID(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the foodCost property.
     * 
     */
    public int getFoodCost() {
        return foodCost;
    }

    /**
     * Sets the value of the foodCost property.
     * 
     */
    public void setFoodCost(int value) {
        this.foodCost = value;
    }

    /**
     * Gets the value of the goldCost property.
     * 
     */
    public int getGoldCost() {
        return goldCost;
    }

    /**
     * Sets the value of the goldCost property.
     * 
     */
    public void setGoldCost(int value) {
        this.goldCost = value;
    }

    /**
     * Gets the value of the woodCost property.
     * 
     */
    public int getWoodCost() {
        return woodCost;
    }

    /**
     * Sets the value of the woodCost property.
     * 
     */
    public void setWoodCost(int value) {
        this.woodCost = value;
    }

    /**
     * Gets the value of the timeCost property.
     * 
     */
    public int getTimeCost() {
        return timeCost;
    }

    /**
     * Sets the value of the timeCost property.
     * 
     */
    public void setTimeCost(int value) {
        this.timeCost = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the upgradePrerequisite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the upgradePrerequisite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpgradePrerequisite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getUpgradePrerequisite() {
        if (upgradePrerequisite == null) {
            upgradePrerequisite = new ArrayList<Integer>();
        }
        return this.upgradePrerequisite;
    }

    /**
     * Gets the value of the unitPrerequisite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the unitPrerequisite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUnitPrerequisite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getUnitPrerequisite() {
        if (unitPrerequisite == null) {
            unitPrerequisite = new ArrayList<Integer>();
        }
        return this.unitPrerequisite;
    }

}
