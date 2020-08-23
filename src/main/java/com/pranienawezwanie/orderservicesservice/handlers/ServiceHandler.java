package com.pranienawezwanie.orderservicesservice.handlers;

import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.model.AppUser;
import com.pranienawezwanie.orderservicesservice.model.Service;
import com.pranienawezwanie.orderservicesservice.model.ServiceType;

import java.util.Scanner;

public class ServiceHandler {
    private Scanner scanner = new Scanner(System.in);
    private EntityDao<Service> serviceEntityDao = new EntityDao<>();

    public void handle(String[] words) {
        if (words[1].equalsIgnoreCase("list")) {
            handleService(words);
        } else if (words[1].equalsIgnoreCase("add")) {
            handleAddService(words);
        }
    }

    private void handleAddService(String[] words) {

        Service service = Service.builder()
                .name(words[2])
                .price(Double.valueOf(words[3]))
                .duration(Integer.valueOf(words[4]))
                .type(ServiceType.valueOf(words[5]))
                .build();

        serviceEntityDao.saveOrUpdate(service);
        System.out.println("Service aded "+service.getId());
    }

    private void handleService(String[] words) {
        serviceEntityDao
                .findAll(Service.class)
                .forEach(System.out::println);
    }
}