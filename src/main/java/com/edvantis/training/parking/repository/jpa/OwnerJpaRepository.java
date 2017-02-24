package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.jpa.JpaUtility;
import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.OwnerRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class OwnerJpaRepository implements OwnerRepository{

    private final Logger logger = Logger.getLogger(OwnerJpaRepository.class);

    @Override
    public Owner getById(int id) {
        EntityManager em = null;
        Owner owner = null;
        try {
            em = JpaUtility.getEntityManager();
            owner = em.find(Owner.class, id);
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return owner;
    }

    @Override
    public void insert(Owner owner) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.persist(owner);
            em.getTransaction().commit();
            logger.info("Owner " + owner.getFirstName() + " " + owner.getFirstName() + " saved to db successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void update(int ownerId, Owner owner) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.merge(owner);
            em.getTransaction().commit();
            logger.info("Owner with " + owner.getId() + " id updated successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void update(Owner owner) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.merge(owner);
            em.getTransaction().commit();
            logger.info("Owner with " + owner.getId() + " id updated successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public void delete(int ownerId) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class, ownerId));
            em.getTransaction().commit();
            logger.info("Owner with " + ownerId + " id removed from db successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }

    public void delete(Owner owner) {
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            em.getTransaction().begin();
            em.remove(em.find(Garage.class,owner.getId()));
            em.getTransaction().commit();
            logger.info("Owner with " + owner.getId() + " id removed from db successfully.");
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Set<Owner> getAllOwnersFromDb() {
        Set<Owner> ownerSet = null;
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Owner> cq = builder.createQuery(Owner.class);
            Root<Owner> owner = cq.from(Owner.class);
            cq.select(owner);
            ownerSet = new HashSet<>(em.createQuery(cq).getResultList());
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return ownerSet;
    }

    @Override
    public Owner getOwnerByLastName(String lastName) {
        Owner owner = null;
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Owner> cq = builder.createQuery(Owner.class);
            Root<Owner> ownerRoot = cq.from(Owner.class);
            cq.where(builder.equal(ownerRoot.get(Owner_.lastName), lastName));
            owner = em.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return owner;
    }

    @Override
    public Owner getOwnerByVehicleNumber(String vehicleNumber) {
        Owner owner = null;
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Owner> cq = builder.createQuery(Owner.class);
            Root<Owner> ownerRoot = cq.from(Owner.class);
            SetJoin<Owner,Vehicle> products = ownerRoot.join(Owner_.userVehicles);
            Predicate predicate = builder.equal(products.get(Vehicle_.number), vehicleNumber);
            cq.where(predicate);
            owner = em.createQuery(cq).getSingleResult();
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return owner;
    }

    private int getOwnerIdFromVehicleByNumber(String vehicleNumber) {
        int ownerId = 0;
        EntityManager em = null;
        try {
            em = JpaUtility.getEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Vehicle> cq = builder.createQuery(Vehicle.class);
            Root<Vehicle> vehicleRoot = cq.from(Vehicle.class);
            cq.where(builder.equal(vehicleRoot.get(Vehicle_.number), vehicleNumber));
            em.createQuery(cq);
        } catch (Exception e) {
            logger.warn(e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return ownerId;
    }



    @Override
    public int getOwnerIdByLastName(String ownerLastName) {
        return 0;
    }
}
