/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 *
 * @author Hachiko
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
//@JsonSubTypes({
//@JsonSubTypes.(value = TextMessage.class, name = "TextMessage"),
//@JsonSubTypes.Type(value = Picture.class, name = "Picture"),
//@JsonSubTypes.Type(value = File.class, name = "File"),
//})
public interface Content {
    
}
