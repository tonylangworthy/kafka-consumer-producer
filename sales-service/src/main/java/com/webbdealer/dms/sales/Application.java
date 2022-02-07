package com.webbdealer.dms.sales;

import io.micronaut.runtime.Micronaut;

import static io.micronaut.context.env.Environment.DEVELOPMENT;

public class Application {

    public static void main(String[] args) {
//        Micronaut.run(Application.class, args);
        Micronaut.build(args)
                .mainClass(Application.class)
                .defaultEnvironments(DEVELOPMENT)
                .start();
    }
}
