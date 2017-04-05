/*******************************************************************************
 * Copyright (c) 2011, 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.service.datastore.model.query;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Query predicate definition for matching field value
 * 
 * @since 1.0
 *
 */
@XmlRootElement(name = "termPredicate")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { //
        "field",
        "value"}, //
    factoryClass = StorablePredicateXmlRegistry.class, //
    factoryMethod = "newTermPredicate")
public interface TermPredicate<V> extends StorablePredicate
{

    /**
     * Return the field
     * 
     * @return
     */
    @XmlElement(name = "field")
    public String getField();
    
    public void setField(String name);

    /**
     * Return the value
     * 
     * @return
     */
    @XmlElement(name = "value")
    public V getValue();
    
    public void setValue(V value);
}
