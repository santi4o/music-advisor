package org.santiago.advisor.controller;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RequestHandler {
    private static final RequestHandler INSTANCE = new RequestHandler();
    private final HttpClient client;
    private String resourceServerPoint;
    private String accessToken;
    private String currentPath;;


    private RequestHandler() {
        client = HttpClient.newBuilder().build();
    }

    public static RequestHandler getInstance() {
        return INSTANCE;
    }

    private HttpRequest buildRequest() {
        return HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(resourceServerPoint + currentPath))
                .GET()
                .build();
    }

    public JsonObject request() {
        try {
            HttpResponse<String> response = client.send(buildRequest(), HttpResponse.BodyHandlers.ofString());
            return JsonParser.parseString(response.body()).getAsJsonObject();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setResourceServerPoint(String resourceServerPoint) {
        this.resourceServerPoint = resourceServerPoint;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }
}
