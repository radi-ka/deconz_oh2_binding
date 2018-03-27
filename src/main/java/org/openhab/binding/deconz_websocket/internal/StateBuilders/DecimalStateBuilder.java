package org.openhab.binding.deconz_websocket.internal.StateBuilders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.types.State;

import java.util.Optional;

public class DecimalStateBuilder implements StateBuilder {

    private final String stateName;
    private final double decimalShift;

    public DecimalStateBuilder(String stateName, int decimalShift) {
        this.stateName = stateName;
        this.decimalShift = Math.pow(10, decimalShift);
    }

    @NonNull
    @Override
    public Optional<State> build(final JsonObject state) {
        final JsonElement stateElement = state.get("state");

        if (stateElement == null || stateElement.isJsonNull())
            return Optional.empty();

        final JsonElement jsonElement = stateElement.getAsJsonObject().get(stateName);

        if (jsonElement == null || jsonElement.isJsonNull())
            return Optional.empty();

        final double decimal = jsonElement.getAsDouble() / decimalShift;

        return Optional.of(new DecimalType(decimal));
    }

    @Override
    public String toString() {
        return "DecimalStateBuilder{" +
                "stateName='" + stateName + '\'' +
                ", decimalShift=" + decimalShift +
                '}';
    }
}
