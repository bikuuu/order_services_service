package com.pranienawezwanie.orderservicesservice.basic;

import com.pranienawezwanie.orderservicesservice.model.ServiceType;

public class Main {
    public static void main(String[] args) {
        Services tapicerka = new Services("tap",200.00,1, ServiceType.WEWNETRZNA);
        System.out.println(tapicerka.getPrice());
        ServiceWithImpregnation impregnation = new ServiceWithImpregnation(tapicerka);
        System.out.println(impregnation.getPrice());
        ServiceWithImpregnation impregnationAndDisinfection = new ServiceWithImpregnation(new ServiceWithDisinfection(tapicerka));
        System.out.println(impregnationAndDisinfection.getPrice());

    }

}
