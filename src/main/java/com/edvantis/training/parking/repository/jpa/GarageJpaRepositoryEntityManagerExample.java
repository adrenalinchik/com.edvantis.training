package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.jpa.JpaUtility;
import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.GarageType;
import com.edvantis.training.parking.models.Garage_;
import com.edvantis.training.parking.repository.GarageJdbcRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/22/2017.
 */
public class GarageJpaRepositoryEntityManagerExample implements GarageJdbcRepository {

    private final Logger logger = Logger.getLogger(GarageJpaRepositoryEntityManagerExample.class);

    @Override
    public Garage getById(int id) {
        EntityManager em = null;
        Garage garage = null;
        try {
            em = JpaUtility.getEntityManager();
            garage = em.find(Garage.class, id);
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return garage;
    }

    @Override
    public void insert(Garage garage) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.persist(garage);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void update(int garageId, Garage garage) {

    }

    public void update(Garage garage) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.merge(garage);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void delete(int garageId) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class, garageId));
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }
    public void delete(Garage garage) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class,garage.getId()));
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public Set<Garage> getAllGaragesByType(GarageType garageType) {
        Set<Garage> garageSet = null;
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Garage> cq = builder.createQuery(Garage.class);
            Root<Garage> garage = cq.from(Garage.class);
            cq.where(builder.equal(garage.get(Garage_.garageType), garageType));
            garageSet = new HashSet<>(em.createQuery(cq).getResultList());
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return garageSet;
    }
}
