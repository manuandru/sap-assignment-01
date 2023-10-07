package mvc_01_basic;

import io.reactivex.rxjava3.core.Observable;

public interface InputSource {

    Observable<Event> onInput();

    enum Event {
        CLICK, REST_EVENT, WS_EVENT, AUTONOMOUS
    }
}
