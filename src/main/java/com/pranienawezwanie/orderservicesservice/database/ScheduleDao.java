package com.pranienawezwanie.orderservicesservice.database;

import com.pranienawezwanie.orderservicesservice.model.Schedule;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDao {
    public List<Schedule> findSchedulesByDate(LocalDate date) {
        List<Schedule> list = new ArrayList<>();
        SessionFactory sessionFactory = HibernateUtil.getOurSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Schedule> criteriaQuery = cb.createQuery(Schedule.class);
            Root<Schedule> rootTable = criteriaQuery.from(Schedule.class);

            criteriaQuery.select(rootTable)
                    .where(
                            cb.equal(
                                    rootTable.get("date"), date
                            )
                    )
                    .orderBy(
                            cb.asc(
                                    rootTable.get("slotNumber")
                            )
                    );

            list.addAll(session.createQuery(criteriaQuery).list());
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return list;
    }
}
