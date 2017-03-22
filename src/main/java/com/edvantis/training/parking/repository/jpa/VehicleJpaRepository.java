package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.models.Garage;
import com.edvantis.training.parking.models.Vehicle;
import com.edvantis.training.parking.models.VehicleType;
import com.edvantis.training.parking.repository.VehicleRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class VehicleJpaRepository implements VehicleRepository {

    private final Logger logger = Logger.getLogger(ParkingJpaRepository.class);

    private EntityManagerFactory emFactory;

    public VehicleJpaRepository(EntityManagerFactory entityManagerFactory) {
        emFactory = entityManagerFactory;
    }

    @Override
    public Vehicle getById(int vehicleId) {
        EntityManager em = null;
        Vehicle vehicle = null;
        try {
            em = emFactory.createEntityManager();
            vehicle = em.find(Vehicle.class, vehicleId);
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return vehicle;
    }

    @Override
    public void insert(Vehicle vehicle) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.persist(vehicle);
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
    public void update(int vehicleId, Vehicle vehicle) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.merge(vehicle);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    public void update(Vehicle vehicle) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.merge(vehicle);
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
    public void delete(int vehicleId) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class, vehicleId));
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }


    }

    public void delete(Vehicle vehicle) {
        EntityManager em = null;
        try {
            em = emFactory.createEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class, vehicle.getId()));
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
    public Set<Vehicle> getAllVehiclesByType(VehicleType vehicleType) {
        return null;
    }

    @Override
    public Vehicle getVehicleByNumber(String vehicleNumber) {
        return null;
    }

    @Override
    public int getVehicleIdByNumber(String vehicleNumber) {
        return 0;
    }
}
