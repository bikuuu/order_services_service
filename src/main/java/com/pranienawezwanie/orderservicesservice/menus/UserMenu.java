package com.pranienawezwanie.orderservicesservice.menus;

import com.pranienawezwanie.orderservicesservice.handlers.AddressHandler;
import com.pranienawezwanie.orderservicesservice.handlers.ExtraServiceHandler;
import com.pranienawezwanie.orderservicesservice.handlers.ServiceHandler;

import java.util.Scanner;

import static com.pranienawezwanie.orderservicesservice.handlers.OrderHandler.handleAddOrder;

public class UserMenu {
    private static Scanner scanner = new Scanner(System.in);
    ServiceHandler serviceHandler = new ServiceHandler();
    ExtraServiceHandler extraServiceHandler = new ExtraServiceHandler();
    AddressHandler addressHandler = new AddressHandler();
    String command;

    public void showUserMenu() {
        user();
    }

    private void user() {
        do {
            System.out.println("Wprowadź komendę: ");
            printAllOptions();
            command = scanner.nextLine();
            String[] words = command.split(" ");

            if (words[0].equalsIgnoreCase("address")) {
                addressHandler.addressHandler(words);
            } else if (words[0].equalsIgnoreCase("order") &&
                    words[1].equalsIgnoreCase("add")) {
                handleAddOrder(words);
            }
        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void printAllOptions() {
        System.out.println("- [service list] ");
        System.out.println("- [order add] ");
        System.out.println("- [quit]");
    }
}
