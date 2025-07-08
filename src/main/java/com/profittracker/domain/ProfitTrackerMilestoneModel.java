package com.profittracker.domain;

import com.profittracker.domain.MilestoneEnum;

import java.util.Date;
import com.google.gson.Gson;

public class ProfitTrackerMilestoneModel {
    private String rsn;
    private long totalCurrentProfit;
    private String currentProfitPerHour;
    private MilestoneEnum milestone;
    private Date dateMilestoneAchieved;

    public ProfitTrackerMilestoneModel(String rsn, long totalCurrentProfit, String currentProfitPerHour, MilestoneEnum milestone, Date dateMilestoneAchieved) {
        this.rsn = rsn;
        this.totalCurrentProfit = totalCurrentProfit;
        this.currentProfitPerHour = currentProfitPerHour;
        this.milestone = milestone;
        this.dateMilestoneAchieved = dateMilestoneAchieved;
    }

    public String getRsn() {
        return rsn;
    }

    public long getCurrentProfit() {
        return totalCurrentProfit;
    }

    public String getCurrentProfitPerHour() {
        return currentProfitPerHour;
    }

    public MilestoneEnum getMilestone() {
        return milestone;
    }

    public Date getDateMilestoneAchieved() {
        return dateMilestoneAchieved;
    }

}