package com.profittracker.infrastructure.repository;

import com.profittracker.application.MilestoneAdapter;
import com.profittracker.infrastructure.DTO.MilestoneDTO;
import com.profittracker.domain.MilestoneModel;

public class MilestoneRepositoryImpl implements com.profittracker.application.MilestoneRepository {
    private final MilestoneAdapter adapter;

    public MilestoneRepositoryImpl(MilestoneAdapter adapter) {
        this.adapter = adapter;
    }

    public void sendMilestoneData(MilestoneModel milestone) {
        MilestoneDTO milestoneDTO = mapMilestoneToDTO(milestone);
        adapter.send(milestoneDTO);
    }

    private MilestoneDTO mapMilestoneToDTO(MilestoneModel milestone) {
        return new MilestoneDTO(
                milestone.getRsn(),
                milestone.getCurrentProfit(),
                milestone.getCurrentProfitPerHour(),
                milestone.getMilestone().milestoneAmount,
                milestone.getDateMilestoneAchieved().toString());
    }

}
