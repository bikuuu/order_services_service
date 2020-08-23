package com.pranienawezwanie.orderservicesservice;


import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.handlers.ExtraServiceHandler;
import com.pranienawezwanie.orderservicesservice.handlers.ServiceHandler;
import com.pranienawezwanie.orderservicesservice.handlers.UserHandler;
import com.pranienawezwanie.orderservicesservice.model.*;
import com.pranienawezwanie.orderservicesservice.database.HibernateUtil;
import com.pranienawezwanie.orderservicesservice.model.ExtraService;
import com.pranienawezwanie.orderservicesservice.model.Service;

import java.util.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class Main {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        HibernateUtil.getOurSessionFactory();
        System.out.println("Initial version.");
        Scanner scanner = new Scanner(System.in);
        UserHandler userHandler = new UserHandler();
        ServiceHandler serviceHandler = new ServiceHandler();
        ExtraServiceHandler extraServiceHandler = new ExtraServiceHandler();

        String[] words;
        String command;
        boolean status = false;

        do {
            System.out.println("Wprowadź komendę [login/register]: ");
            command = scanner.nextLine();
            words = command.split(" ");
            if (words[0].equalsIgnoreCase("login")) {
                boolean checker = false;
                System.out.println("Podaj login i hasło: {login} {password}");
                command = scanner.nextLine();
                do {
                    checker = userHandler.login(words);
                    if (checker == false) {
                        System.out.println("Nieprawidłowy login lub hasło, spróbuj ponownie " +
                                "lub wybierz zarejestruj nowego użytkownika podając register");
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } while (checker == true);
                status = true;
                System.out.println("Witaj, " + words[0]);

            } else if (words[0].equalsIgnoreCase("register")) {
                System.out.println("- [user add {name} {surname} {login} {password}] ");
                command = scanner.nextLine();
                words = command.split(" ");
                userHandler.handle(words);
                System.out.println("Witaj, " + words[2]);
            }
        } while (status == true);

        do {
            System.out.println("Wprowadź komendę: ");
            printAllOptions();
            command = scanner.nextLine();
            words = command.split(" ");

            if (words[0].equalsIgnoreCase("user")) {
                userHandler.handle(words);
            } else if (words[0].equalsIgnoreCase("service")) {
                serviceHandler.handle(words);
            } else if (words[0].equalsIgnoreCase("extraService")) {
                extraServiceHandler.handle(words);
            } else if (words[0].equalsIgnoreCase("order") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddOrder(words);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void handleAddOrder(String[] words) {
        EntityDao<ServiceOrder> serviceOrderEntityDao = new EntityDao<>();
        EntityDao<Service> serviceEntityDao = new EntityDao<>();
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        EntityDao<ExtraService> extraServiceEntityDao = new EntityDao<>();

        Long id = Long.parseLong(words[2]); // id uzytkownika
        Optional<AppUser> appUserOptional = appUserEntityDao.findById(AppUser.class, id);
        if (appUserOptional.isPresent()) { // uzytkownik istnieje
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

    private static Set<ExtraService> getAllExtraServicesFromUser(Service service) {
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

    private static Optional<Service> askUserForService
            (List<Service> allServicesByName, EntityDao<Service> serviceEntityDao) {
        List<Service> serviceList = allServicesByName;
        System.out.println("To znalezione wyniki, którą usługę wybierasz:");
        serviceList.forEach(System.out::println);
        System.out.println();
        System.out.println("Wprowadź ID:");

        Long id = Long.parseLong(scanner.nextLine()); // id uzytkownika
        return serviceEntityDao.findById(Service.class, id);
    }

    private static void printAllOptions() {
        System.out.println("- [user list] ");
        System.out.println("- [user add {name} {surname} {login} {password}] ");
        System.out.println("- [user type change {userId} {userType}] ");
        System.out.println("- [service list] ");
        System.out.println("- [service add {name} {price} {duration} {type}] ");
        System.out.println("- [extraService add {name} {additionalCost}] ");
        System.out.println("- [order add]");
    }

}




