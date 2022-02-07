package com.webbdealer.dms.inventory;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;
import java.util.Optional;

@Controller("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Get("/vehicles")
    List<Vehicle> getAllVehicles() {
        return inventoryService.listAll();
    }

    @Get("/vehicles/{vin}")
    Optional<Vehicle> getVehicleByVin(String vin) {
        return inventoryService.findByVin(vin);
    }
}
