package mvc_01_basic;

import it.assignment01.REstInputSource;
import it.assignment01.SseView;
import it.assignment01.WebsocketViewController;

public class AppMain {
    static public void main(String[] args) {

        MyModel model = new MyModel();
        MyView view = new MyView(model);
        MyInputUI inputUI = new MyInputUI();
        MyController controller = new MyController(model);
        var inputUIUnsubscriber = inputUI.onInput().subscribe(i -> {
            controller.notifyNewUpdateRequested();
        });
        MyAutonomousController autController = new MyAutonomousController(model);
        autController.start();
        view.display();
        inputUI.display();

        SseView sseView = new SseView(model);

        REstInputSource restInput = new REstInputSource();
        var restInputUnsubscriber = restInput.onInput().subscribe(i -> {
            controller.notifyNewUpdateRequested();
        });

        WebsocketViewController wsVC = new WebsocketViewController(model);
        var wsInputUnsubscriber = wsVC.onInput().subscribe(i -> {
            controller.notifyNewUpdateRequested();
        });
    }
}
