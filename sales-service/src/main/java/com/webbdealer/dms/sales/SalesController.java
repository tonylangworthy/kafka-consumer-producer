package com.webbdealer.dms.sales;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
    public Sale createSale(@Body Sale sale) {
        System.out.println("Marking vehicle with VIN " + sale.getVehicle().getVin() + " as sold!");
        return salesService.storeSale(sale);
    }

}
