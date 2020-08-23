package com.pranienawezwanie.orderservicesservice.handlers;

import com.pranienawezwanie.orderservicesservice.database.AppUserDao;
import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.model.Address;
import com.pranienawezwanie.orderservicesservice.model.AppUser;
import com.pranienawezwanie.orderservicesservice.model.UserType;

import java.util.Optional;
import java.util.Scanner;

public class UserHandler {
    private Scanner scanner = new Scanner(System.in);
    private EntityDao<AppUser> appUserEntityDao = new EntityDao<>();

    public void handle(String[] words) {
        if (words[1].equalsIgnoreCase("list")) {
            handleListUsers();
        } else if (
                words[1].equalsIgnoreCase("add")) {
            handleAddUser(words);
        } else if (words[1].equalsIgnoreCase("type") &&
                words[2].equalsIgnoreCase("change")) {
            handleChangeUserType(words);
        } else if (words[1].equalsIgnoreCase("addaddress")) {
            handleAddAddress(words);
        }
    }

    private void handleAddAddress(String[] words) {
        Long id = Long.parseLong(words[2]);
        Optional<AppUser> appUserOptional = appUserEntityDao.findById(AppUser.class, id);

        if (appUserOptional.isPresent()) {
            EntityDao<Address> addressEntityDao = new EntityDao<>();
            AppUser appUser = appUserOptional.get();

            Address address = Address.builder()
                    .city(words[2])
                    .houseNum(words[3])
                    .postalCode(words[4])
                    .street(words[5])
                    .user(appUser)
                    .build();

            addressEntityDao.saveOrUpdate(address);
        }
    }

    private void handleAddUser(String[] words) {
        AppUserDao appUserDao = new AppUserDao();
        AppUser appUser;
        if (!appUserDao.existsUserWithLogin(words[4])) {
            appUser = AppUser.builder()
                    .firstName(words[2])
                    .lastName(words[3])
                    .login(words[4])
                    .password(words[5])
                    .userType(UserType.USER)
                    .build();
            appUserEntityDao.saveOrUpdate(appUser);
            System.out.println("User saved: " + appUser.getId());
        } else {
            System.err.println("User cannot be saved. Login already exists.");
        }
    }

    private void handleChangeUserType(String[] word) {
        Long identifier = Long.parseLong(word[3]);

        Optional<AppUser> optionalAppUser = appUserEntityDao.findById(AppUser.class, identifier);
        if (optionalAppUser.isPresent()) {
            AppUser appUser = optionalAppUser.get();
            appUser.setUserType(UserType.valueOf(word[4].toUpperCase()));
            appUserEntityDao.saveOrUpdate(appUser);
        }
    }

    private void handleListUsers() {
        appUserEntityDao
                .findAll(AppUser.class)
                .forEach(System.out::println);
    }

}
