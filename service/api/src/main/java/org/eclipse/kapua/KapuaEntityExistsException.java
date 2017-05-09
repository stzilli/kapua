/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua;

import org.eclipse.kapua.model.id.KapuaId;

/**
 * KapuaEntityExistsException is thrown when an operation cannot be completed because an unique key constraint has been violated.
 * 
 * @since 1.0.0
 */
public class KapuaEntityExistsException extends KapuaRuntimeException {

    private static final long serialVersionUID = -2991079800089173789L;

    private KapuaId id;

    /**
     * Constructor for the {@link KapuaEntityExistsException} taking in the duplicated id.
     * 
     * @param throwable
     *            The original {@link Throwable}
     * @param id
     *            The {@link KapuaId} that conflicts on the insert operation (duplicated key)
     * @since 1.0.0
     *            the key that conflicts on the insert operation (duplicated key)
     */
    public KapuaEntityExistsException(Throwable throwable, KapuaId id) {
        super(KapuaErrorCodes.ENTITY_ALREADY_EXISTS, throwable);
        this.id = id;
    }

    /**
     * Gets the KapuaId that conflicts.
     * 
     * @return The {@link KapuaId} that has caused the original {@link Exception}.
     * @since 1.0.0
     */
    public KapuaId getId() {
        return id;
    }

}
