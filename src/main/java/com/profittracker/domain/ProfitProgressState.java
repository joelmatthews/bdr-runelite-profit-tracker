package com.profittracker.domain;

import java.util.Set;

public class ProfitProgressState {
    private Set<MilestoneEnum> reachedMilestones;

    public boolean isMilestoneReached(MilestoneEnum milestone) {
        return reachedMilestones.contains(milestone);
    }

    public boolean recordMilestoneReached(MilestoneEnum milestone) {

        if (!isMilestoneReached(milestone)) {
            reachedMilestones.add(milestone);
            return true;
        }
        return false;
    }

}