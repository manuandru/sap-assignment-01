package it.assignment01;

import io.javalin.Javalin;
import mvc_01_basic.ModelInterface;

public class RestController {

    public RestController(ModelInterface model) {
        var app = Javalin.create()
                .get("/increment", ctx -> { // just using get in order to call it from browser searchbar
                    log("user request update => updating the model");
                    model.update();
                }).start(9000);
    }

    private void log(String msg) {
        System.out.println("[Rest Controller] " + msg);
    }
}
