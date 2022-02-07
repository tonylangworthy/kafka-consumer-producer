package com.webbdealer.dms.inventory;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedDeque;

import static io.micronaut.configuration.kafka.annotation.OffsetReset.EARLIEST;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SalesListenerTest implements TestPropertyProvider {

    private static final Collection<Vehicle> received = new ConcurrentLinkedDeque<>();

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));


    @Inject
    InventoryListener inventoryListener;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testMessageIsPublishedToKafkaWhenBookFound() {
        String vin = "WBA3B3C50DF538780";

        Optional<Vehicle> result = retrieveGet("/inventory/vehicles/" + vin);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(vin, result.get().getVin());

        await().atMost(20, SECONDS).until(() -> !received.isEmpty());

        assertEquals(1, received.size());
        Vehicle vehicleFromKafka = received.iterator().next();
        assertNotNull(vehicleFromKafka);
        assertEquals(vin, vehicleFromKafka.getVin());
    }

    @Test
    void testMessageIsNotPublishedToKafkaWhenVehicleNotFound() throws Exception {
        assertThrows(HttpClientResponseException.class, () -> {
            retrieveGet("/inventory/vehicles/INVALID");
        });

        Thread.sleep(5_000);
        assertEquals(0, received.size());
    }

    @NonNull
    @Override
    public Map<String, String> getProperties() {
        return Collections.singletonMap(
                "kafka.bootstrap.servers", kafkaContainer.getBootstrapServers()
        );
    }

    @AfterEach
    void cleanup() {
        received.clear();
    }

    @KafkaListener(offsetReset = EARLIEST)
    static class SalesListener {

        @Topic("sales")
        void updateAnalytics(Vehicle vehicle) {
            received.add(vehicle);
        }
    }

    private Optional<Vehicle> retrieveGet(String url) {
        return client.toBlocking().retrieve(HttpRequest.GET(url), Argument.of(Optional.class, Vehicle.class));
    }
}