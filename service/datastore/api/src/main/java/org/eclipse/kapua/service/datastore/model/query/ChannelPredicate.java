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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Query predicate definition for matching the channel value
 * 
 * @since 1.0
 *
 */
public interface ChannelPredicate extends StorablePredicate
{
    /**
     * Get the channel expression (may use wildcard)
     * 
     * @return
     * 
     * @since 1.0.0
     */
    public String getExpression();
    
    /**
     * Set the channel expression (may use wildcard)
     * 
     * @param expression
     * 
     * @since 1.0.0
     */
    public void setExpression(String expression);
}
