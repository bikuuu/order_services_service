package com.pranienawezwanie.orderservicesservice.handlers;

import com.pranienawezwanie.orderservicesservice.database.AppUserDao;
import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.model.AppUser;
import com.pranienawezwanie.orderservicesservice.model.UserType;

import java.util.Optional;

public class UserHandler {
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
