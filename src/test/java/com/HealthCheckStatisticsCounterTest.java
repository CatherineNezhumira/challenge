package com;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.HealthCheckStatisticsCounter;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HealthCheckStatisticsCounterTest {

    private static final int HEALTH_CHECK_HISTORY_RATE = 5;

    @ParameterizedTest
    @MethodSource("healthCheckHistorySuccessRate")
    void countCorrectSuccessRateForGivenHealthCheckHistory(List<Boolean> healthCheckHistory, int successRate) {

        assertEquals(successRate, HealthCheckStatisticsCounter
            .countSuccessRate(healthCheckHistory, HEALTH_CHECK_HISTORY_RATE));
    }

    private static Stream<Arguments> healthCheckHistorySuccessRate() {
        return Stream.of(
            Arguments.of(List.of(FALSE, FALSE, FALSE, FALSE, FALSE), 0),
            Arguments.of(List.of(TRUE, TRUE, TRUE, TRUE, TRUE), 100),
            Arguments.of(List.of(TRUE, TRUE, FALSE, FALSE, FALSE), 40));
    }
}