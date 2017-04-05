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
 * Query predicate definition for checking if a field exists
 * 
 * @since 1.0.0
 */
public interface ExistsPredicate extends StorablePredicate
{

    /**
     * Gets the field name to check
     * 
     * @return
     * 
     * @since 1.0.0
     */
    public String getName();
    
    /**
     * Sets the field name to check
     * 
     * @param name
     * 
     * @since 1.0.0
     */
    public void setName(String name);
}
