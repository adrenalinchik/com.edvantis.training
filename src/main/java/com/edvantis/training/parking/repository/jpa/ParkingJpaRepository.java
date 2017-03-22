package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.Parking;
import com.edvantis.training.parking.repository.ParkingRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class ParkingJpaRepository implements ParkingRepository {

    private final Logger logger = Logger.getLogger(ParkingJpaRepository.class);
    private EntityManagerFactory emFactory;

    public ParkingJpaRepository(EntityManagerFactory entityManagerFactory) {
        emFactory = entityManagerFactory;
    }

    @Override
    public Parking getById(int parkingId) {
        EntityManager em = null;
        Parking parking = null;
        try {
            em = emFactory.createEntityManager();
            parking = em.find(Parking.class, parkingId);
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return parking;
    }

    @Override
    public void insert(Parking parking) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.persist(parking);
            em.getTransaction().commit();
            logger.info("Parking with" + parking.getId() + " id saved to db successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    @Override
    public void update(int parkingId, Parking parking) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.merge(parking);
            em.getTransaction().commit();
            logger.info("Parking with " + parking.getId() + " id updated successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    public void update(Parking parking) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.merge(parking);
            em.getTransaction().commit();
            logger.info("Parking with " + parking.getId() + " id updated successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void delete(int parkingId) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class, parkingId));
            em.getTransaction().commit();
            logger.info("Parking with " + parkingId + " id removed from db successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    public void delete(Parking parking) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class, parking.getId()));
            em.getTransaction().commit();
            logger.info("Parking with " + parking.getId() + " id removed from db successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
