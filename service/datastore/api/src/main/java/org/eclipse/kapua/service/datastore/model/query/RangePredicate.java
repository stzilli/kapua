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
import javax.xml.bind.annotation.XmlType;

/**
 * {@link StorablePredicate} for matching values in a range.
 * 
 * @since 1.0.0
 *
 * @param <V>
 *            The type of the objetc to compare. It must extends {@link Comparable}.
 */
@XmlRootElement(name = "rangePredicate")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { //
        "field", //
        "min", //
        "max"
}, //
        factoryClass = StorablePredicateXmlRegistry.class, //
        factoryMethod = "newRangePredicate")
public interface RangePredicate<V extends Comparable<V>> extends StorablePredicate {

    /**
     * Gets the field name to be compared.
     * 
     * @return The field name to be compared
     * 
     * @since 1.0.0
     */
    @XmlElement(name = "field")
    public String getField();

    /**
     * Sets the field name to compare.
     * 
     * @param field
     *            The field name to compare.
     * 
     * @since 1.0.0
     */
    public void setField(String field);

    /**
     * Gets the minimum value for filtered results.
     * 
     * @return The minimum value for filtered results.
     * 
     * @since 1.0.0
     */
    @XmlElement(name = "min")
    public V getMinValue();

    /**
     * Sets the minimum value for filtered results.
     * 
     * @param minValue
     *            The minimum value to set for filtering results.
     * 
     * @since 1.0.0
     */
    public void setMinValue(V minValue);

    /**
     * Gets the maximum value for filtered results.
     * 
     * @return The maximum value for filtered results.
     * 
     * @since 1.0.0
     */
    @XmlElement(name = "max")
    public V getMaxValue();

    /**
     * Sets the maximum value for filtered results.
     * 
     * @param maxValue
     *            The max value to set for filtering results
     * 
     * @since 1.0.0
     */
    public void setMaxValue(V maxValue);
}
