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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpgradeTemplate complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpgradeTemplate">
 *   &lt;complexContent>
 *     &lt;extension base="{}Template">
 *       &lt;sequence>
 *         &lt;element name="basicAttackChange" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="piercingAttackChange" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="armorChange" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="healthChange" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="sightRangeChange" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="rangeChange" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="affectedUnitTypes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpgradeTemplate", propOrder = {
    "basicAttackChange",
    "piercingAttackChange",
    "armorChange",
    "healthChange",
    "sightRangeChange",
    "rangeChange",
    "affectedUnitTypes"
})
public class XmlUpgradeTemplate
    extends XmlTemplate
{

    protected int basicAttackChange;
    protected int piercingAttackChange;
    protected int armorChange;
    protected int healthChange;
    protected int sightRangeChange;
    protected int rangeChange;
    @XmlElement(type = Integer.class)
    protected List<Integer> affectedUnitTypes;

    /**
     * Gets the value of the basicAttackChange property.
     * 
     */
    public int getBasicAttackChange() {
        return basicAttackChange;
    }

    /**
     * Sets the value of the basicAttackChange property.
     * 
     */
    public void setBasicAttackChange(int value) {
        this.basicAttackChange = value;
    }

    /**
     * Gets the value of the piercingAttackChange property.
     * 
     */
    public int getPiercingAttackChange() {
        return piercingAttackChange;
    }

    /**
     * Sets the value of the piercingAttackChange property.
     * 
     */
    public void setPiercingAttackChange(int value) {
        this.piercingAttackChange = value;
    }

    /**
     * Gets the value of the armorChange property.
     * 
     */
    public int getArmorChange() {
        return armorChange;
    }

    /**
     * Sets the value of the armorChange property.
     * 
     */
    public void setArmorChange(int value) {
        this.armorChange = value;
    }

    /**
     * Gets the value of the healthChange property.
     * 
     */
    public int getHealthChange() {
        return healthChange;
    }

    /**
     * Sets the value of the healthChange property.
     * 
     */
    public void setHealthChange(int value) {
        this.healthChange = value;
    }

    /**
     * Gets the value of the sightRangeChange property.
     * 
     */
    public int getSightRangeChange() {
        return sightRangeChange;
    }

    /**
     * Sets the value of the sightRangeChange property.
     * 
     */
    public void setSightRangeChange(int value) {
        this.sightRangeChange = value;
    }

    /**
     * Gets the value of the rangeChange property.
     * 
     */
    public int getRangeChange() {
        return rangeChange;
    }

    /**
     * Sets the value of the rangeChange property.
     * 
     */
    public void setRangeChange(int value) {
        this.rangeChange = value;
    }

    /**
     * Gets the value of the affectedUnitTypes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the affectedUnitTypes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAffectedUnitTypes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Integer }
     * 
     * 
     */
    public List<Integer> getAffectedUnitTypes() {
        if (affectedUnitTypes == null) {
            affectedUnitTypes = new ArrayList<Integer>();
        }
        return this.affectedUnitTypes;
    }

}
