package com.pranienawezwanie.orderservicesservice.basic;


public class ServiceWithImpregnation {

    private static final double IMPREGNATION_PRICE = 100.00;
    private Service services;
    public ServiceWithImpregnation(Service service){
        this.services =service;
    }


    public double getPrice(Service service) {
        return service.getPrice() + IMPREGNATION_PRICE;
    }

    @Override
    public String toString() {
        return "ServiceWithImpre{" +
                "services=" + services +
                '}';
    }
}
