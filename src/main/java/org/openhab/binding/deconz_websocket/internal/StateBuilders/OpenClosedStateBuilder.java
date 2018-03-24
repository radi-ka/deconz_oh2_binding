package org.openhab.binding.deconz_websocket.internal.StateBuilders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.eclipse.smarthome.core.types.State;

import java.util.Optional;

public class OpenClosedStateBuilder implements StateBuilder {

    @Override
    public Optional<State> build(final JsonObject state) {
        final JsonElement stateElement = state.get("state");

        if (stateElement.isJsonNull())
            return Optional.empty();

        final JsonElement openClosedElement = stateElement.getAsJsonObject().get("open");

        if (openClosedElement.isJsonNull())
            return Optional.empty();

        final boolean isOpen = openClosedElement.getAsBoolean();

        if (isOpen) {
            return Optional.of(OpenClosedType.OPEN);
        } else {
            return Optional.of(OpenClosedType.CLOSED);
        }
    }
}
