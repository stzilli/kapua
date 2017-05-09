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
package org.eclipse.kapua;

/**
 * {@link KapuaIllegalArgumentException} is thrown when the value of a method parameter is invalid.
 *
 * @since 1.0.0
 */
public class KapuaIllegalArgumentException extends KapuaException {

    private static final long serialVersionUID = -7067191169730223113L;

    /**
     * Constructor.
     *
     * @param code
     *            The code of this {@link KapuaIllegalArgumentException}.
     * @param argName
     *            The argument name that is invalid.
     * @param argValue
     *            The argument value that is invalid.
     * 
     * @since 1.0.0
     */
    protected KapuaIllegalArgumentException(KapuaErrorCodes code, String argName, String argValue) {
        super(code, argName, argValue);
    }

    /**
     * Constructor.
     *
     * @param argName
     *            The argument name that is invalid.
     * @param argValue
     *            The argument value that is invalid.
     * 
     * @since 1.0.0
     */
    public KapuaIllegalArgumentException(String argName, String argValue) {
        this(KapuaErrorCodes.ILLEGAL_ARGUMENT, argName, argValue);
    }
}
