/*******************************************************************************
 * Copyright (c) 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.app.console.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.eclipse.kapua.app.console.server.util.KapuaExceptionHandler;
import org.eclipse.kapua.app.console.shared.GwtKapuaException;
import org.eclipse.kapua.app.console.shared.model.GwtGroupedNVPair;
import org.eclipse.kapua.app.console.shared.model.GwtXSRFToken;
import org.eclipse.kapua.app.console.shared.model.authentication.GwtCredential;
import org.eclipse.kapua.app.console.shared.model.authentication.GwtCredentialCreator;
import org.eclipse.kapua.app.console.shared.model.authentication.GwtCredentialQuery;
import org.eclipse.kapua.app.console.shared.service.GwtCredentialService;
import org.eclipse.kapua.app.console.shared.util.GwtKapuaModelConverter;
import org.eclipse.kapua.app.console.shared.util.KapuaGwtModelConverter;
import org.eclipse.kapua.commons.security.KapuaSecurityUtils;
import org.eclipse.kapua.locator.KapuaLocator;
import org.eclipse.kapua.model.id.KapuaId;
import org.eclipse.kapua.service.authentication.AuthenticationService;
import org.eclipse.kapua.service.authentication.CredentialsFactory;
import org.eclipse.kapua.service.authentication.LoginCredentials;
import org.eclipse.kapua.service.authentication.credential.Credential;
import org.eclipse.kapua.service.authentication.credential.CredentialCreator;
import org.eclipse.kapua.service.authentication.credential.CredentialFactory;
import org.eclipse.kapua.service.authentication.credential.CredentialListResult;
import org.eclipse.kapua.service.authentication.credential.CredentialQuery;
import org.eclipse.kapua.service.authentication.credential.CredentialService;
import org.eclipse.kapua.service.authentication.credential.CredentialType;
import org.eclipse.kapua.service.authentication.shiro.KapuaAuthenticationErrorCodes;
import org.eclipse.kapua.service.authentication.shiro.KapuaAuthenticationException;
import org.eclipse.kapua.service.authentication.shiro.utils.AuthenticationUtils;
import org.eclipse.kapua.service.authentication.shiro.utils.CryptAlgorithm;
import org.eclipse.kapua.service.user.User;
import org.eclipse.kapua.service.user.UserService;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class GwtCredentialServiceImpl extends KapuaConfigurableRemoteServiceServlet<CredentialService> implements GwtCredentialService {

    private static final long serialVersionUID = 7323313459749361320L;

    protected GwtCredentialServiceImpl() {
        super(KapuaLocator.getInstance().getService(CredentialService.class));
    }

    @Override
    public PagingLoadResult<GwtCredential> query(PagingLoadConfig loadConfig, GwtCredentialQuery gwtCredentialQuery) throws GwtKapuaException {
        //
        // Do query
        int totalLength = 0;
        List<GwtCredential> gwtCredentials = new ArrayList<GwtCredential>();

        Map<KapuaId, User> usersCache = new HashMap<KapuaId, User>();
        try {
            KapuaLocator locator = KapuaLocator.getInstance();
            CredentialService credentialService = locator.getService(CredentialService.class);
            UserService userService = locator.getService(UserService.class);

            // Convert from GWT entity
            CredentialQuery credentialQuery = GwtKapuaModelConverter.convertCredentialQuery(loadConfig, gwtCredentialQuery);

            // query
            CredentialListResult credentials = credentialService.query(credentialQuery);

            // If there are results
            if (!credentials.isEmpty()) {
                // count
                if (credentials.getSize() >= loadConfig.getLimit()) {
                    totalLength = Long.valueOf(credentialService.count(credentialQuery)).intValue();
                } else {
                    totalLength = credentials.getSize();
                }

                // Convert to GWT entity
                for (Credential credential : credentials.getItems()) {
                    User user;
                    if (!usersCache.containsKey(credential.getUserId())) {
                        user = userService.find(credential.getScopeId(), credential.getUserId());
                        usersCache.put(credential.getUserId(), user);
                    } else {
                        user = usersCache.get(credential.getUserId());
                    }
                    gwtCredentials.add(KapuaGwtModelConverter.convert(credential, user));
                }
            }

        } catch (Throwable t) {
            KapuaExceptionHandler.handle(t);
        }

        return new BasePagingLoadResult<GwtCredential>(gwtCredentials, loadConfig.getOffset(), totalLength);
    }

    @Override
    public void delete(GwtXSRFToken xsrfToken, String stringAccountId, String gwtCredentialId) throws GwtKapuaException {
        checkXSRFToken(xsrfToken);

        try {
            KapuaLocator locator = KapuaLocator.getInstance();
            CredentialService credentialService = locator.getService(CredentialService.class);
            KapuaId scopeId = GwtKapuaModelConverter.convert(stringAccountId);
            KapuaId credentialId = GwtKapuaModelConverter.convert(gwtCredentialId);
            credentialService.delete(scopeId, credentialId);
        } catch (Throwable t) {
            KapuaExceptionHandler.handle(t);
        }
    }

    @Override
    public GwtCredential create(GwtXSRFToken xsrfToken, GwtCredentialCreator gwtCredentialCreator) throws GwtKapuaException {
        //
        // Checking XSRF token
        checkXSRFToken(xsrfToken);

        //
        // Do create
        GwtCredential gwtCredential = null;
        try {
            // Convert from GWT Entity
            CredentialCreator credentialCreator = GwtKapuaModelConverter.convert(gwtCredentialCreator);

            // Create
            KapuaLocator locator = KapuaLocator.getInstance();
            CredentialService credentialService = locator.getService(CredentialService.class);
            UserService userService = locator.getService(UserService.class);
            Credential credential = credentialService.create(credentialCreator);
            User user = userService.find(GwtKapuaModelConverter.convert(gwtCredentialCreator.getScopeId()), credential.getUserId());
            // Convert
            gwtCredential = KapuaGwtModelConverter.convert(credential, user);

        } catch (Throwable t) {
            KapuaExceptionHandler.handle(t);
        }

        //
        // Return result
        return gwtCredential;
    }

    @Override
    public GwtCredential update(GwtXSRFToken gwtXsrfToken, GwtCredential gwtCredential) throws GwtKapuaException {
        //
        // Checking XSRF token
        checkXSRFToken(gwtXsrfToken);

        //
        // Do update
        GwtCredential gwtCredentialUpdated = null;
        try {
            KapuaId scopeId = GwtKapuaModelConverter.convert(gwtCredential.getScopeId());
            KapuaId credentialId = GwtKapuaModelConverter.convert(gwtCredential.getId());
            // Update
            KapuaLocator locator = KapuaLocator.getInstance();
            CredentialService credentialService = locator.getService(CredentialService.class);
            UserService userService = locator.getService(UserService.class);

            Credential currentCredential = credentialService.find(scopeId, credentialId);
            if (gwtCredential.getCredentialKey() != null && !gwtCredential.getCredentialKey().trim().equals("")) {
                String encryptedPass = AuthenticationUtils.cryptCredential(CryptAlgorithm.BCRYPT, gwtCredential.getCredentialKey());
                currentCredential.setCredentialKey(encryptedPass);
            }
            Credential credentialUpdated = credentialService.update(currentCredential);
            User user = userService.find(credentialUpdated.getScopeId(), credentialUpdated.getUserId());
            // Convert
            gwtCredentialUpdated = KapuaGwtModelConverter.convert(credentialUpdated, user);

        } catch (Throwable t) {
            KapuaExceptionHandler.handle(t);
        }

        //
        // Return result
        return gwtCredentialUpdated;
    }

    @Override
    public ListLoadResult<GwtGroupedNVPair> getCredentialDescription(String scopeShortId, String credentialShortId) throws GwtKapuaException {
        //
        // Do get
        List<GwtGroupedNVPair> gwtCredentialDescription = new ArrayList<GwtGroupedNVPair>();
        try {
            KapuaLocator locator = KapuaLocator.getInstance();
            CredentialService credentialService = locator.getService(CredentialService.class);

            // Convert from GWT Entity
            KapuaId scopeId = GwtKapuaModelConverter.convert(scopeShortId);
            KapuaId credentialId = GwtKapuaModelConverter.convert(credentialShortId);

            // Find
            Credential credential = credentialService.find(scopeId, credentialId);

            // If there are results
            if (credential != null) {
                gwtCredentialDescription.add(new GwtGroupedNVPair("Entity", "Scope Id", KapuaGwtModelConverter.convert(credential.getScopeId())));
                gwtCredentialDescription.add(new GwtGroupedNVPair("Entity", "Id", KapuaGwtModelConverter.convert(credential.getId())));
                gwtCredentialDescription.add(new GwtGroupedNVPair("Entity", "Created On", credential.getCreatedOn()));
                gwtCredentialDescription.add(new GwtGroupedNVPair("Entity", "Created By", KapuaGwtModelConverter.convert(credential.getCreatedBy())));
                gwtCredentialDescription.add(new GwtGroupedNVPair("Entity", "Modified On", credential.getModifiedOn()));
                gwtCredentialDescription.add(new GwtGroupedNVPair("Entity", "Modified By", KapuaGwtModelConverter.convert(credential.getModifiedBy())));
                gwtCredentialDescription.add(new GwtGroupedNVPair("Credential", "Credential Type", credential.getCredentialType().toString()));
                gwtCredentialDescription.add(new GwtGroupedNVPair("Credential", "User ID", KapuaGwtModelConverter.convert(credential.getUserId())));
            }

        } catch (Throwable t) {
            KapuaExceptionHandler.handle(t);
        }

        return new BaseListLoadResult<GwtGroupedNVPair>(gwtCredentialDescription);
    }

    @Override
    public void changePassword(GwtXSRFToken gwtXsrfToken, String oldPassword, final String newPassword, String stringUserId, String stringScopeId) throws GwtKapuaException {
        try {
            KapuaLocator locator = KapuaLocator.getInstance();
            AuthenticationService authenticationService = locator.getService(AuthenticationService.class);
            UserService userService = locator.getService(UserService.class);
            final CredentialService credentialsService = locator.getService(CredentialService.class);
            final CredentialFactory credentialFactory = locator.getFactory(CredentialFactory.class);
            final CredentialsFactory credentialsFactory = locator.getFactory(CredentialsFactory.class);

            final KapuaId scopeId = GwtKapuaModelConverter.convert(stringScopeId);
            final KapuaId userId = GwtKapuaModelConverter.convert(stringUserId);

            User user = userService.find(scopeId, userId);
            final String username = user.getName();
            LoginCredentials loginCredentials = credentialsFactory.newUsernamePasswordCredentials(username, oldPassword.toCharArray());

            boolean validPassword = authenticationService.verifyCredentials(loginCredentials);

            if (validPassword) {
                KapuaSecurityUtils.doPrivileged(new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        CredentialListResult credentialsList = credentialsService.findByUserId(scopeId, userId);
                        Credential oldCredential = null;
                        for (Credential credential : credentialsList.getItems()) {
                            if (credential.getCredentialType().equals(CredentialType.PASSWORD)) {
                                oldCredential = credential;
                                break;
                            }
                        }
                        if (oldCredential != null) {
                            credentialsService.delete(scopeId, oldCredential.getId());
                            CredentialCreator newCredentialCreator = credentialFactory.newCreator(scopeId, userId, CredentialType.PASSWORD, newPassword);
                            credentialsService.create(newCredentialCreator);
                        } else {
                        }
                        return null;
                    }
                });
            } else {
                throw new KapuaAuthenticationException(KapuaAuthenticationErrorCodes.INVALID_LOGIN_CREDENTIALS, null, String.format("Wrong existing password for user %s", username));
            }
        } catch (Throwable t) {
            KapuaExceptionHandler.handle(t);
        }
    }

}
