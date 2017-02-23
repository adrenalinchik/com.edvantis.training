package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.jpa.HibernateUtil;
import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.repository.GarageJdbcRepository;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by taras.fihurnyak on 2/21/2017.
 */
public class GarageJpaRepositorySessionExample implements GarageJdbcRepository {

    private final Logger logger = Logger.getLogger(GarageJpaRepositorySessionExample.class);

    public GarageJpaRepositorySessionExample() {
    }

    @Override
    public Garage getById(int id) {
        Session session = null;
        Garage garage = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            garage = session.load(Garage.class, id);
            Hibernate.initialize(garage);
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return garage;
    }

    @Override
    public void insert(Garage garage) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(garage);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    @Override
    public void update(int garageId, Garage garage) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(garage);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void update(Garage garage) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(garage);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void delete(int garageId) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(getById(garageId));
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public void delete(Garage garage) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(garage);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public Set<Garage> getAllGaragesByType(GarageType garageType) {
        Set<Garage> garageSet = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Garage> criteria = builder.createQuery(Garage.class);
            Root<Garage> garageRoot = criteria.from(Garage.class);
            criteria.select(garageRoot).where(builder.equal(garageRoot.get("garageType"),garageType));
            garageSet = new HashSet<>(session.createQuery(criteria).getResultList());
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return garageSet;
    }

}
