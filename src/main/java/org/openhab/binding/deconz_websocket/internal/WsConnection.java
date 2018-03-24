package org.openhab.binding.deconz_websocket.internal;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.openhab.binding.deconz_websocket.handler.Connection;
import org.openhab.binding.deconz_websocket.handler.Connection_CallBack;
import org.openhab.binding.deconz_websocket.internal.Filters.Filter;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class WsConnection implements Connection, WebSocketListener {
    private final static Map<Integer, Connection_CallBack> EmptyMap = ImmutableMap.of();
    private final JsonParser parser = new JsonParser();
    private Map<Filter.FilterType, Map<Integer, Connection_CallBack>> handlers = new HashMap<>();
    private URI uri;
    private WebSocketClient client;
    private Session session;
    private int next_id;

    public WsConnection(String url) {
        super();

        client = new WebSocketClient();
        try {
            uri = new URI("ws://" + url);
            client.start();
            session = client.connect(this, uri, new ClientUpgradeRequest()).get();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void cancel(Subscription subscription) {
        handlers.get(subscription.filterType).remove(subscription.id);
    }

    @Override
    public Subscription register(Connection_CallBack handler, Filter.FilterType filterType) {
        Subscription newSubscription = new Subscription(filterType, next_id++, this);

        if (!handlers.containsKey(filterType)) {
            handlers.put(filterType, new HashMap<>());
        }

        handlers.get(filterType).put(newSubscription.id, handler);

        return newSubscription;
    }

    @Override
    public void onWebSocketClose(int arg0, String arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWebSocketConnect(Session arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWebSocketError(Throwable arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onWebSocketText(String arg0) {
        JsonObject message = parser.parse(arg0).getAsJsonObject();

        if (message.has("e")
                && message.get("e").getAsString().equals("changed")
                && message.get("r").getAsString().equals("sensors")) {
            publishFiltered(Filter.FilterType.SENSOR_STATE_CHANGED, message);
        }

        publishFiltered(Filter.FilterType.ALL, message);
    }

    private void publishFiltered(Filter.FilterType filterType, JsonObject message) {
        handlers.getOrDefault(filterType, EmptyMap)
                .forEach((t, h) ->
                        h.publish(message)
                );
    }

    public class Subscription {
        private Filter.FilterType filterType;
        private int id;
        private boolean isActive;
        private WsConnection connection;

        private Subscription(Filter.FilterType filterType, int id, WsConnection connection) {
            this.filterType = filterType;
            this.id = id;
            this.connection = connection;


            this.isActive = true;
        }

        public boolean isActive() {
            return isActive;
        }

        public void cancel() {
            if (!isActive)
                return;

            isActive = false;

            connection.cancel(this);
        }
    }
}
