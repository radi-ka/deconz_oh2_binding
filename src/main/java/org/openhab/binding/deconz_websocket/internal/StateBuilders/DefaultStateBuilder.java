package org.openhab.binding.deconz_websocket.internal.StateBuilders;

import com.google.gson.JsonObject;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.types.State;

import java.util.Optional;

public class DefaultStateBuilder implements StateBuilder {
    @NonNull
    @Override
    public Optional<State> build(final JsonObject state) {
        StringType stringType = new StringType(state.getAsString());
        return Optional.of(stringType);
    }

    @Override
    public String toString() {
        return "DefaultStateBuilder{}";
    }
}
