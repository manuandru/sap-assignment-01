package mvc_01_basic.components;


import mvc_01_basic.ModelInterface;

public class Controller {

    private final ModelInterface model;

    public Controller(ModelInterface model) {
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
