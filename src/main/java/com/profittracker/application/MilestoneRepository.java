package com.profittracker.application;

import com.profittracker.domain.ProfitTrackerMilestoneModel;

public interface MilestoneRepository {
    void sendMilestoneData(ProfitTrackerMilestoneModel milestoneData);
}
