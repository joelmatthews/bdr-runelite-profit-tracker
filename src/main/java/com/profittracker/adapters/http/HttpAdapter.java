package com.profittracker.adapters.http;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface HttpAdapter {
    CompletableFuture<Void> postAsync(Object content, String endpoint);
}
