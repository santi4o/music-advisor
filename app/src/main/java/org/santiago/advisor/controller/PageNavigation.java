package org.santiago.advisor.controller;

import org.santiago.advisor.store.Page;
import org.santiago.advisor.view.Printer;

public class PageNavigation extends ControllerDecorator {
    private final Page page;
    public PageNavigation(Controller controller) {
        super(controller);
        page = Page.getInstance();
    }

    @Override
    public void mapRequest(String command) {
        if (command.equals("next") || command.equals("prev")) {
            boolean changed = command.equals("next") ?
                    page.incrementPage() : page.decrementPage();

            if (changed) {
                Printer.getInstance().printPage();
            } else {
                Printer.getInstance().printMessage("No more pages.");
            }
        } else {
            controller.mapRequest(command);
        }
    }
}
