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
 * Query predicate for matching range values implementation
 * 
 * @since 1.0
 *
 */
public interface RangePredicate<V extends Comparable<V>> extends StorablePredicate
{
    /**
     * Get the field to be compared
     * 
     * @return
     */
    public String getField();

    /**
     * 
     * @param storableField
     */
    public void setField(String storableField); 
    
    /**
     * Return the minimum value
     * 
     * @return
     */
    public V getMinValue();

    /**
     * Sets the minimum value
     * 
     * @param minValue The minimum value to set.
     * 
     * @since 1.0.0
     */
    public void setMinValue(V minValue);
    
    /**
     * Get the maximum value.
     * 
     * @return The maximum value.
     * 
     * @since 1.0.0
     */
    public V getMaxValue();
    
    /**
     * Sets the maximum value.
     * 
     * @param maxValue The max value to set.
     * 
     * @since 1.0.0
     */
    public void setMaxValue(V maxValue);
}
