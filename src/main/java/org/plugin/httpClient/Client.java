package org.plugin.httpClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    private static HttpClient client = HttpClient.newBuilder().build();
    public static HttpResponse<String> post(String uri, HttpRequest.BodyPublisher body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(URI.create(uri))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
