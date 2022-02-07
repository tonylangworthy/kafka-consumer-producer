package com.webbdealer.dms.inventory;

import jakarta.inject.Singleton;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class InventoryServiceImpl implements InventoryService {

    private final List<Vehicle> vehicleList = new ArrayList<>();

    @PostConstruct
    void init() {
        vehicleList.add(new Vehicle("KMHCN4AC2AU419939", "2010", "Hyundai", "Accent", false));
        vehicleList.add(new Vehicle("1C3LC56B19N521100", "2009", "Chrysler", "Sebring", false));
        vehicleList.add(new Vehicle("4F4YR12C1WTM29930", "1998", "Mazda", "B-Series", false));
        vehicleList.add(new Vehicle("WBA3B3C50DF538780", "2013", "BMW", "328i", false));
        vehicleList.add(new Vehicle("5N1AR2MM8EC729139", "2014", "Nissan", "Pathfinder", false));
        vehicleList.add(new Vehicle("SALGS2WF5EA181932", "2014", "Land Rover", "Range Rover", false));
    }

    @Override
    public List<Vehicle> listAll() {

        return vehicleList.stream()
                .filter(v -> !v.isSold())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Vehicle> findByVin(String vin) {
        return vehicleList.stream()
                .filter(v -> v.getVin().equals(vin))
                .findFirst();
    }

    @Override
    public void markVehicleAsSold(String vin) {
        Optional<Vehicle> optionalVehicle = findByVin(vin);
        optionalVehicle.ifPresent(v -> v.setSold(true));
    }
}
