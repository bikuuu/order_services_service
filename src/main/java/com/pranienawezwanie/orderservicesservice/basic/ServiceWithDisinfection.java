package com.pranienawezwanie.orderservicesservice.basic;

import lombok.Data;


public class ServiceWithDisinfection {
        private static final double IMPREGNATION_PRICE = 100.00;
        private Service services;
        public ServiceWithDisinfection(Service service){
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

