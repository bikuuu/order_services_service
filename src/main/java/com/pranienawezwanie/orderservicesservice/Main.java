package com.pranienawezwanie.orderservicesservice;


import com.pranienawezwanie.orderservicesservice.database.*;
import com.pranienawezwanie.orderservicesservice.handlers.ExtraServiceHandler;
import com.pranienawezwanie.orderservicesservice.handlers.ServiceHandler;
import com.pranienawezwanie.orderservicesservice.handlers.UserHandler;
import com.pranienawezwanie.orderservicesservice.model.*;
import com.pranienawezwanie.orderservicesservice.model.ExtraService;
import com.pranienawezwanie.orderservicesservice.model.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static final int WORK_START_TIME = 8;
    private static final int WORK_HOURS = 8;

    public static void main(String[] args) {
        HibernateUtil.getOurSessionFactory();
        System.out.println("Initial version.");
        Scanner scanner = new Scanner(System.in);
        UserHandler userHandler = new UserHandler();
        ServiceHandler serviceHandler = new ServiceHandler();
        AppUserDao appUserDao = new AppUserDao();
        LoginDao loginDao = new LoginDao();
        ExtraServiceHandler extraServiceHandler = new ExtraServiceHandler();
        AppUser loggedInUser = null;

        String command;
        do {
            System.out.println("Wprowadź komendę [login/register]: ");
            command = scanner.nextLine();
            String[] words = command.split(" ");
            if (words[0].equalsIgnoreCase("login")) {
                System.out.println("Podaj login i hasło: {login} {password}");
                command = scanner.nextLine();
                words = command.split(" ");
                Optional<AppUser> appUserOptional = loginDao.findByLogin(words[0], words[1]);
                if (appUserOptional.isPresent()) {
                    loggedInUser = appUserOptional.get();
                }
                System.out.println("Witaj, " + words[0]);

            } else if (words[0].equalsIgnoreCase("register")) {
                System.out.println("- [user add {name} {surname} {login} {password}] ");
                command = scanner.nextLine();
                words = command.split(" ");
                userHandler.handle(words);
                System.out.println("Witaj, " + words[2]);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        } while (loggedInUser == null);

        do {
            System.out.println("Wprowadź komendę: ");
            printAllOptions();
            command = scanner.nextLine();
            String[] words = command.split(" ");

            if (words[0].equalsIgnoreCase("user")) {
                userHandler.handle(words);
            } else if (words[0].equalsIgnoreCase("service")) {
                serviceHandler.handle(words);
            } else if (words[0].equalsIgnoreCase("extraService")) {
                extraServiceHandler.handle(words);
            } else if (words[0].equalsIgnoreCase("order") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddOrder(words);
            } else if (words[0].equalsIgnoreCase("schedule") &&
                    words[1].equalsIgnoreCase("add")) {
                handleScheduleAdd(words);
            } else if (words[0].equalsIgnoreCase("schedule") &&
                    words[1].equalsIgnoreCase("available")) {
                handleScheduleShowAvailable(words);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void handleScheduleShowAvailable(String[] words) {
        EntityDao<Schedule> scheduleEntityDao = new EntityDao<>();
        EntityDao<ServiceOrder> serviceOrderEntityDao = new EntityDao<>();

        ScheduleDao scheduleDao1 = new ScheduleDao();
        // yyyy-MM-dd
        List<Schedule> schedulesByDate = scheduleDao1.findSchedulesByDate(LocalDate.parse(words[2]));

        int scheduleIt = 0;
        for (int i = 0; i < WORK_HOURS; i++) {
            Schedule schedule = schedulesByDate.get(scheduleIt);
            if(schedule.getSlotNumber() == i){
                System.out.println("Godzina " + (WORK_START_TIME + i) + " zajęty "/* + schedule.getServiceOrder().getService().getName()*/);
                scheduleIt++;
            }else{
                System.out.println("Godzina " + (WORK_START_TIME + i) + " wolny.");
            }
        }
    }

    private static void handleScheduleAdd(String[] words) {
        EntityDao<Schedule> scheduleEntityDao = new EntityDao<>();
        Schedule schedule = Schedule.builder().
                date(LocalDate.parse(words[2]))
                .duration(Integer.valueOf(words[3]))
                .slotNumber(Integer.valueOf(words[4]))
                .build();

        scheduleEntityDao.saveOrUpdate(schedule);
    }

    private static void handleAddOrder(String[] words) {
        EntityDao<ServiceOrder> serviceOrderEntityDao = new EntityDao<>();
        EntityDao<Service> serviceEntityDao = new EntityDao<>();
        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        EntityDao<ExtraService> extraServiceEntityDao = new EntityDao<>();

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

        Long id = Long.parseLong(scanner.nextLine());
        return serviceEntityDao.findById(Service.class, id);
    }

    private static void printAllOptions() {
        System.out.println("- [user list] ");
        System.out.println("- [user add {name} {surname} {login} {password}] ");
        System.out.println("- [user type change {userId} {userType}] ");
        System.out.println("- [service list] ");
        System.out.println("- [service add {name} {price} {duration} {type}] ");
        System.out.println("- [extraService add {name} {additionalCost}] ");
        System.out.println("- [schedule available yyyy-MM-dd ");
        System.out.println("- [order add]");
    }

}




