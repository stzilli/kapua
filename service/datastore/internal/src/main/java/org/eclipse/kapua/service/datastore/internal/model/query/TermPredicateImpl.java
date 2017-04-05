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

import org.eclipse.kapua.service.datastore.model.query.StorableField;
import org.eclipse.kapua.service.datastore.model.query.TermPredicate;

/**
 * Implementation of query predicate for matching field value
 * 
 * @since 1.0
 *
 */
public class TermPredicateImpl<V> implements TermPredicate<V>
{
    private String field;
    private V        value;

    /**
     * Default constructor
     */
    public TermPredicateImpl()
    {}

    /**
     * Construct a term predicate given the field and value
     * 
     * @param field
     * @param value
     */
    public TermPredicateImpl(String field, V value)
    {
        this.field = field;
        this.value = value;
    }

    @Override
    public String getField()
    {
        return this.field;
    }
    
    @Override
    public void setField(String field)
    {
        this.field = field;
    }

    @Override
    public V getValue()
    {
        return value;
    }

    @Override
    public void setValue(V value)
    {
        this.value = value;
    }
}
