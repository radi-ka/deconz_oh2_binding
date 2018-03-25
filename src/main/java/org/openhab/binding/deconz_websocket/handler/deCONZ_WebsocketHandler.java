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
package org.openhab.binding.deconz_websocket.handler;

import com.google.gson.JsonObject;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.deconz_websocket.internal.Filters.Filter;
import org.openhab.binding.deconz_websocket.internal.StateBuilders.StateBuilder;
import org.openhab.binding.deconz_websocket.internal.WsConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * The {@link deCONZ_WebsocketHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Mirco Kahle - Initial contribution
 */
@NonNullByDefault
public class deCONZ_WebsocketHandler extends BaseThingHandler implements Connection_CallBack {

    private final Logger logger = LoggerFactory.getLogger(deCONZ_WebsocketHandler.class);
    private final StateBuilder stateBuilder;
    private final String channel;
    private Connection.Type type = Connection.Type.UNDEFINED;
    private Connection ws_connection;
    private WsConnection.Subscription subscription;
    private Filter filter;

    public deCONZ_WebsocketHandler(Thing thing, Connection connection, Filter filter, StateBuilder stateBuilder, String channel) {
        super(thing);
        ws_connection = connection;
        this.filter = filter;
        this.stateBuilder = stateBuilder;
        this.channel = channel;
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (channelUID.getId().equals(channel)) {
            logger.debug("Received command {}", command);
            logger.trace("For now this WS connection is receive only. So we don't handle commands.");
        }
    }

    @Override
    public void initialize() {
        logger.debug("Initializing deconz handler.");

        subscription = ws_connection.register(this, filter.getType());

        // TODO: Initialize the thing. If done set status to ONLINE to indicate proper working.
        // Long running initialization should be done asynchronously in background.
        updateStatus(ThingStatus.ONLINE);

        // Note: When initialization can NOT be done set the status with more details for further
        // analysis. See also class ThingStatusDetail for all available status details.
        // Add a description to give user information to understand why thing does not work
        // as expected. E.g.
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR,
        // "Can not access device as username and/or password are invalid");
    }

    @Override
    public void handleRemoval() {
        super.handleRemoval();

        logger.debug("unregistering subscription {}", subscription);

        subscription.cancel();
    }

    @Override
    public void publish(JsonObject message) {
        if (filter.shouldUpdate(message)) {
            Optional<State> s = stateBuilder.build(message);

            if (s == null || !s.isPresent()) {
                logger.error("message {} could not be parsed with parser {}", message, stateBuilder);
                return;
            }

            if (getCallback() == null) {
                logger.warn("callback not initialized.");
                return;
            }

            updateState(channel, s.get());
        }
    }
}
