package com.pranienawezwanie.orderservicesservice.handlers;

import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.model.Address;
import com.pranienawezwanie.orderservicesservice.model.AppUser;

import java.util.Optional;

public class AddressHandler {
    private EntityDao<AppUser> appUserEntityDao = new EntityDao<>();

    public void addressHandler(String[] words){
        if (words[1].equalsIgnoreCase("addaddress")) {
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
}
