package com.edvantis.training.parking.repository.jpa;

import com.edvantis.training.parking.models.*;
import com.edvantis.training.parking.repository.CrudRepository;
import com.edvantis.training.parking.repository.OwnerRepository;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.*;
import java.util.Set;

/**
 * Created by taras.fihurnyak on 2/24/2017.
 */
public class OwnerJpaRepository implements OwnerRepository, CrudRepository<Owner> {

    private final Logger logger = Logger.getLogger(OwnerJpaRepository.class);
    private EntityManagerFactory emFactory;

    public OwnerJpaRepository(EntityManagerFactory entityManagerFactory) {
        emFactory = entityManagerFactory;
    }

    @Override
    public Owner getById(long id) {
        return findById(emFactory, Owner.class, id);
    }

    @Override
    public Set<Owner> getAll() {
        return findAll(emFactory, Owner.class);
    }

    @Override
    public void insert(Owner owner) {
        save(emFactory, owner);
        logger.info(String.format("Owner %s %s saved to db successfully.", owner.getFirstName(), owner.getFirstName()));
    }

    @Override
    public void update(int id, Owner owner) {
        edit(emFactory, owner);
        logger.info(String.format("Owner with %s id updated successfully.", owner.getId()));
    }

    @Override
    public void delete(long id) {
        remove(emFactory, Owner.class, id);
        logger.info(String.format("Owner with %s id removed from db successfully.",id));
    }

    public void update(Owner owner) {
        edit(emFactory, owner);
        logger.info(String.format("Owner with %s id updated successfully.", owner.getId()));
    }

    @Override
    public Owner getByLastName(String lastName) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Owner> cq = builder.createQuery(Owner.class);
        Root<Owner> ownerRoot = cq.from(Owner.class);
        cq.where(builder.equal(ownerRoot.get(Owner_.lastName), lastName));
        Owner owner = em.createQuery(cq).getSingleResult();
        if (em.isOpen()) {
            em.close();
        }
        return owner;
    }

    @Override
    public Owner getByVehicleNumber(String vehicleNumber) {
        EntityManager em = emFactory.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Owner> cq = builder.createQuery(Owner.class);
        Root<Owner> ownerRoot = cq.from(Owner.class);
        SetJoin<Owner, Vehicle> products = ownerRoot.join(Owner_.userVehicles);
        Predicate predicate = builder.equal(products.get(Vehicle_.number), vehicleNumber);
        cq.where(predicate);
        Owner owner = em.createQuery(cq).getSingleResult();
        if (em.isOpen()) {
            em.close();
        }
        return owner;
    }
}
