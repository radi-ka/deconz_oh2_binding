package org.openhab.binding.deconz_websocket.internal.Filters;

import com.google.gson.JsonObject;

public interface Filter {
    boolean shouldUpdate(final JsonObject event);

    FilterType getType();

    enum FilterType {
        ALL,
        SENSOR_STATE_CHANGED
    }
}
