package com.pranienawezwanie.orderservicesservice.basic;

import lombok.Data;


public class ServiceWithDisinfection extends Services {
    private static final double DISINFECTION_PRICE = 50.00;
    private Services services;

    public ServiceWithDisinfection(Services services){
        this.services= services;
    }
    public double getPrice() {
        return services.getPrice() + DISINFECTION_PRICE;
    }


}
