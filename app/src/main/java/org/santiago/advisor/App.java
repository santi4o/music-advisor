package org.santiago.advisor;

import org.santiago.advisor.controller.*;
import org.santiago.advisor.store.Page;

public class App {

    static String accessServerPoint;
    static String resourceServerPoint;
    static Integer pageSize;

    public static void main(String[] args) {
        if (args.length > 0 && args.length % 2 == 0) {
            for (int i = 0; i < args.length; i += 2) {
                if (args[i].equals("-access")) {
                    accessServerPoint = args[i+1];
                } else if (args[i].equals("-resource")) {
                    resourceServerPoint = args[i+1];
                } else if (args[i].equals("-page")) {
                    pageSize = Integer.parseInt(args[i+1]);
                }
            }
        }

        if (accessServerPoint == null) {
            accessServerPoint = "https://accounts.spotify.com";
        }
        if (resourceServerPoint == null) {
            resourceServerPoint = "https://api.spotify.com";
        }
        if (pageSize == null) {
            pageSize = 5;
        }

        Page.getInstance().setPageSize(pageSize);
        RequestHandler.getInstance().setResourceServerPoint(resourceServerPoint);

        Interpreter controller = new Interpreter(
                new Auth(
                        new PageNavigation(new RequestMapper()), accessServerPoint));

        controller.listen();
    }
}
