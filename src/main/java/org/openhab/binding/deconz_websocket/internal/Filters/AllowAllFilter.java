package org.openhab.binding.deconz_websocket.internal.Filters;

import com.google.gson.JsonObject;

public class AllowAllFilter implements Filter {
    private static AllowAllFilter instance = null;

    private AllowAllFilter() {
    }

    public static Filter get() {
        if (instance == null) {
            instance = new AllowAllFilter();
        }

        return instance;
    }

    @Override
    public boolean shouldUpdate(JsonObject event) {
        return true;
    }

    @Override
    public Filter.FilterType getType() {
        return Filter.FilterType.ALL;
    }
}
