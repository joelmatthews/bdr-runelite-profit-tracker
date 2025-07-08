package com.profittracker.application;

import com.profittracker.infrastructure.DTO.MilestoneDTO;

public interface MilestoneAdapter {
    void send(MilestoneDTO milestone);
}
