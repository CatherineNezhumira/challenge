package com;

import java.util.ArrayList;
import java.util.List;

public class HealthCheckerThread extends Thread {

    private static final int HEALTH_CHECK_HISTORY_RATE = 10;
    private static final int FIVE_SECOND_INTERVAL = 1000 * 5;

    private List<Boolean> healthCheckHistory = new ArrayList<>();

    private ServiceHealthCheck serviceHealthCheck = ServiceHealthCheck.getInstance();

    public void run() {

        while (true) {

            switch (serviceHealthCheck.getServiceStatus()) {
                case HEALTHY:
                    updateHealthCheckHistory(Boolean.TRUE);
                    break;
                case TIMED_OUT:
                    HealthCheckLogger.logTimoutError();
                    updateHealthCheckHistory(Boolean.FALSE);
                    break;
                case INCORRECT_RESPONSE:
                    updateHealthCheckHistory(Boolean.FALSE);
                    break;
                case NETWORK_ERROR:
                    HealthCheckLogger.logNetworkError();
                    break;
            }

            try {
                Thread.sleep(FIVE_SECOND_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void updateHealthCheckHistory(Boolean lastHealthCheckResult) {
        if (healthCheckHistory.size() == HEALTH_CHECK_HISTORY_RATE) {
            int successRate = HealthCheckStatisticsCounter.countSuccessRate(healthCheckHistory, HEALTH_CHECK_HISTORY_RATE);
            HealthCheckLogger.logHealthCheckSuccessRate(successRate);

            healthCheckHistory.clear();
        }
        healthCheckHistory.add(lastHealthCheckResult);
    }

}
