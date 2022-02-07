package com.webbdealer.dms.sales;

import io.micronaut.core.annotation.Introspected;

import java.util.Objects;

@Introspected
public class Sale {

    private final String salesDate;
    private final String salesPerson;
    private final Vehicle vehicle;

    public Sale(String salesDate, String salesPerson, Vehicle vehicle) {
        this.salesDate = salesDate;
        this.salesPerson = salesPerson;
        this.vehicle = vehicle;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public String getSalesPerson() {
        return salesPerson;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(salesDate, sale.salesDate) && Objects.equals(salesPerson, sale.salesPerson) && Objects.equals(vehicle, sale.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salesDate, salesPerson, vehicle);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "salesDate='" + salesDate + '\'' +
                ", salesPerson='" + salesPerson + '\'' +
                ", vehicle=" + vehicle +
                '}';
    }
}
