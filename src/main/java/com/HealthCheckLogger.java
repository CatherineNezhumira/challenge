package com;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class HealthCheckLogger {

    private static final Logger LOGGER = Logger.getLogger(HealthCheckerThread.class.getName());
    private static final String LOG_FILE_NAME = "service_healthcheck.log";

    static {
        try {
            FileHandler handler = new FileHandler(LOG_FILE_NAME, true);
            LOGGER.addHandler(handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logTimoutError() {
        LOGGER.severe("Error while pinging service: Timed out");
    }

    public static void logNetworkError() {
        LOGGER.severe("Error while pinging service: %s");
    }

    public static void logHealthCheckSuccessRate(int successRate) {
        LOGGER.info(String.format("Health check success rate is %s", successRate));
    }

}
