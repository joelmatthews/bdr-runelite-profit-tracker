package com.profittracker.application;

import com.profittracker.domain.MilestoneEnum;
import com.profittracker.domain.ProfitProgressState;

public class MilestoneService {

    ProfitProgressState profitProgressState;

    public MilestoneService(ProfitProgressState profitProgressState) {
        this.profitProgressState = profitProgressState;
    }

    public void MilestoneReached(long newProfit) {
        MilestoneEnum milestone = null;

        for (int i = 0; i < MilestoneEnum.values().length; i++) {
            MilestoneEnum current = MilestoneEnum.values()[i];
            MilestoneEnum next = (i + 1 < MilestoneEnum.values().length) ? MilestoneEnum.values()[i + 1] : null;

            if (next == null) {
                if (newProfit >= current.milestoneAmount) {
                    milestone = current;
                    break;
                }
            } else if (newProfit >= current.milestoneAmount && newProfit < next.milestoneAmount) {
                milestone = current;
                break;
            }
        }

        profitProgressState.recordMilestoneReached(milestone);
        // TODO: Call repository to send ProfitTrackerMilestone;
    }
}