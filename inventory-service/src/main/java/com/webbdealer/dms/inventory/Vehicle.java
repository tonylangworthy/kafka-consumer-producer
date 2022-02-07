package com.webbdealer.dms.inventory;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Introspected
public class Vehicle {

    @NonNull
    @NotBlank
    private final String vin;

    private final String year;

    private final String make;

    private final String model;

    private boolean sold;

    public Vehicle(@NonNull String vin, String year, String make, String model, boolean sold) {
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.sold = sold;
    }

    @NonNull
    public String getVin() {
        return vin;
    }

    public String getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return sold == vehicle.sold && vin.equals(vehicle.vin) && Objects.equals(year, vehicle.year) && Objects.equals(make, vehicle.make) && Objects.equals(model, vehicle.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vin, year, make, model, sold);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vin='" + vin + '\'' +
                ", year='" + year + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", sold=" + sold +
                '}';
    }
}
