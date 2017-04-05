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
import javax.xml.bind.annotation.XmlType;

/**
 * {@link StorablePredicate} definition for matching field value.
 * 
 * @since 1.0.0
 * 
 * @param <V>
 *            The type of the value to match.
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { //
        "field", //
        "value" //
}, //
        factoryClass = StorablePredicateXmlRegistry.class, //
        factoryMethod = "newTermPredicate")
public interface TermPredicate<V> extends StorablePredicate {

    /**
     * Gets the field name to match.
     * 
     * @return The field name to match.
     * 
     * @since 1.0.0
     */
    @XmlElement(name = "field")
    public String getField();

    /**
     * Sets the field name to match.
     * 
     * @param name
     *            The field name to match.
     * 
     * @since 1.0.0
     */
    public void setField(String name);

    /**
     * Gets the value to match.
     * 
     * @return The value to match
     * 
     * @since 1.0.0
     */
    @XmlElement(name = "value")
    public V getValue();

    /**
     * Sets the value to match.
     * 
     * @param value
     *            The value to match.
     * 
     * @since 1.0.0
     */
    public void setValue(V value);
}
