//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.21 at 03:51:56 PM EDT 
//


package edu.cwru.SimpleRTS.environment.state.persistence.generated;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import edu.cwru.SimpleRTS.model.resource.ResourceType;

public class Adapter3
    extends XmlAdapter<String, ResourceType>
{


    public ResourceType unmarshal(String value) {
        return (ResourceType.valueOf(value));
    }

    public String marshal(ResourceType value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
