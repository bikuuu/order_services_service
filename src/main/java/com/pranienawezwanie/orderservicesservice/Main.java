package com.pranienawezwanie.orderservicesservice;

import com.pranienawezwanie.orderservicesservice.database.AppUserDao;
import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.handlers.UserHandler;
import com.pranienawezwanie.orderservicesservice.model.Address;
import com.pranienawezwanie.orderservicesservice.model.AppUser;
import com.pranienawezwanie.orderservicesservice.model.UserType;


import javax.swing.text.html.parser.Entity;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initial version.");
        Scanner scanner = new Scanner(System.in);
        UserHandler userHandler = new UserHandler();
        String command;

        do {
            System.out.println("Wprowadz komende: ");
            printAllOptions();
            command = scanner.nextLine();

            String[] words = command.split(" ");


            // user list
            if (words[0].equalsIgnoreCase("user")) {
                userHandler.handle(words);
            }


        } while (!command.equalsIgnoreCase("quit"));
    }

    private static void printAllOptions() {
        System.out.println("- [user list] ");
        System.out.println("- [user add {name} {surname} {login} {password}] ");
        System.out.println("- [user type change {userId} {userType}] ");
    }



}
