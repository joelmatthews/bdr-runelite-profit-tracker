package com.profittracker.application;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.profittracker.domain.MilestoneEnum;
import com.profittracker.domain.MilestoneModel;
import com.profittracker.domain.ProfitProgressState;
import com.profittracker.utility.ProfitTrackerCalculator;

import java.util.Date;

@Singleton
public class MilestoneService {

    ProfitProgressState profitProgressState;
    MilestoneRepository milestoneRepository;

    @Inject
    public MilestoneService(ProfitProgressState profitProgressState, MilestoneRepository milestoneRepository) {
        this.profitProgressState = profitProgressState;
        this.milestoneRepository = milestoneRepository;
    }

    public void checkAndRecordMilestone(String rsn, long newProfit, long millisecondsElapsed) {
        MilestoneEnum milestone = determineMilestone(newProfit);

        if (milestone != null) {
            profitProgressState.recordMilestoneReached(milestone);

            long averageProfitThousandForHour = ProfitTrackerCalculator.calculateProfitHourly(millisecondsElapsed, newProfit);

            MilestoneModel milestoneModel = new MilestoneModel(
                    rsn,
                    newProfit,
                    averageProfitThousandForHour,
                    milestone,
                    new Date()
            );

            milestoneRepository.sendMilestoneData(milestoneModel);
        }
    }

    private MilestoneEnum determineMilestone(long profit) {
        if (profit < MilestoneEnum.MILESTONE_ONE.milestoneAmount) {
            return null;
        }

        for (int i = 0; i < MilestoneEnum.values().length; i++) {
            MilestoneEnum currentMilestone = MilestoneEnum.values()[i];
            MilestoneEnum nextMilestone = (i + 1 < MilestoneEnum.values().length) ? MilestoneEnum.values()[i + 1] : null;

            if (nextMilestone == null) {
                if (profit >= currentMilestone.milestoneAmount) {
                    return currentMilestone;
                }
            } else if (profit >= currentMilestone.milestoneAmount && profit < nextMilestone.milestoneAmount) {
                return currentMilestone;
            }
        }
            return null;
    }
}