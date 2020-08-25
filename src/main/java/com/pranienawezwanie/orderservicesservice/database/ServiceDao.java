package com.pranienawezwanie.orderservicesservice.database;

import com.pranienawezwanie.orderservicesservice.model.Service;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao {
    public List<Service> findAllServicesByName(String phrase) {
        List<Service> list = new ArrayList<>();

        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Service> criteriaQuery = cb.createQuery(Service.class);
            Root<Service> rootTable = criteriaQuery.from(Service.class);
            criteriaQuery.select(rootTable)
                    .where(
                            cb.like(
                                    rootTable.get("name"),
                                    "%" + phrase + "%"
                            )
                    );

            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }
}
