/**
 * Copyright (c) 2014,2018 by the respective copyright holders.
 * <p>
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 * <p>
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * <p>
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.deconz_websocket.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.openhab.binding.deconz_websocket.handler.Connection;
import org.openhab.binding.deconz_websocket.handler.deCONZ_WebsocketHandler;
import org.openhab.binding.deconz_websocket.internal.Filters.AllowAllFilter;
import org.openhab.binding.deconz_websocket.internal.Filters.SensorEventsFilter;
import org.openhab.binding.deconz_websocket.internal.StateBuilders.DecimalStateBuilder;
import org.openhab.binding.deconz_websocket.internal.StateBuilders.DefaultStateBuilder;
import org.openhab.binding.deconz_websocket.internal.StateBuilders.OpenClosedStateBuilder;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Component;

import java.util.Dictionary;

import static org.openhab.binding.deconz_websocket.deCONZ_WebsocketBindingConstants.*;

/**
 * The {@link deCONZ_WebsocketHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Mirco Kahle - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, immediate = true, configurationPid = "binding.deconz_websocket")
@NonNullByDefault
public class deCONZ_WebsocketHandlerFactory extends BaseThingHandlerFactory {
    @Nullable
    private Connection ws_connection;

    public deCONZ_WebsocketHandlerFactory() {
    }

    @Override
    protected void activate(ComponentContext componentContext) {
        super.activate(componentContext);
        Dictionary<String, Object> properties = componentContext.getProperties();
        String wsEndpointUrl = (String) properties.get("websocket_endpoint");

        ws_connection = new WsConnection(wsEndpointUrl);
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (ws_connection == null) {
            return null;
        }

        if (THING_TYPE_ALL.equals(thingTypeUID)) {
            return new deCONZ_WebsocketHandler(thing, ws_connection, AllowAllFilter.get(), new DefaultStateBuilder(), CHANNEL_RAWSTATES);
        }

        deCONZ_WebsocketConfiguration config = thing.getConfiguration().as(deCONZ_WebsocketConfiguration.class);

        if (THING_TYPE_TEMP.equals(thingTypeUID)) {
            return new deCONZ_WebsocketHandler(thing, ws_connection, SensorEventsFilter.get(config.id), new DecimalStateBuilder("temperature", 2), CHANNEL_TEMPERATURE);
        } else if (THING_TYPE_HUMIDITY.equals(thingTypeUID)) {
            return new deCONZ_WebsocketHandler(thing, ws_connection, SensorEventsFilter.get(config.id), new DecimalStateBuilder("humidity", 2), CHANNEL_HUMIDITY);
        } else if (THING_TYPE_OPENCLOSED.equals(thingTypeUID)) {
            return new deCONZ_WebsocketHandler(thing, ws_connection, SensorEventsFilter.get(config.id), new OpenClosedStateBuilder(), CHANNEL_OPENCLOSED);
        }

        return null;
    }
}
