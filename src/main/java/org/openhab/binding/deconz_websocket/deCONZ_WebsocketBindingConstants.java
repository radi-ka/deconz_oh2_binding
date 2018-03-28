/* Copyright (c) 2014,2018 by the respective copyright holders.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.deconz_websocket;

import com.google.common.collect.ImmutableSet;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

import java.util.Set;

/**
 * The {@link deCONZ_WebsocketBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Mirco Kahle - Initial contribution
 */
@NonNullByDefault
public class deCONZ_WebsocketBindingConstants {

    // List of all Channel ids
    public static final String CHANNEL_RAWSTATES = "allEvents";
    public static final String CHANNEL_TEMPERATURE = "temperature";
    public static final String CHANNEL_HUMIDITY = "humidity";
    public static final String CHANNEL_OPENCLOSED = "openclosed";
    public static final String CHANNEL_PRESENCE = "presence";
    private static final String BINDING_ID = "deconz_websocket";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_ALL = new ThingTypeUID(BINDING_ID, "all");
    public static final ThingTypeUID THING_TYPE_TEMP = new ThingTypeUID(BINDING_ID, "temperature");
    public static final ThingTypeUID THING_TYPE_HUMIDITY = new ThingTypeUID(BINDING_ID, "humidity");
    public static final ThingTypeUID THING_TYPE_OPENCLOSED = new ThingTypeUID(BINDING_ID, "openclose");
    public static final ThingTypeUID THING_TYPE_PRESENCE = new ThingTypeUID(BINDING_ID, "presence");

    public static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = ImmutableSet.of(
            THING_TYPE_ALL,
            THING_TYPE_TEMP,
            THING_TYPE_HUMIDITY,
            THING_TYPE_OPENCLOSED,
            THING_TYPE_PRESENCE
    );
}
