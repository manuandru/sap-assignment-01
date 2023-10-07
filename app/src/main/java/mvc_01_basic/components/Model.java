package mvc_01_basic.components;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;
import mvc_01_basic.ModelInterface;

public class Model implements ModelInterface {

    private final Subject<Integer> onVariation = PublishSubject.create();
    private int state = 0;

    public synchronized void update() {
        state++;
        log("state updated: " + state);
        onVariation.onNext(state);
    }

    public synchronized int getState() {
        return state;
    }

    @Override
    public Observable<Integer> onVariation() {
        return onVariation;
    }

    private synchronized void log(String msg) {
        System.out.println("[Model] " + msg);
    }
}
