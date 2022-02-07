package com.webbdealer.dms.sales;

import jakarta.inject.Singleton;

@Singleton
public class SalesServiceImpl implements SalesService {

    @Override
    public Sale storeSale(Sale sale) {
        return sale;
    }
}
