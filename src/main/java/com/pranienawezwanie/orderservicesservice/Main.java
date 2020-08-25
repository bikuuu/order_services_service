package com.pranienawezwanie.orderservicesservice;

import com.pranienawezwanie.orderservicesservice.database.LoginDao;
import com.pranienawezwanie.orderservicesservice.handlers.UserHandler;
import com.pranienawezwanie.orderservicesservice.menus.AdminMenu;
import com.pranienawezwanie.orderservicesservice.menus.UserMenu;
import com.pranienawezwanie.orderservicesservice.model.*;
import com.pranienawezwanie.orderservicesservice.database.HibernateUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static com.pranienawezwanie.orderservicesservice.model.UserType.ADMIN;
import static com.pranienawezwanie.orderservicesservice.model.UserType.USER;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static final int WORK_START_TIME = 8;
    private static final int WORK_HOURS = 8;

    public static void main(String[] args) {
        HibernateUtil.getOurSessionFactory();
        UserHandler userHandler = new UserHandler();
        LoginDao loginDao = new LoginDao();
        AdminMenu adminMenu = new AdminMenu();
        UserMenu userMenu = new UserMenu();
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

        if (loggedInUser.getUserType().equals(USER) || loggedInUser.getUserType().equals(null)) {
            userMenu.showUserMenu();
        } else if (loggedInUser.getUserType().equals(ADMIN)) {
            adminMenu.showAdminMenu();
        }
    }
}




