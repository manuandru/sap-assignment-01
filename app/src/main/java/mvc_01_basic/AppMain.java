package mvc_01_basic;

import it.assignment01.components.REstSource;
import it.assignment01.components.SseView;
import it.assignment01.components.WebsocketViewSource;
import mvc_01_basic.components.*;

public class AppMain {
    static public void main(String[] args) {

        Model model = new Model();
        SwingView view = new SwingView(model);
        InputUISource inputUI = new InputUISource();
        Controller controller = new Controller(model);
        var inputUIUnsubscriber = inputUI.onInput().subscribe(i -> {
            controller.notifyNewUpdateRequested();
        });

        AutonomousAgentSource autAgent = new AutonomousAgentSource();
        var autAgentUnsubscriber = autAgent.onInput().subscribe(i -> {
            controller.notifyNewUpdateRequested();
        });
        autAgent.start();

        view.display();
        inputUI.display();

        SseView sseView = new SseView(model);

        REstSource restInput = new REstSource();
        var restInputUnsubscriber = restInput.onInput().subscribe(i -> {
            controller.notifyNewUpdateRequested();
        });

        WebsocketViewSource wsVC = new WebsocketViewSource(model);
        var wsInputUnsubscriber = wsVC.onInput().subscribe(i -> {
            controller.notifyNewUpdateRequested();
        });
    }
}
