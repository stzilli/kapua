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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Query "and" aggregation definition
 * 
 * @since 1.0.0
 */
@XmlRootElement(name = "andPredicate")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { //
        "predicates"//
        }, //
    factoryClass = StorablePredicateXmlRegistry.class, //
    factoryMethod = "newAndPredicate")
public interface AndPredicate extends StorablePredicate
{

    /**
     * Get the {@link List} of {@link StorablePredicate}s 
     * 
     * @return
     * 
     * @since 1.0.0
     */
    @XmlElement(name = "predicates")
    public List<StorablePredicate> getPredicates();
    
    /**
     * Sets the {@link List} of {@link StorablePredicate}s
     * @param predicates
     * 
     * @since 1.0.0
     */
    public void setPredicates(List<StorablePredicate> predicates);
    
 
    /**
     * Chains the given {@link StorablePredicate} to this {@link AndPredicate}
     * 
     * @param The {@link StorablePredicate} to chain.
     * 
     * @return This {@link AndPredicate} to continue chaining.
     * 
     * @since 1.0.0
     */
    public AndPredicate and(StorablePredicate predicates);
}