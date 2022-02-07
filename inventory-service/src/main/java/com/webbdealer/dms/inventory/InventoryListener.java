package com.webbdealer.dms.inventory;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

@Requires(notEnv = Environment.TEST)
@KafkaListener
public class InventoryListener {

    private final InventoryService inventoryService;

    public InventoryListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Topic("sales")
    public void updateInventory(Sale sale) {
        System.out.println("Received event for vehicle with vin " + sale.getVehicle().getVin());
        inventoryService.markVehicleAsSold(sale.getVehicle().getVin());
    }
}
