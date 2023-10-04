package it.assignment01;

import io.javalin.Javalin;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import mvc_01_basic.InputSource;

public class REstInputSource implements InputSource {

    private final Subject<Event> emitter = PublishSubject.create();

    public REstInputSource() {
        var app = Javalin.create()
                .get("/increment", ctx -> { // just using get in order to call it from browser searchbar
                    log("user request update => updating the model");
                    emitter.onNext(Event.REST_EVENT);
                }).start(9000);
    }

    private void log(String msg) {
        System.out.println("[Rest Controller] " + msg);
    }

    @Override
    public Observable<Event> onInput() {
        return emitter;
    }
}
