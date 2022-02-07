package com.webbdealer.dms.sales;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import reactor.core.publisher.Mono;

@KafkaClient
public interface InventoryClient {

    @Topic("sales")
    Mono<Sale> updateInventory(Sale sale);
}
