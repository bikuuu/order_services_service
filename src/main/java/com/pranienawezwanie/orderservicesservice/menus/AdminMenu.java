package com.pranienawezwanie.orderservicesservice.menus;

import com.pranienawezwanie.orderservicesservice.handlers.AddressHandler;
import com.pranienawezwanie.orderservicesservice.handlers.ExtraServiceHandler;
import com.pranienawezwanie.orderservicesservice.handlers.ServiceHandler;
import com.pranienawezwanie.orderservicesservice.handlers.UserHandler;

import java.util.Scanner;

import static com.pranienawezwanie.orderservicesservice.handlers.OrderHandler.handleAddOrder;

public class AdminMenu {
    private static Scanner scanner = new Scanner(System.in);
    UserHandler userHandler = new UserHandler();
    ServiceHandler serviceHandler = new ServiceHandler();
    ExtraServiceHandler extraServiceHandler = new ExtraServiceHandler();
    AddressHandler addressHandler = new AddressHandler();
    String command;

    public void showAdminMenu() {
        admin();
    }

    private void admin() {
        do {
            System.out.println("Wprowadź komendę: ");
            printAllOptions();
            command = scanner.nextLine();
            String[] words = command.split(" ");

            if (words[0].equalsIgnoreCase("user")) {
                userHandler.handle(words);
            } else if (words[0].equalsIgnoreCase("address")) {
                addressHandler.addressHandler(words);
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

    private static void printAllOptions() {
        System.out.println("- [user list] ");
        System.out.println("- [user add {name} {surname} {login} {password}] ");
        System.out.println("- [user type change {userId} {userType}] ");
        System.out.println("- [service list] ");
        System.out.println("- [service add {name} {price} {duration} {type}] ");
        System.out.println("- [extraService add {name} {additionalCost}] ");
        System.out.println("- [order add]");
        System.out.println("- [quit]");
    }
}
