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
 *     Red Hat Inc
 *******************************************************************************/
package org.eclipse.kapua.commons.util;

import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Date utilities
 *
 * @since 1.0.0
 */
public final class KapuaDateUtils {

    private KapuaDateUtils() {
    }

    private static final String DEFAULT_DATE_PATTERN = "MM/dd/yyyy h:mm a"; // example 24/01/2017 11:22 AM

    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern(DEFAULT_DATE_PATTERN, getKapuaLocale())
            .withZone(getTimeZone());

    /**
     * Private empty constructor.
     * All methods are static.
     * 
     * @since 1.0.0
     */
    private KapuaDateUtils() {
    }

    /**
     * Get current date
     *
     * @return current date.
     * @since 1.0.0
     */
    public static Instant getKapuaSysDate() {
        return Instant.now();
    }

    /**
     * Gets the {@link Locale}.
     * 
     * @return The current {@link Locale}.
     * 
     * @since 1.0.0
     */
    public static Locale getKapuaLocale() {
        return Locale.US;
    }

    /**
     * Gets the timezone.
     * 
     * @return The timezone.
     * 
     * @since 1.0.0
     */
    public static ZoneId getTimeZone() {
        return ZoneOffset.UTC;
    }

    /**
     * Parse the provided String using the default pattern {@value #DEFAULT_DATE_PATTERN}
     *
     * @param date
     *            The {@link String} formatted date.
     * @return The parsed {@link Instant}.
     * @throws ParseException
     * 
     * @since 1.0.0
     */
    public static Instant parseDate(String date) throws ParseException {
        return Instant.from(formatter.parse(date));
    }
}
