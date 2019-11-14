package com;

import java.util.List;

public class HealthCheckStatisticsCounter {

    private static final int PERCENTAGE = 100;

    static int countSuccessRate(List<Boolean> healthCheckHistory, int healthCheckHistoryRate) {
        long successResponseCount = healthCheckHistory.stream().filter(val -> val).count();
        return Math.round(successResponseCount * PERCENTAGE / healthCheckHistoryRate);
    }

}
