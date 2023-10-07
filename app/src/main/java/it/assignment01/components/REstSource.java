package it.assignment01.components;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import mvc_01_basic.InputSource;

public class REstSource implements InputSource {

    private final Subject<Event> emitter = PublishSubject.create();

    public REstSource() {
        var app = Javalin.create()
                .get("/increment", this::requestHandler) // only for testing purpose: you can call it from browser!
                .put("/increment", this::requestHandler)
                .start(9000);
    }

    private void requestHandler(Context ctx) {
        log("user request update => updating the model");
        emitter.onNext(Event.REST_EVENT);
    }

    private void log(String msg) {
        System.out.println("[Rest Controller] " + msg);
    }

    @Override
    public Observable<Event> onInput() {
        return emitter;
    }
}
