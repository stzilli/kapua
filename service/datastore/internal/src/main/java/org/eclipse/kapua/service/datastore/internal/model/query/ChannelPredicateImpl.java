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
package org.eclipse.kapua.service.datastore.internal.model.query;

import org.eclipse.kapua.service.datastore.model.query.ChannelPredicate;

/**
 * Implementation of query predicate for matching the channel value
 * 
 * @since 1.0.0
 */
public class ChannelPredicateImpl implements ChannelPredicate
{
    private String expression;

    /**
     * Default constructor
     */
    public ChannelPredicateImpl()
    {}

    /**
     * Construct a channel match predicate for the given expression
     * 
     * @param expression
     */
    public ChannelPredicateImpl(String expression)
    {
        this.expression = expression;
    }

    @Override
    public String getExpression()
    {
        return this.expression;
    }

    
    @Override
    public void setExpression(String expression)
    {
        this.expression = expression;
    }
}
