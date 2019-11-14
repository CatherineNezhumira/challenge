package com;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.HealthCheckStatus;
import com.ServiceHealthCheck;
import org.apache.http.conn.ConnectTimeoutException;
import org.mockito.internal.util.reflection.FieldSetter;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class ServiceHealthCheckTest {

    private static final String SUCCESS_RESPONSE_BODY = "Magnificent!";
    private static final String FAILURE_RESPONSE_BODY = "Error!";

    @Mock
    private HttpClient client;
    @Mock
    HttpResponse<String> response;

    private ServiceHealthCheck serviceHealthCheck = ServiceHealthCheck.getInstance();

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        initMocks(this);

        FieldSetter.setField(serviceHealthCheck, ServiceHealthCheck.class.getDeclaredField("client"), client);
    }

    @Test
    void shouldReturnHealthyStatusOnSuccess() throws IOException, InterruptedException {
        when(response.body()).thenReturn(SUCCESS_RESPONSE_BODY);
        when(client.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);

        assertEquals(HealthCheckStatus.HEALTHY, serviceHealthCheck.getServiceStatus());
    }

    @Test
    void shouldReturnIncorrectResponseStatusOnFailure() throws IOException, InterruptedException {
        when(response.body()).thenReturn(FAILURE_RESPONSE_BODY);
        when(client.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(response);

        assertEquals(HealthCheckStatus.INCORRECT_RESPONSE, serviceHealthCheck.getServiceStatus());
    }

    @Test
    void shouldReturnTimeoutStatus() throws IOException, InterruptedException {
        when(client.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenThrow(new ConnectTimeoutException());

        assertEquals(HealthCheckStatus.TIMED_OUT, serviceHealthCheck.getServiceStatus());
    }

}