package com.profittracker.adapters.http;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.profittracker.domain.PlayerVerificationDto;
import com.profittracker.support.Config;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;



@Singleton
public class HttpAdapterImpl implements HttpAdapter {
    HttpClient _client;
    Config _config;

    @Inject
    public HttpAdapterImpl(Config config) {
        _client = HttpClient.newHttpClient();
        _config = config;
    }

    public CompletableFuture<Void> postAsync(Map<String, String> content, String endpoint) {
        PlayerVerificationDto payload =  new PlayerVerificationDto();
        payload.setLeaguePlayerId(_leagueService.getLeaguePlayerIds());
        payload.setLeaguePlayerGameName(content.get("playerName"));
        payload.setScreenshot(content.get("screenshot"));

        try {
            String json = toJson(payload);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(_config.getBaseUrl() + endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            return _client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> System.out.print("Successfully posted to server"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private String toJson(Object objectToConvert) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(objectToConvert);
    }
}
