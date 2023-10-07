package it.assignment01.components;

import io.javalin.Javalin;
import io.javalin.http.sse.SseClient;
import io.javalin.http.staticfiles.Location;
import io.reactivex.rxjava3.disposables.Disposable;
import mvc_01_basic.ModelInterface;
import mvc_01_basic.View;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SseView implements View {
    private final Disposable unsubscriber; // call me to unsubscribe!
    private final Queue<SseClient> clients = new ConcurrentLinkedQueue<>();

    public SseView(ModelInterface model) {
        var app = Javalin.create(config -> {
            config.staticFiles.add("/public/sse", Location.CLASSPATH);
        });

        app.sse("/sse", client -> {
            client.keepAlive();
            client.onClose(() -> clients.remove(client));
            clients.add(client);
            publishToClient(client, model.getState());
        });

        this.unsubscriber = model.onVariation().subscribe(v -> {
            log("model updated => updating the view");
            broadcastToClients(v);
        });

        app.start(8000);
    }

    private void publishToClient(SseClient client, int value) {
        client.sendEvent("model", value);
    }

    private void broadcastToClients(int value) {
        clients.forEach(c -> publishToClient(c, value));
    }

    private void log(String msg) {
        System.out.println("[Sse View] " + msg);
    }
}
