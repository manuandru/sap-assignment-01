package it.assignment01.components;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.websocket.WsContext;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import mvc_01_basic.InputSource;
import mvc_01_basic.ModelInterface;
import mvc_01_basic.View;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WebsocketViewSource implements InputSource, View {

    public static final String UPDATE_COMMAND = "update";
    private final Subject<Event> emitter = PublishSubject.create();

    private final Disposable unsubscriber; // call me to unsubscribe!
    private final Queue<WsContext> clients = new ConcurrentLinkedQueue<>();

    public WebsocketViewSource(ModelInterface model) {
        var app = Javalin.create(config -> {
            config.staticFiles.add("/public/websocket", Location.CLASSPATH);
        });

        app.ws("/websocket", ws -> {
            ws.onConnect(ctx -> {
                clients.add(ctx);
                publishToClient(ctx, model.getState());
            });
            ws.onMessage(ctx -> {
                if (UPDATE_COMMAND.equals(ctx.message())) {
                    log("message from ws => updating the model");
                    emitter.onNext(Event.WS_EVENT);
                }
            });
            ws.onClose(clients::remove);
            ws.onError(clients::remove);
        });

        this.unsubscriber = model.onVariation().subscribe(v -> {
            log("model updated => updating the view");
            broadcastToClients(v);
        });
        app.start(10000);
    }

    @Override
    public Observable<Event> onInput() {
        return emitter;
    }

    private void publishToClient(WsContext client, int value) {
        client.send(value);
    }

    private void broadcastToClients(int value) {
        clients.forEach(c -> publishToClient(c, value));
    }

    private void log(String msg) {
        System.out.println("[Websocket ViewController] " + msg);
    }
}
