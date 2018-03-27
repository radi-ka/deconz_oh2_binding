package org.openhab.binding.deconz_websocket.handler;

import com.google.gson.JsonObject;

public interface Connection_CallBack {
    void publish(JsonObject message);
}