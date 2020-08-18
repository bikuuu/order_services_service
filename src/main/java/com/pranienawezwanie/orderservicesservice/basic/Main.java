package com.pranienawezwanie.orderservicesservice.basic;


public class Main {
    public static void main(String[] args) {

        Service service1 = Service.WASHING_ARMCHAIR;
        System.out.println(service1);
        System.out.println(service1.getPrice());
        ServiceWithImpregnation serviceWithImpregnation = new ServiceWithImpregnation(service1);
        System.out.println(serviceWithImpregnation.getPrice(service1));


    }

}
