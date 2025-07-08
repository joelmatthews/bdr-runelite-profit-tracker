package com.profittracker.domain;

import com.google.inject.Singleton;

import java.util.Set;

@Singleton
public class ProfitProgressState {
    private Set<MilestoneEnum> reachedMilestones;

    public boolean isMilestoneAlreadyAchieved(MilestoneEnum milestone) {
        return reachedMilestones.contains(milestone);
    }

    public Set<MilestoneEnum> recordMilestoneReached(MilestoneEnum milestone) {

        if (!isMilestoneAlreadyAchieved(milestone)) {
            reachedMilestones.add(milestone);
        }
        return reachedMilestones;
    }

}