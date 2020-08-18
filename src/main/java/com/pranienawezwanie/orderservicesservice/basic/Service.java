package com.pranienawezwanie.orderservicesservice.basic;

import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.model.ServiceType;

public enum Service {

    WASHING_COACH("washing the coach", 150.00, 1,ServiceType.WEWNETRZNA),
    WASHING_CARPET("washing the coach", 150.00, 1,ServiceType.WEWNETRZNA),
    WASHING_ARMCHAIR("washing the armchair ", 50.00, 1, ServiceType.WEWNETRZNA),
    WASHING_CAR_UPHOLSTERY("washing car upholestry ", 150.00, 2, ServiceType.ZEWNETRZNA),
    WASHING_MATTRESS("washing mattress ", 100.00, 1, ServiceType.WEWNETRZNA);

    private String name;
    private Double price;
    private Integer duration;
    private ServiceType type;

    Service(String name, double price, int duration, ServiceType type) {
        this.name = name;
        this.price= price;
        this.duration = duration;
        this.type = type;
    }


    public Service addServices(Service service){
        EntityDao entityDao = new EntityDao();
        entityDao.saveOrUpdate(service);
        return service;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getDuration() {
        return duration;
    }

    public ServiceType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", type=" + type +
                '}';
    }

}
