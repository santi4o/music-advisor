package org.santiago.advisor.controller;

public class ControllerDecorator implements Controller {
    protected Controller controller;

    public ControllerDecorator(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void mapRequest(String command) {
        controller.mapRequest(command);
    }
}

