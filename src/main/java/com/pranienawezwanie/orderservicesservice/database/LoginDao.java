package com.pranienawezwanie.orderservicesservice.database;

import com.pranienawezwanie.orderservicesservice.model.AppUser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class LoginDao {
    public Optional<AppUser> findByLogin(String login, String password) {
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<AppUser> criteriaQuery = cb.createQuery(AppUser.class);
            Root<AppUser> rootTable = criteriaQuery.from(AppUser.class);

            criteriaQuery.select(rootTable)
                    .where(
                            cb.and(
                                    cb.equal(rootTable.get("login"), login),
                                    cb.equal(rootTable.get("password"), password)
                            )
                    );
            return Optional.of(session.createQuery(criteriaQuery).getSingleResult());
        } catch (HibernateException he) {
            he.printStackTrace();
        } catch (NoResultException nre) {
            System.err.println("Błąd logowania.");
        }
        return Optional.empty();
    }
}
