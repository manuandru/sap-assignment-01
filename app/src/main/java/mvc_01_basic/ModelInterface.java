package mvc_01_basic;

import io.reactivex.rxjava3.core.Observable;

public interface ModelInterface {

    void update();

    int getState();

    Observable<Integer> onVariation();
}
