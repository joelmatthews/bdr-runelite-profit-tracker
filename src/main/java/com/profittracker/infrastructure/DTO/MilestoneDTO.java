package com.profittracker.infrastructure.DTO;

public class MilestoneDTO {
    private String rsn;
    private long totalProfit;
    private String profitRate;
    private long milestone;
    private String timestamp;

    public MilestoneDTO(final String rsn, final long totalProfit, final String profitRate, final long milestone, final String timestamp) {
        this.rsn = rsn;
        this.totalProfit = totalProfit;
        this.profitRate = profitRate;
        this.milestone = milestone;
        this.timestamp = timestamp;
    }
}
