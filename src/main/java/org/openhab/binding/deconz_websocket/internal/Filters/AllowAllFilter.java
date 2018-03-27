package org.openhab.binding.deconz_websocket.internal.Filters;

import com.google.gson.JsonObject;
import org.eclipse.jdt.annotation.NonNull;

public class AllowAllFilter implements Filter {
    private static AllowAllFilter instance = null;

    private AllowAllFilter() {
    }

    @NonNull
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

    @Override
    public String toString() {
        return "AllowAllFilter{}";
    }
}
