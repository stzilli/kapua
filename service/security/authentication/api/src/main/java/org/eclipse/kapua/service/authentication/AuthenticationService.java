/*******************************************************************************
 * Copyright (c) 2011, 2016 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.service.authentication;

import org.eclipse.kapua.KapuaException;
import org.eclipse.kapua.service.KapuaService;
import org.eclipse.kapua.service.authentication.token.AccessToken;

/**
 * {@link AuthenticationService} exposes APIs to manage User object under an Account.<br>
 * It includes APIs to create, update, find, list and delete Users.<br>
 * Instances of the UserService can be acquired through the ServiceLocator.
 * 
 * @since 1.0.0
 */
public interface AuthenticationService extends KapuaService {

    /**
     * Login the provided user login credentials on the system (if the credentials are valid)
     * 
     * @param loginCredentials
     * @return
     * @throws KapuaException
     *             An exception is thrown if the credentials are not found on the system, are expired or are disabled.
     * @since 1.0.0
     */
    public AccessToken login(LoginCredentials loginCredentials)
            throws KapuaException;

    /**
     * Checks if the provided {@link SessionCredentials} are valid.
     * If valid the related session will be restored.
     * 
     * @param sessionCredentials
     *            The {@link SessionCredentials} to validate.
     * @throws KapuaException
     *             An exception is thrown if the credentials are not found on the system, are expired or are disabled.
     * @since 1.0.0
     */
    public void authenticate(SessionCredentials sessionCredentials)
            throws KapuaException;

    /**
     * Logout the current logged user.
     * 
     * @throws KapuaException
     * 
     * @since 1.0.0
     */
    public void logout()
            throws KapuaException;

    /**
     * Return the {@link AccessToken} identified by the provided token identifier
     * 
     * @param tokenId
     * @return The {@link AccessToken} that matches the given tokenId.
     * @throws KapuaException
     *             If no {@link AccessToken} is found for that token identifier.
     * @since 1.0.0
     */
    public AccessToken findAccessToken(String tokenId)
            throws KapuaException;
    
    /**
     * Refresh an {@link AccessToken}.
     * 
     * An {@link AccessToken#getRefreshToken()} is a token that can be used to get a new {@link AccessToken}.
     * The refresh token has a longer TTL but cannot be used to authenticate using {@link #authenticate(SessionCredentials)}.
     * 
     * @param tokenId
     *            The session token. This token can be expired.
     * @param refreshToken
     *            The refresh token that can be used to refresh the session token.
     * @return The new {@link AccessToken} which refers to a new refreshed session.
     * @throws KapuaException
     *             An exception is thrown if the refresh token is expired or if the session token is not found.
     * 
     * @since 1.0.0
     */
    public AccessToken refreshAccessToken(String tokenId, String refreshToken)
            throws KapuaException;

}
