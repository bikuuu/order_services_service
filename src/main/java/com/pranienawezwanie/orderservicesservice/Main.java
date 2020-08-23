package com.pranienawezwanie.orderservicesservice;


import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.database.HibernateUtil;
import com.pranienawezwanie.orderservicesservice.handlers.UserHandler;
import com.pranienawezwanie.orderservicesservice.model.ExtraService;
import com.pranienawezwanie.orderservicesservice.model.Service;
import com.pranienawezwanie.orderservicesservice.model.ServiceType;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        HibernateUtil.getOurSessionFactory();
        System.out.println("Initial version.");
        Scanner scanner = new Scanner(System.in);
        UserHandler userHandler = new UserHandler();
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

            // user list
            if (words[0].equalsIgnoreCase("user")) {
                userHandler.handle(words);
            } else if (words[0].equalsIgnoreCase("service") &&
                    words[1].equalsIgnoreCase("list")) {
                handleService(words);
            } else if (words[0].equalsIgnoreCase("service") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddService(words);
            } else if (words[0].equalsIgnoreCase("ExtraService") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddExtraService(words);
            } else if (words[0].equalsIgnoreCase("ExtraService") &&
                    words[1].equalsIgnoreCase("list")) {
                handleExtraService(words);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void handleExtraService(String[] words) {
        EntityDao<ExtraService> appUserEntityDao = new EntityDao<>();
        appUserEntityDao
                .findAll(ExtraService.class)
                .forEach(System.out::println);
    }

    private static void handleAddExtraService(String[] words) {
        EntityDao<ExtraService> appServiceEntityDao = new EntityDao<>();
        ExtraService extraService = ExtraService.builder()
                .name(words[2])
                .additionalCost(Double.valueOf(words[3]))
                .build();

        appServiceEntityDao.saveOrUpdate(extraService);
        System.out.println(" Extra service aded " + extraService.getId());

    }

    private static void handleAddService(String[] words) {
        EntityDao<Service> appServiceEntityDao = new EntityDao<>();
        Service service = Service.builder()
                .name(words[2])
                .price(Double.valueOf(words[3]))
                .duration(Integer.valueOf(words[4]))
                .type(ServiceType.valueOf(words[5]))
                .build();

        appServiceEntityDao.saveOrUpdate(service);
        System.out.println("Service aded " + service.getId());
    }

    private static void handleService(String[] words) {
        EntityDao<Service> appUserEntityDao = new EntityDao<>();
        appUserEntityDao
                .findAll(Service.class)
                .forEach(System.out::println);
    }

    private static void printAllOptions() {
        System.out.println("- [user list] ");
        System.out.println("- [user add {name} {surname} {login} {password}] ");
        System.out.println("- [user type change {userId} {userType}] ");
        System.out.println("- [service list] ");
        System.out.println("- [add service {name} {price} {duration} {type}] ");
        System.out.println("- [service list] ");
        System.out.println("- [add extra service {name} {additionalCost}] ");
    }
}



