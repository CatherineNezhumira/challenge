package com;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.apache.http.conn.ConnectTimeoutException;

public class ServiceHealthCheck {

    private static final String SERVICE_URL = "http://localhost:12345";
    private static final String SUCCESS_RESPONSE_BODY = "Magnificent!";

    private static ServiceHealthCheck serviceHealthCheck;

    private final HttpClient client;

    private final HttpRequest pingRequest;

    private ServiceHealthCheck() {
        this.client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

        this.pingRequest = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(SERVICE_URL))
            .build();
    }

    public static ServiceHealthCheck getInstance() {
        if (serviceHealthCheck == null) {
            serviceHealthCheck = new ServiceHealthCheck();
        }
        return serviceHealthCheck;
    }

    public HealthCheckStatus getServiceStatus() {
        try {
            HttpResponse<String> response = client.send(pingRequest, HttpResponse.BodyHandlers.ofString());

            if (response.body().equals(SUCCESS_RESPONSE_BODY)) {
                return HealthCheckStatus.HEALTHY;
            } else {
                System.out.println("INCORRECT!");
                return HealthCheckStatus.INCORRECT_RESPONSE;
            }

        } catch (ConnectTimeoutException e) {
            return HealthCheckStatus.TIMED_OUT;

        } catch (IOException | InterruptedException e) {
            return HealthCheckStatus.NETWORK_ERROR;
        }
    }

}
