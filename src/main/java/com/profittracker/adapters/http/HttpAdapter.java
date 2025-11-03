package com.profittracker.adapters.http;

import java.util.concurrent.CompletableFuture;

public interface HttpAdapter {
    CompletableFuture<Void> postAsync(String content, String endpoint);
}
