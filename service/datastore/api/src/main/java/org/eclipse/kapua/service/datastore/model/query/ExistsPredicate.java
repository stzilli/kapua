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
 * {@link StorablePredicate} definition for checking if a field exists
 * 
 * @since 1.0.0
 */
@XmlRootElement(name = "existsPredicate")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { //
        "name"//
        }, //
    factoryClass = StorablePredicateXmlRegistry.class, //
    factoryMethod = "newExistsPredicate")
public interface ExistsPredicate extends StorablePredicate
{

    /**
     * Gets the field name to check existence of.
     * 
     * @return The field name to check existence of.
     * 
     * @since 1.0.0
     */
    @XmlElement(name = "name")
    public String getName();
    
    /**
     * Sets the field name to check existence of.
     * 
     * @param name The field name to check existence of.
     * 
     * @since 1.0.0
     */
    public void setName(String name);
}
