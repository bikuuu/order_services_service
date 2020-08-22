package com.pranienawezwanie.orderservicesservice;


import com.pranienawezwanie.orderservicesservice.database.AppUserDao;
import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.model.AppUser;
import com.pranienawezwanie.orderservicesservice.model.ExtraService;
import com.pranienawezwanie.orderservicesservice.model.Service;
import com.pranienawezwanie.orderservicesservice.model.ServiceType;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Initial version.");
        Scanner scanner = new Scanner(System.in);

        String command;

        do {
            System.out.println("Wprowadz komende: ");
            printAllOptions();
            command = scanner.nextLine();

            String[] words = command.split(" ");


            // user list
            if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("list")) {
                handleListUsers(words);
            } else if (words[0].equalsIgnoreCase("user") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddUser(words);
            } else if (words[0].equalsIgnoreCase("service") &&
                    words[1].equalsIgnoreCase("list")) {
                handleService(words);
            }else if (words[0].equalsIgnoreCase("service") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddService(words);
            }else if (words[0].equalsIgnoreCase("ExtraService") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddExtraService(words);
            }else if (words[0].equalsIgnoreCase("ExtraService") &&
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
            System.out.println(" Extra service aded "+extraService.getId());

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
        System.out.println("Service aded "+service.getId());
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
        System.out.println("- [service list] ");
        System.out.println("- [add service {name} {price} {duration} {type}] ");
        System.out.println("- [service list] ");
        System.out.println("- [add extra service {name} {additionalCost}] ");


    }

    private static void handleAddUser(String[] words) {
        AppUserDao appUserDao = new AppUserDao();
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        if (!appUserDao.existsUserWithLogin(words[4])) {
            AppUser appUser = AppUser.builder()
                    .firstName(words[2])
                    .lastName(words[3])
                    .login(words[4])
                    .password(words[5])
                    .build();

            appUserEntityDao.saveOrUpdate(appUser);
            System.out.println("User saved: " + appUser.getId());

        } else {
            System.err.println("User cannot be saved. Login already exists.");
        }
    }

    private static void handleListUsers(String[] words) {
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao
                .findAll(AppUser.class)
                .forEach(System.out::println);
    }
}



