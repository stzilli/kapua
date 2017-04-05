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
 * {@link StorablePredicate} definition for matching a channel name.
 * It supports using of wildcard as last channel level.
 * E.g.:
 * <ul>
 * <li>test/channel/#</li>
 * </ul>
 * 
 * @since 1.0.0
 */
@XmlRootElement(name = "channelPredicate")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { //
        "expression"//
}, //
        factoryClass = StorablePredicateXmlRegistry.class, //
        factoryMethod = "newChannelPredicate")
public interface ChannelPredicate extends StorablePredicate {

    /**
     * Get the channel expression (may use wildcard)
     * 
     * @return The channel expression to match.
     * 
     * @since 1.0.0
     */
    @XmlElement(name = "expression")
    public String getExpression();

    /**
     * Set the channel expression (may use wildcard)
     * 
     * @param expression
     *            The channel expression to match.
     * 
     * @since 1.0.0
     */
    public void setExpression(String expression);
}
