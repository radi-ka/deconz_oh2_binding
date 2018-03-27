package org.openhab.binding.deconz_websocket.internal.StateBuilders;

import com.google.gson.JsonObject;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.types.State;

import java.util.Optional;

public interface StateBuilder {
    @NonNull
    Optional<State> build(JsonObject state);
}
