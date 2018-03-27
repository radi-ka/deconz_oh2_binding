package org.openhab.binding.deconz_websocket.internal.Filters;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.jdt.annotation.NonNull;

public class SensorEventsFilter implements Filter {
    private final int sensorId;

    private SensorEventsFilter(int sensorId) {
        this.sensorId = sensorId;
    }

    @NonNull
    public static Filter get(int sensorId) {
        return new SensorEventsFilter(sensorId);
    }

    @Override
    public boolean shouldUpdate(JsonObject event) {
        if (!event.has("id"))
            return false;

        if (!event.has("e"))
            return false;

        if (!event.has("r"))
            return false;

        JsonElement jsonElement;

        {
            jsonElement = event.get("id");
            if (jsonElement.isJsonNull())
                return false;

            if (jsonElement.getAsInt() != sensorId)
                return false;
        }

        {
            jsonElement = event.get("e");
            if (jsonElement.isJsonNull())
                return false;

            if (!jsonElement.getAsString().equals("changed"))
                return false;
        }

        {
            jsonElement = event.get("r");
            if (jsonElement.isJsonNull())
                return false;

            if (!jsonElement.getAsString().equals("sensors"))
                return false;
        }

        return true;
    }

    @Override
    public FilterType getType() {
        return FilterType.SENSOR_STATE_CHANGED;
    }

    @Override
    public String toString() {
        return "SensorEventsFilter{" +
                "sensorId=" + sensorId +
                '}';
    }
}
