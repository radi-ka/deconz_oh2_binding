package org.openhab.binding.deconz_websocket.handler;

import org.openhab.binding.deconz_websocket.internal.Filters.Filter;
import org.openhab.binding.deconz_websocket.internal.WsConnection;

public interface Connection {
    WsConnection.Subscription register(Connection_CallBack handler, Filter.FilterType filterType);

    enum Type {
        SENSORS,
        LIGHTS,
        GROUPS,
        ERROR,
        UNDEFINED
    }
}
