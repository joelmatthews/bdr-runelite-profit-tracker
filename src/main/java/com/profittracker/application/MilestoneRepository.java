package com.profittracker.application;

import com.profittracker.domain.MilestoneModel;

public interface MilestoneRepository {
    void sendMilestoneData(MilestoneModel milestoneData);
}
