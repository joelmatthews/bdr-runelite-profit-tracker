package com.profittracker.domain;

import java.util.Date;

public class MilestoneModel {
    private String rsn;
    private long totalCurrentProfit;
    private long averageProfitThousandForHour;
    private MilestoneEnum milestone;
    private Date dateMilestoneAchieved;

    public MilestoneModel(String rsn, long totalCurrentProfit, long averageProfitThousandForHour, MilestoneEnum milestone, Date dateMilestoneAchieved) {
        this.rsn = rsn;
        this.totalCurrentProfit = totalCurrentProfit;
        this.averageProfitThousandForHour = averageProfitThousandForHour;
        this.milestone = milestone;
        this.dateMilestoneAchieved = dateMilestoneAchieved;
    }

    public String getRsn() {
        return rsn;
    }

    public long getCurrentProfit() {
        return totalCurrentProfit;
    }

    public long getAverageProfitThousandForHour() {
        return averageProfitThousandForHour;
    }

    public MilestoneEnum getMilestone() {
        return milestone;
    }

    public Date getDateMilestoneAchieved() {
        return dateMilestoneAchieved;
    }

}