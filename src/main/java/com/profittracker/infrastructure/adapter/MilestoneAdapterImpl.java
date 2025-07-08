package com.profittracker.infrastructure.adapter;

import com.profittracker.application.MilestoneAdapter;
import com.profittracker.infrastructure.DTO.MilestoneDTO;
import com.profittracker.infrastructure.config.Config;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;

public class MilestoneAdapterImpl implements MilestoneAdapter {
    private OkHttpClient httpClient = new OkHttpClient();
    private static final String MILESTONE_ENDPOINT = "/milestones";

    @Override
    public void send(MilestoneDTO milestone) {
        Gson gson = new Gson();
        String json = gson.toJson(milestone);

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(Config.BASE_URL + MILESTONE_ENDPOINT)
                .post(body)
                .build();

        try {
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    System.err.println("[MilestoneAdapter] Failed to send milestone: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (Response res = response) {
                        if (!res.isSuccessful()) {
                            System.err.println("[MilestoneAdapter] Server returned error: " + response.code());
                        } else {
                            System.out.println("[MilestoneAdapter] Milestone sent successfully.");
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("[MilestoneAdapter] Unexpected error: " + e.getMessage());
        }
    }
}