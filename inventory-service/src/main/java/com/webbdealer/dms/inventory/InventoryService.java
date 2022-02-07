package com.webbdealer.dms.inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<Vehicle> listAll();

    Optional<Vehicle> findByVin(String vin);

    void markVehicleAsSold(String vin);
}
