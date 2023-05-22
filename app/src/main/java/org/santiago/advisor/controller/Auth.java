package org.santiago.advisor.controller;

import org.santiago.advisor.view.Printer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.Dotenv;

public class Auth extends ControllerDecorator {
    private boolean authorized;
    private String code;
    private final String accessServerPoint;
    private final String CLIENT_ID;
    private final String CLIENT_SECRET;
    private final Printer printer;

    public Auth(Controller controller, String accessServerPoint) {
        super(controller);
        Dotenv dotenv = Dotenv.load();
        CLIENT_ID = dotenv.get("CLIENT_ID");
        CLIENT_SECRET = dotenv.get("CLIENT_SECRET");
        authorized = false;
        code = null;
        this.accessServerPoint = accessServerPoint;
        printer = Printer.getInstance();
    }

    @Override
    public void mapRequest(String command) {
        if (command.equals("auth")) {
            getAccessCode();
            return;
        }

        if (!authorized && !command.equals("exit")) {
            printer.printMessage("Please, provide access for application.\n");
            return;
        }

        controller.mapRequest(command);
    }

    private void getAccessCode() {
        HttpServer server = null;
        int port = 8080;

        while (server == null && port < 8095) {
            try {
                server = setUpServer(port);
            } catch (Exception e) {
                port += 1;
            }
        }

        printer.printMessage("use this link to request the access code:");
        printer.printMessage(accessServerPoint + "/authorize?client_id=a2136de0b72049e5aefe2808e7d4ba09&redirect_uri=http://localhost:" + port + "&response_type=code");
        printer.printMessage("waiting for code...");
    }

    private void getToken(int port) {
        System.out.println("Making http request for access_token...");
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(
                        (CLIENT_ID + ":" + CLIENT_SECRET).getBytes()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(accessServerPoint + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code&code=" + code + "&redirect_uri=http://localhost:" + port))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jo = JsonParser.parseString(response.body()).getAsJsonObject();
            String accessToken = jo.get("access_token").getAsString();
            if (accessToken != null) {
                printer.printMessage("Success!");
                RequestHandler.getInstance().setAccessToken(accessToken);
                authorized = true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], null);
            }
        }
        return result;
    }

    private HttpServer setUpServer(int port) throws Exception {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(port), 0);
        server.createContext("/",
                new HttpHandler() {
                    public void handle(HttpExchange exchange) throws IOException {
                        Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
                        if (params != null) {
                            code = params.get("code");
                        }
                        String msg;
                        if (params == null || code == null) {
                            msg = "Authorization code not found. Try again.";
                            exchange.sendResponseHeaders(200, msg.length());
                            exchange.getResponseBody().write(msg.getBytes());
                            exchange.getResponseBody().close();
                        } else {
                            System.out.println("code received");
                            msg = "Got the code. Return back to your program.";
                            exchange.sendResponseHeaders(200, msg.length());
                            exchange.getResponseBody().write(msg.getBytes());
                            exchange.getResponseBody().close();
                            server.stop(0);
                            getToken(port);
                        }
                    }
                }
        );
        server.start();
        return server;
    }
}
