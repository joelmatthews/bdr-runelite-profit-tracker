package com.profittracker.adapters.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    Gson _gson;

    @Inject
    public HttpAdapterImpl(Config config) {
        _client = HttpClient.newHttpClient();
        _config = config;
    }

    public CompletableFuture<String> postAsync(String json, String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(_config.getBaseUrl() + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        CompletableFuture resolvedResponse = _client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.body());

        // just return the future (same thing as a promise)
        // the caller can handle it and map it into whatever it needs to be
        // for example, we could have a domain object, with toJson and fromJson methods on it that map
        return resolvedResponse;
    }


}
