package org.openhab.binding.deconz_websocket.internal.StateBuilders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.smarthome.core.types.State;

import java.util.Optional;

public class FromBooleanStateBuilder implements StateBuilder {

    private final String stateName;
    private final State onTrue;
    private final State onFalse;

    public FromBooleanStateBuilder(String stateName, State onTrue, State onFalse) {
        this.stateName = stateName;
        this.onTrue = onTrue;
        this.onFalse = onFalse;
    }

    @Override
    public Optional<State> build(final JsonObject state) {
        final JsonElement stateElement = state.get("state");

        if (stateElement == null || stateElement.isJsonNull())
            return Optional.empty();

        final JsonElement jsonElement = stateElement.getAsJsonObject().get(stateName);

        if (jsonElement == null || jsonElement.isJsonNull())
            return Optional.empty();

        if (jsonElement.getAsBoolean()) {
            return Optional.of(onTrue);
        } else {
            return Optional.of(onFalse);
        }
    }
}
