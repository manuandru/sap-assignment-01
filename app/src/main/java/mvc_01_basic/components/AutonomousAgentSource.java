package mvc_01_basic.components;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import mvc_01_basic.InputSource;

public class AutonomousAgentSource extends Thread implements InputSource {

    private final Subject<Event> emitter = PublishSubject.create();

    public void run() {
        while (true) {
            log("Changing pro-actively the model...");
            emitter.onNext(Event.AUTONOMOUS);
            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
            }
        }
    }

    private void log(String msg) {
        System.out.println("[Autonomous Controller] " + msg);
    }

    @Override
    public Observable<Event> onInput() {
        return emitter;
    }
}
