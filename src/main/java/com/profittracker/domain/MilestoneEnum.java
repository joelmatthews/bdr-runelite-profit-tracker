package com.profittracker.domain;

public enum MilestoneEnum {
    MILESTONE_ONE(1_000_000),
    MILESTONE_TWO(5_000_000),
    MILESTONE_THREE(10_000_000),
    MILESTONE_FOUR(25_000_000),
    MILESTONE_FIVE(50_000_000),
    MILESTONE_SIX(100_000_000),
    MILESTONE_SEVEN(250_000_000),
    MILESTONE_EIGHT(500_000_000),
    MILESTONE_NINE(750_000_000),
    MILESTONE_TEN(1000_000_000),;

    public final long milestoneAmount;

    MilestoneEnum(final long milestoneAmount)
    {
        this.milestoneAmount = milestoneAmount;
    }

    long getMilestoneAmount() {
        return milestoneAmount;
    }
}
