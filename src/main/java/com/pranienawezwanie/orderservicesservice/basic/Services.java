package com.pranienawezwanie.orderservicesservice.basic;


import com.pranienawezwanie.orderservicesservice.model.ServiceType;
import lombok.Data;


public class Services {


    private String name;
    private Double price;
    private Integer duration;
    private ServiceType type;

    public Services(String name, Double price, Integer duration, ServiceType type) {
        this.name = name;
        this.price = price;
        this.duration = duration;
        this.type = type;
    }

    public Services() {
    }

    public double getPrice(){
        return price;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Services{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", type=" + type +
                '}';
    }
}
