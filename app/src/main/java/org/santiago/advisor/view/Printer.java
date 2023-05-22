package org.santiago.advisor.view;

import org.santiago.advisor.store.Page;

public class Printer {
    private static final Printer INSTANCE = new Printer();
    private final Page page;
    private PrintingFormat format;

    private Printer() {
        page = Page.getInstance();
    }

    public static Printer getInstance() {
        return INSTANCE;
    }

    public void setFormat(PrintingFormat format) {
        this.format = format;
    }

    public void printPage() {
        format.printPage();
        printPageInfo();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    private void printPageInfo() {
        System.out.println("---PAGE "+ (page.getCurrentPage() + 1)
                + " OF " + page.getCurrentTotalPages() + "---");
    }
}
