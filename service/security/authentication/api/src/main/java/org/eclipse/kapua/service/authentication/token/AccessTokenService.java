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
package org.eclipse.kapua.service.authentication.token;

import org.eclipse.kapua.KapuaException;
import org.eclipse.kapua.model.id.KapuaId;
import org.eclipse.kapua.service.KapuaEntityService;
import org.eclipse.kapua.service.KapuaUpdatableEntityService;
import org.eclipse.kapua.service.authentication.AuthenticationService;

/**
 * {@link AccessTokenService} service API definition.
 * 
 * @since 1.0.0
 */
public interface AccessTokenService extends KapuaEntityService<AccessToken, AccessTokenCreator>, KapuaUpdatableEntityService<AccessToken> {

    /**
     * Find all access token associated with the given userId.
     * 
     * @param scopeId
     *            The scopeId in which to search the scopeId.
     * @param userId
     *            The User {@link KapuaId} for which to search a
     * @return The {@link AccessTokenListResult} found.
     * @throws KapuaException
     * 
     * @since 1.0.0
     */
    public AccessTokenListResult findByUserId(KapuaId scopeId, KapuaId userId)
            throws KapuaException;

    /**
     * Find the access token by the given tokenId.
     * 
     * @param tokenId
     *            The {@link AccessToken#getTokenId()}
     * @return The {@link AccessToken} found, or {@code null} if not found.
     * @throws KapuaException
     * 
     * @since 1.0.0
     */
    public AccessToken findByTokenId(String tokenId)
            throws KapuaException;

    /**
     * Invalidated the {@link AccessToken} by its id. After calling this method the token will be no longer valid and a new
     * {@link AuthenticationService#login(org.eclipse.kapua.service.authentication.LoginCredentials)} invocation is required in order to get a new valid {@link AccessToken}.
     * 
     * @param scopeId
     *            The {@link KapuaId} scopeId of the {@link AccessToken} to delete.
     * @param id
     *            The {@link KapuaId} of the {@link AccessToken} to delete.
     * 
     * @since 1.0.0
     */
    public void invalidate(KapuaId scopeId, KapuaId id) throws KapuaException;
}
