package mvc_01_basic;


public class MyController implements UserInputObserver {

    private final ModelInterface model;

    public MyController(ModelInterface model) {
        this.model = model;
    }

    public synchronized void notifyNewUpdateRequested() {
        log("New update requested by the user");
        model.update();
    }

    private void log(String msg) {
        System.out.println("[Controller] " + msg);
    }
}
