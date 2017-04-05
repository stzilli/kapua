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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * {@link StorablePredicate} to handle AND {@link StorablePredicate}s chaining.
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
public interface AndPredicate extends StorablePredicate {

    /**
     * Get the {@link List} of {@link StorablePredicate}s in AND logic.
     * 
     * @return The {@link List} of {@link StorablePredicate}s.
     * 
     * @since 1.0.0
     */
    @XmlElementWrapper(name = "predicates")
    @XmlElement(name = "predicate")
    public List<StorablePredicate> getPredicates();

    /**
     * Sets the {@link List} of {@link StorablePredicate}s to chain in AND logic.
     * 
     * @param predicates
     *            The {@link List} of {@link StorablePredicate}s to chain
     * 
     * @since 1.0.0
     */
    public void setPredicates(List<StorablePredicate> predicates);

    /**
     * Chains the given {@link StorablePredicate} to this {@link AndPredicate}
     * 
     * @param predicate
     *            The {@link StorablePredicate} to chain.
     * 
     * @return This {@link AndPredicate} to continue chaining.
     * 
     * @since 1.0.0
     */
    public AndPredicate and(StorablePredicate predicate);
}