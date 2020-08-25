package com.pranienawezwanie.orderservicesservice.handlers;

import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.model.AppUser;
import com.pranienawezwanie.orderservicesservice.model.ExtraService;
import com.pranienawezwanie.orderservicesservice.model.Service;
import com.pranienawezwanie.orderservicesservice.model.ServiceOrder;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import static com.pranienawezwanie.orderservicesservice.handlers.ExtraServiceHandler.askUserForService;
import static com.pranienawezwanie.orderservicesservice.handlers.ExtraServiceHandler.getAllExtraServicesFromUser;

public class OrderHandler {
    Scanner scanner = new Scanner(System.in);

    public static void handleAddOrder(String[] words) {
        EntityDao<Service> serviceEntityDao = new EntityDao<>();
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();

        Long id = Long.parseLong(words[2]);
        Optional<AppUser> appUserOptional = appUserEntityDao.findById(AppUser.class, id);
        if (appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();

            Optional<Service> optionalService = askUserForService(serviceEntityDao.findAll(Service.class), serviceEntityDao);
            ServiceOrder serviceOrder;
            if (optionalService.isPresent()) {
                Service service = optionalService.get();

                Set<ExtraService> extraServices = getAllExtraServicesFromUser(service);
                serviceOrder = new ServiceOrder();
                serviceOrder.setService(service);
                serviceOrder.setExtraServices(extraServices);
            } else {
                System.err.println("Service does not exist");
                return;
            }
            appUser.getServiceOrders().add(serviceOrder);
            appUserEntityDao.saveOrUpdate(appUser);
        } else {
            System.err.println("User does not exist");
        }
    }

}
