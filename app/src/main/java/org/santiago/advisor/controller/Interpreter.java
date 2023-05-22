package org.santiago.advisor.controller;

import java.util.Scanner;

import org.santiago.advisor.store.Navigation;

public class Interpreter extends ControllerDecorator {
    public Interpreter(Controller controller) {
        super(controller);
    }

    public void listen() {
        try (Scanner scanner = new Scanner(System.in)) {
            String command;

            do {
                command = scanner.nextLine();
                controller.mapRequest(command);
            } while(!command.equals("exit") || !Navigation.state.equals("out"));
        }
        System.exit(0);
    }
}
