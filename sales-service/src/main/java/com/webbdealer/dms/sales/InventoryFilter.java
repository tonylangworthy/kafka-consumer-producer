package com.webbdealer.dms.sales;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Filter("/sales")
public class InventoryFilter implements HttpServerFilter {

    private final InventoryClient salesClient;

    public InventoryFilter(InventoryClient inventoryClient) { // <3>
        this.salesClient = inventoryClient;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                                      ServerFilterChain chain) {
        System.out.println("made it here");
        return Flux
                .from(chain.proceed(request))
                .flatMap(response -> {
                    Sale sale = response.getBody(Sale.class).orElse(null);
                    System.out.println("Sale: " + sale.toString());
                    if (sale == null) {
                        return Flux.just(response);
                    }
                    return Flux.from(salesClient.updateInventory(sale)).map(b -> response);
                });
    }
}
