package com.webbdealer.dms.sales;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;

public final class SalesSchemaGenerator {

    public static void main(String args[]) {
        generateSchema();
    }

    public static void generateSchema() {
        Schema sale = SchemaBuilder.record("SaleEvent")
                .namespace("com.webbdealer.dms.sales.event")
                .fields()
                .name("vin")
                .type().stringType().noDefault()
                .endRecord();

        System.out.println(sale.toString());
    }
}
