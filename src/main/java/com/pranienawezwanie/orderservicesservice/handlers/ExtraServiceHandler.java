package com.pranienawezwanie.orderservicesservice.handlers;

import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.database.ServiceDao;
import com.pranienawezwanie.orderservicesservice.model.ExtraService;
import com.pranienawezwanie.orderservicesservice.model.Service;

import java.util.*;
import java.util.stream.Collectors;

public class ExtraServiceHandler {

        private static Scanner scanner = new Scanner(System.in);
        private EntityDao<ExtraService> extraServiceEntityDao = new EntityDao<>();

    public void handle(String[] words) {
        if (words[1].equalsIgnoreCase("list")) {
            handleExtraServiceAdd(words);
        } else if (words[1].equalsIgnoreCase("add")) {
            handleExtraService(words);
        }
    }

    private void handleExtraService(String[] words) {
        EntityDao<ExtraService> appUserEntityDao = new EntityDao<>();
        appUserEntityDao
                .findAll(ExtraService.class)
                .forEach(System.out::println);
    }

    private void handleExtraServiceAdd(String[] words) {

            ServiceDao serviceDao = new ServiceDao();
            EntityDao<Service> serviceEntityDao = new EntityDao<>();
            extraServiceEntityDao = new EntityDao<>();
            Optional<Service> optionalService = askUserForService(serviceDao.findAllServicesByName(words[2]), serviceEntityDao);

            if (optionalService.isPresent()) {
                Service service = optionalService.get();
                String input;
                do {
                    System.out.println("Podaj nazwę usługi:");
                    String name = scanner.nextLine();
                    System.out.println("Podaj cene usługi:");
                    Double additionalCost = Double.parseDouble(scanner.nextLine());

                    ExtraService extraService = new ExtraService(name, additionalCost);
                    extraServiceEntityDao.saveOrUpdate(extraService);

                    service.getAvailableExtraServices().add(extraService);
                    serviceEntityDao.saveOrUpdate(service);

                    System.out.println("Czy chcesz dodać kolejną extra usługę? [y/n]");
                    input = scanner.nextLine();
                } while (input.equalsIgnoreCase("y") ||
                        input.equalsIgnoreCase("t") ||
                        input.startsWith("y") ||
                        input.startsWith("t"));
            } else {
                System.err.println("Wprowadzono niepoprawną usługę");
            }
        }

    public static Set<ExtraService> getAllExtraServicesFromUser(Service service) {
        List<ExtraService> extraServices = new ArrayList<>();
        String input;
        do {
            System.out.println("Extra services, enter id:");
            service.getAvailableExtraServices().forEach(System.out::println);

            Long id = Long.valueOf(scanner.nextLine());
            extraServices.addAll(
                    service.getAvailableExtraServices().stream()
                            .filter(extraService -> extraService.getId() == id)
                            .collect(Collectors.toSet()));
            System.out.println("Czy chcesz dodać kolejne usługi? [y/n]");
            input = scanner.nextLine();

        } while (input.equalsIgnoreCase("y") ||
                input.equalsIgnoreCase("t") ||
                input.startsWith("y") ||
                input.startsWith("t"));
        return new HashSet<>(extraServices);
    }

    public static Optional<Service> askUserForService
            (List<Service> allServicesByName, EntityDao<Service> serviceEntityDao) {
        List<Service> serviceList = allServicesByName;
        System.out.println("To znalezione wyniki, którą usługę wybierasz:");
        serviceList.forEach(System.out::println);
        System.out.println();
        System.out.println("Wprowadź ID:");

        Long id = Long.parseLong(scanner.nextLine());
        return serviceEntityDao.findById(Service.class, id);
    }
}
