package com.pranienawezwanie.orderservicesservice.basic;

import lombok.Data;


public class ServiceWithImpregnation extends Services {

    private static final double IMPREGNATION_PRICE = 100.00;
    private Services services;

    public ServiceWithImpregnation(Services services){
        this.services= services;
    }
    public double getPrice() {
        return services.getPrice() + IMPREGNATION_PRICE;
    }

}
