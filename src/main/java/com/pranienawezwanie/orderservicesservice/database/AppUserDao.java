package com.pranienawezwanie.orderservicesservice.database;

import com.pranienawezwanie.orderservicesservice.model.AppUser;
import org.hibernate.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppUserDao {
    private EntityDao<AppUser> appUserEntityDao = new EntityDao<>();
    private static SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();

    public boolean existsUserWithLogin(String searchedLogin) {
        List<AppUser> list = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AppUser> criteriaQuery = cb.createQuery(AppUser.class);
            Root<AppUser> rootTable = criteriaQuery.from(AppUser.class);

            criteriaQuery
                    .select(rootTable)
                    .where(
                            cb.equal(rootTable.get("login"), searchedLogin)
                    );
            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return !list.isEmpty();
    }

    public boolean passwordChecker(String[] words) {
        String login = words[1];

        Optional<AppUser> optionalAppUser = appUserEntityDao.findByLogin(AppUser.class, login);
        if (optionalAppUser.isPresent()) {
            AppUser appUser = optionalAppUser.get();
            if (appUser.getPassword().equals(words[2])) {
                return true;
            }
        }
        return false;
    }

}
