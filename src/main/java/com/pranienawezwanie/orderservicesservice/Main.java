package com.pranienawezwanie.orderservicesservice;

import com.pranienawezwanie.orderservicesservice.database.EntityDao;
import com.pranienawezwanie.orderservicesservice.database.HibernateUtil;
import com.pranienawezwanie.orderservicesservice.model.AppUser;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initial version.");
        HibernateUtil.getOurSessionFactory();
        System.out.println("Tested hibernate.");

        EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
        appUserEntityDao.saveOrUpdate(new AppUser("Marian", "Paździoch"));
    }
}
