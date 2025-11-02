package com.profittracker.adapters.http;

import java.util.concurrent.CompletableFuture;

public interface HttpAdapter {
    public CompletableFuture postAsync(String json, String endpoint);
}
