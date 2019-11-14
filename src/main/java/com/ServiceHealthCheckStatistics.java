package com;

public class ServiceHealthCheckStatistics {

    public static void main(String[] args) {

        HealthCheckerThread healthCheckerThread = new HealthCheckerThread();
        healthCheckerThread.start();
        healthCheckerThread.setDaemon(true);

    }

}
