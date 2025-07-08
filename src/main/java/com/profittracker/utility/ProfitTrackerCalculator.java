package com.profittracker.utility;

public class ProfitTrackerCalculator {

    public static long calculateProfitHourly(long millisecondsElapsed, long profit)
    {
        long averageProfitThousandForHour;
        double averageProfitPerMillisecond;

        if (millisecondsElapsed > 0)
        {
            averageProfitPerMillisecond = (double)profit / millisecondsElapsed;
        }
        else
        {
            // can't divide by zero, not enough time has passed
            averageProfitPerMillisecond = 0;
        }

        averageProfitThousandForHour = (long)(averageProfitPerMillisecond * 3600);

        return averageProfitThousandForHour;
    }
}
