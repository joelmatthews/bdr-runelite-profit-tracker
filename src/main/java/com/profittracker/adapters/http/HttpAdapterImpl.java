package com.profittracker.adapters.http;
import com.profittracker.support.Config;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@Singleton
public class HttpAdapterImpl implements HttpAdapter {
    HttpClient _client;
    Config _config;

    @Inject
    public HttpAdapterImpl(Config config) {
        _client = HttpClient.newHttpClient();
        _config = config;
    }

    public CompletableFuture<Void> postAsync(String content, String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(_config.getBaseUrl() + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .build();

        return _client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> System.out.print("Successfully posted to server"));
    }


}
