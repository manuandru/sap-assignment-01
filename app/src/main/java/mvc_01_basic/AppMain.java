package mvc_01_basic;

public class AppMain {
    static public void main(String[] args) {

        MyModel model = new MyModel();
        MyView view = new MyView(model);
        MyInputUI inputUI = new MyInputUI();
        MyController controller = new MyController(model);
        var inputUIunsubscriber = inputUI.onInput().subscribe(i -> {
            controller.notifyNewUpdateRequested();
        });
        MyAutonomousController autController = new MyAutonomousController(model);
        autController.start();
        view.display();
        inputUI.display();
    }

}
