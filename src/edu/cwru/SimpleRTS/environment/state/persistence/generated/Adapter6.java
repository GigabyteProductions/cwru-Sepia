//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.14 at 07:33:59 PM EDT 
//


package edu.cwru.SimpleRTS.environment.state.persistence.generated;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import edu.cwru.SimpleRTS.action.ActionType;

public class Adapter6
    extends XmlAdapter<String, ActionType>
{


    public ActionType unmarshal(String value) {
        return (ActionType.valueOf(value));
    }

    public String marshal(ActionType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}